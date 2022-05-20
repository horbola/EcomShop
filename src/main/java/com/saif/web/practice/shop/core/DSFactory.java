/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.sqlite.SQLiteDataSource;

/**
 *
 * @author Saif
 */
public class DSFactory implements Shop {
    Properties prop = null;
    public Properties getProp() {return prop;}
    public void setProp(Properties prop) {this.prop = prop;}
    
    
    public DSFactory(){
        prop = new Properties();
        try(InputStream inStream = getClass().getClassLoader().getResourceAsStream("db.properties")){
            prop.load(inStream);
        } catch (IOException ex) {
            Logger.getLogger(DSFactory.class.getName()).log(Level.SEVERE, null, ex);
            l().debug("db.properties couldn't be read");
        }
    }
    
    public SQLiteDataSource getSqliteDS(){
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(prop.getProperty(""));
        return ds;
    }
    
    public DataSource getDataSource(DSType type){
        BasicDataSource bds = new BasicDataSource();
        switch(type){
            case SQLITE_SHOP_DB :
                bds.setDriverClassName(prop.getProperty("SQLITE_DB_DRIVER_CLASS"));
                bds.setUrl(prop.getProperty("SQLITE_SHOP_DB_URL"));
                break;
                
            case SQLITE_SHOP_DRAFT :
                bds.setDriverClassName(prop.getProperty("SQLITE_DB_DRIVER_CLASS"));
                bds.setUrl(prop.getProperty("SQLITE_SHOP_DRAFT_URL"));
                break;
                
            case MY_SQL :
                bds.setDriverClassName("");
                bds.setUrl("");
                bds.setUsername("");
                bds.setPassword("");
                break;
                
            case ORACLE :
                bds.setDriverClassName("");
                bds.setUrl("");
                bds.setUsername("");
                bds.setPassword("");
                break;
                
            default :
                bds = null;
        }
        return bds;
    }
}
