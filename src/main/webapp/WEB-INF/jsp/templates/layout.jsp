<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="speedbadminton" uri="http://www.speedbadminton.eu/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta content="text/html; charset=UTF-8;" http-equiv="content-type" />
    <title><tiles:getAsString name="title" ignore="true"/></title>
</head>
<body>
<speedbadminton:securityContext var="securityContext" />
<div id="principal">
    <div id="wrap">
        <div id="speedbadminton">
            <div id="header">
                <div id="left-header">
                    <div id="titledashboard"><tiles:getAsString name="title"/></div>
                </div>
            </div>
            <div id="menubar" >
                <div id="userFeatures">
                    <c:choose>
                        <c:when test="${empty securityContext}">
                            <a href="login.html">Log in</a>
                        </c:when>
                        <c:otherwise>
                            Edit details - <a href="logout.html">Log out</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div id="main" class="main">
            <tiles:insertAttribute name="body"/>
        </div>
    </div>
    <div id="footer">
        <div id="footerSupportText">Created by ...</div>
    </div>
</div>
</body>
</html>                