<c:if test="${board.getCurrentTurn().getCurrentPlayer().getCards().isEmpty()}">
    <p>You have no cards.</p>
</c:if>

<c:forEach items="${board.getCurrentTurn().getCurrentPlayer().getCards()}" var="card">
    <div class="country-card">
        <span class="card-name">${card.name}</span>
        <span class="card-bonus">${card.soldierBonus}</span>
    </div>
</c:forEach>
