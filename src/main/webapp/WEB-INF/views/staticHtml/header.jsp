<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
<link href="${contextPath}/resources/css/header.css"
	rel="stylesheet">
	<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<script	src="${contextPath}/resources/js/product.js"></script>
</head>
<body>
<form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
<div class="container-fluid header-background">
		<div style="width: 20%; text-align: center;"
		class="container header-block">
		<div class="header-block-sub-block">
			<a href="/webstore/"><img src="${contextPath}/resources/img/logo.png" id="logo"></a>
		</div>
	</div>
	<div class="container header-block " style="width: 50%;">
	<div class="header-block-sub-block">
		<div id="custom-search-input-container">
			
				<div class="input-group">
					<input id="prodName" type="text" class="search-query form-control button-bg-color"
						placeholder="Search" /> <span class="input-group-btn">
						<button onclick="findByName();" class="btn btn-default button-bg-color" type="button">
							<img src="${contextPath}/resources/img/search.png" height="20px;">
						</button>
					</span>
				</div>
			</div>
		</div>
	</div>
	<div style="width: 10%; text-align: center;"
		class="container header-block">
		<div class="header-block-sub-block" style="padding: 7% 0;">
		<a class="btn btn-default button-bg-color" type="button" href="/webstore/cart">
		<img src="${contextPath}/resources/img/shopping-basket-icon.png" style="width: 26px; height: 26px;">
		</a>
		</div>
	</div>
	<div class="container header-block" style="width: 20%;">
		<div class="header-block-sub-block">
			<div class="dropdown" style="margin: 10px;">
				<button class="btn btn-default dropdown-toggle button-bg-color" type="button"
					data-toggle="dropdown">
					${uname} <span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a href="/webstore/admin">Admin</a></li>
					<li><a href="/webstore/profile">Profile</a></li>
					<li class="divider"></li>
					<li><a onclick="document.forms['logoutForm'].submit()">Logout</a></li>
				</ul>
			</div>

			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<form id="logoutForm" method="POST" action="${contextPath}/logout">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</c:if>
		</div>

	</div>
	</div>


	
</body>
</html>