<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="speedbadminton" uri="http://www.speedbadminton.eu/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<speedbadminton:securityContext var="securityContext" />
    <c:set var="loggedPlayer" value="${pyramidViewModel.loggedPlayer}" />
    <c:set var="loggedPlayerIsFree" value="${pyramidViewModel.isLoggedPlayerIsFree()}" />
    <c:set var="loggedPlayerChallenge" value="${pyramidViewModel.getLoggedPlayerChallenge()}" />
    <c:set var="lastOverallMatches" value="${pyramidViewModel.lastOverallMatches}" />
    <c:set var="openChallenges" value="${pyramidViewModel.openChallenges}" />
    <c:set var="lastPlayerMatches" value="${pyramidViewModel.lastPlayerMatches}" />
    <c:set var="waitingForConfirmationMatches" value="${pyramidViewModel.waitingForConfirmationMatches}" />
    <c:set var="unconfirmedLostMatch" value="${pyramidViewModel.unconfirmedLostMatch}"/>
    <c:set var="unconfirmedWaitingMatch" value="${pyramidViewModel.unconfirmedWaitingMatch}"/>

    <script type="text/javascript">
        $(document).ready(function(){
            bindPyramidFunctions(${isInChallengeDate});
        });
    </script>

<!-- DEBUG: loggedPlayer: ${loggedPlayer}
            loggedPlayerIsFree: ${loggedPlayerIsFree}
            loggedPlayerChallenge: ${loggedPlayerChallenge}
