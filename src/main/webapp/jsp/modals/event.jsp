<%@ page import="model.enums.EventType" %>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/event.css">
</head>
<div class="event-container">
    <c:forEach items="${board.getEvents()}" var="item">
            <div class="event fade top show">
                <c:if test="${item.getEventType().equals(EventType.AttackerDiceEvent)}">
                    <h3 class="title">Attacker Dice Results</h3>
                    Throw results:
                </c:if>
                <c:if test="${item.getEventType().equals(EventType.DefenderDiceEvent)}">
                    <h3 class="title">Defender Dice Results</h3>
                    Throw results:
                </c:if>
                <c:if test="${item.getEventType().equals(EventType.CasualtiesEvent)}">
                    <h3 class="title">Lost soldiers (Attacker Defender)</h3>
                </c:if>
                <c:forEach items="${item.getEventData()}" var="data">
                    ${data}
                </c:forEach>
            </div>
    </c:forEach>
</div>
