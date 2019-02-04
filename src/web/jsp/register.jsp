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
    <form class="mb-3" action="index.jsp">
        <input type="text" id="inputName" class="mb-3 form-control" placeholder="Username" required autofocus>
        <input type="email" id="inputEmail" class="mb-3 form-control" placeholder="Email address" required>
        <input type="date" id="inputBirthday" class="mb-3 form-control" placeholder="Birthday" required>
        <input type="password" id="inputPassword" class="mb-3 form-control" placeholder="Password" required>
        <input type="password" id="inputConfirmPassword" class="mb-3 form-control" placeholder="Confirm Password" required>
        <button type="submit" class="btn btn-lg btn-primary btn-block">Register</button>
    </form>
    <button class="btn btn-lg btn-block" onclick="window.location.href='sign-in.jsp'">Cancel</button>
</div>

<%@include file="snippets/footer.jsp" %>
</body>
</html>
