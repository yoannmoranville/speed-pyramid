<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    $(document).ready(function() {
        preparePlayer('${matchesWithoutResults}');
    });
</script>
<div id="player">
    <div>
        Change password:<br/>
        <c:if test="${not empty errorpwd}">
            ${errorpwd}
            <br/>
        </c:if>
        <c:if test="${not empty changepassword}">
            Your password has been changed
            <br/>
        </c:if>
        <form action="changepassword.html" method="post">
            <label for="oldpassword">Old password: </label>
            <input type="password" name="oldpassword" id="oldpassword"/>
            <label for="newpassword">New password: </label>
            <input type="password" name="newpassword" id="newpassword"/>
            <label for="newpasswordrepeat">Repeat new password: </label>
            <input type="password" name="newpasswordrepeat" id="newpasswordrepeat"/>
            <input type="submit" value="Change" />
        </form>
    </div>
    <div>
        Upload picture: (todo)<br/>
        <c:if test="${not empty erroravatar}">
            ${erroravatar}
            <br/>
        </c:if>
        <c:if test="${not empty uploadpicture}">
            Your new avatar has been saved!
            <br/>
        </c:if>
        <form action="uploadpicture.html" enctype="multipart/form-data" method="post">
            <input type="file" id="avatar" name="avatar" />
            <input type="submit" value="Submit" id="submit" />
        </form>
    </div>
    <div>
        ${player.pyramidPosition}. ${player.name}

        <c:if test="${not empty matches}">
            <c:forEach items="${matches}" var="match">
                <br/>
                ${match.challenger.name} vs ${match.challengee.name}
                    <c:choose>
                        <c:when test="${not empty match.matchDate}">
                            &nbsp;(${match.result} played on "<fmt:formatDate value="${match.matchDate}" pattern="dd-MM-yyyy" />")
                            <c:if test="${not empty match.validationId and empty matchNeedingConfirmation}">
                               &nbsp;- waiting for confirmation of the looser
                            </c:if>
                            <c:if test="${not empty match.validationId and matchNeedingConfirmation == match.id}">
                                &nbsp;- <a href="${matchNeedingConfirmationLink}">please confirm game results</a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            (<a href="#colorbox" id="link_${match.id}">enter results</a>)
                        </c:otherwise>
                    </c:choose>
            </c:forEach>
        </c:if>
    </div>
</div>
<div class="hidden">
    <div id="colorbox" class="colorboxLeft">
        <div id="error" class="error strong"></div>
        <form>
            <table>
                <tr>
                    <th></th>
                    <th>Set 1</th>
                    <th>Set 2</th>
                    <th>Set 3</th>
                </tr>
                <tr>
                    <td id="name_player1">Name of Player1</td>
                    <td><input type="text" maxlength="2" name="set11" id="set11"/></td>
                    <td><input type="text" maxlength="2" name="set21" id="set21"/></td>
                    <td><input type="text" maxlength="2" name="set31" id="set31"/></td>
                </tr>
                <tr>
                    <td id="name_player2">Name of Player2</td>
                    <td><input type="text" maxlength="2" name="set12" id="set12"/></td>
                    <td><input type="text" maxlength="2" name="set22" id="set22"/></td>
                    <td><input type="text" maxlength="2" name="set32" id="set32"/></td>
                </tr>
            </table>
            <label for="dateMatchPlayed">Date of game (example 25-11-2013):</label>
            <input type="text" id="dateMatchPlayed" name="dateMatchPlayed" />
            <br/>
            <input class="btn" type="button" id="btnSave" value="Save" />
            <br/>
            <input class="btn" type="button" id="btnCancel" value="Cancel" />
        </form>
    </div>
</div>