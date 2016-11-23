<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Add doctor</title>
</head>
<body>
    <c:import url="/navigationBar"/>

    <form action="/addDoctorController" method="post">
        <label for="firstName"><fmt:message key="addDoctor.label.firstName"/>:</label>
        <br/>
        <input type="text" id="firstName" name="firstName" required=<fmt:message key="addDoctor.required"/>>
        <br/>

        <label for="lastName"><fmt:message key="addDoctor.label.lastName"/>:</label>
        <br/>
        <input type="text" id="lastName" name="lastName" required>
        <br/>

        <label for="specialization"><fmt:message key="addDoctor.label.specialization"/>:</label>
        <br/>
        <input type="text" id="specialization" name="specialization" required>
        <br/>

        <fmt:message key="addDoctor.label.button.send" var="buttonAddDoctor"/>
        <input type="submit"  value="${buttonAddDoctor}" />
    </form>
    <form action="/doctorsTable">
        <fmt:message key="addDoctor.label.button.goBack" var="buttonGoBack"/>
        <input type="submit"  value="${buttonGoBack}" />
    </form>
</body>
</html>