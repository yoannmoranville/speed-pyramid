<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(document).ready(function() {
        preparePlayer();
    });
</script>
<div id="player">
    ${player.pyramidPosition}. <a href="viewPyramid.html?id=${player.id}">${player.name}</a>

    <c:if test="${not empty matches}">
        <c:forEach items="${matches}" var="match">
            <br/>
            ${match.player1.name} vs ${match.player2.name} <c:choose><c:when test="${not empty match.matchDate}">(${match.result} played on "${match.matchDate}")</c:when><c:otherwise>(enter results - todo: link)</c:otherwise></c:choose>
        </c:forEach>
    </c:if>
</div>
<div class="hidden">
    <div id="colorbox" class="colorboxLeft">
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
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td id="name_player2">Name of Player2</td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
            <input class="btn" type="button" id="btnSave" value="Save" />
        </form>
    </div>
</div>