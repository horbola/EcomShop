/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.beans;


/**
 *
 * @author Saif
 */
public class PaymentBean extends Bean {
    private int paymentID = 0;
    private OrderBean order;
    private int paymentMethod = 0;
    private float amount = 0;
    private String transactionID = "";

    

    public PaymentBean(OrderBean order, int paymentID){
        this.order = order;
        this.paymentID = paymentID;
    }
}
