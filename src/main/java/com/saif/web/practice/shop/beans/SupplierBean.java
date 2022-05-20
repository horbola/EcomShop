/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.beans;

import java.util.ArrayList;

/**
 *
 * @author Saif
 */
public class SupplierBean extends Bean {
    private String supplierID = "";
    private String shortName = "";
    private String fullName = "";
    private String house = "";
    private String road = "";
    private String area = "";
    private String thana = "";
    private String district = "";

    public String getSupplierID() {return supplierID;}
    public void setSupplierID(String supplierID) {this.supplierID = supplierID;}

    public String getShortName() {return shortName;}
    public void setShortName(String shortName) {this.shortName = shortName;}

    public String getFullName() {return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}

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



    
    private ArrayList<SupplierBranchBean> branches;
    private ArrayList<ProductBean> products;
    
    public ArrayList<SupplierBranchBean> getBranches() {return branches;}
    public void setBranches(ArrayList<SupplierBranchBean> branches) {this.branches = branches;}

    public ArrayList<ProductBean> getProducts() {return products;}
    public void setProducts(ArrayList<ProductBean> products) {this.products = products;}


    public SupplierBean(){}
    public SupplierBean(String supplierID){
        this.supplierID = supplierID;
    }
}
