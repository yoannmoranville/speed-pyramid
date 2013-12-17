<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="matches">
    <table class="table table-striped">
        <c:if test="${not empty allOpenChallenges}">
            <tr>
                <td colspan="5"><h3>Current Matches:</h3></td>
            </tr>
        </c:if>
        <c:forEach items="${allOpenChallenges}" var="match">
            <tr>
                <td><fmt:formatDate value="${match.creation}" pattern="dd-MM-yyyy" /></td>
                <td>${match.challenger.name} vs ${match.challengee.name}</td>
                <td><span class="label label-warning">Pending</span></td>
                <td>
                    <form action="delete_match.html" class="deleteMatch" method="post">
                        <input type="hidden" name="id" value="${match.id}" />
                        <input class="btn-danger" disabled="disabled" type="submit" value="Delete this match" />
                    </form>
                </td>
                <td/>
            </tr>
        </c:forEach>

        <c:if test="${not empty allUnconfirmedMatches}">
            <tr>
                <td colspan="5"><h3>Waiting for Confirmation by Looser:</h3></td>
            </tr>
        </c:if>
        <c:forEach items="${allUnconfirmedMatches}" var="match">
            <tr>
                <td><fmt:formatDate value="${match.matchDate}" pattern="dd-MM-yyyy" /></td>
                <td>${match.challenger.name} vs ${match.challengee.name}</td>
                <td>${match.result}</td>
                <td>
                    <form action="delete_match.html" class="deleteMatch" method="post">
                        <input type="hidden" name="id" value="${match.id}" />
                        <input class="btn-danger" disabled="disabled" type="submit" value="Delete this match" />
                    </form>
                </td>
                <td>
                    <form action="change_results.html" class="changeResults" method="post">
                        <input type="hidden" name="id" value="${match.id}" />
                        <input class="btn-danger" disabled="disabled" type="submit" value="Change the results" />
                    </form>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${not empty allLastMatchesWithResults}">
            <tr>
                <td colspan="5"><h3>All played matches:</h3></td>
            </tr>
        </c:if>
        <c:forEach items="${allLastMatchesWithResults}" var="match">
            <tr>
                <td><fmt:formatDate value="${match.matchDate}" pattern="dd-MM-yyyy" /></td>
                <td>${match.challenger.name} vs ${match.challengee.name}</td>
                <td>${match.result}</td>
                <td/>
                <td/>
            </tr>
        </c:forEach>
    </table>
</div>