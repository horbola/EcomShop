<%-- 
    Document   : buynow-page
    Created on : Apr 7, 2020, 5:15:07 PM
    Author     : Saif
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




${applicationScope.operations.addToBuyNowCart()}
<c:set var="aProduct" value="${applicationScope.operations.theBuyNowProduct}" scope="page" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Info</title>
        <%@include file="/prevented/srclink/srclink-seg-global.jspf" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/prevented/tabs/tabs-style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/prevented/buynow/buynow-style.css">
        <script src="${pageContext.request.contextPath}/prevented/buynow/buynow-action.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="/prevented/header/header-seg.jsp" />
            <main>
                <article>
                    <%@include file="/prevented/tabs/tabs-seg.jspf" %>
                    
                    
                    <section title="detail">
                        
                        <ul class="row">
                            <li>
                                <div class="inblock"><img alt="a product image"></div>
                                <div class="inblock">
                                    <ul class="dataCon spec middle">
                                        <li title="productId" data-d="${aProduct.productID}">Product id: ${aProduct.productID}</li>
                                        <li title="mfrId" data-d="${aProduct.supplier.supplierID}">Mfr id: ${aProduct.supplier.supplierID}</li>
                                        <li title="category" data-d="${aProduct.domain}">Category: ${aProduct.domain}</li>
                                        <li title="description" data-d="${aProduct.shortDescrip}">Description: ${aProduct.shortDescrip}</li>
                                        <li title="price" data-d="${aProduct.prevPrice}">Price: ${aProduct.prevPrice}</li>
                                        <li title="quantity" data-d="${aProduct.quantity}">Quantity: ${aProduct.quantity}</li>
                                    </ul>
                                </div>
                                <div class="inblock" title="action">
                                    <div>Wish</div>
                                    <div>Delete</div>
                                </div>
                            </li>
                        </ul>
                        <br />
                        
                        <div>
                            <ul class="options middle">
                                <li>Option 1</li>
                                <li>Option 2</li>
                                <li>Option 3</li>
                            </ul>
                        </div>
                        <br />
                        
                        
                        <div title="addr">
                            <form name="shipAddr">
                                <label>Flat: 
                                <input type="text" name="flat" value="5/B" />
                                </label><br />
                                
                                <label>House: 
                                <input type="text" name="house" value="12" />
                                </label><br />
                                
                                <label>Road: 
                                <input type="text" name="road" value="15" />
                                </label><br />
                                
                                <label>Area: 
                                <input type="text" name="area" value="khilket" />
                                </label><br />
                                
                                <label>Thana: 
                                <input type="text" name="thana" value="nikunja" />
                                </label><br />
                                
                                <label>District: 
                                <input type="text" name="district" value="Dhaka" />
                                </label>
                            </form>
                        </div>
                        
                        <div title="addr">
                            <form name="billAddr">
                                <label>Flat: 
                                <input type="text" name="flat" value="3/a" />
                                </label><br />
                                
                                <label>House: 
                                <input type="text" name="house" value="12" />
                                </label><br />
                                
                                <label>Road: 
                                <input type="text" name="road" value="15" />
                                </label><br />
                                
                                <label>Area: 
                                <input type="text" name="area" value="khilket" />
                                </label><br />
                                
                                <label>Thana: 
                                <input type="text" name="thana" value="nikunja" />
                                </label><br />
                                
                                <label>District: 
                                <input type="text" name="district" value="Dhaka" />
                                </label>
                            </form>
                        </div>
                        
                        
                        <div class="btnB">
                            <button type="button">Make Order</button>
                        </div>
                    </section>
                    
                    
                </article>
            </main>
            
            <%@include file="/prevented/footer/footer-seg.jspf" %>
        </div>
    </body>
</html>
