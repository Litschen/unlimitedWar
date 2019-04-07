<%@ page import="model.enums.EventType" %>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/event.css">
</head>
<div class="event-container">
    <c:forEach items="${board.getEvents()}" var="item">
            <div class="event fade top show">
                <h3 class="title">${item.getTitle()}</h3>
                <c:if test="${!item.getEventType().equals(EventType.CASUALTIES)}">
                    ${item.getDataLabel()}
                    <c:forEach items="${item.getEventData()}" var="data"> ${data} </c:forEach>
                </c:if>
                <c:if test="${item.getEventType().equals(EventType.CASUALTIES)}">
                    <table class="casualties-result">
                        <tr><th>Attacker</th><th>Defender</th></tr>
                        <tr>
                            <c:forEach items="${item.getEventData()}" var="data">
                                <td>${data}</td>
                            </c:forEach>
                        </tr>
                    </table>
                </c:if>

            </div>
    </c:forEach>
</div>
