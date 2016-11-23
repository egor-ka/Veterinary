<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Change clinical record</title>
</head>
<body>
    <c:import url="/navigationBar"/>

    <form action="/changeClinicalRecordController" method="post">
        <label for="prescription"><fmt:message key="changeClinicalRecord.label.prescription"/>:</label>
        <input type="text" id="prescription" name="prescription" required>
        <br/>

        <fmt:message key="changeClinicalRecord.button.send" var="buttonChangeClinicalRecord"/>
        <input type="submit"  value="${buttonChangeClinicalRecord}" />
    </form>
    <form action="/clinicalRecordsTable">
        <fmt:message key="changeClinicalRecord.button.goBack" var="buttonGoBack"/>
        <input type="submit"  value="${buttonGoBack}" />
    </form>
</body>
</html>