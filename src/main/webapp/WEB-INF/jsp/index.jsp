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
<br>
<br>
<br>
<ul>
<%--    <li><a href="/today"> логи за сегодня </a></li>--%>
    <c:forEach items="${dates}" var="date">
        <a href="<c:out value="${date.toString()}"/>">
            <li><c:out value="${date.toString()}"/></li>
        </a>
    </c:forEach>
</ul>
</table>
<footer>
</footer>
</body>
</html>
