<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spring Boot Application</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h1>Spring Boot Application</h1>
<div>
    <form action="/api/books" method="get">
        <input type="submit" value="Go to Books management">
    </form>
</div>
<div>
    <form action="/api/users" method="get">
        <input type="submit" value="Go to Users management">
    </form>
</div>
<div>
    <form action="/api/orders" method="get">
        <input type="submit" value="Go to Orders management">
    </form>
</div>
<div>
    <form>
        <input type="button" value="Sign In" onclick="window.open('http://localhost:8081/login')">
    </form>
</div>
<div>
    <form action="/logout" method="post">
        <input type="submit" value="Sign Out">
    </form>
</div>
</body>
</html>
