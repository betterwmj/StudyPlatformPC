import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "homeworkCreate";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/homeworkCreate/homeworkCreate.html",
    controller:["$scope","$element","$state",'$cookies',"http",controller]
  });
}
function controller($scope,$element,$state,$cookies,http){
  let vm = this;
  vm.types = [
    {label:"选择题",value:1},
    {label:"判断题",value:2},
    {label:"简答题",value:3}
  ];
  let temp = {
    "type":vm.types[0],
    "title":"",
    "answer":'',
    "optionA":"",
    "optionB":"",
    "optionC":"",
    "optionD":"",
  };
  vm.homework = {
    name:new Date().toLocaleString(),
    finishTime:new Date(),
    questions:[],
    subjectId: null,
  };
  vm.questions = [];
  vm.currentQuestion = Object.assign({},temp);
  vm.currentQuestion= vm.types[0];
  vm.questions.push( vm.currentQuestion );
  vm.currIndex = 0;
  vm.currentSubject = null;
  vm.$onInit = async function(){
	vm.currentQuestion.type =vm.types[0];
	vm.subjectlist = await http.get("GetTeacherSubject");
    if( vm.subjectlist.length > 0 ){
      vm.currentSubject = vm.subjectlist[0];
    }
  };
  vm.upItem = function(){
    if( vm.currIndex === 0) {
      return;
    }
    --vm.currIndex;
    vm.currentQuestion = vm.questions[vm.currIndex];
  };
  vm.createNextQuestion = function(){
    if( checkQuestion(vm.currentQuestion) === false){
      return;
    }
    ++vm.currIndex;
    if( vm.questions[vm.currIndex] ){
      vm.currentQuestion = vm.questions[vm.currIndex];
    }else{
      let itemTemp = Object.assign({},temp);
      itemTemp.type = vm.types[0];
      vm.questions.push(itemTemp);
      vm.currentQuestion = itemTemp;
    }
  };

  vm.createHomework = async function(){
	  if( checkQuestion(vm.currentQuestion) === false){
	      return;
	    }
	  let questionList = {
      titles:[],
      subjectId: vm.currentSubject.SubjectID
    };
    vm.questions.forEach( (item)=>{
      let temp = Object.assign({},item);
      temp.type = temp.type.value;
      questionList.titles.push(temp);
    });
    try {
      let list = await http.post("AddQuestion",questionList);
      list.forEach( (item)=>{
        vm.homework.questions.push({
          questionID:item.itemId,
        });
      });
      vm.homework.subjectId = vm.currentSubject.SubjectID;
      let rs = await http.post("CreateHomework",vm.homework)
      if(rs === true){
          showErrMsg("创建作业成功");
          $state.go("teacher.homeWorkHistory");
        
      }else{
        showErrMsg("创建作业失败");
      }
    } catch (error) {
      showErrMsg("创建作业失败");
    }
  }

  function checkQuestion(question){
    let type = question.type.value;
    let title = question.title;
    if( title === undefined){
    
      showErrMsg("请输入题目内容");
      return false;
    }
    if( type === 1){
      let optionA = question.optionA;
      if( optionA === undefined){
        showErrMsg("请输入选项A");
        return false;
      }
      let optionB = question.optionB;
      if( optionB === undefined){
      
        showErrMsg("请输入选项B");
        return false;
      }
      let optionC = question.optionC;
      if( optionC === undefined){
        
        showErrMsg("请输入选项C");
        return false;
      }
      let optionD = question.optionD;
      if( optionD === undefined){
      
        showErrMsg("请输入选项D");
        return false;
      }
    }
    if( !question.answer){
    	showErrMsg("请输入答案");
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
  async function showConfrimMsg(confirmMsg) {
      return await dialog.openDialog({
          type: "normal",
          title: "操作提示",
          content: confirmMsg
      });
  }
}