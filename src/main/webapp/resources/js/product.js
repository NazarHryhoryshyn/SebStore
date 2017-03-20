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

function findByName(){
	var prodName = $("#prodName").val();
	var catName = $("#currCat").text();
	if(catName == ""){
		catName = "All category";
	}
	window.location.href = "/webstore/products/"+catName+"-1-"+prodName+"-all-0-0";
}

function doFilters(){
	var prodName = $("#productName").val();
	var catName = $("#currCat").text();
	var prodMaker = $("#prodMaker").val();
	var minP = $("#minPrice").val();
	var maxP = $("#maxPrice").val();
	if(catName == ""){
		catName = "All category";
	}
	if(prodName == ""){
		prodName = "all";
	}
	if(prodMaker == ""){
		prodMaker = "all";
	}
	window.location.href = "/webstore/products/"+catName+"-1-"+prodName+"-"+prodMaker+"-"+minP+"-"+maxP;
}