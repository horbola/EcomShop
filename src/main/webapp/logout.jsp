<%-- 
    Document   : logout
    Created on : Apr 7, 2020, 12:25:58 PM
    Author     : Saif
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logout</title>
        <script src="${pageContext.request.contextPath}/prevented/logout/logout-action.js"></script>
    </head>
    <body>
        <h1>Logging Out</h1>
        ${pageContext.session.invalidate()}
    </body>
</html>
