<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.USER_NAME" %>
<%@ page import="static com.academy.util.ApplicationConstant.USER_SURNAME" %>
<%@ page import="static com.academy.util.ApplicationConstant.USER_EMAIL" %>
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

            <form id="formEdit" method="post" action="<c:url value='/profile'/>">
                <input type="hidden" class="form-control" id="formEditId" name="<%=USER_ID%>"
                       value="${sessionScope.user.sessionUserId}">
                <input type="hidden" class="form-control" id="formEditProjectsId" name="<%=USER_PROJECTS_ID%>"
                       value="${sessionScope.user.sessionUserProjectsId}">

                <div class="form-group">
                    <label for="editNameModal">Name</label>
                    <input type="text" class="form-control" id="editNameModal" placeholder="Name" name="<%=USER_NAME%>"
                           value="${sessionScope.user.sessionUserName}">
                </div>

                <div class="form-group">
                    <label for="editSurnameModal">Surname</label>
                    <input type="text" class="form-control" id="editSurnameModal" placeholder="Surname"
                           name="<%=USER_SURNAME%>"
                           value="${sessionScope.user.sessionUserSurname}">
                </div>

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="editEmailModal">Email<span class="text-danger">*</span></label>
                        <input type="email" class="form-control" id="editEmailModal" placeholder="Email"
                               name="<%=USER_EMAIL%>" value="${sessionScope.user.sessionUserEmail}" required>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="editPasswordModal">Password<span class="text-danger">*</span></label>
                        <input type="password" class="form-control" id="editPasswordModal" placeholder="Password"
                               name="<%=USER_PASSWORD%>" value="${sessionScope.user.sessionUserPassword}" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group  col-md-12">
                        <label for="formEditProjectId">Projects</label>
                        <select data-placeholder="Select project" class="chosen-select" multiple="true"
                                id="formEditProjectId"
                                name="Project" disabled>
                            <c:forEach var="item" items="${requestScope.projects}">
                                <c:set var="projectSelected" value="false"/>
                                <c:forTokens var="project" delims="," items="${sessionScope.user.sessionUserProjectsId}">
                                    <c:if test="${item.id == project}">
                                        <c:set var="projectSelected" value="true"/>
                                    </c:if>
                                </c:forTokens>

                                <c:choose>
                                    <c:when test="${projectSelected == 'true'}">
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
                    <div class="form-group  col-md-12">
                        <label for="editDescriptionModal">Description</label>
                        <textarea class="form-control" aria-label="Description" id="editDescriptionModal"
                                  name="<%=USER_DESCRIPTION%>">${sessionScope.user.sessionUserDescription}</textarea>
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