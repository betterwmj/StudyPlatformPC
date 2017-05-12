import {app} from "../../bootstrap.js"
export let name = "commonDialog";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/commonDialog/commonDialog.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller],
    bindings:{
      resolve: '<',
      close: '&',
      dismiss: '&'
    }
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
  let vm = this;
  vm.title = "提示信息";
  vm.content = "";
  vm.$onInit = async function(){
    vm.content = vm.resolve.content;
  };
  vm.ok = function () {
    vm.close();
  };

  vm.cancel = function () {
    vm.dismiss();
  };
}