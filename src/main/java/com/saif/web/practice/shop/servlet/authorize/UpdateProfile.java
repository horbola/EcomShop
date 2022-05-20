/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.servlet.authorize;

import com.saif.web.practice.shop.bean.Customer;
import com.saif.web.practice.shop.core.Operations;
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
        
        Customer custOld = (Customer) request.getSession().getAttribute("customer");
        Operations operations = (Operations) request.getServletContext().getAttribute("operations");
        
        Customer custNew = getCustomerUpdate(request);
        Customer updatedCustomer = operations.updateProfile(custOld, custNew);
        request.getSession().setAttribute("customer", updatedCustomer);
        String profilePage = request.getServletContext().getInitParameter("profilePage");
        request.getRequestDispatcher(profilePage).forward(request, response);
    }
    
    private Customer getCustomerUpdate(HttpServletRequest request){
        Customer custNew = new Customer();
        custNew.setUserName(request.getParameter("uName"));
        custNew.setPass(request.getParameter("pass"));
        custNew.setName(request.getParameter("name"));
        return custNew;
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
