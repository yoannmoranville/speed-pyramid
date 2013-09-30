<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Create Player - Admin page</title>
    </head>
    <body>
        <form action="create_player_save.html" method="post">
            <input type="hidden" name="id" />
            <label for="position">Player's position</label>
            <input type="text" id="position" name="position" value="${nextPosition}" disabled="disabled"/>
            <br/>
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
            <select name="gender" id="gender">
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
            </select>
            <br />
            <input type="submit" value="Submit"/>
        </form>
    </body>
</html>