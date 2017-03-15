<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>Cart</title>
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
<link href="${contextPath}/resources/css/location_div_styles.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/cart_styles.css"
	rel="stylesheet">
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script	src="${contextPath}/resources/js/bootstrap.min.js"></script>
<link href="${contextPath}/resources/css/admin.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="staticHtml/header.jsp"></jsp:include>
	
	<div class="container location-block">
		<div class="location-sub-block">
			<div class="row">
				<div id="bc2" class="btn-group btn-breadcrumb">
					<a href="#" class="btn btn-default "><i class="fa fa-home"></i></a>
					<a href="#" class="btn btn-default ">cart</a>
				</div>
			</div>
		</div>
	</div>
	<div class="container content-block">
		<div class="products">
			<div class="prod">
				<div class="product-photo">
					<img alt="" src="${contextPath}/resources/img/open_box-512.png">
				</div>
				<div class="product-description-block">
					<div class="product-description-text">
						<div class="column-product-name">Ball</div>
						<div class="column">Price: $29</div>
						<div class="column">Amount: 1</div>
						<div class="column">Sum: $29</div>
					</div>
					<div class="container-remove">
						<a class="btn btn-danger"><i class="fa fa-times fa-3x" aria-hidden="true"></i></a>
					</div>
				</div>
			</div>
			<div class="prod">
				<div class="product-photo">
					<img alt="" src="${contextPath}/resources/img/open_box-512.png">
				</div>
				<div class="product-description-block">
					<div class="product-description-text">
						<div class="column-product-name">Ball</div>
						<div class="column">Price: $29</div>
						<div class="column">Amount: 1</div>
						<div class="column">Sum: $29</div>
					</div>
					<div class="container-remove">
						<a class="btn btn-danger"><i class="fa fa-times fa-3x" aria-hidden="true"></i></a>
					</div>
				</div>
			</div>
			<div class="count-price">
				<div>Count price: $58</div>
			</div>
			<div class="make-order">
				<a class="btn btn-primary" href="order">Make an order</a>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<jsp:include page="staticHtml/footer.jsp"></jsp:include>
	</div>
</body>
</html>