<%@ page import="model.Board" %>
<%@ page import="model.Country" %>
<%@page import="model.Player" %>
<%@page import="model.enums.Flag" %>
<%@ page import="model.enums.Phase" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="board" class="model.Board" scope="session"/>
<jsp:setProperty name="board" property="*"/>
<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/field.css">
    <link rel="shortcut icon" type="image" href="${pageContext.request.contextPath}/images/logo_transparent.png">
    <title>Play Unlimited War</title>
</head>
<body>
<%@include file="snippets/header.jsp" %>

<div class="wrapper">
    <form action="<%=request.getContextPath()%>/Game/selectedCountry" class="form vertical border rounded" method="post">
        <div class="field border rounded">
            <%  List<Country> allCountries = board.getCountries();
                for (int i = 1; i <= allCountries.size(); i++) {
                    Country currentCountry = allCountries.get(i - 1);
            %>
            <button type="submit" name="country" value="<%=i -1%>" title="<%=currentCountry.getName()%>"
                    class="country country-<%=i%> <%=currentCountry.isSelected() ? currentCountry.getOwner().getPlayerColor() + "-selected" : currentCountry.getOwner().getPlayerColor()%>">
                    <%=currentCountry.getSoldiersCount()%></button>
                <% if (i % 4 == 0 && i < Board.COUNTRY_COUNT_GENERATION) { %>
                    <br/>
                <% } %>
            <% } %>
            <%-- continent connectors --%>
            <% for (int i = 1; i <= 4; i++) { %>
            <span class="connector<%=i%> lineThrough rTol"></span>
            <% }%>
        </div>
        <% session.setAttribute("board", board); %>
        <c:if test="${board.getCurrentTurn().getCurrentPhase() ==  Phase.SETTINGPHASE}">
            <span>Soldiers to place: <c:out value="${board.getCurrentTurn().getCurrentPlayer().soldiersToPlace}"/></span>
        </c:if>
        <c:if test="${board.getCurrentTurn().getCurrentPhase() == Phase.ATTACKPHASE}">
            <button type="submit" name="end" class="btn btn-primary">End Attack Phase</button>
        </c:if>
        <c:if test="${board.getCurrentTurn().getCurrentPhase() == Phase.MOVINGPHASE && board.getCurrentTurn().getFlag() == Flag.MOVE}">
            <button type="submit" name="move" class="btn btn-primary">Move a Soldier</button>
        </c:if>
        <c:if test="${board.getCurrentTurn().getCurrentPhase() == Phase.MOVINGPHASE}">
            <button type="submit" name="end" class="btn btn-primary">Don't Move Soldiers</button>
        </c:if>
    </form>
    <aside>
        <ul class="card">
            <% for (Player player : board.getPlayers()) { %>
            <li class="list-group-item">
                <span class="player-color <%= player.getPlayerColor() %>"></span>
                <%= player.getPlayerName() %>
            </li>
            <% } %>
        </ul>
    </aside>
</div>
<form method="post" action="<%=request.getContextPath()%>/Game/nextTurn">
    <%--Saves the board in the session --%>
    <% session.setAttribute("board", board); %>
    <button type="submit" class="btn btn-primary" name="nextTurn" value="execute">next Turn</button>
</form>

<%@include file="modals/event.jsp" %>
<%@include file="modals/attackRoll.jsp" %>
<%@include file="modals/resultModal.jsp" %>
<%@include file="snippets/footer.jsp" %>
</body>
</html>