<%-- 
    Document   : Review Order
    Created on : Jan 6, 2020, 3:47:24 PM
    Author     : Saif
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="${pageContext.request.contextPath}/js/ReviewOrder.js"></script>
    </head>
    <body>
        <h1>Hello World! ReviewOrder Page</h1>
        <c:forEach items="${requestScope.customersSet}" var="aCustomer">
            <div>
                <h3>${aCustomer.getKey()}</h3>
                <c:forEach items="${aCustomer.getValue()}" var="order">
                    <table>
                        <caption>
                            <span>${order.getOrderId()}   </span>
                            <span>${order.getStatus()}</span>
                        </caption>
                        <c:forEach items="${order.getProducts()}" var="product">
                            <tr>
                                <td>${product.productId}</td>
                                <td>${product.mfrId}</td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td><button type="button" class="btn">Approve</button></td>
                            <td><button type="button" class="btn">Execute</button></td>
                        </tr>
                    </table>
                </c:forEach>
            </div>
        </c:forEach>
    </body>
</html>
