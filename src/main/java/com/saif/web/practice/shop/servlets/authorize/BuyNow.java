/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.servlets.authorize;

import com.saif.web.practice.shop.core.Operations;
import com.saif.web.practice.shop.core.Shop;
import com.saif.web.practice.shop.resources.R;
import com.saif.web.practice.shop.servlets.authorize.Order;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Saif
 */
@WebServlet(name = "BuyNow", urlPatterns = {"/authorize/BuyNow"})
public class BuyNow extends HttpServlet implements Shop {
    
    private void proccessRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        l().debug("processRequest(), starts: ");
        
        Operations operations = R.getOperations(request);
        if(!operations.isCustomerAuthenticated()) return;
        
        operations.placeBuyNowOrder();
        request.getRequestDispatcher("/prevented/order/order-page.jsp").forward(request, response);
        
        l().debug("processRequest(), ends: ");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        proccessRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        proccessRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
