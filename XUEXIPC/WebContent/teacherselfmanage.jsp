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
<body ng-app="app" ng-controller="teacherController" ng-init="init()">
	 <div id="container"><!--页面层容器-->
	   <jsp:include page="head.jsp"/>
	 </div>
	 班级名称:<input type="text" ng-model="className"> <button ng-click="createClass()">创建班级</button>
	 <hr>
	 <div ng-repeat="item in classes">
	 	班级名称:{{item.className}} <button ng-click="distributionClass(item)">添加学生</button> <button ng-click="showClassStudents(item)">查看学生</button>
	 </div>
	 <div ng-repeat="item in students">
	 	学生姓名:{{item.name}}  <input type="checkbox" ng-model="item.isChecked">
	 </div>
	 
	 <div ng-show="selectClass !== null">
	 	<hr>
	 	班级名称:{{selectClass.className}}
	 	<div ng-repeat="std in selectClass.students">
	 		id:{{std.userID}}, name:{{std.name}}
	 	</div>
	 </div>
</body>
<script>
var app = angular.module("app",[]);
app.controller("teacherController",["$scope","$http",function($scope,$http){
	$scope.className = "";
	$scope.students = [];
	$scope.classes = [];
	$scope.selectClass = null;
	$scope.init = function(){
		$http({
    		url:"/XUEXI/GetStudent",method:"GET",
    	}).then(function(response){
    		$scope.students = response.data;
    		getClasses();
    	},function(response){
    		window.alert(response);
    	});
	};
	$scope.createClass = function(){
		if( $scope.className === "" ){
			window.alert("请输入班级名称");
			return;
		}
		$http({
    		url:"/XUEXI/CreateClass",method:"GET",params:{"className":$scope.className}
    	}).then(function(response){
    		if( response.data === "true"){
    			window.alert("添加班级成功");
    		}else{
    			window.alert("该班级已存在");
    		}
    		
    	},function(response){
    		window.alert(response);
    	});
	};
	
	$scope.distributionClass = function(item){
		var data = {
			classId:"",
			studentIds:[]
		};
		data.classId = item.classId;
		$scope.students.forEach( function(std){
			if( std.isChecked){
				data.studentIds.push(std.userID);	
			}
		});
		if( data.studentIds.length === 0 ){
			window.alert("请选择学生");
			return;
		}
		$http({
    		url:"/XUEXI/UpdateStudentClassId",method:"POST",data:data
    	}).then(function(response){
    		if( response.data === "true"){
    			$http({
    	    		url:"/XUEXI/GetStudent",method:"GET",
    	    	}).then(function(response){
    	    		$scope.students = response.data;
    	    	},function(response){
    	    		window.alert(response);
    	    	});
    		}
    	},function(response){
    		window.alert(response);
    	});
	}
	
	$scope.showClassStudents = function(classItem){
		$scope.selectClass = classItem;
		$http({
    		url:"/XUEXI/GetAllClassStudent",method:"GET",params:{classid:classItem.classId}
    	}).then(function(response){
    		$scope.selectClass.students = response.data;
    	},function(response){
    		window.alert(response);
    	});
	}
	function getClasses(){
		$http({
    		url:"/XUEXI/GetClasses",method:"GET",
    	}).then(function(response){
    		$scope.classes = response.data;
    	},function(response){
    		window.alert(response);
    	});
	}
}]);
</script>
</html>