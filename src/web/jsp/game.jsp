<%@ page import="model.Player" %>
<%@ page import="model.Country" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.BoardBean" %>
<%@ page import="model.Coordinates" %>
<jsp:useBean id="board" class="model.BoardBean" scope="session"/>
<jsp:setProperty name="board" property="*"/>
<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/field.css">
    <link rel="shortcut icon" type="image" href="${pageContext.request.contextPath}/images/logo_transparent.png">
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
                Coordinates coordinates = currentCountry.getCoordinates();

        %>
            <button style="
                    <% if(coordinates.getTop() > 0) {%>
                    top:    <%= coordinates.getTop()%>px;<%}%>
                <% if(coordinates.getRight() > 0) {%>
                    right:  <%= coordinates.getRight()%>px;<%}%>
                <% if(coordinates.getBottom() > 0) {%>
                    bottom: <%= coordinates.getBottom()%>px;<%}%>

                <% if(coordinates.getLeft() > 0) {%>
                    left:   <%= coordinates.getLeft()%>px;<%}%>
                    width: <%=Country.COUNTRY_PIXEL_WIDTH%> px;
                    height: <%=Country.COUNTRY_PIXEL_HEIGHT%> px ;"
                    class="country country<%=i%> <%=currentCountry.getOwner().getPlayerColor()%>" title="<%=currentCountry.getName()%>">
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
<form method="post" action="<%=request.getContextPath()%>/Game/">
    <%--Saves the board in the session --%>
    <% session.setAttribute("board", board); %>
    <button type="submit" name="nextTurn" value="execute">next Turn</button>
</form>
<div class="modal fade">
    <div class="modal-dialog modal-content">
        <h1 class="modal-header modal-title">You Win :D</h1>
        <div class="modal-footer">
            <button class="btn btn-primary" data-dismiss="modal" onclick="window.location.href='index.jsp'">Save</button>
            <button class="btn btn-secondary" data-dismiss="modal" onclick="window.location.href='index.jsp'">Don't save</button>
        </div>
    </div>
</div>
<%--<%@include file="modals/attackRoll.jsp" %> --%>
<%@include file="snippets/footer.jsp" %>
</body>
</html>