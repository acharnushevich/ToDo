<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<jsp:include page="/WEB-INF/resources/template/admin_projects_create_modal_tmpl.jsp"/>
<jsp:include page="/WEB-INF/resources/template/admin_projects_edit_modal_tmpl.jsp"/>
<jsp:include page="/WEB-INF/resources/template/admin_projects_search_modal_tmpl.jsp"/>

<div class="container shadow p-3 mb-5 bg-white rounded">

    <div class="form-row">
        <div class="col">
            <b>Projects</b>
        </div>
        <div>
            <img src="<c:url value='/resources/image/bootstrap-icons/person-plus.svg'/>" alt="" width="16"
                 height="16" style="cursor: pointer"
                 title="Create New Project" onclick="showModal('createModalProject')">
            <img src="<c:url value='/resources/image/bootstrap-icons/pencil-square.svg'/>" alt="" width="16"
                 height="16" style="cursor: pointer"
                 title="Edit Project" id="buttonEditModal" onclick="showModal('editModalProject')">
            <img src="<c:url value='/resources/image/bootstrap-icons/search.svg'/>" alt="" width="16"
                 height="16" style="cursor: pointer" title="Search Project"
                 onclick="showModal('searchModalProject')">
            <c:if test="${requestScope.searchFilter == 'true'}">
                <a href="<c:url value='/admin/projects'/>"><img
                        src="<c:url value='/resources/image/bootstrap-icons/x-square.svg'/>" alt="" width="16"
                        height="16" style="cursor: pointer" title="Cancel Filter"></a>
            </c:if>
        </div>
    </div>

    <br>

    <div class="form-row">
        <div class="col">
            <jsp:include page="/WEB-INF/resources/template/error_tmpl.jsp"/>

            <div id="error-name" class="alert alert-danger" role="alert" style="display: none">
                <form:errors path="projectDTO.name"/></div>

            <table class="table table-hover table-sm">
                <thead>
                <tr>
                    <th scope="col">№</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                </tr>
                </thead>
                <tbody>

                <c:set var="num" value="0"/>
                <c:forEach var="item" items="${requestScope.projects}">
                    <c:set var="num" value="${num + 1}"/>
                    <tr onclick="setActiv(this,'${item.id}','${item.name}','${item.description}')"
                        ondblclick="setDblActiv(this,'${item.id}','${item.name}','${item.description}')">
                        <th scope="row">${num}</th>
                        <td>${item.name}</td>
                        <td>${item.description}</td>
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
    function setActiv(obj, id, name, description) {
        if (obj.classList.contains("table-active")) {
            $('table tr').removeClass('table-active');

            document.getElementById('editIdModal').value = "";
            document.getElementById('editNameModal').value = "";
            document.getElementById('editDescriptionModal').value = "";
        } else {
            $('table tr').removeClass('table-active');
            $(obj).addClass('table-active');

            document.getElementById('editIdModal').value = id;
            document.getElementById('editNameModal').value = name;
            document.getElementById('editDescriptionModal').value = description;
        }
    }

    function setDblActiv(obj, id, name, description) {
        $('table tr').removeClass('table-active');
        $(obj).addClass('table-active');

        document.getElementById('editIdModal').value = id;
        document.getElementById('editNameModal').value = name;
        document.getElementById('editDescriptionModal').value = description;

        $('#editModalProject').modal('show');
    }

    function showModal(id) {
        if (id == "editModalProject" && document.getElementById('editIdModal').value != "" || id != "editModalProject") {
            $('#' + id).modal('show');
        } else {
            alertInfo("Select Project");
        }
    }

    function clickBtn(modal, name) {
        if (name == "edit") {
            document.getElementById(modal).action = "<c:url value='/admin/projects'/>" + "/" + document.getElementById(name + "IdModal").value;
        } else if (name == "delete") {
            name = "edit";
            document.getElementById(modal).action = "<c:url value='/admin/projects'/>" + "/" + document.getElementById(name + "IdModal").value + "/delete";
        }
    }

    $(document).ready(function () {
        if (document.getElementById("name.errors") != null && document.getElementById("name.errors").innerText != "") {
            document.getElementById("error-name").style.display = "block";
        }
    });
</script>
</body>
</html>
