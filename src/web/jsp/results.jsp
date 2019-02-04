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
    <tr>
        <th scope="row">1</th>
        <td>06.12.2018</td>
        <td class="alert-success">Victory</td>
    </tr>
    <tr>
        <th scope="row">2</th>
        <td>06.12.2018</td>
        <td class="alert-danger">Defeat</td>
    </tr>
    <tr>
        <th scope="row">3</th>
        <td>07.12.2018</td>
        <td class="alert-success">Victory</td>
    </tr>
    </tbody>
</table>
<%@include file="snippets/footer.jsp" %>
</body>
</html>