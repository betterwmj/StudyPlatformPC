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
<body ng-app="app" ng-controller="teacherController">
	 <div id="container"><!--页面层容器-->
	   <jsp:include page="head.jsp"/>
	 </div>   
	<div id="PageBody"><!--页面主体-->
		　<div id="Sidebar"><!--侧边栏-->
		<button name="choice" style="background-color:transparent;border:0 ; " ng-click="getTitle('选择题')"><font color="red"> 选择题</font></button>
		<button name="trueorfalse" style="background-color:transparent;border:0 ; " ng-click="getTitle('判断题')"><font color="red">判断题</font></button>
		</div>　　　
	</div>
	<select ng-model="currSubject">
		<option ng-repeat="item in subjects">{{item.SubjectName}}</option>
	</select>
	<div></div>
	<div ng-if="currType === '选择题'">
		<div ng-repeat="item in titles">
		  {{item.title}} <input type="checkbox" ng-model="item.isChecked" ng-click="putTitle($event,item)"><br>
		  A {{item.optionA}}
		  B {{item.optionB}}
		  C {{item.optionC}}
		  D {{item.optionD}}
		  
		</div>
	</div>
	<div ng-if="currType === '判断题'">
		<div ng-repeat="item in titles">
		  {{$index+1}}
		  {{item.title}}
		  {{item.answer}}<br>
		  <input type="checkbox" ng-model="item.isChecked" ng-click="putTitle($event,item)">
		</div>
	</div>
	<hr>
	选择的题目,共{{selectedTitle.length}}道题,试卷名称:<input type="text" ng-model="paperTitle">,共{{score}}分
	<div ng-repeat="item in selectedTitle">
		类型:{{item.type}}
		题目:{{item.title}}
	</div>
	<button ng-click="submitPaper()">提交试卷</button>
  <script>
    var app = angular.module("app",[]);
    app.controller("teacherController",["$scope","$http",function($scope,$http){
    	$scope.paperTitle = "";
    	$scope.subjects = [];
    	$scope.currSubject = null;
    	$scope.titles = [];
    	$scope.currType = "";
    	$scope.selectedTitle = [];
    	$scope.score = 0;
    	$http({
    		url:"/XUEXI/GetAllSubject",method:"GET"
    	}).then(function(response){
    		$scope.subjects = response.data;
    		$scope.currSubject = $scope.subjects[0];
    	},function(response){
    		
    	});
    	$scope.$watch("selectedTitle",function(){
    		var score = 0;
    		$scope.selectedTitle.forEach( function(item){
    			if(item.type === "判断题"){
    				score+=6;
    			}else if(item.type === "选择题"){
    				score+=8;
    			}
    		});
    		$scope.score = score;
    	},true);
    	$scope.getTitle = function(type){
    		$scope.currType = type;
    		$http({
        		url:"/XUEXI/GetTitle",method:"GET",params:{
        			"subject":$scope.currSubject,
        			"type":type
        		}
        	}).then(function(response){
        		$scope.titles = response.data;
        		$scope.titles.forEach(function(item){
        			item.isChecked = false;
        		});
        		mergeData();
        	},function(response){
        		
        	});
    	}
    	/**
    	*添加或者移除一个题目
    	*/
    	$scope.putTitle = function($event,title){
    		if( $event.target.checked === true){
    			var data = {};
        		angular.copy(title, data);
        		data.type = $scope.currType;
        		$scope.selectedTitle.push(data);
    		}else{
    			$scope.selectedTitle = $scope.selectedTitle.filter(function(item){
    				return item.title !== title.title;
    			});
    		}
    	}
    	
    	$scope.submitPaper = function(){
    		if($scope.paperTitle === ""){
    			window.alert("请填写试卷名称");
    			return;
    		}
    		if($scope.score !== 100){
    			window.alert("请确保试卷总分为100分");
    			return;
    		}
    		var paper = {
  				papername:$scope.paperTitle,
  				papertitles:[]
    		};
    		var papertitles = [];
    		angular.copy($scope.selectedTitle, papertitles);
    		paper.papertitles = papertitles;
    		$http({
    			url:"/XUEXI/InsertTestPaper",method:"POST",data:paper
    		}).then(function(response){
    			if( response.data === "true" ){
    				window.alert("添加试卷成功");
    			}
    		},function(error){
    			window.alert(error)
    		});
    	}
    	//重新选中已经选择过的题目
    	function mergeData(){
    		$scope.selectedTitle.forEach(function(item){
    			var find = $scope.titles.find( function(oneTitle){
    				return oneTitle.title === item.title;
    			});
    			if(find){
    				find.isChecked = true;	
    			}
    		});
    	}
    }]);
  </script>


</body>
</html>