import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "studentTestPaperResultDetail";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/studentTestPaperResultDetail/studentTestPaperResultDetail.html",
    controller:["$scope","$element","$state",'$cookies',"$stateParams","http",controller]
  });
}
function controller($scope,$element,$state,$cookies,$stateParams,http){
  let vm = this;
  vm.paper = null;
  vm.resultDetail = [];
  vm.$onInit = async function(){
    try {
      $stateParams.testpaperID = parseInt($stateParams.testpaperID);
      vm.subject = {
        SubjectName:$stateParams.SubjectName,
        SubjectID:$stateParams.SubjectID,
        teacherID :$stateParams.teacherID
      };
  //    let paperList = await http.get("GetPaper",{subjectId:vm.subject.SubjectID});
  //    vm.paper = paperList.find((item)=>{
  //      return item.testpaperID === $stateParams.testpaperID
  //    });
      let paperResultList = await http.get("GetPaperResult");
      vm.paperResult = paperResultList.find((item)=>{
        return item.paper.paperId === $stateParams.testpaperID
      });
      vm.paperResult.paper.time = new Date(vm.paperResult.paper.time.time);
      vm.resultDetail = await http.get("GetPaperResultDetail",{
        paperResultID:$stateParams.paperResultID
      });
    } catch (error) {
      showErrMsg("初始化页面异常");
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