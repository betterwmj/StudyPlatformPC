import {app} from "../../bootstrap.js"
export let name = "adminSidebar";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/adminSidebar/adminSidebar.html",
    controller:["$scope","$rootScope","$element","$state",'$cookies','$transitions',"http",controller]
  });
}
function controller($scope,$rootScope,$element,$state,$cookies,$transitions,http){
  let vm = this;
 

  vm.$onInit = function(){
	  
	 
  }
}