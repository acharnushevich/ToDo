<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.TASK_DESCRIPTION" %>
<%@ page import="static com.academy.util.ApplicationConstant.*" %>

<div class="modal fade" id="newModalTask" tabindex="-1" role="dialog" aria-labelledby="New Task"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content modal-form">
            <div class="modal-header">
                <h5 class="modal-title" id="newModalTaskTitle">New Task</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="formNewTask" enctype="multipart/form-data" method="post" action="<c:url value='/tasks'/>">
                <div class="form-row">
                    <div class="form-group col-md">
                        <label for="newNameTaskModal">Name<span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="newNameTaskModal" placeholder="Name"
                               name="<%=TASK_NAME%>" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="projectId">Project<span class="text-danger">*</span></label>
                        <select class="form-control" id="projectId" name="<%=TASK_PROJECT_ID%>" required>
                            <c:forEach var="item" items="${requestScope.projects}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="dateId">Due Date<span class="text-danger">*</span></label>
                        <input type="date" class="form-control" id="dateId" name="<%=TASK_DATE%>" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="priorityTaskModal">Priority<span class="text-danger">*</span></label>
                        <select class="form-control" id="priorityTaskModal" name="<%=TASK_PRIORITY%>" required>
                            <c:forEach var="item" items="${requestScope.priorities}">
                                <option value="${item.id}">${item.name}</option>
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
                                  name="<%=TASK_DESCRIPTION%>"></textarea>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Create</button>
                </div>
            </form>
        </div>
    </div>
</div>