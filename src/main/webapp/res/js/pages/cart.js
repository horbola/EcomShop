/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function(e){
    $('.btn button').click(placeOrder);
});


function placeOrder(){
    var shipAddr = document.forms.shipAddr; // Or document.forms['shipAddr']
    var billAddr = document.forms.billAddr;

    let addrForm = new Form("http://localhost:8080/Shop/authorize/Order", "POST");
    addrForm.append("flatS", shipAddr.elements.flat.value);
    addrForm.append("houseS", shipAddr.elements.house.value);
    addrForm.append("roadS", shipAddr.elements.road.value);
    addrForm.append("areaS", shipAddr.elements.area.value);
    addrForm.append("thanaS", shipAddr.elements.thana.value);
    addrForm.append("districtS", shipAddr.elements.district.value);

    addrForm.append("flatB", billAddr.elements.flat.value);
    addrForm.append("houseB", billAddr.elements.house.value);
    addrForm.append("roadB", billAddr.elements.road.value);
    addrForm.append("areaB", billAddr.elements.area.value);
    addrForm.append("thanaB", billAddr.elements.thana.value);
    addrForm.append("districtB", billAddr.elements.district.value);

    addrForm.append("deliveryMethod", "1");
    addrForm.append("paymentMethod", "1");

    addrForm.submit();
}
