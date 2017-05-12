import {app} from "../../bootstrap.js"
export let name = "teacherUserInfo";
import {dialog} from "../cwDialog/cwDialog.js";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/teacherUserInfo/teacherUserInfo.html",
    controller:["$scope","$element","$state","$cookies","http",controller]
  });
}
function controller($scope,$element,$state,$cookies,http){
	let vm=this;
	vm.$onInit=init();
	vm.classes = [];//教授的班级信息
	vm.userInfo = {
		teacher_number:"",
		realName:"",
		password:"",
		subjectID:"",
		teacherID:""
	};
	async function init(){
		try {
			let user= $cookies.getObject("userInfo");
			let userInfo = await http.get("GetUserInfoByName",{user_number:user.teacher_number,type:user.type});
			let classes = await http.get("GetTeacherClasses");
			vm.userInfo = userInfo;
			vm.classes = classes;
		} catch (error) {
			showErrMsg("加载个人信息异常");
		}		
	}
	
	vm.updateinfo=async function(){
		try {
			let data={
				"teacher_number" :vm.userInfo.teacher_number,
				"realName" :vm.userInfo.realName,
				"password" :vm.userInfo.password,
				"teacherID":vm.userInfo.userID
			}
			let result = await http.post("UpdateTeacher",data);
			if(result == true){
				showErrMsg("修改成功");
			}else{
			
				showErrMsg("修改失败");
			}
		} catch (error) {
		
				showErrMsg("更新信息异常");
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
