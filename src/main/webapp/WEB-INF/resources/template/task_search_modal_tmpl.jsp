<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.*" %>

<div class="modal fade" id="searchModalTask" tabindex="-1" role="dialog" aria-labelledby="Search Task"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content modal-form">
            <div class="modal-header">
                <h5 class="modal-title" id="searchModalTaskTitle">Search Task</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="formSearchTask" method="get" action="<c:url value='/tasks/search'/>">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="searchFromDateId">From</label>
                        <input type="date" class="form-control" id="searchFromDateId" name="<%=FROM_DATE%>">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="searchToDateId">To</label>
                        <input type="date" class="form-control" id="searchToDateId" name="<%=TO_DATE%>">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md">
                        <label for="searchNameTaskModal">Name</label>
                        <input type="text" class="form-control" id="searchNameTaskModal" placeholder="Name"
                               name="<%=TASK_NAME%>">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="searchProjectId">Project</label>
                        <select class="form-control" id="searchProjectId" name="<%=TASK_PROJECT_ID%>">
                            <option value=""></option>
                            <c:forEach var="item" items="${requestScope.projects}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="searchPriorityTaskModal">Priority</label>
                        <select class="form-control" id="searchPriorityTaskModal" name="<%=TASK_PRIORITY%>">
                            <option value=""></option>
                            <c:forEach var="item" items="${requestScope.priorities}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="searchStatusTaskModal">Status</label>
                        <select class="form-control" id="searchStatusTaskModal" name="<%=TASK_STATUS%>">
                            <option value=""></option>
                            <c:forEach var="item" items="${requestScope.tasksStatus}">
                                <c:if test="${item.name != 'Create' && item.name != 'Edit'}">
                                    <option value="${item.id}">${item.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>
            </form>
        </div>
    </div>
</div>