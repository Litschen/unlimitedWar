<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="shortcut icon" type="image" href="${pageContext.request.contextPath}/images/logo_transparent.png">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Welcome to Unlimited War</title>
</head>
<body>
<%@include file="snippets/header.jsp" %>
<%@include file="snippets/signed-in.jsp" %>

<main>
    <form action="<%=request.getContextPath()%>/Home/action" method="post" name="homeForm">
        <button name="selectColor" class="btn btn-primary">Start game</button>
        <button name="results" class="btn btn-secondary">See results</button>
    </form>
</main>

<%@include file="modals/chooseColor.jsp" %>
<%@include file="snippets/footer.jsp" %>
</body>
</html>
