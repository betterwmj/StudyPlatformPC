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
	vm.imgUrl=null;
	let  onlineQuesionsDetail=null;
	function init(){ 
	    vm.onlineQuesionsDetail = $stateParams.onlineQuestionsDetail; 
	    onlineQuesionsDetail =vm.onlineQuesionsDetail;
	    vm.currentClass = $stateParams.currentClass;
	    vm.isHistroy = $stateParams.isHistroy;
	    vm.userinfo = $cookies.getObject("userInfo");
	    vm.userinfoId =parseInt("10", vm.userinfo.id);
	    getQuestionReply();
	    setTimeout(function(){
            let imgInputs = findImgInput();
            imgInputs.eq(0).bind("change",onSelectImg);
        },0);
   }
   vm.reply=function(){
	   vm.isShow = true;
	   vm.msg="";
   }
   vm.goToImgUrl=function(img){
		  window.open(img);
	  }
  async function postReply(){	
	  let content =vm.answerContent;
	   if(content ===null || content==="" ||content ===undefined){
		   showErrMsg("请输入内容");
		   return;
	   }
	  let userInfo = $cookies.getObject("userInfo");
	   let data = {
		  answer:vm.answerContent,
		  img:vm.imgUrl,
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
				   if(vm.onlineQuesionsDetail.studentId === item.answerId && item.type ===0 ){
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
   vm.deleleReply = async function(replyId,answerId){
	   let userinfo = $cookies.getObject("userInfo");
		   if(parseInt("10", userinfo.id) === answerId){
			   let comfirm =await showConfrimMsg("是否删除此条回复?");
			   if(comfirm==="确定"){
				   let result= await http.get('DeleteReply',{ReplyID:replyId});
				   if(result === true){
					   vm.onlineQuesionsDetail=onlineQuesionsDetail;
					   getQuestionReply();
				   }else{
					   showErrMsg("删除失败");
				   }	 
		   } 
	   }
	   
   }
   function findImgInput(){
       return angular.element(document.getElementsByClassName("js_product_imgs"));
   }
   vm.replyQuestion = async function(){
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
		   postReply();

	   } catch (error) {
		   console.log(error);
		   showErrMsg("上传图片失败");
	   }
       
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