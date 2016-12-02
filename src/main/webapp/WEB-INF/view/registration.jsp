<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <style>
        <%@include file="../css/bootstrap.min.css"%>
    </style>
    <style>
        <%@include file="../css/styles.css"%>
    </style>
</head>
<body>
    <div class="container-fluid">
        <c:import url="/languageBar"/>
        <div class="signIn">
            <form action="./registrationController" id="register" method="post">
                <div class="form-group">
                    <label for="firstName"><fmt:message key="registration.label.firstName"/>:</label>
                    <input type="text" class="form-control" id="firstName" name="firstName"/>
                    <c:if test="${not empty error_messages_registration.firstName}">
                        <span class="text-danger">
                            <fmt:message key="${error_messages_registration.firstName}"/>
                        </span>
                        <br/>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="lastName"><fmt:message key="registration.label.lastName"/>:</label>
                    <input type="text" class="form-control" id="lastName" name="lastName"/>
                    <c:if test="${not empty error_messages_registration.lastName}">
                        <span class="text-danger">
                            <fmt:message key="${error_messages_registration.lastName}"/>
                        </span>
                        <br/>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="username"><fmt:message key="registration.label.username"/>:</label>
                    <input type="text" class="form-control" id="username" name="username"/>
                    <c:if test="${not empty error_messages_registration.username}">
                        <span class="text-danger">
                            <fmt:message key="${error_messages_registration.username}"/>
                        </span>
                        <br/>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="registration.label.password"/>:</label>
                    <input type="password" class="form-control" id="password" name="password"/>
                    <c:if test="${not empty error_messages_registration.password}">
                        <span class="text-danger">
                            <fmt:message key="${error_messages_registration.password}"/>
                        </span>
                        <br/>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="checkPassword"><fmt:message key="registration.label.checkPassword"/>:</label>
                    <input type="password" class="form-control" id="checkPassword" name="checkPassword"/>
                    <span class="text-danger">
                    <c:if test="${not empty error_messages_registration.checkPassword}">
                        <span class="text-danger">
                            <fmt:message key="${error_messages_registration.checkPassword}"/>
                        </span>
                        <br/>
                    </c:if>
                </div>
            </form>
            <form action="./signIn" id="goBack"></form>
            <p>
                <fmt:message key="registration.button.send" var="buttonSend"/>
                <button type="submit" class="btn btn-black" name="submit" form="register"/>${buttonSend}</button>
                <fmt:message key="registration.button.goBack" var="buttonGoBack"/>
                <button type="submit" class="btn btn-black" form="goBack"/>${buttonGoBack}</button>
            </p>
        </div>
    </div>
</body>
</html>