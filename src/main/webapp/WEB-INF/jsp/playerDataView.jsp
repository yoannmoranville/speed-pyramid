<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    $(document).ready(function() {
        preparePlayer('${matchesWithoutResults}');
    });
</script>
<div id="player">
    ${player.pyramidPosition}. <a href="viewPyramid.html?id=${player.id}">${player.name}</a>

    <c:if test="${not empty matches}">
        <c:forEach items="${matches}" var="match">
            <br/>
            ${match.challenger.name} vs ${match.challengee.name}
                <c:choose>
                    <c:when test="${not empty match.matchDate}">
                        &nbsp;(${match.result} played on "<fmt:formatDate value="${match.matchDate}" pattern="dd-MM-yyyy" />")
                        <c:if test="${not empty match.validationId}">
                           &nbsp;- waiting for confirmation of the loose
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        (<a href="#colorbox" id="link_${match.id}">enter results</a>)
                    </c:otherwise>
                </c:choose>
        </c:forEach>
    </c:if>
</div>
<div class="hidden">
    <div id="colorbox" class="colorboxLeft">
        <div id="error" class="error strong"></div>
        <form>
            <table>
                <tr>
                    <th></th>
                    <th>Set 1</th>
                    <th>Set 2</th>
                    <th>Set 3</th>
                </tr>
                <tr>
                    <td id="name_player1">Name of Player1</td>
                    <td><input type="text" maxlength="2" name="set11" id="set11"/></td>
                    <td><input type="text" maxlength="2" name="set21" id="set21"/></td>
                    <td><input type="text" maxlength="2" name="set31" id="set31"/></td>
                </tr>
                <tr>
                    <td id="name_player2">Name of Player2</td>
                    <td><input type="text" maxlength="2" name="set12" id="set12"/></td>
                    <td><input type="text" maxlength="2" name="set22" id="set22"/></td>
                    <td><input type="text" maxlength="2" name="set32" id="set32"/></td>
                </tr>
            </table>
            <label for="dateMatchPlayed">Date of game (example 25-11-2013):</label>
            <input type="text" id="dateMatchPlayed" name="dateMatchPlayed" />
            <input class="btn" type="button" id="btnSave" value="Save" />
        </form>
    </div>
</div>