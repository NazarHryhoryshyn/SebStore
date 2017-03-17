<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>Order №987890</title>
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/order.css" rel="stylesheet">
<link href="${contextPath}/resources/css/location_div_styles.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
</head>
<body>
	<jsp:include page="staticHtml/header.jsp"></jsp:include>
	<div class="container location-block">
		<div class="location-sub-block">
			<div class="row">
				<div id="bc2" class="btn-group btn-breadcrumb">
					<a href="#" class="btn btn-default "><i class="fa fa-home"></i></a>
					<a href="#" class="btn btn-default ">admin</a> <a href="#"
						class="btn btn-default ">order № ${order.id}</a>
				</div>
			</div>
		</div>
	</div>
	<div class="container content-block" style="padding: 20px;">
		<div class="order-info-column">
			<table>
				<tr>
					<td>User</td>
					<td>${order.user.lastname} ${order.user.firstname}</td>
				</tr>
				<tr>
					<td>Receiver</td>
					<td>${order.receiver}</td>
				</tr>
				<tr>
					<td>Phone</td>
					<td>${order.phone}</td>
				</tr>
				<tr>
					<td>Email</td>
					<td>${order.email}</td>
				</tr>
			</table>
		</div>
		<div class="order-info-column">
		<table>
				<tr>
					<td>Delivery type</td>
					<td>${order.deliveryType}</td>
				</tr>
				<tr>
					<td>Delivery price</td>
					<td>${order.deliveryPrice}&#8372;</td>
				</tr>
				<tr>
					<td>Address</td>
					<td>${order.address}</td>
				</tr>
			</table>
		</div>
		<div class="order-info-column">
		<table>
				<tr>
					<td>Payment type</td>
					<td>${order.paymentType}</td>
				</tr>
				<tr>
					<td>Card number</td>
					<td>${order.cardNumber}</td>
				</tr>
			</table>
		</div>
		<div class="order-info-column" style="clear: both;">
			<table>
				<tr>
					<td>Date</td>
					<td>${order.date}</td>
				</tr>
				<tr>
					<td>Status</td>
					<td>${order.status}</td>
				</tr>
			</table>
		</div>
		<div class="" style="clear: both;">
		<br><p>Products in order:</p>
		<table>
			<c:forEach items="${order.products}" var="product">
				<tr>
					<td>${product.name}</td>
					<td>${product.price}&#8372;</td>
				</tr>
			</c:forEach>
		</table>
		</div>
	</div>
	<div class="container-fluid">
		<jsp:include page="staticHtml/footer.jsp"></jsp:include>
	</div>
</body>
</html>