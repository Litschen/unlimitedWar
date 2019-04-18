<%@ page import="controller.UserController" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/signinError.css">
</head>
<c:if test="${UserController.displayErrorMessage}">
    <div class="alert">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <%=UserController.SIGNIN_ERROR_MESSAGE%>
    </div>
</c:if>