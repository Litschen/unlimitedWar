<c:if test="${board.flag == Flag.GAME_WIN || board.flag == Flag.GAME_LOSE}">
    <div class="modal show" role="dialog">
        <form class="modal-dialog modal-content" action="<%=request.getContextPath()%>/Game/result" method="post">
            <c:if test="${board.flag == Flag.GAME_WIN}">
                <div class="modal-header">
                    <h5 class="modal-title">You Win!</h5>
                </div>
                <div class="modal-body">
                    <div class="trophy"></div>
                </div>
            </c:if>
            <c:if test="${board.flag == Flag.GAME_LOSE}">
                <div class="modal-header">
                    <h5 class="modal-title">You Lose :(</h5>
                </div>
            </c:if>
            <div class="modal-footer">
                <button name="save" value="save" class="btn btn-primary">Save</button>
                <button name="cancel" class="btn btn-secondary">Don't save</button>
            </div>
        </form>
    </div>
</c:if>