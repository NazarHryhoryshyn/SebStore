<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
<link href="${contextPath}/resources/css/header_styles.css"
	rel="stylesheet">
</head>
<body>
	<div style="width: 20%; text-align: center;"
		class="container header-block">
		<div class="header-block-sub-block">
			<img src="${contextPath}/resources/img/logo.png" id = "logo">
		</div>
	</div>
	<div class="container header-block "
		style="width: 50%;">
		<div id="custom-search-input-container">
			<div class="header-block-sub-block">
				<div class="input-group">
					<input type="text" class="  search-query form-control"
						placeholder="Search" /> <span class="input-group-btn">
						<button class="btn btn-default" type="button">
							<img src="${contextPath}/resources/img/search.png" height="20px;">
						</button>
					</span>
				</div>
			</div>
		</div>
	</div>
	<div class="container header-block" style="width: 30%;">
	<div class="header-block-sub-block">
	<c:if test="${isAdmin}"><a href="admin">Admin</a></c:if>
	<c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <a onclick="document.forms['logoutForm'].submit()">Logout</a>
        cart | my profile |
    </c:if> 
    language
	</div>
	</div>
</body>
</html>