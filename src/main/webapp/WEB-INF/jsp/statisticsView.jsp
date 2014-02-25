<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="speedbadminton" uri="http://www.speedbadminton.eu/tags" %>
<speedbadminton:securityContext var="securityContext" />
<script type="text/javascript">
    $(document).ready(function() {
        bindStatistics();
    });
</script>
<div id="statistics">
    <div style="float:left; width:150px;">
        <ul class="nav nav-stacked">
            <c:forEach items="${players}" var="player" varStatus="step">
                <li>
                    <input id="${player.id}" type="radio" name="radio_player" class="radio_player"/>${player.pyramidPosition}. ${player.name}
                </li>
            </c:forEach>
        </ul>
    </div>
    <div style="float:left; margin-left:160px;">
        <div id="line-statistics"></div>
    </div>
</div>
<div style="clear:both;"></div>