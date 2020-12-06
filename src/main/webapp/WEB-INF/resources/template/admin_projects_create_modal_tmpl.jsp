<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.*" %>

<div class="modal fade" id="createModalProject" tabindex="-1" role="dialog" aria-labelledby="New project"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content modal-form">
            <div class="modal-header">
                <h5 class="modal-title">New project</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="formCreate" method="post" action="<c:url value='/admin/projects'/>">
                <div class="form-row">
                    <div class="form-group  col-md-6">
                        <label for="createNameModal">Name<span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="createNameModal" placeholder="Name"
                               name="<%=PROJECT_NAME%>" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-12">
                        <label for="createDescriptionModal">Description</label>
                        <textarea class="form-control" aria-label="Description" id="createDescriptionModal"
                                  name="<%=PROJECT_DESCRIPTION%>"></textarea>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Create New Project</button>
                </div>
            </form>
        </div>
    </div>
</div>