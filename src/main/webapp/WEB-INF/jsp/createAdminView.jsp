<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="centermain">
    <div id="createPlayer">
        <form action="saveAdmin.html" method="post">
            <label for="email">Email</label>
            <input type="text" id="email" name="email"/>
            <c:if test="${not empty error_email}"><div class="alert alert-danger">${error_email}</div></c:if>
            <br />
            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>
            <c:if test="${not empty error_password}"><div class="alert alert-danger">${error_password}</div></c:if>
            <br />
            <label for="name">Name</label>
            <input type="text" id="name" name="name"/>
            <c:if test="${not empty error_name}"><div class="alert alert-danger">${error_name}</div></c:if>
            <br />
            <label for="gender">Gender</label>
            <select name="gender" id="gender">
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
            </select>
            <br />
            <input type="submit" value="Submit"/>
        </form>
    </div>
</div>