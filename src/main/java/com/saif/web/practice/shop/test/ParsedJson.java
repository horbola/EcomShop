/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.test;

import java.util.Iterator;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Saif
 */
public class ParsedJson {
    static String json = "[{\"productId\":\"1\",\"mfrId\":\"aci\",\"description\":\"Mosquito Coil\",\"price\":\"100.0\",\"quantity\":\"500\"},{\"productId\":\"2\",\"mfrId\":\"aci\",\"description\":\"Mosquito Spray\",\"price\":\"100.0\",\"quantity\":\"500\"}]";

    public static void main(String ...s) throws Exception {
        parseJson(json);
    }

    private static String parseJson(String cartJson) throws Exception{ 
        Object obj = new JSONParser().parse(cartJson); 
        // typecasting obj to JSONArray 
        JSONArray ja = (JSONArray) obj; 
          
        // iterating phoneNumbers 
        Iterator itr = ja.iterator(); 
        Iterator rowItr = null;
        String parsedJson = "";
          
        while (itr.hasNext())  
        { 
            rowItr = ((Map) itr.next()).entrySet().iterator(); 
            while (rowItr.hasNext()) { 
                Map.Entry pair = (Map.Entry) rowItr.next(); 
                parsedJson += pair.getKey() + " : " + pair.getValue();
            }
        }
        
        return parsedJson;
    } 

}