<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
</head>
<body>
<center>
    <h1>Users Management</h1>
    <h2>
        <a href="/users/addUser">Add New User</a>
        &nbsp;
        <a href="http://localhost:8081/">Main menu</a>
    </h2>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Users</h2></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Second Name</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.secondName}</td>
                <td>
                    <form action="/users/byId/${user.id}" method="get"><input type="submit" value="View"></form>
                </td>
                <td>
                    <form action="/users/updateUser/${user.id}" method="get"><input type="submit" value="Update"></form>
                </td>
                <td>
                    <form action="/users/delete/${user.id}" method="post"><input type="submit" value="Delete"></form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>