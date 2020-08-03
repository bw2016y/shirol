<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!doctype html>
<html lang="en">
<head>
        <meta charset="UTF-8">
        <title>test</title>
</head>
<body>

        <h1>index</h1>
        <h1><shiro:principal/></h1>
        <shiro:authenticated>
                <a href="${pageContext.request.contextPath}/user/logout">logout</a>

        </shiro:authenticated>


        <ul>
                <shiro:hasAnyRoles name="user,admin">
                        <li><a href="">user</a>

                                <ul>
                                        <shiro:hasPermission name="user:add:*">
                                                <li><a href=""> add </a></li>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="user:del:*">
                                                <li><a href=""> del </a></li>
                                        </shiro:hasPermission>

                                        <shiro:hasPermission name="product:*:*">
                                                <li><a href=""> retrive </a></li>
                                        </shiro:hasPermission>
                                         <li><a href=""> update </a></li>
                                </ul>



                        </li>

                </shiro:hasAnyRoles>
                 <shiro:hasRole name="admin">
                        <li> <a href="">admin</a></li>
                </shiro:hasRole>
        </ul>
</body>
</html>