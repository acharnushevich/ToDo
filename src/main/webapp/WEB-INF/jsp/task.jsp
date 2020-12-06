<%@ page import="static com.academy.util.ApplicationConstant.TASK_DESCRIPTION" %>
<%@ page import="static com.academy.util.ApplicationConstant.*" %>
<%@ page import="com.academy.persistence.entity.enums.TaskStatus" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Task</title>

    <link href="<c:url value='/resources/vendor/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/image/favicon.gif'/>" rel="icon" type="image/gif">
</head>
<body>

<jsp:include page="/WEB-INF/resources/template/navbar_tmpl.jsp"/>

<jsp:include page="/WEB-INF/resources/template/task_edit_modal_tmpl.jsp"/>
<jsp:include page="/WEB-INF/resources/template/work_log_create_modal_tmpl.jsp"/>
<jsp:include page="/WEB-INF/resources/template/work_log_edit_modal_tmpl.jsp"/>

<div class="container">
    <div class="form-row">
        <div class="col-8 shadow p-3 mb-5 bg-white rounded">
            <div class="form-row">
                <div class="col">
                    <b>Task</b>
                </div>
                <div>
                    <c:set var="taskStatusOpen" value="<%=TaskStatus.Open%>"/>
                    <c:choose>
                        <c:when test="${requestScope.task.taskStatus == taskStatusOpen}">
                            <img src="<c:url value='/resources/image/bootstrap-icons/journal-check.svg'/>" alt=""
                                 width="16" height="16" style="cursor: pointer"
                                 title="Resolve Task" id="buttonResolveTaskModal"
                                 onclick="clickBtnStatus('<%=TaskStatus.Fixed%>')">
                            <img src="<c:url value='/resources/image/bootstrap-icons/journal-x.svg'/>" alt=""
                                 width="16" height="16" style="cursor: pointer"
                                 title="Close Task" id="buttonCloseTaskModal"
                                 onclick="clickBtnStatus('<%=TaskStatus.Closed%>')">
                            <img src="<c:url value='/resources/image/bootstrap-icons/pencil-square.svg'/>" alt=""
                                 width="16" height="16" style="cursor: pointer" title="Edit Task"
                                 onclick="showModal('editModalTask')">
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value='/resources/image/bootstrap-icons/journal-arrow-up.svg'/>" alt=""
                                 width="16" height="16" style="cursor: pointer"
                                 title="Rostore Task" id="buttonRostoreTaskModal"
                                 onclick="clickBtnStatus('<%=TaskStatus.Open%>')">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <br>

            <div class="form-row">
                <div class="col">
                    <div id="error-name" class="alert alert-danger" role="alert" style="display: none">
                        <form:errors path="taskDTO.name"/></div>
                    <div id="error-date" class="alert alert-danger" role="alert" style="display: none">
                        <form:errors path="taskDTO.date"/><form:errors path="workLogDTO.date"/></div>
                    <div id="error-projectId" class="alert alert-danger" role="alert" style="display: none">
                        <form:errors path="taskDTO.projectId"/></div>
                    <div id="error-time" class="alert alert-danger" role="alert" style="display: none">
                        <form:errors path="workLogDTO.time"/></div>

                    <label>${requestScope.task.name}</label>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-12">
                    <textarea class="form-control" aria-label="Description" id="descriptionId" disabled
                              name="<%=TASK_DESCRIPTION%>">${requestScope.task.description}</textarea>
                </div>
            </div>

            <br/>
            <c:forEach var="item" items="${requestScope.attachments}">
                <div class="form-row">
                    <div class="col">
                            ${item.fileName}
                        <img src="<c:url value='/resources/image/bootstrap-icons/journal-arrow-down.svg'/>" alt=""
                             width="16" height="16" style="cursor: pointer"
                             title="Download" onclick="clickBtnFile('DownloadFile', ${item.id})">
                        <c:if test="${requestScope.task.taskStatus == taskStatusOpen}">
                            <img src="<c:url value='/resources/image/bootstrap-icons/x.svg'/>" alt=""
                                 width="16" height="16" style="cursor: pointer"
                                 title="Delete" onclick="clickBtnFile('DeleteFile', ${item.id})">
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="col-3 shadow p-3 mb-5 bg-white rounded" style="margin-left: auto;">
            <div class="form-row">
                <label>Reporter: ${requestScope.task.userName}</label>
            </div>

            <div class="form-row">
                <label>Due Date: ${requestScope.task.date}</label>
            </div>

            <div class="form-row">
                <label>Time Tracking: <span id="timeTrackingId"></span>h</label>
            </div>

        </div>
    </div>

    <div class="form-row">
        <div class="col shadow p-3 mb-5 bg-white rounded">
            <div class="form-row">
                <div class="col">
                    <b>Work Log</b>
                </div>
                <div>
                    <c:if test="${requestScope.task.taskStatus == taskStatusOpen}">
                        <img src="<c:url value='/resources/image/bootstrap-icons/journal-plus.svg'/>" alt="" width="16"
                             height="16" style="cursor: pointer" title="New Work Log"
                             onclick="showModal('createModalWorkLog')">
                        <img src="<c:url value='/resources/image/bootstrap-icons/pencil-square.svg'/>" alt=""
                             width="16" height="16" style="cursor: pointer" title="Edit Work Log"
                             onclick="showModal('editModalWorkLog')">
                    </c:if>
                </div>
            </div>

            <br>

            <jsp:include page="/WEB-INF/resources/template/error_tmpl.jsp"/>

            <div class="form-row">
                <div class="col">
                    <table class="table table-hover table-sm">
                        <tbody>
                        <c:set var="timeTracking" value="${0}"/>
                        <c:forEach var="item" items="${requestScope.workLogs}">
                            <c:set var="timeTracking" value="${timeTracking + item.time}"/>
                            <tr onclick="setActiv(this, '${item.userId}', '${item.id}', '${item.time}',
                                    '${item.date}', '${item.description}')"
                                ondblclick="setDblActiv(this, '${item.userId}', '${item.id}', '${item.time}',
                                        '${item.date}', '${item.description}')">
                                <td>
                                    <div class="form-row">
                                        <div class="col">
                                            <label>${item.date} - ${item.userSurname} ${item.userName}</label>
                                        </div>
                                        <div class="col-2">
                                            <label>Time Spent: ${item.time}h</label>
                                        </div>
                                    </div>

                                    <div class="form-row">
                                        <div class="col">
                                            <label>${item.description}</label>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>

                        <script>
                            document.querySelector('#timeTrackingId').innerHTML = ${timeTracking};
                        </script>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<form id="formDownload" method="post" action="<c:url value='/tasks/${requestScope.task.id}/download-file'/>">
    <input type="hidden" class="form-control" id="formDownloadAttachmentId" name="<%=TASK_ATTACHMENT_ID%>">
