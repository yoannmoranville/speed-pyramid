<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="speedbadminton" uri="http://www.speedbadminton.eu/tags" %>
<speedbadminton:securityContext var="securityContext" />
<html>
    <head>
        <title>Pyramid view</title>
        <link rel="stylesheet" href="css/pyramid.css" type="text/css" />
        <script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
        <script type="text/javascript" src="js/pyramid.js"></script>
    </head>
    <body>
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
                <a href="#colorbox" class="spanLink" id="link_${player.id}"><span class="buildingBlock" id="${player.id}">${player.pyramidPosition}. ${player.name}</span></a>
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
    </body>
</html>