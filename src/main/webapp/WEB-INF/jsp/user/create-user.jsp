<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>User</title>
    <link rel="stylesheet" href="../css/style.css">
</head>

<body>
<form method="post" action="/users/createUser">
    <b>Form for create new user:</b><br>
    <p><b>Enter new data</b><br>
        <input type="text" name="name"> Name<br>
        <input type="text" name="secondName"> Second Name<br>
        <input type="text" name="email"> Email<br>
        <input type="password" name="password"> Password<br>
    <p><b>Choose role</b><br>
        <input type="radio" value="ADMIN" name="role"> ADMIN<br>
        <input type="radio" value="MANAGER" name="role"> MANAGER<br>
        <input type="radio" value="CUSTOMER" name="role" checked> CUSTOMER<br>
    </p>
    <p><input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </p>
</form>
<p><a href="/users">List users</a></p>
</body>

</html>
