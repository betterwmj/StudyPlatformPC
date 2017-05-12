import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "teacherOnlineAnswerDetail";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/teacherOnlineAnswerDetail/teacherOnlineAnswerDetail.html",
    controller:["$scope","$cookies","$element","$state","http","$stateParams",controller]
  });
}
function controller($scope, $cookies,$element,$state,http,$stateParams,){
	let vm = this;
	vm.$onInit=init;
	vm.isShow=false;
	vm.replyList=null;
	vm.onlineQuesionsDetail =null;
	vm.currentClass=null;
	vm.isHistroy =null;
	function init(){ 
	    vm.onlineQuesionsDetail = $stateParams.onlineQuestionsDetail; 
	    vm.currentClass = $stateParams.currentClass;
	    vm.isHistroy = $stateParams.isHistroy;
	    getQuestionReply();
   }
   vm.reply=function(){
	   vm.isShow = true;
	   vm.msg="";
   }
   vm.sure=async function(){	
	   let userInfo = $cookies.getObject("userInfo");
	   let data = {
		  answer:vm.answerContent,
		  questionID:$stateParams.onlineQuestionsDetail.id,
		  answerID:userInfo.id,
		  type:userInfo.type
	   };
	   vm.isShow = true;
	   let result= await http.post('ReplyStudentQuestion',data);
	   if(result === true){
		   vm.msg="回复成功";
		   vm.isShow = false;
		   getQuestionReply();
	   }else{
		   showErrMsg("回复失败");
	   }	  
   }
   async function getQuestionReply(){	
	   try {
		   let result= await http.get('getQuestionAnswer',{questionID:vm.onlineQuesionsDetail.id});
		   vm.replyList =result;
		   vm.replyList.forEach( async (item)=>{
			   item.answerTime =new Date(item.answerTime.time);
			   if(item.type ===0){
				   let studentInfo = await http.get("GetStudents",{
						userID:item.answerId
				   });
				   if(studentInfo.length!==0)
				      item.realName =studentInfo[0].realName;
			   }else{
				   let teacherInfo = await http.get("GetTeachers",{
						userID:item.answerId
				   });
				   if(teacherInfo.length!==0)
				      item.realName =teacherInfo[0].realName;
			   }
			   
		    });
		   if(vm.isHistroy === true){
			   let userInfo = $cookies.getObject("userInfo");
			   let newReplyList=[];
			   let id=parseInt(userInfo.id);
			   vm.replyList.forEach( (item)=>{
				   if(id === item.answerId && item.type ===1 ){
					   newReplyList.push(item);
				   }
				   
			   });
			   vm.replyList=newReplyList;
		   }else{
			   vm.replyList =result;
		   }
		   
		    
	   }catch (error) {
          showErrMsg("获取回复失败");
          return;
	    }
   }
   vm.previousPage =function(){
	   if(vm.isHistroy === true){
		   $state.go("teacher.onlineHistoryAnswer");
	   }else{
		   $state.go("teacher.onlineanswer",{currentClass:vm.currentClass});
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