<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="matches">
    <c:forEach items="${matches}" var="match">
        ${match.player1.name} vs ${match.player2.name}: <fmt:formatDate value="${match.matchDate}" pattern="dd-MM-yyyy" />
        <br />
    </c:forEach>
</div>