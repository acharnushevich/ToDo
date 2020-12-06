<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.USER_EMAIL" %>
<%@ page import="static com.academy.util.ApplicationConstant.USER_PASSWORD" %>
<%@ page import="static com.academy.util.ApplicationConstant.USER_NAME" %>
<%@ page import="static com.academy.util.ApplicationConstant.*" %>

<div class="modal fade" id="loginModalId" tabindex="-1" role="dialog" aria-labelledby="Sign Up"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content modal-form">
            <div class="modal-header">
                <h5 class="modal-title">Sign Up</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form method="post" action="<c:url value='/registration'/>">

                <div class="form-group">
                    <label for="inputNameModal">Name</label>
                    <input type="text" class="form-control" id="inputNameModal" placeholder="Name" name="<%=USER_NAME%>"
                           autofocus>
                </div>

                <div class="form-group">
                    <label for="inputSurnameModal">Surname</label>
                    <input type="text" class="form-control" id="inputSurnameModal" placeholder="Surname"
                           name="<%=USER_SURNAME%>">
                </div>

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="inputEmailModal">Email<span class="text-danger">*</span></label>
                        <input type="email" class="form-control" id="inputEmailModal" placeholder="Email"
                               name="<%=USER_EMAIL%>" required>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="inputPasswordModal">Password<span class="text-danger">*</span></label>
                        <input type="password" class="form-control" id="inputPasswordModal" placeholder="Password"
                               name="<%=USER_PASSWORD%>" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="inputDescriptionModal">Description</label>
                    <textarea class="form-control" aria-label="Description" id="inputDescriptionModal"
                              name="<%=USER_DESCRIPTION%>"></textarea>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" name="<%=ACTION%>" value="NewUser">Create New
                        Account
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>