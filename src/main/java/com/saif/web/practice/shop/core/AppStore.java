/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.core;

import com.saif.web.practice.shop.beans.ProductBean;
import com.saif.web.practice.shop.beans.WishCartOrder;
import static com.saif.web.practice.shop.jooq.Tables.PRODUCTS;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Saif
 */
public class AppStore extends WishCartOrder {
    private String laptops = "";
    private String mobiles = "";
    private String watches = "";

    public String getLaptops() {
        return laptops;
    }

    public String getMobiles() {
        return mobiles;
    }

    public String getWatches() {
        return watches;
    }
    
    
    
    

    /**
     * this no arg constructor is provided for the purpose of getting map value in jstl.
     * i don't get the beans in jstl foreach loop if this constructor is not present.
     */
    public AppStore(){}

    
    public void fillAllProducts(ResultSet rs) throws SQLException{
        while(rs.next()){
            addToProducts(rs);
        }
        l().debug("total number of proeducts : "+products.size()); 
    }
    
    
    
    
    public HashMap<String,ProductBean> getProductsAsCategory(String category){
        HashMap<String,ProductBean> laptops = new HashMap<>();
            for(Map.Entry<String,ProductBean> entry : products.entrySet()){
                ProductBean product = entry.getValue();
                if(product.getDomain().equalsIgnoreCase(category))
                    laptops.put(product.getKey(), product);
            }
            return laptops;
    }
    
    public String getJson(String category){
        JSONArray jArray = new JSONArray();
        for(Map.Entry<String,ProductBean> entry : products.entrySet()){
            ProductBean product = entry.getValue();
            if(product.getDomain().equalsIgnoreCase(category))
                jArray.add(product.getJson());
        }
        String codedJson = "";
        try {codedJson = URLEncoder.encode(jArray.toJSONString(), "UTF-8");}
         catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AppStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codedJson;
    }


    
    public String getSelectAllSql() {
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.selectFrom(PRODUCTS)
                         .getSQL();
        return s;
    }
    
}
