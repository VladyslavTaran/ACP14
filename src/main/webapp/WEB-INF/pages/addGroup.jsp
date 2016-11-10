<%@ page import="model.Group" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<% Group transferred = (Group) request.getAttribute("group");%>
<div class="container">
    <h1>Input group details</h1>

    <form method="post" action="addGroup">
        <ul>
            <li>Input group name:
                <input name="name" type="text">
            </li>
            <li>Input whether group is active:
                <input name="active" type="text">
            </li>
            <li>Submit
                <input type="submit">
            </li>
        </ul>
    </form>
</div>

</body>
</html>
