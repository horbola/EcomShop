<%-- 
    Document   : Orders
    Created on : Dec 22, 2019, 10:41:22 AM
    Author     : Saif
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World! Orders Page</h1>
        
        <a href="<c:out value='${pageContext.request.contextPath}${initParam.productsPage}' />">Products</a> <br />
        <a href="<c:out value='${pageContext.request.contextPath}${initParam.makeOrder}' />">Make Order. Invoked from Cart</a> <br />
        <a href="<c:out value='${pageContext.request.contextPath}${initParam.loginPage}' />">Login</a> <br />
        <a href="<c:out value='${pageContext.request.contextPath}${initParam.signupPage}' />">Sign Up</a> <br />
        <a href="<c:out value='${pageContext.request.contextPath}${initParam.showOrders}' />">Orders</a> <br />
        <a href="<c:out value='${pageContext.request.contextPath}${initParam.profilePage}' />">Profile</a> <br />
    </body>
</html>
