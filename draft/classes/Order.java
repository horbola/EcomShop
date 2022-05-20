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
public class Order extends CartOrder {
    protected int orderID = 0;

    public int getOrderID() {return orderID;}
    public void setOrderID(int orderID) {this.orderID = orderID;}
    
    public Order(CustomerBean customer, int orderID){
        super(customer);
        this.orderID = orderID;
    }
    
}
