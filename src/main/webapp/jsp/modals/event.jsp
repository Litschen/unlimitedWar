<%@ page import="ch.zhaw.unlimitedWar.model.enums.EventType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/event.css">
</head>
<div class="event-container">
    <c:forEach items="${sessionScope.events}" var="event">
            <div class="event fade top show">
                <h3 class="title">${event.getTitle()}</h3>
                <c:if test="${!event.getEventType().equals(EventType.CASUALTIES)}">
                    ${event.getDataLabel()}
                    <c:forEach items="${event.getEventData()}" var="data"> ${data} </c:forEach>
                </c:if>
                <c:if test="${event.getEventType().equals(EventType.CASUALTIES)}">
                    <table class="casualties-result">
                        <tr><th>Attacker</th><th>Defender</th></tr>
                        <tr>
                            <c:forEach items="${event.getEventData()}" var="data">
                                <td>${data}</td>
                            </c:forEach>
                        </tr>
                    </table>
                </c:if>
            </div>
    </c:forEach>
</div>
