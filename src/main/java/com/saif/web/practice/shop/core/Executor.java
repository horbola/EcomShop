/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.core;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;
import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.CachedRowSet;

import java.util.logging.Level;

/**
 *
 * @author Saif
 */
public class Executor{
    
    private static DataSource ds;
    public static DataSource getDs() {return ds;}
    public static void setDs(DataSource ds) {Executor.ds = ds;}

    
    public static CachedRowSet execute(String sql) {
        try(Connection conn = ds.getConnection()){
            try(Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery(sql)){
                
                CachedRowSet crs = new CachedRowSetImpl();
                crs.populate(rs);
                return crs;

            }catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Executor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Executor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void execute(ArrayList<String> sqls) {
        try(Connection conn = ds.getConnection()){
            try(Statement s = conn.createStatement()){
                
                for(String sql : sqls){
                    s.executeUpdate(sql);
                }

            }catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Executor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Executor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
