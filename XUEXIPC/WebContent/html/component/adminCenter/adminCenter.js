import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "adminCenter";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/adminCenter/adminCenter.html",
    controller:["$scope","$element","$state",'$cookies',"http","$httpParamSerializerJQLike",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$httpParamSerializerJQLike){
  let vm = this;
  vm.userResult =null;
  vm.isStudent =true;
  vm.isTeacher =false;
  vm.userinfo =null;
  vm.isShow =false;
  vm.isShow2 =false;
  vm.isShow3 =false;
  vm.currentPage = 1;
  vm.pageItem = 5;
  vm.maxSize = 5;
  vm.allUser=[];
  vm.types = [
	    {label:"学生",value:0},
	    {label:"老师",value:1},
   ];
  vm.$onInit = init;
  async function init(){
	 vm.currentType =vm.types[0];
	 vm.getUser();  
  }
  vm.pageChanged = function(){
	    getData();
  };
  vm.getUser = async function(){
	  if(vm.currentType.value === 0){
		  vm.isStudent =true;
		  vm.isTeacher =false;
		  try {
	        vm.userResult = await http.get("GetStudents");  
	        vm.allUser =vm.userResult;
	        getData();
	      }catch(error){
	    	  showErrMsg("获取学生信息失败");
	        return;
	      }
      }else if(vm.currentType.value ===1){
    	  vm.isStudent =false;
    	  vm.isTeacher =true;
    	  try {
	        vm.userResult = await http.get("GetTeachers");
	        vm.allUser =vm.userResult;
	        getData();
	      }catch(error){
	    	  showErrMsg("获取老师信息失败");
	        return;
	      }
      }
  }
  vm.search = async function(){
	  if( vm.user_number ===""){
		  vm.getUser();
	  }
	  else{
		  if(vm.currentType.value ===0){
			  vm.isStudent =true;
			  vm.isTeacher =false;
		  }else{
			  vm.isStudent =false;
			  vm.isTeacher =true;
		  }
		  let result= await http.get("GetUserInfoByName",{user_number:vm.user_number,type:vm.currentType.value});
		  vm.userResult.splice(0,vm.userResult.length);
		  vm.userResult.push(result);
		 		  
	  }
	  
  }
  vm.addStudent =async function(){
	   let userResult = await http.get("GetUserInfoByName",{user_number:vm.school_number,type:0});
	   if(userResult !== null ){
		   showErrMsg("改学号已存在");
	  }else{
		  let result= await http.post("StudentRegister",
		  $httpParamSerializerJQLike({
			  school_number:vm.school_number,
	          realName:vm.studentRealName,
	        }),
	        {
	          'Content-Type': 'application/x-www-form-urlencoded'
	        });
		  if(result ===true){
			  vm.getUser();
			  showErrMsg("添加学生成功");
			 
		  }
	  }	  
  }
  vm.addTeacher =async function(){
	  let userResult = await http.get("GetUserInfoByName",{user_number:vm.teacher_number,type:1});
	   if(userResult !== null ){
		   showErrMsg("改教工号已存在");
		   
	  }else{
		  let result= await http.post("TeacherRegister",
		  $httpParamSerializerJQLike({
			  teacher_number:vm.teacher_number,
			  realName:vm.teacherRealName,
	        }),
	        {
	          'Content-Type': 'application/x-www-form-urlencoded'
	        });
		  if(result ===true){
			  showErrMsg("添加老师成功");
			  vm.getUser();
		  }
	  }
    }
  vm.edit = function(user){
	  vm.userinfo = user;  
	  if( vm.currentType.value === 0){
		  vm.isShow =true;
		  vm.isShow2 =true;
		  vm.isShow3 =false;
	  }else{
		  vm.isShow =false;
		  vm.isShow2 =true;
		  vm.isShow3 =true;
	  }
	  vm.style={
          width:"400px",
          top:"30%",
          left:"40%",
	      border:"1px solid #888",
	      "box-shadow":"1px 1px 10px black",
	      padding: "10px",
	      "font-size": "12px",
	      position: "absolute",
	      "text-align": "center",
	      "background": "#fff",
	       "z-index": 11,
	      "border-radius":"1px",
	  }
	  var popLayer = document.getElementById('popLayer');
      popLayer.style.width = document.body.clientWidth+"px" ;//浏览器工作区域内页面宽度
      popLayer.style.height = document.body.clientHeight+"px" ;
		  
  }
  vm.updateStudent = function(){
	  updateStudentInfo(vm.userinfo);
	  vm.isShow =false;
	  vm.isShow2 =false; 
  }
  vm.dismiss = function(){
	  vm.isShow =false;
	  vm.isShow2 =false;
	  vm.isShow3 =false;
	  vm.getUser();
  }
  vm.updateTeacher = function(){
	  updateTeacherInfo(vm.userinfo);
	  vm.isShow2 =false;
	  vm.isShow3 =false;
  }
  async function updateStudentInfo(userinfo){
	
	  try{
		  let data={
				"school_number" :userinfo.school_number,
				"realName" :userinfo.realName,
				"password" :userinfo.password,
				"school"   :userinfo.school,
				"telephone":userinfo.telephone,
				"studentID":userinfo.userID		
		  };
		  let result= await http.post("UpdateStudent",data);
		  if(result == true){
			   showErrMsg("修改成功");
		  }else{
			    showErrMsg("修改失败");
				vm.getUser();
		  } 
		}catch (error) {
			showErrMsg("更新信息异常");
	   }
  } 
  async function updateTeacherInfo(userinfo){
	    try{
			let data={
				"teacher_number":userinfo.teacher_number,
				"realName" :userinfo.realName,
				"password" :userinfo.password,
				"teacherID":userinfo.userID
			}
			let result = await http.post("UpdateTeacher",data);
			if(result == true){
				showErrMsg("修改成功");
			}else{
				 showErrMsg("修改失败");
				 vm.getUser();
			  } 
			}catch (error) {
			
				showErrMsg("更新信息异常");
		   }
		
  } 
  vm.deleteUser= async function(user){  
	  let confirmMsg = "您真的确定要删除吗？\n\n请确认！"
      let confirm = await showConfrimMsg(confirmMsg);
	  if(confirm === "确定"){
		  let result= await http.get("DeleteUser",{
			    type: vm.currentType.value,
			    userID:user.userID
		  });
		  if(result == true){
			
				showErrMsg("删除成功");
				vm.getUser();
		  }else{
			
				showErrMsg("删除失败");
				vm.getUser();
		  } 
	  }  
	  
  } 
  function getData(){
	    let newData = [];
	    let start = (vm.currentPage-1)*vm.pageItem;
	    for(let i = start; i < start+vm.pageItem; ++i){
	      if( i >= vm.allUser.length ){
	        vm.userResult = newData;
	        return;
	      }
	      newData.push( vm.allUser[i]);
	    }
	    vm.userResult = newData;
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

  