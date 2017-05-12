<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>注册 - 我爱学习网</title>
	<link rel="stylesheet" type="text/css" href="style/register-login.css">	
</head>
 <script  language="JavaScript" type="text/JavaScript">
        function changeBody(index){
            switch(index){
                case 1:{
                    document.getElementById('mainx1').style.display = "";
                    document.getElementById('mainx2').style.display = "none";
                    break;
                }
                case 2:{
                    document.getElementById('mainx1').style.display = "none";
                    document.getElementById('mainx2').style.display = "";
                    break;
                }
                  
            }
        }
    </script>
<body>
<div id="box"></div>
<div class="cent-box register-box">
	<div class="cent-box-header">
		<h1 class="main-title hide">我爱学习网</h1>
		<h2 class="sub-title">和同学老师们一起分享学习的快乐</h2>
	</div>
	
		<div class="index-tab">
			<div class="index-slide-nav">
				<a href="login.html">登录</a>
				<a href="register.jsp" class="active">注册</a>
				<div class="slide-bar slide-bar1"></div>				
			</div>
		</div>
		    <div align="center">
		    <a href="#" onclick="changeBody(1)">学生</a>
	        <a href="#" onclick="changeBody(2)">老师</a>
	        </div>
			<div class="group" id="mainx1" style="display:"" ">
				<iframe src="studentRegister.jsp" width="300" height="550"  frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>
			</div>
			<div class="group" id="mainx2" style="display: none">
				
				<iframe src="teacherRegister.jsp" width="300" height="550"  frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>				
			</div>       
  </div>
<div class="footer">
	<p>I LOVE STUDY 2017</p>
</div>
</body>
</html>