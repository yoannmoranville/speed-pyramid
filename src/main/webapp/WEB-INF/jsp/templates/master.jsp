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

        <link href="favicon.ico" rel="shortcut icon" />
        <link rel="stylesheet" href="bootstrap/css/bootstrap.css" type="text/css" />
        <link rel="stylesheet" href="css/customstyles.css" type="text/css" />
        <link rel="stylesheet" href="bootstrap/datepicker/css/datepicker.css" type="text/css" />

        <script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
        <script type="text/javascript" src="js/pyramid.js"></script>
        <script type="text/javascript" src="js/player.js"></script>
        <script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
        <script type="text/javascript" src="bootstrap/datepicker/js/bootstrap-datepicker.js"></script>
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
                </div>

                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="viewPyramid.html">Speed Pyramid</a></li>
                        <c:if test="${not empty securityContext}">
                            <li><a href="viewPlayerData.html">Profile</a></li>
                            <c:if test="${securityContext.admin}">
                                <li><a href="createPlayer.html">Create new player</a></li>
                                <li><a href="viewPlayers.html">List of players</a></li>
                                <li><a href="viewMatches.html">List of matches</a></li>
                            </c:if>
                            <form class="navbar-form navbar-right" action="logout.html" method="GET">
                                <button type="submit" class="btn btn-warning">Sign out</button>
                            </form>
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
                    <h4>Hi <span class="strong"><c:out value="${securityContext.name}"/></span></h4>
                    <div><c:if test="${securityContext.child}"><a href="logout.html?parent=true">Switch back to <c:out value="${securityContext.parentName}"/></a></c:if></div>
                    <div id="isInChallenge"></div>
                </div>
            </c:when>
        </c:choose>

        <div class="main">
            <tiles:insertAttribute name="body"/>
        </div>

        <div class="container">
            <hr>
            <footer>
                <p><a target="_blank" href="https://github.com/yoannmoranville/speed-pyramid"> Speed-Pyramid 2013</a></p>
            </footer>
        </div>
    </body>
</html>