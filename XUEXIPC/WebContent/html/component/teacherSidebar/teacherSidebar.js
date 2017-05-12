import {app} from "../../bootstrap.js"
export let name = "teacherSidebar";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/teacherSidebar/teacherSidebar.html",
    controller:["$scope","$rootScope","$element","$state",'$cookies','$transitions',"http",controller]
  });
}
function controller($scope,$rootScope,$element,$state,$cookies,$transitions,http){
  let vm = this;
  let topRouter = [
	    {
	        name:"出试卷",
	        url:"teacher.paperCreate",  
	    },
	    {
	        name:"布置作业",
	        url:"teacher.homeworkCreate",
	        
	    },
	    {
	        name:"答疑",
	        url:"teacher.onlineanswer",
	       
	    },
	    {
	        name:"个人中心",
	        url:"teacher.teacherManagerClassCreate",
	       
	    },
	
	];
	 vm.$onInit = function(){
		  vm.routers = topRouter;
		 
	 }
}