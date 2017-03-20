<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>profile</title>
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
<link href="${contextPath}/resources/css/location_div_styles.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/profile.css" rel="stylesheet">
<script src="${contextPath}/resources/js/jquery-3.1.1.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/js/profile.js"></script>
<link href="${contextPath}/resources/css/admin.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="staticHtml/header.jsp"></jsp:include>
	<div class="container location-block">
		<div class="location-sub-block">
			<div class="row">
				<div id="bc2" class="btn-group btn-breadcrumb">
					<a href="/webstore/" class="btn btn-default "><i class="fa fa-home"></i></a>
					<a href="#" class="btn btn-default ">profile</a>
				</div>
			</div>
		</div>
	</div>
	<div class="container content-block">
		<div class="column">
			<div class="prop-name">First name:</div>
			<div class="prop-value">${user.firstname}</div>
			<div class="prop-butt">
				<button class="btn btn-danger" data-toggle="modal"
					data-target="#modal-1">
					<i class="fa fa-pencil" aria-hidden="true"></i>
				</button>
			</div>
		</div>
		<div class="column">
			<div class="prop-name">Last name:</div>
			<div class="prop-value">${user.lastname}</div>
			<div class="prop-butt">
				<button class="btn btn-danger" data-toggle="modal"
					data-target="#modal-2">
					<i class="fa fa-pencil" aria-hidden="true"></i>
				</button>
			</div>
		</div>
		<div class="column">
			<div class="prop-name">Email:</div>
			<div class="prop-value">${user.email}</div>
			<div class="prop-butt">
				<button class="btn btn-danger" data-toggle="modal"
					data-target="#modal-3">
					<i class="fa fa-pencil" aria-hidden="true"></i>
				</button>
			</div>
		</div>
		<div class="column">
			<div class="prop-name">Telephone:</div>
			<div class="prop-value">${user.telephone}</div>
			<div class="prop-butt">
				<button class="btn btn-danger" data-toggle="modal"
					data-target="#modal-4">
					<i class="fa fa-pencil" aria-hidden="true"></i>
				</button>
			</div>
		</div>
		<div class="column">
			<div class="prop-name">Sex:</div>
			<div class="prop-value">${user.sex}</div>
			<div class="prop-butt">
				<button class="btn btn-danger" data-toggle="modal"
					data-target="#modal-5">
					<i class="fa fa-pencil" aria-hidden="true"></i>
				</button>
			</div>
		</div>
		<div class="column">
			<div class="prop-name">
				<button class="btn btn-info" data-toggle="modal"
					data-target="#modal-6">Change password</button>
			</div>
		</div>
		<div class="column">
			<div class="prop-name">
				<button class="btn btn-info" data-toggle="modal"
					data-target="#modal-7">My orders</button>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<jsp:include page="staticHtml/footer.jsp"></jsp:include>
	</div>
	<div class="modal fade" id="modal-1">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change first name</h4>
				</div>
				<div class="modal-body small-modal-body">
					<input id="newFirstName" type="text" value="${user.firstname}">
				</div>
				<div class="modal-footer">
					<button class="btn btn-success small-modal-button"
						onclick="changeFirstName(); location.reload();"
						data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="modal-2">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change last name</h4>
				</div>
				<div class="modal-body small-modal-body">
					<input id="newLastName" type="text" value="${user.lastname}">
				</div>
				<div class="modal-footer">
					<button class="btn btn-success small-modal-button"
						onclick="changeLastName(); location.reload();"
						data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="modal-3">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change email</h4>
				</div>
				<div class="modal-body small-modal-body">
					<input id="newEmail" type="text" value="${user.email}">
				</div>
				<div class="modal-footer">
					<button class="btn btn-success small-modal-button"
						onclick="changeEmail(); location.reload();" data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="modal-4">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change telephone</h4>
				</div>
				<div class="modal-body small-modal-body">
					<input id="newTelephone" type="text" value="${user.telephone}">
				</div>
				<div class="modal-footer">
					<button class="btn btn-success small-modal-button"
						onclick="changeTelephone(); location.reload();"
						data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="modal-5">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change sex</h4>
				</div>
				<div class="modal-body small-modal-body">
					<c:if test="${user.sex == 'male'}">
						<select id="newSex">
							<option selected="selected">male</option>
							<option>female</option>
						</select>
					</c:if>
					<c:if test="${user.sex == 'female'}">
						<select id="newSex">
							<option>male</option>
							<option selected="selected">female</option>
						</select>
					</c:if>
				</div>
				<div class="modal-footer">
					<button class="btn btn-success small-modal-button"
						onclick="changeSex(); location.reload();" data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="modal-6">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change password</h4>
				</div>
				<div class="modal-body small-modal-body">
					old password:<br> <input id="oldPass" type="text"><br>
					new password:<br> <input id="newPass" type="text"><br>
					repeat new password: <br> <input id="newPass2" type="text"><br>
				</div>
				<div class="modal-footer">
					<button class="btn btn-success small-modal-button"
						onclick="changePassword();" data-dismiss="modal">Save</button>
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="modal-7">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">My orders</h4>
				</div>
				<div class="modal-body small-modal-body">
				
					<c:forEach items="${user.orders}" var="o">
						<div class="order-block">
							<p>№ ${o.id}</p>
							<div class="order-block">
								<ul>
									<c:forEach items="${o.products}" var="p">
										<li>${p.name} ${p.price}</li>
									</c:forEach>
								</ul>
								Count price: $198
							</div>
						</div>
					</c:forEach>
				
				</div>
				<div class="modal-footer">
					<button class="btn btn-danger small-modal-button"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>