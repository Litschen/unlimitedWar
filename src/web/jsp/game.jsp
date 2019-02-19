<%@ page import="model.Player" %>
<%@ page import="model.Country" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.BoardBean" %>
<jsp:useBean id="board" class="model.BoardBean" scope="session"/>
<jsp:setProperty name="board" property="*"/>
<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="../css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="../css/stylesheet.css">
    <link type="text/css" rel="stylesheet" href="../css/field.css">
    <link rel="shortcut icon" type="image" href="../images/logo_transparent.png">
    <title>Play Unlimited War</title>
</head>
<body>
<%@include file="snippets/header.jsp" %>

<div class="wrapper">
    <div class="field border rounded">
        <%
            ArrayList<Country> allCountries  = board.getCountries();
            for(int i = 1; i <= allCountries.size(); i++){
                Country currentCountry = allCountries.get(i - 1);

        %>
            <button class="country country-<%=i%> <%=currentCountry.getOwner().getPlayerColor()%>" title="<%=currentCountry.getName()%>">
                <%=currentCountry.getSoldiersCount()%></button>
            <% if(i % 4 == 0 && i < BoardBean.COUNTRY_COUNT_GENERATION){ %>
                <br/>
            <% }%>
        <%}%>
        <%-- continent connectors --%>
        <%
            for(int i = 1; i <=4; i++){
        %>
        <span class="connector<%=i%> lineThrough rTol"></span>
        <% }%>
    </div>
    <aside>
        <ul class="card">
            <% for(Player player : board.getPlayers()){

            %>
            <li class="list-group-item">
                <span class="player-color <%= player.getPlayerColor() %>"></span>
                <%= player.getPlayerName() %>
            </li>
            <% } %>
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