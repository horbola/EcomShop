/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.core;

import com.saif.web.practice.shop.bean.CartBean;
import com.saif.web.practice.shop.bean.CustomerBean;
import com.saif.web.practice.shop.bean.OrderBean;
import com.saif.web.practice.shop.bean.ProductBean;
import com.saif.web.practice.shop.servlet.Cart;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.logging.Level;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Saif
 */
public class Operations {
    private Logger log = LogManager.getLogger(Operations.class);
    private String jdbcPath;
    
    public Operations(String jdbcPath){
        loadJdbc();
        this.jdbcPath = jdbcPath;
    }
    
    public void loadJdbc(){
        try{Class.forName("org.sqlite.JDBC");}
        catch(ClassNotFoundException ex){ex.printStackTrace();}
    }

    public String getJdbcPath() {
        return jdbcPath;
    }

    
    
    public CustomerBean authenticate(CustomerBean loginCust) {
        String selectBuff = loginCust.getSelectSql();
        SortedMap result[] = null;
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            ResultSet rs = s.executeQuery(selectBuff);
            result = ResultSupport.toResult(rs).getRows();
            
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
        
        CustomerBean customer = null;
        if(result != null && result.length != 0 && loginCust.getUserName().equals(result[0].get("uName"))){
            customer = new CustomerBean();
            customer.setUserName((String) result[0].get("uName"));
            customer.setPass((String) result[0].get("pass"));
            customer.setName((String) result[0].get("name"));
        }
        return customer;
    }
    
    

    public SortedMap[] showAllProducts() {
        SortedMap[] products = null;
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(jdbcPath)){
            s = conn.createStatement();
            ResultSet rs = s.executeQuery(new ProductBean().getSelectAllSql());
            products = ResultSupport.toResult(rs).getRows();
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
        
        return products;
    }
    
    public void getProdInfo(ProductBean product){
        try(Connection conn = DriverManager.getConnection(jdbcPath)){
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(product.getProductSql());
            product.setMfrId(rs.getString("mfrId"));
            product.setCategory(rs.getString("cate"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getString("price"));
            product.setQuantity(rs.getString("quantity"));
        }catch (SQLException ex) {}
    }
    
    public int updateCart(CartBean cart){
        log.debug("updateCart(), starts: ");
        int cartCount = 0;
        ResultSet rs = null;
        try(Connection conn = DriverManager.getConnection(jdbcPath)){
            Statement s = conn.createStatement();
            
            //This portion doesn't work here. It must be a container's problem.
            //I saw it worked first. after some days it doesn't work.
            rs = s.executeQuery(cart.getCartProdSql());
            cartCount = getRsRowCount(rs, cart);
            log.debug("updateCart(): "+cartCount);
            while(rs.next()){
                if(rs.getInt("productId") == cart.getTheProd().getProductIdInt()){
                    log.debug("updateCart(): "+rs.getString("productId") +"this product already exists");
                    return getRsRowCount(rs, cart);
                }
            }
            
            s.executeUpdate(cart.getUpdateCartSql());
            rs = s.executeQuery(cart.getCartProdSql());
            cartCount = getRsRowCount(rs, cart);
        }catch(SQLException e){}
        
        log.debug("updateCart(), starts: ");
        return cartCount;
    }
    
    private int getRsRowCount(ResultSet rs, CartBean cart) throws SQLException{
        int cartCount = 0;
        while(rs.next()){
            log.debug("rs.getString(\"productId\") is: "+rs.getString("productId") +"equals(cart.getTheProd().getProductId())"+equals(cart.getTheProd().getProductId()));
            cartCount++;
        }
        return cartCount;
    }

    public SortedMap[] makeOrder(OrderBean order) {
        log.debug("makeOrder(), starts: "+order);
        SortedMap[] orderFeedback = null;
        
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            log.debug("try(Connection conn = DriverManager.getConnection(getJdbcPath())){");
            s = conn.createStatement();
            log.debug("s = conn.createStatement();");
            // gets the highest orderId from Orders table so that the next number
            // can be generated for the new order.
            
            ResultSet rs = s.executeQuery(order.getHighestOrderIdSql());
            log.debug("ResultSet rs = s.executeQuery(order.getHighestOrderIdSql());");
            int maxOrderId = 0;
            maxOrderId = rs.getInt("max(orderId)");
            order.setOrderId(maxOrderId);
            log.debug("order.setOrderId(maxOrderId);");
            
            // Inserts a single order into Orders table.
            for(StringBuffer b : order.getInsertSql()){
                log.debug("for(StringBuffer b : order.getInsertSql()){");
                s.executeUpdate(b.toString());
            }
            log.debug("After: for(StringBuffer b : order.getInsertSql()){");
            
            // gets the inserted oreder to give the feedback to the customer.
            rs = s.executeQuery(order.getFeedbackOrderSql());
            orderFeedback = ResultSupport.toResult(rs).getRows();
            
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){log.debug("makeOrder(), exception occured");}
        }
        
