import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "paperDetail";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/paperDetail/paperDetail.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
  let vm = this;
  vm.msg = "";
  vm.paper = {};
  vm.paperDetail = null;
  vm.$onInit = async function(){ 
    vm.paper = {
      testName:$stateParams.testName,
      testpaperID:$stateParams.testpaperID,
    }
    vm.currentPage = $stateParams.currentPage;
	vm.pageItems = $stateParams.pageItems;
	vm.totalItems = $stateParams.totalItems;
    if( vm.paper === null || vm.paper.testpaperID === null ){
      return;
    }
    try {
      vm.paperDetail = await http.get("GetPaperDetail",{
        paperID:vm.paper.testpaperID
      });
     
     
    } catch (error) {
      showErrMsg("获取试卷详情异常");
    }
    //$state.go("teacher.publishPaper",{currentPage:vm.currentPage,pageItems:vm.pageItems,totalItems:vm.totalItems});    
  }
  function showErrMsg(errMsg) {
	     
      dialog.openDialog({
          type: "warning",
          title: "提示",
          content: errMsg
      });
    
  }
}