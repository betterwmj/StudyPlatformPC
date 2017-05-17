import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "studentOnlineAnswerDetail";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/studentOnlineAnswerDetail/studentOnlineAnswerDetail.html",
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
    vm.imgUrl=null;
	vm.userinfo = null;
	vm.currentReplyPage =1;
	vm.replyPageItem=7;
	vm.totalReplyItems=0;
	function init(){ 
	    vm.onlineQuesionsDetail = $stateParams.onlineQuestionsDetail; 
	    vm.currentClass = $stateParams.currentClass;
	    vm.isHistroy = $stateParams.isHistroy;
	    vm.currentPage = $stateParams.currentPage;
	     vm.pageItems = $stateParams.pageItems;
	     vm.totalItems = $stateParams.totalItems;
	    vm.userinfo = $cookies.getObject("userInfo");
	    vm.userinfoId =parseInt("10", vm.userinfo.id);
	    getQuestionReply();
	    setTimeout(function(){
	        let imgInputs = findImgInput();
	        imgInputs.eq(0).bind("change",onSelectImg);
	    },0);
   }
	 vm.pageChanged=function(){
		  getQuestionReply();
	   }
   vm.reply=function(){
	   vm.isShow = true;
	   vm.msg="";
   }
   function findImgInput(){
	      return angular.element(document.getElementsByClassName("js_product_imgs"));
	  }
	  function onSelectImg(event){
	      let parentElement=event.target.parentNode;
	      let img=null;
	      for(let i=0;i<parentElement.children.length;i++){
	          if(parentElement.children[i].nodeName==="IMG"){
	              img=parentElement.children[i];
	          }
	      }
	      let files=event.target.files;
	      if( files.length === 0 ){
	          img.src="";
	          return;
	      }
	      let file = files[0];
	      let reader = new FileReader();
	      reader.onload = function(e) { 
	          img.src = e.target.result;
	      }; 
	      reader.readAsDataURL(file);
	  }
	  vm.sure = async function(){
		   let imgs=[];
	      imgs = findImgInput();
	      let file=[];
	      for(var i=0;i<imgs.length;i++){
	          if(imgs[i].files[0]!==undefined){
	               file.push(imgs[i].files[0]);
	          } 
	      }
	      let formData = new FormData();
	      for(var i=0;i<file.length;i++){
	          formData.append("img"+i,file[i]);
	      } 
		   try {
			   if(file.length!=0){
				   let imgResult = await http.submitForm("UploadImage",formData);
				   vm.imgUrl =imgResult;
				}
			   reply();
		   } catch (error) {
			   console.log(error);
			   showErrMsg("上传图片失败");
		   }
	      
	  }
	  vm.goToImgUrl=function(img){
		  window.open(img);
	  }
	  
   async  function reply(){	
	   let content =vm.answerContent;
	   if(content ===null || content==="" ||content ===undefined){
		   showErrMsg("请输入内容");
		   return;
	   }
	   let userInfo = $cookies.getObject("userInfo");
	   let data = {
		  answer:vm.answerContent,
		  questionID:$stateParams.onlineQuestionsDetail.id,
		  img:vm.imgUrl,
		  answerID:userInfo.id,
		  type:userInfo.type
	   };
	   vm.isShow = true;
	   let result= await http.post('ReplyStudentQuestion',data);
	   if(result === true){
		   showErrMsg("回复成功");
		   vm.isShow = false;
		   getQuestionReply();
	   }else{
		 
		   showErrMsg("回复失败");
	   }	  
   }
   async function getQuestionReply(){	
	   try {
		   let result= await http.get('getQuestionAnswer',{
			   questionID:vm.onlineQuesionsDetail.id,
			   currentPage:vm.currentReplyPage,
			   pageItems:vm.replyPageItem
			   });
		   vm.replyList =result;
		   if( result.length!==0){
		          vm.totalReplyItems =result[0].count;
		     }
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
	   }catch (error) {
          showErrMsg("获取回复失败");
          return;
	    }
   }
   vm.previousPage =function(){
	   if(vm.isHistroy === true){
		   $state.go("student.studentQuestionHistory",{currentPage:vm.currentPage,pageItems:vm.pageItems,totalItems:vm.totalItems});
	   }else{
		   $state.go("student.onlineQuestion",{currentClass:vm.currentClass,currentPage:vm.currentPage,pageItems:vm.pageItems,totalItems:vm.totalItems});
	   }   
   }
   vm.deleleReply = async function(replyId,answerId){
	     
		   if(parseInt("10", vm.userinfo.id) === answerId){
			   let comfirm =await showConfrimMsg("是否删除此条回复?");
			   if(comfirm==="确定"){
				   let result= await http.get('DeleteReply',{ReplyID:replyId});
				   if(result === true){
					
					   getQuestionReply();
				   }else{
					   showErrMsg("删除失败");
				   }	 
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