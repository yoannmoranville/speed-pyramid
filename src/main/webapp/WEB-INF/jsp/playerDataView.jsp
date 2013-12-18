<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="player">

    <div class="well">
        <h3>${player.name} <small>Your Rank: ${player.pyramidPosition}</small></h3>
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
                <p class="help-block">Size should be maximum 100px width and 150px height</p>
            </div>

            <button type="submit" class="btn btn-default">Upload</button>
        </form>

    </div>

</div>


