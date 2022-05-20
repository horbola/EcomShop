/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.listeners;

import com.saif.web.practice.shop.core.AppStore;
import com.saif.web.practice.shop.core.DSFactory;
import com.saif.web.practice.shop.core.Operations;
import com.saif.web.practice.shop.resources.R;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author Saif
 */

public class AppStart implements ServletContextListener {
    ServletContext application;

    @Override
    public void contextInitialized(ServletContextEvent e) {
        application = e.getServletContext();
        initOperations();
        initAppStore();
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        application = e.getServletContext();
        destroyAppStore();
        destroyOperations();
    }
    
    
    
    private void initOperations(){
        Operations operations = new Operations(new DSFactory());
        application.setAttribute("operations", operations);
        
    }
    
    private void destroyOperations(){
        application.removeAttribute("operations");
    }
    
    
    
    private void initAppStore(){
        AppStore appStore = new AppStore();
        application.setAttribute(R.APPSTORE, appStore);
        R.getOperations(application).setAppStore(appStore);
    }
    
    private void destroyAppStore(){
        application.removeAttribute(R.APPSTORE);
    }
}
