/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.listeners;

import com.saif.web.practice.shop.core.Shop;
import com.saif.web.practice.shop.core.Operations;
import com.saif.web.practice.shop.resources.R;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebListener;
    

/**
 * Web application lifecycle listener.
 *
 * @author Saif
 */
@WebListener
public class CustomerRequestListener implements ServletRequestListener, Shop {

    @Override
    public void requestInitialized(ServletRequestEvent re) {
        HttpServletRequest request = (HttpServletRequest) re.getServletRequest();
        Operations operations = R.getOperations(request);
        operations.setRequest(request);
    }
    
    @Override
    public void requestDestroyed(ServletRequestEvent re) {
        HttpServletRequest request = (HttpServletRequest) re.getServletRequest();
        Operations operations = R.getOperations(request);
        operations.destoryRequest(request);
    }
}
