/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.resources;

import com.saif.web.practice.shop.beans.CustomerBean;
import com.saif.web.practice.shop.core.AppStore;
import com.saif.web.practice.shop.core.Operations;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Saif
 */
public class R {
    public static final String CUSTOMER = "customerX";
    public static final String OPERATIONS = "operations";
    public static final String APPSTORE = "appStore";
    
    
    public static final Operations getOperations(ServletContext application){
        Operations operations = (Operations) application.getAttribute(OPERATIONS);
        return operations;
    }
    public static final Operations getOperations(HttpSession session){
        Operations operations = (Operations) session.getServletContext().getAttribute(OPERATIONS);
        return operations;
    }
    public static final Operations getOperations(HttpServletRequest request){
        Operations operations = (Operations) request.getServletContext().getAttribute(OPERATIONS);
        return operations;
    }
    
    public static final CustomerBean getCustomer(HttpServletRequest request){
        CustomerBean customer = (CustomerBean) request.getSession().getAttribute(CUSTOMER);
        return customer;
    }
    
    
    public static void setCustomer(HttpServletRequest request, CustomerBean customer){
        request.getSession().setAttribute(CUSTOMER, customer);
    }
    
    public static String getLoginPage(HttpServletRequest request){
        String loginPage = request.getServletContext().getInitParameter("loginPage");
        return loginPage;
    }

    public static AppStore getAppStore(ServletContext servletContext) {
        AppStore appStore = (AppStore) servletContext.getAttribute(APPSTORE);
        return appStore;
    }
}
