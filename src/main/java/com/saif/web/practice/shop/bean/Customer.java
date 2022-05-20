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
public class Customer {
    String userName = null;
    String pass = null;
    String name = null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getSelectSql(){
        String customerSelect = "SELECT * FROM Customers WHERE uName = \"" +userName +"\";";
        return customerSelect;
    }
    
    public String getInsertSql(){
        StringBuffer buff = new StringBuffer("INSERT INTO Customers (uName, pass, name) VALUES (");
        buff.append("\"" +userName +"\"");
        buff.append(",");
        buff.append("\"" +pass +"\"");
        buff.append(",");
        buff.append("\"" +name +"\"");
        buff.append(");");
        return buff.toString();
    }
    
    public String getUpdateSql(Customer old){
        StringBuffer buff = new StringBuffer();
        buff.append("UPDATE Customers SET uName = ");
        buff.append("\"" +userName +"\"");
        buff.append(",");
        buff.append("pass = ");
        buff.append("\"" +pass +"\"");
        buff.append(",");
        buff.append("name = ");
        buff.append("\"" +name +"\"");
        buff.append("WHERE uName = ");
        buff.append("\"" +old.userName +"\"");
        buff.append(";");
        return buff.toString();
    }
    
    public String getUpdateFeedbackSql() {
        StringBuffer buff = new StringBuffer();
        buff.append("SELECT * FROM Customers where uName = ");
        buff.append("\"" +userName +"\"");
        buff.append(";");
        return buff.toString();
    }
    
}
