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
    <form>
        <select id="language" name="language" onchange="submit()">
            <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>EN</option>
            <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>РУ</option>
        </select>
    </form>
    <form action="/signInController" method="post">
        <label for="username"><fmt:message key="signIn.label.username"/>:</label>
        <br/>
        <input type="text" id="username" name="username" value=""/>
        <label style="color:red">${sessionScope["error_messages_sign_in"]["username"]}</label>
        <br/>
        <label for="password"><fmt:message key="signIn.label.password"/>:</label>
        <br/>
        <input type="password" id="password" name="password" value=""/>
        <label style="color:red">${sessionScope["error_messages_sign_in"]["password"]}</label>
        <br/>
        <fmt:message key="signIn.button.send" var="buttonSend"/>
        <input type="submit" value="${buttonSend}" />
        <br/>
        <label style="color:red">${sessionScope["error_messages_sign_in"]["signIn"]}</label>
    </form>
    <form action="/register" method="post">
        <fmt:message key="signIn.button.register" var="buttonRegister"/>
        <input type="submit" value="${buttonRegister}" />
        <br/>
    </form>
</body>
</html>