/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import org.apache.logging.log4j.Logger;

/**
 *
 * @author Saif
 */
public class ParseJson {
    private final Logger LOGGER = LogManager.getLogger(ParseJson.class);
    private int orderId;

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    
    public ArrayList<StringBuffer> parseCart(String uName, String cartJson){ 
        LOGGER.debug("parse()");
        JSONArray ja = null;
        try {
            Object obj = new JSONParser().parse(cartJson);
            ja = (JSONArray) obj; 
        }
        catch(ParseException ex) {}
        
        Iterator itr = ja.iterator();
        Iterator rowItr = null;
        StringBuffer colBuff = new StringBuffer("INSERT INTO OrdersTemp (uName, orderId, productId, mfrId, quantity) VALUES (");
        
        StringBuffer values;
        ArrayList<StringBuffer> buffs = new ArrayList<>();
        
        while (itr.hasNext())  
        { 
            Map row = (Map) itr.next();
            values = new StringBuffer(colBuff);
            
            values.append("\"" +uName +"\"");
            values.append(",");
            
            values.append(orderId);
            values.append(",");
            
            String productId = (String) row.get("productId");
            values.append(productId);
            values.append(",");
            
            String mfrId = (String) row.get("mfrId");
            values.append("\"" +mfrId +"\"");
            values.append(",");
            
            String quantity = (String) row.get("quantity");
            values.append(quantity);
            values.append(");");
            
            buffs.add(values);
        }
        return buffs;
    }
    
}
