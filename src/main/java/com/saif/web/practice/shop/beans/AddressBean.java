/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.beans;

import static com.saif.web.practice.shop.jooq.Tables.*;
import java.util.ArrayList;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

/**
 *
 * @author Saif
 */
public class AddressBean extends Bean {
    private CustomerBean customer;
    private int addressID = 0;
    private String addressType = "both";
    private String flat = "";
    private String house = "";
    private String road = "";
    private String area = "";
    private String thana = "";
    private String district = "";

    public CustomerBean getCustomer() {return customer;}
    public void setCustomer(CustomerBean customer) {this.customer = customer;}

    public int getAddressID() {return addressID;}
    public void setAddressID(int addressID) {this.addressID = addressID;}
    public void setAddressID(String addressID) {this.addressID = Integer.parseInt(addressID);}

    public String getAddressType() {return addressType;}
    public void setAddressType(String addressType) {this.addressType = addressType;}
    
    public String getFlat() {return flat;}
    public void setFlat(String flat) {this.flat = flat;}

    public String getHouse() {return house;}
    public void setHouse(String house) {this.house = house;}

    public String getRoad() {return road;}
    public void setRoad(String road) {this.road = road;}

    public String getArea() {return area;}
    public void setArea(String area) {this.area = area;}

    public String getThana() {return thana;}
    public void setThana(String thana) {this.thana = thana;}

    public String getDistrict() {return district;}
    public void setDistrict(String district) {this.district = district;}

    
    public AddressBean(){}
    
    public AddressBean(CustomerBean customer){
        this.customer = customer;
    }
    
    public AddressBean(CustomerBean customer, int addressID){
        this(customer);
        this.addressID = addressID;
    }
    
    
    
    boolean areSame(AddressBean address) {
        if(flat.equals(address.getFlat()) &&
           house.equals(address.getHouse()) &&
           road.equals(address.getRoad()) &&
           thana.equals(address.getThana()) &&
           area.equals(address.getArea()) &&
           district.equals(address.getDistrict())
           ) return true;
        return false;
    }
    
    SameWhat areAllSame() {
        SameWhat sameWhat = new SameWhat();
        ArrayList<AddressBean> addrBook = customer.getAddressBook();
        if(addrBook == null) return sameWhat;
        for(AddressBean addr : addrBook){
            sameWhat.isSame = areSame(addr);
            if(sameWhat.isSame){
                sameWhat.addressID = addr.getAddressID();
                return sameWhat;
            }
        }        
        return sameWhat;
    }
    
    class SameWhat {
        boolean isSame = false;
        int addressID = 0;
    }
    
    
    
    
    public String getSelectOneSql(){
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.selectFrom(ADDRESSES)
                         .where(ADDRESSES.CUSTOMERID.eq(customer.getCustomerID()))
                         .and(ADDRESSES.ADDRESSID.eq(addressID))
                         .getSQL(ParamType.INLINED);
        return s;
    }
    
    public String getSelectMaxAddressIDSql(){
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.select(DSL.max(ADDRESSES.ADDRESSID).as("maxAddressID"))
                         .from(ADDRESSES)
                         .where(ADDRESSES.CUSTOMERID.eq(customer.getCustomerID()))
                         .getSQL(ParamType.INLINED);
        return s;
    }

    public String getInserOneSql() {
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.insertInto(ADDRESSES, ADDRESSES.CUSTOMERID, ADDRESSES.ADDRESSID, ADDRESSES.ADDRESSTYPE, ADDRESSES.FLAT, ADDRESSES.HOUSE, ADDRESSES.ROAD, ADDRESSES.AREA, ADDRESSES.THANA, ADDRESSES.DISTRICT)
                         .values(customer.getCustomerID(), addressID, addressType, flat, house, road, area, thana, district)
                         .getSQL(ParamType.INLINED);
        return s;
    }

    
}
