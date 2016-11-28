<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Registration</title>
</head>
<body>
    <c:import url="/languageBar"/>

    <form action="./registrationController" method="post">
        <label for="firstName"><fmt:message key="registration.label.firstName"/>:</label>
        <br/>
        <input type="text" id="firstName" name="firstName" value=""/>
        <label style="color:red">
            <c:if test="${not empty sessionScope['error_messages_registration']['firstName']}">
                <fmt:message key="${sessionScope['error_messages_registration']['firstName']}"/>
            </c:if>
        </label>
        <br/>

        <label for="lastName"><fmt:message key="registration.label.lastName"/>:</label>
        <br/>
        <input type="text" id="lastName" name="lastName" value=""/>
        <label style="color:red">
            <c:if test="${not empty sessionScope['error_messages_registration']['lastName']}">
                <fmt:message key="${sessionScope['error_messages_registration']['lastName']}"/>
            </c:if>
        </label>
        <br/>

        <label for="username"><fmt:message key="registration.label.username"/>:</label>
        <br/>
        <input type="text" id="username" name="username" value=""/>
        <label style="color:red">
            <c:if test="${not empty sessionScope['error_messages_registration']['username']}">
                <fmt:message key="${sessionScope['error_messages_registration']['username']}"/>
            </c:if>
        </label>
        <br/>

        <label for="password"><fmt:message key="registration.label.password"/>:</label>
        <br/>
        <input type="password" id="password" name="password" value=""/>
        <label style="color:red">
            <c:if test="${not empty sessionScope['error_messages_registration']['password']}">
                <fmt:message key="${sessionScope['error_messages_registration']['password']}"/>
            </c:if>
        </label>
        <br/>

        <label for="checkPassword"><fmt:message key="registration.label.checkPassword"/>:</label>
        <br/>
        <input type="password" id="checkPassword" name="checkPassword" value=""/>
        <label style="color:red">
            <c:if test="${not empty sessionScope['error_messages_registration']['checkPassword']}">
                <fmt:message key="${sessionScope['error_messages_registration']['checkPassword']}"/>
            </c:if>
        </label>
        <br/>
        <fmt:message key="registration.button.send" var="buttonSend"/>
        <input type="submit" name="submit" value="${buttonSend}" />
    </form>
    <form action="./signIn">
        <fmt:message key="registration.button.goBack" var="buttonGoBack"/>
        <input type="submit"  value="${buttonGoBack}" />
    </form>
</body>
</html>