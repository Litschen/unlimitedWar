<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${sessionScope.user == null}">
   <c:redirect url="./sign-in.jsp"/>
</c:if>

<jsp:include page="./snippets/header.jsp"/>
