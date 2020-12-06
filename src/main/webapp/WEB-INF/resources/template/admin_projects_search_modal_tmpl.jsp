<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.academy.util.ApplicationConstant.*" %>

<div class="modal fade" id="searchModalProject" tabindex="-1" role="dialog" aria-labelledby="Search project"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content modal-form">
            <div class="modal-header">
                <h5 class="modal-title">Search project</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="formSearch" method="get" action="<c:url value='/admin/projects/search'/>">
                <div class="form-row">
                    <div class="form-group  col-md-6">
                        <label for="searchNameModal">Name</label>
                        <input type="text" class="form-control" id="searchNameModal" placeholder="Name"
                               name="<%=PROJECT_NAME%>">
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary"
                            onclick="clickBtn('formSearch', 'AdminSearchProject')">
                        Search
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>