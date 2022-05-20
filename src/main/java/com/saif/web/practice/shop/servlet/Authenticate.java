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
    private final Logger logger = LogManager.getLogger(Authenticate.class);
    String debug = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String uName = request.getParameter("uName");
        String pass = request.getParameter("pass");
        String origUrl = request.getParameter("origUrl");
        
        Operations operations = (Operations)request.getServletContext().getAttribute("operations");
        // if customer is null then the Customer isn't signed Up. Ask her to sign Up.
        Customer customer = operations.authenticate(uName, pass);
//        customer = null; // made null for debugging pupose.
        

//        logger.debug("Authenticated User Name: " + customer.getUserName());
//        logger.debug("Authenticated Password: " + customer.getPass());
//        logger.debug("Authenticated Name: " + customer.getName());
//
//        logger.debug("Entered processRequest()");
//        logger.debug("origUrl is: "+ origUrl);
//        logger.debug("Direct uName is: "+ uName);
//        logger.debug("Direct pass is: "+ pass);
//        logger.debug("all three executed");
        
        if(customer != null){
            // if(login page is invoded from redirection then redirect it to the original page)
            // else if(login page is invoked from any page staticaly, then stay on that page after logged in)
            // else if(login page is invoked directly from start then redirect to products page)
            request.getSession().setAttribute("customer", customer);
            String forwardPage = request.getServletContext().getInitParameter("productsPage");
            request.getRequestDispatcher(origUrl).forward(request, response);
        }else {
            // Not Signed Up. Send an error message to the NotSignedUpErrorPage
            // as a query parameter asking her to sign up.
            
            String notSignedUp = new String("notSignedUp=You+are+not+registered+to+our+online+shopping.+Please+sign+in+first.+Its+very+easy.");
            StringBuffer uri = new StringBuffer(request.getContextPath());
            uri.append(request.getServletContext().getInitParameter("signUpPage"));
            uri.append("?");
            uri.append(notSignedUp);
            uri.append("&");
            uri.append("origUrl=");
            uri.append(URLEncoder.encode(origUrl, "UTF-8"));
            
            // String redirectPage = request.getServletContext().getInitParameter("notSignedUpErrorPage");
            // String redirectUrl = request.getContextPath()+redirectPage+"?"+notSignedUp;
            
            logger.debug(uri.toString());
            response.sendRedirect(uri.toString());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
