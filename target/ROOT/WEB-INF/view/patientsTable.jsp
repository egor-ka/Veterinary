<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Patients Table</title>
</head>
<body>
    <c:import url="/navigationBar"/>

    <form action="/addPatient" method="post">
        <fmt:message key="patientsTable.button.add.patient" var="buttonAddPatient"/>
        <input type="submit"  value="${buttonAddPatient}" />
    </form>

    <label style="color:red">${sessionScope["error_messages_patients_table"]["patientsTable"]}</label>
    <br/>
    <table class="table-fill" border="1">
        <thead>
            <tr>
                <th><fmt:message key="patientsTable.column.ownerName"/></th>
                <th><fmt:message key="patientsTable.column.petName"/></th>
                <th><fmt:message key="patientsTable.column.petSpecies"/></th>
            </tr>
        </thead>
        <c:forEach var="patient" items="${patients_table}">
            <tr>
                <td>${patient.ownerName}</td>
                <td>${patient.petName}</td>
                <td>${patient.petSpecies}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>