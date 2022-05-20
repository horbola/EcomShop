/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.servlet.authorize;

import com.saif.web.practice.shop.bean.CustomerBean;
import com.saif.web.practice.shop.core.Operations;
import com.saif.web.practice.shop.bean.OrderBean;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * When this class is called with a xmlhttprequest from browser then
 * the logger and setting attribute to request doesn't work. But i noticed
 * that logger worked once, why!? is it a bug in servlet?
 */

@WebServlet(name = "Order", urlPatterns = {"/authorize/Order"})
public class Order extends HttpServlet {
    private Logger log = LogManager.getLogger(Order.class);
    private String orderFeedbackPage;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        log.debug("processRequest(), starts: ");
        
        CustomerBean customer = (CustomerBean) request.getSession().getAttribute("customer");
        String orderProdsJsonParam = request.getParameter("orderProdsJsonParam");
        
        OrderBean order = new OrderBean(customer, orderProdsJsonParam);
        
        Operations operations = (Operations) request.getServletContext().getAttribute("operations");
        SortedMap[] orderFeedback = operations.makeOrder(order);
        
        // the next line doesn't work. I don't get this variable in jsp with the el.
        // request.setAttribute("parsedJson", parsedJson);

        if(order != null){
            request.setAttribute("orderFeedback", orderFeedback);
            //orderFeedbackPage = request.getServletContext().getInitParameter("orderFeedbackPage");
            request.getRequestDispatcher("/order/order-page.jsp").forward(request, response);
        }
        log.debug("processRequest(), ends: ");
    }
    
    
    
    /*
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        LOGGER.debug("processRequest()");
        CustomerBean customer = (CustomerBean) request.getSession().getAttribute("customer");
        String uName = null;
        if(customer != null){uName = customer.getUserName();}
        String cartJson = request.getParameter("cartJson");
        
        OrderBean ordered = new OrderBean(customer, cartJson);
        ordered.setOrderId(10);
        for(StringBuffer b : ordered.getInserts()){
            LOGGER.debug(b.toString());
        }
        
        Operations operations = (Operations) request.getServletContext().getAttribute("operations");
        SortedMap[] order = operations.makeOrder(uName, cartJson);
        
        // the next line doesn't work. I don't get this variable in jsp with the el.
        // request.setAttribute("parsedJson", parsedJson);

        if(order != null){
            request.setAttribute("order", order);
            orderFeedbackPage = request.getServletContext().getInitParameter("orderFeedbackPage");
            request.getRequestDispatcher(orderFeedbackPage).forward(request, response);
        }
        
    }
    
    
    */
    
    
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
