<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<form method="post" action="/orders/updateOrder">
    <b>Form for update order with id=${order.id}:</b><br>
    <p><b>Enter update data:</b><br>
    <div>
        <table border="1" cellpadding="2">
            <div>List of order items</div>
            <tr>
                <th>Book Title</th>
                <th>Book Author</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
            <c:forEach var="itemsForUpdate" items="${itemsForUpdate}">
                <tr>
                    <input type="hidden" name="id" value=${order.id}>
                    <input type="hidden" name="bookId" value=${itemsForUpdate.book.id}>
                    <td>${itemsForUpdate.book.title}</td>
                    <td>${itemsForUpdate.book.author}</td>
                    <td><input data-quantity type="number" step="1" min="0" name="quantity" value=${itemsForUpdate.quantity}></td>
                    <td data-price>${itemsForUpdate.price}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    </p>
    <p><input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </p>
</form>

<div data-total>Total Cost = ${order.totalCost}</div>
<div>Status = ${order.status.toString().toLowerCase()}</div>
<p><a href="/orders">List orders</a></p>
<script>
    const prices = [...document.querySelectorAll('td[data-price]')].map(item => item.innerHTML);
    const totalNode = document.querySelector('div[data-total]');
    document.querySelectorAll('input[data-quantity]').forEach(input => {
        input.addEventListener('input', event => {
            const quantities = [...document.querySelectorAll('input[data-quantity]')].map(item => item.value)
            let total = 0;
            quantities.forEach((item, index) => {
                total += item * +prices[index];
            })
            totalNode.innerHTML = "Total Cost = " + total.toFixed(2);
        })
    })
</script>
</body>
</html>