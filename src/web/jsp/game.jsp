<%@ page import="model.Player" %>
<%@ page import="model.Country" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.BoardBean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <form action="/Game/selectedCountry" class="form vertical border rounded">
        <div class="field border rounded">
            <%
                ArrayList<Country> allCountries  = board.getCountries();
                for(int i = 1; i <= allCountries.size(); i++){
                    Country currentCountry = allCountries.get(i - 1);

                %>
            <button  name="country" value="<%=i - 1%>"
                    class="country country-<%=i%> <%=currentCountry.getOwner().getPlayerColor()%>" title="<%=currentCountry.getName()%>">
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
        <c:if test="${true}">
            <p>Soldiers to place: <c:out value="${board.getSoldiersToPlace()}"/></p>
        </c:if>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
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
<c:if test='${false}'>
    <%@include file="modals/attackRoll.jsp" %>
</c:if>
<%@include file="snippets/footer.jsp" %>
</body>
</html>