<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="static com.academy.util.ApplicationConstant.ROLE_ADMIN" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Panel</title>

    <link href="<c:url value='/resources/vendor/chosen/css/chosen.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/vendor/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/image/favicon.gif'/>" rel="icon" type="image/gif">
</head>
<body>

<jsp:include page="/WEB-INF/resources/template/navbar_tmpl.jsp"/>

<jsp:include page="/WEB-INF/resources/template/admin_users_create_modal_tmpl.jsp"/>
<jsp:include page="/WEB-INF/resources/template/admin_users_edit_modal_tmpl.jsp"/>
<jsp:include page="/WEB-INF/resources/template/admin_users_search_modal_tmpl.jsp"/>

<div class="container shadow p-3 mb-5 bg-white rounded">

    <div class="form-row">
        <div class="col">
            <b>Users</b>
        </div>
        <div>
            <img src="<c:url value='/resources/image/bootstrap-icons/person-plus.svg'/>" alt="" width="16"
                 height="16" style="cursor: pointer"
                 title="Create New User" onclick="showModal('createModalUser')">
            <img src="<c:url value='/resources/image/bootstrap-icons/pencil-square.svg'/>" alt="" width="16"
                 height="16" style="cursor: pointer"
                 title="Edit User" id="buttonEditModal" onclick="showModal('editModalUser')">
            <img src="<c:url value='/resources/image/bootstrap-icons/search.svg'/>" alt="" width="16"
                 height="16" style="cursor: pointer" title="Search User"
                 onclick="showModal('searchModalUser')">
            <c:if test="${requestScope.searchFilter == 'true'}">
                <a href="<c:url value='/admin/users'/>"><img
                        src="<c:url value='/resources/image/bootstrap-icons/x-square.svg'/>" alt="" width="16"
                        height="16" style="cursor: pointer" title="Cancel Filter"></a>
            </c:if>
        </div>
    </div>

    <br>

    <div class="form-row">
        <div class="col">
            <jsp:include page="/WEB-INF/resources/template/error_tmpl.jsp"/>

            <div id="error-email" class="alert alert-danger" role="alert" style="display: none">
                <form:errors path="userDTO.email"/></div>
            <div id="error-password" class="alert alert-danger" role="alert" style="display: none">
                <form:errors path="userDTO.password"/></div>

            <table class="table table-hover table-sm">
                <thead>
                <tr>
                    <th scope="col">№</th>
                    <th scope="col">Role</th>
                    <th scope="col">Name</th>
                    <th scope="col">Email</th>
                    <th scope="col">Activ</th>
                </tr>
                </thead>
                <tbody>

                <c:set var="num" value="0"/>
                <c:set var="roleAdmin" value="<%= ROLE_ADMIN%>"/>
                <c:forEach var="item" items="${requestScope.users}">
                    <c:set var="num" value="${num + 1}"/>
                    <c:set var="roleClass" value="badge badge-success"/>
                    <c:if test="${item.role == roleAdmin}">
                        <c:set var="roleClass" value="badge badge-danger"/>
                    </c:if>
                    <c:set var="profileEnableClass" value="badge badge-success"/>
                    <c:if test="${item.profileEnable == 'false'}">
                        <c:set var="profileEnableClass" value="badge badge-danger"/>
                    </c:if>

                    <tr onclick="setActiv(this,'${item.id}','${item.email}','${item.password}','${item.name}','${item.surname}','${item.description}','${item.projectsId}','${item.role}','${item.profileEnable}')"
                        ondblclick="setDblActiv(this,'${item.id}','${item.email}','${item.password}','${item.name}','${item.surname}','${item.description}','${item.projectsId}','${item.role}','${item.profileEnable}')">
                        <th scope="row">${num}</th>
                        <td><span class="${roleClass}">${item.role}</span></td>
                        <td>${item.name}</td>
                        <td>${item.email}</td>
                        <td><span class="${profileEnableClass}">${item.profileEnable}</span></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="<c:url value='/resources/js/jquery-3.5.1.min.js'/>"></script>
<script src="<c:url value='/resources/vendor/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/resources/vendor/chosen/js/chosen.jquery.min.js'/>"></script>

