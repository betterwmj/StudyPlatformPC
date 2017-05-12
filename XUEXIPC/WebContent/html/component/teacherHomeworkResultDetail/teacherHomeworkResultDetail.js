import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "teacherHomeworkResultDetail";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/teacherHomeworkResultDetail/teacherHomeworkResultDetail.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
  let vm = this;
  vm.homework = null;
  vm.homeworkResultDetail = [];
  vm.$onInit = async function(){
    try {
      vm.homework = {
        homeworkID:$stateParams.homeworkId,
        homeWorkName:$stateParams.homeWorkName,
      };
      vm.homeworkResultDetail = await http.get("GetHomeworkResultDetail",{
        homeworkResultID:$stateParams.homeworkResultID,
      });
    } catch (error) {
      showErrMsg("获取学生作业结果详情异常");
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