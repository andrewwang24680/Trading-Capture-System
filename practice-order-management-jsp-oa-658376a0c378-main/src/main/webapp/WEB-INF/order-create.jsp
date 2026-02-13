<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>Create Order</title>
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
  <h2>Create Order</h2>

  <form action="" method="post">

    <div class="form-group">
      <label for="user">User:</label>
      <select id="user" name="userId" class="form-control">
        <c:forEach var="user" items="${requestScope.users}">
          <option value="${user.userId}">${user.firstName} ${user.lastName}</option>
        </c:forEach>
      </select>
    </div>

    <div class="form-group">
      <label for="orderId">Order ID:</label>
      <input type="text" id="orderId" name="orderId" class="form-control" required/>
    </div>

    <div class="form-group">
      <label for="clOrderId">Client Order ID:</label>
      <input type="text" id="clOrderId" name="clOrderId" class="form-control" required/>
    </div>

    <div class="form-group">
      <label for="orderStatus">Order Status:</label>
      <select id="orderStatus" name="orderStatus" class="form-control">
        <option value="New">New</option>
        <option value="Partially Filled">Partially Filled</option>
        <option value="Filled">Filled</option>
      </select>
    </div>

    <div class="form-group">
      <label for="quantity">Order Quantity:</label>
      <input type="number" id="quantity" name="quantity" class="form-control" step="0.01" required/>
    </div>

    <div class="form-group">
      <label for="side">Side:</label>
      <select id="side" name="side" class="form-control">
        <option value="Buy">Buy</option>
        <option value="Sell">Sell</option>
      </select>
    </div>

    <div class="form-group">
      <label for="orderType">Order Type:</label>
      <select id="orderType" name="orderType" class="form-control">
        <option value="Market">Market</option>
        <option value="Limit">Limit</option>
      </select>
    </div>

    <div class="form-group">
      <label for="price">Price:</label>
      <input type="number" id="price" name="price" class="form-control" step="0.01" required/>
    </div>

    <div class="form-group">
      <label for="priceType">Price Type:</label>
      <select id="priceType" name="priceType" class="form-control">
        <option value="Percentage">Percentage</option>
        <option value="Per Unit">Per Unit</option>
        <option value="Fixed Amount">Fixed Amount</option>
      </select>
    </div>

    <div class="form-group">
      <label for="currency">Currency:</label>
      <select id="currency" name="currency" class="form-control">
        <c:forEach var="currency" items="${requestScope.currencyOptions}">
          <option value="${currency}">${currency}</option>
        </c:forEach>
      </select>
    </div>

    <div class="form-group">
      <label for="instrument">Instrument:</label>
      <select id="instrument" name="instrument" class="form-control">
        <c:forEach var="instrument" items="${requestScope.instrumentOptions}">
          <option value="${instrument}">${instrument}</option>
        </c:forEach>
      </select>
    </div>

    <div class="form-group">
      <label for="settleType">Settle Type:</label>
      <select id="settleType" name="settleType" class="form-control">
        <option value="Regular">Regular</option>
        <option value="Cash">Cash</option>
        <option value="Next Day (T+)">Next Day (T+)</option>
        <option value="T+2">T+2</option>
      </select>
    </div>

    <div class="form-group">
      <label for="settleDate">Settle Date:</label>
      <input type="date" id="settleDate" name="settleDate" class="form-control" required/>
    </div>

    <!-- Interested Party (Email) -->
    <div class="form-group">
      <label for="interestedParty">Interested Party:</label>
      <input type="email" id="interestedParty" name="interestedParty" class="form-control" required/>
    </div>

    <!-- Submit Button -->
    <button type="submit" class="btn btn-primary">Create Order</button>
  </form>

</div>

</body>
</html>
