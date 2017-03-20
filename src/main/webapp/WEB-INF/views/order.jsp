<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>Make order</title>
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/order.css" rel="stylesheet">
<link href="${contextPath}/resources/css/location_div_styles.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
<script src="${contextPath}/resources/js/jquery-3.1.1.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/js/order.js"></script>
</head>
<body>
	<jsp:include page="staticHtml/header.jsp"></jsp:include>
	<div class="container location-block">
		<div class="location-sub-block">
			<div class="row">
				<div id="bc2" class="btn-group btn-breadcrumb">
					<a href="/webstore/" class="btn btn-default "><i class="fa fa-home"></i></a>
					<a href="#" class="btn btn-default ">Make order</a>
				</div>
			</div>
		</div>
	</div>
	<div class="container content-block">
		<div class="stage-block">
			<div id="title-1" class="stage" style="background: #259425;">Огляд
				товарів</div>
			<div id="title-2" class="stage">Доставка</div>
			<div id="title-3" class="stage">Оплата</div>
			<div id="title-4" class="stage">Завершення</div>
		</div>
		<div id="rewatch-product" style="display: static;">
			<div class="tab-content">
			<c:forEach items="${products}" var="product">
				<div class="prod-block">
					<p style="font-weight: bold;">${product.name} </p>${product.price}&#8372;
				</div>
			</c:forEach>
			</div>
			<div class="button-block">
				<button class="btn btn-primary"
					onclick="$('#rewatch-product').css('display', 'none'); $('#delivery').css('display', 'block'); $('#title-1').css('background', 'none'); $('#title-2').css('background', '#259425');">
					<i class="fa fa-arrow-right"></i>
				</button>
			</div>
		</div>
		<div id="delivery" style="display: none;">
			<div class="tab-content">
				<table class="table-delivery">
					<tr>
						<td>Type sending</td>
						<td><select id="delivery-type">
								<option>courier</option>
								<option selected>self sending</option>
						</select></td>
					</tr>
					<tr>
						<td>Receiver</td>
						<td><input id="receiver" type="text"></td>
					</tr>
					<tr>
						<td>Phone</td>
						<td><input id="phone" type="number" maxlength="14"></td>
					</tr>
					<tr>
						<td>Email</td>
						<td><input id="email" type="text"></td>
					</tr>
					<tr>
						<td>Address</td>
						<td><input id="address" type="text"></td>
					</tr>
					<tr>
						<td>Delivery price</td>
						<td>10&#8372;</td>
					</tr>
				</table>
			</div>
			<div class="button-block">
				<button class="btn btn-primary"
					onclick="$('#delivery').css('display', 'none'); $('#rewatch-product').css('display', 'block'); $('#title-2').css('background', 'none'); $('#title-1').css('background', '#259425');">
					<i class="fa fa-arrow-left"></i>
				</button>
				<button class="btn btn-primary"
					onclick="$('#delivery').css('display', 'none'); $('#payment').css('display', 'block'); $('#title-2').css('background', 'none'); $('#title-3').css('background', '#259425');">
					<i class="fa fa-arrow-right"></i>
				</button>
			</div>

		</div>
		<div id="payment" style="display: none;">
			<div class="tab-content">
				<table class="table-delivery">
					<tr>
						<td>Type payment</td>
						<td><select id="payment-type">
								<option>cash</option>
								<option selected>credit card</option>
						</select></td>
					</tr>
				</table>
				<div id="payment-block" class="payment-block">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">Payment Details</h3>
						</div>
						<div class="panel-body">
							<form role="form">
								<div class="form-group">
									<label for="cardNumber"> CARD NUMBER</label>
									<div class="input-group">
										<input type="number" maxlength="16" class="form-control" id="cardNumber"
											placeholder="Valid Card Number" required autofocus /> <span
											class="input-group-addon"><i class="fa fa-lock" aria-hidden="true"></i></span>
									</div>
								</div>
								<div>
									<div class="col-xs-7 col-md-7">
										<div class="form-group">
											<label for="expityMonth"> EXPIRY DATE</label>
											<div class="col-xs-6 col-lg-6 pl-ziro">
												<input type="number" maxlength="2" max="12" class="form-control" id="expityMonth"
													placeholder="MM" required />
											</div>
											<div class="col-xs-6 col-lg-6 pl-ziro">
												<input type="number" maxlength="2" max="12" class="form-control" id="expityYear"
													placeholder="YY" required />
											</div>
										</div>
									</div>
									<div class="col-xs-5 col-md-5 pull-right">
										<div class="form-group">
											<label for="cvCode"> CV CODE</label> <input type="password"
												class="form-control" id="cvCode" placeholder="CV" required />
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
					<ul class="nav nav-pills nav-stacked">
						<li class="active"><a href="#"><span
								class="badge pull-right"><span
									class="glyphicon glyphicon-usd"></span>${productSumPrice}</span> Final Payment</a></li>
					</ul>
				</div>
			</div>
			<div class="button-block">
				<button class="btn btn-primary"
					onclick="$('#payment').css('display', 'none'); $('#delivery').css('display', 'block'); $('#title-3').css('background', 'none'); $('#title-2').css('background', '#259425');">
					<i class="fa fa-arrow-left"></i>
				</button>
				<button class="btn btn-primary"
					onclick="setOrderValues(); $('#payment').css('display', 'none'); $('#finish').css('display', 'block'); $('#title-3').css('background', 'none'); $('#title-4').css('background', '#259425');">
					<i class="fa fa-arrow-right"></i>
				</button>
			</div>
		</div>
		<div id="finish" style="display: none;">
			<div class="tab-content" style="text-align: center;">
				<div class="result-block">
					<div style="text-align: left;">
						Products:<br>
						<ul>
						<c:forEach items="${products}" var="prod">
							<li>${prod.name} ${prod.price}&#8372;</li>
						</c:forEach>
						</ul>
						sum: ${productSumPrice}&#8372;<br><br>
						Delivery:<br>
						<p id="inf-delivery-type"></p>
						Address: <p id="inf-address"></p>
						Receiver: <p id="inf-receiver"></p>
						price delivery: 10<br>
						Payment:<br>
						type: <p id="inf-pay-type"></p>
						<div id="inf-creditcard-block">credit card: <p id="inf-credit-card"></p></div>
						
						Payable: ${productSumPrice+10}<br>
						<hr>
						<c:set var="now" value="<%=new java.util.Date()%>" />
						
						Date: <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${now}" /><br>
						Welcome to WebStore!
					</div>
				</div>
				<button class="btn btn-primary" style="width: 300px;">Get check</button>
			</div>
			<div class="button-block">
				<button class="btn btn-primary"
					onclick="$('#finish').css('display', 'none'); $('#payment').css('display', 'block'); $('#title-4').css('background', 'none'); $('#title-3').css('background', '#259425');">
					<i class="fa fa-arrow-left"></i>
				</button>
				<button onclick="makeOrder();" class="btn btn-primary">finish</button>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<jsp:include page="staticHtml/footer.jsp"></jsp:include>
	</div>
</body>
</html>