<div class="border rounded cards-container">
    <c:if test="${board.getCurrentTurn().getCurrentPlayer().getCards().isEmpty()}">
        <p>You have no cards.</p>
    </c:if>

    <c:forEach items="${board.getCurrentTurn().getCurrentPlayer().getCards()}" var="card">
        <button class="card country-card" name="country-card">
            <span>${card.name}</span>
            <span class="card-bonus">${card.soldierBonus}</span>
        </button>
    </c:forEach>
</div>
