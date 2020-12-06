<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.*" %>

<div class="modal fade" id="createModalUser" tabindex="-1" role="dialog" aria-labelledby="New profile"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content modal-form">
            <div class="modal-header">
                <h5 class="modal-title">New profile</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="formCreate" method="post" action="<c:url value='/admin/users'/>">
                <input type="hidden" class="form-control" id="createProjectsId" name="<%=USER_PROJECTS_ID%>">

                <div class="form-row">
                    <div class="form-group  col-md-6">
                        <label for="createRoleModal">Role<span class="text-danger">*</span></label>
                        <select class="form-control" name="<%=USER_ROLE%>" id="createRoleModal" required>
                            <c:forEach var="item" items="${requestScope.roles}">
                                <option value="${item.name}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group  col-md-6">
                        <label for="createNameModal">Name</label>
                        <input type="text" class="form-control" id="createNameModal" placeholder="Name"
                               name="<%=USER_NAME%>">
                    </div>
                    <div class="form-group  col-md-6">
                        <label for="createSurnameModal">Surname</label>
                        <input type="text" class="form-control" id="createSurnameModal" placeholder="Surname"
                               name="<%=USER_SURNAME%>">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="createEmailModal">Email<span class="text-danger">*</span></label>
                        <input type="email" class="form-control" id="createEmailModal" placeholder="Email"
                               name="<%=USER_EMAIL%>" required>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="createPasswordModal">Password<span class="text-danger">*</span></label>
                        <input type="password" class="form-control" id="createPasswordModal" placeholder="Password"
                               name="<%=USER_PASSWORD%>" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group  col-md-12">
                        <label for="createProjectId">Projects</label>
                        <select data-placeholder="Select project" class="chosen-select" multiple="true"
                                id="createProjectId"
                                name="Project">
                            <c:forEach var="item" items="${requestScope.projects}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-12">
                        <label for="createDescriptionModal">Description</label>
                        <textarea class="form-control" aria-label="Description" id="createDescriptionModal"
                                  name="<%=USER_DESCRIPTION%>"></textarea>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" onclick="clickBtn('formCreate', 'create')">
                        Create New Account
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>