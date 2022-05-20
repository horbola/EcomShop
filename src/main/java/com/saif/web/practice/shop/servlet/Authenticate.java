/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.servlet;

import com.saif.web.practice.shop.bean.Customer;
import com.saif.web.practice.shop.bean.Operations;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Saif
 */
public class Authenticate extends HttpServlet {
    private final Logger LOGGER = LogManager.getLogger(Authenticate.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        LOGGER.debug("doGet()");
        String origUrl = request.getParameter("origUrl");
        String productsPage = request.getServletContext().getInitParameter("productsPage");
        
        Customer loginCust = new Customer();
        loginCust.setUserName(request.getParameter("uName"));
        loginCust.setPass(request.getParameter("pass"));
        
        Operations operations = (Operations)request.getServletContext().getAttribute("operations");
        // if customer is null then the Customer isn't signed Up. Ask her to sign Up.
        Customer customer = operations.authenticate(loginCust);
        
        
        
        if(customer != null){
            LOGGER.debug("doGet(): customer not null");
            request.getSession().setAttribute("customer", customer);
            if(origUrl != null && !origUrl.equals("")){
                LOGGER.debug("doGet(): origUrl is - " +origUrl);
                request.getRequestDispatcher(origUrl).forward(request, response);
            }else {
                LOGGER.debug("doGet(): origUrl is null");
                request.getRequestDispatcher(productsPage).forward(request, response);
            }
        }else {
            // Not Signed Up. Send an error message to the NotSignedUpErrorPage
            // as a query parameter asking her to sign up.
            StringBuffer uri = new StringBuffer();
            uri.append(request.getServletContext().getInitParameter("signUpPage"));
            uri.append("?notSignedUp=");
            uri.append(URLEncoder.encode("You are not registered to our Online shopping. Please Sign up..", "UTF-8"));
            uri.append("&origUrl=");
            uri.append(origUrl);
            
            LOGGER.debug("Redirect url from Authenticate: " + uri.toString());
            request.getRequestDispatcher(uri.toString()).forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
