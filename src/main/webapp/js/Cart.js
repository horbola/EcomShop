/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


window.onload = onLoad;
function onLoad(){
    document.getElementsByClassName("cart")[0].onclick = cartClick;
    registerCartEvent();
};


function cartClick(){
    makeDocument();
}

function makeDocument(){
    function addCell(tr, val) {
        var td = document.createElement('td');
        td.innerHTML = val; 
        tr.appendChild(td);
    }


    function addRow(table, val_1, val_2, val_3, val_4, val_5) {
        var tr = document.createElement('tr');
        addCell(tr, val_1); 
        addCell(tr, val_2);
        addCell(tr, val_3);
        addCell(tr, val_4);
        addCell(tr, val_5);
        table.appendChild(tr);
    };

    
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
        window.alert("blabla");
        console.log(JSON.stringify(cart.cart));
        var xhr = new XMLHttpRequest();
        var cartJson = "?cartJson="+JSON.stringify(cart.cart);
        var url = "http://localhost:8080/Shop/protectet/MakeOrder"+cartJson;
        // window.location = url;
        
        xhr.open('GET', url, true);
        xhr.send();
        xhr.onreadystatechange = processRequest;
        function processRequest(e) {
           if (xhr.readyState === 4 && xhr.status === 200) {
               window.alert("From inside proccessRequest()");
               var htmlP = xhr.responseText;
               console.log(htmlP);
               makeDocumentFromXhr(htmlP);
            }
        }
    }
     
    function buildTable() {
        var table = document.createElement('table');
        
        for(let i = 0; i<cart.cart.length; i++){
            var aProduct = cart.getFromCart(i);
            addRow(table, aProduct.productId, aProduct.mfrId, aProduct.description, aProduct.price, aProduct.quantity);
        }
        
        var body = document.createElement("body");
        createNav(body);
        body.appendChild(table);
        addButton(body);
        var bodyText = document.getElementsByTagName('body')[0].innerHTML;
        console.log(bodyText);
        
        return body;
    }
    
    document.body = buildTable();
}



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


var cart;
function Cart(){
    this.cart = [];
    
    this.addToCart = function(row){
        this.cart.push(row);
    };
    
    this.getFromCart = function(i){
        return this.cart[i];
    };
    
    this.testMsg = function(){
        return "This is a message";
    };
}



function makeDocumentFromXhr(htmlP){
    var parser = new DOMParser();
    doc = parser.parseFromString(htmlP, "text/html");
    document.replaceChild( document.importNode(doc.documentElement, true), document.documentElement);
}
