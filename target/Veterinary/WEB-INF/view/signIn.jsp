<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Sign in</title>
</head>
<body>
    <c:import url="/languageBar"/>

    <form action="./signInController" method="post">
        <label for="username"><fmt:message key="signIn.label.username"/>:</label>
        <br/>
        <input type="text" id="username" name="username" value=""/>
        <label style="color:red">
            <c:if test="${not empty sessionScope['error_messages_sign_in']['username']}">
                <fmt:message key="${sessionScope['error_messages_sign_in']['username']}"/>
            </c:if>
        </label>
        <br/>
        <label for="password"><fmt:message key="signIn.label.password"/>:</label>
        <br/>
        <input type="password" id="password" name="password" value=""/>
        <label style="color:red">
            <c:if test="${not empty sessionScope['error_messages_sign_in']['password']}">
                <fmt:message key="${sessionScope['error_messages_sign_in']['password']}"/>
            </c:if>
        </label>
        <br/>
        <fmt:message key="signIn.button.send" var="buttonSend"/>
        <input type="submit" value="${buttonSend}" />
        <br/>
        <label style="color:red">
            <c:if test="${not empty sessionScope['error_messages_sign_in']['signIn']}">
                <fmt:message key="${sessionScope['error_messages_sign_in']['signIn']}"/>
            </c:if>
        </label>
    </form>
    <form action="./registration">
        <fmt:message key="signIn.button.registration" var="buttonRegistration"/>
        <input type="submit" value="${buttonRegistration}" />
        <br/>
    </form>
</body>
</html>