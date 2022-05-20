<%-- 
    Document   : cart-page
    Created on : Mar 30, 2020, 11:52:21 AM
    Author     : Saif
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile Editing</title>
        <%@include file="/prevented/srclink/srclink-seg-global.jspf" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/prevented/tabs/tabs-style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/prevented/profile/profile-edit-style.css">
        <script src="${pageContext.request.contextPath}/prevented/profile/profile-edit-action.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="/prevented/header/header-seg.jsp" />
            <main>
                <article>
                    <%@include file="/prevented/tabs/tabs-seg.jspf" %>
                    
                    
                    <section title="detail">
                        <form action="${pageContext.request.contextPath}/prevented/profile/profile-page.jsp" method="POST">
                            User name: <input type="text" name="customerID" value="${sessionScope.customerX.customerID}"><br>
                            password : <input type="password" name="password" value="${sessionScope.customerX.password}"><br>
                            name: <input type="text" name="name" value="${sessionScope.customerX.name}"><br>
                            <input type="hidden" name="update" value="true">
                            <button type="submit" value="submit">Submit</button>
                            <button type="reset" value="reset">Reset</button>
                        </form>
                    </section>
                    
                    
                </article>
            </main>
            
            <%@include file="/prevented/footer/footer-seg.jspf" %>
        </div>
    </body>
</html>
