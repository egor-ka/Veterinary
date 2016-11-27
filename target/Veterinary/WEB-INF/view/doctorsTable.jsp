<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Doctors table</title>
</head>
<body>
    <c:import url="/navigationBar"/>
    <form action="./addDoctor" method="post">
        <fmt:message key="doctorsTable.button.add.doctor" var="buttonAddDoctor"/>
        <input type="submit" value="${buttonAddDoctor}" />
    </form>
    <form action="./doctorsTableController" method="post">
        <fmt:message key="doctorsTable.button.extraFeatures" var="buttonExtraFeatures"/>
        <input type="submit" name="buttonExtraFeatures" value="${buttonExtraFeatures}"/>
    </form>
    <c:choose>
        <c:when test="${empty doctors_table}">
            <label style="color:red">${sessionScope["error_messages_doctors_table"]["doctorsTable"]}</label>
            <br/>
        </c:when>
        <c:otherwise>
            <table border="1">
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
                                    <button type="submit" name="doctorId" value="${doctor.id}"/>${buttonDeleteDoctor}</button>
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