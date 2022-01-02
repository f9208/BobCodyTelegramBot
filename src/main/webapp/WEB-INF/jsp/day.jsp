<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css">
    <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
</head>
<body>
<header>
</header>
<a href="/">на главную</a>
<br>
<br>
<br>
<c:set var="currentChatId" value="${currentChatId}"/>

<c:if test="${label}">
    логи мейна за ${messages.get(0).dateTime.toLocalDate()}
</c:if>
<c:if test="${!label}">
    логи для чата ${currentChatId} за ${messages.get(0).dateTime.toLocalDate()}
</c:if>
<table border="1" cellspacing="0" cellpadding="2">
    <tr>
        <td>Время</td>
        <td>чат</td>
        <td>автор</td>
        <td>текст</td>
    </tr>
    <c:forEach var="m" items="${messages}">
        <tr>
            <td><c:out value="${m.dateTime.toLocalTime()}"/></td>
            <td> ${m.chat.id} </td>
            <td><c:out value="${m.guest.firstName}"/></td>
            <td><c:out value="${m.textMessage}"/></td>
        </tr>
    </c:forEach>
</table>
<footer>
</footer>
</body>
</html>