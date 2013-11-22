<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="speedbadminton" uri="http://www.speedbadminton.eu/tags" %>
<speedbadminton:securityContext var="securityContext" />
<div id="players">
    <table>
        <c:forEach items="${players}" var="playerView" varStatus="step">
            <tr>
                <td>${playerView.player.pyramidPosition}. ${playerView.player.name}</td>
                <c:if test="${securityContext.admin}">
                    <td>
                    <c:choose>
                        <c:when test="${!playerView.inUse}">
                            <form action="switch_to_player.html" method="post">
                                <input type="hidden" name="id" value="${playerView.player.id}" />
                                <input type="submit" value="Log in as this player" />
                            </form>
                        </c:when>
                        <c:otherwise>
                            Player already logged in
                        </c:otherwise>
                    </c:choose>
                    </td>
                    <td>
                        <form action="delete_user.html" method="post">
                            <input type="hidden" name="id" value="${playerView.player.id}" />
                            <input type="submit" value="Delete this player" />
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</div>