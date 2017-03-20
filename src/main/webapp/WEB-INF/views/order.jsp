<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
				<div class="prod-block">
					<p style="font-weight: bold;">Ball</p>
					$99<br> 1 pcs.
				</div>
				<div class="prod-block">
					<p style="font-weight: bold;">Ball</p>
					$99<br> 1 pcs.
				</div>
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
						<td><select>
								<option>courier</option>
								<option>self sending</option>
						</select></td>
					</tr>
					<tr>
						<td>Receiver</td>
						<td><input type="text"></td>
					</tr>
					<tr>
						<td>Phone</td>
						<td><input type="number" maxlength="14"></td>
					</tr>
					<tr>
						<td>Email</td>
						<td><input type="text"></td>
					</tr>
					<tr>
						<td>Address</td>
						<td><input type="text"></td>
					</tr>
					<tr>
						<td>Delivery price</td>
						<td>&#8372; 10</td>
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
						<td><select>
								<option>cash</option>
								<option>credit card</option>
						</select></td>
					</tr>
				</table>
				<div class="payment-block">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">Payment Details</h3>
						</div>
						<div class="panel-body">
							<form role="form">
								<div class="form-group">
									<label for="cardNumber"> CARD NUMBER</label>
									<div class="input-group">
										<input type="text" class="form-control" id="cardNumber"
											placeholder="Valid Card Number" required autofocus /> <span
											class="input-group-addon"><i class="fa fa-lock" aria-hidden="true"></i></span>
									</div>
								</div>
								<div>
									<div class="col-xs-7 col-md-7">
										<div class="form-group">
											<label for="expityMonth"> EXPIRY DATE</label>
											<div class="col-xs-6 col-lg-6 pl-ziro">
												<input type="text" class="form-control" id="expityMonth"
													placeholder="MM" required />
											</div>
											<div class="col-xs-6 col-lg-6 pl-ziro">
												<input type="text" class="form-control" id="expityYear"
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
									class="glyphicon glyphicon-usd"></span>4200</span> Final Payment</a></li>
					</ul>
					<br /> <a href="http://www.jquery2dotnet.com"
						class="btn btn-success btn-lg btn-block" role="button">Pay</a>
				</div>
			</div>
			<div class="button-block">
				<button class="btn btn-primary"
					onclick="$('#payment').css('display', 'none'); $('#delivery').css('display', 'block'); $('#title-3').css('background', 'none'); $('#title-2').css('background', '#259425');">
					<i class="fa fa-arrow-left"></i>
				</button>
				<button class="btn btn-primary"
					onclick="$('#payment').css('display', 'none'); $('#finish').css('display', 'block'); $('#title-3').css('background', 'none'); $('#title-4').css('background', '#259425');">
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
							<li>Ball 99</li>
							<li>laptop 99</li>
						</ul>
						sum: 198<br><br>
						Delivery:<br>
						Courier<br>
						Address: Івано-Франківськ вулювовчинецька буд 1<br>
						Receiver: Григоришин Назар Іванович<br>
						price delivery: 10<br><br>
						Payment:<br>
						credit card: 2340 2340 3290 2345<br><br>
						
						Payable: 208<br>
						<hr>
						Date: 24.08.2017 19:09:03<br>
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
				<button class="btn btn-primary">finish</button>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<jsp:include page="staticHtml/footer.jsp"></jsp:include>
	</div>
</body>
</html>