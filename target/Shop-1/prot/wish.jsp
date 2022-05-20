<%-- 
    Document   : wish-page
    Created on : Apr 3, 2020, 12:06:03 PM
    Author     : Saif
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Wish Info</title>
        <%@include file="/prevented/srclink/srclink-seg-global.jspf" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/prevented/tabs/tabs-style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/prevented/wish/wish-style.css">
        <script src="${pageContext.request.contextPath}/prevented/wish/wish-action.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="/prevented/header/header-seg.jsp" />
            <main>
                <article>
                    <%@include file="/prevented/tabs/tabs-seg.jspf" %>
                    
                    
                    <section title="detail">
                        <ul class="row">
                            <c:forEach items="${applicationScope.operations.wishProducts}" var="entry">
                            <c:set var="aProduct" value="${entry.value}" />
                            <li>
                                <div class="inblock"><img alt="a product image"></div>
                                <div class="inblock">
                                    <ul class="dataCon spec middle">
                                        <li title="productId" data-d="${aProduct.productID}">Product id: ${aProduct.productID}</li>
                                        <li title="mfrId" data-d="${aProduct.supplier.supplierID}">Mfr id: ${aProduct.supplier.supplierID}</li>
                                        <li title="category" data-d="${aProduct.domain}">Category: ${aProduct.domain}</li>
                                        <li title="description" data-d="${aProduct.shortDescrip}">Description: ${aProduct.shortDescrip}</li>
                                        <li title="price" data-d="${aProduct.prevPrice}">Price: ${aProduct.prevPrice}</li>
                                        <li title="quantity" data-d="${aProduct.quantity}">Quantity: ${aProduct.quantity}</li>
                                    </ul>
                                </div>
                                <div class="inblock" title="action">
                                    <div>Wish</div>
                                    <div>Delete</div>
                                </div>
                            </li>
                            </c:forEach>
                        </ul>
                    </section>
                    
                    
                </article>
            </main>
            
            <%@include file="/prevented/footer/footer-seg.jspf" %>
        </div>
    </body>
</html>
