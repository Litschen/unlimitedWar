<%@ page import="model.Board" %>
<%@ page import="model.Country" %>
<%@page import="model.enums.Flag" %>
<%@ page import="model.enums.Phase" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <% Board board = (Board) session.getAttribute("board"); %>
    <form action="<%=request.getContextPath()%>/Game/selectedCountry" class="form vertical border rounded" method="post">
        <p>
            <span class="current-phase">Phase:
                <span class="emphasise">
                    <c:if test="${board.getCurrentTurn().getCurrentPhase() ==  Phase.SET}">
                        Set
                    </c:if>
                    <c:if test="${board.getCurrentTurn().getCurrentPhase() == Phase.ATTACK}">
                        Attack
                    </c:if>
                    <c:if test="${board.getCurrentTurn().getCurrentPhase() == Phase.MOVE}">
                        Move
                    </c:if>
                </span>
            </span>
            <span class="turncounter">Turn: <span class="emphasise">${board.getCurrentTurn().getTurnNumber()}</span></span>
        </p>
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
        <c:if test="${board.getCurrentTurn().getCurrentPhase() ==  Phase.SET}">
            <span>Soldiers to place: <span class="emphasise"><c:out value="${board.getCurrentTurn().getCurrentPlayer().soldiersToPlace}"/></span></span>
        </c:if>
        <c:if test="${board.getCurrentTurn().getCurrentPhase() == Phase.ATTACK}">
            <button type="submit" name="end" class="btn btn-primary">End Attack Phase</button>
        </c:if>
        <c:if test="${board.getCurrentTurn().getCurrentPhase() == Phase.MOVE && board.getCurrentTurn().getFlag() == Flag.MOVE}">
            <button type="submit" name="move" class="btn btn-primary">Move a Soldier</button>
        </c:if>
        <c:if test="${board.getCurrentTurn().getCurrentPhase() == Phase.MOVE}">
            <button type="submit" name="end" class="btn btn-primary">Don't Move Soldiers</button>
        </c:if>
    </form>
    <aside>
        <ul class="card">
            <c:forEach items="${board.getPlayers()}" var="player">
                <li class="list-group-item ${player == board.getCurrentTurn().getCurrentPlayer() ? 'current-player' : ''}">
                    <span class="player-color ${player.getPlayerColor()}"></span>
                   ${player.getPlayerName()}</li>
            </c:forEach>
        </ul>
    </aside>
    <c:if test="${board.getCurrentTurn().getCurrentPhase() ==  Phase.SET && !board.getCurrentTurn().currentPlayerIsUser()}">
        <form method="post" action="<%=request.getContextPath()%>/Game/nextTurn" class="next-turn-btn">
            <%--Saves the board in the session --%>
            <% session.setAttribute("board", board); %>
            <button type="submit" class="btn btn-primary" name="nextTurn" value="execute">next Turn</button>
        </form>
    </c:if>
</div>
<%@include file="modals/event.jsp" %>
<%@include file="modals/attackRoll.jsp" %>
<%@include file="modals/resultModal.jsp" %>
<%@include file="snippets/footer.jsp" %>
</body>
</html>