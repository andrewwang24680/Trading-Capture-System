<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Order</title>
    <style>
        body {
            background-color: #f4f4f4;
        }
        .container {
            margin-top: 30px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Update Order</h2>

    <!-- DONE: Create a form to allow user to update their order. Similar to order-create, except orderId cannot be updated and all fields value should be prefilled. -->
    <form action="${pageContext.request.contextPath}/order/update" method="post">

        <!-- Hidden Field: Order ID (for submission) -->
        <input type="hidden" name="orderId" value="${order.orderId}">

        <!-- User -->
        <div class="form-group">
            <label for="user">User:</label>
            <select id="user" name="userId" class="form-control">
                <c:forEach var="user" items="${requestScope.users}">
                    <option value="${user.userId}"
                        ${order.userId == user.userId ? 'selected' : ''}>
                            ${user.firstName} ${user.lastName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- Order ID (Read-only display) -->
        <div class="form-group">
            <label for="orderId">Order ID:</label>
            <input type="text" id="orderId" value="${order.orderId}"
                   class="form-control readonly-field" readonly/>
        </div>

        <!-- Client Order ID -->
        <div class="form-group">
            <label for="clOrderId">Client Order ID:</label>
            <input type="text" id="clOrderId" name="clOrderId"
                   value="${order.clOrderId}" class="form-control"/>
        </div>

        <!-- Order Status -->
        <div class="form-group">
            <label for="orderStatus">Order Status:</label>
            <select id="orderStatus" name="orderStatus" class="form-control">
                <option value="New" ${order.orderStatus == 'New' ? 'selected' : ''}>
                    New
                </option>
                <option value="Partially Filled" ${order.orderStatus == 'Partially Filled' ? 'selected' : ''}>
                    Partially Filled
                </option>
                <option value="Filled" ${order.orderStatus == 'Filled' ? 'selected' : ''}>
                    Filled
                </option>
            </select>
        </div>

        <!-- Order Quantity -->
        <div class="form-group">
            <label for="quantity">Order Quantity:</label>
            <input type="number" id="quantity" name="quantity"
                   value="${order.orderQuantity}"
                   class="form-control" step="0.01" required/>
        </div>

        <!-- Side -->
        <div class="form-group">
            <label for="side">Side:</label>
            <select id="side" name="side" class="form-control">
                <option value="Buy" ${order.side == 'Buy' ? 'selected' : ''}>
                    Buy
                </option>
                <option value="Sell" ${order.side == 'Sell' ? 'selected' : ''}>
                    Sell
                </option>
            </select>
        </div>

        <!-- Order Type -->
        <div class="form-group">
            <label for="orderType">Order Type:</label>
            <select id="orderType" name="orderType" class="form-control">
                <option value="Market" ${order.orderType == 'Market' ? 'selected' : ''}>
                    Market
                </option>
                <option value="Limit" ${order.orderType == 'Limit' ? 'selected' : ''}>
                    Limit
                </option>
            </select>
        </div>

        <!-- Price -->
        <div class="form-group">
            <label for="price">Price:</label>
            <input type="number" id="price" name="price"
                   value="${order.price}"
                   class="form-control" step="0.01" required/>
        </div>

        <!-- Price Type -->
        <div class="form-group">
            <label for="priceType">Price Type:</label>
            <select id="priceType" name="priceType" class="form-control">
                <option value="Percentage" ${order.priceType == 'Percentage' ? 'selected' : ''}>
                    Percentage
                </option>
                <option value="Per Unit" ${order.priceType == 'Per Unit' ? 'selected' : ''}>
                    Per Unit
                </option>
                <option value="Fixed Amount" ${order.priceType == 'Fixed Amount' ? 'selected' : ''}>
                    Fixed Amount
                </option>
            </select>
        </div>

        <!-- Currency -->
        <div class="form-group">
            <label for="currency">Currency:</label>
            <select id="currency" name="currency" class="form-control">
                <c:forEach var="currency" items="${requestScope.currencyOptions}">
                    <option value="${currency}"
                        ${order.currency == currency ? 'selected' : ''}>
                            ${currency}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- Instrument -->
        <div class="form-group">
            <label for="instrument">Instrument:</label>
            <select id="instrument" name="instrument" class="form-control">
                <c:forEach var="instrument" items="${requestScope.instrumentOptions}">
                    <option value="${instrument}"
                        ${order.instrumentName == instrument ? 'selected' : ''}>
                            ${instrument}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- Settle Type -->
        <div class="form-group">
            <label for="settleType">Settle Type:</label>
            <select id="settleType" name="settleType" class="form-control">
                <option value="Regular" ${order.settleType == 'Regular' ? 'selected' : ''}>
                    Regular
                </option>
                <option value="Cash" ${order.settleType == 'Cash' ? 'selected' : ''}>
                    Cash
                </option>
                <option value="Next Day (T+)" ${order.settleType == 'Next Day (T+)' ? 'selected' : ''}>
                    Next Day (T+)
                </option>
                <option value="T+2" ${order.settleType == 'T+2' ? 'selected' : ''}>
                    T+2
                </option>
            </select>
        </div>

        <!-- Settle Date -->
        <div class="form-group">
            <label for="settleDate">Settle Date:</label>
            <input type="date" id="settleDate" name="settleDate"
                   value="${order.settleDate}"
                   class="form-control" required/>
        </div>

        <!-- Interested Party (Email) -->
        <div class="form-group">
            <label for="interestedParty">Interested Party:</label>
            <input type="email" id="interestedParty" name="interestedParty"
                   value="${order.interestedParty}"
                   class="form-control" required/>
        </div>

        <!-- Submit Buttons -->
        <button type="submit" class="btn btn-primary">Update Order</button>
        <button type="button" class="btn btn-secondary" onclick="window.history.back()">Cancel</button>
    </form>

</div>
</body>
</html>
