<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title>Add clinical record</title>
    <style>
        <%@include file="../css/bootstrap.min.css"%>
    </style>
    <style>
        <%@include file="../css/styles.css"%>
    </style>
</head>
<body>
    <div class="container-fluid">
        <c:import url="/navigationBar"/>
        <div class="container">
            <form action="./addClinicalRecordController" id="addClinicalRecord" method="post">
                <div class="form-group">
                    <select class="form-control" name="selectedDoctorId" class="selectList" required>
                        <option value="" disabled selected hidden><fmt:message key="addClinicalRecord.list.placeholder.doctor"/></option>
                        <c:forEach var="doctor" items="${doctors_table}">
                            <option value=${doctor.id}>${doctor.firstName} ${doctor.lastName}, ${doctor.specialization}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <select class="form-control" name="selectedPatientId" class="selectList" required>
                        <option value="" disabled selected hidden><fmt:message key="addClinicalRecord.list.placeholder.patient"/></option>
                        <c:forEach var="patient" items="${patients_table}">
                            <option value=${patient.id}>${patient.ownerName}, ${patient.petSpecies} ${patient.petName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="prescription"><fmt:message key="addClinicalRecord.label.prescription"/>:</label>
                    <input type="text" class="form-control" id="prescription" name="prescription" required>
                </div>
            </form>
            <form action="./clinicalRecordsTableController" id="goBack" method="post"></form>
            <p>
                <fmt:message key="addClinicalRecord.button.send" var="buttonAddClinicalRecord"/>
                <button type="submit" class="btn btn-black" form="addClinicalRecord"/>${buttonAddClinicalRecord}</button>
                <fmt:message key="addClinicalRecord.button.goBack" var="buttonGoBack"/>
                <button type="submit" class="btn btn-black" form="goBack"/>${buttonGoBack}</button>
            </p>
        </div>
    </div>
</body>
</html>