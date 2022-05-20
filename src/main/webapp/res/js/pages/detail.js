/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




$(function(){
    $('.wishD').click(addToWish);
    $('.cartD').click(addToCart);
    $('.buyD').click(buyNow);
});

function addToWish(){
    var wishForm = new Form("http://localhost:8080/Shop/AddToWish", "POST");
    wishForm.append('supplierID', $("span[title=mId]").text());
    wishForm.append('productID', $("span[title=pId]").text());
    wishForm.append('quantity', $("span[title=qua]").text());
    wishForm.append('placed', 'true');
    wishForm.append('forward', 'true');
    wishForm.submit();
}

function addToCart(){
    var cartForm = new Form("http://localhost:8080/Shop/AddToCart", "POST");
    cartForm.append('supplierID', $("span[title=mId]").text());
    cartForm.append('productID', $("span[title=pId]").text());
    cartForm.append('quantity', $("span[title=qua]").text());
    cartForm.append('placed', 'true');
    cartForm.append('forward', 'true');
    cartForm.submit();
}

function buyNow(){
    let buyNowForm = new Form("http://localhost:8080/Shop/prevented/buynow/buynow-page.jsp", "GET");
    buyNowForm.append('supplierID', document.querySelectorAll("span[title=mId]")[0].innerHTML);
    buyNowForm.append('productID', document.querySelectorAll("span[title=pId]")[0].innerHTML);
    buyNowForm.append('quantity', document.querySelectorAll("span[title=qua]")[0].innerHTML);
    buyNowForm.append('placed', 'true');
    buyNowForm.submit();
}