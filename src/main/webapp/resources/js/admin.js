function createTreeCategory() {
	$.ajax({
		type : "GET",
		url : "/webstore/admin/MainCategories",
		data : {},
		success : function(mainCategories) {
			$.ajax({
				type : "GET",
				url : "/webstore/admin/TreeCategories",
				data : {},
				success : function(treeCategories) {
					$("#categoryTree").text("");
					var cats = "";
					for (var i = 0; i < mainCategories.length; i++) {
						cats += "<li>"
						+ mainCategories[i].name
						+ "<a class='category-button' href='#' onclick='generateModalAddCategory(\""+ mainCategories[i].id +"\");'" 
						+ "data-toggle='modal' data-target='#modal-category-add-category'>"
						+ "			<i class='fa fa-plus'></i>"
						+ "		</a> "
						+ "		<a class='category-button' href='#' onclick='generateModalEditCategory(\""+ mainCategories[i].id +"\");'" 
						+ " data-toggle='modal' data-target='#modal-category-edit-category'>"
						+ "			<i class='fa fa-pencil'></i>"
						+ "		</a>" 
						+ "		<a class='category-button' href='#' onclick='removeCategory(\""+ mainCategories[i].id +"\");' >" 
						+ "			<i class='fa fa-times'></i>"
						+ "		</a>" 
						+ makeSubTree(treeCategories[mainCategories[i].id], treeCategories) +
						"</li>";
					}
					$("#categoryTree").append(
					"<ul>" + cats + " " +
							"<li> <a class='category-button' href='#' " +
								"onclick='generateModalAddCategory(\"-1\");'" 
								+ "data-toggle='modal' data-target='#modal-category-add-category'>"
								+ "		<i class='fa fa-plus'></i>"
								+ "	</a> "
							+"</li> " + "</ul>");
				}
			});
		}
	});
}

function removeCategory(categoryId){
	$.ajax({
		type : "POST",
		url : "/webstore/admin/removeCategory",
		data : {"categoryId" : categoryId}
	});
	createTreeCategory();
}

function makeSubTree(cats, treeCategories){
	var tree = "<ul>";
	for(var l = 0; l < cats.length; l++){
	}
	for(var l = 0; l < cats.length; l++){
		tree += "<li>"+ cats[l].name 
		+ "<a class='category-button' href='#' onclick='generateModalAddCategory(\""+ cats[l].id +"\");' data-toggle='modal' data-target='#modal-category-add-category'>"
		+ "			<i class='fa fa-plus'></i>"
		+ "		</a> "
		+ "		<a class='category-button' href='#' onclick='generateModalEditCategory(\""+ cats[l].id +"\");' data-toggle='modal' data-target='#modal-category-edit-category'>"
		+ "			<i class='fa fa-pencil'></i>"
		+ "		</a>" 
		+ "		<a class='category-button' href='#' onclick='removeCategory(\""+ cats[l].id +"\");' >" 
		+ "			<i class='fa fa-times'></i>"
		+ "		</a>"
		+ makeSubTree(treeCategories[cats[l].id], treeCategories) +"</li>";
	}
	tree += "</ul>";
	return tree;
}

function generateModalAddCategory(categoryId){
	$("#new-category-features").text("");
	$("#new-feature-name").val("");
	$("#modal-add-new-category-name").val("");
	
	if(categoryId == -1){
		$("#modal-add-category-title").text("New Category");
		$("#modal-add-category-id").val("-1");		
	}
	else{
		$.ajax({
			type : "GET",
			url : "/webstore/admin/getCategory",
			data : {"categoryId" : categoryId},
			success : function(cat) {
				$("#modal-add-category-title").text("Sub category to " + cat.name);
				$("#modal-add-category-id").val(cat.id);		
			}
		});
	}
}

function newCategoryFeatureNames(){
	var fNames = [];
	$('#new-category-features').each(function(){
	        $(this).find('li').each(function(){
	            var current = $(this);
		        fNames.push(current.text());
	        });
	    });
	return fNames;
}

function addNewFetureToList(){	
	var fNames = [];
	fNames = newCategoryFeatureNames();
	
	var featureName = $("#new-feature-name").val();
	if(featureName != ""){
		if(!fNames.includes(featureName)){
		$("#new-category-features").append(
				"<li>" + featureName + "</li>");
		}
	}
}

function createCategory(){
	var featureNames = [];
	featureNames = newCategoryFeatureNames();
	if(featureNames.length == 0){
		featureNames.push("no_elements");
	}
	var name = $("#modal-add-new-category-name").val();
	var mainCategoryId = $("#modal-add-category-id").val();
	$.ajax({
		method : "POST",
		url : "/webstore/admin/addCategory",
	    dataType:'json',
		data : {
			name : name,
			mainCategoryId : mainCategoryId,
			featureNames : featureNames
		}
	});
	location.reload();
}

