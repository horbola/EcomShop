/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.beans;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import static com.saif.web.practice.shop.jooq.Tables.*;
import java.util.ArrayList;
import java.util.Map;
import org.jooq.conf.ParamType;

/**
 *
 * @author Saif
 */
public class WishBean extends WishCartOrder{
    
    public WishBean(CustomerBean customer){
        super(customer);
    }
    
    
    
    
    
    public String getSelectSql(){
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.selectFrom(WISHES)
                         .where(WISHES.CUSTOMERID.eq(customer.getCustomerID()))
                         .getSQL(ParamType.INLINED);
        return s;
    }

    public ArrayList<String> getInsertAllSql() {
        ArrayList<String> sqls = new ArrayList<>();
        for(Map.Entry entry : products.entrySet()){
            ProductBean product = (ProductBean) entry.getValue();
            DSLContext create = DSL.using(SQLDialect.SQLITE);
            String sql = create.insertInto(WISHES, WISHES.CUSTOMERID, WISHES.SUPPLIERID, WISHES.PRODUCTID, WISHES.PREVPRICE, WISHES.QUANTITY, WISHES.PLACED)
                         .values(customer.getCustomerID(), product.getSupplier().getSupplierID(), product.getProductID(), product.getPrevPrice(), product.getQuantity(), product.getPlaced())
                         .getSQL(ParamType.INLINED);
             sqls.add(sql);
        }
        return sqls;
    }
    
    public String getDeleteSql(){
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String sql = create.deleteFrom(WISHES)
                           .where(WISHES.CUSTOMERID.eq(customer.getCustomerID()))
                           .getSQL(ParamType.INLINED);
        return sql;
    }
    
}



