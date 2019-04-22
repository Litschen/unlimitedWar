<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="signOutModal" class="modal show" role="dialog" style="display: none;">
    <div class="modal-dialog modal-content">
        <div class="modal-header">
            <h5 class="modal-title">Confirm sign out</h5>
        </div>
        <div class="modal-body row">
            <p class="modal-dialog">If you sign out you lose all your progress. Do you want to sign out?</p>
        </div>
        <div class="modal-footer">
            <form action="<%=request.getContextPath()%>/SignOut" method="post">
                <button name="Sign out" class="btn btn-primary">Sign out</button>
            </form>
            <button name="cancel" class="btn btn-secondary"
                    onclick="document.getElementById('signOutModal').style.display = 'none'">Cancel
            </button>
        </div>
    </div>
</div>
