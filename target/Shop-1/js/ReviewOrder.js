/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


window.onload = onLoad;
function onLoad(){
    var buttons = document.getElementsByClassName("btn");
    for(let b in buttons){
        buttons[b].onclick = function(){
            var div = this.parentNode.parentNode.parentNode.parentNode.parentNode;
            var caption = this.parentNode.parentNode.parentNode.parentNode.childNodes[1];
            
            
            window.alert("you are alert"+div.tagName+"  "+caption.tagName);
        };
    }
}