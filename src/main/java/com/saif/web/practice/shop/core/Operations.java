/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.core;

import com.saif.web.practice.shop.bean.Customer;
import com.saif.web.practice.shop.bean.Order;
import com.saif.web.practice.shop.bean.Product;
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
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Saif
 */
public class Operations {
    private final Logger LOGGER = LogManager.getLogger(Operations.class);
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

    
    
    public Customer authenticate(Customer loginCust) {
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
        
        Customer customer = null;
        if(result != null && result.length != 0 && loginCust.getUserName().equals(result[0].get("uName"))){
            customer = new Customer();
            customer.setUserName((String) result[0].get("uName"));
            customer.setPass((String) result[0].get("pass"));
            customer.setName((String) result[0].get("name"));
        }
        return customer;
    }
    
    

    public SortedMap[] showAllProducts() {
        LOGGER.debug("showAllProducts()");
        SortedMap[] products = null;
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            ResultSet rs = s.executeQuery(new Product().getSelectAllSql());
            products = ResultSupport.toResult(rs).getRows();
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
        
        return products;
    }

    public SortedMap[] makeOrder(Order order) {
        LOGGER.debug("makeOrder()");
        
        SortedMap[] orderFeedback = null;
        
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            // gets the highest orderId from Orders table so that the next number
            // can be generated for the new order.
            ResultSet rs = s.executeQuery(order.getHighestOrderIdSql());
            SortedMap map = ResultSupport.toResult(rs).getRows()[0];
            LOGGER.debug("orderId from sql is: " +map.get("max(orderId)"));
            order.setOrderId((int) map.get("max(orderId)"));
            LOGGER.debug("orderId from order bean is: " +order.getOrderId());
            
            // Inserts a single order into Orders table.
            for(StringBuffer b : order.getInsertSql()){
                s.executeUpdate(b.toString());
                LOGGER.debug("insertSql is: " +b.toString());
            }
            
            // gets the inserted oreder to give the feedback to the customer.
            LOGGER.debug("feedbackOrderSql is: " +order.getFeedbackOrderSql());
            rs = s.executeQuery(order.getFeedbackOrderSql());
            orderFeedback = ResultSupport.toResult(rs).getRows();
            
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
        
        for(SortedMap m : orderFeedback){
            LOGGER.debug("makeOrder(): orderId: "+m.get("orderId"));
            LOGGER.debug("makeOrder(): productId: "+m.get("productId"));
            LOGGER.debug("makeOrder(): mfrId: "+m.get("mfrId"));
            LOGGER.debug("makeOrder(): quantity: "+m.get("quantity"));
        }
        return orderFeedback;
    }

    public Customer makeCustomer(Customer signUpCust){
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
        
        Customer customer = new Customer();
        customer.setUserName((String) custFeedback.get("uName"));
        customer.setPass((String) custFeedback.get("pass"));
        customer.setName((String) custFeedback.get("name"));
        
        return customer;
    }

    

    public Customer updateProfile(Customer old, Customer custNew) {
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

    private Customer updateCustomer(SortedMap updateProfileFeedback) {
        Customer customer = new Customer();
        customer.setUserName((String) updateProfileFeedback.get("uName"));
        customer.setPass((String) updateProfileFeedback.get("pass"));
        customer.setName((String) updateProfileFeedback.get("name"));
        return customer;
    }

    public Product productsEntry(Product product) {
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
        
        Product productFeedback = new Product();
        productFeedback.setProductId(productFeedbackMap.get("productId").toString());
        productFeedback.setMfrId(productFeedbackMap.get("mfrId").toString());
        productFeedback.setDescription(productFeedbackMap.get("description").toString());
        productFeedback.setPrice(productFeedbackMap.get("price").toString());
        productFeedback.setQuantity(productFeedbackMap.get("quantity").toString());
        
        return productFeedback;
    }
    
    
    
    public HashMap<String, ArrayList<Order>> reviewOrder() {
        LOGGER.debug("reviewOrder()");
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
         HashMap<String, ArrayList<Order>> customers = parse(orders);
         logCustomers(customers);
         return customers;
    }
    
    
    private HashMap<String, ArrayList<Order>> parse(SortedMap[] rows){
        HashMap<String, ArrayList<Order>> customers = new HashMap<>();
        
        int rowCount = 0;
        ArrayList<Order> orders;
        Order order;
        Product product;
        
        for(Map m : rows){
            LOGGER.debug(m.get("uName"));
            String uName = (String) m.get("uName");
            
            if(!customers.containsKey(uName)){
                orders = new ArrayList<>();
                
                order = new Order();
                order.setOrderIdReview((int) m.get("orderId"));
                order.setStatus((int) m.get("status")); 
                
                rowCount++;
                product = new Product();
                product.setMfrId((String) m.get("mfrId"));
                product.setProductId(m.get("productId").toString());
                product.setOrderQuan(m.get("quantity").toString());
                
                order.setProduct(product);
                orders.add(order);
                customers.put(uName, orders);
            }else {
                orders = customers.get(uName);
                int oid = (int) m.get("orderId");
                
                if(containsOrder(orders, oid))
                    order = getOrder(orders, oid);
                else {
                    order = new Order();
                    order.setOrderIdReview(oid);
                    order.setStatus((int) m.get("status")); 
                    orders.add(order);
                }
                
                rowCount++;
                product = new Product();
                product.setMfrId((String) m.get("mfrId"));
                product.setProductId(m.get("productId").toString());
                product.setOrderQuan(m.get("quantity").toString());
                order.setProduct(product);
            }
        }
        LOGGER.debug("rowCount from parse(): " +rowCount);
        return customers;
    }
    
    private boolean containsOrder(ArrayList<Order> orders, int oid){
        for(Order o : orders){
            if(o.getOrderId() == oid)
                return true;
        }
        return false;
    }
    
    private Order getOrder(ArrayList<Order> orders, int oid){
        for(Order o : orders){
            if(o.getOrderId() == oid)
                return o;
        }
        return null;
    }
    
    private void logCustomers(HashMap<String, ArrayList<Order>> customers){
        int rowCount = 0;
        for(String uName : customers.keySet()){
            ArrayList<Order> o = customers.get(uName);
            for(Order order : o){
                ArrayList<Product> p = order.getProducts();
                for(Product product : p){
                    rowCount++;
                    LOGGER.debug("uName: "+uName+" orderId: "+order.getOrderId()+" productId: "+product.getProductId()+" mfrId: "+product.getMfrId()+" quantity: "+product.getOrderQuan()+" status: "+order.getStatus());
                }
            }
        }
        LOGGER.debug("rowCount from logCustomers(): " +rowCount);
        
        Set<Entry<String, ArrayList<Order>>> customersSet = customers.entrySet();
        for(Entry<String, ArrayList<Order>> e : customersSet){
            LOGGER.debug("Entry: " +e.getKey());
        }
    }
    
}
