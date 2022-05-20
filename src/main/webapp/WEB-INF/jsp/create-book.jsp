<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
        <title>Book</title>
        <link rel="stylesheet" href="css/style.css">
    </head>

    <body>
        <form method="post" action="createBook">
            <b>Form for create new book:</b><br>
            <p><b>Enter new data</b><br>
                <input type="text" name="isbn"> ISBN<br>
                <input type="text" name="title"> Title<br>
                <input type="text" name="author"> Author<br>
                <input type="text" name="cover"> Cover<br>
                <input type="number" step="0.01" name="price"> Price<br>
            </p>
            <p><input type="submit" value="Submit">
                <input type="reset" value="Reset">
            </p>
        </form>
    </body>

    </html>