<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title>Patients Table</title>
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
        <div class="container-fluid">
            <div class="row">
                <form action="./addPatient" id="addPatient" method="post"></form>
                <form action="./patientsTableController" id="extraFeatures" method="get"></form>
                <p>
                    <fmt:message key="patientsTable.button.add.patient" var="buttonAddPatient"/>
                    <button type="submit" class="btn btn-black" form="addPatient"/>${buttonAddPatient}</button>
                    <fmt:message key="patientsTable.button.extraFeatures" var="buttonExtraFeatures"/>
                    <button type="submit" name="buttonExtraFeatures" class="btn btn-black" form="extraFeatures"/>${buttonExtraFeatures}</button>
                </p>
            </div>
            <div class="row">
                <c:if test="${not empty success_message_patients_table}">
                    <div class="alert alert-success">
                        <fmt:message key="${success_message_patients_table}"/>
                    </div>
                </c:if>
            </div>
            <div class="row">
                <c:choose>
                    <c:when test="${empty patients_table}">
                        <c:if test="${not empty error_messages_patients_table.patientsTable}">
                            <span class="alert alert-danger">
                                <fmt:message key="${error_messages_patients_table.patientsTable}"/>
                            </span>
                        </c:if>
                        <br/>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-hover">
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
                                    <c:if test="${not empty extraFeaturesPatients and extraFeaturesPatients eq true}">
                                        <td>
                                            <form action="./deletePatientController" method="post">
                                                <fmt:message key="patientsTable.button.delete.patient" var="buttonDeletePatient"/>
                                                <button type="submit" class="btn btn-black" name="patientsId" value="${patient.id}"/>${buttonDeletePatient}</button>
                                            </form>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>
</html>