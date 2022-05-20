/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.servlet.admin;

import com.saif.web.practice.shop.bean.Operations;
import com.saif.web.practice.shop.bean.Product;
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
public class ProductsEntry extends HttpServlet {
    private final Logger LOGGER = LogManager.getLogger(ProductsEntry.class);
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
        Product product = new Product();
        product.setProductId(request.getParameter("productId"));
        product.setMfrId(request.getParameter("mfrId"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(request.getParameter("price"));
        product.setQuantity(request.getParameter("quantity"));
        
        Operations operations = (Operations) request.getServletContext().getAttribute("operations");
        Product productFeedback = operations.productsEntry(product);
        LOGGER.debug(productFeedback.getProductId());
        LOGGER.debug(productFeedback.getMfrId());
        LOGGER.debug(productFeedback.getDescription());
        LOGGER.debug(productFeedback.getPrice());
        LOGGER.debug(productFeedback.getQuantity());
        
        request.setAttribute("productFeedback", productFeedback);
        String productsEntryPage = request.getServletContext().getInitParameter("productsEntryPage");
        request.getRequestDispatcher(productsEntryPage).forward(request, response);
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
