<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>User</title>
        <link rel="stylesheet" href="../css/style.css">
    </head>
    <body>
        <h1>User, id=${user.id}</h1>
        <div>${user.name} ${user.secondName}</div>
        <div>Email = ${user.email}</div>
        <div>Role = ${user.role.toString().toLowerCase()}</div>
        <p><a href="/api/users">List users</a></p>
    </body>
    </html>