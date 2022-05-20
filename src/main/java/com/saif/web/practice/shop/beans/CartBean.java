/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.beans;

import static com.saif.web.practice.shop.jooq.Tables.*;
import java.util.ArrayList;
import java.util.Map;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;

/**
 *
 * @author Saif
 */
public class CartBean extends CartOrder{
    
    
    public CartBean(CustomerBean customer){
        super(customer);
    }
    
    
    
    
    
    
    
    
    
    
    public String getSelectAllSql(){
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.selectFrom(CARTS)
                         .where(CARTS.CUSTOMERID.eq(customer.getCustomerID()))
                         .getSQL(ParamType.INLINED);
        return s;
    }

    public ArrayList<String> getInsertAllSql() {
        ArrayList<String> sqls = new ArrayList<>();
        for(Map.Entry entry : products.entrySet()){
            ProductBean product = (ProductBean) entry.getValue();
            DSLContext create = DSL.using(SQLDialect.SQLITE);
            String sql = create.insertInto(CARTS, CARTS.CUSTOMERID, CARTS.SHIPPINGADDRESSID, CARTS.BILLINGADDRESSID, CARTS.DELIVERYMETHOD, CARTS.PAYMENTMETHOD, CARTS.SUPPLIERID, CARTS.PRODUCTID, CARTS.PREVPRICE, CARTS.QUANTITY, CARTS.PLACED)
                         .values(customer.getCustomerID(), shippingAddress.getAddressID(), billingAddress.getAddressID(), deliveryMethod, paymentMethod, product.getSupplier().getSupplierID(), product.getProductID(), product.getPrevPrice(), Integer.valueOf(product.getQuantity()), getDate())
                         .getSQL(ParamType.INLINED);
             sqls.add(sql);
        }
        return sqls;
    }

    public String getDeleteSql(){
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String sql = create.deleteFrom(CARTS)
                           .where(CARTS.CUSTOMERID.eq(customer.getCustomerID()))
                           .getSQL(ParamType.INLINED);
        return sql;
    }
    
}
