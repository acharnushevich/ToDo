<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.*" %>

<div class="modal fade" id="createModalWorkLog" tabindex="-1" role="dialog" aria-labelledby="New Work Log"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content modal-form">
            <div class="modal-header">
                <h5 class="modal-title" id="createModalWorkLogTitle">New Work Log</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="formCreate" method="post" action="<c:url value='/tasks/${requestScope.task.id}/add-worklog'/>">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="createDateModal">Date<span class="text-danger">*</span></label>
                        <input type="date" class="form-control" id="createDateModal" name="<%=WORK_LOG_DATE%>"
                               required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="createTimeModal">Time Spent (h)<span class="text-danger">*</span></label>
                        <input type="number" class="form-control" id="createTimeModal" name="<%=WORK_LOG_TIME%>"
                               min="0" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group  col-md-12">
                        <label for="createDescriptionModal">Description</label>
                        <textarea class="form-control" aria-label="Description" id="createDescriptionModal"
                                  name="<%=WORK_LOG_DESCRIPTION%>"></textarea>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Create New Work Log</button>
                </div>
            </form>
        </div>
    </div>
</div>