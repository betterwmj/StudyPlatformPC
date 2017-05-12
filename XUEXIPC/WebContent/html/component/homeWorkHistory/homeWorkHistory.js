import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "homeWorkHistory";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/homeWorkHistory/homeWorkHistory.html",
    controller:["$scope","$element","$state",'$cookies',"http",controller]
  });
}
function controller($scope,$element,$state,$cookies,http){
  let vm = this;
  vm.$onInit = async function(){
    try {
      vm.homeworks = await http.get("GetHomework");
      vm.homeworks.forEach( (item)=>{
        if( item.finishTime ){
          item.finishTime = new Date(item.finishTime.time);
        }
      });
    } catch (error) {
    
      showErrMsg("获取作业列表异常");
    }
  }
  function showErrMsg(errMsg) {
	     
      dialog.openDialog({
          type: "warning",
          title: "提示",
          content: errMsg
      });
    
  }

}