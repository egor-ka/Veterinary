<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title>Sign in</title>
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
            <c:if test="${not empty error_messages_sign_in.signIn}">
                <div class="alert alert-danger">
                    <fmt:message key="${error_messages_sign_in.signIn}"/>
                </div>
            </c:if>
            <c:if test="${not empty success_message_sign_in}">
                <div class="alert alert-success">
                    <fmt:message key="${success_message_sign_in}"/>
                </div>
            </c:if>
            <form action="./signInController" id="signIn" method="post">
                <div class="form-group">
                    <label for="username"><fmt:message key="signIn.label.username"/>:</label>
                    <input type="text" class="form-control" id="username" name="username"/>
                    <c:if test="${not empty error_messages_sign_in.username}">
                        <span class="text-danger">
                            <fmt:message key="${error_messages_sign_in.username}"/>
                        </span>
                        <br/>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="signIn.label.password"/>:</label>
                    <input type="password" class="form-control" id="password" name="password"/>
                    <c:if test="${not empty error_messages_sign_in.password}">
                        <span class="text-danger">
                            <fmt:message key="${error_messages_sign_in.password}"/>
                        </span>
                        <br/>
                    </c:if>
                </div>
            </form>
            <form action="./registration" id="registration"></form>
            <p>
                <fmt:message key="signIn.button.send" var="buttonSend"/>
                <button type="submit" class="btn btn-black" form="signIn"/>${buttonSend}</button>
                <fmt:message key="signIn.button.registration" var="buttonRegistration"/>
                <button type="submit" class="btn btn-black" form="registration">${buttonRegistration}</button>
            </p>
        </div>
    </div>
</body>
</html>