function generateModalEditCategory(categoryId){
	$("#edit-category-features").text("");
	$("#edit-category-new-feature-name").val("");
	$("#edit-category-name").val("");
	
		$.ajax({
			type : "GET",
			url : "/webstore/admin/getCategory",
			data : {"categoryId" : categoryId},
			success : function(cat) {
				$("#edit-category-name").val(cat.name);
				$("#modal-edit-category-title").text("Edit " + cat.name);
				$("#modal-edit-category-id").val(cat.id);
				for (var i = 0; i < cat.features.length; i++) {
					$("#edit-category-features").append("<li>"+cat.features[i].name +
							"<a href='#' onclick='editCategoryRemoveNewFetureFromList(\""+cat.features[i].name+"\");'><i class=\"fa fa-times\"></i></a></li>");
				}
			}
		});
}

function editCategoryRemoveNewFetureFromList(featureName){
	var fNames = [];
	fNames = editCategoryFeatureNames();
	var index = fNames.indexOf(featureName);
	if (index > -1) {
		fNames.splice(index, 1);
	}
	$("#edit-category-features").text("");
	for(var i = 0; i < fNames.length; i++){
		$("#edit-category-features").append(
				"<li>" + fNames[i] + "<a href='#' onclick='editCategoryRemoveNewFetureFromList(\""+fNames[i]+"\");'><i class=\"fa fa-times\"></i></a></li>");
	}
}

function editCategoryFeatureNames(){
	var fNames = [];
	$('#edit-category-features').each(function(){
	        $(this).find('li').each(function(){
	            var current = $(this);
		        fNames.push(current.text());
	        });
	    });
	return fNames;
}

function editCategoryAddNewFetureToList(){	
	var fNames = [];
	fNames = editCategoryFeatureNames();
	
	var featureName = $("#edit-category-new-feature-name").val();
	if(featureName != ""){
		if(!fNames.includes(featureName)){
		$("#edit-category-features").append(
				"<li>" + featureName + " <a><i class=\"fa fa-times\"></i></a></li>");
		}
	}
}

function editCategory(){
	var featureNames = [];
	featureNames = editCategoryFeatureNames();
	if(featureNames.length == 0){
		featureNames.push("no_elements");
	}
	var name = $("#edit-category-name").val();
	var categoryId = $("#modal-edit-category-id").val();
	$.ajax({
		method : "POST",
		url : "/webstore/admin/editCategory",
	    dataType:'json',
		data : {
			name : name,
			categoryId : categoryId,
			featureNames : featureNames
		}
	});
	createTreeCategory();
}

function createListProducts(){
		$.ajax({
			type : "GET",
			url : "/webstore/admin/getProducts",
			data : {},
			success : function(products) {
						$("#list-product").text("");
						var prods = "<tr>"+
										"<th>Name</th>"+
										"<th>Price</th>"+
										"<th>Category</th>"+
										"<th>Producer</th>"+
										"<th>Country</th>"+
										"<th>Weight</th>"+
										"<th>Amount on warehouse</th>"+
										"<th>Additional features</th>"+
										"<th>Photos</th>"+
										"<th></th>"+
										"<th></th>"+
									"</tr>";
						for (var i = 0; i < products.length; i++) {
							prods+="<tr>"+
							"<td>"+products[i].product.name+"</td>"+
							"<td>"+products[i].product.price+"&#8372;</td>"+
							"<td>"+products[i].category.name+"</td>"+
							"<td>"+products[i].product.producer+"</td>"+
							"<td>"+products[i].product.country+"</td>"+
							"<td>"+products[i].product.weight+"</td>"+
							"<td>"+products[i].product.amountOnWarehouse+"</td>"+
							"<td><button onclick='generateModalProductFeatures(\""+products[i].product.id+"\");' class='btn btn-primary' data-toggle='modal'"+
							"	data-target='#modal-product-features'>Show</button></td>"+
							"	<td><button onclick='generateModalProductPhotos(\""+products[i].product.id+"\");' class='btn btn-primary' data-toggle='modal'"+
							"		data-target='#modal-product-photos'>Show</button></td>"+
							"	<td><button onclick='generateEditProductModal(\""+products[i].product.id+"\");' class='btn btn-danger' data-toggle='modal'"+
							"			data-target='#modal-product-edit'>"+
							"			<i class='fa fa-pencil'></i>"+
							"		</button></td>"+
							"<td><button onclick='removeProduct(\""+products[i].product.id+"\");' class='btn btn-danger'><i class='fa fa-times'></i></button></td>"+
							"</tr>";
						}
						prods +="<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>"+
								"</td><td></td><td></td><td><button onclick='generateModalAddProduct();' class='btn btn-success' data-toggle='modal'"+
								"		data-target='#modal-add-product'>"+
								"		<i class='fa fa-plus'></i>"+
								"	</button></tr>";
						$("#list-product").append(
						"<table class='table-product'>" + prods + "</table>");
					}
				});
}

