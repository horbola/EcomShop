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
        <link rel="stylesheet" href="../../css/NavStyle.css" />
    </head>
    <body>
        <h1>Hello World! Profile Page</h1>
        <%@include file="/WEB-INF/jspf/NavBar.jspf" %>
        
        <br />
        <a href="<c:out value='${pageContext.request.contextPath}${initParam.profileEditPage}' />">Edit Profile</a> <br />
        
    </body>
        
</html>
