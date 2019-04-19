<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/event.css">
</head>
<div class="event-container">
    <c:forEach items="${sessionScope.events}" var="event">
        <div class="event fade top show">
            <h3 class="title">${event.getTitle()}</h3>
                ${event.getDataLabel()}
        </div>
    </c:forEach>
</div>
