import {app} from "../../bootstrap.js"
export let name = "studentCenter";
import {dialog} from "../cwDialog/cwDialog.js";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/studentCenter/studentCenter.html",
    controller:["$scope","$element","$state",'$cookies',"http",controller]
  });
}
function controller($scope,$element,$state,$cookies,http){
	  let vm = this;
		vm.userinfo = {
			school_number:"",
			realName:"",
			password:"",
			school:"",
			telephone:"",
			studentID:""
	  };
	  vm.$onInit = async function(){
			try {
				let user= $cookies.getObject("userInfo");
				let userinfo = await http.get("GetUserInfoByName",{user_number:user.school_number,type:user.type});
				vm.userinfo = userinfo;
				let classes = await http.get("GetAllSubject");
				vm.classes = classes;
				vm.classes.forEach( async (item)=>{
					let result =await http.get("GetClassNameByClassId",{"classID":item.classId});
					if(result!=null)
					   item.className=result.className;
				});
			} catch (error) {
			
				showErrMsg("加载个人信息异常");
			}
	  }
//	  async function getClassName(classId){
//			if(!classId){
//				return;
//			}
//		  let result = await http.get("GetClassNameByClassId",{"classID":classId});
//		  return result;
//		  $scope.$applyAsync(null);
//	  }
		vm.updateinfo=async function(){
				try {
					let data={
						"school_number" :vm.userinfo.school_number,
						"realName" :vm.userinfo.realName,
						"password" :vm.userinfo.password,
						"school"   :vm.userinfo.school,
						"telephone":vm.userinfo.telephone,
						"studentID":vm.userinfo.userID		
					};
					let result= await http.post("UpdateStudent",data);
					if(result==true){
					
						showErrMsg("修改成功");
					}else{
					
						showErrMsg("修改失败");
					}
				} catch (error) {
					showErrMsg("修改失败");
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