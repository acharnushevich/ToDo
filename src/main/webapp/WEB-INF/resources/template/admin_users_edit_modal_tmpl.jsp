<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.*" %>

<div class="modal fade" id="editModalUser" tabindex="-1" role="dialog" aria-labelledby="Edit profile"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content modal-form">
            <div class="modal-header">
                <h5 class="modal-title" id="editModalUserTitle">Edit profile</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="formEdit" method="post">
                <input type="hidden" class="form-control" id="editIdModal" name="<%=USER_ID%>">
                <input type="hidden" class="form-control" id="editProjectsId" name="<%=USER_PROJECTS_ID%>">

                <div class="form-row">
                    <div class="form-group  col-md-6">
                        <label for="editRoleModal">Role</label>
                        <select class="form-control" name="<%=USER_ROLE%>" id="editRoleModal">
                            <c:forEach var="item" items="${requestScope.roles}">
                                <option value="${item.name}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group  col-md-6">
                        <label for="editNameModal">Name</label>
                        <input type="text" class="form-control" id="editNameModal" placeholder="Name"
                               name="<%=USER_NAME%>">
                    </div>
                    <div class="form-group  col-md-6">
                        <label for="editSurnameModal">Surname</label>
                        <input type="text" class="form-control" id="editSurnameModal" placeholder="Surname"
                               name="<%=USER_SURNAME%>">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="editEmailModal">Email<span class="text-danger">*</span></label>
                        <input type="email" class="form-control" id="editEmailModal" placeholder="Email"
                               name="<%=USER_EMAIL%>" required>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="editPasswordModal">Password<span class="text-danger">*</span></label>
                        <input type="password" class="form-control" id="editPasswordModal" placeholder="Password"
                               name="<%=USER_PASSWORD%>" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group  col-md-12">
                        <label for="editProjectId">Projects</label>
                        <select data-placeholder="Select project" class="chosen-select" multiple="true"
                                id="editProjectId"
                                name="Project">
                            <c:forEach var="item" items="${requestScope.projects}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-12">
                        <label for="editDescriptionModal">Description</label>
                        <textarea class="form-control" aria-label="Description" id="editDescriptionModal"
                                  name="<%=USER_DESCRIPTION%>"></textarea>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-12">
                        <div class="custom-control custom-checkbox">
                            <input type="checkbox" class="custom-control-input" id="editProfileEnableModal"
                                   name="<%=USER_PROFILE_ENABLE%>">
                            <label class="custom-control-label" for="editProfileEnableModal">Activ</label>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" onclick="clickBtn('formEdit', 'edit')">Edit</button>
                    <button type="submit" class="btn btn-danger" onclick="clickBtn('formEdit', 'delete')">Delete
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>