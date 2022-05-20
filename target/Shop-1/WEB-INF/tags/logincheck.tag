<%-- 
    Document   : logincheck
    Created on : Apr 7, 2020, 2:58:58 PM
    Author     : Saif
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="This tag checks whether the customer is logged in or not and changes the url for button according" pageEncoding="UTF-8"%>


<c:choose>
    <c:when test="${applicationScope.operations.customerAuthenticated}">
        <div class="acc" onclick="window.location = 'http://localhost:8080/Shop/prevented/profile/profile-page.jsp'">Account</div>
    </c:when>
    <c:otherwise>
        <div class="acc" onclick="window.location = 'http://localhost:8080/Shop/login/login-page.jsp'">Login</div>
    </c:otherwise>
</c:choose>
