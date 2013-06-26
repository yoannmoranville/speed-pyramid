<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Player view</title></head>
<body>

<c:forEach items="${players}" var="player">
    ${player.name} ${player.age}
    <br />
</c:forEach>

</body>
</html>