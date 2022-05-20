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
    </head>
    <body>
        <h1>Hello World! ProfileEdit Page</h1>
        <%@include file="/WEB-INF/jspf/NavBar.jspf" %>
        <!--here ${pageContext.request.contextPath} need not to be specified but does in case of jsp path.-->
        <a href="<c:url value='${initParam.updateProfile}' />">Submit</a>
    </body>
</html>
