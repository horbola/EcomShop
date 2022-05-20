/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.core;

import com.saif.web.practice.shop.beans.AddressBean;
import com.saif.web.practice.shop.beans.CartBean;
import com.saif.web.practice.shop.beans.CartOrder;
import com.saif.web.practice.shop.beans.CustomerBean;
import com.saif.web.practice.shop.beans.OrderBean;
import com.saif.web.practice.shop.beans.ProductBean;
import com.saif.web.practice.shop.beans.WishBean;
import com.saif.web.practice.shop.beans.WishCartOrder;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

/**
 *
 * @author Saif
 */
public class Operations implements Shop{
    private DSFactory dsf;
    private AppStore appStore;
    private CustomerBean customer;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public void setDsf(DSFactory dsf){this.dsf = dsf;}
    public void setAppStore(AppStore appStore){this.appStore = appStore;}
    public void setCustomer(CustomerBean customer){this.customer = customer;}
    public void setRequest(HttpServletRequest request) {this.request = request;}
    public void destoryRequest(HttpServletRequest request) {this.request = null;}
    public void setResponse(HttpServletResponse response) {this.response = response;}
    public void destoryResponse(HttpServletResponse response) {this.response = null;}
    

    /**
     * app doesn't get deployed in tomcat nor glassfish if i try to initialize it
     * here with the method 'dsf.getSqliteDS()' instead of calling it in constructor.
     * i thought no method gets called until they are used inside another method even if
     * they are used as field initializer. but it seems servers don't support it.
     */
    DataSource shopDraft;
    
    public Operations(){}
    
