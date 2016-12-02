<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<div class="navbar navbar-inverse">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li style="margin-top: 7.5px">
                <form>
                    <select class="form-control" id="language" name="language" onchange="submit()">
                        <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>EN</option>
                        <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>РУ</option>
                    </select>
                </form>
            </li>
            <li>
                <a href="./doctorsTableController">
                    <fmt:message key="navBar.button.show.table.doctors"/>
                </a>
            </li>
            <li>
                <a href="./patientsTableController">
                    <fmt:message key="navBar.button.show.table.patients"/>
                </a>
            </li>
            <li>
                <a href="./clinicalRecordsTableController">
                    <fmt:message key="navBar.button.show.table.clinicalRecords"/>
                </a>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="./logOutController">
                    <fmt:message key="navBar.button.logout"/>
                </a>
            </li>
        </ul>
    </div>
</div>
