<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${sessionScope.user == null}">
    <c:redirect url="./sign-in.jsp"/>
</c:if>
