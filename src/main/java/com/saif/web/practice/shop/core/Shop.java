/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.core;

/**
 *
 * @author Saif
 */


import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public interface Shop {
    
    default Logger l(){
        Logger log = LogManager.getLogger(this.getClass());
        return log;
    }
    
}


