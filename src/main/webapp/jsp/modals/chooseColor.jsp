<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.enums.PlayerColor" %>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/colorSelection.css">
</head>
<c:if test="${true}">
    <div class="modal show" role="dialog" style="display: block;">
        <form class="modal-dialog modal-content" action="<%=request.getContextPath()%>/colorSelection" method="post" name="colorForm">
            <div class="modal-header">
                <h5 class="modal-title">Select your color</h5>
                <button name="cancel" class="close"></button>
            </div>
            <div class="modal-body row colorOptions" style="text-align: center;">
                <c:forEach items="${PlayerColor.values()}" var="color">
                    <div class="col">
                        <input type="radio" name="selectedColor" value="${color}" class="color-selection ${color}">
                    </div>
                </c:forEach>
            </div>
            <div class="modal-footer">
                <button type="submit" name="roll" class="btn btn-primary">Start Game</button>
                <button name="cancel" class="btn btn-secondary">Cancel</button>
            </div>
        </form>
    </div>
</c:if>
