<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Subscriptions</title>
    <!-- Bootstrap -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
			    <h1>Register</h1>
				<c:if test="${not empty success}">
					<div class="alert alert-success" role="alert">
						<c:out value="${success}" />
					</div>		
				</c:if>
				<form:form method="POST" modelAttribute="user">
					<c:set var="formHasBindError">
						<form:errors path="*" cssClass="alert alert-danger" element="div" />
					</c:set>
					<c:if test="${not empty formHasBindError}">
						${formHasBindError}
					</c:if>
			        <div class="form-group row">
			            <form:label path="name" class="col-sm-2 col-form-label">Name:</form:label>
			            <div class="col-sm-4">
				            <form:input type="text" path="name"/>
				        </div>
			        </div>
			        <div class="form-group row">
			            <form:label path="email" class="col-sm-2 col-form-label">Email:</form:label>
			            <div class="col-sm-4">
				            <form:input type="email" path="email"/>
				        </div>
			        </div>
			        <div class="form-group row">
			            <form:label path="password" class="col-sm-2 col-form-label">Password:</form:label>
			            <div class="col-sm-4">
				            <form:password path="password"/>
			            </div>
			        </div>
			        <div class="form-group row">
			            <form:label path="passwordConfirmation" class="col-sm-2 col-form-label">Password Confirmation:</form:label>
			            <div class="col-sm-4">
				            <form:password path="passwordConfirmation"/>
			            </div>
			        </div>
			        <div class="form-group row">
			            <div class="col-sm-4">&nbsp;</div>
			            <div class="col-sm-2">
					        <button type="submit" class="btn btn-primary">Register!</button>
					    </div>
				    </div>
			    </form:form>
			</div>
			<div class="col-6">
			    <h1>Login</h1>
			    <form method="post" action="/login">
					<c:if test="${not empty error}">
						<div class="alert alert-danger" role="alert">
							<c:out value="${error}" />
						</div>		
					</c:if>
					<c:if test="${not empty logout}">
						<div class="alert alert-success" role="alert">
							<c:out value="${logout}" />
						</div>		
					</c:if>
			        <div class="form-group row">
			            <label for="email" class="col-sm-2 col-form-label">Email:</label>
			            <div class="col-sm-4">
				            <input type="email" name="username" id="username"/>
				        </div>
			        </div>
			        <div class="form-group row">
			            <label for="password" class="col-sm-2 col-form-label">Password:</label>
			            <div class="col-sm-4">
				            <input type="password" name="password" id="login-password" />
			            </div> 
			        </div>
			        <div class="form-group row">
			            <div class="col-sm-4">&nbsp;</div>
			            <div class="col-sm-2">
					        <button type="submit" class="btn btn-primary">Login!</button>
					    </div>
				    </div>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			    </form>    
			</div>
		</div>
	</div>
</body>
</html>