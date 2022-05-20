<%-- 
    Document   : ProductsEntry
    Created on : Jan 6, 2020, 8:14:42 AM
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
        <h1>Products Entry Page</h1>
        <c:if test="${!empty requestScope.productFeedback}">
            <table class="enteredProducts">
                <tr>
                    <td>Products Id: </td><td>${requestScope.productFeedback.productId}</td>
                </tr>
                <tr>
                    <td>Mfr Id: </td><td>${requestScope.productFeedback.mfrId}</td>
                </tr>
                <tr>
                    <td>Description: </td><td><td>${requestScope.productFeedback.description}</td>
                </tr>
                <tr>
                    <td>Price: </td><td>${requestScope.productFeedback.price}</td>
                </tr>
                <tr>
                    <td>Quantity: </td><td>${requestScope.productFeedback.quantity}</td>
                </tr>
                <tr>
                    <td><button type="submit" value="submit">Submit</button></td>
                    <td><button type="reset" value="reset">Reset</button></td>
                </tr>
            </table>
        </c:if>
        
        <form class="entryProducts" action="${pageContext.request.contextPath}${initParam.productsEntry}">
            <table>
                <tr>
                    <td>Products Id: </td><td><input type="text" name="productId" /></td>
                </tr>
                <tr>
                    <td>Mfr Id: </td><td><input type="text" name="mfrId" /></td>
                </tr>
                <tr>
                    <td>Description: </td><td><input type="text" name="description" /></td>
                </tr>
                <tr>
                    <td>Price: </td><td><input type="text" name="price" /></td>
                </tr>
                <tr>
                    <td>Quantity: </td><td><input type="text" name="quantity" /></td>
                </tr>
                <tr>
                    <td><button type="submit" value="submit">Submit</button></td>
                    <td><button type="reset" value="reset">Reset</button></td>
                </tr>
            </table>
        </form>
    </body>
</html>
