<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="speedbadminton" uri="http://www.speedbadminton.eu/tags" %>
<speedbadminton:securityContext var="securityContext" />
    <script type="text/javascript">
        $(document).ready(function() {
<c:if test="${not empty yourself}">
            preparePyramid('${yourself}', '${availables}');
</c:if>
        });
    </script>
    <div id="pyramid">
        <c:set var="jumpNb" value="1"/>
        <c:set var="currentJump" value="1"/>
        <c:forEach items="${players}" var="player" varStatus="currentPlayer">
            <a href="#colorbox" class="spanLink" id="link_${player.id}"><div class="buildingBlock" id="${player.id}">${player.pyramidPosition}.<br/>${player.name}</div></a>
            <c:choose>
                <c:when test="${jumpNb == currentJump}">
                    <div></div>
                    <c:set var="jumpNb" value="${jumpNb + 1}"/>
                    <c:set var="currentJump" value="1"/>
                </c:when>
                <c:otherwise>
                    <c:set var="currentJump" value="${currentJump + 1}"/>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:forEach begin="0" end="${jumpNb - currentJump}">
            <span class="buildingBlock empty">&nbsp;</span>
        </c:forEach>
    </div>
    <div class="hidden">
        <div id="colorbox" class="colorboxLeft">
            <form>
                <img src="images/nobody.jpg" class="avatar" id="avatar" alt="Player avatar" />
                <div id="data" class="middle">
                    <div id="name" class="middle"></div>
                    <div id="position" class="middle"></div>
                    <div id="gender" class="middle"></div>
                </div>
                <div></div>
                <input class="btn" type="button" id="btnEncounter" value="Ask for encounter" />
                <input class="btn" type="button" id="btnCancel" value="Cancel" />
            </form>
        </div>
    </div>