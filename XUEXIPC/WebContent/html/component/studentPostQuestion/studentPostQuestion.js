import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "studentPostQuestion";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/studentPostQuestion/studentPostQuestion.html",
    controller:["$scope","$element","$state",'$cookies',"http",controller]
  });
}
function controller($scope,$element,$state,$cookies,http){
  let vm = this;
  vm.$onInit = init;
//  vm.postQuestion = postQuestion;
  vm.title = "";
  vm.content = "";
  vm.subjectList = [];
  vm.currSubject = null;
  vm.currSubjectId = null;
  vm.currTeacher = null;
  vm.imgUrl=null;
  async function init(){
    let userInfo = $cookies.getObject("userInfo");
    let subjectTeacherData = await http.get("GetAllSubject");
    vm.subjectList = dataGroup(subjectTeacherData);
    if(vm.subjectList.length !==0){
    	 vm.currSubject = vm.subjectList[0];
    	 vm.currSubjectId = vm.currSubject.SubjectID;
    	 vm.currTeacher = vm.currSubject.teacherList[0];
    }  
    $scope.$watch("$ctrl.currSubject",function(){
      vm.currSubject = vm.subjectList.find(function(item){
        return item.SubjectID === vm.currSubject.SubjectID;
      });
      if(vm.currSubject!=null)
       vm.currTeacher = vm.currSubject.teacherList[0];
    });
    setTimeout(function(){
        let imgInputs = findImgInput();
        imgInputs.eq(0).bind("change",onSelectImg);
    },0);
  }

  async function postQuestions(){
    
	if( checkQuestion() === false ){
      return;
    }
    let userInfo = $cookies.getObject("userInfo");
    let question = {
      title:vm.title,
      content:vm.content,
      img:vm.imgUrl,
      studentId:userInfo.id,
      teacherID:vm.currTeacher.teacherId,
      subjectID:vm.currSubjectId
    };
    try {
      let result = await http.post("PostStudentQuestion",question);
       showErrMsg("提交成功");
       $state.go("student.studentQuestionHistory");
      
    } catch (error) {
   
      showErrMsg("提交问题失败");
    }
  }

  function checkQuestion(){
    try {
      vm.title = vm.title.trim();
      vm.content = vm.content.trim();
      if( vm.title === "" ){
        showErrMsg("请输入标题");
        return false;
      }
      if( vm.title.length < 5 ){
   
        showErrMsg("标题不能少于五个字");
        return false;
      }
      if( vm.content === "" ){
        showErrMsg("请输入内容");
        return false;
      }
      if( vm.content.length < 5 ){
        showErrMsg("内容不能少于五个字");
        return false;
      }
      return true;
    } catch (error) {
      showErrMsg("数据检查异常");
      return false;
    }
  }

  /**
   * 按科目，对老师进行分组
   */
  function dataGroup(data){
    let groupList = [];
    data.forEach(function(item){
      let findGroup = groupList.find( (group)=>{
        return item.SubjectID === group.SubjectID;
      });
      if( !findGroup ){
        findGroup = {
          SubjectID:item.SubjectID,
          SubjectName:item.SubjectName,
          teacherList:[],
        };
        groupList.push(findGroup);
      }
      findGroup.teacherList.push({
        teacherId:item.teacherId,teacherName:item.realName
      });
    });
    return groupList;
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
  vm.postQuestion = async function(){
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
		   postQuestions();
	   } catch (error) {
		   console.log(error);
		   showErrMsg("上传图片失败");
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