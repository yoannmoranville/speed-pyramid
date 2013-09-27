<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                preparePyramid(${yourself}, ${availables}); //availables being a list, it does not convert good into JS?
            });
        </script>
        <div id="pyramid">
            <c:set var="jumpNb" value="1"/>
            <c:set var="currentJump" value="1"/>
            <c:forEach items="${players}" var="player" varStatus="currentPlayer">
                <div class="buildingBlock" id="${player.id}">${currentPlayer.index + 1}. ${player.name}</div>
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