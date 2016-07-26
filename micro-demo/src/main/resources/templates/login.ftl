<html>
<head>
<@macro.header />
</head>
<body>

<#if loginResult?exists>
<div class="error">${loginResult.msg!""}</div>
</#if>
<form action="" method="post">
    用户名：<input type="text" name="username" value="<@shiro.principal/>"><br/>
    密码：<input type="password" name="password"><br/>
    自动登录：<input type="checkbox" name="rememberMe" value="true"><br/>
    <input type="submit" value="登录">
</form>

</body>
</html>