<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Clinical Records Table</title>
</head>
<body>
    <c:import url="/navigationBar"/>
    <form action="./refreshPatientsAndDoctorsTablesController" method="post">
        <fmt:message key="clinicalRecordsTable.button.add.clinicalRecord" var="buttonAddClinicalRecord"/>
        <input type="submit"  value="${buttonAddClinicalRecord}"/>
    </form>
    <form action="./clinicalRecordsTableController" method="post">
        <fmt:message key="clinicalRecordsTable.button.extraFeatures" var="buttonExtraFeatures"/>
        <input type="submit" name="buttonExtraFeatures" value="${buttonExtraFeatures}"/>
    </form>
    <br/>
    <c:if test="not empty ${sessionScope['error_messages_clinical_records_table']['DoctorsTable']}">
        <label style="color:red">${sessionScope["error_messages_clinical_records_table"]["DoctorsTable"]}</label>
        <br/>
    </c:if>
    <c:if test="not empty ${sessionScope['error_messages_clinical_records_table']['PatientsTable']}">
        <label style="color:red">${sessionScope["error_messages_clinical_records_table"]["PatientsTable"]}</label>
        <br/>
    </c:if>
    <c:choose>
        <c:when test="${empty clinical_records_table}">
            <label style="color:red">${sessionScope["error_messages_clinical_records_table"]["clinicalRecordsTable"]}</label>
        </c:when>
        <c:otherwise>
            <table border="1">
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
                                    <button type="submit" name="clinicalRecordIdChange" value="${id}"/>${buttonChangeClinicalRecord}</button>
                                </form>
                            </td>
                            <td>
                                <form action="./deleteClinicalRecordController" method="post">
                                    <fmt:message key="clinicalRecordsTable.button.delete.clinicalRecord" var="buttonDeleteClinicalRecord"/>
                                    <button type="submit" name="clinicalRecordIdDelete" value="${id}"/>${buttonDeleteClinicalRecord}</button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>