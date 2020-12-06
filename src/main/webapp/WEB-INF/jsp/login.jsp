<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page import="static com.academy.util.ApplicationConstant.USER_EMAIL" %>
<%@ page import="static com.academy.util.ApplicationConstant.USER_PASSWORD" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Log in - ToDo</title>

    <link href="<c:url value='/resources/vendor/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/resources/css/login.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/resources/image/favicon.gif'/>" rel="icon" type="image/gif">
</head>

<body>
<div class="div-signin">
    <div class="container">
        <div class="row">
            <div class="col-sm-8">
                <h1 class="h2 mb-3 font-weight-normal">Welcome to ToDo</h1>
                <h1 class="h3 mb-3 font-weight-normal">The best tool for planning and tracking task is waiting for
                    you</h1>
            </div>
            <div class="col-sm-4">
                <form class="form-signin" method="post" action="<c:url value='/login'/>">
                    <jsp:include page="/WEB-INF/resources/template/error_tmpl.jsp"/>

                    <div id="error-email" class="alert alert-danger" role="alert" style="display: none">
                        <form:errors path="userDTO.email"/></div>
                    <div id="error-password" class="alert alert-danger" role="alert" style="display: none">
                        <form:errors path="userDTO.password"/></div>

                    <label for="inputEmail" class="sr-only">Email address</label>
                    <input type="email" id="inputEmail" name="<%=USER_EMAIL%>" class="form-control"
                           placeholder="Please enter email" autofocus required>
                    <label for="inputPassword" class="sr-only">Password</label>
                    <input type="password" id="inputPassword" name="<%=USER_PASSWORD%>" class="form-control"
                           placeholder="Please enter password" required>

                    <button type="submit" class="btn btn-primary" name="action">Log In</button>

                    <hr/>

                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#loginModalId">
                        Create New Account
                    </button>
                </form>

            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/resources/template/login_modal_tmpl.jsp"/>

<script src="<c:url value='/resources/js/jquery-3.5.1.min.js'/>"></script>
<script src="<c:url value='/resources/vendor/bootstrap/js/bootstrap.min.js'/>"></script>

<script>
    $(document).ready(function () {
        if (document.getElementById("email.errors") != null && document.getElementById("email.errors").innerText != "") {
            document.getElementById("error-email").style.display = "block";
        }
        if (document.getElementById("password.errors") != null && document.getElementById("password.errors").innerText != "") {
            document.getElementById("error-password").style.display = "block";
        }
    });
</script>
</body>
</html>