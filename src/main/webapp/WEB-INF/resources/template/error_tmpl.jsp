<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="messages">
    <c:if test="${!empty sessionScope.error}">
        <div class="alert alert-danger" role="alert">${sessionScope.error}</div>
        <c:remove var="error" scope="session" />
    </c:if>
</div>

<script>
    function alertInfo(messageText) {
        const messages = document.getElementById('messages');
        const message = document.createElement('div');
        message.className = 'alert alert-warning alert-dismissible fade show';
        message.innerHTML = messageText + ' <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
        messages.appendChild(message);
    }
</script>