</form>

<form id="formDelete" method="post" action="<c:url value='/tasks/${requestScope.task.id}/delete-file'/>">
    <input type="hidden" class="form-control" id="formDeleteAttachmentId" name="<%=TASK_ATTACHMENT_ID%>">
</form>

<script src="<c:url value='/resources/js/jquery-3.5.1.min.js'/>"></script>
<script src="<c:url value='/resources/vendor/bootstrap/js/bootstrap.min.js'/>"></script>

<script>
    function setActiv(obj, userId, id, time, date, description) {
        if (obj.classList.contains("table-active")) {
            $('table tr').removeClass('table-active');

            document.getElementById('editUserIdModal').value = "";
            document.getElementById('editIdModal').value = "";
            document.getElementById('editTimeModal').value = "";
            document.getElementById('editDateModal').value = "";
            document.getElementById('editDescriptionModal').value = "";
        } else {
            $('table tr').removeClass('table-active');
            $(obj).addClass('table-active');

            document.getElementById('editUserIdModal').value = userId;
            document.getElementById('editIdModal').value = id;
            document.getElementById('editTimeModal').value = time;
            document.getElementById('editDateModal').value = date;
            document.getElementById('editDescriptionModal').value = description;
        }
    }

    function setDblActiv(obj, userId, id, time, date, description) {
        $('table tr').removeClass('table-active');
        $(obj).addClass('table-active');

        document.getElementById('editUserIdModal').value = userId;
        document.getElementById('editIdModal').value = id;
        document.getElementById('editTimeModal').value = time;
        document.getElementById('editDateModal').value = date;
        document.getElementById('editDescriptionModal').value = description;

        if (${requestScope.task.taskStatus == taskStatusOpen}) {
            if (document.getElementById('editUserIdModal').value == "${sessionScope.user.sessionUserId}") {
                $('#editModalWorkLog').modal('show');
            } else {
                alertInfo("Select your work log");
            }
        }
    }

    function showModal(id) {
        if (id == "editModalWorkLog" && document.getElementById('editIdModal').value != "" && document.getElementById('editUserIdModal').value == "${sessionScope.user.sessionUserId}" || id != "editModalWorkLog") {
            $('#' + id).modal('show');
        } else {
            alertInfo("Select your work log");
        }
    }

    function clickBtnStatus(tasksStatus) {
        document.getElementById("editStatusTaskModal").value = tasksStatus;
        document.getElementById("formEditTask").submit();
    }

    function clickBtnFile(action, id) {
        if (action == "DownloadFile") {
            document.getElementById('formDownloadAttachmentId').value = id;
            document.getElementById("formDownload").submit();
        } else {
            document.getElementById('formDeleteAttachmentId').value = id;
            document.getElementById("formDelete").submit();
        }
    }

    function clickBtn(modal, name) {
        if (name == "edit") {
            document.getElementById(modal).action = "<c:url value='/tasks/${requestScope.task.id}/edit-worklog'/>";
        } else if (name == "delete") {
            document.getElementById(modal).action = "<c:url value='/tasks/${requestScope.task.id}/delete-worklog'/>";
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
        if (document.getElementById("time.errors") != null && document.getElementById("time.errors").innerText != "") {
            document.getElementById("error-time").style.display = "block";
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
