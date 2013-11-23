<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="matches">
    <c:forEach items="${matches}" var="match">
        <fmt:formatDate value="${match.matchDate}" pattern="dd-MM-yyyy" />: <a href="viewPlayerData.html?id=${match.challenger.id}">${match.challenger.name}</a> vs <a href="viewPlayerData.html?id=${match.challengee.id}">${match.challengee.name}</a>
        <br />
    </c:forEach>
</div>