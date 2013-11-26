<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="speedbadminton" uri="http://www.speedbadminton.eu/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<speedbadminton:securityContext var="securityContext" />
    <script type="text/javascript">
        $(document).ready(function() {
            <c:if test="${not empty yourself}">preparePyramid('${yourself}', ${isInChallenge}, ${isInChallengeDate});</c:if>
            <c:if test="${empty yourself}">preparePyramidLoggedout();</c:if>
        });
    </script>

    <div class="centermain">
        <c:set var="row_pos" value="1"/>
        <c:set var="max_per_row" value="1"/>

        <c:forEach items="${players}" var="player" varStatus="currentPlayer">
            <c:set var="can_be_challenged" value="false"/>
            <c:forEach items="${available_players}" var="aplayer_id">
                <c:if test="${player.id == aplayer_id}">
                    <c:set var="can_be_challenged" value="true"/>
                </c:if>
            </c:forEach>
            <c:choose>
                <c:when test="${row_pos == 1}">
                    <div class="centerboxes">
                </c:when>
            </c:choose>

                <div class="mybox" data-playerid="${player.id}">
                    ${player.pyramidPosition}. ${player.name}
                    <div class="player_actions">
                        <c:if test="${current_player_id == player.id}">
                            <span class="label label-info">That's you.</span>
                        </c:if>
                        <c:if test="${can_be_challenged}">
                            <span class="label label-success">challenge me!</span>
                        </c:if>

                    </div>
                </div>

                <!-- player profile + challenge dialog -->
                <!-- Modal -->
                <div class="modal fade" id="modal_${player.id}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title" id="myModalLabel">${player.name}</h4>
                            </div>
                            <div class="modal-body">
                                <c:choose>
                                    <c:when test="${empty player.avatarPath}"><img src="images/nobody.jpg" alt="Avatar" class="img-circle"></c:when>
                                    <c:otherwise><img src="${avatarPath}${player.avatarPath}" alt="Avatar" class="img-circle"></c:otherwise>
                                </c:choose>
                                <div class="well">
                                    <p>${player.name}</p>
                                    <c:if test="${not empty securityContext}"><p>${player.email}</p></c:if>
                                    <p>${player.gender}</p>
                                </div>
                                <c:if test="${not empty lastResultsOfPlayer}">
                                    <div class="well">
                                        <h5>Last results:</h5>
                                        <c:forEach items="${lastResultsOfPlayer}" var="lastResult">
                                            ${lastResult.challenger.name} vs ${lastResult.challengee.name}: ${lastResult.result} played on <fmt:formatDate value="${lastResult.matchDate}" pattern="dd-MM-yyyy" />
                                            <br/>
                                        </c:forEach>
                                    </div>
                                </c:if>
                                <c:if test="${not empty openChallengesOfPlayer}">
                                    <div class="well">
                                        <h5>Open challenge:</h5>
                                        <c:forEach items="${openChallengesOfPlayer}" var="openChallenge">
                                            ${openChallenge.challenger.name} vs ${openChallenge.challengee.name}: created on <fmt:formatDate value="${openChallenge.creation}" pattern="dd-MM-yyyy" />
                                            <br/>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                <c:if test="${can_be_challenged}">
                                    <button type="button" class="btn btn-success btn-challenge" data-challenge_player="${player.id}">Challenge this player.</button>
                                </c:if>
                                <c:if test="${!can_be_challenged}">
                                    <button type="button" class="btn btn-warning" disabled="disabled">You cannot challenge.</button>
                                </c:if>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->

            <c:choose>
                <c:when test="${row_pos == max_per_row}">
                    </div>
                    <c:set var="row_pos" value="1"/>
                    <c:set var="max_per_row" value="${max_per_row + 1}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="row_pos" value="${row_pos + 1}"/>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${row_pos != 1}">
            <c:forEach begin="0" end="${max_per_row - row_pos}">
                <div class="mybox">

                </div>
            </c:forEach>
        </c:if>
        <div id="lastResults">
            <h3>Last results:</h3>
            <c:forEach items="${lastResults}" var="lastResult">
                ${lastResult.challenger.name} vs ${lastResult.challengee.name}: ${lastResult.result} played on <fmt:formatDate value="${lastResult.matchDate}" pattern="dd-MM-yyyy" />
                <br/>
            </c:forEach>
        </div>
        <div id="openChallenges">
            <h3>Open challenges:</h3>
            <c:forEach items="${openChallenges}" var="openChallenge">
                ${openChallenge.challenger.name} vs ${openChallenge.challengee.name}: created on <fmt:formatDate value="${openChallenge.creation}" pattern="dd-MM-yyyy" />
                <br/>
            </c:forEach>
        </div>
    </div>