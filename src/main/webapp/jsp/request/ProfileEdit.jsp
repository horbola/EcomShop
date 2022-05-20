<%-- 
    Document   : ProfileEdit
    Created on : Dec 22, 2019, 10:43:05 AM
    Author     : Saif
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/NavStyle.css" />
    </head>
    <body>
        <h1>Hello World! ProfileEdit Page</h1>
        <%@include file="/WEB-INF/jspf/NavBar.jspf" %>
        <!--here ${pageContext.request.contextPath} need not to be specified but does in case of jsp path.-->
        <!--<a href="<c:url value='${initParam.updateProfile}' />">Submit</a>-->
        <form action="<c:url value='${initParam.updateProfile}' />" method="POST">
            User name: <input type="text" name="uName" value="${sessionScope.customer.userName}"><br>
            password : <input type="password" name="pass" value="${sessionScope.customer.pass}"><br>
            name: <input type="text" name="name" value="${sessionScope.customer.name}"><br>
            <button type="submit" value="submit">Submit</button>
            <button type="reset" value="reset">Reset</button>
        </form>
    </body>
</html>
