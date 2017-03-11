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
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
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
				<input id="tab1" type="radio" name="tabs" checked> <label
					for="tab1" title="Вкладка 1">Users</label> <input id="tab2"
					type="radio" name="tabs"> <label for="tab2"
					title="Вкладка 2">Categories</label> <input id="tab3" type="radio"
					name="tabs"> <label for="tab3" title="Вкладка 3">Products</label>
				<input id="tab4" type="radio" name="tabs"> <label for="tab4"
					title="Вкладка 4">Orders</label>

				<section id="content-tab1">
					<div class="tab-content">
						<table class="table-user">
							<tr>
								<th>First name</th>
								<th>Last name</th>
								<th>Login</th>
								<th>Email</th>
								<th>Telephone</th>
								<th>Sex</th>
								<th>Admin</th>
								<th>Blocked</th>
								<th>Orders</th>
							</tr>
							<c:forEach items="${users}" var="u">
								<tr>
									<td>${u.firstname}</td>
									<td>${u.lastname}</td>
									<td>${u.login}</td>
									<td>${u.email}</td>
									<td>${u.telephone}</td>
									<td>${u.sex}</td>
									<c:if test="${isAdmins[u.login] == true}">
										<td><input type="checkbox"  onclick="changeAdmiStatus(this,'${u.login}');" checked></td>
									</c:if>
									<c:if test="${isAdmins[u.login] == false}">
										<td><input type="checkbox" onclick="changeAdmiStatus(this,'${u.login}');"></td>
									</c:if>
									<c:if test="${isBlockeds[u.login] == true}">
										<td><input type="checkbox"  onclick="changeBlockedStatus(this,'${u.login}');" checked></td>
									</c:if>
									<c:if test="${isBlockeds[u.login] == false}">
										<td><input type="checkbox" onclick="changeBlockedStatus(this,'${u.login}');"></td>
									</c:if>
									<td><button class="btn btn-primary" data-toggle="modal" onclick="showUserOrders('${u.login}');"
											data-target="#modal-user-orders">Show</button></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</section>
				<section id="content-tab2">
					<div class="tab-content">
						<ul>
							<li>Category 1 <a class="category-button" href="#"
								data-toggle="modal" data-target="#modal-category-add-category">
									<i class="fa fa-plus"></i>
							</a> <a class="category-button" href="#" data-toggle="modal"
								data-target="#modal-category-edit-category"> <i
									class="fa fa-pencil"></i></a>
							</li>
							<li>Category 2 <a class="category-button"> <i
									class="fa fa-plus"></i>
							</a> <a class="category-button"> <i class="fa fa-pencil"></i></a> <a
								class="category-button" style="text-align: center;"> <i
									class="fa fa-info" aria-hidden="true"></i>
							</a>
								<ul>
									<li>Sub category 1 <a class="category-button"> <i
											class="fa fa-plus"></i>
									</a> <a class="category-button"> <i class="fa fa-pencil"></i></a> <a
										class="category-button" style="text-align: center;"> <i
											class="fa fa-info" aria-hidden="true"></i>
									</a>
									</li>
									<li>Sub category 2 <a class="category-button"> <i
											class="fa fa-plus"></i>
									</a> <a class="category-button"> <i class="fa fa-pencil"></i>
									</a> <a class="category-button" style="text-align: center;"> <i
											class="fa fa-info" aria-hidden="true"></i>
									</a>
									</li>
									<li>Sub category 3 <a class="category-button"> <i
											class="fa fa-plus"></i>
									</a> <a class="category-button"> <i class="fa fa-pencil"></i>
									</a> <a class="category-button" style="text-align: center;"> <i
											class="fa fa-info" aria-hidden="true"></i>
									</a>
										<ul>
											<li>Sub sub category 1 <a class="category-button"> <i
													class="fa fa-plus"></i>
											</a> <a class="category-button"> <i class="fa fa-pencil"></i>
											</a> <a class="category-button" style="text-align: center;">
													<i class="fa fa-info" aria-hidden="true"></i>
											</a>
											</li>
											<li>Sub sub category 2 <a class="category-button"> <i
													class="fa fa-plus"></i>
											</a> <a class="category-button"> <i class="fa fa-pencil"></i>
											</a> <a class="category-button" style="text-align: center;">
													<i class="fa fa-info" aria-hidden="true"></i>
											</a>
											</li>
											<li>Sub sub category 3 <a class="category-button"> <i
													class="fa fa-plus"></i>
											</a> <a class="category-button"> <i class="fa fa-pencil"></i>
											</a> <a class="category-button" style="text-align: center;">
													<i class="fa fa-info" aria-hidden="true"></i>
											</a>
											</li>
										</ul>
									</li>
								</ul>
							</li>
							<li>Category 3 <a class="category-button"> <i
									class="fa fa-plus"></i>
							</a> <a class="category-button"> <i class="fa fa-pencil"></i>
							</a> <a class="category-button" style="text-align: center;"> <i
									class="fa fa-info" aria-hidden="true"></i>
							</a>
							</li>
							<li>Category 4 <a class="category-button"> <i
									class="fa fa-plus"></i>
							</a> <a class="category-button"> <i class="fa fa-pencil"></i>
							</a> <a class="category-button" style="text-align: center;"> <i
									class="fa fa-info" aria-hidden="true"></i>
							</a>
							</li>
							<li>Category 5 <a class="category-button"> <i
									class="fa fa-plus"></i>
							</a> <a class="category-button"> <i class="fa fa-pencil"></i>
							</a> <a class="category-button" style="text-align: center;"> <i
									class="fa fa-info" aria-hidden="true"></i>
							</a>
							</li>
						</ul>
					</div>
				</section>
				<section id="content-tab3">
					<div class="tab-content">
						<table class="table-product">
							<tr>
								<th>Name</th>
								<th>Price</th>
								<th>Category</th>
								<th>Producer</th>
								<th>Country</th>
								<th>Weight</th>
								<th>Amount on warehouse</th>
								<th>Additional features</th>
								<th>Photos</th>
								<th></th>
							</tr>
							<tr>
								<td>Ball</td>
								<td>$99</td>
								<td>Toys</td>
								<td>Adidas</td>
								<td>Ukrain</td>
								<td>230</td>
								<td>40</td>
								<td><button class="btn btn-primary" data-toggle="modal"
										data-target="#modal-product-features">Show</button></td>
								<td><button class="btn btn-primary" data-toggle="modal"
										data-target="#modal-product-photos">Show</button></td>
								<td><button class="btn btn-danger" data-toggle="modal"
										data-target="#modal-product-edit">
										<i class="fa fa-pencil"></i>
									</button></td>
							</tr>
							<tr>
								<td>Ball</td>
								<td>$99</td>
								<td>Toys</td>
								<td>Adidas</td>
								<td>Ukrain</td>
								<td>230</td>
								<td>40</td>
								<td><button class="btn btn-primary">Show</button></td>
								<td><button class="btn btn-primary" data-toggle="modal"
										data-target="#modal-product-photos">Show</button></td>
								<td><button class="btn btn-danger">
										<i class="fa fa-pencil"></i>
									</button></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td><button class="btn btn-success" data-toggle="modal"
										data-target="#modal-category-add-product">
										<i class="fa fa-plus"></i>
									</button></td>
							</tr>
						</table>
					</div>
				</section>
				<section id="content-tab4">
					<div class="tab-content">
						<table class="table-order">
							<tr>
								<th>№</th>
								<th>User</th>
								<th>Status</th>
								<th>Date</th>
								<th></th>
								<th></th>
							</tr>
							<tr>
								<td>2342345</td>
								<td>Nazar Hryhoryshyn</td>
								<td>in processing</td>
								<td>23.05.2017 16:33:07</td>
								<td><a class="btn btn-info"
									href="/webstore/admin/order/235">full info</a></td>
								<td><button class="btn btn-danger" data-toggle="modal"
										data-target="#modal-order-ch-status">change status</button></td>
							</tr>
							<tr>
								<td>2342345</td>
								<td>Nazar Hryhoryshyn</td>
								<td>in processing</td>
								<td>23.05.2017 16:33:07</td>
								<td><button class="btn btn-info">full info</button></td>
								<td><button class="btn btn-danger">change status</button></td>
							</tr>
						</table>
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
	<div class="modal fade" id="modal-product-features">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Ball features</h4>
				</div>
				<div class="modal-body small-modal-body">
					<div class="modal-body-content">
						<p style="font-weight: bold;">Ball</p>
						<table class="table-features">
							<tr>
								<td>Color</td>
								<td>black-white</td>
							</tr>
							<tr>
								<td>year made</td>
								<td>2007</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="modal-footer">
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
					<h4 class="modal-title">Ball photos</h4>
				</div>
				<div class="modal-body small-modal-body">
					<div class="modal-body-content">
						<p style="font-weight: bold;">Ball</p>
						<div id="carousel" class="carousel slide" data-ride="carousel">
							<ol class="carousel-indicators">
								<li data-target="#carousel" data-slide-to="0" class="active"></li>
								<li data-target="#carousel" data-slide-to="1"></li>
								<li data-target="#carousel" data-slide-to="2"></li>
							</ol>
							<div class="carousel-inner">
								<div class="item active">
										<button style="margin-left: 45%;" onclick="alert('hi');"><i class="fa fa-times"></i></button>
									<img src="${contextPath}/resources/img/open_box-512.png"
										alt="Slide 1">
								</div>
								<div class="item">
								<button style="margin-left: 45%;" onclick="alert('hi');"><i class="fa fa-times"></i></button>
									<img src="${contextPath}/resources/img/open_box-512.png"
										alt="Slide 2">
								</div>
								<div class="item">
								<button style="margin-left: 45%;" onclick="alert('hi');"><i class="fa fa-times"></i></button>
									<img src="${contextPath}/resources/img/open_box-512.png"
										alt="Slide 3">
								</div>
							</div>
							<a href="#carousel" class="left carousel-control"
								data-slide="prev"></a> <a href="#carousel"
								class="right carousel-control" data-slide="next"></a>
						</div>
						<br>
						<input type="file" accept="image/*">
						<br>
						<button class="btn btn-primary">add photo</button>
					</div>
				</div>
				<div class="modal-footer">
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
					<h4 class="modal-title">Ball edit</h4>
				</div>
				<div class="modal-body small-modal-body">
					<div class="modal-body-content">
						<table class="table-prod-edit">
							<tr>
								<td>Name</td>
								<td><input type="text" value="Ball"></td>
							</tr>
							<tr>
								<td>Price</td>
								<td><input type="number" min="0" value="99"></td>
							</tr>
							<tr>
								<td>Category</td>
								<td><select>
										<option selected="selected">Toys</option>
										<option>Food</option>
								</select></td>
							</tr>
							<tr>
								<td>Producer</td>
								<td><input type="text" value="Adidas"></td>
							</tr>
							<tr>
								<td>Country</td>
								<td><input type="text" value="Ukraine"></td>
							</tr>
							<tr>
								<td>Weight</td>
								<td><input type="number" value="2050"></td>
							</tr>
							<tr>
								<td>Amount on warehouse</td>
								<td><input type="number" value="45"></td>
							</tr>
						</table>
						<hr>
						<table class="table-prod-edit">
							<tr>
								<td>Color</td>
								<td><input type="text" value="Black-white"></td>
							</tr>
							<tr>
								<td>Year made</td>
								<td><input type="number" value="2010"></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary small-modal-button"
						data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modal-category-add-product">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">New Product</h4>
				</div>
				<div class="modal-body small-modal-body">
					Name:<br> <input type="text" placeholder="Product name"><br>
					Price:<br> <input type="number" placeholder="Price"><br>
					Category:<br> <select><option selected="selected">Toys</option>
						<option>Food</option></select><br> Producer:<br> <input
						type="text" placeholder="Producer"><br> Country:<br>
					<input type="text" placeholder="Country"><br> Weight:<br>
					<input type="number" min="0" value="0" placeholder="Weight"><br>
					Amount on warehouse:<br> <input type="text" value="1" min="0"
						placeholder="Product name"><br>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary small-modal-button"
						data-dismiss="modal">Save</button>
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
					<h4 class="modal-title">New Category</h4>
				</div>
				<div class="modal-body small-modal-body">
					Name:<br> <input type="text" placeholder="Category name"><br>
					Additional features:<br>
					<ul style="text-align: left;">

					</ul>
					<br> <br> new feature: <input type="text"
						placeholder="New feature name"> <br> <br>
					<button class="btn btn-primary">
						<i class="fa fa-plus"> Add feature</i>
					</button>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary small-modal-button"
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
					<h4 class="modal-title">Edit category</h4>
				</div>
				<div class="modal-body small-modal-body">
					Name:<br> <input type="text" placeholder="Category name"
						value="Toys"><br> Additional features:<br>
					<ul style="text-align: left;">
						<li>Color <a><i class="fa fa-times"></i></a></li>
						<li>Year made <a><i class="fa fa-times"></i></a></li>
					</ul>
					<br> <br> new feature: <input type="text"
						placeholder="New feature name"> <br> <br>
					<button class="btn btn-primary">
						<i class="fa fa-plus"> Add feature</i>
					</button>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary small-modal-button"
						data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
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
					<p style="font-weight: bold;">Order № 234324</p>
					<select>
						<option selected="selected">in processing</option>
						<option>preparation for sending</option>
						<option>sent</option>
						<option>arrived</option>
					</select>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary small-modal-button"
						data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>