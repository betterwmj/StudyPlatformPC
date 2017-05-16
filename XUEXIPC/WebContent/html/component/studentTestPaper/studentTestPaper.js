import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "studentTestPaper";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/studentTestPaper/studentTestPaper.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
  let vm = this;
  vm.msg = "";
  vm.subject = null;
  vm.paper = null;
  vm.paperDetail = null;
  vm.questionIndex = 0;
  vm.currQuestion = null;
  vm.$onInit = async function(){
    try {
      let userInfo = $cookies.getObject("userInfo");
      vm.subject = {
        SubjectName:$stateParams.SubjectName,
        SubjectID:$stateParams.SubjectID,
        teacherID :$stateParams.teacherID
      };
      vm.paper = {
        testpaperID:$stateParams.testpaperID,
        testName:$stateParams.testName
      };
      vm.paperDetail = await http.get("GetPaperDetail",{
        paperID:vm.paper.testpaperID
      });
      vm.currQuestion = vm.paperDetail[0];
    } catch (error) {
      showErrMsg("初始化页面异常");
   
    }
  } 

  vm.changeIndex = function(upOrDown){
    if( upOrDown === 'up'){
      --vm.questionIndex;
      vm.questionIndex = vm.questionIndex >= 0 ? vm.questionIndex : 0;
    }else{
      ++vm.questionIndex;
      if( vm.questionIndex >= vm.paperDetail.length ){
        vm.questionIndex -= 1;
      }
    }
    vm.currQuestion = vm.paperDetail[vm.questionIndex];
  }

  vm.submitResult = async function(){
    try {
      let userInfo = $cookies.getObject("userInfo");
      let result = { 
        "paperID":vm.paper.testpaperID,
        "studentID":userInfo.id,
        "score":0,
        "paperresult":[]
      };
      let correctAnswer =0;
      vm.paperDetail.forEach( (item)=>{
        let studentAnswer = item.studentAnswer;
        let answer = item.answer;       
        if( studentAnswer === answer ){	
          result.score += item.score;
          correctAnswer=correctAnswer+1;
        }
        result.paperresult.push({
          "questionID":item.itemId,
          "answer":studentAnswer||""
        });
      });
      let rs = await http.post("SubmitPaper",result);
      let correctPercent =Math.round(correctAnswer/vm.paperDetail.length* 100);
      let infor= "";
      if(correctPercent <20){
    	  infor= "测试结果不妙，别灰心继续努力哦！";
      }else if(correctPercent <60){
    	  infor= "革命尚未成功！";
      }else if(correctPercent <80) {
    	  infor= "还需更上一层楼！";
      }else if(correctPercent <90){
    	  infor= "good！";
      }
      if(rs===true){
    	  showErrMsg("提交答案成功! \n" +"正确率"+correctPercent+"%" +" \n"+infor);
    	  $state.go('student.studentTestPaperList',{
              SubjectName:vm.subject.SubjectName,
              SubjectID:vm.subject.SubjectID,
              teacherID:vm.subject.teacherID
           });
      }else{
    	  showErrMsg("提交失败");
      }
         
    } catch (error) {
      showErrMsg("提交答案失败");
    }
  }

  vm.goBack = function(){
    dialog.openDialog({
        type: "normal",
        title: "提示",
        content: "是否放弃作答?"
    })
    .then(function(btn){
      if( btn === '确定'){
        $state.go("student.studentTestPaperList",{
          SubjectName:vm.subject.SubjectName,SubjectID:vm.subject.SubjectID,teacherID:vm.subject.teacherID
        });
      }
    });
  }
  function showErrMsg(errMsg) {
	     
      dialog.openDialog({
          type: "warning",
          title: "提示",
          content: errMsg
      });
    
  }
}