/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saif.web.practice.shop.beans;

import com.saif.web.practice.shop.core.AddressType;
import com.saif.web.practice.shop.core.Shop;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Saif
 */
public class Bean implements Shop {
    
    protected ProductBean productRRMedia(Object obj){
        ProductBean product = new ProductBean();
        if(obj instanceof HttpServletRequest){
            HttpServletRequest reqeust = (HttpServletRequest) obj;
            productRequestEnd(reqeust, product);
            return product;
        }
        else if(obj instanceof ResultSet){
            ResultSet rs = (ResultSet) obj;
            productRsEnd(rs, product);
            return product;
        }
        else return null;
    }
    
    private void productRequestEnd(HttpServletRequest request, ProductBean product){
        String supplierID = request.getParameter("supplierID");
        if(supplierID != null){
            if(!(supplierID.equalsIgnoreCase("null") || supplierID.equals("")))
                product.setSupplier(new SupplierBean(supplierID));
        }
        
        String productID = request.getParameter("productID");
        if(productID != null){
            if(!(productID.equalsIgnoreCase("null") || productID.equals("")))
                product.setProductID(productID);
        }

        String domain = request.getParameter("domain");
        if(domain != null){
            if(!(domain.equalsIgnoreCase("null") || domain.equals("")))
                product.setDomain(domain);
        }

        String kingdom = request.getParameter("kingdom");
        if(kingdom != null){
            if(!(kingdom.equalsIgnoreCase("null") || kingdom.equals("")))
                product.setKingdom(kingdom);
        }

        String phylum = request.getParameter("phylum");
        if(phylum != null){
            if(!(phylum.equalsIgnoreCase("null") || phylum.equals("")))
                product.setPhylum(phylum);
        }

        String klass = request.getParameter("klass");
        if(klass != null){
            if(!(klass.equalsIgnoreCase("null") || klass.equals("")))
                product.setKlass(klass);    
        }

        String orderr = request.getParameter("orderr");
        if(orderr != null){
            if(!(orderr.equalsIgnoreCase("null") || orderr.equals("")))
                product.setOrderr(orderr);
        }

        String family = request.getParameter("family");
        if(family != null){
            if(!(family.equalsIgnoreCase("null") || family.equals("")))
                product.setFamily(family);
        }

        String genus = request.getParameter("genus");
        if(genus != null){
            if(!(genus.equalsIgnoreCase("null") || genus.equals("")))
                product.setGenus(genus);
        }

        String species = request.getParameter("species");
        if(species != null){
            if(!(species.equalsIgnoreCase("null") || species.equals("")))
                product.setSpecies(species);
        }

        String shortDescrip = request.getParameter("shortDescrip");
        if(shortDescrip != null){
            if(!(shortDescrip.equalsIgnoreCase("null") || shortDescrip.equals("")))
                product.setShortDescrip(shortDescrip);
        }

        String longDescrip = request.getParameter("longDescrip");
        if(longDescrip != null){
            if(!(longDescrip.equalsIgnoreCase("null") || longDescrip.equals("")))
                product.setLongDescrip(longDescrip);
        }

        String size = request.getParameter("size");
        if(size != null){
            if(!(size.equalsIgnoreCase("null") || size.equals("")))
                product.setSize(size);
        }

        String color = request.getParameter("color");
        if(color != null){
            if(!(color.equalsIgnoreCase("null") || color.equals("")))
                product.setColor(color);
        }

        String style = request.getParameter("style");
        if(style != null){
            if(!(style.equalsIgnoreCase("null") || style.equals("")))
                product.setStyle(style);
        }

        String weight = request.getParameter("weight");
        if(weight != null){
            if(!(weight.equalsIgnoreCase("null") || weight.equals("")))
                product.setWeight(weight);
        }

        String prevPrice = request.getParameter("prevPrice");
        if(prevPrice != null){
            if(!(prevPrice.equalsIgnoreCase("null") || prevPrice.equals("")))
                product.setPrevPrice(prevPrice);
        }

        String price = request.getParameter("price");
        if(price != null){
            if(!(price.equalsIgnoreCase("null") || price.equals("")))
                product.setPrice(price);
        }
        
        String stock = request.getParameter("stock");
        if(stock != null){
            if(!(stock.equalsIgnoreCase("null") || stock.equals("")))
                product.setStock(stock);
        }

        String quantity = request.getParameter("quantity");
        if(quantity != null){
            if(!(quantity.equalsIgnoreCase("null") || quantity.equals("")))
                product.setQuantity(quantity);
        }

        //to fill this field we must send a parameter from browser that could be "arrived"
        //or it could be true and with the help of a Boolean this logic can be true and
        //a new date of the placement to Products table for this product can be generated.
        String arrived = request.getParameter("arrived");
        if(arrived != null){
            if(!(arrived.equalsIgnoreCase("null") || arrived.equals("")))
                product.setArrived(new Date(System.currentTimeMillis()));
        }

        //to fill this field we must send a parameter from browser that could be "true"
        //so that with the help a Boolean this logic can be true and a new date of the placement to wish or cart
        //for this product can be generated.
        String placed = request.getParameter("placed");
        Boolean isPlaced = Boolean.valueOf(placed);
        if(isPlaced){
            product.setPlaced(new Date(System.currentTimeMillis()));
        }

        String path = request.getParameter("path");
        if(path != null){
            if(!(path.equalsIgnoreCase("null") || path.equals("")))
                product.setPath(path);
        }
    }

    
    
    
    private void productRsEnd(ResultSet rs, ProductBean product){
        try{
            String supplierID = rs.getString("supplierID");
            if(supplierID != null){
                if(!(supplierID.equalsIgnoreCase("null") || supplierID.equals("")))
                    product.setSupplier(new SupplierBean(supplierID));
            }
        }catch(SQLException e){}
        
        try{
            String productID = rs.getString("productID");
            if(productID != null){
                if(!(productID.equalsIgnoreCase("null") || productID.equals("")))
                    product.setProductID(productID);
            }
        }catch(SQLException e){}
            
        try{
            String domain = rs.getString("domain");
            if(domain != null){
                if(!(domain.equalsIgnoreCase("null") || domain.equals("")))
                    product.setDomain(domain);
            }
        }catch(SQLException e){}
        
        try{
            String kingdom = rs.getString("kingdom");
            if(kingdom != null){
                if(!(kingdom.equalsIgnoreCase("null") || kingdom.equals("")))
                    product.setKingdom(kingdom);
            }
        }catch(SQLException e){}
        
        try{
            String phylum = rs.getString("phylum");
            if(phylum != null){
                if(!(phylum.equalsIgnoreCase("null") || phylum.equals("")))
                    product.setPhylum(phylum);
            }
        }catch(SQLException e){}
        
        try{
            String klass = rs.getString("klass");
            if(klass != null){
                if(!(klass.equalsIgnoreCase("null") || klass.equals("")))
                    product.setKlass(klass);    
            }
        }catch(SQLException e){}
        
        try{
            String orderr = rs.getString("orderr");
            if(orderr != null){
                if(!(orderr.equalsIgnoreCase("null") || orderr.equals("")))
                    product.setOrderr(orderr);
            }
        }catch(SQLException e){}
        
        try{
            String family = rs.getString("family");
            if(family != null){
                if(!(family.equalsIgnoreCase("null") || family.equals("")))
                    product.setFamily(family);
            }
        }catch(SQLException e){}
        
        try{
            String genus = rs.getString("genus");
            if(genus != null){
                if(!(genus.equalsIgnoreCase("null") || genus.equals("")))
                    product.setGenus(genus);
            }
        }catch(SQLException e){}
        
        try{
            String species = rs.getString("species");
            if(species != null){
                if(!(species.equalsIgnoreCase("null") || species.equals("")))
                    product.setSpecies(species);
            }
        }catch(SQLException e){}
        
        try{
            String shortDescrip = rs.getString("shortDescrip");
            if(shortDescrip != null){
                if(!(shortDescrip.equalsIgnoreCase("null") || shortDescrip.equals("")))
                    product.setShortDescrip(shortDescrip);
            }
        }catch(SQLException e){}
        
        try{
            String longDescrip = rs.getString("longDescrip");
            if(longDescrip != null){
                if(!(longDescrip.equalsIgnoreCase("null") || longDescrip.equals("")))
                    product.setLongDescrip(longDescrip);
            }
        }catch(SQLException e){}
        
        try{
            int size = rs.getInt("size");
            if(size != 0){
                product.setSize(size);
            }
        }catch(SQLException e){}
        
        try{
            String color = rs.getString("color");
            if(color != null){
                if(!(color.equalsIgnoreCase("null") || color.equals("")))
                    product.setColor(color);
            }
        }catch(SQLException e){}
        
        try{
            String style = rs.getString("style");
            if(style != null){
                if(!(style.equalsIgnoreCase("null") || style.equals("")))
                    product.setStyle(style);
            }
        }catch(SQLException e){}
        
        try{
            int weight = rs.getInt("weight");
            if(weight != 0){
                product.setWeight(weight);
            }
        }catch(SQLException e){}
        
        try{
            BigDecimal prevPrice = rs.getBigDecimal("prevPrice");
            if(prevPrice != null){
                product.setPrevPrice(prevPrice);
            }
        }catch(SQLException e){}
        
        try{
            BigDecimal price = rs.getBigDecimal("price");
            if(price != null){
                product.setPrice(price);
            }
        }catch(SQLException e){}
        
        try{
            int stock = rs.getInt("stock");
            if(stock != 0){
                product.setStock(stock);
            }
        }catch(SQLException e){}
        
        try{
            int quantity = rs.getInt("quantity");
            if(quantity != 0){
                product.setQuantity(quantity);
            }
        }catch(SQLException e){}
        
        try{
            Date arrived = rs.getDate("arrived");
            if(arrived != null){
                product.setArrived(arrived);
            }
        }catch(SQLException e){}

        // this way to get the date throws sql exception. i don't understand why.
        // that's why i took the string form of the placed date. then extracted
        // the date in the setter method.
        //
        // try{
        //     Date placed = rs.getDate("placed");
        //     if(placed != null){
        //         product.setPlaced(placed);
        //     }
        // }catch(SQLException e){}
        
        try{
            String placed = rs.getString("placed");
            if(placed != null){
                if(!(placed.equalsIgnoreCase("null") || placed.equals("")))
                    product.setPlaced(placed);
            }
        }catch(SQLException e){}
        
        try{
            String path = rs.getString("path");
            if(path != null){
                if(!(path.equalsIgnoreCase("null") || path.equals("")))
                    product.setPath(path);
            }
        }catch(SQLException e){}
    }
    
    
    protected void addressRRMedia(Object addressInfo, AddressBean address, AddressType type){
        if(addressInfo instanceof HttpServletRequest){
            HttpServletRequest request = (HttpServletRequest) addressInfo;
            if(type == AddressType.SHIPPING) fillRequestShippingAddress(request, address);
            else if(type == AddressType.BILLING) fillRequestBillingAddress(request, address);
        }
        else if(addressInfo instanceof ResultSet){
            ResultSet rs = (ResultSet) addressInfo;
            if(type == AddressType.SHIPPING) fillPrevShippingAddress(rs, address);
            else if(type == AddressType.BILLING) fillPrevBillingAddress(rs, address);
        }
    }
    
    
    
