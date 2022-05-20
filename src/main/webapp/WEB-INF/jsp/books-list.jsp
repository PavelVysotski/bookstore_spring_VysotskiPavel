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
        	<a href="/books/addBook">Add New Book</a>
        	&nbsp;&nbsp;&nbsp;
        	<a href="/books/count">Count of all books</a>
        	
        </h2>
	</center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Books</h2></caption>
            <tr>
                <th>ID</th>
                <th>ISBN</th>
                <th>Title</th>
                <th>Author</th>
                <th>Cover</th>
                <th>Price</th>
            </tr>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td><c:out value="${book.id}" /></td>
                    <td><c:out value="${book.isbn}" /></td>
                    <td><c:out value="${book.title}" /></td>
                    <td><c:out value="${book.author}" /></td>
                    <td><c:out value="${book.typeCover.toString().toLowerCase()}" /></td>
                    <td><c:out value="${book.price}" /></td>
                    <td>
                    	<a href="/books/updateBook/<c:out value='${book.id}' />">Update</a>
                    	&nbsp;&nbsp;&nbsp;&nbsp;
                    	<a href="/books/delete/<c:out value='${book.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>	
</body>
</html>
