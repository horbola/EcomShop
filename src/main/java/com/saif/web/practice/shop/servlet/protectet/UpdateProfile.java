/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.servlet.protectet;

import com.saif.web.practice.shop.bean.Customer;
import com.saif.web.practice.shop.bean.Operations;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UpdateProfile extends HttpServlet {
    private final Logger LOGGER = LogManager.getLogger(UpdateProfile.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        LOGGER.debug("doPost()");
        Customer customerUpdate = new Customer();
        customerUpdate.setUserName(request.getParameter("uName"));
        customerUpdate.setPass(request.getParameter("pass"));
        customerUpdate.setName(request.getParameter("name"));
        
        LOGGER.debug(request.getParameter("uName"));
        LOGGER.debug(request.getParameter("pass"));
        LOGGER.debug(request.getParameter("name"));
        
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Operations operations = (Operations) request.getServletContext().getAttribute("operations");
        Customer updatedCustomer = operations.updateProfile(customer.getUserName(), customerUpdate);
        request.getSession().setAttribute("customer", updatedCustomer);
        
        String profilePage = request.getServletContext().getInitParameter("profilePage");
        request.getRequestDispatcher(profilePage).forward(request, response);
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
