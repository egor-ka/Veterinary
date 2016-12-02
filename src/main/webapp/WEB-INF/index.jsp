<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title>Index</title>
</head>
<body>
    <%
        response.sendRedirect("./signIn");
    %>
</body>
</html>