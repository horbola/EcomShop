/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.bean;

/**
 *
 * @author Saif
 */
public class Product {
    private String productId;
    private String mfrId;
    private String description;
    private String price;
    private String quantity;
    /**
     * This field is included for calculation of orders by admin.
     * This field gets it's value from OrdersTemp table.
     */
    private String orderQuan;
    
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMfrId() {
        return mfrId;
    }

    public void setMfrId(String mfrId) {
        this.mfrId = mfrId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrderQuan() {
        return orderQuan;
    }

    public void setOrderQuan(String orderQuan) {
        this.orderQuan = orderQuan;
    }
    
    /**
     * This method is called when admin needs to make entry new product in the product table.
     * @returns the prepared sql statement to make new entry.
     */
    public String getInsertSql(){
        StringBuffer buff = new StringBuffer();
        buff.append("INSERT INTO Products(productId, mfrId, description, price, quantity) VALUES (");
        buff.append("'"+productId+"',");
        buff.append("'"+mfrId+"',");
        buff.append("'"+description+"',");
        buff.append(price);
        buff.append(",");
        buff.append(quantity);
        buff.append(");");
        return buff.toString();
    }

    public String getSelectSql() {
        StringBuffer buff = new StringBuffer();
        buff.append("SELECT * FROM Products WHERE Products.productId = ");
        buff.append("'"+productId+"'");
        buff.append("and Products.mfrId = ");
        buff.append("'"+mfrId+"';");
        return buff.toString();
    }
    
    public String getSelectAllSql(){
        String s = "SELECT * FROM Products;";
        return s;
    }
}
