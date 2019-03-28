<%@ page import="model.enums.EventType" %>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/event.css">
</head>
<div class="event-container">
    <c:forEach items="${board.getEvents()}" var="item">
        <c:if test="${item.getEventType().equals(EventType.AttackerDiceEvent)}">
            <div class="event fade top show">
                <h3 class="title">Attacker Dice Results</h3>
                <c:forEach items="${item.getEventData()}" var="data">
                    <p>Throw result: ${data}</p>
                </c:forEach>
            </div>
        </c:if>
    </c:forEach>
</div>
