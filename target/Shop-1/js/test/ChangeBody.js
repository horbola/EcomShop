/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


window.onload = function(){
    document.getElementsByClassName("changeBody")[0].onclick = changeBody;
};

function changeBody(){
    var body = document.createElement("body");
    var p = document.createElement("p");
    var text = document.createTextNode("this is text");
    body.appendChild(p.appendChild(text));
    document.body = body;
}