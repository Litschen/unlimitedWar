<div class="card-img-overlay">
<div class="modal show" role="dialog">
    <form class="modal-dialog modal-content" action="<%=request.getContextPath()%>/Game/attack" method="post" name="attackForm">
        <div class="modal-header">
            <h5 class="modal-title">Modal title</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close" />
        </div>
        <div class="modal-body row">
            <div class="col">
                <h6>Attacker</h6>
                <%-- TODO use for loop --%>
                <input type="checkbox" name="attackDice1" class="dice" checked>
                <input type="checkbox" name="attackDice2" class="dice">
                <input type="checkbox" name="attackDice3" class="dice" disabled>
            </div>
            <div class="col">
                <h6>Defender</h6>
                <input type="checkbox" name="defendDice1" class="dice" checked>
                <input type="checkbox" name="defendDice2" class="dice">
                <input type="checkbox" name="defendDice3" class="dice" disabled>
            </div>
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-primary">Roll</button>
            <button type="submit" class="btn btn-secondary">Cancel</button>
        </div>
    </form>
</div>
