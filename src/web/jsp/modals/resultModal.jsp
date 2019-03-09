<div class="modal fade show">
    <form class="modal-dialog modal-content" action="<%=request.getContextPath()%>/Game/finish" method="post">
        <%--<c:if test="${board.flag == 'win'}">--%>
            <div class="modal-header">
                <h5 class="modal-title">You Win!</h5>
            </div>
            <div class="modal-body">
                <div class="trophy"></div>
            </div>
        <%--</c:if>--%>
        <%--<c:if test="${board.flag == 'lose'}">--%>
            <%--<div class="modal-header">--%>
                <%--<h5 class="modal-title">You Lose :(</h5>--%>
            <%--</div>--%>
        <%--</c:if>--%>
        <div class="modal-footer">
            <button name="save" value="save" class="btn btn-primary">Save</button>
            <button name="cancel" class="btn btn-secondary">Don't save</button>
        </div>
    </form>
</div>