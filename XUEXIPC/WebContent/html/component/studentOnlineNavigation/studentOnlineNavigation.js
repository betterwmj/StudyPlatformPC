import {app} from "../../bootstrap.js"
export let name = "studentOnlineNavigation";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/studentOnlineNavigation/studentOnlineNavigation.html",
    controller:["$scope","$element","$state",'$cookies',"http",controller]
  });
}
function controller($scope,$element,$state,$cookies,http){
  let vm = this;
}