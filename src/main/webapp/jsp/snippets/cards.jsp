<form class="border rounded cards-container" action="<%=request.getContextPath()%>/Game/selectCard" method="post">
    <c:if test="${board.getCurrentTurn().getCurrentPlayer().getCards().isEmpty()}">
        <p>You have no cards.</p>
    </c:if>

    <c:forEach items="${board.getCurrentTurn().getCurrentPlayer().getCards()}" var="card" varStatus="status">
        <button class="card country-card" name="country-card" value="${status.index}">
            <span>${card.name}</span>
            <span class="card-bonus">${card.soldierBonus}</span>
        </button>
    </c:forEach>
</form>
