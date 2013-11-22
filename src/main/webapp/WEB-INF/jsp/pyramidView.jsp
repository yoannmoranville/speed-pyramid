<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="speedbadminton" uri="http://www.speedbadminton.eu/tags" %>
<speedbadminton:securityContext var="securityContext" />

    <div class="centermain">
        <c:set var="row_pos" value="1"/>
        <c:set var="max_per_row" value="1"/>

        <c:forEach items="${players}" var="player" varStatus="currentPlayer">
            <c:choose>
                <c:when test="${row_pos == 1}">
                    <div class="centerboxes">
                </c:when>
            </c:choose>

                <div class="mybox">
                    <a href="Player">${player.name}</a> <!--<span class="glyphicon glyphicon-arrow-up"></span> -->
                    <div class="player_actions">
                        <c:if test="${current_player_id == player.id}">
                            <span class="label label-info">That's you.</span>
                        </c:if>
                        <c:forEach items="${available_players}" var="aplayer_id">
                            <c:if test="${player.id == aplayer_id}">
                                <button type="button" data-toggle="modal" data-target="#myModal" class="btn btn-success btn-xs">Challenge</button>
                            </c:if>
                        </c:forEach>

                    </div>
                </div>

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

    </div>

    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Challenge Player</h4>
                </div>
                <div class="modal-body">
                    In this dialog you can challenge Player xy...
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary">Confirm</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

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
                <div id="error_user_already_challenged">You can not challenge this player, because he/she is already in a challenge</div>
                <div id="error_not_reachable">You can not challenge this player, because he/she is not reachable from your position</div>
                <div id="error_you_already_challenged">You are already in a challenge, so you can not challenge anybody else</div>
                <input class="btn hidden" type="button" id="btnEncounter" value="Ask for encounter" />
                <input class="btn" type="button" id="btnCancel" value="Cancel" />
            </form>
        </div>
    </div>