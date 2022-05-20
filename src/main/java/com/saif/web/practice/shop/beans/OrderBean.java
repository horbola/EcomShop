/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.beans;

import static com.saif.web.practice.shop.jooq.Tables.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import java.util.Map;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;

/**
 *
 * @author Saif
 */
public class OrderBean extends CartOrder {
    private int orderID = 0;
    
    public int getOrderID() {return orderID;}
    public void setOrderID(int orderID) {this.orderID = orderID;}
    
    
    private static final int NOT_VIEWED = 1;
    private static final int PENDING = 2;
    private static final int CANCELLED = 3;
    private static final int EXECUTING = 4;
    private static final int EXECUTED = 5;

    private int status = NOT_VIEWED;    

    public int getStatus() {return status;}
    public void setStatus(int status) {this.status = status;}

    
    
    
    public OrderBean(CustomerBean customer){
        super(customer);
    }
    
    public OrderBean(CustomerBean customer, int orderID){
        super(customer);
        this.orderID = orderID;
    }
    
    public OrderBean(CustomerBean customer, CartBean cart){
        super(customer);
        shippingAddress = cart.getShippingAddress();
        billingAddress = cart.getBillingAddress();
        deliveryMethod = cart.getDeliveryMethod();
        paymentMethod = cart.getPaymentMethod();
        products = cart.getProducts();
        date = cart.getDate();
        
    }

    
    
    public ArrayList<String> getInsertAllSql() {
        ArrayList<String> sqls = new ArrayList<>();
        Iterator<ProductBean> itr = products.values().iterator();
        for(Map.Entry<String,ProductBean> entry : products.entrySet()){
            ProductBean product = entry.getValue();
            DSLContext create = DSL.using(SQLDialect.SQLITE);
            String s = create.insertInto(ORDERS, ORDERS.CUSTOMERID, ORDERS.ORDERID, ORDERS.SHIPPINGADDRESSID, ORDERS.BILLINGADDRESSID, ORDERS.DELIVERYMETHOD, ORDERS.PAYMENTMETHOD, ORDERS.SUPPLIERID, ORDERS.PRODUCTID, ORDERS.PRICE, ORDERS.QUANTITY, ORDERS.PLACED, ORDERS.STATUS)
                             .values(customer.getCustomerID(), orderID, shippingAddress.getAddressID(), billingAddress.getAddressID(), deliveryMethod, paymentMethod, product.getSupplier().getSupplierID(), product.getProductID(), product.getPrevPrice(), Integer.valueOf(product.getQuantity()), getDate(), status)
                             .getSQL(ParamType.INLINED);
            sqls.add(s);
        }
        return sqls;
    }

    public String getSelectOneSql() {
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.selectFrom(ORDERS)
                         .where(ORDERS.CUSTOMERID.eq(customer.getCustomerID()))
                         .and(ORDERS.ORDERID.eq(orderID))
                         .getSQL(ParamType.INLINED);
        return s;
    }
    
    
    public String getSelectMaxOrderIDSql(){
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.select(DSL.max(ORDERS.ORDERID).as("maxOrderID"))
                         .from(ORDERS)
                         .getSQL(ParamType.INLINED);
        return s;
    }
    
    public String getSelectAllSql() {
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.selectFrom(ORDERS)
                           .where(ORDERS.CUSTOMERID.eq(customer.getCustomerID()))
                           .getSQL(ParamType.INLINED);
        return s;
    }
    
}
