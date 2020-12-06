<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome to ToDo</title>

    <link href="<c:url value='/resources/vendor/chosen/css/chosen.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/vendor/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/image/favicon.gif'/>" rel="icon" type="image/gif">
</head>
<body>

<jsp:include page="/WEB-INF/resources/template/navbar_tmpl.jsp"/>

<jsp:include page="/WEB-INF/resources/template/task_create_modal_tmpl.jsp"/>
<jsp:include page="/WEB-INF/resources/template/task_search_modal_tmpl.jsp"/>

<div class="container shadow p-3 mb-5 bg-white rounded">
    <div class="form-row">
        <div class="col">
            <b>Tasks</b>
        </div>
        <div>
            <img src="<c:url value='/resources/image/bootstrap-icons/journal-plus.svg'/>" alt="" width="16"
                 height="16" style="cursor: pointer"
                 title="Create New Task" onclick="showModal('newModalTask')">
            <img src="<c:url value='/resources/image/bootstrap-icons/journal-text.svg'/>" alt="" width="16"
                 height="16" style="cursor: pointer"
                 title="Open Task" id="buttonOpenModal" onclick="clickBtnOpenTask()">
            <img src="<c:url value='/resources/image/bootstrap-icons/search.svg'/>" alt="" width="16"
                 height="16" style="cursor: pointer" title="Search Task"
                 onclick="showModal('searchModalTask')">
            <c:if test="${requestScope.searchFilter == 'true'}">
                <a href="<c:url value='/tasks'/>"><img
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
                <form:errors path="taskDTO.name"/></div>
            <div id="error-date" class="alert alert-danger" role="alert" style="display: none">
                <form:errors path="taskDTO.date"/></div>
            <div id="error-projectId" class="alert alert-danger" role="alert" style="display: none">
                <form:errors path="taskDTO.projectId"/></div>

            <table id="tableTaskId" class="table table-hover table-sm">
                <thead>
                <tr>
                    <th scope="col">№</th>
                    <th scope="col">Date</th>
                    <th scope="col">Name</th>
                    <th scope="col">Project</th>
                    <th scope="col">Priority</th>
                    <th scope="col">Status</th>
                </tr>
                </thead>
                <tbody>

                <c:set var="num" value="0"/>
                <c:forEach var="item" items="${requestScope.tasks}">
                    <c:choose>
                        <c:when test="${item.priority == 'Low'}">
                            <c:set var="priorityClass" value="badge badge-light"/>
                        </c:when>
                        <c:when test="${item.priority == 'Medium'}">
                            <c:set var="priorityClass" value="badge badge-info"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="priorityClass" value="badge badge-warning"/>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${item.taskStatus == 'Fixed'}">
                            <c:set var="taskStatusClass" value="badge badge-success"/>
                        </c:when>
                        <c:when test="${item.taskStatus == 'Closed'}">
                            <c:set var="taskStatusClass" value="badge badge-danger"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="taskStatusClass" value="badge badge-primary"/>
                        </c:otherwise>
                    </c:choose>

                    <c:set var="num" value="${num + 1}"/>
                    <tr onclick="setActiv(this, '${item.id}')"
                        ondblclick="setDblActiv(this, '${item.id}')">
                        <th scope="row">${num}</th>
                        <td>${item.date}</td>
                        <td>${item.name}</td>
                        <td>${item.projectName}</td>
                        <td><span class="${priorityClass}">${item.priority}</span></td>
                        <td><span class="${taskStatusClass}">${item.taskStatus}</span></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<form id="formOpen" method="get">
</form>

<script src="<c:url value='/resources/js/jquery-3.5.1.min.js'/>"></script>
<script src="<c:url value='/resources/vendor/bootstrap/js/bootstrap.min.js'/>"></script>

<script>
    function setActiv(obj, id) {
        if (obj.classList.contains("table-active")) {
            $('table tr').removeClass('table-active');

            document.getElementById("formOpen").action = "<c:url value='/tasks'/>";
        } else {
            $('table tr').removeClass('table-active');
            $(obj).addClass('table-active');

            document.getElementById("formOpen").action = "<c:url value='/tasks'/>" + "/" + id;
        }
    }

    function setDblActiv(obj, id) {
        $('table tr').removeClass('table-active');
        $(obj).addClass('table-active');

        document.getElementById("formOpen").action = "<c:url value='/tasks'/>" + "/" + id;
        document.getElementById("formOpen").submit();
    }

    function showModal(id) {
        $('#' + id).modal('show');
    }

    function clickBtnOpenTask() {
        var active = document.getElementById('tableTaskId').querySelector('.table-active');
        if (active != null) {
            document.getElementById("formOpen").submit();
        } else {
            alertInfo("Select Open Task");
        }
    }

    $(document).ready(function () {
        if (document.getElementById("name.errors") != null && document.getElementById("name.errors").innerText != "") {
            document.getElementById("error-name").style.display = "block";
        }
        if (document.getElementById("date.errors") != null && document.getElementById("date.errors").innerText != "") {
            document.getElementById("error-date").style.display = "block";
        }
        if (document.getElementById("projectId.errors") != null && document.getElementById("projectId.errors").innerText != "") {
            document.getElementById("error-projectId").style.display = "block";
        }

        $('input[type="file"]').on("change", function () {
            let filenames = [];
            let files = document.getElementById("customFile").files;
            if (files.length > 1) {
                filenames.push("Total Files (" + files.length + ")");
            } else {
                for (let i in files) {
                    if (files.hasOwnProperty(i)) {
                        filenames.push(files[i].name);
                    }
                }
            }
            $(this).next(".custom-file-label").html(filenames.join(","));
        });
    });
</script>
</body>
</html>
