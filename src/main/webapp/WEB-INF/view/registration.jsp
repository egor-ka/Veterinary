<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Registration</title>
</head>
<body>
    <form action="/registerController" method="post">
        <label for="firstName"><fmt:message key="register.label.firstName"/>:</label>
        <br/>
        <input type="text" id="firstName" name="firstName" value=""/>
        <label style="color:red">${sessionScope["error_messages_registration"]["firstName"]}</label>
        <br/>

        <label for="lastName"><fmt:message key="register.label.lastName"/>:</label>
        <br/>
        <input type="text" id="lastName" name="lastName" value=""/>
        <label style="color:red">${sessionScope["error_messages_registration"]["lastName"]}</label>
        <br/>

        <label for="username"><fmt:message key="register.label.username"/>:</label>
        <br/>
        <input type="text" id="username" name="username" value=""/>
        <label style="color:red">${sessionScope["error_messages_registration"]["username"]}</label>
        <br/>

        <label for="password"><fmt:message key="register.label.password"/>:</label>
        <br/>
        <input type="password" id="password" name="password" value=""/>
        <label style="color:red">${sessionScope["error_messages_registration"]["password"]}</label>
        <br/>

        <label for="checkPassword"><fmt:message key="register.label.checkPassword"/>:</label>
        <br/>
        <input type="password" id="checkPassword" name="checkPassword" value=""/>
        <br/>
        <fmt:message key="register.button.send" var="buttonSend"/>
        <input type="submit" name="submit" value="${buttonSend}" />
    </form>
    <form action="/signIn">
        <fmt:message key="register.button.goBack" var="buttonGoBack"/>
        <input type="submit"  value="${buttonGoBack}" />
    </form>
</body>
</html>