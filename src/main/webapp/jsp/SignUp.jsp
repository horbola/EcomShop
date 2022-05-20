<%-- 
    Document   : SignUp
    Created on : Dec 27, 2019, 4:26:58 PM
    Author     : Saif
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/NavStyle.css" />
    </head>
    <body>
        <h1>SignUp Page</h1>
        
        <%@include file="/WEB-INF/jspf/NavBar.jspf" %>
        
        <c:if test="${!empty param.notSignedUp}">
            <h3>Sign Up Required: ${param.notSignedUp}</h3>
        </c:if>
        <c:if test="${!empty param.couldNotSignUp}">
            <h3>Sign Up Again: ${param.couldNotSignUp}</h3>
        </c:if>
        
        <form action="${pageContext.request.contextPath}${initParam.makeCustomer}" method="POST">
            User name: <input type="text" name="uName"><br>
            password : <input type="password" name="pass"><br>
            name: <input type="text" name="name"><br>
            <button type="submit" value="submit">Submit</button>
            <button type="reset" value="reset">Reset</button>
            <c:if test="${!empty param.origUrl}">
                <input type="hidden" name="origUrl" value="${fn:escapeXml(param.origUrl)}">
            </c:if>
        </form>
    </body>
</html>