    public void fillRequestShippingAddress(HttpServletRequest request,  AddressBean shippingAddress){
        //if shipping address is null at unloggedin means no address is defined by customer.
        //so new address from request can be formed with a new instance. but if not null means
        //customer saved an address previously. and if request contains address then this address
        //should override the previous address in the previous instance.
        shippingAddress.setAddressType("shipping");
        shippingAddress.setFlat(request.getParameter("flatS"));
        shippingAddress.setHouse(request.getParameter("houseS"));
        shippingAddress.setRoad(request.getParameter("roadS"));
        shippingAddress.setArea(request.getParameter("areaS"));
        shippingAddress.setThana(request.getParameter("thanaS"));
        shippingAddress.setDistrict(request.getParameter("districtS"));
    }
    
    public void fillPrevShippingAddress(ResultSet rs, AddressBean prevShippingAddress) {
        try {
            prevShippingAddress.setAddressType(rs.getString("AddressType"));
            prevShippingAddress.setAddressID(rs.getInt("AddressID"));
            prevShippingAddress.setFlat(rs.getString("flat"));
            prevShippingAddress.setHouse(rs.getString("house"));
            prevShippingAddress.setRoad(rs.getString("road"));
            prevShippingAddress.setArea(rs.getString("area"));
            prevShippingAddress.setThana(rs.getString("thana"));
            prevShippingAddress.setDistrict(rs.getString("district"));
        } catch (SQLException ex) {
            Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fillRequestBillingAddress(HttpServletRequest request, AddressBean billingAddress){
        //if shipping address is null at unloggedin means no address is defined by customer.
        //so new address from request can be formed with a new instance. but if not null means
        //customer saved an address previously. and if request contains address then this address
        //should override the previous address with the previous instance
        billingAddress.setAddressType("billing");
        billingAddress.setFlat(request.getParameter("flatB"));
        billingAddress.setHouse(request.getParameter("houseB"));
        billingAddress.setRoad(request.getParameter("roadB"));
        billingAddress.setArea(request.getParameter("areaB"));
        billingAddress.setThana(request.getParameter("thanaB"));
        billingAddress.setDistrict(request.getParameter("districtB"));
    }
    
    public void fillPrevBillingAddress(ResultSet rs, AddressBean prevBillingAddress) {
        try {
            prevBillingAddress.setAddressType(rs.getString("AddressType"));
            prevBillingAddress.setAddressID(rs.getInt("addressID"));
            prevBillingAddress.setFlat(rs.getString("flat"));
            prevBillingAddress.setHouse(rs.getString("house"));
            prevBillingAddress.setRoad(rs.getString("road"));
            prevBillingAddress.setArea(rs.getString("area"));
            prevBillingAddress.setThana(rs.getString("thana"));
            prevBillingAddress.setDistrict(rs.getString("district"));
        } catch (SQLException ex) {
            Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
