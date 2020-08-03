<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login</title>
</head>
<body>
<h1>regis</h1>

    <form action="${pageContext.request.contextPath}/user/regis" method="post">
  用户名：   <input type="text" name="username"> <br>
        密码：<input type="text" name="password">   <br>
        <input type="submit" value="注册">
    </form>
</body>
</html>