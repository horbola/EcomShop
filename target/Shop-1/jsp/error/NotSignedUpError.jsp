<%-- 
    Document   : NotSignedError
    Created on : Dec 22, 2019, 7:33:45 PM
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
        <h1>Hello World! notSignedUpErrorPage</h1>
        Message: <c:out value='${param.notSignedUp}' /> <br />
        <a href="<c:url value='${initParam.signupPage}' />">Sign Up here</a>
        
    </body>
</html>
