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
<header class="navbar navbar-dark bg-color-white">
    <a class="navbar-brand" href="index.jsp">
        <img class="logo" rel="icon" src="../imageslogo_transparent.png" alt="Unlimited War logo">
    </a>
    <h1>Unlimited War</h1>
    <div class="dropdown">
        <img class="user" rel="icon" src="../images/menu.png" alt="Menu Image"/>
        <ul class="dropdown-menu dropdown-menu-right">
            <li>
                <a class="dropdown-item" href="index.jsp"><img class="menu-icon" rel="icon" src="../images/home.png" alt="Home"/>Home</a>
            </li>
            <li>
                <a class="dropdown-item" href="profile.jsp"><img class="menu-icon" rel="icon" src="../images/user.png" alt="Profile"/>Profile</a>
            </li>
            <li>
                <a class="dropdown-item" href="./results.jsp"><img class="menu-icon" rel="icon" src="../images/results.png" alt="Results"/>Results</a>
            </li>
            <li>
                <a class="dropdown-item" href="sign-in.jsp"><img class="menu-icon" rel="icon" src="../images/log-out2.png" alt="Logout"/>Log out</a>
            </li>
        </ul>
    </div>
</header>
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
<footer class="bg-color-white">
    <p>PSIT Gruppe 02<br/>
        Crnjac T, Huguenin M, Schreier M</p>
</footer>
</body>
</html>