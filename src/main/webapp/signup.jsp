<%-- 
    Document   : Login
    Created on : Jan 21, 2020, 7:12:01 PM
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/signup/signup-style.css" />
    </head>
    <body>
        <h1>Sign Up Form</h1>
        
        <c:if test="${!empty param.notSignedUp}">
            <h3>Sign Up Required: ${param.notSignedUp}</h3>
        </c:if>
        <c:if test="${!empty param.couldNotSignUp}">
            <h3>Sign Up Again: ${param.couldNotSignUp}</h3>
        </c:if>
    
        <form action="${pageContext.request.contextPath}/Signup" method="POST">
            <figure class="imgcontainer">
                <img src="${pageContext.request.contextPath}/image/img_avatar2.png" alt="Avatar" class="avatar" />
            </figure>
            <section>
                <article class="container">
                    <label for="customerID">User Name</label>
                    <input type="text" name="customerID" placeholder="Enter User Name" required="required" />

                    <label for="password">Password</label>
                    <input type="password" name="password" placeholder="Enter Password" required="required" />
                    
                    <label for="name">Name</label>
                    <input type="text" name="name" placeholder="Enter Your Full Name" required="required" />
                    
                    <button type="submit">Sign Up</button>
                </article>
                <article class="container" style="background-color:#f1f1f1">
                    <button type="button" class="cancelbtn both">Cancel</button>
                    <button type="reset" class="resetbtn both">Reset</button>
                    <span class="pass">Forgot <a href="#">Password?</a></span>
                </article>
            </section>
            <c:if test="${!empty param.origUrl}">
                <input type="hidden" name="origUrl" value="${fn:escapeXml(param.origUrl)}">
            </c:if>
        </form>
    </body>
</html>
