<%-- 
    Document   : Profile
    Created on : Dec 22, 2019, 10:42:47 AM
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
        <h1>Hello World! Profile Page</h1>
        <%@include file="/WEB-INF/jspf/NavBar.jspf" %>
        
        <table>
            <tr>
                <td>User Name: </td><td>${sessionScope.customer.userName}</td>
            </tr>
            <tr>
                <td>Password: </td><td>${sessionScope.customer.pass}</td>
            </tr>
            <tr>
                <td>Name: </td><td>${sessionScope.customer.name}</td>
            </tr>
        </table>
        
        <br />
        <a href="<c:out value='${pageContext.request.contextPath}${initParam.profileEditPage}' />">Edit Profile</a> <br />
        
    </body>
        
</html>
