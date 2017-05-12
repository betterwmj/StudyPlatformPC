import {app} from "../../bootstrap.js"
export let name = "studentSidebar";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/studentSidebar/studentSidebar.html",
    controller:["$scope","$rootScope","$element","$state",'$cookies','$transitions',"http",controller]
  });
}
function controller($scope,$rootScope,$element,$state,$cookies,$transitions,http){
  let vm = this;
  let topRouter = [
	    {
	        name:"在线测试",
	        url:"student.test",  
	    },
	    {
	        name:"作业",
	        url:"student.homework",
	        
	    },
	    {
	        name:"答疑",
	        url:"student.postQuestion",
	       
	    },
	    {
	        name:"个人中心",
	        url:"student.center",
	       
	    },
	];

  vm.$onInit = function(){
	  vm.routers = topRouter;
	 
  }
}