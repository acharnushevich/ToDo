<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.*" %>

<div class="modal fade" id="editModalWorkLog" tabindex="-1" role="dialog" aria-labelledby="Edit Work Log"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content modal-form">
            <div class="modal-header">
                <h5 class="modal-title" id="editModalWorkLogTitle">Edit Work Log</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="formEdit" method="post">
                <input type="hidden" class="form-control" id="editUserIdModal" name="<%=WORK_LOG_USER_ID%>">
                <input type="hidden" class="form-control" id="editIdModal" name="<%=WORK_LOG_ID%>">

                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="editDateModal">Date<span class="text-danger">*</span></label>
                        <input type="date" class="form-control" id="editDateModal" name="<%=WORK_LOG_DATE%>"
                               required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="editTimeModal">Time Spent (h)<span class="text-danger">*</span></label>
                        <input type="number" class="form-control" id="editTimeModal" name="<%=WORK_LOG_TIME%>"
                               required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group  col-md-12">
                        <label for="editDescriptionModal">Description</label>
                        <textarea class="form-control" aria-label="Description" id="editDescriptionModal"
                                  name="<%=WORK_LOG_DESCRIPTION%>"></textarea>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" onclick="clickBtn('formEdit', 'edit')">Edit</button>
                    <button type="submit" class="btn btn-danger" onclick="clickBtn('formEdit', 'delete')">Delete</button>
                </div>
            </form>
        </div>
    </div>
</div>