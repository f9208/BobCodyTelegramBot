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
посмотреть логи со мной
<br>
<form action="/" method="get">
    <input type="number" name="chatId" id="chatId">
    <input type="submit">
</form>
<br>
<br>
<br>
<c:set var="chatId" value="${currentChatId}"/>

<c:if test="${label}"> Логи мейна </c:if>
<c:if test="${!label}"> Логи с ${currentChatId} </c:if>

<ul>
    <c:forEach items="${dates}" var="date">
        <a href="/${currentChatId}/<c:out value="${date.toString()}"/>">
            <li><c:out value="${date.toString()}"/></li>
        </a>
    </c:forEach>
</ul>
</table>
<footer>
</footer>
</body>
</html>
