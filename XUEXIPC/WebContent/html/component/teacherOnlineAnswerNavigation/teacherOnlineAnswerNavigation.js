import {app} from "../../bootstrap.js"
export let name = "teacherOnlineAnswerNavigation";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/teacherOnlineAnswerNavigation/teacherOnlineAnswerNavigation.html",
    controller:["$scope","$element","$state",'$cookies',"http",controller]
  });
}
function controller($scope,$element,$state,$cookies,http){
  let vm = this;
}