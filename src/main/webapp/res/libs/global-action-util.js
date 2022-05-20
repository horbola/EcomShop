/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function BuildQueryString(){
    var queryString = "";
    
    this.append = function(key, value){
        let kv = "";
        if(queryString === ""){
            kv = "?"+key+"="+value;
        }
        else kv = "&"+key+"="+value;
        queryString += kv;
    };
    
    this.getQueryString = function(){
        let qs = encodeURI(queryString);
        return qs;
    };
}



function postDataRedirect(name, data, location) {
var form = document.createElement("form");

form.method = "POST";
form.action = location;

if (data.constructor === Array && name.constructor === Array) {
    for (var i = 0; i < data.length; i++) {
        var element = document.createElement("input");
        element.type = "hidden";
        element.value = data[i];
        element.name = name[i];
        form.appendChild(element);
    }
} else {
    var element1 = document.createElement("input");
    element1.type = "hidden";
    element1.value = data;
    element1.name = name;
    form.appendChild(element1);
}

document.body.appendChild(form);

form.submit(); 
}






function Form(location, method) {
    var form = document.createElement("form");

    form.action = location;
    form.method = method;


    this.append = function(name, data){
        makeNameValueString(name, data);
        var element = document.createElement("input");
        element.type = "hidden";
        element.name = name;
        element.value = data;
        form.appendChild(element);
    };

    this.submit = function(){
        document.body.appendChild(form);
        form.submit(); 
    };
    

    
    let nameValueString = "";
    function makeNameValueString(name, value){
        nameValueString += "{" +name+ " : " +value+ "}, ";
    };
    
    this.getNameValueString = function(){
        return nameValueString.substring(0, nameValueString.length-2);
    };
}


function makeDocumentFromXhr(responseText){
    var parser = new DOMParser();
    doc = parser.parseFromString(responseText, "text/html");
    console.log("documentURL"+doc.documentURI);
    console.log("URL"+doc.URL);
    console.log("location"+doc.location);
    document.replaceChild( document.importNode(doc.documentElement, true), document.documentElement);
    
}

function classof(o) {
    if (o === null) return "Null";
    if (o === undefined) return "Undefined";
    return Object.prototype.toString.call(o).slice(8,-1);
}
