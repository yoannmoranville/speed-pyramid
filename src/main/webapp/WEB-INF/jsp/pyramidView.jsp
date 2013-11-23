<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="speedbadminton" uri="http://www.speedbadminton.eu/tags" %>
<speedbadminton:securityContext var="securityContext" />

    <script type="text/javascript">

        $(document).on('click','.mybox', function(){
            var modal_id = '#modal_'+$(this).data('playerid');
            console.log('model_id:'+modal_id);

            $(modal_id).modal({});

        });

        $(document).on('click','.btn-challenge',function(){
            $(this).attr("disabled", "disabled");
            var logged_player = '${yourself}';
            var challenge_player = $(this).data('challenge_player');
            console.log('player '+logged_player+' is challenging '+challenge_player);

            $.post("usersEncounterQuestion.html", {asker: logged_player, asked: challenge_player}, function(data){
                if(data.success == 'true'){
                    console.log("sucessfully challenged. emails sent.");
                    location.reload();
                } else {
                    $(this).removeAttr("disabled");
                    console.log(data);
                    alert("Sorry a problem occured, please contact admin...");
                }
            });
            $(this).text("Player challenged.");
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
                                    <p>${player.email}</p>
                                    <p>${player.gender}</p>
                                </div>
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

    </div>

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