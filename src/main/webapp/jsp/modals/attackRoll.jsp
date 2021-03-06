<c:if test="${board.getCurrentTurn().getFlag() == Flag.ATTACK}">
    <div class="modal show" role="dialog">
        <form class="modal-dialog modal-content" action="<%=request.getContextPath()%>/Game/attack" method="post" name="attackForm">
            <div class="modal-header">
                <h5 class="modal-title">Select dices to attack</h5>
                <button name="cancel" class="close"></button>
            </div>
            <div class="modal-body row">
                <div class="col">
                    <h6>Attacker</h6>
                    <% for (int i = 0; i < board.getCurrentTurn().getAttackDiceCount(); i++) { %>
                    <input type="checkbox" name="attackDice" class="dice" checked <%= (i == 0) ? "disabled" : "" %>>
                    <% } %>
                </div>
                <div class="col">
                    <h6>Defender</h6>
                    <% for (int i = 0; i < board.getCurrentTurn().getDefendDiceCount(); i++) { %>
                    <input type="checkbox" name="defendDice" class="dice" checked disabled>
                    <% } %>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" name="roll" class="btn btn-primary">Roll</button>
                <button name="cancel" class="btn btn-secondary">Cancel</button>
            </div>
        </form>
    </div>
</c:if>
