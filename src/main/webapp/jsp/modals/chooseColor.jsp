<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.enums.PlayerColor" %>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/colorSelection.css">
<script>
    function enableSubmit() {
        document.getElementsByName("play")[0].disabled=false;
    }
</script>
</head>
<c:if test="${sessionScope.showColorModal}">
    <div class="modal show" role="dialog" style="display: block;">
        <form class="modal-dialog modal-content" action="<%=request.getContextPath()%>/Home/colorSelection" method="post" name="colorForm">
            <div class="modal-header">
                <h5 class="modal-title">Select your color</h5>
                <button name="cancel" class="close"></button>
            </div>
            <div class="modal-body row colorOptions" style="text-align: center;">
                <c:forEach items="${PlayerColor.values()}" var="color">
                    <div class="col">
                        <input type="radio" name="selectedColor" value="${color}" class="color-selection ${color}" onclick="enableSubmit()">
                    </div>
                </c:forEach>
            </div>
            <div class="modal-footer">
                <button name="play" class="btn btn-primary" disabled>Play</button>
                <button name="cancel" class="btn btn-secondary">Cancel</button>
            </div>
        </form>
    </div>
</c:if>
