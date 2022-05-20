<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
</head>
<body>
<center>
    <h1>Book Management</h1>
    <h2>
        <a href="/users/addUser">Add New User</a>
    </h2>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Users</h2></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Second Name</th>
            <th>Email</th>
            <th>Role</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.id}" /></td>
                <td><c:out value="${user.name}" /></td>
                <td><c:out value="${user.secondName}" /></td>
                <td><c:out value="${user.email}" /></td>
                <td><c:out value="${user.role.toString().toLowerCase()}" /></td>
                <td>
                    <a href="/users/updateUser/<c:out value='${user.id}' />">Update</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="/users/delete/<c:out value='${user.id}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
