<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Player view</title></head>
<body>

<c:forEach items="${players}" var="player" varStatus="step">
    ${step.index}. <a href="viewPlayers.html?id=${player.id}">${player.name}</a>
    <c:if test="${not empty matches}">
        <c:forEach items="${matches}" var="match">
            <br/>
            ${match.player1} vs ${match.player2} (on ${match.matchDate})
        </c:forEach>
    </c:if>
    <br />
</c:forEach>

</body>
</html>