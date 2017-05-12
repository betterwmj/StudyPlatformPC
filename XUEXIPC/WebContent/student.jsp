<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta  charset="utf-8" />
	<title>无标题文档</title>
	<script src="/XUEXI/js/angular.js"></script>
	<script src="/XUEXI/js/jquery-3.2.0.min.js"></script>
	<link href="/XUEXI/style/teacherpage.css" rel="stylesheet" type="text/css" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body ng-app="app" ng-controller="studentController" ng-init="init()">
	 <div id="container"><!--页面层容器-->
	   <jsp:include page="studenthead.jsp"/>
	 </div>   
</body>

</html>