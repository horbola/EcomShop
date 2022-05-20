<%-- 
    Document   : ConfirmOrder
    Created on : Dec 22, 2019, 10:41:46 AM
    Author     : Saif
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/NavStyle.css" />
    </head>
    <body>
        <h1>Hello World! ConfirmOrder Page</h1>
        <%@include file="/WEB-INF/jspf/NavBar.jspf" %>
        <h1>${requestScope.cartJson}</h1>
        <h1>This is parsed: ${requestScope.parsedJson}</h1>
    </body>
</html>
