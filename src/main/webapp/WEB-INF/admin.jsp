<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="cmnUtilFunc" class="cl.huerta.codingdojo.subscriptions.utils.DateUtils"/>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Subscriptions</title>
    <!-- Bootstrap -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    
</head>
<body>
	<nav class="navbar navbar-light bg-light justify-content-between">
		<h1>Admin Dashboard</h1>
		<form class="form-inline" action="/logout" method="POST">
			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Logout!</button>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
	</nav>
	<div class="container">
		<c:if test="${not empty customers}">
		<h1>Customers</h1>
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">#</th>
					<th scope="col">Name</th>
					<th scope="col">Next Due Date</th>
					<th scope="col">Amount Due</th>
					<th scope="col">Package Type</th>
				</tr>
		  	</thead>
		  	<tbody>
			    <c:forEach var="customer" items="${customers}">
			    <tr>
					<c:set var="packageCost">
						<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${customer.getPackage().cost}" />
					</c:set>
			    	<th scope="row"><c:out value="${customer.id}"/></th>
			    	<td><c:out value="${customer.name}"/></td>
			    	<td><fmt:formatDate type = "date" value = "${cmnUtilFunc.nextDueDate(customer.hiredAt)}" /></td>
			    	<td>$<c:out value="${packageCost}"/></td>
			    	<td><c:out value="${customer.getPackage().name}"/></td>
			    </tr>	
			    </c:forEach>
		  	</tbody>
		</table>
		</c:if>
		
		<c:if test="${not empty packages}">
		<h1>Packages</h1>
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">#</th>
					<th scope="col">Package Name</th>
					<th scope="col">Package Cost</th>
					<th scope="col">Available</th>
					<th scope="col">Users</th>
					<th scope="col">Actions</th>
				</tr>
		  	</thead>
		  	<tbody>
			    <c:forEach var="pkg" items="${packages}">
			    <tr>
					<c:set var="packageCost">
						<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${pkg.cost}" />
					</c:set>
					<c:set var="packageUsers">${pkg.users.size()}</c:set>
			    	<th scope="row"><c:out value="${pkg.id}"/></th>
			    	<td><c:out value="${pkg.name}"/></td>
			    	<td>$<c:out value="${packageCost}"/></td>
			    	<td><c:choose>
			    		<c:when test="${pkg.isAvailable}">available</c:when>
			    		<c:otherwise>unavailable</c:otherwise>
			    	</c:choose></td>
			    	<td><c:out value="${packageUsers}"/></td>
			    	<td>
						<form id="activate${pkg.id}" class="form-inline" action="/packages/${pkg.id}/edit" method="POST">
							<input type="hidden" id="isActive" name="isActive" value="true">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
						<form id="deactivate${pkg.id}" class="form-inline" action="/packages/${pkg.id}/edit" method="POST">
							<input type="hidden" id="isActive" name="isActive" value="false">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
			    		<c:if test="${pkg.isDefault}">default | </c:if>
			    		<c:if test="${!pkg.isDefault && pkg.isAvailable && packageUsers == 0}"><a href="#" onClick="document.getElementById('deactivate${pkg.id}').submit()">deactivate</a> | </c:if>
			    		<c:if test="${!pkg.isDefault && !pkg.isAvailable}"><a href="#" onClick="document.getElementById('activate${pkg.id}').submit()">activate</a> | </c:if>
			    	 	<a href="/packages/${pkg.id}">edit</a>
			    	 </td>
			    </tr>	
			    </c:forEach>
		  	</tbody>
		</table>
		</c:if>
		
		<h1>Create a Package</h1>
		<form:form class="form" method="POST" modelAttribute="pkg">
			<c:set var="formHasBindError">
				<form:errors path="*" cssClass="alert alert-danger" element="div" />
			</c:set>
			<c:if test="${not empty formHasBindError}">
				${formHasBindError}
			</c:if>
			<div class="form-group">
				<form:label class="my-1 mr-2" path="name">Package Name: </form:label>
				<form:input type="text" path="name"/>
			</div>
			<div class="form-group">
				<form:label class="my-1 mr-2" path="cost">Package Cost: </form:label>
				<form:input type="number" path="cost"/>
			</div>
			<button type="submit" class="btn btn-primary my-1">Add</button>
		</form:form>
	</div>
</body>
</html>