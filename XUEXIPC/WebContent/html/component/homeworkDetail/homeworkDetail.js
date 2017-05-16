import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "homeworkDetail";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/homeworkDetail/homeworkDetail.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
  let vm = this;
  vm.$onInit = async function(){
    try {
      vm.homework = {
        homeWorkName:$stateParams.homeWorkName,
        homeworkId:$stateParams.homeworkId,
      };
      vm.currentPage = $stateParams.currentPage;
  	  vm.pageItems = $stateParams.pageItems;
  	  vm.totalItems = $stateParams.totalItems;
      vm.homeworkDetail = await http.get("GetHomeworkDetail",{
        homeworkID:vm.homework.homeworkId
      });
    } catch (error) {
      showErrMsg("获取作业详情异常");
    }
  };
  function showErrMsg(errMsg) {
	     
      dialog.openDialog({
          type: "warning",
          title: "提示",
          content: errMsg
      });
    
  }

}