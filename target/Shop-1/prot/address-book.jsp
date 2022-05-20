<%-- 
    Document   : addressBook-page
    Created on : Apr 10, 2020, 8:53:33 AM
    Author     : Saif
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Info</title>
        <%@include file="/prevented/srclink/srclink-seg-global.jspf" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/prevented/tabs/tabs-style.css">
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/prevented/addressBook/addressBook-style.css">
        <script src="${pageContext.request.contextPath}/prevented/addressBook/addressBook-action.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="/prevented/header/header-seg.jsp" />
            <main>
                <article>
                    <%@include file="/prevented/tabs/tabs-seg.jspf" %>
                    
                    
                    <section title="detail">
                        <table border="1">
                            <caption>All of your addresses</caption>
                            <thead>
                                <tr>
                                    <th>sl.</th>
                                    <th>Flat</th>
                                    <th>House</th>
                                    <th>Road</th>
                                    <th>Area</th>
                                    <th>Thana</th>
                                    <th>District</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="address" items="${applicationScope.operations.addressBook}">
                                <tr>
                                    <td>${address.addressType}</td>
                                    <td>${address.flat}</td>
                                    <td>${address.house}</td>
                                    <td>${address.road}</td>
                                    <td>${address.area}</td>
                                    <td>${address.thana}</td>
                                    <td>${address.district}</td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </section>
                    
                    
                </article>
            </main>
            
            <%@include file="/prevented/footer/footer-seg.jspf" %>
        </div>
    </body>
</html>

