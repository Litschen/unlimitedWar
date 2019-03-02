<div class="modal fade show">
    <form class="modal-dialog modal-content" action="<%=request.getContextPath()%>/Game/attack" method="post" name="attackForm">
        <div class="modal-header">
            <h5 class="modal-title">You Win :D</h5>
        </div>
        <div class="modal-footer">
            <button type="submit" value="save" class="btn btn-primary">Save</button>
            <button name="cancel" class="btn btn-secondary">Don't save</button>
        </div>
    </form>
</div>