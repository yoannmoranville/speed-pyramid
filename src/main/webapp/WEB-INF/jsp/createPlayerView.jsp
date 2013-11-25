<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="centermain">

    <div id="createPlayer">
        <form action="create_player_save.html" method="post">
            <input type="hidden" name="id" />
            <label for="name">Player's name</label>
            <input type="text" id="name" name="name"/>
            <c:if test="${not empty error_name}"><div class="alert alert-danger">${error_name}</div></c:if>
            <br />
            <label for="email">Player's email</label>
            <input type="text" id="email" name="email"/>
            <c:if test="${not empty error_email}"><div class="alert alert-danger">${error_email}</div></c:if>
            <br />
            <label for="gender">Player's gender</label>
            <select name="gender" id="gender">
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
            </select>
            <br />
            <input type="submit" value="Submit"/>
        </form>
    </div>
</div>