        log.debug("finally{");
        for(SortedMap m : orderFeedback){
            log.debug("makeOrder(): orderId: "+m.get("orderId"));
            log.debug("makeOrder(): productId: "+m.get("productId"));
            log.debug("makeOrder(): mfrId: "+m.get("mfrId"));
            log.debug("makeOrder(): quantity: "+m.get("quantity"));
        }
        log.debug("for(SortedMap m : orderFeedback){");
        if(orderFeedback == null) log.debug("makeOrder(), ends: orderFeedback is null");
        return orderFeedback;
    }

    public CustomerBean makeCustomer(CustomerBean signUpCust){
        String select = signUpCust.getSelectSql();
        String insert = signUpCust.getInsertSql();
        
        SortedMap custFeedback = null;
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            s.executeUpdate(insert);
            
            ResultSet rs = s.executeQuery(select);
            custFeedback = ResultSupport.toResult(rs).getRows()[0];
            
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
        
        CustomerBean customer = new CustomerBean();
        customer.setUserName((String) custFeedback.get("uName"));
        customer.setPass((String) custFeedback.get("pass"));
        customer.setName((String) custFeedback.get("name"));
        
        return customer;
    }


    public void getCart(CartBean cart){
        log.debug("getCart(), starts: ");
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            ResultSet rs = s.executeQuery(cart.getCart_ProductSql());
            cart.fillCartProdsResult(rs);
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
        log.debug("getCart(), starts: ");
    }
    

    public CustomerBean updateProfile(CustomerBean old, CustomerBean custNew) {
        SortedMap updateProfileFeedback = null;
        
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            s.executeUpdate(custNew.getUpdateSql(old));
            
            ResultSet rs = s.executeQuery(custNew.getUpdateFeedbackSql());
            updateProfileFeedback = ResultSupport.toResult(rs).getRows()[0];
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
        
        return updateCustomer(updateProfileFeedback);
    }

    private CustomerBean updateCustomer(SortedMap updateProfileFeedback) {
        CustomerBean customer = new CustomerBean();
        customer.setUserName((String) updateProfileFeedback.get("uName"));
        customer.setPass((String) updateProfileFeedback.get("pass"));
        customer.setName((String) updateProfileFeedback.get("name"));
        return customer;
    }

    public ProductBean productsEntry(ProductBean product) {
        SortedMap productFeedbackMap = null;
        
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            s.executeUpdate(product.getInsertSql());
            
            ResultSet rs = s.executeQuery(product.getSelectSql());
            productFeedbackMap = ResultSupport.toResult(rs).getRows()[0];
            
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
        
        ProductBean productFeedback = new ProductBean();
        productFeedback.setProductId(productFeedbackMap.get("productId").toString());
        productFeedback.setMfrId(productFeedbackMap.get("mfrId").toString());
        productFeedback.setDescription(productFeedbackMap.get("description").toString());
        productFeedback.setPrice(productFeedbackMap.get("price").toString());
        productFeedback.setQuantity(productFeedbackMap.get("quantity").toString());
        
        return productFeedback;
    }
    
    
    
    public HashMap<String, ArrayList<OrderBean>> reviewOrder() {
        String selectBuff = "select * from OrdersTemp;";
        SortedMap orders[] = null;
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            ResultSet rs = s.executeQuery(selectBuff);
            orders = ResultSupport.toResult(rs).getRows();
            
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
         HashMap<String, ArrayList<OrderBean>> customers = parse(orders);
         logCustomers(customers);
         return customers;
    }
    
    
    private HashMap<String, ArrayList<OrderBean>> parse(SortedMap[] rows){
        HashMap<String, ArrayList<OrderBean>> customers = new HashMap<>();
        
        int rowCount = 0;
        ArrayList<OrderBean> orders;
        OrderBean order;
        ProductBean product;
        
        for(Map m : rows){
            String uName = (String) m.get("uName");
            
            if(!customers.containsKey(uName)){
                orders = new ArrayList<>();
                
                order = new OrderBean();
                order.setOrderIdReview((int) m.get("orderId"));
                order.setStatus((int) m.get("status")); 
                
                rowCount++;
                product = new ProductBean();
                product.setMfrId((String) m.get("mfrId"));
                product.setProductId(m.get("productId").toString());
                product.setOrderQuan(m.get("quantity").toString());
                
                order.addProduct(product);
                orders.add(order);
                customers.put(uName, orders);
            }else {
                orders = customers.get(uName);
                int oid = (int) m.get("orderId");
                
                if(containsOrder(orders, oid))
                    order = getOrder(orders, oid);
                else {
                    order = new OrderBean();
                    order.setOrderIdReview(oid);
                    order.setStatus((int) m.get("status")); 
                    orders.add(order);
                }
                
                rowCount++;
                product = new ProductBean();
                product.setMfrId((String) m.get("mfrId"));
                product.setProductId(m.get("productId").toString());
                product.setOrderQuan(m.get("quantity").toString());
                order.addProduct(product);
            }
        }
        return customers;
    }
    
    private boolean containsOrder(ArrayList<OrderBean> orders, int oid){
        for(OrderBean o : orders){
            if(o.getOrderId() == oid)
                return true;
        }
        return false;
    }
    
    private OrderBean getOrder(ArrayList<OrderBean> orders, int oid){
        for(OrderBean o : orders){
            if(o.getOrderId() == oid)
                return o;
        }
        return null;
    }
    
    private void logCustomers(HashMap<String, ArrayList<OrderBean>> customers){
        int rowCount = 0;
        for(String uName : customers.keySet()){
            ArrayList<OrderBean> o = customers.get(uName);
            for(OrderBean order : o){
                ArrayList<ProductBean> p = order.getProducts();
                for(ProductBean product : p){
                    rowCount++;
                }
            }
        }
        
        Set<Entry<String, ArrayList<OrderBean>>> customersSet = customers.entrySet();
        for(Entry<String, ArrayList<OrderBean>> e : customersSet){
        }
    }
    
    
    public void transCart(CartBean cart){
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            ArrayList<String> buffs = cart.getCartProdsSql();
            for(String sql : buffs){
                s.executeUpdate(sql);
            }
            
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
    }
    
}


