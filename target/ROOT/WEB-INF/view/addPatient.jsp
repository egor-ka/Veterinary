<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Add patient</title>
</head>
<body>
    <c:import url="/navigationBar"/>

    <form action="/addPatientController" method="post">
        <label for="ownerName"><fmt:message key="addPatient.label.ownerName"/>:</label>
        <br/>
        <input type="text" id="ownerName" name="ownerName" required>
        <br/>

        <label for="petName"><fmt:message key="addPatient.label.petName"/>:</label>
        <br/>
        <input type="text" id="petName" name="petName" required>
        <br/>

        <label for="petSpecies"><fmt:message key="addPatient.label.petSpecies"/>:</label>
        <br/>
        <input type="text" id="petSpecies" name="petSpecies" required>
        <br/>

        <fmt:message key="addPatient.button.send" var="buttonAddPatient"/>
        <input type="submit"  value="${buttonAddPatient}" />
    </form>
    <form action="/patientsTable">
        <fmt:message key="addPatient.button.goBack" var="buttonGoBack"/>
        <input type="submit"  value="${buttonGoBack}" />
    </form>
</body>
</html>