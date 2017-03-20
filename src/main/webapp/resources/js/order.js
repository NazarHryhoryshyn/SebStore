function setOrderValues(){
	$("#inf-delivery-type").text($("#delivery-type").val());
	$("#inf-address").text($("#address").val());
	$("#inf-receiver").text($("#receiver").val());
	$("#inf-pay-type").text($("#payment-type").val());
	$("#inf-credit-card").text($("#cardNumber").val());
}

function makeOrder(){
	var deliveryType = $("#delivery-type").val();
	var paymentType = $("#payment-type").val();
	var receiver = $("#receiver").val();
	var phone = $("#phone").val();
	var email = $("#email").val();
	var address = $("#address").val();
	var cardNumber = $("#cardNumber").val();
	var cardTreeNumber = $("#cvCode").val();
	var cardTermOf = $("#expityYear").val()+$("#expityMonth").val();
	if((paymentType == "credit card" && (cardNumber == "" || cardNumber.length < 16 || cvCode == "" || expityYear == ""))
			|| receiver == "" || address == ""){
		alert("fill all needed fields!");
		return;
	}
		$.ajax({
			method : "POST",
			url : "/webstore/admin/addOrder",
		    dataType:'json',
			data : {
				deliveryType : deliveryType,
				paymentType : paymentType,
				receiver : receiver,
				phone : phone,
				email : email,
				address : address,
				cardNumber : cardNumber+"",
				cardTreeNumber : cardTreeNumber+"",
				cardTermOf : cardTermOf+""
			}
		});
		window.location.href = "/webstore/";
}
$('document').ready(function(){
	$('#payment-type').on('change', function() {
		if($( "#payment-type" ).val() == "cash"){
			$("#payment-block").hide();
			$("#inf-creditcard-block").hide();
		}
		if($( "#payment-type" ).val() == "credit card"){
			$("#payment-block").show();
			$("#inf-creditcard-block").show();
		}
	});
	
});
