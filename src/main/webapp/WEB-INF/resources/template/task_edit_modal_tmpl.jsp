<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.TASK_DESCRIPTION" %>
<%@ page import="static com.academy.util.ApplicationConstant.*" %>

<div class="modal fade" id="editModalTask" tabindex="-1" role="dialog" aria-labelledby="New Task"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content modal-form">
            <div class="modal-header">
                <h5 class="modal-title" id="editModalTaskTitle">Edit Task</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="formEditTask" enctype="multipart/form-data" method="post"
                  action="<c:url value='/tasks/${requestScope.task.id}'/>">
                <input type="hidden" class="form-control" id="editIdTaskModal" name="<%=TASK_ID%>"
                       value="${requestScope.task.id}">
                <input type="hidden" class="form-control" id="editStatusTaskModal" name="<%=TASK_STATUS%>"
                       value="${requestScope.task.taskStatus}">

                <div class="form-row">
                    <div class="form-group col-md">
                        <label for="editNameTaskModal">Name<span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="editNameTaskModal" placeholder="Name"
                               name="<%=TASK_NAME%>" required value="${requestScope.task.name}">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="dateId">Due Date<span class="text-danger">*</span></label>
                        <input type="date" class="form-control" id="dateId" name="<%=TASK_DATE%>" required
                               value="${requestScope.task.date}">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="priorityTaskModal">Priority<span class="text-danger">*</span></label>
                        <select class="form-control" id="priorityTaskModal" name="<%=TASK_PRIORITY%>" required>
                            <c:forEach var="item" items="${requestScope.priorities}">
                                <c:choose>
                                    <c:when test="${item.name == requestScope.task.priority}">
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

                <div class="form-row">
                    <div class="form-group col-md">
                        <input type="file" class="custom-file-input form-control" id="customFile" name="<%=FILE%>"
                               multiple>
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md">
                        <label for="descriptionModal">Description</label>
                        <textarea class="form-control" aria-label="Description" id="descriptionModal"
                                  name="<%=TASK_DESCRIPTION%>">${requestScope.task.description}</textarea>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Edit</button>
                </div>
            </form>
        </div>
    </div>
</div>