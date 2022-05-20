/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.listener;

import com.saif.web.practice.shop.core.Operations;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        application = e.getServletContext();
        destroyOperations();
    }
    
    
    
    private void initOperations(){
        Operations operations = new Operations(application.getInitParameter("jdbcPath"));
        application.setAttribute("operations", operations);
    }
    
    private void destroyOperations(){
        application.removeAttribute("operations");
    }
}
