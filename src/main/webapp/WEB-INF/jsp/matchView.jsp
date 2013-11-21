<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="matches">
    <c:forEach items="${matches}" var="match">
        ${match.matchDate}: <a href="viewPlayerData.html?id=${match.challengerId}">${match.challengerName}</a> vs <a href="viewPlayerData.html?id=${match.challengeeId}">${match.challengeeName}</a>
        <br />
    </c:forEach>
</div>