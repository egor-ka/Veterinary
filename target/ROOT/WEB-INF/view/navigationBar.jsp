<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="lang"/>

<table>
    <tr>
        <td>
            <fmt:message key="navBar.label.language"/>
        </td>
        <td>
            <form>
                <select id="language" name="language" onchange="submit()">
                    <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>EN</option>
                    <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>РУ</option>
                </select>
            </form>
        </td>
        <td>
            <form action="/doctorsTableController" method="post">
                <fmt:message key="navBar.button.show.table.doctors" var="buttonShowTableDoctors"/>
                <input type="submit"  value="${buttonShowTableDoctors}"/>
            </form>
        </td>
        <td>
            <form action="/patientsTableController" method="post">
                <fmt:message key="navBar.button.show.table.patients" var="buttonShowTablePatients"/>
                <input type="submit"  value="${buttonShowTablePatients}"/>
            </form>
        </td>
        <td>
            <form action="/clinicalRecordsTableController" method="post">
                <fmt:message key="navBar.button.show.table.clinicalRecords" var="buttonShowTableClinicalRecords"/>
                <input type="submit"  value="${buttonShowTableClinicalRecords}"/>
            </form>
        </td>
        <td>
            <form action="/logOutController" method="post">
                <fmt:message key="navBar.button.logout" var="buttonLogOut"/>
                <input type="submit" value="${buttonLogOut}"/>
            </form>
        <td>
    </tr>
</table>
