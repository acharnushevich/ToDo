<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="static com.academy.util.ApplicationConstant.ROLE_ADMIN" %>
<%@ page import="static com.academy.util.ApplicationConstant.USER_DESCRIPTION" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>

    <link href="<c:url value='/resources/vendor/chosen/css/chosen.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/vendor/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/image/favicon.gif'/>" rel="icon" type="image/gif">
</head>
<body>

<jsp:include page="/WEB-INF/resources/template/navbar_tmpl.jsp"/>

<jsp:include page="/WEB-INF/resources/template/profile_edit_modal_tmpl.jsp"/>

<script src="<c:url value='/resources/js/jquery-3.5.1.min.js'/>"></script>
<script src="<c:url value='/resources/vendor/bootstrap/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/resources/vendor/chosen/js/chosen.jquery.min.js'/>"></script>

<c:set var="roleAdmin" value="<%= ROLE_ADMIN%>"/>
<c:set var="roleClass" value="badge badge-success"/>
<c:if test="${sessionScope.user.sessionUserRole == roleAdmin}">
    <c:set var="roleClass" value="badge badge-danger"/>
</c:if>

<div class="container">
    <div class="form-row">
        <div class="col-4 shadow p-3 mb-5 bg-white rounded">
            <div class="form-row">
                <div class="col">
                    <b>Profile</b>
                </div>
                <div>
                    <img src="<c:url value='/resources/image/bootstrap-icons/pencil-square.svg'/>" alt=""
                         width="16" height="16" style="cursor: pointer"
                         title="Edit User" id="buttonEditModal" onclick="showModal('editModalUser')">
                </div>
            </div>

            <br>

            <jsp:include page="/WEB-INF/resources/template/error_tmpl.jsp"/>

            <div id="error-email" class="alert alert-danger" role="alert" style="display: none">
                <form:errors path="userDTO.email"/></div>
            <div id="error-password" class="alert alert-danger" role="alert" style="display: none">
                <form:errors path="userDTO.password"/></div>

            <div class="form-row">
                <label>Role: <span class="${roleClass}">${sessionScope.user.sessionUserRole}</span></label>
            </div>
            <div class="form-row">
                <label>Name: ${sessionScope.user.sessionUserName}</label>
            </div>
            <div class="form-row">
                <label>Surname: ${sessionScope.user.sessionUserSurname}</label>
            </div>
            <div class="form-row">
                <label>Email: ${sessionScope.user.sessionUserEmail}</label>
            </div>

            <div class="form-row">
                <div class="form-group  col-md-12">
                    <label for="projectId">Projects</label>
                    <select data-placeholder="Select project" class="chosen-select" multiple="true" id="projectId"
                            name="Project" disabled>
                        <c:forEach var="item" items="${requestScope.projects}">
                            <c:set var="projectSelected" value="false"/>
                            <c:forTokens var="project" delims="," items="${sessionScope.user.sessionUserProjectsId}">
                                <c:if test="${item.id == project}">
                                    <c:set var="projectSelected" value="true"/>
                                </c:if>
                            </c:forTokens>

                            <c:choose>
                                <c:when test="${projectSelected == 'true'}">
                                    <option value="${item.id}" selected>${item.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${item.id}">${item.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <script>
                $("#projectId").chosen({width: "100%"});
                $("#formEditProjectId").chosen({width: "100%"});
                $(".chosen-choices").addClass("form-control");
            </script>

            <div class="form-row">
                <div class="form-group col-md-12">
                    <label for="descriptionId">Description</label>
                    <textarea class="form-control" aria-label="Description" id="descriptionId" disabled
                              name="<%=USER_DESCRIPTION%>">${sessionScope.user.sessionUserDescription}</textarea>
                </div>
            </div>
        </div>

        <div class="col-7 shadow p-3 mb-5 bg-white rounded" style="margin-left: auto;">
            <div class="form-row">
                <div class="col">
                    <b>Activity</b>
                </div>
            </div>

            <br>

            <div class="form-row">
                <div class="col">
                    <table class="table table-hover table-sm">
                        <tbody>
                        <c:forEach var="item" items="${requestScope.activities}">
                            <c:choose>
                                <c:when test="${item.name == 'Open'}">
                                    <c:set var="nameClass" value="badge badge-info"/>
                                </c:when>
                                <c:when test="${item.name == 'Fixed'}">
                                    <c:set var="nameClass" value="badge badge-success"/>
                                </c:when>
                                <c:when test="${item.name == 'Closed'}">
                                    <c:set var="nameClass" value="badge badge-danger"/>
                                </c:when>
                                <c:when test="${item.name == 'Edit'}">
                                    <c:set var="nameClass" value="badge badge-warning"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="nameClass" value="badge badge-primary"/>
                                </c:otherwise>
                            </c:choose>

                            <tr>
                                <td>${item.date}</td>
                                <td>${item.taskName}</td>
                                <td><span class="${nameClass}">${item.name}</span></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function showModal(id) {
        $('#' + id).modal('show');
    }

    function clickBtn(modal, name) {
        var selectedValue = "";
        var prjctsId = modal + "ProjectsId";
        var prjctFormId = modal + "ProjectId";
        var select = document.getElementById(prjctFormId).getElementsByTagName('option');
        for (var j = 0; j < select.length; j++) {
            if (select[j].selected) {
                selectedValue += select[j].value + ",";
            }
        }
        document.getElementById(prjctsId).value = selectedValue;
    }

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