function removeProduct(productId){
	$.ajax({
		type : "POST",
		url : "/webstore/admin/removeProduct",
		data : {"productId" : productId}
	});
	//createListProducts();
}

function generateModalProductFeatures(productId){
	$.ajax({
		type : "GET",
		url : "/webstore/admin/getProduct",
		data : {"productId" : productId},
		success : function(prod) {
			$("#modal-product-feature-title").text(prod.name + " features");
			var efeats = "";
			for(var i = 0; i < prod.product.productExtraFeatures.length; i++){
				efeats += "<tr><td>"+ prod.product.productExtraFeatures[i].name 
				+ "</td><td>"+ prod.product.productExtraFeatures[i].value+"</td></tr>";
			}
			$("#modal-product-feature-content").text("");
			$("#modal-product-feature-content").append("" +
					"<p style='font-weight: bold;'>"+prod.product.name+"</p>"+
								"<table class='table-features'>"+
								efeats +
								"</table>");
		}
	});
}

function generateModalAddProduct(){
	$.ajax({
		type : "GET",
		url : "/webstore/admin/allCategories",
		data : {},
		success : function(cats) {
			$("#new-product-category").text("");	
			for(var i = 0; i < cats.length; i++){
				$("#new-product-category").append("<option>"+cats[i].name+"</option>");
			}
		}
	});
}

function createProduct(){
	var name = $("#new-product-name").val();
	var price = $("#new-product-price").val();
	var category = $("#new-product-category").val();
	var producer = $("#new-product-producer").val();
	var country = $("#new-product-country").val();
	var weight = $("#new-product-weight").val();
	var amountOnWarehouse = $("#new-product-amount-on-warehouse").val();
	$.ajax({
		method : "POST",
		url : "/webstore/admin/addProduct",
	    dataType:'json',
		data : {
			name : name,
			price : price,
			category : category,
			producer : producer,
			country : country,
			weight : weight,
			amountOnWarehouse : amountOnWarehouse
		},
		success : function(){
			createListProducts();
		}
	});
}

function generateEditProductModal(productId){
	$.ajax({
		type : "GET",
		url : "/webstore/admin/allCategories",
		data : {},
		success : function(cats) {
			$("#modal-product-edit-category").text("");	
			for(var i = 0; i < cats.length; i++){
				$("#modal-product-edit-category").append("<option>"+cats[i].name+"</option>");
			}
		}
	});
	$.ajax({
		type : "GET",
		url : "/webstore/admin/getProduct",
		data : {"productId" : productId},
		success : function(prod) {
			$("#modal-product-edit-title").text(prod.product.name + " edit");
			$("#modal-product-edit-id").val(prod.product.id);
			$("#modal-product-edit-name").val(prod.product.name);
			$("#modal-product-edit-price").val(prod.product.price);
			$("#modal-product-edit-category").val(prod.category.name);
			$("#modal-product-edit-producer").val(prod.product.producer);
			$("#modal-product-edit-country").val(prod.product.country);
			$("#modal-product-edit-weight").val(prod.product.weight);
			$("#modal-product-edit-amount-on-warehouse").val(prod.product.amountOnWarehouse);
			
			var pFeatures = "";
			for(var i = 0; i < prod.product.productExtraFeatures.length; i++){
				pFeatures += "<tr><td>"+ prod.product.productExtraFeatures[i].name 
				+ "</td><td><input type='text' class='fValue' value='"+ prod.product.productExtraFeatures[i].value+"'></td></tr>";
			}
			$("#modal-product-edit-extraFeatures").text("");
			$("#modal-product-edit-extraFeatures").append(pFeatures);
		}
	});
}

