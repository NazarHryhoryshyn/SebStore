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
<link href="${contextPath}/resources/css/carousel.css"rel="stylesheet">
<link href="${contextPath}/resources/css/menu.css" rel="stylesheet">
<link href="${contextPath}/resources/css/location_div_styles.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/products_styles.css"
	rel="stylesheet">
<script src="${contextPath}/resources/js/jquery-3.1.1.js"></script>
<script	src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script	src="${contextPath}/resources/js/product.js"></script>
</head>
<body>
<jsp:include page="staticHtml/header.jsp"></jsp:include>
	<div class="container location-block">
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
					<c:if test="${currentCategory.equals(\"All category\")}">
							<li class="selected-menu-item"><a href="/webstore/products/All category-1">All category</a></li>
					</c:if>
					<c:if test="${!currentCategory.equals(\"All category\")}">
							<li><a href="/webstore/products/All category-1">All category</a></li>
					</c:if>
					<c:forEach items="${categories}" var="category">
						<c:if test="${category.equals(\"topSeparator\")}">
							<li class='menu-separator'><i class="fa fa-chevron-down" aria-hidden="true"></i></li>
						</c:if>
						<c:if test="${category.equals(\"bottomSeparator\")}">
							<li class='menu-separator'><i class="fa fa-chevron-up" aria-hidden="true"></i></li>
						</c:if>
						<c:if test="${!category.equals(\"bottomSeparator\")}">
							<c:if test="${!category.equals(\"topSeparator\")}">
								<c:if test="${currentCategory == category}">
									<li class="selected-menu-item"><a href="/webstore/products/${category}-1">${category}</a></li>
								</c:if>
								<c:if test="${currentCategory != category}">
									<li><a href="/webstore/products/${category}-1">${category}</a></li>
								</c:if>
							</c:if>
						</c:if>
					</c:forEach>
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
				<div class="row">
					<c:forEach items="${products}" var="prod" begin="0" end="2">
						<div class="col-sm-4">
							<div class="col-item">
								<div class="photo">
									<img src="http:/webstore/admin/product/photo?id=${photos[prod.id]}"/>
								</div>
								<div class="info">
									<div class="row">
										<div class="price col-md-6">
											<h5>${prod.name}</h5>
											<h5 class="price-text-color">${prod.price}&#8372;</h5>
										</div>
										<div class="rating hidden-sm col-md-6"></div>
									</div>
									<div class="separator clear-left">
										<p onclick="addProductToCart(${prod.id});" class="btn-add">
											<i class="fa fa-shopping-cart"></i><a
												href="#" class="hidden-sm">Add
												to cart</a>
										</p>
										<p class="btn-details">
											<i class="fa fa-list"></i><a
												href="/webstore/product/${prod.id}" class="hidden-sm">More
												details</a>
										</p>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="second-line-block">
					<div class="row">
						<c:if test="${amountProducts > 3}">
							<c:forEach items="${products}" var="prod" begin="3" end="5">
								<div class="col-sm-4">
									<div class="col-item">
										<div class="photo">
											<img src="/webstore/admin/product/photo?id=${photos[prod.id]}"/>
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
														href="/webstore/product/${prod.id}" class="hidden-sm">More
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
				<div class="pagination-block">
					<ul class="pagination">
						<c:if test="${page == 1 }">
							<li><a href="#">&lt;&lt;</a></li>
							<li><a href="#">&lt;</a></li>
						</c:if>
						<c:if test="${page > 1 }">
							<li><a href="/webstore/products/${currentCategory}-${1}">&lt;&lt;</a></li>
							<li><a href="/webstore/products/${currentCategory}-${page-1}">&lt;</a></li>
						</c:if>
						<c:forEach begin="${(block*5)-4}" end="${(block * 5) > totalPages ? totalPages : block * 5}" step="1" varStatus="loop">
							<c:if test="${loop.index == page}">
								<li class="active"><a href="/webstore/products/${currentCategory}-${loop.index}">${loop.index}</a></li>
							</c:if>
							<c:if test="${loop.index != page}">
								<li><a href="/webstore/products/${currentCategory}-${loop.index}">${loop.index}</a></li>
							</c:if>
						</c:forEach>
						<c:if test="${page == totalPages }">
							<li><a href="#">&gt;</a></li>
							<li><a href="#">&gt;&gt;</a></li>
						</c:if>
						<c:if test="${page < totalPages }">
							<li><a href="/webstore/products/${currentCategory}-${page+1}">&gt;</a></li>
							<li><a href="/webstore/products/${currentCategory}-${totalPages}">&gt;&gt;</a></li>
						</c:if>
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