<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${board.getCurrentTurn().currentPlayerIsUser()}">
    <form class="border rounded cards-container" action="<%=request.getContextPath()%>/Game/selectCard" method="post">
        <c:set var="curPlayer" value="${board.getCurrentTurn().getCurrentPlayer()}"></c:set>

        <c:if test="${curPlayer.getCards().isEmpty()}">
            <p>You have no cards.</p>
        </c:if>

        <c:forEach items="${curPlayer.getCards()}" var="card" varStatus="status">
            <button class="card country-card" name="country-card" value="${status.count - 1}">
                <span>${card.cardName}</span>
                <span class="card-bonus">${card.soldierBonus}</span>
                <span class="${card.isPlayerOwner(curPlayer) ? 'owner' : ''}"></span>
            </button>
        </c:forEach>
    </form>
</c:if>