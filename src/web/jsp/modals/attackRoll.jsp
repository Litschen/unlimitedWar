<jsp:useBean id="dice" class="controller.BoardBean"/>
<jsp:setProperty name="dice" property="*"/>
<script>
    function toggleDice(elem) {
        if(elem.classList.contains('filled')){
            elem.classList.remove('filled');
            elem.innerHTML = 'OFF';
        } else {
            elem.classList.add('filled');
            elem.innerHTML = 'ON';
        }
    }

    function x(soldiersCount) {
        return 2;
    }

    function attack() {
        // roll dice
    }
</script>

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
            </div>
            <div class="col">
                <h6>Defender</h6>
                <label class="switch">
                    <input type="checkbox dice" checked>
                    <span class="slider round"></span>
                </label>
            </div>
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-primary">Roll</button>
        </div>
    </form>

</div>
