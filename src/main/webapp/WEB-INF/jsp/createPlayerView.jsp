<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="centermain">

    <div id="createPlayer">
        <c:choose>
            <c:when test="${error == 'true'}">
                This email address already exists...
            </c:when>
            <c:otherwise>
                <form action="create_player_save.html" method="post">
                    <input type="hidden" name="id" />
                    <label for="name">Player's name</label>
                    <input type="text" id="name" name="name"/>
                    <br />
                    <label for="email">Player's email</label>
                    <input type="text" id="email" name="email"/>
                    <br />
                    <label for="gender">Player's gender</label>
                    <select name="gender" id="gender">
                        <option value="MALE">Male</option>
                        <option value="FEMALE">Female</option>
                    </select>
                    <br />
                    <input type="submit" value="Submit"/>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</div>