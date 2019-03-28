<%@ page import="model.interfaces.Event" %>
<%@ page import="model.enums.EventType" %>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/event.css">
    <script src="${pageContext.request.contextPath}/js/popover.js"></script>
</head>
<%-- Event event = board.getEvent(EventType.AttackerDiceEvent);%>
<script type="text/javascript">
    window.onload = function () {
    var type = "<%= event.getEventType() %>";
        var data = <%= event.getEventData() %>;

        if (type === "AttackerDiceEvent"){
            createPopup("Attacker Dice Results", data, "Throw result: ");
        }
    };
</script>--%>
<div class="event-container">
</div>
