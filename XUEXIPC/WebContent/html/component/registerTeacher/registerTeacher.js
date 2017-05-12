import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "registerTeacher";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/registerTeacher/registerTeacher.html",
    controller:["$scope","$element","$state",'$cookies',"http",'$httpParamSerializerJQLike',controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$httpParamSerializerJQLike){
		let vm = this;
		vm.userInfo = {
			teacher_number:"",
			realName:"",
			password:"",
			rePassword:"",
		};
  	vm.register = async function(){
				if( checkRegister() === false ){
					return;
				}
				let userResult =null;
				try {
						userResult = await http.get("GetUserInfoByName",{user_number: vm.userInfo.teacher_number,type:1});
						if(userResult === null ){
							showErrMsg("您不是本校教师，无法注册");
							return;
						}
						if(userResult.realName !==  vm.userInfo.realName ){
					
							showErrMsg("请检查您的真实姓名是否正确");
							return;
						}
						if(userResult.password !== "" ){
							showErrMsg("您已注册，请登录");
							return;
						}
						register();
				} catch (error) {
						showErrMsg("注册时发生异常");
				}
		}
		async function register(){
				try {
						let result = await http.post("TeacherRegister",
						$httpParamSerializerJQLike({
							teacher_number:vm.userInfo.teacher_number,
							realName:vm.userInfo.realName,
							password:vm.userInfo.password,
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
		function checkRegister(){
			if( !vm.userInfo.teacher_number ){
			
				showErrMsg("请输入用户名");
				return false;
			}
			vm.userInfo.teacher_number = vm.userInfo.teacher_number.trim();
			if( vm.userInfo.teacher_number === ""){
				
				showErrMsg("请输入用户名");
				return false;
			}
			if( !vm.userInfo.realName ){
			
				showErrMsg("请输入真实姓名");
				return false;
			}
			vm.userInfo.realName = vm.userInfo.realName.trim();
			if( vm.userInfo.realName === ""){
			
				showErrMsg("请输入真实姓名");
				return false;
			}

			if( !vm.userInfo.password ){
				showErrMsg("请输入密码");
				return false;
			}
			vm.userInfo.password = vm.userInfo.password.trim();
			if( vm.userInfo.password === ""){
				
				showErrMsg("请输入密码");
				return false;
			}

			if( vm.userInfo.password !== vm.userInfo.rePassword ){
			
				showErrMsg("两次输入密码不一致");
				return false;
			}
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