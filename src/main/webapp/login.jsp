<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login</title>
</head>
<body>
<h1>login</h1>
<h1>hello world</h1>



    <form action="${pageContext.request.contextPath}/user/login" method="post">
  用户名：   <input type="text" name="username"> <br>
        密码：<input type="text" name="password">   <br>
       输入验证码： <input type="text" name="code"><img src="${pageContext.request.contextPath}/user/getImage" alt="" ><br>
        <input type="submit" value="login">
    </form>
</body>
</html>