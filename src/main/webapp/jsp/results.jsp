<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="controller.ResultController" %>

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
<%@ include file="snippets/redirect-signed-out.jsp" %>

<table id="resultsTable" class="table table-striped table-light">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Date</th>
        <th scope="col">Outcome</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items = "${(items.getAllResultsOfUser())}" var="data"> ${data}
        <th scope="row">1</th>
        <td>${data.getAllResultsOfUser().print()}</td>
        <td class="alert-success">Victory</td>
    </c:forEach>
    </tr>
    </tbody>
</table>
<%@ include file="snippets/footer.jsp" %>
</body>
</html>