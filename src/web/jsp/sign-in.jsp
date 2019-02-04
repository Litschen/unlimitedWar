<!Doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Unlimited War</title>
    <link type="text/css" rel="stylesheet" href="../css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="../css/stylesheet.css">
    <link type="text/css" rel="stylesheet" href="../css/signin.css">
</head>

<body>
<header class="navbar navbar-dark bg-color-white">
    <a class="navbar-brand" href="index.jsp">
        <img class="logo" rel="icon" src="../images/logo_transparent.png" alt="Unlimited War logo">
    </a>
    <h1>Unlimited War</h1>
</header>

<div class="homeBox border rounded">
    <h1 class="h3 mb-3 font-weight-normal">Sign in</h1>
    <form class="form-signin mb-3" action="index.jsp">
        <input type="email" id="inputEmail" class="mb-3 form-control" placeholder="Email address" required autofocus>
        <input type="password" id="inputPassword" class="mb-3 form-control" placeholder="Password" required>
        <button type="submit" class="btn btn-lg btn-primary btn-block">Sign in</button>
    </form>
    <button class="btn btn-lg btn-block" onclick="window.location.href='register.html'">Register</button>
</div>

<%@include file="snippets/footer.jsp" %>
</body>
</html>
