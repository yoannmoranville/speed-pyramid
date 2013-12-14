<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    $(document).ready(function(){
        preparePlayerData();
    });
</script>
<div id="player">

    <div class="well">
        <h3>${player.name} <small>Your Rank: ${player.pyramidPosition}</small></h3>


        <c:if test="${not empty matches}">
            <c:forEach items="${matches}" var="match">
                <br/>
                ${match.player1.name} vs ${match.player2.name}
                <c:choose>
                    <c:when test="${not empty match.matchDate}">
                        &nbsp;(${match.result} played on "<fmt:formatDate value="${match.matchDate}" pattern="dd-MM-yyyy" />")
                        <c:if test="${match.confirmed == false and empty matchNeedingConfirmation}">
                            &nbsp;- waiting for confirmation of the looser
                        </c:if>
                        <c:if test="${match.confirmed == false and matchNeedingConfirmation == match.id}">
                            &nbsp;- <a href="${matchNeedingConfirmationLink}">please confirm game results</a>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-success enter_result_button" data-matchid="${match.id}">enter results</button>

                        <!-- player profile + challenge dialog -->
                        <!-- Modal -->
                        <div class="modal fade" id="modal_${match.id}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" id="myModalLabel">${match.player1.name} vs. ${match.player2.name}</h4>
                                    </div>
                                    <div class="modal-body">
                                        <form id="matchform" data-matchid="${match.id}" data-challengerid="${match.player1.id}" data-challengeeid="${match.player2.id}">
                                            <table class="table">
                                                <tr>
                                                    <th></th>
                                                    <th>${match.player1.name}</th>
                                                    <th>${match.player2.name}</th>

                                                </tr>

                                                <tr>
                                                    <td>Set 1</td>

                                                    <td><input type="text" maxlength="2" name="set11" id="set11"/></td>
                                                    <td><input type="text" maxlength="2" name="set12" id="set12"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Set 2</td>

                                                    <td><input type="text" maxlength="2" name="set21" id="set21"/></td>
                                                    <td><input type="text" maxlength="2" name="set22" id="set22"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Set 3</td>

                                                    <td><input type="text" maxlength="2" name="set11" id="set31"/></td>
                                                    <td><input type="text" maxlength="2" name="set12" id="set32"/></td>
                                                </tr>
                                            </table>
                                            <label>Date of game </label>
                                            <input type="text" id="dateMatchPlayed" class="dateMatchPlayed" name="dateMatchPlayed" data-date-format="dd-mm-yyyy" />

                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        <button type="button" class="btn btn-success" id="savematch">Save Result</button>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div><!-- /.modal -->

                    </c:otherwise>
                </c:choose>



            </c:forEach>
        </c:if>
    </div>

    <div class="well">
        <h3>Change password:</h3>
        <c:if test="${not empty errorpwd}">
            <div class="alert alert-danger">${errorpwd}</div>

        </c:if>
        <c:if test="${not empty changepassword}">
            <div class="alert alert-success">Your password has been changed!</div>

        </c:if>
        <form class="form-inline" action="changepassword.html" method="post">
            <div class="form-group">
                <label class="sr-only" for="oldpassword">Email address</label>
                <input type="password" class="form-control" id="oldpassword" name="oldpassword" placeholder="Old password">
            </div>
            <div class="form-group">
                <label class="sr-only" for="newpassword">Password</label>
                <input type="password" class="form-control" id="newpassword" name="newpassword" placeholder="Password">
            </div>
            <div class="form-group">
                <label class="sr-only" for="newpasswordrepeat">Password</label>
                <input type="password" class="form-control" id="newpasswordrepeat" name="newpasswordrepeat" placeholder="Confirm Password">
            </div>

            <button type="submit" class="btn btn-default">Change Password</button>
        </form>

    </div>
    <div class="well">
        <h3>Upload picture:</h3>
        <c:if test="${not empty erroravatar}">
            ${erroravatar}
            <br/>
        </c:if>
        <c:if test="${not empty uploadpicture}">
            Your new avatar has been saved!
            <br/>
        </c:if>

        <form role="form" action="uploadpicture.html" enctype="multipart/form-data" method="post">

            <div class="form-group">
                <label for="avatar">Choose your avatar picture</label>
                <input type="file" id="avatar" name="avatar">
                <p class="help-block">Size should be in square format and maximum 800px</p>
            </div>

            <button type="submit" class="btn btn-default">Upload</button>
        </form>

    </div>

</div>


