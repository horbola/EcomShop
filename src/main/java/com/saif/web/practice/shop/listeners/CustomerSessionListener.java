/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.listeners;

import com.saif.web.practice.shop.beans.CartBean;
import com.saif.web.practice.shop.core.Shop;
import com.saif.web.practice.shop.beans.CustomerBean;
import com.saif.web.practice.shop.core.AppStore;
import com.saif.web.practice.shop.core.Operations;
import com.saif.web.practice.shop.resources.R;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web application lifecycle listener.
 *
 * @author Saif
 */
@WebListener
public class CustomerSessionListener implements HttpSessionListener, Shop {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        initCustomer(session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        destroyCustomer(session);
    }

    private void initCustomer(HttpSession session) {
        CustomerBean customer = (CustomerBean) session.getAttribute(R.CUSTOMER);
        if(customer == null) {
            customer = new CustomerBean();
            session.setAttribute(R.CUSTOMER, customer);
        }
        R.getOperations(session).setCustomer(customer);
        R.getOperations(session).getAllProducts();
    }

    private void destroyCustomer(HttpSession session) {
        CustomerBean customer = (CustomerBean) session.getAttribute(R.CUSTOMER);
        if(customer != null) {
            if(customer.isAuthenticated()){
                R.getOperations(session).storeWish();
                CartBean cart = customer.getCart();
                R.getOperations(session).storeCartOrder(cart);
            }
            else {
                // try to store informations somewhere else and track users so thant this info can be
                // retrieve latter for that user.
            }
            session.removeAttribute(R.CUSTOMER);
        }
    }
}
