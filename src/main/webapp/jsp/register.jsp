<!Doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="../css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="../css/stylesheet.css">
    <link rel="stylesheet" href="../css/signin.css">
    <title>Register for Unlimited War</title>
</head>

<body>
<header class="navbar navbar-dark bg-color-white">
    <a class="navbar-brand" href="index.jsp">
        <img class="logo" rel="icon" src="../images/logo_transparent.png" alt="Unlimited War logo">
    </a>
    <h1>Unlimited War</h1>
</header>

<div class="homeBox border rounded">

    <c:if test="${sessionScope.user ==  null}">
        <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
    </c:if>
    <c:if test="${sessionScope.user !=  null}">
        <h1 class="h3 mb-3 font-weight-normal">Edit Profile</h1>
    </c:if>

    <form class="mb-3" action="<%=request.getContextPath()%>/user" method="post">
        <input type="text" class="mb-3 form-control" placeholder="Username" name ="name" value="${sessionScope.user.name}" required autofocus>
        <input type="email" class="mb-3 form-control" placeholder="Email address" name ="mail" value="${sessionScope.user.mail}" required ${sessionScope.user == null ? "" : "disabled"}>
        <input type="password" class="mb-3 form-control" placeholder="Password" name ="pwd" value="${sessionScope.user.password}" required>
        <input type="password" class="mb-3 form-control" placeholder="Confirm Password" name ="confirm-pwd" value="${sessionScope.user.password}" required>

        <c:if test="${sessionScope.user ==  null}">
            <button name="register" class="btn btn-lg btn-primary btn-block">Register</button>
        </c:if>
        <c:if test="${sessionScope.user !=  null}">
            <button name="edit" class="btn btn-lg btn-primary btn-block">Save</button>
        </c:if>
    </form>
    <form class="mb-3" action="<%=request.getContextPath()%>/user" method="post">
        <button name="cancel" class="btn btn-lg btn-block">Cancel</button>
    </form>
</div>

<%@include file="modals/registrationEvent.jsp" %>
<%@include file="snippets/footer.jsp" %>
</body>
</html>
