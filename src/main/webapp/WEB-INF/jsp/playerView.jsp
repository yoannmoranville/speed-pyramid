<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="speedbadminton" uri="http://www.speedbadminton.eu/tags" %>
<speedbadminton:securityContext var="securityContext" />
<script type="text/javascript">
    $(document).ready(function() {
        preparePlayerList();
    });
</script>
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
                        <c:choose>
                            <c:when test="${playerView.player.enabled}">
                                <form action="disable_user.html" class="disablePlayer" method="post">
                                    <input type="hidden" name="id" value="${playerView.player.id}" />
                                    <input type="submit" value="Disable this player" />
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="enable_user.html" class="enablePlayer" method="post">
                                    <input type="hidden" name="id" value="${playerView.player.id}" />
                                    <input type="submit" value="Enable this player" />
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</div>