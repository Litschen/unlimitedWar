<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="result" class="ch.zhaw.unlimitedWar.controller.ResultController" scope="session"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="../css/bootstrap.css" rel="stylesheet">
    <link href="../css/stylesheet.css" rel="stylesheet">
    <link href="../css/results.css" rel="stylesheet">

    <title>Results</title>
</head>
<body>
<%@include file="snippets/header.jsp" %>
<table id="resultsTable" class="table table-striped table-light">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Date</th>
        <th scope="col">Outcome</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items = "${result.getAllResultsOfUser(sessionScope.user)}" var="data" varStatus="status">
        <tr>
            <th scope="row">${status.count}</th>
            <td>
                <fmt:parseDate value="${data.getDate()}" var="formatted" pattern="yyyy-MM-dd"/>
                <fmt:setLocale value="de_DE"/>
                <fmt:formatDate value="${formatted}"/>
            </td>
            <td class="${data.getOutcome() ? 'alert-success' : 'alert-danger'}">${data.getOutcome() ? 'win':'lose'}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@include file="snippets/footer.jsp" %>
</body>
</html>
