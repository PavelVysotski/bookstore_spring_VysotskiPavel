<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>User</title>
    <link rel="stylesheet" href="../css/style.css">
</head>

<body>
<form method="post" action="/api/users/updateUser">
    <b>Form for update user with id=${user.id}:</b><br>
    <p><b>Enter update data:</b><br>
        <input type="hidden" name="id" value="${user.id}">
        <input type="text" name="name" value="${user.name}"> Name<br>
        <input type="text" name="secondName" value="${user.secondName}"> Second Name<br>
        <input type="text" name="email" value="${user.email}"> Email<br>
        <input type="password" name="password" value="${user.password}"> Password<br>
        <div>User role - ${user.role.toString().toUpperCase()}</div>
        <b>Choose role</b><br>
        <input type="radio" value="ADMIN" name="role"> ADMIN<br>
        <input type="radio" value="MANAGER" name="role"> MANAGER<br>
        <input type="radio" value="CUSTOMER" name="role" checked> CUSTOMER<br>
    </p>
    <p><input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </p>
</form>
<p><a href="/api/users">List users</a></p>
</body>

</html>
