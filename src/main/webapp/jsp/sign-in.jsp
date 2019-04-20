<!Doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Unlimited War</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/signin.css">
</head>

<body>
<%@include file="snippets/header.jsp" %>

<c:if test="${sessionScope.user != null}">
    <c:redirect url="./index.jsp"/>
</c:if>

<div class="homeBox border rounded">
    <h1 class="h3 mb-3 font-weight-normal">Sign in</h1>
    <%@include file="modals/signinError.jsp" %>
    <form class="form-signin mb-3" action="<%=request.getContextPath()%>/SignIn" method="post">
        <input type="email" id="inputEmail" class="mb-3 form-control" name="<%=SignInController.MAIL_PARAMETER_NAME%>"
               placeholder="Email address" required autofocus>
        <input type="password" id="inputPassword" class="mb-3 form-control"
               name="<%=SignInController.PASSWORD_PARAMETER_NAME%>" placeholder="Password" required>
        <button type="submit" class="btn btn-lg btn-primary btn-block">Sign in</button>
    </form>
    <button class="btn btn-lg btn-block" onclick="window.location.href='profile.jsp'">Register</button>
</div>

<%@include file="snippets/footer.jsp" %>
</body>
</html>
