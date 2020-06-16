<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Subscriptions</title>
    <!-- Bootstrap -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    
</head>
<body>
	<c:set var="packageCost">
		<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${user.package.cost}" />
	</c:set>
	<nav class="navbar navbar-light bg-light justify-content-between">
		<h1>Welcome, <c:out value="${user.name}" />!</h1>
		<form class="form-inline" action="/logout" method="POST">
			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Logout!</button>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
	</nav>
	<div class="container">
		<div class="row">
			<div class="col-4"><h2>Your current package: </h2></div>
			<div class="col-4"><h2><c:out value="${user.package.name}" /></h2></div>
		</div>
		<div class="row">
			<div class="col-4"><h2>Package cost: </h2></div>
			<div class="col-4"><h2>$<c:out value="${packageCost}" /></h2></div>
		</div>
		<c:if test="${not empty availablePackages}">
		<form class="form" action="/users/${user.id}/edit" method="POST">
			<select name="package" id="package">
			    <c:forEach var="pkg" items="${availablePackages}">
			        <option value="${pkg.id}"><c:out value="${pkg.name}"/></option>
			    </c:forEach>
   			</select>
			<button class="btn btn-primary" type="submit">Switch!</button>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
		</c:if>
	</div>
</body>
</html>