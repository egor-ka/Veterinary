<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title>Add patient</title>
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
            <form action="./addPatientController" id="addPatient" method="post">
                <div class="form-group">
                    <label for="ownerName"><fmt:message key="addPatient.label.ownerName"/>:</label>
                    <input type="text" class="form-control" id="ownerName" name="ownerName" required>
                </div>
                <div class="form-group">
                    <label for="petName"><fmt:message key="addPatient.label.petName"/>:</label>
                    <input type="text" class="form-control" id="petName" name="petName" required>
                </div>
                <div class="form-group">
                    <label for="petSpecies"><fmt:message key="addPatient.label.petSpecies"/>:</label>
                    <input type="text" class="form-control" id="petSpecies" name="petSpecies" required>
                </div>
            </form>
            <form action="./patientsTableController" id="goBack" method="post"></form>
            <p>
                <fmt:message key="addPatient.button.send" var="buttonAddPatient"/>
                <button type="submit" class="btn btn-black" form="addPatient"/>${buttonAddPatient}</button>
                <fmt:message key="addPatient.button.goBack" var="buttonGoBack"/>
                <button type="submit" class="btn btn-black" form="goBack"/>${buttonGoBack}</button>
            </p>
        </div>
    </div>
</body>
</html>