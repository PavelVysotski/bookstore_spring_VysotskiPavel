<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h1>Order, id=${order.id}</h1>
<div>User = ${user.name} ${user.secondName} </div>
<div>
    <table border="1" cellpadding="2">
        <div>List of order items</div>
        <tr>
            <th>Book Title</th>
            <th>Book Author</th>
            <th>Quantity</th>
            <th>Price</th>
        </tr>
        <c:forEach var="orderItem" items="${orderItems}">
            <tr>
                <td>${orderItem.book.title}</td>
                <td>${orderItem.book.author}</td>
                <td>${orderItem.quantity}</td>
                <td>${orderItem.price}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<div>Total Cost = ${order.totalCost}</div>
<div>Status = ${order.status.toString().toLowerCase()}</div>
<p><a href="/orders">List orders</a></p>
</body>
</html>