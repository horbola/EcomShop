<%-- 
    Document   : Products
    Created on : Dec 22, 2019, 10:37:13 AM
    Author     : Saif
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!--Here i need to use context-relative path to get the css file dynamically forwarded from servlet.-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/NavStyle.css" />
        <script src="${pageContext.request.contextPath}/js/Cart.js"></script>
    </head>
    <body>
        <h1>Hello World! Products Page</h1>
        <!-- If a path starts with a slash then it's a context-relative path, otherwise it's a page-relative path.
        Page relative path starts with the directory off the context-root directory. In this app's case the
        context-relative path of this page is "/shop/jsp/products.jsp" and the page-relative path is "jsp/products.jsp".
        But the prefix "jsp" in this case is included by engine by defallt. So we can use only "products.jsp".-->
        <%@include file="/WEB-INF/jspf/NavBar.jspf" %> <br />
        <!--here ${pageContext.request.contextPath} needs to be specified but not in case of servlet path.-->
        <!--<a href="<c:url value='${initParam.productsPage}' />">Products</a>-->
        
        <table>
            <c:forEach items="${requestScope.products}" var="row">
                <tr data-productId="${row.get("productId")}" data-mfrId="${row.get("mfrId")}" data-quantity="${row.get("quantity")}">
                    <td>${row.get("description")}</td>
                    <td>${row.get("price")}</td>
                    <td class="addToCart">Add To Cart</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>


