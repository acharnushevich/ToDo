<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.*" %>

<div class="modal fade" id="searchModalUser" tabindex="-1" role="dialog" aria-labelledby="Search profile"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content modal-form">
            <div class="modal-header">
                <h5 class="modal-title">Search profile</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="formSearch" method="get" action="<c:url value='/admin/users/search'/>">
                <div class="form-row">
                    <div class="form-group  col-md-6">
                        <label for="searchRoleModal">Role</label>
                        <select class="form-control" name="<%=USER_ROLE%>" id="searchRoleModal">
                            <option value=""></option>
                            <c:forEach var="item" items="${requestScope.roles}">
                                <option value="${item.name}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group  col-md-6">
                        <label for="searchNameModal">Name</label>
                        <input type="text" class="form-control" id="searchNameModal" placeholder="Name"
                               name="<%=USER_NAME%>">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group  col-md-6">
                        <label for="searchSurnameModal">Surname</label>
                        <input type="text" class="form-control" id="searchSurnameModal" placeholder="Surname"
                               name="<%=USER_SURNAME%>">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="searchEmailModal">Email</label>
                        <input type="email" class="form-control" id="searchEmailModal" placeholder="Email"
                               name="<%=USER_EMAIL%>">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-12">
                        <div class="custom-control custom-checkbox">
                            <input type="checkbox" class="custom-control-input" id="searchProfileEnableModal"
                                   name="<%=USER_PROFILE_ENABLE%>">
                            <label class="custom-control-label" for="searchProfileEnableModal">Activ</label>
                        </div>
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