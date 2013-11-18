<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${error == 'true'}">
        Sorry, there was a problem, please contact the administrators...
    </c:when>
    <c:otherwise>
        The results have been saved, you will receive a mail with the final results.
        <br/>
        You can already log in and check your new position.
    </c:otherwise>
</c:choose>