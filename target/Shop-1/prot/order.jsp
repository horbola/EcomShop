<%-- 
    Document   : OrderFeedback
    Created on : Jan 4, 2020, 2:35:01 PM
    Author     : Saif
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Info</title>
        <%@include file="/prevented/srclink/srclink-seg-global.jspf" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/prevented/tabs/tabs-style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/prevented/order/order-style.css">
        <script src="${pageContext.request.contextPath}/prevented/order/order-action.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="/prevented/header/header-seg.jsp" />
            <main>
                <article>
                    <%@include file="/prevented/tabs/tabs-seg.jspf" %>
                    
                    
                    <section title="detail">
                        <table>
                            <caption>Your Orders</caption>
                            <tr>
                                <td>Order Id</td>
                                <td>Product Id</td>
                                <td>Mfr Id</td>
                                <td>Quantity</td>
                            </tr>
                            <c:forEach items="${applicationScope.operations.allOrders}" var = "orderEntry">
                            <c:set var="order" value="${orderEntry.value}" />
                                <c:forEach items="${order.products}" var = "productEntry">
                                <c:set var="product" value="${productEntry.value}" />
                                <tr>
                                    <td>Order Id: ${order.orderID}</td>
                                    <td>Product Id: ${product.productID}</td>
                                    <td>Mfr Id: ${product.supplier.supplierID}</td>
                                    <td>Quantity: ${product.quantity}</td>
                                </tr>
                                </c:forEach>
                            </c:forEach>
                        </table>
                    </section>
                    
                    
                </article>
            </main>
            
            <%@include file="/prevented/footer/footer-seg.jspf" %>
        </div>
    </body>
</html>
