<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>Book</title>
    <link rel="stylesheet" href="../css/style.css">
</head>

<body>
<form method="post" action="/api/books/updateBook">
    <b>Form for update book with id=${book.id}:</b><br>
    <p><b>Enter update data:</b><br>
        <input type="hidden" name="id" value="${book.id}">
        <input type="text" name="isbn" value="${book.isbn}"> ISBN<br>
        <input type="text" name="title" value="${book.title}"> Title<br>
        <input type="text" name="author" value="${book.author}"> Author<br>
    <div>Type of cover - ${book.typeCover.toString().toUpperCase()}</div>
    <b>Choose type of cover</b><br>
    <input type="radio" value="HARD" name="cover" checked> HARD<br>
    <input type="radio" value="SOFT" name="cover"> SOFT<br>
    <input type="number" step="0.01" name="price" value="${book.price}"> Price<br>
    </p>
    <p><input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </p>
</form>
<p><a href="/api/books">List books</a></p>
</body>

</html>
