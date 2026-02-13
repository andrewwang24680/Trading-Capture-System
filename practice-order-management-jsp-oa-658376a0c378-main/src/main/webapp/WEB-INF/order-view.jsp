<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order Details</title>
    <style>
        body {
            background-color: #f4f4f4;
        }

        .container {
            margin-top: 30px;
        }

        .detail-label {
            font-weight: bold;
        }

        .detail-value {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Order Details</h2>

    <div class="form-group">
        <label class="detail-label">User:</label>
        <span class="detail-value">${requestScope.orderOwner.firstName} ${requestScope.orderOwner.lastName}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Order ID:</label>
        <span class="detail-value">${requestScope.order.orderId}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Client Order ID:</label>
        <span class="detail-value">${requestScope.order.clOrderId}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Order Status:</label>
        <span class="detail-value">${requestScope.order.orderStatus}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Order Quantity:</label>
        <span class="detail-value">${requestScope.order.orderQuantity}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Side:</label>
        <span class="detail-value">${requestScope.order.side}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Order Type:</label>
        <span class="detail-value">${requestScope.order.orderType}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Price:</label>
        <span class="detail-value">${requestScope.order.price}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Price Type:</label>
        <span class="detail-value">${requestScope.order.priceType}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Currency:</label>
        <span class="detail-value">${requestScope.order.currency}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Instrument:</label>
        <span class="detail-value">${requestScope.order.instrumentName}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Settle Type:</label>
        <span class="detail-value">${requestScope.order.settleType}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Settle Date:</label>
        <span class="detail-value">${requestScope.order.settleDate}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Interested Party:</label>
        <span class="detail-value">${requestScope.order.interestedParty}</span>
    </div>

    <div class="form-group">
        <label class="detail-label">Creation Time:</label>
        <span class="detail-value">${requestScope.order.creationTime}</span>
    </div>

    <a href="update?orderId=${requestScope.order.orderId}" class="btn btn-info">Update Order</a>
    <a href="delete?orderId=${requestScope.order.orderId}" class="btn btn-danger">Delete Order</a>
</div>
</body>
</html>
