import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "teacherManagerClassCreate";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/teacherManagerClassCreate/teacherManagerClassCreate.html",
    controller:["$scope","$element","$state",'$cookies',"http",controller]
  });
}
function controller($scope,$element,$state,$cookies,http){
  let vm = this;
  vm.classInfo = {  
    className:"",
  };
  vm.msg = "";
  vm.$onInit = async function(){
    try {
      vm.userInfo = $cookies.getObject("userInfo");
      vm.subjectlist = await http.get("GetAllSubject");
      if( vm.subjectlist.length > 0 ){
        vm.currentSubject = vm.subjectlist[0];
      }
  	  let classes = await http.get("GetTeacherClasses");
	  vm.classes = classes;
    } catch (error) {
      showErrMsg("加载数据异常异常");
    }
  }
  vm.create = async function(){
    try {
      let result = await http.get("CreateClass",{
        className:vm.classInfo.className,
        subjectID:vm.currentSubject.SubjectID
      });
      if( result === true ){
        showErrMsg("班级创建成功");
        let classes = await http.get("GetTeacherClasses");
  	    vm.classes = classes;
      }else{
        showErrMsg("该班级已存在");
      }
    } catch (error) {
      showErrMsg("班级创建失败");
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