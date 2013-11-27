<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${error == 'true'}">
        Sorry, there was a problem, please contact the administrators...
    </c:when>
    <c:otherwise>
        The results have been saved, you will receive a mail with the final results.
        <br/>
        You will be redirected in 3 seconds...
        <script type="text/javascript">
            setTimeout(function() {
                window.location.href = 'viewPlayerData.html'
            }, 3000);
        </script>
    </c:otherwise>
</c:choose>