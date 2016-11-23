<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Add clinical record</title>
</head>
<body>
    <c:import url="/navigationBar"/>

    <form action="/addClinicalRecordController" method="post">
        <select name="selectedDoctorId" required>
            <option value="" disabled selected hidden><fmt:message key="addClinicalRecord.list.placeholder.doctor"/></option>
            <c:forEach var="doctor" items="${doctors_table}">
                <option value=${doctor.id}>${doctor.firstName} ${doctor.lastName}, ${doctor.specialization}</option>
            </c:forEach>
        </select>
        <br/>
        <br/>

        <select name="selectedPatientId" required>
            <option value="" disabled selected hidden><fmt:message key="addClinicalRecord.list.placeholder.patient"/></option>
            <c:forEach var="patient" items="${patients_table}">
                <option value=${patient.id}>${patient.ownerName}, ${patient.petSpecies} ${patient.petName}</option>
            </c:forEach>
        </select>
        <br/>
        <br/>

        <label for="prescription"><fmt:message key="addClinicalRecord.label.prescription"/>:</label>
        <input type="text" id="prescription" name="prescription" required>
        <br/>

        <fmt:message key="addClinicalRecord.button.send" var="buttonAddClinicalRecord"/>
        <input type="submit"  value="${buttonAddClinicalRecord}" />
    </form>
    <form action="/clinicalRecordsTable">
        <fmt:message key="addClinicalRecord.button.goBack" var="buttonGoBack"/>
        <input type="submit"  value="${buttonGoBack}" />
    </form>
</body>
</html>