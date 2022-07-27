<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<form method="post" action="/api/orders/createOrder">
    <b>Form for create order:</b><br>
    <p><b>Enter data:</b><br>
    <div>
        <table border="1" cellpadding="2">
            <div>List of order items</div>
            <tr>
                <th>Book Title</th>
                <th>Book Author</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
            <c:forEach var="book" items="${books}">
                <tr>
                    <input type="hidden" name="bookId" value="${book.id}">
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td><input data-quantity type="number" step="1" min="0" name="quantity" value="0"></td>
                    <td data-price>${book.price}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    </p>
    <p><input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </p>
</form>

<div data-total>Total Cost = 0</div>
<p><a href="/api/orders">List orders</a></p>
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
