<%-- 
    Document   : LoginRequired
    Created on : Dec 22, 2019, 8:44:52 PM
    Author     : Saif
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World! LoginRequired Page</h1>
        <a href="<c:out value='${pageContext.request.contextPath}${initParam.loginPage}' />">Log in here</a>
        <h5>Original url is: ${param.origUrl}</h5>
    </body>
</html>
