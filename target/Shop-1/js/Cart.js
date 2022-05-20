/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


window.onload = onLoad;
function onLoad(){
    registerCartEvent();
    document.getElementsByClassName("cart")[0].onclick = makeContent;
};

function registerCartEvent(){
    cart = new Cart();
    var addToCart = document.getElementsByClassName("addToCart");
    for(let i = 0; i<addToCart.length; i++){
        addToCart[i].onclick = function(){
            
            var tr = this.parentNode;
            var children = tr.childNodes;
            
            var row = {
                productId     : tr.getAttribute("data-productId"),
                mfrId         : tr.getAttribute("data-mfrId"),
                description   : children[1].innerHTML,
                price         : children[3].innerHTML,
                quantity      : tr.getAttribute("data-quantity")
            };
            
            cart.addToCart(row);
        };
    }
}

function makeContent(){
    document.body = buildBody();
    
    function buildBody(){
        var body = document.createElement("body");
        createNav(body);
        body.appendChild(buildTable());
        addButton(body);
        var bodyText = document.getElementsByTagName('body')[0].innerHTML;
        console.log(bodyText);
        
        return body;
    }
    
    function createNav(body){
        var nav = document.createElement("nav");
        var ul = document.createElement("ul");
        var li = document.createElement("li");
        
        var a, text;
        
        a = document.createElement("a");
        a.setAttribute("href", "/Shop/ShowProducts");
        text = document.createTextNode("Products");
        a.appendChild(text);
        li.appendChild(a);
        
        a = document.createElement("a");
        text = document.createTextNode("Cart");
        a.appendChild(text);
        li.appendChild(a);
        
        a = document.createElement("a");
        a.setAttribute("href", "/Shop/protectet/MakeOrder");
        text = document.createTextNode("Make Order");
        a.appendChild(text);
        li.appendChild(a);
        
        a = document.createElement("a");
        a.setAttribute("href", "/Shop/jsp/Login.jsp");
        text = document.createTextNode("Login");
        a.appendChild(text);
        li.appendChild(a);
        
        a = document.createElement("a");
        a.setAttribute("href", "/Shop/jsp/SignUp.jsp");
        text = document.createTextNode("Sign Up");
        a.appendChild(text);
        li.appendChild(a);
        
        a = document.createElement("a");
        a.setAttribute("href", "/Shop/protectet/ShowOrders");
        text = document.createTextNode("Orders");
        a.appendChild(text);
        li.appendChild(a);
        
        a = document.createElement("a");
        a.setAttribute("href", "/Shop/jsp/protected/Profile.jsp");
        text = document.createTextNode("Profile");
        a.appendChild(text);
        li.appendChild(a);
        
        body.appendChild(nav.appendChild(ul.appendChild(li)));
    }
    
    function buildTable() {
        var table = document.createElement('table');
        
        for(let i = 0; i<cart.cart.length; i++){
            var aProduct = cart.getFromCart(i);
            addRow(table, aProduct);
        }
        
        function addRow(table, aProduct) {
            var tr = document.createElement('tr');
            addCell(tr, aProduct.productId); 
            addCell(tr, aProduct.mfrId);
            addCell(tr, aProduct.description);
            addCell(tr, aProduct.price);
            addCell(tr, aProduct.quantity);
            table.appendChild(tr);
        };
    
        function addCell(tr, val) {
            var td = document.createElement('td');
            td.innerHTML = val; 
            tr.appendChild(td);
        }
        return table;
    }
    
    function addButton(body){
        var button = document.createElement("input");
        button.setAttribute("type", "button");
        button.setAttribute("value", "Make Order");
        button.setAttribute("name", "Order Button");
        // This line doesn't work here. But if i call the function then works
        // at time of body appending, not on click on the button.
        // button.setAttribute("onclick", clickMakeOrder);
        button.addEventListener("click", clickMakeOrder);
        body.appendChild(button);
    }
    
    function clickMakeOrder(){
        console.log(JSON.stringify(cart.cart));
        var xhr = new XMLHttpRequest();
        var cartJson = "?cartJson="+JSON.stringify(cart.cart);
        var url = "http://localhost:8080/Shop/authorize/MakeOrder"+cartJson;
        // window.location = url;
        
        xhr.open('GET', url, true);
        xhr.send();
        xhr.onreadystatechange = processRequest;
        function processRequest(e) {
           if (xhr.readyState === 4 && xhr.status === 200) {
               var htmlP = xhr.responseText;
               console.log(htmlP);
               makeDocumentFromXhr(htmlP);
            }
        }
        
        function makeDocumentFromXhr(htmlP){
            var parser = new DOMParser();
            doc = parser.parseFromString(htmlP, "text/html");
            document.replaceChild( document.importNode(doc.documentElement, true), document.documentElement);
        }
    }
}





var cart;
function Cart(){
    this.cart = [];
    
    this.addToCart = function(row){
        this.cart.push(row);
    };
    
    this.getFromCart = function(i){
        return this.cart[i];
    };
    
}
