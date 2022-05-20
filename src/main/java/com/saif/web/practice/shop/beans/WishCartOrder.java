/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.beans;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Saif
 */
public class WishCartOrder extends Bean {
    protected CustomerBean customer;
    protected HashMap<String, ProductBean> products;
    protected Date date;
    
    public CustomerBean getCustomer() {return customer;}
    public void setCustomer(CustomerBean customer) {this.customer = customer;}
    
    public HashMap<String, ProductBean> getProducts() {return products;}
    public void setProducts(HashMap<String, ProductBean> products) {this.products = products;}
    
    public Date getDate() {
        if(date == null) date = new Date(System.currentTimeMillis());
        return date;
    }
    public void setDate(Date date) {this.date = date;}
    public void setDate(String date) {this.date = Date.valueOf(date);}
    
    
    
    public WishCartOrder(){}
    protected WishCartOrder(CustomerBean customer){
        this.customer = customer;
    }
    
    public int getCount() {
        if(products == null){products = new HashMap<>();}
        return products.size();
    }
    
    public ProductBean getOneProduct(HttpServletRequest request){
        String supplierID = request.getParameter("supplierID");
        String productID = request.getParameter("productID");
        String productKey = supplierID+productID;
        return products.get(productKey);
    }
    
    public boolean addToProducts(Object obj){
        if(products == null){products = new HashMap<>();}
        ProductBean product = productRRMedia(obj);
        String sID = product.getSupplier().getSupplierID();
        SupplierBean supplier = null;
        if(this instanceof OrderBean) supplier = customer.containsSupplier(customer.getAllOrders(), sID);
        else supplier = containsSupplier(sID);
        if(supplier != null) product.setSupplier(supplier);
        
        if(products.containsKey(product.getKey())) return false;
        else{
            products.put(product.getKey(), product);
            return true;
        }
    }
    
    public boolean sync(ResultSet rs) throws SQLException {
        int step = 1;
        while(rs.next()){
            if(step == 1){
                if(this instanceof WishBean) syncDate(rs);
                else syncOtherFields(rs);
            }
            addToProducts(rs);
            step++;
        }
        return true;
    }
    
    
    public void syncDate(ResultSet rs) throws SQLException {
        try{
            String dateOfWish = rs.getString("placed");
            if(dateOfWish != null){
                if(!(dateOfWish.equalsIgnoreCase("null") || dateOfWish.equals("")))
                    setDate(dateOfWish);
            }
        }catch(SQLException e){}
    }
    
    public void syncOtherFields(ResultSet rs) throws SQLException {
        // throw exceptions from this place. override it in subclass.
    }
    
    protected SupplierBean containsSupplier(String supplierID){
        if(products == null) return null;
        SupplierBean supplier = null;
        Iterator<ProductBean> itr = products.values().iterator();
        while(itr.hasNext()){
            ProductBean product = itr.next();
            String suppID = product.getSupplier().getSupplierID();
            if(suppID.equals(supplierID)){
                supplier = product.getSupplier();
                return supplier;
            }
        }
        return null;
    }
    
}
