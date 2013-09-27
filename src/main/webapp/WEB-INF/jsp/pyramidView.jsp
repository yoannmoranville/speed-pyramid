<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/taglib/containsInteger.tld" prefix="speed" %>

<html>
    <head>
        <title>Pyramid default view</title>
        <link rel="stylesheet" href="css/pyramid.css" type="text/css" />
        <script type="text/javascript" src="http://fiddle.jshell.net/js/lib/mootools-core-1.4.5-nocompat.js"></script>
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.js"></script>
        <script type="text/javascript" src="js/pyramid.js"></script>
    </head>
    <body>
        <div id="pyramid"></div>
            <%--<c:set var="jumpNb" value="1"/>--%>
            <%--<c:set var="currentJump" value="1"/>--%>
            <div class="hidden">
                <c:forEach items="${players}" var="player" varStatus="currentPlayer">
                    <span class="player" id="player_${currentPlayer.index + 1}">${currentPlayer.index + 1}. ${player.name}</span>
                    <%--<c:choose>--%>
                        <%--<c:when test="${jumpNb == currentJump}">--%>
                            <%--</div><div>--%>
                            <%--<c:set var="jumpNb" value="${currentPlayer.index + 1}"/>--%>
                            <%--<c:set var="currentJump" value="0"/>--%>
                        <%--</c:when>--%>
                        <%--<c:otherwise>--%>
                            <%--<c:set var="currentJump" value="${currentJump + 1}"/>--%>
                        <%--</c:otherwise>--%>
                    <%--</c:choose>--%>
                </c:forEach>
            </div>
    </body>
</html>