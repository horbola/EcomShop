/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var laptops, mobiles, watches;

$(function(){
    getData();
    imageDetail();
    buyNow();
    addToWish();
    addToCart();
});


function getData(){
    var data = $('meta[name="laptops"]').attr('content');
    laptops = JSON.parse(decodeURIComponent(data));
    
    data = $('meta[name="mobiles"]').attr('content');
    mobiles = JSON.parse(decodeURIComponent(data));
    
    data = $('meta[name="watches"]').attr('content');
    watches = JSON.parse(decodeURIComponent(data));
}

function imageDetail(){
    $(".img img").click(function(e){
        var $li = $(this).parents('li');
        var qb = new BuildQueryString();
        qb.append("supplierID", $li.attr('data-supplierID'));
        qb.append("productID", $li.attr('data-productId'));
        window.location.href = "http://localhost:8080/Shop/prevented/detail/detail-page.jsp"+qb.getQueryString();
    });
}


function buyNow(){
    $(".buy").click(function(e){
        var $li = $(this).parents('li');
        var qb = new BuildQueryString();
        qb.append("supplierID", $li.attr('data-supplierID'));
        qb.append("productID", $li.attr('data-productId'));
        window.location.href = "http://localhost:8080/Shop/prevented/detail/detail-page.jsp"+qb.getQueryString();
    });
}
    
    

function addToWish(){
    $(".wish").click(function(){
        var $li = $(this).parents('li');
        var aProduct = {};
        aProduct.supplierID = $li.attr('data-supplierID');
        aProduct.productID = $li.attr('data-productId');
        aProduct.quantity = '1';
        aProduct.placed = 'true';
        
        var url = "http://localhost:8080/Shop/AddToWish?";
        $.getJSON(url, $.param(aProduct), function(data, status, xhr){
            if(status === 'success')
                $('.wishCount').text(data.wishCount);
        });
    });
}


function addToCart(){
    $('.cart').click(function(){
        var $li = $(this).parents('li');
        var aProduct = {};
        aProduct.supplierID = $li.attr('data-supplierID');
        aProduct.productID = $li.attr('data-productId');
        aProduct.quantity = '1';
        aProduct.placed = 'true';
        
        var url = 'http://localhost:8080/Shop/AddToCart?';
        $.getJSON(url, $.param(aProduct), function(data, status, xhr){
            if(status === 'success')
                $('.cartCount').text(data.cartCount);
        });
    });
}
