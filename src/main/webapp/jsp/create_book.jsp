<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
        <title>Book</title>
        <link rel="stylesheet" href="css/style.css">
    </head>

    <body>
        <form name="create-book" method="post" action="controller">
            <b>Form for create new book:</b><br>
            <p><b>Enter new data</b><Br>
            <input type="hidden" name="command" value="create-book">
                <input type="text" name="isbn"> ISBN<Br>
                <input type="text" name="title"> Title<Br>
                <input type="text" name="author"> Author<Br>
                <input type="text" name="cover"> Cover<Br>
                <input type="number" step="0.01" name="price"> Price<Br>
            </p>
            <p><input type="submit" value="Submit">
                <input type="reset" value="Reset">
            </p>
        </form>
    </body>

    </html>