-->
    <!-- Player has to confirm a match result where he lost -->
    <c:if test="${unconfirmedLostMatch!=null}">
        <div class="jumbotron">
            ${unconfirmedLostMatch.challenger.name} vs ${unconfirmedLostMatch.challengee.name} <span class="label label-danger">You lost. ${unconfirmedLostMatch.result}</span>
            <span>&nbsp;</span><button id="confirmLostMatch" class="btn btn-success" data-matchid="${unconfirmedLostMatch.id}">Confirm Result</button>
        </div>
    </c:if>
    <!-- Player is waiting for confirmation of Looser -->
    <c:if test="${unconfirmedWaitingMatch!=null}">
        <div class="jumbotron">
                ${unconfirmedWaitingMatch.challenger.name} vs ${unconfirmedWaitingMatch.challengee.name} <span class="label label-success">You won. (${unconfirmedWaitingMatch.result})</span> <span class="label label-warning">Waiting for confirmation. </span>
        </div>
    </c:if>
    <!-- Showing Player's open challenge. -->
    <c:if test="${loggedPlayerChallenge!=null}">
        <div class="jumbotron" id="openChallenges">
                ${loggedPlayerChallenge.challenger.name} vs ${loggedPlayerChallenge.challengee.name}
            <button id="enter_result_button" class="btn btn-success" data-matchid="${loggedPlayerChallenge.id}">Enter results</button>
        </div>

        <!-- Modal for Enter Results -->
        <div class="modal fade" id="modal_results" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">${loggedPlayerChallenge.challenger.name} vs. ${loggedPlayerChallenge.challengee.name}</h4>
                    </div>
                    <div class="modal-body">
                        <form id="matchform" data-matchid="${loggedPlayerChallenge.id}" data-challengerid="${loggedPlayerChallenge.challenger.id}" data-loggedplayerid="${loggedPlayer.id}" data-challengeeid="${loggedPlayerChallenge.challengee.id}">
                            <table class="table">
                                <tr>
                                    <th></th>
                                    <th>${loggedPlayerChallenge.challenger.name}</th>
                                    <th>${loggedPlayerChallenge.challengee.name}</th>

                                </tr>

                                <tr>
                                    <td>Set 1</td>

                                    <td><input class="set" type="text" maxlength="2" name="set11" id="set11"/></td>
                                    <td><input class="set" type="text" maxlength="2" name="set12" id="set12"/></td>
                                </tr>
                                <tr>
                                    <td>Set 2</td>

                                    <td><input class="set" type="text" maxlength="2" name="set21" id="set21"/></td>
                                    <td><input class="set" type="text" maxlength="2" name="set22" id="set22"/></td>
                                </tr>
                                <tr>
                                    <td>Set 3</td>

                                    <td><input class="set" type="text" maxlength="2" name="set11" id="set31"/></td>
                                    <td><input class="set" type="text" maxlength="2" name="set12" id="set32"/></td>
                                </tr>
                            </table>
                            <label>Date of game </label>
                            <input type="text" id="dateMatchPlayed" class="dateMatchPlayed" name="dateMatchPlayed" data-date-format="dd-mm-yyyy" />

                        </form>
                        <div id="resultsValidationBox" class="alert alert-danger">

                        </div>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-success" id="savematch">Save Result</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </c:if>

    <div class="centermain">
        <c:set var="row_pos" value="1"/>
        <c:set var="max_per_row" value="1"/>

        <c:forEach items="${pyramidViewModel.playerViewModelList}" var="playerViewModel">
            <c:set var="player" value="${playerViewModel.player}" />
            <c:set var="currentMatch" value="${playerViewModel.currentMatch}" />
            <c:set var="pastMatches" value="${playerViewModel.pastMatches}" />

            <c:set var="can_be_challenged" value="${playerViewModel.free}"/>

            <c:choose>
                <c:when test="${row_pos == 1}">
                    <div class="centerboxes">
                </c:when>
            </c:choose>

                <div class="mybox" data-playerid="${player.id}">
                    <c:set var="colorOfPositionLabel" value="label-primary" />
                    <c:choose>
                        <c:when test="${player.id == loggedPlayer.id}">
                            <c:set var="colorOfPositionLabel" value="label-info" />
                        </c:when>
                        <c:otherwise>
                            <c:if test="${can_be_challenged && loggedPlayerIsFree}">
                                <c:set var="colorOfPositionLabel" value="label-success" />
                            </c:if>
                            <c:if test="${currentMatch!=null}">
                                <c:set var="colorOfPositionLabel" value="label-warning" />
                            </c:if>
                        </c:otherwise>
                    </c:choose>

                    <span class="label ${colorOfPositionLabel}">${player.pyramidPosition}</span>
                    <div>${player.name}</div>
                    <%--<div class="player_actions">--%>
                        <%--<c:choose>--%>
                            <%--<c:when test="${player.id == loggedPlayer.id}">--%>
                                <%--<c:choose>--%>
                                    <%--<c:when test="${currentMatch!=null}">--%>
                                        <%--<span class="label label-primary">You play vs. ${playerViewModel.getCurrentOpponent().name}</span>--%>
                                    <%--</c:when>--%>
                                    <%--<c:otherwise>--%>
                                        <%--<span class="label label-primary">--%>
                                        <%--That's you.--%>
                                        <%--</span>--%>
                                    <%--</c:otherwise>--%>
                                <%--</c:choose>--%>
                                <%--<c:if test="${player.id == loggedPlayer.id}">--%>

                                <%--</c:if>--%>
                            <%--</c:when>--%>
                            <%--<c:otherwise>--%>
                                <%--<c:if test="${can_be_challenged && loggedPlayerIsFree}">--%>
                                    <%--<span class="label label-success">challenge me!</span>--%>
                                <%--</c:if>--%>
                                <%--<c:if test="${currentMatch!=null}">--%>
                                    <%--<span class="label label-info">plays vs. ${playerViewModel.getCurrentOpponent().name}</span>--%>
                                <%--</c:if>--%>
                            <%--</c:otherwise>--%>
                        <%--</c:choose>--%>


                    <%--</div>--%>
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
                                    <c:when test="${empty player.avatarPath}"><img src="images/nobody.jpg" alt="Avatar" class="img-circle avatar"></c:when>
                                    <c:otherwise><img src="${avatarPath}${player.avatarPath}" alt="Avatar" class="img-circle"></c:otherwise>
                                </c:choose>
                                <div class="well">
                                    <p>${player.name}</p>
                                    <c:if test="${not empty securityContext}"><p>${player.email}</p></c:if>
                                    <p>${player.gender}</p>
                                </div>
                                <c:if test="${not empty pastMatches}">
                                    <div class="well" id="lastResultPlayer_${player.id}">
                                        <h5>Last results of player:</h5>
                                        <table class="table">
                                            <c:forEach var="match" items="${pastMatches}">
                                                <tr><td><fmt:formatDate value="${match.matchDate}" pattern="dd-MM-yyyy" /></td><td>${match.challenger.name} vs. ${match.challengee.name}</td><td>${match.result}</td></tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </c:if>
                                <c:if test="${currentMatch!=null}">
                                    <div class="well" id="openChallengePlayer_${player.id}">
                                        <h5>Open challenge:</h5>
                                        <tr><td><fmt:formatDate value="${currentMatch.matchDate}" pattern="dd-MM-yyyy" /></td><td>${currentMatch.challenger.name} vs. ${currentMatch.challengee.name}</td><td>${currentMatch.result}</td></tr>
                                    </div>
                                </c:if>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                <c:if test="${loggedPlayerChallenge == null}">
                                    <c:if test="${can_be_challenged}">
                                        <button type="button" class="btn btn-success btn-challenge" data-challenge_player="${player.id}" data-challengee_player_name="${player.name}">Challenge this player.</button>
                                    </c:if>
                                    <c:if test="${!can_be_challenged}">
                                        <button type="button" class="btn btn-warning" disabled="disabled">You cannot challenge.</button>
                                    </c:if>
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

        <c:if test="${not empty waitingForConfirmationMatches}">
            <div id="lastResults">
                <h3>Waiting for Confirmation by Looser:</h3>
                <table class="table table-condensed">
                    <c:forEach items="${waitingForConfirmationMatches}" var="lastResult">
                        <c:set var="isChallengerWinner" value="span"/>
                        <c:set var="isChallengerLooser" value="strong"/>
                        <c:if test="${lastResult.challenger.id == lastResult.result.getMatchWinner().id}">
                            <c:set var="isChallengerWinner" value="strong"/>
                            <c:set var="isChallengerLooser" value="span"/>
                        </c:if>
                        <tr>
                            <td><fmt:formatDate value="${lastResult.matchDate}" pattern="dd-MM-yyyy" /></td>
                            <td><${isChallengerWinner}>${lastResult.challenger.name}</${isChallengerWinner}> vs <${isChallengerLooser}>${lastResult.challengee.name}</${isChallengerLooser}></td>
                            <td>${lastResult.result}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
        <c:if test="${not empty lastPlayerMatches}">
            <div id="lastResults">
                <h3>Your last matches:</h3>
                <table class="table table-condensed">
                    <c:forEach items="${lastPlayerMatches}" var="lastResult">
                        <c:set var="isChallengerWinner" value="span"/>
                        <c:set var="isChallengerLooser" value="strong"/>
                        <c:if test="${lastResult.challenger.id == lastResult.result.getMatchWinner().id}">
                            <c:set var="isChallengerWinner" value="strong"/>
                            <c:set var="isChallengerLooser" value="span"/>
                        </c:if>
                        <tr>
                            <td><fmt:formatDate value="${lastResult.matchDate}" pattern="dd-MM-yyyy" /></td>
                            <td><${isChallengerWinner}>${lastResult.challenger.name}</${isChallengerWinner}> vs <${isChallengerLooser}>${lastResult.challengee.name}</${isChallengerLooser}></td>
                            <td>${lastResult.result}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
        <c:if test="${not empty lastOverallMatches}">
            <div id="lastResults">
                <h3>Last Pyramid Matches:</h3>
                <table class="table table-condensed">
                    <c:forEach items="${lastOverallMatches}" var="lastResult">
                        <c:set var="isChallengerWinner" value="span"/>
                        <c:set var="isChallengerLooser" value="strong"/>
                        <c:if test="${lastResult.challenger.id == lastResult.result.getMatchWinner().id}">
                            <c:set var="isChallengerWinner" value="strong"/>
                            <c:set var="isChallengerLooser" value="span"/>
                        </c:if>
                        <tr>
                            <td><fmt:formatDate value="${lastResult.matchDate}" pattern="dd-MM-yyyy" /></td>
                            <td><${isChallengerWinner}>${lastResult.challenger.name}</${isChallengerWinner}> vs <${isChallengerLooser}>${lastResult.challengee.name}</${isChallengerLooser}></td>
                            <td>${lastResult.result}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
        <c:if test="${not empty openChallenges}">
            <div id="lastResults">
                <h3>Next Matches:</h3>
                <table class="table table-condensed">
                    <c:forEach items="${openChallenges}" var="lastResult">
                        <c:set var="isChallengerWinner" value="span"/>
                        <c:set var="isChallengerLooser" value="strong"/>
                        <c:if test="${lastResult.challenger.id == lastResult.result.getMatchWinner().id}">
                            <c:set var="isChallengerWinner" value="strong"/>
                            <c:set var="isChallengerLooser" value="span"/>
                        </c:if>
                        <tr>
                            <td><fmt:formatDate value="${lastResult.creation}" pattern="dd-MM-yyyy" /></td>
                            <td><${isChallengerWinner}>${lastResult.challenger.name}</${isChallengerWinner}> vs <${isChallengerLooser}>${lastResult.challengee.name}</${isChallengerLooser}></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>


        <img alt="loader" src="images/loader.gif" class="hidden" />
    </div>