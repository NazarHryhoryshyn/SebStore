<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Admin</title>

<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/admin.css" rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
</head>

<body>
	<div class="container">
		<div class="tabs">
			<input id="tab1" type="radio" name="tabs" checked>
			 <label
				for="tab1" title="Вкладка 1">Користувачі
			 </label> 
				<input id="tab2" type="radio" name="tabs"> 
				<label for="tab2" title="Вкладка 2">Категорії</label> 
				<input id="tab3" type="radio" name="tabs"> 
				<label for="tab3" title="Вкладка 3">Товари</label> 

			<section id="content-tab1">
				<p>Здесь размещаете любое содержание....</p>
			</section>
			<section id="content-tab2">
				<p>Здесь размещаете любое содержание....</p>
			</section>
			<section id="content-tab3">
				<p>Здесь размещаете любое содержание....</p>
			</section>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

</body>
</html>