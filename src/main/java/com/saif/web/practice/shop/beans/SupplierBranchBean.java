/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.beans;

/**
 *
 * @author Saif
 */
public class SupplierBranchBean extends Bean {
    private SupplierBean supplier;
    private int branchID = 0;
    private String house = "";
    private String road = "";
    private String area = "";
    private String thana = "";
    private String district = "";
    

    
    public SupplierBranchBean(SupplierBean supplier, int branchID){
        this.supplier = supplier;
        this.branchID = branchID;
    }
}
