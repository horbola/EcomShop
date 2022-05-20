<%-- 
    Document   : Products-Home
    Created on : Mar 20, 2020, 9:34:10 AM
    Author     : Saif
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="st" uri="Shop-Tags" %>

<c:set var="aProduct" value="${operations.oneProduct}" scope="page" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products Details</title>
        <%@include file="/prevented/srclink/srclink-seg-global.jspf" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/prevented/detail/detail-style.css">
        <script src="${pageContext.request.contextPath}/prevented/detail/detail-action.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="/prevented/header/header-seg.jsp" />
            <main>
                <article>
                    <section class="preview">
                        <c:set var="imgUrl" value="${pageContext.request.contextPath}${aProduct.path}" />
                        <div><img alt="" src="${imgUrl}"></div>
                        <div>
                            <ul>
                                <li><span>Mfr Id : </span>          <span title="mId">${aProduct.supplier.supplierID}</span></li>
                                <li><span>Product Id : </span>      <span title="pId">${aProduct.productID}</span></li>
                                <li><span>Description : </span>     <span title="des">${aProduct.shortDescrip}</span></li>
                                <li><span>Category : </span>        <span title="cat">${aProduct.domain}</span></li>
                                <li><span>Price : </span>           <span title="pri">${aProduct.price}</span></li>
                                <li><span>Quantity : </span>        <span title="qua">${aProduct.stock}</span></li>
                            </ul>
                        </div>  
                    </section>
                    <section class="config">
                        <div>
                            <span>Color</span>
                            <ul>
                                <li>White</li>
                                <li>Black</li>
                                <li>Red</li>
                                <li>Green</li>
                            </ul>
                        </div>
                        <div>
                            <span>Style</span>
                            <ul>
                                <li>Style 1</li>
                                <li>Style 2</li>
                                <li>Style 3</li>
                                <li>Style 4</li>
                            </ul>
                        </div>
                        <div>
                            <span>Size</span>
                            <ul>
                                <li>Small</li>
                                <li>Medium</li>
                                <li>Large</li>
                            </ul>
                        </div>
                    </section>
                </article>
                
                <article class="actions">
                    <span class="buyD">Buy Now</span>
                    <div>
                        <span class="wishD">Wish</span>
                        <span class="cartD">Cart</span>
                    </div>
                    <span>Continue Shopping</span>
                </article>
            </main>
            <%@include file="/prevented/footer/footer-seg.jspf" %>
        </div>
    </body>
</html>
