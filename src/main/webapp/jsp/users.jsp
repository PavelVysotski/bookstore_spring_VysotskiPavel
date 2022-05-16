<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>
        <head>
            <title>Users</title>
            <link rel="stylesheet" href="css/style.css">
        </head>
        <body>
            <h1>Users</h1>
            <table id="TableUsers">
                <tr>
                    <th>N#</th>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Second Name</th>
                    <th>Email</th>
                    <th>Role</th>
                </tr>
                <c:forEach items="${users}" var="user" varStatus="counter">
                    <tr>
                        <td>${counter.count}</td>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.secondName}</td>
                        <td>${user.email}</td>
                        <td>${user.role.toString().toLowerCase()}</td>
                    </tr>
                </c:forEach>
            </table>
        </body>
        </html>