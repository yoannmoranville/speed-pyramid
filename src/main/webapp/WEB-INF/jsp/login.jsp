<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
    <div class="container">
        <h1>Hello, Gekko!</h1>

        <div class="container loginbox">

            <form class="form-signin" action="check_login.html" method="post">
                <h2 class="form-signin-heading">Please sign in</h2>
                <c:if test="${error}">
                    <div class="alert alert-danger">Your username or your password is wrong...</div>
                </c:if>
                <%--<c:if test="${error == 'disabled'}">--%>
                    <%--<div class="alert alert-danger">You user is disabled, contact an admin...</div>--%>
                <%--</c:if>--%>
                <input name="email" id="email" type="text" class="form-control" placeholder="Email address" required autofocus>
                <input name="password" id="password" type="password" class="form-control" placeholder="Password" required>
                <!--<label class="checkbox">
                    <input type="checkbox" value="remember-me"> Remember me
                </label>
                -->
                <input type="submit" method="execute" value="Sign in" class="btn btn-lg btn-primary btn-block"  />
            </form>

        </div> <!-- /container -->
    </div>
</div>
