<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>Popular products</title>
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
<link href="${contextPath}/resources/css/carousel.css"
	rel="stylesheet">
	<link href="${contextPath}/resources/css/menu.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/header_styles.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/location_div_styles.css"
	rel="stylesheet">
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script	src="${contextPath}/resources/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid header-background">
		<jsp:include page="staticHtml/header.jsp"></jsp:include>
	</div>
	<div class="container colation-block">
		<div class="location-sub-block">
			<div class="row">
				<div id="bc2" class="btn-group btn-breadcrumb">
					<a href="#" class="btn btn-default "><i class="fa fa-home"></i></a>
					<a href="#" class="btn btn-default ">popular products</a>
				</div>
			</div>
		</div>
	</div>
	<div class="container content-block">
		<div class="container content-sub-block">
			<div class="category-block">
				<div id="menu">
					<ul class="menu-ul">
						<li><a href="products">CSS Главная</a></li>
						<li>
							<a href="#">CSS Меню</a>
						</li>
						<li>
							<a href="#">CSS Меню</a>
						</li><li>
							<a href="#">CSS Меню</a>
						</li><li>
							<a href="#">CSS Меню</a>
						</li><li>
							<a href="#">CSS Меню</a>
						</li><li>
							<a href="#">CSS Меню</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="product-block">
				<div class="row product-sub-block" >
					<div class="row carousel-buttons">
						<div class="col-md-9"></div>
						<div class="col-md-3">
							<!-- Controls -->
							<div class="controls pull-right hidden-xs">
								<a class="left fa fa-chevron-left btn btn-success"
									href="#carousel-example" data-slide="prev"></a><a
									class="right fa fa-chevron-right btn btn-success"
									href="#carousel-example" data-slide="next"></a>
							</div>
						</div>
					</div>
					<div id="carousel-example" class="carousel slide hidden-xs carousel-styles"
						data-ride="carousel">
						<!-- Wrapper for slides -->
						<div class="carousel-inner">
							<div class="item active">
								<div class="row">
									<div class="col-sm-4">
										<div class="col-item">
											<div class="photo">
												<img src="http://placehold.it/350x260"
													class="img-responsive" alt="a" />
											</div>
											<div class="info">
												<div class="row">
													<div class="price col-md-6">
														<h5>Oleg</h5>
														<h5 class="price-text-color">$199.99</h5>
													</div>
													<div class="rating hidden-sm col-md-6">
													</div>
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
												<img src="http://placehold.it/350x260"
													class="img-responsive" alt="a" />
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
												<img src="http://placehold.it/350x260"
													class="img-responsive" alt="a" />
											</div>
											<div class="info">
												<div class="row">
													<div class="price col-md-6">
														<h5>Next Sample Product</h5>
														<h5 class="price-text-color">$149.99</h5>
													</div>
													<div class="rating hidden-sm col-md-6">
													</div>
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
							<div class="item">
								<div class="row">
									<div class="col-sm-4">
										<div class="col-item">
											<div class="photo">
												<img src="http://placehold.it/350x260"
													class="img-responsive" alt="a" />
											</div>
											<div class="info">
												<div class="row">
													<div class="price col-md-6">
														<h5>Product with Variants</h5>
														<h5 class="price-text-color">$199.99</h5>
													</div>
													<div class="rating hidden-sm col-md-6">
													</div>
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
												<img src="http://placehold.it/350x260"
													class="img-responsive" alt="a" />
											</div>
											<div class="info">
												<div class="row">
													<div class="price col-md-6">
														<h5>Grouped Product</h5>
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
												<img src="http://placehold.it/350x260"
													class="img-responsive" alt="a" />
											</div>
											<div class="info">
												<div class="row">
													<div class="price col-md-6">
														<h5>Product with Variants</h5>
														<h5 class="price-text-color">$149.99</h5>
													</div>
													<div class="rating hidden-sm col-md-6">
													</div>
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
						</div>
					</div>
				</div>
				<div class="row product-sub-block">
					<div class="row carousel-buttons">
						<div class="col-md-9"></div>
						<div class="col-md-3">
							<!-- Controls -->
							<div class="controls pull-right hidden-xs">
								<a class="left fa fa-chevron-left btn btn-success"
									href="#carousel-example-generic" data-slide="prev"></a><a
									class="right fa fa-chevron-right btn btn-success"
									href="#carousel-example-generic" data-slide="next"></a>
							</div>
						</div>
					</div>
					<div id="carousel-example-generic" class="carousel slide hidden-xs carousel-styles"
						data-ride="carousel">
						<!-- Wrapper for slides -->
						<div class="carousel-inner">
							<div class="item active">
								<div class="row">
									<div class="col-sm-4">
										<div class="col-item">
											<div class="photo">
												<img src="http://placehold.it/350x260"
													class="img-responsive" alt="a" />
											</div>
											<div class="info">
												<div class="row">
													<div class="price col-md-6">
														<h5>Sample Product</h5>
														<h5 class="price-text-color">$199.99</h5>
													</div>
													<div class="rating hidden-sm col-md-6">
													</div>
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
												<img src="http://placehold.it/350x260"
													class="img-responsive" alt="a" />
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
												<img src="http://placehold.it/350x260"
													class="img-responsive" alt="a" />
											</div>
											<div class="info">
												<div class="row">
													<div class="price col-md-6">
														<h5>Next Sample Product</h5>
														<h5 class="price-text-color">$149.99</h5>
													</div>
													<div class="rating hidden-sm col-md-6">
													</div>
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
							<div class="item">
								<div class="row">
									<div class="col-sm-4">
										<div class="col-item">
											<div class="photo">
												<img src="http://placehold.it/350x260"
													class="img-responsive" alt="a" />
											</div>
											<div class="info">
												<div class="row">
													<div class="price col-md-6">
														<h5>Product with Variants</h5>
														<h5 class="price-text-color">$199.99</h5>
													</div>
													<div class="rating hidden-sm col-md-6">
													</div>
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
												<img src="http://placehold.it/350x260"
													class="img-responsive" alt="a" />
											</div>
											<div class="info">
												<div class="row">
													<div class="price col-md-6">
														<h5>Grouped Product</h5>
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
												<img src="http://placehold.it/350x260"
													class="img-responsive" alt="a" />
											</div>
											<div class="info">
												<div class="row">
													<div class="price col-md-6">
														<h5>Product with Variants</h5>
														<h5 class="price-text-color">$149.99</h5>
													</div>
													<div class="rating hidden-sm col-md-6">
													</div>
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
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<jsp:include page="staticHtml/footer.jsp"></jsp:include>
	</div>

</body>
</html>