<c:if test="${board.getCurrentTurn().getFlag() == Flag.GAME_WIN || board.getCurrentTurn().getFlag() == Flag.GAME_LOSE}">
    <div class="modal show" role="dialog">
        <form class="modal-dialog modal-content" action="<%=request.getContextPath()%>/Result/Save" method="post">
            <c:if test="${board.getCurrentTurn().getFlag() == Flag.GAME_WIN}">
                <div class="modal-header">
                    <h5 class="modal-title">You Win!</h5>
                </div>
                <div class="modal-body">
                    <div class="trophy"></div>
                </div>
                <div class="modal-footer">
                    <button name="win" class="btn btn-primary">Finish</button>
                </div>
            </c:if>
            <c:if test="${board.getCurrentTurn().getFlag() == Flag.GAME_LOSE}">
                <div class="modal-header">
                    <h5 class="modal-title">You Lose :(</h5>
                </div>
                <div class="modal-footer">
                    <button name="lose" class="btn btn-primary">Finish</button>
                </div>
            </c:if >
        </form>
    </div>
</c:if>