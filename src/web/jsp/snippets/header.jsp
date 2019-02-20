<header class="navbar navbar-dark bg-color-white">
    <a class="navbar-brand" href="./index.jsp">
        <img class="logo" rel="icon" src="${pageContext.request.contextPath}/images/logo_transparent.png" alt="Unlimited War logo">
    </a>
    <h1>Unlimited War</h1>
    <div class="dropdown">
        <img class="user" rel="icon" src="${pageContext.request.contextPath}/images/menu.png" alt="Menu Image"/>
        <ul class="dropdown-menu dropdown-menu-right">
            <li>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/jsp/index.jsp">Home <img class="menu-icon float-right" rel="icon" src="${pageContext.request.contextPath}/images/home.png" alt="Home"/></a>
            </li>
            <li>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/jsp/profile.jsp">Profile <img class="menu-icon float-right" rel="icon" src="${pageContext.request.contextPath}/images/user.png" alt="Profile"/></a>
            </li>
            <li>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/jsp/results.jsp">Results <img class="menu-icon float-right" rel="icon" src="${pageContext.request.contextPath}/images/results.png" alt="Results"/></a>
            </li>
            <li>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/jsp/sign-in.jsp">Log out <img class="menu-icon float-right" rel="icon" src="${pageContext.request.contextPath}/images/log-out2.png" alt="Logout"/></a>
            </li>
        </ul>
    </div>
</header>