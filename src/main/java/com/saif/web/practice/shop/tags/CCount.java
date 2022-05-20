/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.tags;

import com.saif.web.practice.shop.resources.R;
import com.saif.web.practice.shop.util.ShopUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Saif
 */
public class CCount extends SimpleTagSupport {

    /**
     * Called by the container to invoke this tag. The implementation of this
     * method is provided by the tag library developer, and handles all tag
     * processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        int cartCount = R.getOperations(request).getCartCount();
        
        try {
            if(cartCount != 0){
                out.println("<span class=\"cartCount\">"+cartCount+"</span>");
            }else {
                out.println("<span class=\"cartCount\">0</span>");
            }

        } catch (IOException ex) {
            throw new JspException("Error in WCount tag", ex);
        }
    }
    
}
