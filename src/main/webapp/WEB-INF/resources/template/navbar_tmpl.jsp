<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.ROLE_ADMIN" %>

<div class="container">
    <div class="form-group shadow p-3 mb-5 bg-white rounded">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="<c:url value='/tasks'/>">ToDo</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse"
                    data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="<c:url value='/tasks'/>">Home <span
                                class="sr-only">(current)</span></a>
                    </li>

                    <c:set var="roleAdmin" value="<%= ROLE_ADMIN%>"/>
                    <c:if test="${not empty sessionScope.user}">
                    <c:if test="${sessionScope.user.sessionUserRole == roleAdmin}">

                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                               data-toggle="dropdown"
                               aria-haspopup="true" aria-expanded="false">
                                Admin Panel
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="<c:url value='/admin/users'/>">Users</a>
                                <a class="dropdown-item" href="<c:url value='/admin/projects'/>">Projects</a>
                            </div>
                        </li>
                    </c:if>
                </ul>

                <ul class="navbar-nav">
                    <li class="nav-item">
                        <span class="badge badge-info">Hi, ${sessionScope.user.sessionUserName}</span>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/profile'/>">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/logOut'/>">Log Out</a>
                    </li>
                </ul>
                </c:if>
            </div>
        </nav>
    </div>
</div>
