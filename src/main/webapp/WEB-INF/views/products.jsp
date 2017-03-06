<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>Products</title>
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
<link href="${contextPath}/resources/css/carousel.css" rel="stylesheet">
<link href="${contextPath}/resources/css/menu.css" rel="stylesheet">
<link href="${contextPath}/resources/css/location_div_styles.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/products_styles.css"
	rel="stylesheet">
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script	src="${contextPath}/resources/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="staticHtml/header.jsp"></jsp:include>
	<div class="container colation-block">
		<div class="location-sub-block">
			<div class="row">
				<div id="bc2" class="btn-group btn-breadcrumb">
					<a href="#" class="btn btn-default "><i class="fa fa-home"></i></a>
					<a href="#" class="btn btn-default ">products</a>
				</div>
			</div>
		</div>
	</div>
	<div class="container content-block">
		<div class="container content-sub-block">
			<div class="category-block">
				<div id="menu">
					<ul class="menu-ul">
						<li><a href="#">All</a></li>
						<li><a href="#">Category 1</a></li>
						<li><a href="#">Category 2</a></li>
						<li><a href="#">Category 3</a></li>
						<li><a href="#">Category 4</a></li>
						<li><a href="#">Category 5</a></li>
						<li><a href="#">Category 6</a></li>
					</ul>
				</div>
				<div style="border-bottom: 3px solid #B3FFB3; margin-top: 20px;"></div>
				<div class="filters">
					<div class="title-filters">Filters</div>
					<div class="filters-container">
						<div class="parameter-block">
							<div class="parameter-name-block">name</div>
							<div class="parameter-input-block">
								<input type="text" name="name">
							</div>
						</div>
						<div class="parameter-block">
							<div class="parameter-name-block">producer</div>
							<div class="parameter-input-block">
								<input type="text" name="producer">
							</div>
						</div>
						<div class="parameter-interval-block">
							<div>price</div>
							<div>
								<div class="parameter-interval-input-container"
									style="float: left;">
									<input type="number" value="1" min="1" max="200" name="price_min">
								</div>
								-
								<div class="parameter-interval-input-container"
									style="float: right;">
									<input type="number" value="10" min="1" max="200" name="price_max">
								</div>
							</div>
						</div>
						<div class="parameter-block" style="height: 45px;">
								<button type="button" class="btn btn-success mybutton">Go</button>
						</div>
					</div>
				</div>
			</div>
			<div class="product-block">
				<div>
					<div class="row">
						<div class="col-sm-4">
							<div class="col-item">
								<div class="photo">
									<img src="http://placehold.it/350x260" class="img-responsive"
										alt="a" />
								</div>
								<div class="info">
									<div class="row">
										<div class="price col-md-6">
											<h5>Oleg</h5>
											<h5 class="price-text-color">$199.99</h5>
										</div>
										<div class="rating hidden-sm col-md-6"></div>
									</div>
									<div class="separator clear-left">
										<p class="btn-add">
											<i class="fa fa-shopping-cart"></i><a
												href="http://www.jquery2dotnet.com" class="hidden-sm">Add
												to cart</a>
										</p>
										<p class="btn-details">
											<i class="fa fa-list"></i><a
												href="/webstore/product/2" class="hidden-sm">More
												details</a>
										</p>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="col-item">
								<div class="photo">
									<img src="http://placehold.it/350x260" class="img-responsive"
										alt="a" />
								</div>
								<div class="info">
									<div class="row">
										<div class="price col-md-6">
											<h5>Product Example</h5>
											<h5 class="price-text-color">$249.99</h5>
										</div>
										<div class="rating hidden-sm col-md-6"></div>
									</div>
									<div class="separator clear-left">
										<p class="btn-add">
											<i class="fa fa-shopping-cart"></i><a
												href="http://www.jquery2dotnet.com" class="hidden-sm">Add
												to cart</a>
										</p>
										<p class="btn-details">
											<i class="fa fa-list"></i><a
												href="http://www.jquery2dotnet.com" class="hidden-sm">More
												details</a>
										</p>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="col-item">
								<div class="photo">
									<img src="http://placehold.it/350x260" class="img-responsive"
										alt="a" />
								</div>
								<div class="info">
									<div class="row">
										<div class="price col-md-6">
											<h5>Next Sample Product</h5>
											<h5 class="price-text-color">$149.99</h5>
										</div>
										<div class="rating hidden-sm col-md-6"></div>
									</div>
									<div class="separator clear-left">
										<p class="btn-add">
											<i class="fa fa-shopping-cart"></i><a
												href="http://www.jquery2dotnet.com" class="hidden-sm">Add
												to cart</a>
										</p>
										<p class="btn-details">
											<i class="fa fa-list"></i><a
												href="http://www.jquery2dotnet.com" class="hidden-sm">More
												details</a>
										</p>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="second-line-block">
					<div class="row">
						<div class="col-sm-4">
							<div class="col-item">
								<div class="photo">
									<img src="http://placehold.it/350x260" class="img-responsive"
										alt="a" />
								</div>
								<div class="info">
									<div class="row">
										<div class="price col-md-6">
											<h5>Sample Product</h5>
											<h5 class="price-text-color">$199.99</h5>
										</div>
										<div class="rating hidden-sm col-md-6"></div>
									</div>
									<div class="separator clear-left">
										<p class="btn-add">
											<i class="fa fa-shopping-cart"></i><a
												href="http://www.jquery2dotnet.com" class="hidden-sm">Add
												to cart</a>
										</p>
										<p class="btn-details">
											<i class="fa fa-list"></i><a
												href="http://www.jquery2dotnet.com" class="hidden-sm">More
												details</a>
										</p>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="col-item">
								<div class="photo">
									<img src="http://placehold.it/350x260" class="img-responsive"
										alt="a" />
								</div>
								<div class="info">
									<div class="row">
										<div class="price col-md-6">
											<h5>Product Example</h5>
											<h5 class="price-text-color">$249.99</h5>
										</div>
										<div class="rating hidden-sm col-md-6"></div>
									</div>
									<div class="separator clear-left">
										<p class="btn-add">
											<i class="fa fa-shopping-cart"></i><a
												href="http://www.jquery2dotnet.com" class="hidden-sm">Add
												to cart</a>
										</p>
										<p class="btn-details">
											<i class="fa fa-list"></i><a
												href="http://www.jquery2dotnet.com" class="hidden-sm">More
												details</a>
										</p>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="col-item">
								<div class="photo">
									<img src="http://placehold.it/350x260" class="img-responsive"
										alt="a" />
								</div>
								<div class="info">
									<div class="row">
										<div class="price col-md-6">
											<h5>Next Sample Product</h5>
											<h5 class="price-text-color">$149.99</h5>
										</div>
										<div class="rating hidden-sm col-md-6"></div>
									</div>
									<div class="separator clear-left">
										<p class="btn-add">
											<i class="fa fa-shopping-cart"></i><a
												href="http://www.jquery2dotnet.com" class="hidden-sm">Add
												to cart</a>
										</p>
										<p class="btn-details">
											<i class="fa fa-list"></i><a
												href="http://www.jquery2dotnet.com" class="hidden-sm">More
												details</a>
										</p>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="pagination-block">
					<ul class="pagination">
						<li class="disabled"><a href="#">«</a></li>
						<li class="active"><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">»</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<jsp:include page="staticHtml/footer.jsp"></jsp:include>
	</div>
</body>
</html>