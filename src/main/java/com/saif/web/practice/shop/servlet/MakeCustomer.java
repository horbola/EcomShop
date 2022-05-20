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
    private final Logger logger = LogManager.getLogger(MakeCustomer.class);
    
    private boolean debug = true;
    private String confirmCustomerPage;

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
        String name = request.getParameter("name");
        
        Operations operations = (Operations)request.getServletContext().getAttribute("operations");
        Customer customer = operations.makeCustomer(uName, pass, name);
        
        logger.debug("userName is: "+ customer.getUserName());
        logger.debug("password is: "+ customer.getPass());
        logger.debug("name is: "+ customer.getName());
        
        confirmCustomerPage = request.getServletContext().getInitParameter("confirmCustomerPage");
        logger.debug("confirmCustomerPage is: " +confirmCustomerPage);
        logger.debug("origUrl is: " +request.getParameter("origUrl"));
        logger.debug("Encoded origUrl is: " +URLEncoder.encode(request.getParameter("origUrl"), "UTF-8"));
        
        if(customer != null){
            request.getSession().setAttribute("customer", customer);
            request.getRequestDispatcher(request.getParameter("origUrl")).forward(request, response);
            
        }else{
            String signUpErrorPage = request.getServletContext().getInitParameter("signUpErrorPage");
            String redirectUri = request.getContextPath()+signUpErrorPage;
            response.sendRedirect(redirectUri);
        }


        
//        if(debug){
//            request.getRequestDispatcher(confirmCustomerPage).forward(request, response);
//        }
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
