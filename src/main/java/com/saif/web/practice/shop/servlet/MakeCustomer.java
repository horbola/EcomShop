/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.servlet;

import com.saif.web.practice.shop.bean.Customer;
import com.saif.web.practice.shop.core.Operations;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
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
public class MakeCustomer extends HttpServlet {
    private final Logger LOGGER = LogManager.getLogger(MakeCustomer.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        LOGGER.debug("doPost()");
        String origUrl = request.getParameter("origUrl");
        String productsPage = request.getServletContext().getInitParameter("productsPage");
        
        Operations operations = (Operations)request.getServletContext().getAttribute("operations");
        // if customer is null then the Customer coudn't be signed Up. Ask her to try to sign Up again.
        Customer signUpCust = getSignUpCust(request);
        Customer customer = operations.makeCustomer(signUpCust);
        
        if(customer != null){
            LOGGER.debug("doPost(): customer not null");
            request.getSession().setAttribute("customer", customer);
            if(origUrl != null && !origUrl.equals("")){
                LOGGER.debug("doPost(): origUrl is - " +origUrl);
                request.getRequestDispatcher(origUrl).forward(request, response);
            }else {
                LOGGER.debug("doPost(): origUrl is null");
                request.getRequestDispatcher(productsPage).forward(request, response);
            }
        }else {
            // Couldn't be Signed Up. Redirect to signup page with an error message
            // as a query parameter asking her to try to sign up again.
            StringBuffer uri = new StringBuffer();
            uri.append(request.getServletContext().getInitParameter("signUpPage"));
            uri.append("?couldNotSignUp=");
            uri.append(URLEncoder.encode("Your Signup process couldn't be performed. Please Sign up again...", "UTF-8"));
            uri.append("&origUrl=");
            uri.append(origUrl);
            
            LOGGER.debug("Redirect url from MakeCustomer(): " + uri.toString());
            request.getRequestDispatcher(uri.toString()).forward(request, response);
        }
    }
    
    private Customer getSignUpCust(HttpServletRequest request){
        Customer signUpCust = new Customer();
        signUpCust.setUserName(request.getParameter("uName"));
        signUpCust.setPass(request.getParameter("pass"));
        signUpCust.setName(request.getParameter("name"));
        return signUpCust;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
