<%-- 
    Document   : OrderFeedback
    Created on : Jan 4, 2020, 2:35:01 PM
    Author     : Saif
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page </title>
    </head>
    <body>
        <h1>Hello World! OrderFeedback Page</h1>
        <table>
            <c:forEach items="${orderFeedback}" var = "aProduct">
                <tr>
                    <td>${aProduct.get("orderId")}</td>
                    <td>${aProduct.get("productId")}</td>
                    <td>${aProduct.get("mfrId")}</td>
                    <td>${aProduct.get("quantity")}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
