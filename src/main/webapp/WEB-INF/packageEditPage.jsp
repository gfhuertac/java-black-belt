<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Subscriptions</title>
    <!-- Bootstrap -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
</head>
<body>
	<c:set var="packageUsers">${pkg.users.size()}</c:set>
	<nav class="navbar navbar-light bg-light justify-content-between">
		<h1>Admin Dashboard</h1>
		<form class="form-inline" action="/logout" method="POST">
			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Logout!</button>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
	</nav>
	<div class="container">
	<h1>Package: <c:out value="${pkg.name}" /></h1>
	<form id="edit" class="form" action="/packages/${pkg.id}/edit" method="POST">
		<div class="form-group">
			<label class="my-1 mr-2" for="cost">Cost: </label>
			$ <input type="number" id="cost" name="cost" value="${pkg.cost}">
		</div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
	<div class="row">
		<div class="col-2">
			<button onClick="document.getElementById('edit').submit()" type="button" class="btn btn-primary my-1">Edit</button>
		</div>
		<div class="col-2">
			<c:if test="${!pkg.isDefault && packageUsers == 0}">
			<form id="edit" class="form-inline" action="/packages/${pkg.id}/delete" method="POST">
			<button type="submit" class="btn btn-danger my-1">Delete</button>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</form>
			</c:if>
		</div>
	</div>
	</div>
</body>
</html>