function editProduct(){
	var productId = $("#modal-product-edit-id").val();
	var name = $("#modal-product-edit-name").val();
	var price = $("#modal-product-edit-price").val();
	var category = $("#modal-product-edit-category").val();
	var producer = $("#modal-product-edit-producer").val();
	var country = $("#modal-product-edit-country").val();
	var weight = $("#modal-product-edit-weight").val();
	var amountOnWarehouse = $("#modal-product-edit-amount-on-warehouse").val();
	
	var extraFeatures = [];
	
//	$("#modal-product-edit-extraFeatures").find('tr').each(function (i, el) {
//        var $tds = $(this).find('td');
//           var efName = $tds.eq(0).text();
//           var efValue = $tds.eq(1).find('.fValue').val();
//           if(efValue == ""){
//        	   efValue = "null_null";
//           }
//           extraFeatures.push(efName + "__"+ efValue);
//    });
	
	$.ajax({
		method : "POST",
		url : "/webstore/admin/editProduct",
	    dataType:'json',
		data : {
			productId : productId,
			name : name,
			price : price,
			category : category,
			producer : producer,
			country : country,
			weight : weight,
			amountOnWarehouse : amountOnWarehouse,
			//extraFeatures : extraFeatures
		},
		success : function(){
			createListProducts();
		}
	});
	createListProducts();
}

function showUserOrders(login) {
	$.ajax({
		type : "GET",
		url : "/webstore/admin/user/orders",
		data : {
			"login" : login
		},
		success : function(orders) {
			$("#modal-user-orders-title").text(login + " orders");
			$("#modal-user-orders-orders").text("");
			var countPrice = 0;
			for (var i = 0; i < orders.length; i++) {
				var prods = "";
				for (var j = 0; j < orders[i].products.length; j++) {
					prods += "<li>" + orders[i].products[j].name + " "
							+ orders[i].products[j].price + "&#8372;</li>";
					countPrice += orders[i].products[j].price;
				}
				$("#modal-user-orders-orders").append(
						"<div class='u-order'>" + "	<p>№ " + orders[i].id
								+ "</p>" + "	<ul>" + prods + "	</ul>"
								+ "	Count price: " + countPrice
								+ "&#8372;</div>");
			}
		}
	});
}

function changeAdmiStatus(cb, login) {
	$.ajax({
		method : "POST",
		url : "/webstore/admin/changeIsAdmin",
		data : {
			login : login,
			status : cb.checked
		}
	});
}

function changeBlockedStatus(cb, login) {
	$.ajax({
		method : "POST",
		url : "/webstore/admin/changeIsBlocked",
		data : {
			login : login,
			status : cb.checked
		}
	});
}

function generateModalProductPhotos(productId){
	$("#modal-product-photos-title").text("");
	$.ajax({
		method : "GET",
		url : "/webstore/admin/getProduct",
		data : {productId : productId},
		success : function(product){
			$("#photos-product-id").val(productId);
			
			if(product.product.photos.length > 0){					
				$("#modal-product-photos-title").append(product.product.name + " photos");
				$("#photo-data-slides").text("");
				$("#photo-slide-items").text("");
				var setActive = "active";
				for(var i = 0; i < product.product.photos.length; i++){
					if(i == 0){
						$("#photo-data-slides").append("<li class='"+setActive+"' data-target='#carousel' data-slide-to='"+(i)+"'></li>");
						$("#photo-slide-items").append("<div class='item "+setActive+"'><button onclick='removePhoto("+product.product.photos[i].id+");' style='margin-left: 45%;'> <i class='fa fa-times'></i></button>"+
								"<img src='/webstore/admin/product/photo?id="+product.product.photos[i].id+"' style='width: 250px;'></div>");
					}else{
						$("#photo-data-slides").append("<li data-target='#carousel' data-slide-to='"+(i)+"'></li>");
						$("#photo-slide-items").append("<div class='item'><button onclick='removePhoto("+product.product.photos[i].id+");' style='margin-left: 45%;'><i class='fa fa-times'></i></button>"+
								"<img src='/webstore/admin/product/photo?id="+product.product.photos[i].id+"' style='width: 250px;'></div>");
					}
				}
			}
		}
		});
}

var files = [];

$(document).on("change",
	"#fileLoader",
	function(event) {
	files=event.target.files;
});

function processUpload(){
	var fileData = new FormData();
	fileData.append("file", files[0]);
	var productId = $("#photos-product-id").val();
	var url = "/webstore/admin/addProductPhoto/"+productId;
	$.ajax({dataType : 'json',
		url : url,
		data : fileData,
		type : "POST",
		enctype: 'multipart/form-data',
		processData: false, 
		contentType:false
	});
	$("#button-close-photos").click();
	$("#photo-data-slides").text("");
	$("#photo-slide-items").text("");
}

function removePhoto(photoId){
	$.ajax({dataType : 'json',
		url : "/webstore/admin/removeProductPhoto",
		data : {photoId : photoId},
		type : "POST"
	});
	$("#button-close-photos").click();
	$("#photo-data-slides").text("");
	$("#photo-slide-items").text("");
}



