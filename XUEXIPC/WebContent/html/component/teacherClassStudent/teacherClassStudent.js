import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "teacherClassStudent";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/teacherClassStudent/teacherClassStudent.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
  let vm = this;
  vm.classStudent =null;
  let classId=null;
  vm.$onInit = async function(){
      classId = $stateParams.classId;
	  try{
      vm.classStudent = await http.get("GetAllClassStudent",{classId:classId});
    } catch (error) {
      showErrMsg("加载数据异常异常");
    }
  }
  vm.deleteStudent =async function(userId){
      let confirm =await showConfrimMsg("是否删除?");
      if(confirm ==="确定"){
    	  let result =await http.get("updateStudentClass",{
    		  studentId:userId,
    		  classId:classId,
    		  typeId:0
    		  
    	  });
    	  if(result ===true){
    		  showErrMsg("删除成功");
    		  vm.classStudent = await http.get("GetAllClassStudent",{classId:classId});
    	  }else{
    		  showErrMsg("删除失败");
    	  }
      }	  
  }
  function showErrMsg(errMsg) {
	     
      dialog.openDialog({
          type: "warning",
          title: "提示",
          content: errMsg
      });
    
  }
  async function showConfrimMsg(confirmMsg) {
      return await dialog.openDialog({
          type: "normal",
          title: "操作提示",
          content: confirmMsg
      });
  }
}