<%-- 
    Document   : cart-page
    Created on : Mar 30, 2020, 11:52:21 AM
    Author     : Saif
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="st" uri="Shop-Tags" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile Info</title>
        <%@include file="/prevented/srclink/srclink-seg-global.jspf" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/prevented/tabs/tabs-style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/prevented/profile/profile-style.css">
        <script src="${pageContext.request.contextPath}/prevented/profile/profile-action.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="/prevented/header/header-seg.jsp" />
            <main>
                <article>
                    <%@include file="/prevented/tabs/tabs-seg.jspf" %>
                    
                    
                    <st:updateProfile />
                    <section title="detail">
                        <div title="accInfo">
                            <p>Account Information</p>
                            <p title="Name"> Name: ${sessionScope.customerX.name}</p>
                            <p title="userName">User ID: ${sessionScope.customerX.customerID}</p>
                        </div>
                        <div title="addr">
                            <p>Default Shipping Address</p>
                            <p>Default Billing Address</p>
                        </div>	
                        <div>
                            <button title="editPro" type="button" onclick="window.location = 'http://localhost:8080/Shop/prevented/profile/profile-edit-page.jsp'">Edit Profile</button>
                        </div>
                    </section>
                    
                    
                </article>
            </main>
            
            <%@include file="/prevented/footer/footer-seg.jspf" %>
        </div>
    </body>
</html>
