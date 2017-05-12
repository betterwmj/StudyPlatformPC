import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "paperResultDetail";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/paperResultDetail/paperResultDetail.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
  let vm = this;
  vm.msg = "";
  vm.paperResult = null;
  vm.paperResultList = [];
  vm.$onInit = async function(){
    vm.paperResult = {
      id:$stateParams.resultId,
      testpaperID:$stateParams.testpaperID,
      testName:$stateParams.testName,
    };
    try {
      vm.paperResultList = await http.get("GetPaperResultDetail",{
        paperResultID:vm.paperResult.id
      });
    } catch (error) {
      showErrMsg("获取试卷作答结果详情异常");
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