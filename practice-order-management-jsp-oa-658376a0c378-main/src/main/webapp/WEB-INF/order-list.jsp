<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order Listing</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        h2 {
            color: #333;
            text-align: center;
            padding: 20px 0;
        }

        form label {
            margin-right: 10px;
        }

        form input[type="text"], form input[type="date"], form select {
            margin-bottom: 10px;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }

        form {
            margin-bottom: 20px;
        }

        .sortable {
            cursor: pointer;
            text-decoration: underline;
            color: #007bff;
        }

        .sortable:hover {
            color: #0056b3;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Order Listing</h2>
        <a href="create" class="btn btn-success">Create New Order</a>
    </div>

    <form action="" method="get">
        <div class="row">
            <!-- Order ID 搜索 -->
            <div class="col-md-3">
                <label for="search">Order ID:</label>
                <input type="text" id="search" name="search" placeholder="Search..."
                       value="${param.search}" class="form-control"/>
            </div>

            <div class="col-md-3">
                <label for="status">Order Status:</label>
                <select id="status" name="status" class="form-control">
                    <option value="">All Statuses</option>
                    <c:forEach var="status" items="${requestScope.statusOptions}">
                        <option value="${status}" <c:if test="${status == param.status}">selected</c:if>>
                                ${status}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-3">
                <label for="side">Side:</label>
                <select id="side" name="side" class="form-control">
                    <option value="">All Sides</option>
                    <c:forEach var="sideOption" items="${requestScope.sideOptions}">
                        <option value="${sideOption}" <c:if test="${sideOption == param.side}">selected</c:if>>
                                ${sideOption}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-3">
                <label for="instrument">Instrument:</label>
                <select id="instrument" name="instrument" class="form-control">
                    <option value="">All Instruments</option>
                    <c:forEach var="instrumentOption" items="${requestScope.instrumentOptions}">
                        <option value="${instrumentOption}"
                                <c:if test="${instrumentOption == param.instrument}">selected</c:if>>
                                ${instrumentOption}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="row mt-2">

            <div class="col-md-3">
                <label for="settleDateFrom">Settle Date From:</label>
                <input type="date" id="settleDateFrom" name="settleDateFrom"
                       value="${param.settleDateFrom}" class="form-control"/>
            </div>

            <div class="col-md-3">
                <label for="settleDateTo">Settle Date To:</label>
                <input type="date" id="settleDateTo" name="settleDateTo"
                       value="${param.settleDateTo}" class="form-control"/>
            </div>

            <div class="col-md-3">
                <label for="pageSize">Page Size:</label>
                <select id="pageSize" name="pageSize" class="form-control">
                    <c:forEach var="size" items="${requestScope.pageSizes}">
                        <option value="${size}" <c:if test="${size == param.pageSize}">selected</c:if>>
                                ${size}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-3">
                <label>&nbsp;</label> <!-- 占位，对齐按钮 -->
                <button type="submit" class="btn btn-primary btn-block">Filter</button>
            </div>
        </div>

        <input type="hidden" name="sortBy" value="${param.sortBy}"/>
        <input type="hidden" name="sortOrder" value="${param.sortOrder}"/>
    </form>

    <table class="table table-striped table-bordered">
        <thead class="thead-light">
        <tr>
            <th>
                <a href="?search=${param.search}&status=${param.status}&side=${param.side}&instrument=${param.instrument}&settleDateFrom=${param.settleDateFrom}&settleDateTo=${param.settleDateTo}&pageSize=${param.pageSize}&pageNum=${param.pageNum}&sortBy=orderId&sortOrder=${param.sortBy == 'orderId' && param.sortOrder == 'asc' ? 'desc' : 'asc'}"
                   class="sortable">
                    Order ID
                    <c:if test="${param.sortBy == 'orderId'}">
                        ${param.sortOrder == 'asc' ? '▲' : '▼'}
                    </c:if>
                </a>
            </th>

            <th>
                <a href="?search=${param.search}&status=${param.status}&side=${param.side}&instrument=${param.instrument}&settleDateFrom=${param.settleDateFrom}&settleDateTo=${param.settleDateTo}&pageSize=${param.pageSize}&pageNum=${param.pageNum}&sortBy=status&sortOrder=${param.sortBy == 'status' && param.sortOrder == 'asc' ? 'desc' : 'asc'}"
                   class="sortable">
                    Status
                    <c:if test="${param.sortBy == 'status'}">
                        ${param.sortOrder == 'asc' ? '▲' : '▼'}
                    </c:if>
                </a>
            </th>

            <th>Quantity</th>
            <th>Side</th>

            <th>
                <a href="?search=${param.search}&status=${param.status}&side=${param.side}&instrument=${param.instrument}&settleDateFrom=${param.settleDateFrom}&settleDateTo=${param.settleDateTo}&pageSize=${param.pageSize}&pageNum=${param.pageNum}&sortBy=price&sortOrder=${param.sortBy == 'price' && param.sortOrder == 'asc' ? 'desc' : 'asc'}"
                   class="sortable">
                    Price
                    <c:if test="${param.sortBy == 'price'}">
                        ${param.sortOrder == 'asc' ? '▲' : '▼'}
                    </c:if>
                </a>
            </th>

            <th>Instrument Name</th>

            <th>
                <a href="?search=${param.search}&status=${param.status}&side=${param.side}&instrument=${param.instrument}&settleDateFrom=${param.settleDateFrom}&settleDateTo=${param.settleDateTo}&pageSize=${param.pageSize}&pageNum=${param.pageNum}&sortBy=settleDate&sortOrder=${param.sortBy == 'settleDate' && param.sortOrder == 'asc' ? 'desc' : 'asc'}"
                   class="sortable">
                    Settle Date
                    <c:if test="${param.sortBy == 'settleDate'}">
                        ${param.sortOrder == 'asc' ? '▲' : '▼'}
                    </c:if>
                </a>
            </th>

            <th>
                <a href="?search=${param.search}&status=${param.status}&side=${param.side}&instrument=${param.instrument}&settleDateFrom=${param.settleDateFrom}&settleDateTo=${param.settleDateTo}&pageSize=${param.pageSize}&pageNum=${param.pageNum}&sortBy=creationTime&sortOrder=${param.sortBy == 'creationTime' && param.sortOrder == 'asc' ? 'desc' : 'asc'}"
                   class="sortable">
                    System Creation Time
                    <c:if test="${param.sortBy == 'creationTime'}">
                        ${param.sortOrder == 'asc' ? '▲' : '▼'}
                    </c:if>
                </a>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${requestScope.orders}">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/order/view?orderId=${order.orderId}"
                       style="color: #007bff; text-decoration: underline;">
                            ${order.orderId}
                    </a>
                </td>
                <td>${order.status}</td>
                <td>${order.quantity}</td>
                <td>${order.side}</td>
                <td>${order.price}</td>
                <td>${order.instrumentName}</td>
                <td>${order.settleDate}</td>
                <td>${order.systemCreationTime}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="pagination">
        <c:forEach var="i" begin="1" end="${requestScope.totalPages}">
            <c:url var="pageUrl" value="">
                <c:param name="pageNum" value="${i}"/>
                <c:param name="pageSize" value="${param.pageSize}"/>
                <c:param name="search" value="${param.search}"/>
                <c:param name="status" value="${param.status}"/>
                <c:param name="side" value="${param.side}"/>
                <c:param name="instrument" value="${param.instrument}"/>
                <c:param name="settleDateFrom" value="${param.settleDateFrom}"/>
                <c:param name="settleDateTo" value="${param.settleDateTo}"/>
                <c:param name="sortBy" value="${param.sortBy}"/>
                <c:param name="sortOrder" value="${param.sortOrder}"/>
            </c:url>
            <a href="${pageUrl}" class="btn ${i == param.pageNum ? 'btn-primary' : 'btn-secondary'}">${i}</a>
        </c:forEach>
    </div>
</div>

</body>
</html>