    public Operations(DSFactory dsf){
        this.dsf = dsf;
        shopDraft = dsf.getDataSource(DSType.SQLITE_SHOP_DRAFT);
        Executor.setDs(shopDraft);
    }
    

    
    public HashMap<String, ProductBean> getAllProducts() {
        l().debug("getAllProducts() starts: ");
        try(Connection conn = shopDraft.getConnection();
            Statement s = conn.createStatement()){
        
            String sql = appStore.getSelectAllSql();
            try(ResultSet rs = s.executeQuery(sql)){
                appStore.fillAllProducts(rs);
                return appStore.getProducts();
            }catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
                l().debug("");
            }
            
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("");
        }
        return null;
    }
    
    public ProductBean getOneProduct(){
        return appStore.getOneProduct(request);
    }
    
    
    
    
    
    
    public void setLoginInfo() {
        customer.setLoginInfo(request);
    }
    
    public void login() {
        try(Connection conn = shopDraft.getConnection();
            Statement s = conn.createStatement()){
        
            try(ResultSet rs = s.executeQuery(customer.getSelectSql())){
                if(rs.next() != false && customer.getPassword().equals(rs.getString("password"))){
                    customer.setName(rs.getString("name"));
                    customer.setAuthenticated(true);
                }
            }catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
                l().debug("Customer login sql couldn't be executed");
            }
            
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("Connection or Statement couldn't be created");
        }
    }
    
    
    public void setSignupInfo(){
        customer.setSignupInfo(request);
    }
    
    public void signup(){
        try(Connection conn = shopDraft.getConnection();
            Statement s = conn.createStatement()){
        
            s.executeUpdate(customer.getInsertSql());
            customer.setAuthenticated(true);
            
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("Signup couldn't be performed. Problem occuured in connection or statment");
        }
    }
    
    public boolean isCustomerAuthenticated(){
        return customer.isAuthenticated();
    }
    
    public void updatePassword() {
        try(Connection conn = shopDraft.getConnection();
            Statement s = conn.createStatement()){
        
            customer.setPassword(request.getParameter("password"));
            String sql = customer.getUpdatePasswordSql();
            s.executeUpdate(sql);
            
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("");
        }
    }
    
    public void updateName(){
        try(Connection conn = shopDraft.getConnection();
            Statement s = conn.createStatement()){
        
            customer.setName(request.getParameter("name"));
            String sql = customer.getUpdateNameSql();
            s.executeUpdate(sql);
            
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("");
        }
    }
    


    
    
    
    public int getWishCount(){
        return customer.getWish().getCount();
    }

    public HashMap<String,ProductBean> getWishProducts(){
        return customer.getWish().getProducts();
    }
    
    public void fetchWishProductsInfo() {
        WishBean wish = customer.getWish();
        fetchProductsInfo(wish);
    }
    
    public void fetchProductsInfo(WishCartOrder wco){
        try(Connection conn = shopDraft.getConnection();
            Statement s = conn.createStatement()){
        
            for(Map.Entry<String,ProductBean> entry : wco.getProducts().entrySet()){
                ProductBean product = entry.getValue();
                String sql = product.getSelectOneSql();
                try(ResultSet rs = s.executeQuery(sql)){
                    if(rs.next()){
                        product.fillProuductsInfo(rs);
                    }
                }catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
                    l().debug("");
                }
            }
            
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("");
        }
    }
    
    public void addToWish() {
        boolean isAdded = customer.getWish().addToProducts(request);
    }
    
    public void syncWish() {
        WishBean wish = customer.getWish();
        try(Connection conn = shopDraft.getConnection();
            Statement s = conn.createStatement()){
        
            try(ResultSet rs = s.executeQuery(wish.getSelectSql())){
                wish.sync(rs);
                s.execute(wish.getDeleteSql());
            }catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
                l().debug("Fetching wish produccts couldn't be performed");
            }
            
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("Connection or statmenet couldn't be created from inside fetchWish()");
        }
    }
    
    public void storeWish() {
        WishBean wish = customer.getWish();
        if(wish.getProducts() == null || wish.getProducts().size() == 0) return;
        try(Connection conn = shopDraft.getConnection();
            Statement s = conn.createStatement()){
        
            ArrayList<String> sqls = wish.getInsertAllSql();
            for(String sql : sqls){
                s.executeUpdate(sql);
            }
            
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("Storing wish products from bean to table couldn't be possible. Problem occured in creating connection or statement");
        }
    }
    
    
    

    
    
    
    
    
    
    
    
    public int getCartCount(){
        return customer.getCart().getCount();
    }
    
    public HashMap<String,ProductBean> getCartProducts(){
        return customer.getCart().getProducts();
    }
    
    public void fetchCartProductsInfo() {
        CartBean cart = customer.getCart();
        fetchProductsInfo(cart);
    }
    
    public void addToCart() {
        boolean isAdded = customer.getCart().addToProducts(request);
    }
    
    public void syncCart(){
        CartBean cart = customer.getCart();
        try(Connection conn = shopDraft.getConnection();
            Statement s = conn.createStatement()){
        
            try(ResultSet rs = s.executeQuery(cart.getSelectAllSql())){
                cart.sync(rs);
                s.execute(cart.getDeleteSql());
            }catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
                l().debug("Cart Product select sql couldn't be executed");
            }
            
            try(ResultSet rs = s.executeQuery(cart.getPrevShippingAddress().getSelectOneSql())){
                if(rs.next()) cart.fillPrevShippingAddress(rs);
            }catch (SQLException | NullPointerException ex) {
                java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
                l().debug("Shipping address select sql couldn't be executed");
            }
            
            try(ResultSet rs = s.executeQuery(cart.getPrevBillingAddress().getSelectOneSql())){
                if(rs.next()) cart.fillPrevBillingAddress(rs);
            }catch (SQLException | NullPointerException ex) {
                java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
                l().debug("Billing address select sql couldn't be executed");
            }
            
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("Problem occured to create Connection or Statement to fetch cart info");
        }
    }
    
    public void storeCartOrder(CartOrder co) {
        try(Connection conn = shopDraft.getConnection();
            Statement s = conn.createStatement()){
            
            if(customer.getAddressBook() == null) fetchAddresses();
            
            synchronized(this){
                if(!co.noStoreAddress(AddressType.SHIPPING)){
                    generateAddressID(co, AddressType.SHIPPING, s);
                    String sql = co.getShippingAddress().getInserOneSql();
                    s.executeUpdate(sql);
                }
                else{
                    boolean isTransferred = co.transferAddressID(AddressType.SHIPPING);
                    if(!isTransferred) co.transferAddressIDFromAddressBook(AddressType.SHIPPING);
                }
                
                
                if(!co.noStoreAddress(AddressType.BILLING)){
                    generateAddressID(co, AddressType.BILLING, s);
                    String sql = co.getBillingAddress().getInserOneSql();
                    s.executeUpdate(sql);
                }
                else{
                    boolean isTransferred = co.transferAddressID(AddressType.BILLING);
                    if(!isTransferred) co.transferAddressIDFromAddressBook(AddressType.BILLING);
                }
            }
            
            if(co instanceof CartBean){
                for(String sql : ((CartBean) co).getInsertAllSql()){
                    s.executeUpdate(sql);
                }
            }
            else if (co instanceof OrderBean){
                synchronized(this){
                    generateOrderID((OrderBean) co, s);
                    for(String sql : ((OrderBean) co).getInsertAllSql()){
                        s.executeUpdate(sql);
                    }
                }
            }
            
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("");
        }
    }

    
    
    private void generateAddressID(CartOrder co, AddressType type, Statement s) throws SQLException{
        if(type == AddressType.SHIPPING){
            String sql = co.getShippingAddress().getSelectMaxAddressIDSql();
            try(ResultSet rs = s.executeQuery(sql)){
                int aID = 0;
                if(rs.next()) aID = rs.getInt("maxAddressID");
                co.getShippingAddress().setAddressID(aID+1);
            }catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
                l().debug("maxAddressID to generate Shipping AddressID couldn't be possible");
            }
        }
        else if(type == AddressType.BILLING){
            String sql = co.getBillingAddress().getSelectMaxAddressIDSql();
            try(ResultSet rs = s.executeQuery(sql)){
                int aID = 0;
                if(rs.next()) aID = rs.getInt("maxAddressID");
                co.getBillingAddress().setAddressID(aID+1);
            }catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
                l().debug("maxAddressID to generate billing AddressID couldn't be possible");
            }
        }
        else l().debug("There is a problem in addressID generation");
    }
    
    private void generateOrderID(OrderBean order, Statement s){
        String sql = order.getSelectMaxOrderIDSql();
        try(ResultSet rs = s.executeQuery(sql)){
            int oID = 0;
            if(rs.next()) oID = rs.getInt("maxOrderID");
            order.setOrderID(oID+1);
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("");
        }
    }
    
    
    public void addToBuyNowCart(){
        boolean isAdded = customer.getBuyNowCart().addToProducts(request);
    }
    
    public ProductBean getTheBuyNowProduct(){
        ProductBean product = customer.getBuyNowCart().getOneProduct(request);
        return product;
    }
    





    
    
    
    
    public OrderBean getCurrOrder() {
        return customer.getOrderFromCart();
    }

    public void fetchAllOrders() {
        try(Connection conn = shopDraft.getConnection();
            Statement s = conn.createStatement()){
        
            String sql = customer.getAllOrdersSql();
            try(ResultSet rs = s.executeQuery(sql)){
                customer.fillAllOrders(rs);
            }catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
                l().debug("");
            }
            
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("");
        }
    }
    
    public HashMap<Integer,OrderBean> getAllOrders(){
        if(customer.getAllOrders() == null) fetchAllOrders();
        return customer.getAllOrders();
    }
    
    public void placeOrder() {
        CartBean cart = customer.getCart();
        cart.fillRequestShippingAddress(request);
        cart.fillRequestBillingAddress(request);
        
        String deliMeth = request.getParameter("deliveryMethod");
        if(deliMeth != null){
            if(!(deliMeth.equalsIgnoreCase("null") || deliMeth.equals("")))
                cart.setDeliveryMethod(deliMeth);
        }
        //Promp to correct the delivery method
        else cart.setDeliveryMethod(1);
        
        String paymMeth = request.getParameter("paymentMethod");
        if(paymMeth != null){
            if(!(paymMeth.equalsIgnoreCase("null") || paymMeth.equals("")))
                cart.setPaymentMethod(paymMeth);
        }
        //Promp to correct the payment method
        else cart.setPaymentMethod(1);
        
        OrderBean orderFromCart = customer.buildOrderFromCart();
        storeCartOrder(orderFromCart);
        customer.setCartToNull();
    }
    
    public void placeBuyNowOrder() {
        CartBean buyNowCart = customer.getBuyNowCart();
        
        buyNowCart.fillRequestShippingAddress(request);
        buyNowCart.fillRequestBillingAddress(request);
        
        String deliMeth = request.getParameter("deliveryMethod");
        if(deliMeth != null){
            if(!(deliMeth.equalsIgnoreCase("null") || deliMeth.equals("")))
                buyNowCart.setDeliveryMethod(deliMeth);
        }
        //Promp to correct the delivery method
        else buyNowCart.setDeliveryMethod(1);
        
        String paymMeth = request.getParameter("paymentMethod");
        if(paymMeth != null){
            if(!(paymMeth.equalsIgnoreCase("null") || paymMeth.equals("")))
                buyNowCart.setPaymentMethod(paymMeth);
        }
        //Promp to correct the payment method
        else buyNowCart.setPaymentMethod(1);
        
        OrderBean orderFromBuyNowCart = customer.buildOrderFromBuyNowCart();
        storeCartOrder(orderFromBuyNowCart);
        customer.setBuyNowCartToNull();
    }
    
    
    
    
    public void fetchAddresses(){
        try(Connection conn = shopDraft.getConnection();
            Statement s = conn.createStatement()){
            
            String sql = customer.getSelectAllSql();
            try(ResultSet rs = s.executeQuery(sql)){
                customer.fillAddressBook(rs);
            }catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
                l().debug("");
            }
         
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("");
        }
    }
    
    public ArrayList<AddressBean> getAddressBook() {
        if(customer.getAddressBook() == null) fetchAddresses();
        return customer.getAddressBook();
    }
    
}
