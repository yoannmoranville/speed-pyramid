<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/taglib/containsInteger.tld" prefix="speed" %>

<html>
    <head><title>Pyramid default view</title></head>
    <body>
        <div id="pyramid">
            <table>
                <c:forEach items="${players}" var="player" varStatus="currentNumber">
                    <td>
                        <c:if test="${speed:contains(rowJumps, currentNumber.index + 1)}">
                            <tr>
                        </c:if>
                        ${player.name} (${currentNumber.index + 1})
                        <c:if test="${speed:contains(rowJumps, currentNumber.index + 1)}">
                            </tr>
                        </c:if>
                    </td>
                </c:forEach>
            </table>
        </div>
    </body>
</html>