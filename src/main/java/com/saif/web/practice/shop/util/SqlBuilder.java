/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.util;

/**
 *
 * @author Saif
 */
public class SqlBuilder {
    
    String[] cols, table, cond;
    StringBuffer select;
    
    public static void main(String ...s){
        new SqlBuilder();
    }
    
    SqlBuilder(){
        String sql = select("one", "two", "three", "four").from("Orders", "Products").getSql();
        System.out.println(sql);
    }
    
    public SqlBuilder select(String ...cols){
        this.cols = cols;
        select = new StringBuffer();
        select.append("SELECT ");
        
        if(cols.length == 0){
            select.append("* ");
        }else{
            for(int i = 0; i<cols.length; i++){
                select.append(cols[i]);
                if(i != cols.length-1){
                    select.append(",");
                }
                select.append(" ");
            }
        }
        return this;
    }
    
    public SqlBuilder from(String ...table){
        this.table = table;
        select.append("FROM ");
        for(int i = 0; i<table.length; i++){
            select.append(table[i]);
            if(i != table.length-1){
                select.append(",");
            }
            select.append(" ");
        }
        return this;
    }
    
    public SqlBuilder where(String ...cond){
        this.cond = cond;
        for(int i = 0; i<table.length; i++){
            select.append(table[i]);
            if(i != table.length-1){
                select.append("and");
            }
            select.append(" ");
        }
        return this;
    }
    
    public String getSql(){
        return select.toString();
    }
}
