<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.*" %>

<div class="modal fade" id="editModalProject" tabindex="-1" role="dialog" aria-labelledby="Edit project"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content modal-form">
            <div class="modal-header">
                <h5 class="modal-title" id="editModalProjectTitle">Edit project</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="formEdit" method="post">
                <input type="hidden" class="form-control" id="formEditActionId" name="<%=ACTION%>">
                <input type="hidden" class="form-control" id="editIdModal" name="<%=PROJECT_ID%>">

                <div class="form-row">
                    <div class="form-group  col-md-6">
                        <label for="editNameModal">Name<span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="editNameModal" placeholder="Name"
                               name="<%=PROJECT_NAME%>" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-12">
                        <label for="editDescriptionModal">Description</label>
                        <textarea class="form-control" aria-label="Description" id="editDescriptionModal"
                                  name="<%=PROJECT_DESCRIPTION%>"></textarea>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" onclick="clickBtn('formEdit', 'edit')">
                        Edit
                    </button>
                    <button type="submit" class="btn btn-danger" onclick="clickBtn('formEdit', 'delete')">
                        Delete
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>