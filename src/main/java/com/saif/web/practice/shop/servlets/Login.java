/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.servlets;

import com.saif.web.practice.shop.core.Shop;
import com.saif.web.practice.shop.beans.CustomerBean;
import com.saif.web.practice.shop.core.Operations;
import com.saif.web.practice.shop.resources.R;
import com.saif.web.practice.shop.util.ShopUtil;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Saif
 */

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet implements Shop {

    private void proccessRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        l().debug("doGet(), starts: ");
        Operations operations = R.getOperations(request);
        operations.setLoginInfo();
        operations.login();
        
        
        String origUrl = request.getParameter("origUrl");
        if(operations.isCustomerAuthenticated()){
            ShopUtil.addCookie(response);
            operations.syncWish();
            operations.syncCart();

            if(origUrl != null){
                if(!(origUrl.equalsIgnoreCase("null") || origUrl.equals(""))){
                    request.getRequestDispatcher(origUrl).forward(request, response);
                }
                else {request.getRequestDispatcher("/home/home-page.jsp").forward(request, response);}
            }
            else {request.getRequestDispatcher("/home/home-page.jsp").forward(request, response);}
                
        }else {
            // Not Signed Up. Send an error message as NotSignedUpError to the SignupPage
            // as a query parameter asking her to sign up.
            StringBuffer uri = new StringBuffer();
            uri.append(request.getServletContext().getInitParameter("signupPage"));
            uri.append("?notSignedUp=");
            uri.append(URLEncoder.encode("You are not registered to our Online shopping. Please Sign up..", "UTF-8"));
            uri.append("&origUrl=");
            uri.append(origUrl);
            
            l().debug("doGet(), ends else: ");
            request.getRequestDispatcher(uri.toString()).forward(request, response);
        }
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
