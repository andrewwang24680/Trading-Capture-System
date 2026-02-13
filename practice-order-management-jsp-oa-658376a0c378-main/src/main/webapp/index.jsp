<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="./WEB-INF/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <style>
        html, body {
            max-width: 100%;
            /*overflow-x: hidden;*/
        }
        .dashboard-header {
            padding: 20px 0;
            background-color: #f8f9fa;
            text-align: center;
            border-bottom: 1px solid #ddd;
            margin-bottom: 20px;
        }
        .dashboard-header h1 {
            font-size: 2.5rem;
        }
        .dashboard-content {
            margin: 20px;
        }
        .welcome-card {
            background-color: #007bff;
            color: white;
            padding: 20px;
            border-radius: 10px;
        }
        .dashboard-widgets .widget {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 20px;
            text-align: center;
            margin-bottom: 20px;
        }
        .widget h3 {
            font-size: 1.5rem;
            margin-bottom: 15px;
        }
        .widget a {
            text-decoration: none;
        }
    </style>
</head>
<body>

<!-- Dashboard Header -->
<div class="dashboard-header">
    <c:if test="${not empty sessionScope.user}">
        <h1>Welcome to Your Dashboard,
                ${sessionScope.user.firstName} ${sessionScope.user.lastName}!</h1>
    </c:if>
</div>

<div class="container-fluid dashboard-content">
    <div class="row justify-content-center">
        <div class="col-lg-12 mb-4">
            <div class="welcome-card">
                <h2>Hello, ${sessionScope.user.firstName}!</h2>
                <p>Here’s a quick overview of your recent activities and available actions.</p>
            </div>
        </div>
    </div>

    <!-- First Row of Widgets -->
    <div class="row justify-content-center">
        <div class="col-md-4 dashboard-widgets">
            <div class="widget">
                <h3>Manage Orders</h3>
                <p>View and manage all your orders in one place.</p>
                <a href="order/list" class="btn btn-primary">Go to Orders</a>
            </div>
        </div>
        <div class="col-md-4 dashboard-widgets">
            <div class="widget">
                <h3>Execution Management</h3>
                <p>Oversee the execution status of your operations.</p>
                <a href="#" class="btn btn-primary">Go to Execution</a>
            </div>
        </div>
        <div class="col-md-4 dashboard-widgets">
            <div class="widget">
                <h3>Allocation Management</h3>
                <p>Oversee the allocation status of your operations.</p>
                <a href="#" class="btn btn-primary">Go to Allocation</a>
            </div>
        </div>
    </div>

    <!-- Second Row of Widgets -->
    <div class="row justify-content-center">
        <div class="col-md-4 dashboard-widgets">
            <div class="widget">
                <h3>Financial Management</h3>
                <p>Review your financial accounts and transactions.</p>
                <a href="#" class="btn btn-primary">Go to Financials</a>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<div class="text-center mt-5">
    <p>&copy; 2024 TCS Simple App. All rights reserved.</p>
</div>

</body>
</html>