<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/confirmSignOut.css">
</head>
<div class="modal-content" id="signOutModal" role="dialog" style="display: none">

    <div class="modal-header">
        <h5 class="modal-title">Confirm sign out</h5>
    </div>
    <p class="modal-dialog">If you sign out you lose all your progress. Do you want to sign out?</p>
    <div class="modal-footer">
        <form action="<%=request.getContextPath()%>/SignOut" method="post">
            <button name="Sign out" class="btn btn-primary">Sign out</button>
        </form>
        <button name="cancel" class="btn btn-secondary"
                onclick="document.getElementById('signOutModal').style.display = 'none'">Cancel
        </button>
    </div>
</div>
