<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="players">
    <c:forEach items="${players}" var="player" varStatus="step">
        ${player.pyramidPosition}. <a href="viewPyramid.html?id=${player.id}">${player.name}</a>
        <c:if test="${not empty matches}">
            <c:forEach items="${matches}" var="match">
                <br/>
                ${match.challenger} vs ${match.challenger} (on ${match.matchDate})
            </c:forEach>
        </c:if>
        <br />
    </c:forEach>
</div>