/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.bean;

import com.saif.web.practice.shop.test.ParsedJson;
import com.saif.web.practice.shop.util.ParseJson;
import com.saif.web.practice.shop.util.SqlStatements;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
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
    
    
    
    /**
     * 
     * public Customer authenticate(String uName, String pass) {
        StringBuffer selectBuff = new StringBuffer("SELECT * FROM Customers WHERE uName = \"" +uName +"\";");
        SortedMap result[] = null;
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            Sql sql = new Sql(selectBuff.toString(), s);
            
            result = sql.executeQuery();
            
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
        
        Customer customer = null;
        if(result != null && result.length != 0 && uName.equals(result[0].get("uName"))){
            customer = new Customer();
            customer.setUserName((String) result[0].get("uName"));
            customer.setPass((String) result[0].get("pass"));
            customer.setName((String) result[0].get("name"));
        }
        return customer;
    }
     */
    
    

    public SortedMap[] showAllProducts() {
        LOGGER.debug("showAllProducts()");
        SortedMap[] products = null;
        StringBuffer buff = new StringBuffer("SELECT * FROM Products;");
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            Sql sql = new Sql(buff.toString(), s);
            products = sql.executeQuery();
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
            order.setOrderId((int) map.get("max(orderId)"));
            LOGGER.debug("orderId is: " +order.getOrderId());
            
            // Inserts a single order into Orders table.
            Sql sql = new Sql();
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

    /**
     * public Customer makeCustomer(String uName, String pass, String name) {
        StringBuffer buff = new StringBuffer("INSERT INTO Customers (uName, pass, name) VALUES (");
        buff.append("\"" +uName +"\"");
        buff.append(",");
        buff.append("\"" +pass +"\"");
        buff.append(",");
        buff.append("\"" +name +"\"");
        buff.append(");");
        
        StringBuffer selectBuff = new StringBuffer("SELECT * FROM Customers WHERE uName = \"" +uName +"\";");
        SortedMap customerInfo = null;
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            Sql sql = new Sql(buff.toString(), s);
            sql.executeInsert();
            
            sql.setSqlStatement(selectBuff.toString());
            customerInfo = sql.executeQuery()[0];
            
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
        
        Customer customer = new Customer();
        customer.setUserName((String) customerInfo.get("uName"));
        customer.setPass((String) customerInfo.get("pass"));
        customer.setName((String) customerInfo.get("name"));
        
        return customer;
    }
     */
    
    

    public Customer updateProfile(String uName, Customer customerUpdate) {
        SqlStatements sqlS = new SqlStatements();
        SortedMap updateProfileFeedback = null;
        
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            Sql sql = new Sql(sqlS.updateProfile(uName, customerUpdate), s);
            sql.executeUpdate();
            
            sql.setSqlStatement(sqlS.updateProfileFeedback(customerUpdate.getUserName()));
            updateProfileFeedback = sql.executeQuery()[0];
            
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
        SqlStatements sqlS = new SqlStatements();
        SortedMap productFeedbackMap = null;
        
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            Sql sql = new Sql(sqlS.productsEntry(product), s);
            sql.executeInsert();
            
            sql.setSqlStatement(sqlS.productEntryFeedback(product));
            productFeedbackMap = sql.executeQuery()[0];
            
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
    
    
    
    /*
    public SortedMap[] makeOrder(String uName, String cartJson) {
        LOGGER.debug("makeOrder()");
        int orderId = 0;
        SqlStatements sqlS = new SqlStatements();
        
        StringBuffer highestOrderIdBuff = new StringBuffer("select max(orderId) from Orders;");
        ParseJson parseJson = new ParseJson();
        ArrayList<StringBuffer> insertOrder;
        StringBuffer feedbackOrder = new StringBuffer("select orderId, productId, mfrId, quantity from OrdersTemp where uName = ");
        feedbackOrder.append("\"" +uName +"\"");
        feedbackOrder.append("and orderId = ");
        
        SortedMap[] orderFeedback = null;
        
        Statement s = null;
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            Sql sql = new Sql();
            sql.setStatement(s); 
            
            // gets the highest orderId from Orders table so that the next number
            // can be generated for the new order.
            sql.setSqlStatement(highestOrderIdBuff.toString());
            SortedMap map = sql.executeQuery()[0];
            orderId = (int) map.get("max(orderId)");
            parseJson.setOrderId(orderId+1);
            feedbackOrder.append(orderId+1);
            feedbackOrder.append(";");
            sqlS.setOrderId(orderId);
            
            // Inserts a single order into Orders table.
            insertOrder = parseJson.parseCart(uName, cartJson);
            for(StringBuffer b : insertOrder){
                sql.setSqlStatement(b.toString());
                sql.executeInsert();
            }
            
            // gets the inserted oreder to give the feedback to the customer.
            sql.setSqlStatement(feedbackOrder.toString());
            orderFeedback = sql.executeQuery();
            
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
    */
    
}
