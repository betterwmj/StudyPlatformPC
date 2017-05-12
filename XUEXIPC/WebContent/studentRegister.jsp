<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="style/register-login.css">	
<title>Insert title here</title>
</head>
<body>
<div id="box"></div>
<div class="cent-box register-box">
    <div class="cont-main clearfix">
  <form action="/XUEXI/servlet/treatUser" method="post" name="form1">
		<div class="login form">
		  <div class="group">
		  <div class="group-ipt user">
					<input type="text" name="user" id="user" class="ipt" placeholder="选择一个用户名" required>
				</div>
				<div class="group-ipt password">
					<input type="password" name="password" id="password" class="ipt" placeholder="设置登录密码" required>
				</div>
				<div class="group-ipt password1">
					<input type="password" name="password1" id="password1" class="ipt" placeholder="重复密码" required>
				</div>
				<div class="group-ipt studentNo">
					<input type="text" name="studentNo" id="studentNo" class="ipt" placeholder="输入学号" required>
				</div>
				<div class="group-ipt school">
					<input type="text" name="school" id="school" class="ipt" placeholder="输入学校" required>
				</div>
				<div class="group-ipt classes">
					<input type="text" name="classes" id="classes" class="ipt" placeholder="输入班级" required>
				</div>
				<div class="group-ipt telephone">
					<input type="text" name="telephone" id="telephone" class="ipt" placeholder="输入手机号码 " required>
				</div>
			</div>
		   </div>
			<div class="button">
			<button type="submit" class="login-btn register-btn" id="button">注册</button>
			<input type="hidden" name="action" value="studentregister">
		</div>
	</form>
	
	</div>
</div>
</body>
</html>