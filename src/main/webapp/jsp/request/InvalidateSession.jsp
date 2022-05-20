<%-- 
    Document   : InvalidateSession
    Created on : Jan 1, 2020, 1:00:43 PM
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
        <h1>Hello World! Invalidate Session Page</h1>
        ${pageContext.session.invalidate()}
        <h4>Session is invalidated</h4>
    </body>
</html>
