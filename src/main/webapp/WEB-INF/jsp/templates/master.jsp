<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="speedbadminton" uri="http://www.speedbadminton.eu/tags" %>
<speedbadminton:securityContext var="securityContext" />
<html lang="en">
<head>

    <meta content="text/html; charset=UTF-8;" http-equiv="content-type" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><tiles:getAsString name="title" ignore="true"/></title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.css" type="text/css" />
    <link rel="stylesheet" href="css/customstyles.css" type="text/css" />

    <script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>

</head>

<body>


<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="viewPyramid.html">Speed Pyramid</a>
        </div>

        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="viewPyramid.html">View pyramid</a></li>
                <c:if test="${not empty securityContext}">
                    <li><a href="viewPlayerData.html">View your user data</a></li>

                    <c:if test="${securityContext.admin}">
                        <li><a href="createPlayer.html">New Player</a></li>
                        <li><a href="viewPlayers.html">Player List</a></li>
                        <li><a href="viewMatches.html">Matches</a></li>
                    </c:if>
                    <li><a href="logout.html">Log out</a></li>
                </c:if>
                <c:if test="${empty securityContext}">
                    <form class="navbar-form navbar-right" action="login.html" method="GET">
                        <button type="submit" class="btn btn-success">Sign in</button>
                    </form>
                </c:if>
            </ul>
        </div><!--/.nav-collapse -->



    </div>
</div>

    <c:choose>
        <c:when test="${not empty securityContext}">
            <div class="well">
                Hi <span class="strong"><c:out value="${securityContext.name}"/></span> | <c:if test="${securityContext.child}"><a href="logout.html?parent=true">Switch back to <c:out value="${securityContext.parentName}"/></a> | </c:if>
            </div>
        </c:when>
    </c:choose>
<div class="main">
    <tiles:insertAttribute name="body"/>
</div>


<div class="container">
    <!-- Example row of columns
    <div class="row">
        <div class="col-md-4">
            <h2>Heading</h2>
            <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-md-4">
            <h2>Heading</h2>
            <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-md-4">
            <h2>Heading</h2>
            <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
    </div>
    -->
    <hr>

    <footer>
        <p>&copy; Speed-Pyramid 2013</p>
    </footer>
</div> <!-- /container -->


</body>
</html>


</body>
</html>