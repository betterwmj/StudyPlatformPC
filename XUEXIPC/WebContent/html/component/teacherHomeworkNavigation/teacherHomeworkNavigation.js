import {app} from "../../bootstrap.js"
export let name = "teacherHomeworkNavigation";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/teacherHomeworkNavigation/teacherHomeworkNavigation.html",
    controller:["$scope","$element","$state",'$cookies',"http",controller]
  });
}
function controller($scope,$element,$state,$cookies,http){
  let vm = this;

  vm.$onInit = function(){
  
  }
}