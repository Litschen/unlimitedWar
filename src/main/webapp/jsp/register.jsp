<!Doctype html>
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
    <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
    <form class="mb-3" action="<%=request.getContextPath()%>/user" method="post">
        <input type="text" id="inputName" class="mb-3 form-control" placeholder="Username" name ="name" required autofocus>
        <input type="email" id="inputEmail" class="mb-3 form-control" placeholder="Email address" name ="mail" required>
        <input type="password" id="inputPassword" class="mb-3 form-control" placeholder="Password" name ="pwd" required>
        <input type="password" id="inputConfirmPassword" class="mb-3 form-control" placeholder="Confirm Password" name ="confirm-pwd" required>
        <button name="register" class="btn btn-lg btn-primary btn-block">Register</button>
    </form>
    <form class="mb-3" action="<%=request.getContextPath()%>/user" method="post">
        <button name="cancel" class="btn btn-lg btn-block">Cancel</button>
    </form>
</div>

<%@include file="modals/registrationEvent.jsp" %>
<%@include file="snippets/footer.jsp" %>
</body>
</html>
