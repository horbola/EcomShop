/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.servlet.protectet;

import com.saif.web.practice.shop.bean.Customer;
import com.saif.web.practice.shop.bean.Operations;
import com.saif.web.practice.shop.bean.Order;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.ServletException;
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


public class MakeOrder extends HttpServlet {
    private final Logger LOGGER = LogManager.getLogger(MakeOrder.class);
    private boolean debug = true;
    private String orderFeedbackPage;
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
        LOGGER.debug("processRequest()");
        
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        String cartJson = request.getParameter("cartJson");
        
        Order order = new Order(customer, cartJson);
        
        Operations operations = (Operations) request.getServletContext().getAttribute("operations");
        SortedMap[] orderFeedback = operations.makeOrder(order);
        
        // the next line doesn't work. I don't get this variable in jsp with the el.
        // request.setAttribute("parsedJson", parsedJson);

        if(order != null){
            request.setAttribute("orderFeedback", orderFeedback);
            orderFeedbackPage = request.getServletContext().getInitParameter("orderFeedbackPage");
            request.getRequestDispatcher(orderFeedbackPage).forward(request, response);
        }
        
    }
    
    
    
    /*
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        LOGGER.debug("processRequest()");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        String uName = null;
        if(customer != null){uName = customer.getUserName();}
        String cartJson = request.getParameter("cartJson");
        
        Order ordered = new Order(customer, cartJson);
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
