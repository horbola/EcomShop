/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.bean;

import com.saif.web.practice.shop.bean.CartBean;
import com.saif.web.practice.shop.bean.AddressBean;
import com.saif.web.practice.shop.core.Shop;
import static com.saif.web.practice.shop.jooq.Tables.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Saif
 */
public class CustomerBean implements Shop {
    private String customerID = "";
    private String password = "";
    private String name = "";

    public String getCustomerID() {return customerID;}
    public void setCustomerID(String customerID) {this.customerID = customerID;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    
    
    
    private boolean authenticated = false;

    public boolean isAuthenticated() {return authenticated;}
    public void setAuthenticated(boolean authenticated) {this.authenticated = authenticated;}

    
    
    private ArrayList<AddressBean> addressBook;

    public ArrayList<AddressBean> getAddressBook() {return addressBook;}
    public void setAddressBook(ArrayList<AddressBean> addressBook) {this.addressBook = addressBook;}
    
    
    
    private WishBean wish;
    private CartBean cart;
    private OrderBean orderTemp;
    private HashMap<String,OrderBean> ordersTemp;
    private HashMap<String,OrderBean> orders;
    private HashMap<String,OrderCanBean> ordersCan;

    public WishBean getWish() {
        if(wish == null) wish = new WishBean(this);
        return wish;
    }
    public void setWish(WishBean wish) {this.wish = wish;}

    public CartBean getCart() {
        if(cart == null) cart = new CartBean(this);
        return cart;
    }
    public void setCart(CartBean cart) {this.cart = cart;}

    public OrderBean getOrderTemp() {
        if(orderTemp == null) orderTemp = new OrderBean(cart);
        return orderTemp;
    }
    public void setOrderTemp(OrderBean orderTemp) {this.orderTemp = orderTemp;}

    
    public HashMap<String,OrderBean> getOrdersTemp() {return ordersTemp;}
    public void setOrdersTemp(HashMap<String,OrderBean> ordersTemp) {this.ordersTemp = ordersTemp;}

    public HashMap<String,OrderBean> getOrders() {return orders;}
    public void setOrders(HashMap<String,OrderBean> orders) {this.orders = orders;}

    public HashMap<String,OrderCanBean> getOrdersCan() {return ordersCan;}
    public void setOrdersCan(HashMap<String,OrderCanBean> ordersCan) {this.ordersCan = ordersCan;}
    
    
    
    public CustomerBean(){}
    public CustomerBean(String customerID){this.customerID = customerID;}
    public CustomerBean(HttpServletRequest request) {getLoginInfo(request);}
    
    public boolean getLoginInfo(HttpServletRequest request){
        String customerID = request.getParameter("customerID");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        
        if(!(customerID == null | customerID.equals("null") | customerID.equals("")))
            this.customerID = customerID;
        else return false;
        
        if(!(password != null | password.equals("null") | password.equals("")))
            this.password = password;
        else return false;
        
        if(!(name != null | name.equals("null") | name.equals("")))
            this.name = name;
        
        return true;
    }
    
    public boolean getSignupInfo(HttpServletRequest request){
        return getLoginInfo(request);
    }
    
    public void fillAddressBook(ResultSet rs) throws SQLException {
        if(addressBook == null) addressBook = new ArrayList<AddressBean>();
        while(rs.next()){
            AddressBean address = new AddressBean(this);
            address.setAddressID(rs.getInt("addressID"));
            address.setAddressType(rs.getString("addressType"));
            address.setFlat(rs.getString("flat"));
            address.setHouse(rs.getString("house"));
            address.setRoad(rs.getString("road"));
            address.setArea(rs.getString("area"));
            address.setThana(rs.getString("thana"));
            address.setDistrict(rs.getString("district"));
            addressBook.add(address);
        }
    }
    
    
    
    
    
    public String getSelectSql(){
        String s = create.select()
                         .from(CUSTOMERS)
                         .where(CUSTOMERS.CUSTOMERID.eq(customerID))
                         .getSQL();
       return s;
    }
    
    public String getInsertSql(){
        String s = create.insertInto(CUSTOMERS, CUSTOMERS.CUSTOMERID, CUSTOMERS.PASSWORD, CUSTOMERS.NAME)
                         .values(customerID, password, name)
                         .getSQL();
        return s;
    }

    public String getOrdersTempSql() {
        String sql = create.selectFrom(ORDERSTEMP)
                           .where(ORDERSTEMP.CUSTOMERID.eq(customerID))
                           .getSQL();
        return sql;
    }

}
