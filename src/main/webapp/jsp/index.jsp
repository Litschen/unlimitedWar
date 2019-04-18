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

    <main>
        <a href="${pageContext.request.contextPath}/jsp/game.jsp" class="btn btn-primary" role="button">Start game</a>
        <a href="${pageContext.request.contextPath}/jsp/results.jsp" class="btn btn-primary" role="button">See
            results</a>
    </main>

<%@include file="snippets/footer.jsp" %>
</body>
</html>
