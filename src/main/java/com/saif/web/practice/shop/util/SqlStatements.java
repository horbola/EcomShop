/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.util;

import com.saif.web.practice.shop.bean.Customer;
import com.saif.web.practice.shop.bean.Operations;
import com.saif.web.practice.shop.bean.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Saif
 */
public class SqlStatements {
    private final Logger LOGGER = LogManager.getLogger(SqlStatements.class);
    
    private String userName;
    private int orderId;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId+1;
    }
    
    
    public String orderFeedback(){
        StringBuffer buff = new StringBuffer("select orderId, productId, mfrId, quantity, description, price from Orders, Products ");
        buff.append("where Orders.uName = ");
        buff.append("\"" +userName +"\"");
        buff.append("and orderId = ");
        buff.append(orderId);
        buff.append("and Products.productId = Orders.productId ");
        buff.append("and Products.mfrId = Orders.mfrId;");
        return buff.toString();
    }

    public String updateProfile(String uName, Customer customerUpdate) {
        StringBuffer buff = new StringBuffer();
        buff.append("UPDATE Customers SET uName = ");
        buff.append("\"" +customerUpdate.getUserName() +"\"");
        buff.append(",");
        buff.append("pass = ");
        buff.append("\"" +customerUpdate.getPass() +"\"");
        buff.append(",");
        buff.append("name = ");
        buff.append("\"" +customerUpdate.getName() +"\"");
        buff.append("WHERE uName = ");
        buff.append("\"" +uName +"\"");
        buff.append(";");
        LOGGER.debug("updateProfile()"+ buff.toString());
        return buff.toString();
    }

    public String updateProfileFeedback(String uName) {
        StringBuffer buff = new StringBuffer();
        buff.append("SELECT * FROM Customers where uName = ");
        buff.append("\"" +uName +"\"");
        buff.append(";");
        return buff.toString();
    }
    
    public String productsEntry(Product product){
        StringBuffer buff = new StringBuffer();
        buff.append("INSERT INTO Products(productId, mfrId, description, price, quantity) VALUES (");
        buff.append("'"+product.getProductId()+"',");
        buff.append("'"+product.getMfrId()+"',");
        buff.append("'"+product.getDescription()+"',");
        buff.append(product.getPrice());
        buff.append(",");
        buff.append(product.getQuantity());
        buff.append(");");
        return buff.toString();
    }

    public String productEntryFeedback(Product product) {
        StringBuffer buff = new StringBuffer();
        buff.append("SELECT * FROM Products WHERE Products.productId = ");
        buff.append("'"+product.getProductId()+"'");
        buff.append("and Products.mfrId = ");
        buff.append("'"+product.getMfrId()+"';");
        return buff.toString();
    }
}
