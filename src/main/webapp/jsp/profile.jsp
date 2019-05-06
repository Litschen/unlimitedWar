<!Doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="register" scope="session" value="${sessionScope.user == null}"/>
<c:set var="user" scope="session" value="${register ? sessionScope.tmpUser : sessionScope.user}"/>
<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="../css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="../css/stylesheet.css">
    <link rel="stylesheet" href="../css/signin.css">

    <c:if test="${register}">
        <title>Register for Unlimited War</title>
    </c:if>
    <c:if test="${!register}">
        <title>Edit Profile</title>
    </c:if>
</head>

<body>
<%@ include file="snippets/header.jsp" %>

<div class="homeBox border rounded">
    <c:if test="${register}">
        <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
    </c:if>
    <c:if test="${!register}">
        <h1 class="h3 mb-3 font-weight-normal">Edit Profile</h1>
    </c:if>

    <form class="mb-3" action="<%=request.getContextPath()%>/user" method="post">
        <input type="text" class="mb-3 form-control" placeholder="Username" name ="name" value="${user.name}" required autofocus>
        <input type="email" class="mb-3 form-control" placeholder="Email address" name ="mail" value="${user.mail}" required ${register ? "" : "readonly"}>
        <input type="password" class="mb-3 form-control" placeholder="Password" name ="pwd" value="${user.password}" required>
        <input type="password" class="mb-3 form-control" placeholder="Confirm Password" name ="confirm-pwd" value="${user.password}" required>

        <c:if test="${register}">
            <button name="register" class="btn btn-lg btn-primary btn-block">Register</button>
        </c:if>
        <c:if test="${!register}">
            <button name="save" class="btn btn-lg btn-primary btn-block">Save</button>
        </c:if>
    </form>
    <form class="mb-3" action="<%=request.getContextPath()%>/user" method="post">
        <button name="cancel" class="btn btn-lg btn-block">Cancel</button>
    </form>
</div>
<%@ include file="snippets/footer.jsp" %>
</body>
</html>
