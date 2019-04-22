<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${sessionScope.user == null}">
    <c:redirect url="${pageContext.request.contextPath}/jsp/sign-in.jsp"/>
</c:if>

<jsp:include page="${pageContext.request.contextPath}/jsp/snippets/header.jsp" />
