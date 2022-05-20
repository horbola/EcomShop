/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.bean;

import com.saif.web.practice.shop.test.ParsedJson;
import com.saif.web.practice.shop.util.ParseJson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Saif
 */
public class Operations {
    private final Logger logger = LogManager.getLogger(Operations.class);
    String jdbcPath;
    
    public Operations(String jdbcPath){
        loadJdbc();
        this.jdbcPath = jdbcPath;
    }
    
    public void loadJdbc(){
        try{Class.forName("org.sqlite.JDBC");}
        catch(ClassNotFoundException ex){ex.printStackTrace();}
    }

    public String getJdbcPath() {
        return jdbcPath;
    }

    
    
    public Customer authenticate(String uName, String pass) {
        StringBuffer selectBuff = new StringBuffer("SELECT * FROM Customers WHERE uName = \"" +uName +"\";");
        SortedMap result[] = null;
        Statement s = null;
        String path = getJdbcPath();
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            Sql sql = new Sql(selectBuff.toString(), s);
            
            result = sql.executeQuery();
            
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
        
        Customer customer = null;
        if(result != null && result.length != 0 && uName.equals(result[0].get("uName"))){
            customer = new Customer();
            customer.setUserName((String) result[0].get("uName"));
            customer.setPass((String) result[0].get("pass"));
            customer.setName((String) result[0].get("name"));
        }
        return customer;
    }

    public SortedMap[] showAllProducts() {
        SortedMap[] result = null;
        StringBuffer buff = new StringBuffer("SELECT * FROM Products;");
        Statement s = null;
        String path = getJdbcPath();
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            Sql sql = new Sql(buff.toString(), s);
            result = sql.executeQuery();
            
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
        return result;
    }

    public void makeOrder(String uName, String cartJson) {
        ArrayList<StringBuffer> buffs = ParseJson.parseJson(cartJson);
        
        logger.debug("size of buff: " +buffs.size());
        for(StringBuffer b : buffs){
            logger.debug("this is from ArrayList: " +b.toString());
        }
        
        
        
//        int i = 0;
//        for(StringBuffer buff : buffs){
//            logger.debug("buffer "+ i++ +"\n");
//            logger.debug(buff.toString());
//        }
//        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
//            s = conn.createStatement();
//            
//        }catch(SQLException e){}
//        finally{
//            try{if(s != null) s.close();}
//            catch(SQLException e){}
//        }
    }

    public Customer makeCustomer(String uName, String pass, String name) {
        StringBuffer buff = new StringBuffer("INSERT INTO Customers (uName, pass, name) VALUES (");
        buff.append("\"" +uName +"\"");
        buff.append(",");
        buff.append("\"" +pass +"\"");
        buff.append(",");
        buff.append("\"" +name +"\"");
        buff.append(");");
        
        StringBuffer selectBuff = new StringBuffer("SELECT * FROM Customers WHERE uName = \"" +uName +"\";");
        
        SortedMap result = null;
        
        Statement s = null;
        String path = getJdbcPath();
        try(Connection conn = DriverManager.getConnection(getJdbcPath())){
            s = conn.createStatement();
            
            Sql sql = new Sql(buff.toString(), s);
            sql.executeInsert();
            
            sql.setSqlStatement(selectBuff.toString());
            result = sql.executeQuery()[0];
            
        }catch(SQLException e){}
        finally{
            try{if(s != null) s.close();}
            catch(SQLException e){}
        }
        
        Customer customer = new Customer();
        customer.setUserName((String) result.get("uName"));
        customer.setPass((String) result.get("pass"));
        customer.setName((String) result.get("name"));
        
        return customer;
    }
    
}
