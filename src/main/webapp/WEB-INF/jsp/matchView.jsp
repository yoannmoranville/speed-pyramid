<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Player view</title></head>
<body>

<c:forEach items="${matches}" var="match">
    ${match.matchDate}: <a href="viewPlayers.html?id=${match.player1.id}">${match.player1.name}</a> vs <a href="viewPlayers.html?id=${match.player2.id}">${match.player2.name}</a>
    <br />
</c:forEach>

</body>
</html>