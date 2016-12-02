<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title>Doctors table</title>
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
                <form action="./addDoctor" id="addDoctor" method="post"></form>
                <form action="./doctorsTableController" id="extraFeatures" method="post"></form>
                <p>
                    <fmt:message key="doctorsTable.button.add.doctor" var="buttonAddDoctor"/>
                    <button type="submit" class="btn btn-black" form="addDoctor"/>${buttonAddDoctor}</button>
                    <fmt:message key="doctorsTable.button.extraFeatures" var="buttonExtraFeatures"/>
                    <button type="submit" name="extraFeaturesName" class="btn btn-black" form="extraFeatures" value="${extraFeaturesDoctors}"/>${buttonExtraFeatures}</button>
                </p>
            </div>
            <div class="row">
                <c:if test="${not empty success_message_doctors_table}">
                    <div class="alert alert-success">
                        <fmt:message key="${success_message_doctors_table}"/>
                    </div>
                </c:if>
            </div>
            <div class="row">
                <c:choose>
                    <c:when test="${empty doctors_table}">
                        <label style="color:red">
                            <c:if test="${not empty error_messages_doctors_table.doctorsTable}">
                                <fmt:message key="${error_messages_doctors_table.doctorsTable}"/>
                            </c:if>
                        </label>
                        <br/>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th><fmt:message key="doctorsTable.column.firstName"/></th>
                                    <th><fmt:message key="doctorsTable.column.lastName"/></th>
                                    <th><fmt:message key="doctorsTable.column.specialization"/></th>
                                </tr>
                            </thead>
                            <c:forEach var="doctor" items="${doctors_table}">
                                <tr>
                                    <td>${doctor.firstName}</td>
                                    <td>${doctor.lastName}</td>
                                    <td>${doctor.specialization}</td>
                                    <c:if test="${not empty extraFeaturesDoctors and extraFeaturesDoctors eq true}">
                                        <td>
                                            <form action="./deleteDoctorController" method="post">
                                                <fmt:message key="doctorsTable.button.delete.doctor" var="buttonDeleteDoctor"/>
                                                <button type="submit" class="btn btn-black" name="doctorId" value="${doctor.id}"/>${buttonDeleteDoctor}</button>
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












































</body>
</html>