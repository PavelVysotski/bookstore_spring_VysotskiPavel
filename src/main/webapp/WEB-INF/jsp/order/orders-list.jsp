<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
  <title>Order Management Application</title>
  <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<center>
  <h1>Orders Management</h1>
  <h2>
    <a href="/orders/addOrder">Add New Order</a>
    &nbsp;
    <a href="http://localhost:8081/">Main menu</a>
  </h2>
</center>
<div align="center">
  <table border="1" cellpadding="5">
    <caption><h2>List of Orders</h2></caption>
    <tr>
      <th>ID</th>
      <th>User</th>
      <th>Total Cost</th>
      <th>Status</th>
    </tr>
    <c:forEach var="order" items="${orders}">
      <tr>
        <td>${order.id}</td>
        <td>${order.user.name} ${order.user.secondName}</td>
        <td>${order.totalCost}</td>
        <td>${order.status}</td>
        <td>
          <form action="/orders/byId/${order.id}" method="get"><input type="submit" value="View"></form>
        </td>
        <td>
          <form action="/orders/updateOrder/${order.id}" method="get"><input type="submit" value="Update"></form>
        </td>
        <security:authorize access="hasAnyRole('ADMIN', 'MANAGER')">
          <td>
            <form action="/orders/delete/${order.id}" method="post"><input type="submit" value="Delete"></form>
          </td>
        </security:authorize>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
</html>
