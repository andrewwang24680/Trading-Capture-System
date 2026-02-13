<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}">
        <img src="${pageContext.request.contextPath}/static/images/tcs-logo.png" alt="TCS" class="d-inline-block align-text-top" style="height: 20px">
        TCS Simple App
    </a>
    <div class="navbar-nav">
        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/order/list">Order Management</a>
        <a class="nav-item nav-link" href="#">Execution Management</a>
        <a class="nav-item nav-link" href="#">Allocation Management</a>
        <a class="nav-item nav-link" href="#">Financial Account Management</a>
        <c:if test="${sessionScope.user != null}">
            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
        </c:if>
    </div>
</div>
</body>
</html>

