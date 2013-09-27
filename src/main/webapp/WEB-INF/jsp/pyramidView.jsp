<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/taglib/containsInteger.tld" prefix="speed" %>

<html>
    <head>
        <title>Pyramid default view</title>
        <script language="javascript">
            window.onload = function () {
                var landscape_canvas = document.getElementById("landscape");
                var ctx = landscape_canvas.getContext("2d");
//                ctx.fillStyle = "Blue";
//                ctx.fillRect(0, 0, 800, 850);
                ctx.fillStyle = "#67ff30";
                ctx.beginPath();
                ctx.moveTo(150, 250);
                ctx.lineTo(300, 20);
                ctx.lineTo(450, 250);
                ctx.lineTo(150, 250);
                ctx.fill();
                ctx.closePath();
            }
        </script>
    </head>
    <body>
        <div id="pyramid">
            <c:forEach items="${players}" var="player" varStatus="currentNumber">
                ${currentNumber.index}. ${player.name}
                <%--<c:if test="${speed:contains(rowJumps, currentNumber.index + 1)}">--%>
                    <br/>
                <%--</c:if>--%>
            </c:forEach>
            <canvas id="landscape" width="800" height="350"></canvas>
    </body>
</html>