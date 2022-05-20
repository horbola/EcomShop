/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = function(){
    var newDoc = document.getElementsByClassName("newDoc")[0];
    newDoc.onclick = makeDocument; //function(){window.alert("this is a fucking alert");};
};



function makeDocument(){
    // var doc = document.implementation.createHTMLDocument("New Document");
    // This is another way to replace a document with a new one.
    var doc = parseTextDoc();
    var p = doc.createElement("p");
    p.innerHTML = "This is a new paragraph."; 
  
    try{doc.body.appendChild(p);}
    catch(e){console.log(e);}

    document.replaceChild( document.importNode(doc.documentElement, true), document.documentElement);
}

function parseTextDoc(){
    var parser = new DOMParser();
    
    var textDoc = '\
    <html>\
        <head>\
            <title>TODO supply a title</title>\
            <meta charset="UTF-8">\
            <meta name="viewport" content="width=device-width, initial-scale=1.0">\
        </head>\
        <body>\
            <div>TODO write content</div>\
            <p>This is a complete document to test</p>\
        </body>\
    </html>';
    
    return doc = parser.parseFromString(textDoc, "text/html");
}