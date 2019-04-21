<%@ page import="controller.SignInController" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/signinError.css">
</head>
<c:if test="${SignInController.DISPLAY_ERROR_MESSAGE}">
    <div class="alert">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <%=SignInController.SIGNIN_ERROR_MESSAGE%>
    </div>
</c:if>