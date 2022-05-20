/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.util;

import com.saif.web.practice.shop.core.Shop;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Saif
 */
public class ShopUtil implements Shop {
    
    public static void addCookie(HttpServletResponse response){
        Cookie userName = new Cookie("userName", "Saifkhan");
        response.addCookie(userName);
    }
    
    public static String buildCartJson(String key, String value){
        JSONObject cartJson = new JSONObject();
        cartJson.put(key, value);
        return cartJson.toJSONString();
    } 
    
    public static String buildWishJson(String key, String value){
        JSONObject wishJson = new JSONObject();
        wishJson.put(key, value);
        return wishJson.toJSONString();
    } 
}
