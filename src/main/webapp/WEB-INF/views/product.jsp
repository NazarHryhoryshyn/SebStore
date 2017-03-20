<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>Product name</title>
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
<link href="${contextPath}/resources/css/location_div_styles.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/product_styles.css"
	rel="stylesheet">

<link href="${contextPath}/resources/css/tab_styles.css"
	rel="stylesheet">
<script src="${contextPath}/resources/js/jquery-3.1.1.js"></script>
<script	src="${contextPath}/resources/js/bootstrap.min.js"></script>
<link href="${contextPath}/resources/css/admin.css" rel="stylesheet">
<script src="${contextPath}/resources/js/product.js"></script>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#mytable #checkall").click(function() {
				if ($("#mytable #checkall").is(':checked')) {
					$("#mytable input[type=checkbox]").each(function() {
						$(this).prop("checked", true);
					});

				} else {
					$("#mytable input[type=checkbox]").each(function() {
						$(this).prop("checked", false);
					});
				}
			});

			$("[data-toggle=tooltip]").tooltip();
		});
	</script>
	<jsp:include page="staticHtml/header.jsp"></jsp:include>

	<div class="container location-block">
		<div class="location-sub-block">
			<div class="row">
				<div id="bc2" class="btn-group btn-breadcrumb">
					<a href="/webstore/" class="btn btn-default "><i class="fa fa-home"></i></a>
					<a href="/webstore/products/${product.category.name}-1-all-all-0-0" class="btn btn-default ">${product.category.name}</a> 
					<a href="#" class="btn btn-default ">${product.name}</a>
				</div>
			</div>
		</div>
	</div>
	<div class="container content-block">
		<div class="top-block">
			<div class="image-side">
				<div id="carousel" class="carousel slide" data-ride="carousel">
					<ol class="carousel-indicators">
						<c:if test="${amountPhotos == 0}">
							<li data-target="#carousel" data-slide-to="0" class="active"></li>
						</c:if>						
						<c:if test="${amountPhotos != 0}">
							<c:forEach begin="0" end="${amountPhotos-1}" step="1" var="ind">
								<c:if test="${ind == 0}">
									<li data-target="#carousel" data-slide-to="${ind}" class="active"></li>
								</c:if>
								<c:if test="${ind != 0}">
									<li data-target="#carousel" data-slide-to="${ind}"></li>
								</c:if>
							</c:forEach>
						</c:if>
					</ol>
					<div class="carousel-inner">
						<c:if test="${amountPhotos == 0}">
							<div class="item active">
								<img src="${contextPath}/resources/img/open_box-512.png"
									alt="Slide 1">
							</div>
						</c:if>
						<c:if test="${amountPhotos != 0}">
							<c:forEach items="${photos}" var="photo" varStatus="loop">
								<c:if test="${loop.index == 0}">
									<div class="item active">
										<img src="/webstore/admin/product/photo?id=${photo}"
											alt="Slide ${loop.index}">
									</div>
								</c:if>
								<c:if test="${loop.index != 0}">
									<div class="item">
										<img src="/webstore/admin/product/photo?id=${photo}"
											alt="Slide ${loop.index}">
									</div>
								</c:if>
							</c:forEach>
						</c:if>
					</div>
					<a href="#carousel" class="left carousel-control" data-slide="prev"></a> 
					<a href="#carousel" class="right carousel-control" data-slide="next"></a>
				</div>
			</div>
			<div class="main-info-side">
				<div style="height: 80%;">
					<div class="product-name">
							${product.name}
						</div>
						<div class="column">
						  Price: ${product.price}&#8372;
						</div>
						<div class="column">
							Exists ${product.amountOnWarehouse} pcs. on stack
						</div>
				</div>
				<div style="height: 20%; margin-left: 30px;">
					<button onclick="addProductToCart(${product.id});" type="button" class="btn btn-success"><span style="margin-right: 5px;"><i class="fa fa-plus" aria-hidden="true"></i></span>Add to cart</button>
				</div>
			</div>
		</div>
		<div class="bottom-block">
			<div class="tabs">
				<input id="tab1" type="radio" name="tabs" checked> <label
				
					for="tab1" title="Вкладка 1"><span style="margin-right: 10px;"><i class="fa fa-cog" aria-hidden="true"></i></span>All characteristic </label> <input id="tab2"
					type="radio" name="tabs"> <label for="tab2"
					title="Вкладка 2"> <span style="margin-right: 10px;"><i class="fa fa-comments" aria-hidden="true"></i></span>Comments</label>

				<section id="content-tab1">
					<table class="characteristic-table">
					<c:forEach items="${features}" var="feat">
						<tr>
							<td>${feat.name}</td>
							<td>${feat.value}</td>
						</tr>
					</c:forEach>
					</table>
				</section>
				<section id="content-tab2">
					<div class="comments">
						<div class="comment">
							<div class="comment-name-date">Ivan Vintonyak 27.01.2017 19:30:05</div>
							<div class="comment-text">Good thing!</div>
						</div>
					</div>	
					<div class="comments">
						<div class="comment">
							<div class="comment-name-date">Oleg Vintonyak 27.01.2017 19:30:05</div>
							<div class="comment-text">It is really good thing!</div>
						</div>
					</div>
					<div class="leave-comment-block">
						<div><textarea rows="4" cols="135"></textarea></div>
						<div class="button-container"><button type="button" class="btn btn-default button-bg-color"><span style="margin-right: 5px;"><i class="fa fa-plus" aria-hidden="true"></i></span>Add comment</button></div>
					</div>				
				</section>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<jsp:include page="staticHtml/footer.jsp"></jsp:include>
	</div>
</body>
</html>