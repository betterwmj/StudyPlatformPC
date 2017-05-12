<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style/register-login.css">	
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
  <div class="cent-box register-box">
    <div class="cont-main clearfix">
    <form action="/XUEXI/servlet/treatUser" method="post" name="form1">
		<div class="login form">
		   <div class="group">
		  <div class="group-ipt user">
					<div class="group-ipt user">
					<input type="text" name="tuser" id="tuser" class="ipt" placeholder="选择一个用户名" required>
				</div>
				<div class="group-ipt password">
					<input type="password" name="tpassword" id="password" class="ipt" placeholder="设置登录密码" required>
				</div>
				<div class="group-ipt password1">
					<input type="password" name="tpassword1" id="password1" class="ipt" placeholder="重复密码" required>
				</div>
				
				<div class="group-ipt Subject">
					<input type="text" name="Subject" id="Subject" class="ipt" placeholder="输入所教学科名 " required>
				</div>
			</div>
		</div>
		</div>
			<div class="button">
			<button type="submit" class="login-btn register-btn" id="button">注册</button>
			<input type="hidden" name="action" value="teacheregister">
		</div>
	</form>  
	 </div>
  </div>
</body>
</html>