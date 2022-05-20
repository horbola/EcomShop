/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.test;

import com.saif.web.practice.shop.bean.Customer;
import com.saif.web.practice.shop.bean.Operations;
import com.saif.web.practice.shop.bean.Order;
import com.saif.web.practice.shop.bean.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Saif
 */
public class ReviewOrderTest{
    
    private final Logger LOGGER = LogManager.getLogger(ReviewOrderTest.class);
    private String jdbcPath;
    
    public ReviewOrderTest(String jdbcPath){
        loadJdbc();
        this.jdbcPath = jdbcPath;
        reviewOrder();
    }
    
    public void loadJdbc(){
        try{Class.forName("org.sqlite.JDBC");}
        catch(ClassNotFoundException ex){ex.printStackTrace();}
    }

    public String getJdbcPath() {
        return jdbcPath;
    }
    
    
    public void reviewOrder() {
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
    }
    
    public static void main(String ...s){
        new ReviewOrderTest("jdbc:sqlite://D:\\Faysal2\\System\\Computer\\Program\\Database\\Sofware\\Databases\\Sqlite\\Shop\\ShopDB.db");
    }
    
}
