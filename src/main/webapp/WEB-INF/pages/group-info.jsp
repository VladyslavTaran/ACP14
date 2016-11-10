<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Group info</title>
</head>
<body>

<c:set var="transferred" value="${group}"/>

<div class="container">
    <ul>
        <li>
            <div class="column">
                name : ${transferred.name}
            </div>
        </li>
        <li>
            <div class="column">
                active : ${transferred.active}
            </div>
        </li>
    </ul>
</div>
</body>
</html>