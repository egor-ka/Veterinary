<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title>Clinical Records Table</title>
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
                <form action="./loadPatientsAndDoctorsTablesController" id="addClinicalRecord"></form>
                <form action="./clinicalRecordsTableController" id="extraFeatures" method="post"></form>
                <p>
                    <fmt:message key="clinicalRecordsTable.button.add.clinicalRecord" var="buttonAddClinicalRecord"/>
                    <button type="submit" class="btn btn-black" form="addClinicalRecord"/>${buttonAddClinicalRecord}</button>
                    <fmt:message key="clinicalRecordsTable.button.extraFeatures" var="buttonExtraFeatures"/>
                    <button type="submit" name="extraFeaturesName" class="btn btn-black" form="extraFeatures" value="${extraFeaturesClinicalRecords}"/>${buttonExtraFeatures}</button>
                </p>
            </div>
            <div class="row">
                <c:if test="${not empty error_messages_clinical_records_table.doctorsTable}">
                    <div class="alert alert-danger">
                        <fmt:message key="${error_messages_clinical_records_table.doctorsTable}"/>
                    </div>
                </c:if>
                <c:if test="${not empty error_messages_clinical_records_table.patientsTable}">
                    <div class="alert alert-danger">
                        <fmt:message key="${error_messages_clinical_records_table.patientsTable}"/>
                    </div>
                </c:if>
                <c:if test="${not empty error_messages_clinical_records_table.clinicalRecordsTable}">
                    <div class="alert alert-danger">
                        <fmt:message key="${error_messages_clinical_records_table.clinicalRecordsTable}"/>
                    </div>
                </c:if>
                <c:if test="${not empty success_message_clinical_records_table}">
                    <div class="alert alert-success">
                        <fmt:message key="${success_message_clinical_records_table}"/>
                    </div>
                </c:if>
            </div>
            <div class="row">
                <c:if test="${not empty clinical_records_table}">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th><fmt:message key="clinicalRecordsTable.column.doctor.name"/></th>
                                <th><fmt:message key="clinicalRecordsTable.column.doctor.specialization"/></th>
                                <th><fmt:message key="clinicalRecordsTable.column.patient.petSpecies"/></th>
                                <th><fmt:message key="clinicalRecordsTable.column.patient.petName"/></th>
                                <th><fmt:message key="clinicalRecordsTable.column.patient.ownerName"/></th>
                                <th><fmt:message key="clinicalRecordsTable.column.prescription"/></th>
                            </tr>
                        </thead>
                        <c:forEach var="clinicalRecord" items="${clinical_records_table}">
                            <tr>
                                <c:forEach var="column" items="${clinicalRecord}" varStatus="counter">
                                    <c:choose>
                                        <c:when test="${counter.count == 1}">
                                            <c:set var="id" value="${column}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${column}</td>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${not empty extraFeaturesClinicalRecords and extraFeaturesClinicalRecords eq true}">
                                    <td>
                                        <form action="./changeClinicalRecord" method="post">
                                            <fmt:message key="clinicalRecordsTable.button.change.clinicalRecord" var="buttonChangeClinicalRecord"/>
                                            <button type="submit" class="btn btn-black" name="clinicalRecordIdChange" value="${id}"/>${buttonChangeClinicalRecord}</button>
                                        </form>
                                    </td>
                                    <td>
                                        <form action="./deleteClinicalRecordController" method="post">
                                            <fmt:message key="clinicalRecordsTable.button.delete.clinicalRecord" var="buttonDeleteClinicalRecord"/>
                                            <button type="submit" class="btn btn-black" name="clinicalRecordIdDelete" value="${id}"/>${buttonDeleteClinicalRecord}</button>
                                        </form>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>