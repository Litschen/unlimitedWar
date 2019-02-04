<%@ page import = "controller.Board" %>

<jsp:useBean id="board" class="controller.Board"/>
<jsp:setProperty name="board" property="*"/>
<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="../css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="../css/stylesheet.css">
    <link type="text/css" rel="stylesheet" href="../css/field.css">
    <title>Play Unlimited War</title>
</head>
<body>
<%@include file="snippets/header.jsp" %>

<div class="wrapper">
    <div class="field border rounded">
        <span class="country country-1 red">2</span>
        <span class="country country-2 blue">5</span>
        <span class="country country-3 yellow">1</span>
        <span class="country country-4 green">6</span>
        <br/>
        <span class="country country-5 green">10</span>
        <span class="country country-6 red">1</span>
        <span class="country country-7 yellow">5</span>
        <span class="country country-8 green">13</span>
        <br/>
        <span class="country country-9 blue">7</span>
        <span class="country country-10 red">2</span>
        <span class="country country-11 blue">9</span>
        <span class="country country-12 yellow">7</span>
        <br/>
        <span class="country country-13 green">3</span>
        <span class="country country-14 red">8</span>
        <span class="country country-15 yellow">5</span>
        <span class="country country-16 blue">1</span>
    </div>
    <aside>
        <ul class="card">
            <li class="list-group-item">
                <span class="player-color <%= board.getPlayer(0).getPlayerColor() %>"></span>
                <%= board.getPlayer(0).getPlayerName() %>
            </li>
            <li class="list-group-item">
                <span class="player-color <%= board.getPlayer(1).getPlayerColor() %>"></span>
                <%= board.getPlayer(1).getPlayerName() %>
            </li>
            <li class="list-group-item">
                <span class="player-color <%= board.getPlayer(2).getPlayerColor() %>"></span>
                <%= board.getPlayer(2).getPlayerName() %>
            </li>
            <li class="list-group-item">
                <span class="player-color <%= board.getPlayer(3).getPlayerColor() %>"></span>
                <%= board.getPlayer(3).getPlayerName() %>
            </li>
        </ul>
    </aside>
</div>

<div class="modal fade">
    <div class="modal-dialog modal-content">
        <h1 class="modal-header modal-title">You Win :D</h1>
        <div class="modal-footer">
            <button class="btn btn-primary" data-dismiss="modal" onclick="window.location.href='index.jsp'">Save</button>
            <button class="btn btn-secondary" data-dismiss="modal" onclick="window.location.href='index.jsp'">Don't save</button>
        </div>
    </div>
</div>
<%@include file="snippets/footer.jsp" %>
</body>
</html>