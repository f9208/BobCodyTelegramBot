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

логи за сегодня
<table border="1" cellspacing="0" cellpadding="2">
    <tr>
        <td>Дата-время</td>
        <td>автор</td>
        <td>текст</td>
    </tr>
    <c:forEach var="m" items="${message}">
        <tr>
            <td><c:out value="${m.dateTime}"/></td>
            <td><c:out value="${m.guest.firstName}"/></td>
            <td><c:out value="${m.textMessage}"/></td>
        </tr>

    </c:forEach>

</table>
<footer>
</footer>
</body>
</html>
