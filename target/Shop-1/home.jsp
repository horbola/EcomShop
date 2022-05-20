<%-- 
    Document   : Products-Home
    Created on : Mar 20, 2020, 9:34:10 AM
    Author     : Saif
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="st" uri="Shop-Tags" %>

<%  List list = new ArrayList<Integer>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(5);
    list.add(6);
    list.add(7);
    pageContext.setAttribute("list", list);
%>



<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products Home</title>
        <%@include file="/prevented/srclink/srclink-seg-global.jspf" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/home/home-style.css">
        <script src="${pageContext.request.contextPath}/home/home-action.js"></script>
        
<!--        <meta name="laptops" content="${applicationScope.appStore.getJson('laptop')}">
        <meta name="mobiles" content="${applicationScope.appStore.getJson('mobile')}">
        <meta name="watches" content="${applicationScope.appStore.getJson('watch')}">-->
    </head>
    <body>
        <div class="container">
            <jsp:include page="/prevented/header/header-seg.jsp" />
            <main>
                
                <article class="cate1">
                    <section class="items">
                        <ul>
                            <li class="active">cate one</li>
                            <li>item 2</li>
                            <li>item 3</li>
                            <li>item 4</li>
                            <li>item 4</li>
                            <li>item 4</li>
                            <li>item 4</li>
                            <li>item 4</li>
                            <li>item 4</li>
                        </ul>
                    </section>
                    <section class="products">
                        <ul>
                            <c:forEach items="${applicationScope.appStore.getProductsAsCategory('laptop')}" var="entry">
                            <c:set var="product" value="${entry.value}" />
                            <li class="li" data-supplierID="${product.supplier.supplierID}" data-productId="${product.productID}">
                                <c:set var="imgUrl" value="${pageContext.request.contextPath}${product.path}" />
                                <div class="img"><a href="#"><img alt="" src="${imgUrl}"></a></div>
                                <div class="info">
                                    <a href="#">${product.shortDescrip}</a>
                                    <div class="price">
                                        <span class="st">Price:</span><strong>${product.price}</strong>
                                        <span class="buy">Buy</span>
                                    </div>
                                    <div class="actions">
                                        <span class="wish">Wish</span>
                                        <span class="cart">Cart</span>
                                    </div>
                                </div>
                            </li>
                            </c:forEach>
                        </ul>
                        
                    </section>
                </article>

                <article class="cate2">
                    <section class="items">
                        <ul>
                            <li class="active">cate two</li>
                            <li>item 2</li>
                            <li>item 3</li>
                            <li>item 4</li>
                            <li>item 4</li>
                            <li>item 4</li>
                            <li>item 4</li>
                            <li>item 4</li>
                            <li>item 4</li>
                        </ul>
                    </section>
                    <section class="products">
                        <ul>
                            <c:forEach items="${applicationScope.appStore.getProductsAsCategory('mobile')}" var="entry">
                            <c:set var="product" value="${entry.value}" />
                            <li class="li" data-supplierID="${product.supplier.supplierID}" data-productId="${product.productID}">
                                <c:set var="imgUrl" value="${pageContext.request.contextPath}${product.path}" />
                                <div class="img"><a href="#"><img alt="" src="${imgUrl}"></a></div>
                                <div class="info">
                                    <a href="#">${product.shortDescrip}</a>
                                    <div class="price">
                                        <span class="st">Price:</span><strong>${product.price}</strong>
                                        <span class="buy">Buy</span>
                                    </div>
                                    <div class="actions">
                                        <span class="wish">Wish</span>
                                        <span class="cart">Cart</span>
                                    </div>
                                </div>
                            </li>
                            </c:forEach>
                        </ul>
                        
                    </section>
                </article>

                <article class="cate3">
                    <section class="items">
                        <ul>
                            <li class="active">cate three</li>
                            <li>item 2</li>
                            <li>item 3</li>
                            <li>item 4</li>
                            <li>item 4</li>
                            <li>item 4</li>
                            <li>item 4</li>
                            <li>item 4</li>
                            <li>item 4</li>
                        </ul>
                    </section>
                    <section class="products">
                        <ul>
                            <c:forEach items="${applicationScope.appStore.getProductsAsCategory('watch')}" var="entry">
                            <c:set var="product" value="${entry.value}" />
                            <li class="li" data-supplierID="${product.supplier.supplierID}" data-productId="${product.productID}">
                                <c:set var="imgUrl" value="${pageContext.request.contextPath}${product.path}" />
                                <div class="img"><a href="#"><img alt="" src="${imgUrl}"></a></div>
                                <div class="info">
                                    <a href="#">${product.shortDescrip}</a>
                                    <div class="price">
                                        <span class="st">Price:</span><strong>${product.price}</strong>
                                        <span class="buy">Buy</span>
                                    </div>
                                    <div class="actions">
                                        <span class="wish">Wish</span>
                                        <span class="cart">Cart</span>
                                    </div>
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
