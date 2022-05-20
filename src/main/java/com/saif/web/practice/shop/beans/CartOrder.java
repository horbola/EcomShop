/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.beans;

import com.saif.web.practice.shop.beans.AddressBean.SameWhat;
import com.saif.web.practice.shop.core.AddressType;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Saif
 */
public class CartOrder extends WishCartOrder {
    protected AddressBean shippingAddress;
    protected AddressBean billingAddress;
    protected AddressBean prevShippingAddress;
    protected AddressBean prevBillingAddress;
    protected int deliveryMethod = 0;
    protected int paymentMethod = 0;

    public AddressBean getShippingAddress() {
        if(shippingAddress == null) shippingAddress = new AddressBean(customer);
        return shippingAddress;
    }
    public void setShippingAddress(AddressBean shippingAddress) {this.shippingAddress = shippingAddress;}

    public AddressBean getBillingAddress() {
        if(billingAddress == null) billingAddress = new AddressBean(customer);
        return billingAddress;
    }
    public void setBillingAddress(AddressBean billingAddress) {this.billingAddress = billingAddress;}

    public AddressBean getPrevShippingAddress() {
        if(prevShippingAddress == null) prevShippingAddress = new AddressBean(customer);
        return prevShippingAddress;
    }
    public void setPrevShippingAddress(AddressBean prevShippingAddress) {this.prevShippingAddress = prevShippingAddress;}

    public AddressBean getPrevBillingAddress() {
        if(prevBillingAddress == null) prevBillingAddress = new AddressBean(customer);
        return prevBillingAddress;
    }
    public void setPrevBillingAddress(AddressBean prevBillingAddress) {this.prevBillingAddress = prevBillingAddress;}
    
    public int getDeliveryMethod() {return deliveryMethod;}
    public void setDeliveryMethod(int deliveryMethod) {this.deliveryMethod = deliveryMethod;}
    public void setDeliveryMethod(String deliveryMethod) {this.deliveryMethod = Integer.parseInt(deliveryMethod);}

    public int getPaymentMethod() {return paymentMethod;}
    public void setPaymentMethod(int paymentMethod) {this.paymentMethod = paymentMethod;}
    public void setPaymentMethod(String paymentMethod) {this.paymentMethod = Integer.parseInt(paymentMethod);}
    
    
    
    public CartOrder(CustomerBean customer){
        super(customer);
    }
    
    
    
    
    public void fillPrevShippingAddress(ResultSet rs) {
        if(prevShippingAddress == null) prevShippingAddress = new AddressBean(customer);
        addressRRMedia(rs, prevShippingAddress, AddressType.SHIPPING);
    }

    public void fillPrevBillingAddress(ResultSet rs) {
        if(prevBillingAddress == null) prevBillingAddress = new AddressBean(customer);
        addressRRMedia(rs, prevBillingAddress, AddressType.BILLING);
    }

    public void fillRequestShippingAddress(HttpServletRequest request) {
        if(shippingAddress == null) shippingAddress = new AddressBean(customer);
        addressRRMedia(request, shippingAddress, AddressType.SHIPPING);
    }

    public void fillRequestBillingAddress(HttpServletRequest request) {
        if(billingAddress == null) billingAddress = new AddressBean(customer);
        addressRRMedia(request, billingAddress, AddressType.BILLING);
    }
    
    
    

    @Override
    public void syncOtherFields(ResultSet rs) throws SQLException {
        try{
            int shipAddrID = rs.getInt("shippingAddressID");
            if(shipAddrID != 0 && prevShippingAddress == null)
                prevShippingAddress = new AddressBean(customer, shipAddrID);
        }catch(SQLException e){}

        try{
            int billAddrID = rs.getInt("billingAddressID");
            if(billAddrID != 0 && prevBillingAddress == null)
                prevBillingAddress = new AddressBean(customer, billAddrID);
        }catch(SQLException e){}

        try{
            int deliMeth = rs.getInt("deliveryMethod");
            if(deliMeth != 0 && deliveryMethod != 0)
                deliveryMethod = deliMeth;
        }catch(SQLException e){}

        try{
            int paymMeth = rs.getInt("paymentMethod");
            if(paymMeth != 0 && paymentMethod != 0)
                paymentMethod = paymMeth;
        }catch(SQLException e){}

        try{
            String dateOfCartOrder = rs.getString("placed");
            if(dateOfCartOrder != null){
                if(!(dateOfCartOrder.equalsIgnoreCase("null") || dateOfCartOrder.equals("")))
                    setDate(dateOfCartOrder);
            }
        }catch(SQLException e){}
    }
    
    
    public boolean noStoreAddress(AddressType type){
        boolean noStore = false;
        SameWhat sameWhat = null;
        if(type == AddressType.SHIPPING){
            if(shippingAddress == null) noStore = true;
            else if(prevShippingAddress != null && shippingAddress.areSame(prevShippingAddress)) noStore = true;
            else if((sameWhat = shippingAddress.areAllSame()).isSame) noStore = true;
        }
        else if(type == AddressType.BILLING){
            if(billingAddress == null) noStore = true;
            else if(prevBillingAddress != null && billingAddress.areSame(prevBillingAddress)) noStore = true;
            else if((sameWhat = billingAddress.areAllSame()).isSame) noStore = true;
        }        
        return noStore;
    }
    
    public boolean transferAddressID(AddressType type){
        if(type == AddressType.SHIPPING){
            if(shippingAddress == null) shippingAddress = new AddressBean(customer, 0);
            if(prevShippingAddress == null) return false;
            else{
                shippingAddress.setAddressID(prevShippingAddress.getAddressID());
                if(shippingAddress.getAddressID() == 0) return false;
                else return true;
            }
        }
        else if(type == AddressType.BILLING){
            if(billingAddress == null) billingAddress = new AddressBean(customer, 0);
            if(prevBillingAddress == null) return false;
            else{
                billingAddress.setAddressID(prevBillingAddress.getAddressID());
                if(billingAddress.getAddressID() == 0) return false;
                else return true;
            }
        }
        return false;
    }
    
    
    public void transferAddressIDFromAddressBook(AddressType type){
        if(type == AddressType.SHIPPING)
            shippingAddress.setAddressID((shippingAddress.areAllSame()).addressID);
            
        else if(type == AddressType.BILLING)
            billingAddress.setAddressID((billingAddress.areAllSame()).addressID);
    }
    
}
