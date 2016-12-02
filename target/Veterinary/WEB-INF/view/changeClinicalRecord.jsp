<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title>Change clinical record</title>
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
            <form action="./changeClinicalRecordController" id="change" method="post">
                <div class="form-group">
                    <label for="prescription"><fmt:message key="changeClinicalRecord.label.prescription"/>:</label>
                    <input type="text" class="form-control" id="prescription" name="prescription" required>
                </div>
            </form>
            <form action="./clinicalRecordsTableController" id="goBack" method="post"></form>
            <p>
                <fmt:message key="changeClinicalRecord.button.send" var="buttonChangeClinicalRecord"/>
                <button type="submit" class="btn btn-black" name="clinicalRecordIdChange" value="${param.clinicalRecordIdChange}" form="change"/>${buttonChangeClinicalRecord}</button>
                <fmt:message key="changeClinicalRecord.button.goBack" var="buttonGoBack"/>
                <button type="submit" class="btn btn-black" form="goBack"/>${buttonGoBack}</button>
            </p>
        </div>
    </div>
</body>
</html>