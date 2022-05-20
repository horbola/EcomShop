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
    private static final Logger logger = LogManager.getLogger(ParseJson.class);
    
    static String json = "[{\"productId\":\"1\",\"mfrId\":\"aci\",\"description\":\"Mosquito Coil\",\"price\":\"100.0\",\"quantity\":\"500\"},{\"productId\":\"2\",\"mfrId\":\"aci\",\"description\":\"Mosquito Spray\",\"price\":\"100.0\",\"quantity\":\"500\"}]";

    public static void main(String ...s){
        ArrayList<StringBuffer> parsedJson = parseJson(json);
        for(StringBuffer buff : parsedJson){
            System.out.println(buff.toString());
        }
    }
    
    public static ArrayList<StringBuffer> parseJson(String cartJson){ 
        JSONArray ja = null;
        try {
            Object obj = new JSONParser().parse(cartJson);
            ja = (JSONArray) obj; 
        }
        catch(ParseException ex) {}
        
        Iterator itr = ja.iterator(); 
        Iterator rowItr = null;
        StringBuffer colBuff = new StringBuffer();
        colBuff.append("INSERT INTO Orders (productId, mfrId, description, price, quantity) values (");
        StringBuffer values;
        ArrayList<StringBuffer> buffs = new ArrayList<>();
        
        while (itr.hasNext())  
        { 
            Map row = (Map) itr.next();
            values = new StringBuffer(colBuff);
            
            String productId = (String) row.get("productId");
            values.append(productId);
            values.append(", ");
            
            String mfrId = (String) row.get("mfrId");
            values.append(mfrId);
            values.append(", ");
            
            String description = (String) row.get("description");
            values.append(description);
            values.append(", ");
            
            String price = (String) row.get("price");
            values.append(price);
            values.append(", ");
            
            String quantity = (String) row.get("quantity");
            values.append(quantity);
            values.append(");");
            
            buffs.add(values);
        }
        return buffs;
    }
    
    
    
    /**
     * public static String parseJson(String cartJson){ 
        Object obj = null; 
        try {obj = new JSONParser().parse(cartJson);
        }catch(ParseException ex) {}
        JSONArray ja = (JSONArray) obj; 
          
        Iterator itr = ja.iterator(); 
        Iterator rowItr = null;
        String parsedJson = "";
          
        while (itr.hasNext())  
        { 
            rowItr = ((Map) itr.next()).entrySet().iterator(); 
            while (rowItr.hasNext()) { 
                Map.Entry pair = (Map.Entry) rowItr.next(); 
                parsedJson += pair.getKey() + " : " + pair.getValue() +"\n";
            }
        }
        
        return parsedJson;
    }
     */
}
