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
<script src="${contextPath}/resources/js/jquery-3.1.1.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/js/product.js"></script>
</head>
<body>
	<div class="container-fluid header-background">
		<jsp:include page="staticHtml/header.jsp"></jsp:include>
	</div>
	<div class="container location-block">
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
					<li><a href="products/allCategory">All category</a></li>
					<c:forEach items="${categories}" var="category">
						<li><a href="products/${category}">${category}</a></li>
					</c:forEach>
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
								<c:forEach items="${popularProducts}" var="prod" begin="0" end="2">
									<div class="col-sm-4">
										<div class="col-item">
											<div class="photo">
												<img src="/webstore/admin/product/photo?id=${productPhoto[prod.id]}"
													class="img-responsive"/>
											</div>
										<div class="info">
											<div class="row">
												<div class="price col-md-6">
													<h5>${prod.name}</h5>
													<h5 class="price-text-color">${prod.price}&#8372;</h5>
												</div>
												<div class="rating hidden-sm col-md-6">
												</div>
											</div>
											<div class="separator clear-left">
												<p onclick="addProductToCart(${prod.id});" class="btn-add">
													<i class="fa fa-shopping-cart"></i><a
													href="#" class="hidden-sm">Add
													to cart</a>
												</p>
												<p class="btn-details">
													<i class="fa fa-list"></i><a
													href="product/${prod.id}" class="hidden-sm">More
													details</a>
												</p>
											</div>
											<div class="clearfix">
											</div>
										</div>
										</div>
									</div>
								</c:forEach>
								</div>
							</div>
							<div class="item">
								<div class="row">
								<c:if test="${amountProducts > 3}">
									<c:forEach items="${popularProducts}" var="prod" begin="3" end="5">
										<div class="col-sm-4">
											<div class="col-item">
												<div class="photo">
													<img src="/webstore/admin/product/photo?id=${productPhoto[prod.id]}"
														class="img-responsive"/>
												</div>
												<div class="info">
													<div class="row">
														<div class="price col-md-6">
															<h5>${prod.name}</h5>
															<h5 class="price-text-color">${prod.price}&#8372;</h5>
														</div>
														<div class="rating hidden-sm col-md-6">
														</div>
													</div>
													<div class="separator clear-left">
														<p onclick="addProductToCart(${prod.id});" class="btn-add">
															<i class="fa fa-shopping-cart"></i><a
																href="#" class="hidden-sm">Add
																to cart</a>
														</p>
														<p class="btn-details">
															<i class="fa fa-list"></i><a
																href="product/${prod.id}" class="hidden-sm">More
																details</a>
														</p>
													</div>
													<div class="clearfix"></div>
												</div>
											</div>
										</div>
									</c:forEach>
								</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${amountProducts > 6}">
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
									<c:forEach items="${popularProducts}" var="prod" begin="6" end="8">
									<div class="col-sm-4">
										<div class="col-item">
											<div class="photo">
												<img src="/webstore/admin/product/photo?id=${productPhoto[prod.id]}"
													class="img-responsive"/>
											</div>
										<div class="info">
											<div class="row">
												<div class="price col-md-6">
													<h5>${prod.name}</h5>
													<h5 class="price-text-color">${prod.price}&#8372;</h5>
												</div>
												<div class="rating hidden-sm col-md-6">
												</div>
											</div>
											<div class="separator clear-left">
												<p onclick="addProductToCart(${prod.id});" class="btn-add">
													<i class="fa fa-shopping-cart"></i><a
													href="#" class="hidden-sm">Add
													to cart</a>
												</p>
												<p class="btn-details">
													<i class="fa fa-list"></i><a
													href="product/${prod.id}" class="hidden-sm">More
													details</a>
												</p>
											</div>
											<div class="clearfix">
											</div>
										</div>
										</div>
									</div>
								</c:forEach>
								</div>
							</div>
							<div class="item">
								<div class="row">
								<c:if test="${amountProducts > 9}">
									<c:forEach items="${popularProducts}" var="prod" begin="9" end="11">
										<div class="col-sm-4">
											<div class="col-item">
												<div class="photo">
													<img src="/webstore/admin/product/photo?id=${productPhoto[prod.id]}"
														class="img-responsive"/>
												</div>
												<div class="info">
													<div class="row">
														<div class="price col-md-6">
															<h5>${prod.name}</h5>
															<h5 class="price-text-color">${prod.price}&#8372;</h5>
														</div>
														<div class="rating hidden-sm col-md-6">
														</div>
													</div>
													<div class="separator clear-left">
														<p onclick="addProductToCart(${prod.id});" class="btn-add">
															<i class="fa fa-shopping-cart"></i><a
																href="#" class="hidden-sm">Add
																to cart</a>
														</p>
														<p class="btn-details">
															<i class="fa fa-list"></i><a
																href="product/${prod.id}" class="hidden-sm">More
																details</a>
														</p>
													</div>
													<div class="clearfix"></div>
												</div>
											</div>
										</div>
									</c:forEach>
								</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
				</c:if>
				
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<jsp:include page="staticHtml/footer.jsp"></jsp:include>
	</div>

</body>
</html>