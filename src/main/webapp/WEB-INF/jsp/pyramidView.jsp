<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/taglib/containsInteger.tld" prefix="speed" %>

<html>
    <head>
        <title>Pyramid default view</title>
        <link rel="stylesheet" href="css/pyramid.css" type="text/css" />
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.js"></script>
        <script type="text/javascript" src="js/pyramid.js"></script>
        <script type="text/javascript">
            $(function() {
                preparePyramid(${yourself}, ${availables});
            });
        </script>
    </head>
    <body>
        <div id="pyramid">
            <c:set var="jumpNb" value="1"/>
            <c:set var="currentJump" value="1"/>
            <c:forEach items="${players}" var="player" varStatus="currentPlayer">
                <c:choose>
                    <c:when test="${not empty yourself and yourself == player.id}">
                        <div class="buildingBlock yourself" id="${player.id}">${currentPlayer.index + 1}. ${player.name}</div>
                    </c:when>
                    <c:otherwise>
                        <div class="buildingBlock" id="${player.id}">${currentPlayer.index + 1}. ${player.name}</div>
                    </c:otherwise>
                </c:choose>
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
                <div class="buildingBlock empty">&nbsp;</div>
            </c:forEach>
        </div>
    </body>
</html>

<%--<c:forEach items="${availables}" var="available">--%>
    <%--<c:choose>--%>
        <%--<c:when test="${available.id == player.id}">--%>
            <%--<div class="buildingBlock available" id="${player.id}">${currentPlayer.index + 1}. ${player.name}</div>--%>
        <%--</c:when>--%>
        <%--<c:otherwise>--%>
            <%--<div class="buildingBlock" id="${player.id}">${currentPlayer.index + 1}. ${player.name}</div>--%>
        <%--</c:otherwise>--%>
    <%--</c:choose>--%>
<%--</c:forEach>--%>