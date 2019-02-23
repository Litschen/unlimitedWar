<jsp:useBean id="dice" class="controller.GameController"/>
<jsp:setProperty name="dice" property="*"/>

<div class="modal show" role="dialog">
    <form class="modal-dialog modal-content">
        <div class="modal-header">
            <h5 class="modal-title">Modal title</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">x</span>
            </button>
        </div>
        <div class="modal-body row">
            <div class="col">
                <h6>Attacker</h6>
                <input type="checkbox" class="dice" checked>
                <input type="checkbox" class="dice">
                <input type="checkbox" class="dice" disabled>
            </div>
            <div class="col">
                <h6>Defender</h6>
                <input type="checkbox" class="dice" checked>
                <input type="checkbox" class="dice">
                <input type="checkbox" class="dice" disabled>
            </div>
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-primary">Roll</button>
        </div>
    </form>

</div>
