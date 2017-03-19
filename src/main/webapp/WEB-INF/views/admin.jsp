<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Admin</title>

<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/admin.css" rel="stylesheet">
<link href="${contextPath}/resources/css/location_div_styles.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/tab_styles.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
<script src="${contextPath}/resources/js/jquery-3.1.1.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/js/admin.js"></script>

</head>

<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#mytable #checkall").click(function() {
				if ($("#mytable #checkall").is(':checked')) {
					$("#mytable input[type=checkbox]").each(function() {
						$(this).prop("checked", true);
					});

				} else {
					$("#mytable input[type=checkbox]").each(function() {
						$(this).prop("checked", false);
					});
				}
			});

			$("[data-toggle=tooltip]").tooltip();
			$("#tab1").click();
		});
	</script>
	<jsp:include page="staticHtml/header.jsp"></jsp:include>
	<div class="container location-block">
		<div class="location-sub-block">
			<div class="row">
				<div id="bc2" class="btn-group btn-breadcrumb">
					<a href="#" class="btn btn-default "><i class="fa fa-home"></i></a>
					<a href="#" class="btn btn-default ">profile</a>
				</div>
			</div>
		</div>
	</div>
	<div class="container content-block">
		<div class="container" style="margin: 10px;">
			<div class="tabs">
				<input id="tab1" onclick="createListUsers(1);" type="radio" name="tabs" checked> <label
					for="tab1" title="Users">Users</label> 
				<input id="tab2" onclick="createTreeCategory();"
					type="radio" name="tabs"> <label for="tab2"
					title="Categories">Categories</label> 
				<input id="tab3" onclick="createListProducts(1);" type="radio"
					name="tabs"> <label for="tab3" title="Products">Products</label>
				<input id="tab4" onclick="createListOrders(1);" type="radio" name="tabs"> <label for="tab4"
					title="Orders">Orders</label>

				<section id="content-tab1">
					<div id="list-users" class="tab-content">
						<input type="hidden" id="isAdmTemplVal" value="true">
						<input type="hidden" id="isBlockTemplVal" value="true">
					</div>
				</section>
				<section id="content-tab2">
					<div id="categoryTree"  class="tab-content">
					</div>
				</section>
				<section id="content-tab3">
					<div id="list-product" class="tab-content">
						
					</div>
				</section>
				<section id="content-tab4">
					<div id="list-orders" class="tab-content">
					</div>
				</section>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<jsp:include page="staticHtml/footer.jsp"></jsp:include>
	</div>

	<div class="modal fade" id="modal-user-orders">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 id="modal-user-orders-title" class="modal-title"></h4>
				</div>
				<div class="modal-body small-modal-body">
					<div id="modal-user-orders-orders" class="modal-body-content">
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="modal-category-add-category">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 id="modal-add-category-title" class="modal-title"></h4>
				</div>
				<div id="modal-add-category-content" class="modal-body small-modal-body">
				<input id="modal-add-category-id" type="hidden">
					Name:<br> <input id="modal-add-new-category-name" type="text" placeholder="Category name"><br>
					Additional features:<br>
					<ul id="new-category-features" style="text-align: left;">

					</ul>
					<br> <br> new feature: <input id="new-feature-name" type="text"
						placeholder="New feature name"> <br> <br>
					<button class="btn btn-primary" onclick="addNewFetureToList();">
						<i class="fa fa-plus"> Add feature</i>
					</button>
				</div>
				<div class="modal-footer">
					<button onclick="createCategory();" class="btn btn-primary small-modal-button"
						data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modal-category-edit-category">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 id="modal-edit-category-title" class="modal-title"></h4>
				</div>
				<div class="modal-body small-modal-body">
				<input id="modal-edit-category-id" type="hidden">
					Name:<br> <input id="edit-category-name" type="text" placeholder="Category name"
						value="Toys"><br> Additional features:<br>
					<ul id="edit-category-features" style="text-align: left;">
					</ul>
					<br> <br> new feature: <input id="edit-category-new-feature-name" type="text"
						placeholder="New feature name"> <br> <br>
					<button class="btn btn-primary"  onclick="editCategoryAddNewFetureToList();">
						<i class="fa fa-plus"> Add feature</i>
					</button>
				</div>
				<div class="modal-footer">
					<button onclick="editCategory();" class="btn btn-primary small-modal-button"
						data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="modal-product-features">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 id="modal-product-feature-title" class="modal-title"></h4>
				</div>
				<div class="modal-body small-modal-body">
					<div id="modal-product-feature-content" class="modal-body-content">
						
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="modal-add-product">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">New Product</h4>
				</div>
				<div class="modal-body small-modal-body">
					Name:<br> <input id="new-product-name" type="text" placeholder="Product name"><br>
					Price:<br> <input id="new-product-price" type="number" placeholder="Price"><br>
					Category:<br> <select id="new-product-category" ></select><br> 
					Producer:<br> <input id="new-product-producer" type="text" placeholder="Producer"><br> 
					Country:<br> <input id="new-product-country" type="text" placeholder="Country"><br> 
					Weight:<br> <input id="new-product-weight" type="number" min="0" value="0" placeholder="Weight"><br>
					Amount on warehouse:<br> <input id="new-product-amount-on-warehouse" type="text" value="1" min="0"><br>
				</div>
				<div class="modal-footer">
					<button onclick="createProduct();" class="btn btn-primary small-modal-button"
						data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="modal-product-edit">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 id="modal-product-edit-title" class="modal-title"></h4>
				</div>
				<div class="modal-body small-modal-body">
					<div class="modal-body-content">
					<input id="modal-product-edit-id" type="hidden" value="">
						<table class="table-prod-edit">
							<tr>
								<td>Name</td>
								<td><input id="modal-product-edit-name" type="text" value=""></td>
							</tr>
							<tr>
								<td>Price</td>
								<td><input id="modal-product-edit-price" type="number"></td>
							</tr>
							<tr>
								<td>Category</td>
								<td><select id="modal-product-edit-category">
								</select></td>
							</tr>
							<tr>
								<td>Producer</td>
								<td><input id="modal-product-edit-producer" type="text" value="Adidas"></td>
							</tr>
							<tr>
								<td>Country</td>
								<td><input id="modal-product-edit-country" type="text" value="Ukraine"></td>
							</tr>
							<tr>
								<td>Weight</td>
								<td><input id="modal-product-edit-weight" type="number" value="2050"></td>
							</tr>
							<tr>
								<td>Amount on warehouse</td>
								<td><input id="modal-product-edit-amount-on-warehouse" type="number" value="45"></td>
							</tr>
						</table>
						<hr>
						<table id="modal-product-edit-extraFeatures" class="table-prod-edit">
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button onclick='editProduct();' class="btn btn-primary small-modal-button"
						data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modal-product-photos">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 id="modal-product-photos-title" class="modal-title"></h4>
				</div>
				<div class="modal-body small-modal-body">
					<div class="modal-body-content">
						<div id="carousel" class="carousel slide" data-ride="carousel">
							<ol id="photo-data-slides" class="carousel-indicators">
							<li class="active" data-target='#carousel' data-slide-to="0"></li>
							</ol>
							<div id="photo-slide-items" class="carousel-inner">
							<div class="item active"><button style="margin-left: 45%;"> <i class="fa fa-times"></i></button>
								<img src="#" style="width: 250px;"></div>
							</div>
							<a href="#carousel" class="left carousel-control"
								data-slide="prev"></a> <a href="#carousel"
								class="right carousel-control" data-slide="next"></a>
						</div>
						<br>						
					 	    <input id="photos-product-id" type="hidden">					 
					 		<input type="file" name="file" id="fileLoader" accept="image/*"/> 						
							<input onclick="processUpload();" type="submit" id="fileSubmit" value="Upload"/>
						<br>
						
					</div>
				</div>
				<div class="modal-footer">
					<button id="button-close-photos" class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modal-order-ch-status">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change status</h4>
				</div>
				<div class="modal-body small-modal-body">
					<p style="font-weight: bold;">Order №<font id="title-order-id">234324</font></p>
					<select id="new-order-status">
						<option selected="selected">in processing</option>
						<option>preparation for sending</option>
						<option>sent</option>
						<option>arrived</option>
					</select>
				</div>
				<div class="modal-footer">
					<button onclick='changeOrderStatus();' class="btn btn-primary small-modal-button"
						data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>