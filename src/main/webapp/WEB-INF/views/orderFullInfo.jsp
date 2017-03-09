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
	<div class="container colation-block">
		<div class="location-sub-block">
			<div class="row">
				<div id="bc2" class="btn-group btn-breadcrumb">
					<a href="#" class="btn btn-default "><i class="fa fa-home"></i></a>
					<a href="#" class="btn btn-default ">admin</a> <a href="#"
						class="btn btn-default ">order №34564</a>
				</div>
			</div>
		</div>
	</div>
	<div class="container content-block" style="padding: 20px;">
		<div class="order-info-column">
			<table>
				<tr>
					<td>User</td>
					<td>Hryhoryshyn Nazar</td>
				</tr>
				<tr>
					<td>Receiver</td>
					<td>Hryhoryshyn Ivan</td>
				</tr>
				<tr>
					<td>Phone</td>
					<td>095645654</td>
				</tr>
				<tr>
					<td>Email</td>
					<td>naz.@gmail.ru</td>
				</tr>
			</table>
		</div>
		<div class="order-info-column">
		<table>
				<tr>
					<td>Delivery type</td>
					<td>self sending</td>
				</tr>
				<tr>
					<td>Delivery price</td>
					<td>10</td>
				</tr>
				<tr>
					<td>Address</td>
					<td>Ivano-Frankivsk Vovchinetska street</td>
				</tr>
			</table>
		</div>
		<div class="order-info-column">
		<table>
				<tr>
					<td>Payment type</td>
					<td>credit card</td>
				</tr>
				<tr>
					<td>Card number</td>
					<td>7890 8979 6776 5776</td>
				</tr>
			</table>
		</div>
		<div class="order-info-column" style="clear: both;">
		<table>
				<tr>
					<td>Date</td>
					<td>24.08.1997 01:04:01</td>
				</tr>
				<tr>
					<td>Status</td>
					<td>in processing</td>
				</tr>
				<tr>
					<td>Payed Money</td>
					<td>$208</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="container-fluid">
		<jsp:include page="staticHtml/footer.jsp"></jsp:include>
	</div>
</body>
</html>