import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "teacherPaper";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/teacherPaper/teacherPaper.html",
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
  vm.currentType = vm.types[0];
  vm.questions = [];
  vm.allQuestion = [];
  vm.currentPage = 1;
  vm.pageItem = 3;
  vm.msg = "";
  vm.currentSubject = null;
  vm.subjectlist =null;
  vm.$onInit = async function(){
    try {
      vm.subjectlist = await http.get("GetTeacherSubject");
      if( vm.subjectlist.length !== 0){
        vm.currentSubject = vm.subjectlist[0];
        vm.getQuestionByType();
      }  
    } catch (error) {
      showErrMsg("初始化页面异常");
    }
    
  }
  vm.paper = {
    subjectId :null,
    papername:new Date().toLocaleString(),
    papertitles:[]
  };
  vm.createPager = async function(){
    vm.msg = "";
    let count = vm.paper.papertitles.length;
    let totalScore = 0;
    vm.paper.subjectId = vm.currentSubject.SubjectID;
    vm.paper.papertitles.forEach((item)=>{
      totalScore += item.score;
    });
    if( count === 0 || totalScore === 0 ){

      showErrMsg("请选择题目且合理配置试卷分数");
      return;
    }
    let confirmMsg = "确定创建试卷?共"+count+"道题,共"+totalScore+"分";
    let confirm = await showConfrimMsg(confirmMsg);
    if(confirm === "确定"){
    	try {
            let result = await http.post("CreatePaper",vm.paper);
            showErrMsg("创建试卷成功");
              $state.go("teacher.publishPaper");
           } catch (error) {
            showErrMsg("创建试卷失败");
         }
    }

  };
  vm.getQuestionByType = async function(){
	  if( vm.subjectlist.length!==0){
		  let rs = await getQuestions(vm.currentSubject.SubjectID,vm.currentType.value);
	  } 
  }
  vm.getQuestionBySubject =async function(){
	  if( vm.subjectlist.length!==0){
	    let rs = await getQuestions(vm.currentSubject.SubjectID,vm.currentType.value);
	  }
  }
  vm.pageChanged = function(step){
    let pageCount = Math.ceil(vm.allQuestion.length/vm.pageItem);
    if( vm.currentPage + step <= 0 || vm.currentPage + step > pageCount ){
      return;
    }
    vm.currentPage = vm.currentPage + step ;
    getData();
  };

  vm.append = function(question){
    if( question.isChecked === true ){
      vm.paper.papertitles.push({
        itemId:question.itemId,
        score:question.score
      });
    }else{
      vm.paper.papertitles = vm.paper.papertitles.filter( (item)=>{
        return item.itemId !== question.itemId;
      });
    }
  }

  vm.questionScoreChange = function(question){
    let find = vm.paper.papertitles.find(function(item){
      return item.itemId === question.itemId;
    });
    if( find ){
      find.score = question.score;
    }
  }

  function mergeData(){
    vm.allQuestion.map( (item)=>{
      let find = vm.paper.papertitles.find( (tItem)=>{
        return tItem.itemId === item.itemId
      });
      if( find ){
        item.isChecked = true;
        item.score = find.score;
      }
    });
  }

  async function getQuestions(subjectId,value){
    let result = null;
    try {
      result = await http.get("GetQuestions",{
    	subjectId:subjectId,
        type:value
      });
      vm.allQuestion = result;
      mergeData();
      vm.allQuestion.forEach( (item)=>{
        if( "score" in item === false ){
          item.score = 5;
        }
      });
      getData();
      
    } catch (error) {
      showErrMsg("获取题目数据异常");
    }
    return result;
  }

  function getData(){
    let newData = [];
    let start = (vm.currentPage-1)*vm.pageItem;
    for(let i = start; i < start+vm.pageItem; ++i){
      if( i >= vm.allQuestion.length ){
        vm.questions = newData;
        return;
      }
      newData.push( vm.allQuestion[i]);
    }
    vm.questions = newData;
  }
  function showErrMsg(errMsg) {
	     
      dialog.openDialog({
          type: "warning",
          title: "错误提示",
          content: errMsg
      });
    
  }

  async function showConfrimMsg(confirmMsg) {
      return await dialog.openDialog({
          type: "normal",
          title: "提示",
          content: confirmMsg
      });
  }
}