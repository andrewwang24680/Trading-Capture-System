<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<div class="container mt-5" style="max-width: 400px;">
    <h2 class="text-center mb-4">Login</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <c:if test="${not empty requestScope.errorMessage}">
            <div class="alert alert-danger" role="alert">
                    ${requestScope.errorMessage}
            </div>
        </c:if>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="text" id="email" name="email" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-success btn-block">Login</button>
    </form>
</div>
</body>
</html>
