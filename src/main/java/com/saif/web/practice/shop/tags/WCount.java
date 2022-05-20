/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.tags;

import com.saif.web.practice.shop.resources.R;
import com.saif.web.practice.shop.util.ShopUtil;
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
public class WCount extends SimpleTagSupport {

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
        int wishCount = R.getOperations(request).getWishCount();
        
        try {
            if(wishCount != 0){
                out.println("<span class=\"wishCount\">"+wishCount+"</span>");
            }else {
                out.println("<span class=\"wishCount\">0</span>");
            }
        } catch (java.io.IOException ex) {
            throw new JspException("Error in WCount tag", ex);
        }
    }
    
}
