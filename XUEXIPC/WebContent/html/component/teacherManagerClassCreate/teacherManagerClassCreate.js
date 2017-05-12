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
      console.log(vm.classInfo.className);
	  if( checkClass(vm.classInfo.className) ===true){
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
     }else{
    	 showErrMsg("班级名不能为空！");
     }
  }
  vm.deleteClass =async function(classId){
	     let confirm =await showConfrimMsg("是否删除该班级?");
	     if(confirm ==="确定"){
	    	 let userInfo = $cookies.getObject("userInfo");
		     let result = await http.get("DeleteTeacherClass",{
		    	 teacherId:userInfo.id,
		    	 classId:classId
		      });
		      if( result === true ){
		        showErrMsg("班级删除成功");
		        let classes = await http.get("GetTeacherClasses");
		  	    vm.classes = classes;
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
  function checkClass(className){
	  if(className === null || className ===undefined ||className ==="" ){ 
	    	 return false;
	   }  else{
		    return true;
	   }
	  
  }
}