<%-- any content can be specified here e.g.: --%>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="st" uri="Shop-Tags" %>

<header>
    <nav>
        <span title="logo" onclick="window.location = 'http://localhost:8080/Shop/home/home-page.jsp'">LOGO</span>
        <div>
            <span><a href="http://localhost:8080/Shop/prevented/wish/wish-page.jsp">Wish</a></span>
            <st:wcount />
        </div>
        <div>
            <span><a href="http://localhost:8080/Shop/prevented/cart/cart-page.jsp">Cart</a></span>
            <st:ccount />
        </div>
        <st:logincheck />
    </nav>
    

    <section class="buzz-words">
        <div>Simple</div>
        <div>Secured</div>
        <div>Fast</div>
        <div>Fashion</div>
        <div>Everything</div>
    </section>
    <section class="index">
        <div>Category</div>
        <div>Filter</div>
        <div>Free</div>
        <div>
            <form class="searchProd" action="#" method="get" onsubmit="return false">
                <input type="text" onfocus="if(this.value = 'Search..') this.value = ''" onblur="if(this.value = '') this.value = 'Search..'" value="Search.." name="q" />
                <input type='submit' value="search" />
            </form>
        </div>
    </section>
</header>
