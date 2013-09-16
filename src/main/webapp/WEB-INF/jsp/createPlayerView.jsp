<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Create Player - Admin page</title>
    </head>
    <body>
        <form action="createPlayer/save.html" method="post">
            <input type="hidden" name="id">
            <label for="name">Player's name</label>
            <input type="text" id="name" name="name"/>
            <br />
            <label for="email">Player's email</label>
            <input type="text" id="email" name="email"/>
            <br />
            <label for="password">Player's password</label>
            <input type="text" id="password" name="password"/>
            <br />
            <label for="gender">Player's gender</label>
            <input type="text" id="gender" name="gender"/>
            <br />
            <input type="submit" value="Submit"/>
        </form>
    </body>
</html>