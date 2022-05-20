<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>User</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
<form method="post" action="createUser">
    <b>Form for create new user:</b><br>
    <p><b>Enter new data</b><br>
        <input type="text" name="name"> Name<br>
        <input type="text" name="secondName"> Second Name<br>
        <input type="text" name="email"> Email<br>
        <input type="password" name="password"> Password<br>
        <input type="text" name="role"> Role<br>
    </p>
    <p><input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </p>
</form>
</body>

</html>
