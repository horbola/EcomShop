/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.servlets;

import com.saif.web.practice.shop.core.Operations;
import com.saif.web.practice.shop.core.Shop;
import com.saif.web.practice.shop.resources.R;
import com.saif.web.practice.shop.util.ShopUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Saif
 */
@WebServlet(name = "AddToCart", urlPatterns = {"/AddToCart"})
public class AddToCart extends HttpServlet implements Shop {
    int cartCount = 0;
    
    private void proccessRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        l().debug("doPost(), starts: ");
        
        Operations operations = R.getOperations(request);
        operations.addToCart();
        cartCount = operations.getCartCount();
        
        request.getSession().setAttribute("cartCount", ""+cartCount);
        
        Boolean forward = Boolean.valueOf(request.getParameter("forward"));
        if(forward){
            request.getRequestDispatcher("/home/home-page.jsp").forward(request, response);
        }
        else{
            response.setContentType("text/plain;charset=UTF-8");
            try ( PrintWriter out = response.getWriter()) {
                out.println(ShopUtil.buildCartJson("cartCount", ""+cartCount));
            }
        }
        
        l().debug("doPost(), ends: ");
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
    }// </editor-fold>

}
