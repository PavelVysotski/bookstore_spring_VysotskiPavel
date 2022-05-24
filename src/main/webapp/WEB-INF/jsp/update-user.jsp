<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>User</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
<form method="post" action="/users/updateUser">
    <b>Form for update user with id=${user.id}:</b><br>
    <p><b>Enter update data:</b><br>
        <input type="hidden" name="id" value="<c:out value='${user.id}' />">
        <input type="text" name="name" value="<c:out value='${user.name}' />"> Name<br>
        <input type="text" name="secondName" value="<c:out value='${user.secondName}' />"> Second Name<br>
        <input type="text" name="email" value="<c:out value='${user.email}' />"> Email<br>
        <input type="password" name="password" value="<c:out value='${user.password}' />"> Password<br>
        <input type="text" name="role" value="<c:out value='${user.role.toString().toLowerCase()}' />"> Role<br>
    </p>
    <p><input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </p>
</form>
</body>

</html>
