/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.beans;

import static com.saif.web.practice.shop.jooq.Tables.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

/**
 *
 * @author Saif
 */
public class CustomerBean extends Bean {
    private String customerID = "";
    private String password = "";
    private String name = "";

    public String getCustomerID() {return customerID;}
    public void setCustomerID(String customerID) {this.customerID = customerID;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    
    
    
    private boolean authenticated = false;

    public boolean isAuthenticated() {return authenticated;}
    public void setAuthenticated(boolean authenticated) {this.authenticated = authenticated;}

    
    
    private ArrayList<AddressBean> addressBook;

    public ArrayList<AddressBean> getAddressBook() {return addressBook;}
    public void setAddressBook(ArrayList<AddressBean> addressBook) {this.addressBook = addressBook;}
    
    
    
    private WishBean wish;
    
    private CartBean cart;
    private OrderBean orderFromCart;
    
    private CartBean buyNowCart;
    private OrderBean orderFromBuyNowCart;
    
    private HashMap<Integer,OrderBean> canOrders;
    private HashMap<Integer,OrderBean> exedOrders;
    private HashMap<Integer,OrderBean> allOrders;

    public WishBean getWish() {
        if(wish == null) wish = new WishBean(this);
        return wish;
    }
    public void setWish(WishBean wish) {this.wish = wish;}

    
    
    public CartBean getCart() {
        if(cart == null) cart = new CartBean(this);
        return cart;
    }
    public void setCart(CartBean cart) {this.cart = cart;}
    public void setCartToNull(){cart = null;}
    
    public OrderBean getOrderFromCart() {return orderFromCart;}
    public void setOrderFromCart(OrderBean orderFromCart) {this.orderFromCart = orderFromCart;}
    public OrderBean buildOrderFromCart(){
        orderFromCart = new OrderBean(this, cart);
        return orderFromCart;
    }
    
    
    

    public CartBean getBuyNowCart() {
        if(buyNowCart == null) buyNowCart = new CartBean(this);
        return buyNowCart;
    }
    public void setBuyNowCart(CartBean buyNowCart) {this.buyNowCart = buyNowCart;}
    public void setBuyNowCartToNull(){buyNowCart = null;}
    
    public OrderBean getOrderFromBuyNowCart() {return orderFromBuyNowCart;}
    public void setOrderFromBuyNowCart(OrderBean orderFromBuyNowCart) {this.orderFromBuyNowCart = orderFromBuyNowCart;}
    public OrderBean buildOrderFromBuyNowCart(){
        orderFromBuyNowCart = new OrderBean(this, buyNowCart);
        return orderFromBuyNowCart;
    }


    
    public HashMap<Integer,OrderBean> getCanOrders() {return canOrders;}
    public void setCanOrders(HashMap<Integer,OrderBean> canOrders) {this.canOrders = canOrders;}

    public HashMap<Integer,OrderBean> getAllOrders() {return allOrders;}
    public void setAllOrders(HashMap<Integer,OrderBean> allOrders) {this.allOrders = allOrders;}

    public HashMap<Integer,OrderBean> getExedOrders() {return exedOrders;}
    public void setExedOrders(HashMap<Integer,OrderBean> exedOrders) {this.exedOrders = exedOrders;}
    
    
    
    public CustomerBean(){}
    public CustomerBean(String customerID){this.customerID = customerID;}
    public CustomerBean(HttpServletRequest request) {setLoginInfo(request);}
    
    public boolean setLoginInfo(HttpServletRequest request){
        String customerID = request.getParameter("customerID");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        
        if(customerID != null){
            if(!(customerID.equalsIgnoreCase("null") || customerID.equals("")))
                this.customerID = customerID;
        }
        else return false;
        
        if(password != null){
            if(!(password.equalsIgnoreCase("null") || password.equals("")))
                this.password = password;
        }
        else return false;
        
        if(name != null){
            if(!(name.equalsIgnoreCase("null") || name.equals("")))
                this.name = name;
        }
        else return false;
        
        return true;
    }
    
    public boolean setSignupInfo(HttpServletRequest request){
        return setLoginInfo(request);
    }
    
    public void fillAddressBook(ResultSet rs) throws SQLException {
        if(addressBook == null) addressBook = new ArrayList<AddressBean>();
        while(rs.next()){
            AddressBean address = new AddressBean(this);
            address.setAddressID(rs.getInt("addressID"));
            address.setAddressType(rs.getString("addressType"));
            address.setFlat(rs.getString("flat"));
            address.setHouse(rs.getString("house"));
            address.setRoad(rs.getString("road"));
            address.setArea(rs.getString("area"));
            address.setThana(rs.getString("thana"));
            address.setDistrict(rs.getString("district"));
            addressBook.add(address);
        }
    }
    
    
    public void fillAllOrders(ResultSet rs) throws SQLException {
        if(allOrders == null) allOrders = new HashMap<Integer,OrderBean>();
        OrderBean order = null;
        while(rs.next()){
            int oID = rs.getInt("orderID");
            if(!allOrders.containsKey(oID)){
                order = new OrderBean(this, oID);
                
                int shipAddrID = rs.getInt("shippingAddressID");
                AddressBean shipAddr = containsShippingAddress(shipAddrID, allOrders);
                if(shipAddr != null) order.setShippingAddress(shipAddr);
                else order.setShippingAddress(new AddressBean(this, shipAddrID));
                
                int billAddrID = rs.getInt("billingAddressID");
                AddressBean billAddr = containsBillingAddress(billAddrID, allOrders);
                if(billAddr != null) order.setBillingAddress(billAddr);
                else order.setBillingAddress(new AddressBean(this, billAddrID));
                
                order.setDeliveryMethod(rs.getInt("deliveryMethod"));
                order.setPaymentMethod(rs.getInt("paymentMethod"));
                
                order.addToProducts(rs);
                
                //order.setDate(rs.getDate("date"));
                //order.setStatus(rs.getInt("status"));
                
                order.setDate(new Date(System.currentTimeMillis()));
                order.setStatus(0);
                
                allOrders.put(oID, order);
            }
            else{
                order = allOrders.get(oID);
                order.addToProducts(rs);
            }
        }
    }
    
    private AddressBean containsShippingAddress(int shipAddrID, HashMap<Integer,OrderBean> allOrders){
        for(Map.Entry<Integer,OrderBean> entry : allOrders.entrySet()){
            OrderBean order = entry.getValue();
            if(shipAddrID == order.getShippingAddress().getAddressID()){
                return order.getShippingAddress();
            }
        }
        return null;
    }
    
    private AddressBean containsBillingAddress(int billAddrID, HashMap<Integer,OrderBean> allOrders){
        for(Map.Entry<Integer,OrderBean> entry : allOrders.entrySet()){
            OrderBean order = entry.getValue();
            if(billAddrID == order.getBillingAddress().getAddressID()){
                return order.getBillingAddress();
            }
        }
        return null;
    }
    
    
    SupplierBean containsSupplier(HashMap<Integer,OrderBean> allOrders, String supplierID){
        Iterator<OrderBean> ordersItr = allOrders.values().iterator();
        while(ordersItr.hasNext()){
            HashMap<String,ProductBean> products = ordersItr.next().getProducts();
            if(products == null) continue;
            SupplierBean supplier = null;
            Iterator<ProductBean> productsItr = products.values().iterator();
            while(productsItr.hasNext()){
                ProductBean product = productsItr.next();
                String suppID = product.getSupplier().getSupplierID();
                if(suppID.equals(supplierID)){
                    supplier = product.getSupplier();
                    return supplier;
                }
            }
        }
        return null;
    }
    
    
    
    
    
    public String getSelectSql(){
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.select()
                         .from(CUSTOMERS)
                         .where(CUSTOMERS.CUSTOMERID.eq(customerID))
                         .getSQL(ParamType.INLINED);
       return s;
    }
    
    public String getInsertSql(){
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.insertInto(CUSTOMERS, CUSTOMERS.CUSTOMERID, CUSTOMERS.PASSWORD, CUSTOMERS.NAME)
                         .values(customerID, password, name)
                         .getSQL(ParamType.INLINED);
        return s;
    }
    
    public String getUpdatePasswordSql(){
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.update(CUSTOMERS)
                         .set(CUSTOMERS.PASSWORD, password)
                         .where(CUSTOMERS.CUSTOMERID.eq(customerID))
                         .getSQL(ParamType.INLINED);
        return s;
    }
    
    public String getUpdateNameSql(){
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.update(CUSTOMERS)
                         .set(CUSTOMERS.NAME, name)
                         .where(CUSTOMERS.CUSTOMERID.eq(customerID))
                         .getSQL(ParamType.INLINED);
        return s;
    }

    
    
    public String getSelectAllSql(){
        DSLContext create = DSL.using(SQLDialect.SQLITE);    
        String s = create.selectFrom(ADDRESSES)
                         .where(ADDRESSES.CUSTOMERID.eq(customerID))
                         .getSQL(ParamType.INLINED);
        return s;
    }
    
    public String getAllOrdersSql() {
        DSLContext create = DSL.using(SQLDialect.SQLITE);    
        String s = create.selectFrom(ORDERS)
                           .where(ORDERS.CUSTOMERID.eq(customerID))
                           .getSQL(ParamType.INLINED);
        return s;
    }

}
