<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head><title>Login</title></head>
    <body>
        <c:if test="${error == 'true'}">
            Your username or your password is wrong...
        </c:if>
        <form action="check_login.html" method="post">
            <label for="email">Email address</label>
            <input type="text" name="email" id="email"/>
            <br />
            <label for="password">Password</label>
            <input type="password" name="password" id="password" />
            <br />
            <input type="submit" method="execute" value="login" />
        </form>
    </body>
</html>