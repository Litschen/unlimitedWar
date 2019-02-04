<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="../css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="../css/stylesheet.css">
    <link rel="stylesheet" href="../css/signin.css">
    <title>Profile</title>
</head>

<body>
<%@include file="snippets/header.jsp" %>

<div class="homeBox border rounded">
    <h1 class="h3 mb-3 font-weight-normal">Edit Profile</h1>
    <form>
        <input type="text" id="inputName" class="mb-3 form-control" placeholder="Username" value="Max" required autofocus>
        <input type="email" id="inputEmail" class="mb-3 form-control" placeholder="Email address" value="max@gmail.com" required>
        <input type="date" id="inputBirthday" class="mb-3 form-control" placeholder="Birthday" value="2015-05-11" required>
        <input type="password" id="inputPassword" class="mb-3 form-control" placeholder="Password" value="asdf" required>
        <input type="password" id="inputConfirmPassword" class="mb-3 form-control" placeholder="Confirm Password" value="asdf" required>
        <button type="submit" class="btn btn-lg btn-primary btn-block">Save</button>
        <button type="submit" class="btn btn-lg btn-block">Cancel</button>
    </form>
</div>

<%@include file="snippets/footer.jsp" %>
</body>
</html>
