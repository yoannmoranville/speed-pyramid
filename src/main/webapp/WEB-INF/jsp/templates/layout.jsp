<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="speedbadminton" uri="http://www.speedbadminton.eu/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta content="text/html; charset=UTF-8;" http-equiv="content-type" />
    <title><tiles:getAsString name="title" ignore="true"/></title>
    <link rel="stylesheet" href="css/<tiles:getAsString name="css"/>" type="text/css" />
    <script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="js/jquery.colorbox-min.js"></script>
    <script type="text/javascript" src="js/<tiles:getAsString name="js" />"></script>
</head>
<body>
<speedbadminton:securityContext var="securityContext" />
<div id="principal">
    <div id="wrap">
        <div id="speedbadminton">
            <div id="header">
                <ul>
                    <li><a href="viewPyramid.html">View pyramid</a></li>
                    <c:if test="${not empty securityContext}">
                        <li><a href="viewPlayerData.html">View your user data</a></li>
                        <c:if test="${securityContext.admin}">
                            <li><a href="createPlayer.html">Create a player</a></li>
                            <li><a href="viewPlayers.html">View list of players</a></li>
                            <li><a href="viewMatches.html">View list of matches</a></li>
                        </c:if>
                    </c:if>
                </ul>
                <p id="userFeatures">
                    <c:choose>
                        <c:when test="${empty securityContext}">
                            <a href="login.html">Log in</a>
                        </c:when>
                        <c:otherwise>
                            You are logged in as <span class="strong">${securityContext.name}</span> - <a href="logout.html">Log out</a>
                        </c:otherwise>
                    </c:choose>
                </p>
            </div>
        </div>
        <div id="main" class="main">
            <tiles:insertAttribute name="body"/>
        </div>
    </div>
    <div id="footer">
        <p id="footerSupportText">Developed by Domme, Lukas and Yoann (Sourcecode on <a target="_blank" href="https://github.com/yoannmoranville/speed-pyramid">Github</a>)</p>
    </div>
</div>
</body>
</html>                