<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title>Add doctor</title>
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
            <form action="./addDoctorController" id="addDoctor" method="post">
                <div class="form-group">
                    <label for="firstName"><fmt:message key="addDoctor.label.firstName"/>:</label>
                    <input type="text" class="form-control" id="firstName" name="firstName" required>
                </div>
                <div class="form-group">
                    <label for="lastName"><fmt:message key="addDoctor.label.lastName"/>:</label>
                    <input type="text" class="form-control" id="lastName" name="lastName" required>
                </div>
                <div class="form-group">
                    <label for="specialization"><fmt:message key="addDoctor.label.specialization"/>:</label>
                    <input type="text" class="form-control" id="specialization" name="specialization" required>
                </div>
            </form>
            <form action="./doctorsTable" id="goBack"></form>
            <p>
                <fmt:message key="addDoctor.button.send" var="buttonAddDoctor"/>
                <button type="submit" class="btn btn-black" form="addDoctor"/>${buttonAddDoctor}</button>
                <fmt:message key="addDoctor.button.goBack" var="buttonGoBack"/>
                <button type="submit" class="btn btn-black" form="goBack"/>${buttonGoBack}</button>
            </p>
        </div>
    </div>
</body>
</html>