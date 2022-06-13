<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>User Management Application</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<center>
    <h1>Books Management</h1>
    <h2>
        <a href="/api/books/create">Add New Book</a>
        &nbsp;
        <a href="/api/books/count">Count of all books</a>
        &nbsp;
        <a href="http://localhost:8081/">Main menu</a>
    </h2>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Books</h2></caption>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
        </tr>
        <c:forEach var="book" items="${books}">
            <tr>
                <td>${book.id}</td>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td>
                    <form action="/api/books/${book.id}" method="get"><input type="submit" value="View"></form>
                </td>
                <security:authorize access="hasAnyRole('ADMIN', 'MANAGER')">
                    <td>
                        <form action="/api/books/update/${book.id}" method="get"><input type="submit" value="Update"></form>
                    </td>
                </security:authorize>
                <security:authorize access="hasRole('ADMIN')">
                    <td>
                        <form action="/api/books/delete/${book.id}" method="post"><input type="submit" value="Delete">
                        </form>
                    </td>
                </security:authorize>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
