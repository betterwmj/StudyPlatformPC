import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "registerStudent";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/registerStudent/registerStudent.html",
    controller:["$scope","$element","$state",'$cookies',"http",'$httpParamSerializerJQLike',controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$httpParamSerializerJQLike){
  let vm = this;
  vm.userInfo = {
    school_number:"",
    realName:"",
    password:"",
    rePassword:"",
    school:"",
    telephone:"",
  };
  vm.register = async function(){
    if( check() === false ){
      return;
    }
    let userResult =null;
    try {
    	userResult = await http.get("GetUserInfoByName",{user_number: vm.userInfo.school_number,type:0});
    	if( userResult === null ){
				showErrMsg("您不是本校学生，无法注册");
    		return;
    	}
    	if( userResult.realName !==  vm.userInfo.realName ){
				showErrMsg("请检查您的真实姓名是否正确");
				return;
    	}
    	if(userResult.password !== "" ){
				showErrMsg("您已注册，请登录");
				return;
    	}else{
    		studentRegister();
    	}
     }catch (error) {
			
	   showErrMsg("注册失败");
     }
  }
  async function studentRegister(){
	  try {
	      let result = await http.post("StudentRegister",
	        $httpParamSerializerJQLike({
	          school_number:vm.userInfo.school_number,
	          realName:vm.userInfo.realName,
	          password:vm.userInfo.password,
	          school:vm.userInfo.school,
	          telephone:vm.telephone
	        }),
	        {
	          'Content-Type': 'application/x-www-form-urlencoded'
	        });
	      if( result === true ){
	        $state.go('login');
	      }
	    } catch (error) {
				showErrMsg("注册失败");
	    }
  }
  
  function check(){
    if( !vm.userInfo.school_number || vm.userInfo.school_number.trim() === ""){
				showErrMsg("用户名不能为空");
				return false;
		}
		vm.userInfo.school_number = vm.userInfo.school_number.trim();

		if( !vm.userInfo.realName || vm.userInfo.realName.trim() === ""){
				showErrMsg("真实姓名不能为空");
				return false;
		}
		vm.userInfo.realName = vm.userInfo.realName.trim();

		if( !vm.userInfo.password || vm.userInfo.password.trim() === ""){
			
				showErrMsg("密码不能为空");
				return false;
		}
		vm.userInfo.password = vm.userInfo.password.trim();

		if( vm.userInfo.rePassword !== vm.userInfo.password ){
			showErrMsg("两次输入密码不一致");
			return false;
        }

		if( !vm.userInfo.school || vm.userInfo.school.trim() === ""){
				showErrMsg("学校不能为空");
				return false;
		}
		vm.userInfo.school = vm.userInfo.school.trim();

		if( !vm.userInfo.telephone || vm.userInfo.telephone.trim() === ""){
				showErrMsg("手机号不能为空");
				return false;
		}
		vm.userInfo.telephone = vm.userInfo.telephone.trim();

		return true;
  }
  function showErrMsg(errMsg) {
	     
      dialog.openDialog({
          type: "warning",
          title: "提示",
          content: errMsg
      });
    
  }
}