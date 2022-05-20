/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.beans;

import java.math.BigDecimal;
import static com.saif.web.practice.shop.jooq.Tables.*;
import java.sql.Date;
import java.sql.SQLException;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.json.simple.JSONObject;

/**
 *
 * @author Saif
 */
public class ProductBean extends Bean {
    private SupplierBean supplier;
    private String productID = "";
    
    private String domain = "";
    private String kingdom = "";
    private String phylum = "";
    private String klass = "";
    private String orderr = "";
    private String family = "";
    private String genus = "";
    private String species = "";
    
    private String shortDescrip = "";
    private String longDescrip = "";
    private int size = 0;
    private String color = "";
    private String style = "";
    private int weight = 0;
    private BigDecimal prevPrice = new BigDecimal(0);
    private BigDecimal price = new BigDecimal(0);
    private int stock = 0;
    private int quantity = 0;
    private Date arrived = null;
    private Date placed = null;
    private String path = "";

    public SupplierBean getSupplier() {return supplier;}
    public void setSupplier(SupplierBean supplier) {this.supplier = supplier;}

    public String getProductID() {return productID;}
    public void setProductID(String productID) {this.productID = productID;}

    public String getDomain() {return domain;}
    public void setDomain(String domain) {this.domain = domain;}

    public String getKingdom() {return kingdom;}
    public void setKingdom(String kingdom) {this.kingdom = kingdom;}

    public String getPhylum() {return phylum;}
    public void setPhylum(String phylum) {this.phylum = phylum;}

    public String getKlass() {return klass;}
    public void setKlass(String klass) {this.klass = klass;}

    public String getOrderr() {return orderr;}
    public void setOrderr(String orderr) {this.orderr = orderr;}

    public String getFamily() {return family;}
    public void setFamily(String family) {this.family = family;}

    public String getGenus() {return genus;}
    public void setGenus(String genus) {this.genus = genus;}

    public String getSpecies() {return species;}
    public void setSpecies(String species) {this.species = species;}

    public String getShortDescrip() {return shortDescrip;}
    public void setShortDescrip(String shortDescrip) {this.shortDescrip = shortDescrip;}

    public String getLongDescrip() {return longDescrip;}
    public void setLongDescrip(String longDescrip) {this.longDescrip = longDescrip;}

    public int getSize() {return size;}
    public void setSize(int size) {this.size = size;}
    public void setSize(String size) {this.size = Integer.parseInt(size);}

    public String getColor() {return color;}
    public void setColor(String color) {this.color = color;}

    public String getStyle() {return style;}
    public void setStyle(String style) {this.style = style;}

    public int getWeight() {return weight;}
    public void setWeight(int weight) {this.weight = weight;}
    public void setWeight(String weight) {this.weight = Integer.parseInt(weight);}

    public BigDecimal getPrice() {return price;}
    public void setPrice(BigDecimal price) {this.price = price;}
    public void setPrice(String price) {this.price = new BigDecimal(price);}

    public BigDecimal getPrevPrice() {return prevPrice;}
    public void setPrevPrice(BigDecimal prevPrice) {this.prevPrice = prevPrice;}
    public void setPrevPrice(String prevPrice) {this.prevPrice = new BigDecimal(prevPrice);}

    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}
    public void setStock(String stock) {this.stock = Integer.parseInt(stock);}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setQuantity(String quantity) {this.quantity = Integer.parseInt(quantity);}

    public Date getArrived() {return arrived;}
    public void setArrived(Date arrived) {this.arrived = arrived;}
    public void setArrived(String arrived) {this.arrived = Date.valueOf(arrived);}

    public Date getPlaced() {return placed;}
    public void setPlaced(Date placed) {this.placed = placed;}
    public void setPlaced(String placed) {this.placed = Date.valueOf(placed);}

    public String getPath() {return path;}
    public void setPath(String path) {this.path = path;}
    
    
    
    
    
    public ProductBean(){}
    public ProductBean(SupplierBean supplier, String productID){
        this.supplier = supplier;
        this.productID = productID;
    }
    
    
    public String getKey(){
        return supplier.getSupplierID()+productID;
    }
    
    
    public void fillProuductsInfo(Object productInfo) throws SQLException{
        ProductBean product = productRRMedia(productInfo);
        shortDescrip = product.getShortDescrip();
        longDescrip = product.getLongDescrip();
        size = product.getSize();
        color = product.getColor();
        style = product.getStyle();
        weight = product.getWeight();
        price = product.getPrice();
        quantity = product.getQuantity();
        arrived = product.getArrived();
    }
    
    public JSONObject getJson(){
        JSONObject jObject = new JSONObject();
        jObject.put("supplierID", supplier.getSupplierID());
        jObject.put("productID", productID);
        jObject.put("domain", domain);
        jObject.put("kingdom", kingdom);
        jObject.put("phylum", phylum);
        jObject.put("klass", klass);
        jObject.put("orderr", orderr);
        jObject.put("family", family);
        jObject.put("genus", genus);
        jObject.put("species", species);
        jObject.put("shortDescrip", shortDescrip);
        jObject.put("longDescrip", longDescrip);
        jObject.put("size", size);
        jObject.put("weight", weight);
        jObject.put("prevPrice", prevPrice);
        jObject.put("price", price);
        jObject.put("stock", stock);
        jObject.put("quantity", quantity);
        jObject.put("arrived", arrived);
        jObject.put("placed", placed);
        jObject.put("path", path);
        return jObject;
    }
    
    

    public String getSelectOneSql() {
        DSLContext create = DSL.using(SQLDialect.SQLITE);
        String s = create.selectFrom(PRODUCTS)
                         .where(PRODUCTS.SUPPLIERID.eq(supplier.getSupplierID()))
                         .and(PRODUCTS.PRODUCTID.eq(productID))
                         .getSQL(ParamType.INLINED);
        return s;
    }
    
}





