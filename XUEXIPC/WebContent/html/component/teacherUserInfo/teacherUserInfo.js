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
	vm.isShow =false;
	vm.progress =0;
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
		setTimeout(function(){
            let fileInputs = findFileInput();
            fileInputs.eq(0).bind("change",onSelectFile);
	    },0);
	}
	vm.upload =function(){
		vm.isShow=true;
	}
	 function findFileInput(){
	       return angular.element(document.getElementsByClassName("js_file"));
	  }
	   vm.uploadFile = async function(){
		   let uploadFile=[];
		   uploadFile = findFileInput();
	       let file=[];
	       for(var i=0;i<uploadFile.length;i++){
	           if(uploadFile[i].files[0]!==undefined){
	                file.push(uploadFile[i].files[0]);
	           } 
	       }
	       let formData = new FormData();
	       for(var i=0;i<file.length;i++){
	           formData.append("uploadFile"+i,file[i]);
	       } 
		   try {

			   if(file.length!=0){
				   let result = await http.submitForm("UploadImage",formData,{     
	                    progress: 
	                      function(e){
		                        if (e.lengthComputable) {
		                        	vm.progress=Math.round(((e.loaded / e.total) * 100 ));
		                        }
	                      }      	
		                
		            });
				   if(result!=null){
					   showErrMsg("上传文件成功");
				   }
				   
			   }
		   } catch (error) {
			   console.log(error);
			   showErrMsg("上传文件失败");
		   }
	       
	   }
	   function onSelectFile(event){
	       let files=event.target.files;
	       if( files.length === 0 ){
	           return;
	       }
	       let file = files[0];
	       let reader = new FileReader();
	       reader.onload = function(e) { 
	         
	       }; 
	       reader.readAsDataURL(file);
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
