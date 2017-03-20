function addProductToCart(productId){
	$.ajax({
		type : "POST",
		url : "/webstore/product/addToCart",
		async : false,
		data : {"productId" : productId},
		success : function(added){
			if(added){
				alert("succesfully aded to cart");
			} else{
				alert("product exists in cart");
			}
		},
		 error: function(XMLHttpRequest, textStatus, errorThrown) { 
		        alert("Status: " + textStatus); alert("Error: " + errorThrown);
		    } 
	});
	location.reload();
}
