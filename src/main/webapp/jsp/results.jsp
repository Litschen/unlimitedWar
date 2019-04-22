<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="result" class="controller.ResultController" scope="session"/>

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

<table id="resultsTable" class="table table-striped table-light">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Date</th>
        <th scope="col">Outcome</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items = "${(ResultController.getAllResultsOfUser())}" var="data">
        <th scope="row">1</th>
        <td>${data}</td>
        <td class="alert-success">Victory</td>
    </c:forEach>
    </tr>
    </tbody>
</table>
<%@include file="snippets/footer.jsp" %>
<%@include file="modals/resultModal.jsp" %>
</body>
</html>