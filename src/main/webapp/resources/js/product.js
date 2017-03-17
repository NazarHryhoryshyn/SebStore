function addProductToCart(productId){
	$.ajax({
		type : "POST",
		url : "/webstore/product/addToCart",
		data : {"productId" : productId},
		success : function(){
			alert("succesfully aded to cart");
		},
		 error: function(XMLHttpRequest, textStatus, errorThrown) { 
		        alert("Status: " + textStatus); alert("Error: " + errorThrown); 
		    } 
	});
}