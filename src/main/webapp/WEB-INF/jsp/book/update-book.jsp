<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>Book</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
<form method="post" action="/books/updateBook">
    <b>Form for update book with id=${book.id}:</b><br>
    <p><b>Enter update data:</b><br>
        <input type="hidden" name="id" value="${book.id}">
        <input type="text" name="isbn" value="${book.isbn}"> ISBN<br>
        <input type="text" name="title" value="${book.title}"> Title<br>
        <input type="text" name="author" value="${book.author}"> Author<br>
        <input type="text" name="cover" value="${book.typeCover.toString().toLowerCase()}"> Cover<br>
        <input type="number" step="0.01" name="price" value="${book.price}"> Price<br>
    </p>
    <p><input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </p>
</form>
<p><a href="/books">List books</a></p>
</body>

</html>
