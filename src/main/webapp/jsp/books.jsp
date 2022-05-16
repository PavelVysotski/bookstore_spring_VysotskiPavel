<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>
        <head>
            <title>Book</title>
            <link rel="stylesheet" href="css/style.css">
        </head>
        <body>
            <h1>Books</h1>
            <table id="TableBook">
                <tr>
                    <th>N#</th>
                    <th>Id</th>
                    <th>ISBN</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Cover</th>
                    <th>Price</th>
                </tr>
                <c:forEach items="${books}" var="book" varStatus="counter">
                    <tr>
                        <td>${counter.count}</td>
                        <td>${book.id}</td>
                        <td>${book.isbn}</td>
                        <td>${book.title}</td>
                        <td>${book.author}</td>
                        <td>${book.typeCover.toString().toLowerCase()}</td>
                        <td>${book.price}</td>
                    </tr>
                </c:forEach>
            </table>
        </body>
        </html>