<script>
    function setActiv(obj, id, email, password, name, surname, description, projects, role, profileEnable) {
        if (obj.classList.contains("table-active")) {
            $('table tr').removeClass('table-active');

            document.getElementById('editIdModal').value = "";
            document.getElementById('editEmailModal').value = "";
            document.getElementById('editPasswordModal').value = "";
            document.getElementById('editNameModal').value = "";
            document.getElementById('editSurnameModal').value = "";
            document.getElementById('editDescriptionModal').value = "";
            document.getElementById('editRoleModal').value = "";
            document.getElementById('editProfileEnableModal').checked = "";

            document.getElementById('editProjectId').value = "";
            $("#editProjectId").trigger("chosen:updated");
        } else {
            $('table tr').removeClass('table-active');
            $(obj).addClass('table-active');

            document.getElementById('editIdModal').value = id;
            document.getElementById('editEmailModal').value = email;
            document.getElementById('editPasswordModal').value = password;
            document.getElementById('editNameModal').value = name;
            document.getElementById('editSurnameModal').value = surname;
            document.getElementById('editDescriptionModal').value = description;
            document.getElementById('editRoleModal').value = role;

            if (profileEnable == "true")
                document.getElementById('editProfileEnableModal').checked = "checked";

            var pItem = projects.split(",");
            var select = document.getElementById('editProjectId').getElementsByTagName('option');
            for (var i = 0; i < pItem.length; i++) {
                for (var j = 0; j < select.length; j++) {
                    if (select[j].value === pItem[i]) select[j].selected = true;
                }
            }
            $("#editProjectId").trigger("chosen:updated");
        }
    }

    function setDblActiv(obj, id, email, password, name, surname, description, projects, role, profileEnable) {
        $('table tr').removeClass('table-active');
        $(obj).addClass('table-active');

        document.getElementById('editIdModal').value = id;
        document.getElementById('editEmailModal').value = email;
        document.getElementById('editPasswordModal').value = password;
        document.getElementById('editNameModal').value = name;
        document.getElementById('editSurnameModal').value = surname;
        document.getElementById('editDescriptionModal').value = description;
        document.getElementById('editRoleModal').value = role;

        if (profileEnable == "true")
            document.getElementById('editProfileEnableModal').checked = "checked";

        var pItem = projects.split(",");
        var select = document.getElementById('editProjectId').getElementsByTagName('option');
        for (var i = 0; i < pItem.length; i++) {
            for (var j = 0; j < select.length; j++) {
                if (select[j].value === pItem[i]) select[j].selected = true;
            }
        }
        $("#editProjectId").trigger("chosen:updated");

        $('#editModalUser').modal('show');
    }

    function showModal(id) {
        if (id == "editModalUser" && document.getElementById('editIdModal').value != "" || id != "editModalUser") {
            $('#' + id).modal('show');
        } else {
            alertInfo("Select User");
        }
    }

    function clickBtn(modal, name) {
        if (name == "edit") {
            document.getElementById(modal).action = "<c:url value='/admin/users'/>" + "/" + document.getElementById(name + "IdModal").value;
        } else if (name == "delete") {
            name = "edit";
            document.getElementById(modal).action = "<c:url value='/admin/users'/>" + "/" + document.getElementById(name + "IdModal").value + "/delete";
        }

        var selectedValue = "";
        var projectsId = name + "ProjectsId";
        var projectFormId = name + "ProjectId";
        var select = document.getElementById(projectFormId).getElementsByTagName('option');
        for (var j = 0; j < select.length; j++) {
            if (select[j].selected) {
                selectedValue += select[j].value + ",";
            }
        }
        document.getElementById(projectsId).value = selectedValue;
    }

    $(document).ready(function () {
        if (document.getElementById("email.errors") != null && document.getElementById("email.errors").innerText != "") {
            document.getElementById("error-email").style.display = "block";
        }
        if (document.getElementById("password.errors") != null && document.getElementById("password.errors").innerText != "") {
            document.getElementById("error-password").style.display = "block";
        }

        $("#createProjectId").chosen({width: "100%"});
        $("#editProjectId").chosen({width: "100%"});
        $(".chosen-choices").addClass("form-control");
    });
</script>
</body>
</html>
