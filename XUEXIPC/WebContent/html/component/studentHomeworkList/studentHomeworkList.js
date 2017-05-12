import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "studentHomeworkList";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/studentHomeworkList/studentHomeworkList.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
  let vm = this;
  vm.msg = "";
  vm.subject = null;
  vm.homeworkListUnFinish = [];
  vm.homeworkListFinish = [];
  vm.displayHomeworkList = [];
  vm.showFlag = true;
  $scope.className1 = true;
  $scope.className2 = false;
  vm.isShowUnfinish =true;
  vm.isShowFinish =false;
  vm.$onInit = async function(){
    try {
      vm.subject = {
        SubjectName:$stateParams.SubjectName,
        SubjectID:$stateParams.SubjectID
      };
      vm.homeworkList = await http.get("GetHomework",{
    	  subjectId:vm.subject.SubjectID
      });
      vm.homeworkResults = await http.get("GetHomeworkResult");
      vm.homeworkList.forEach( (homework)=>{
    	 homework.finishTime = new Date(homework.finishTime.time);
        let find = vm.homeworkResults.find( (result)=>{
          return homework.homeId === result.homework.homeworkId
        });
        if( find ){
          vm.homeworkListFinish.push(homework);
        }else{
          vm.homeworkListUnFinish.push(homework);
        }
      });
      vm.displayHomeworkList = vm.homeworkListUnFinish;
    } catch (error) {
      showErrMsg("初始化页面异常");
    }    
  }
  vm.changeHomeworkType = function(flag){
    vm.showFlag = flag;
    if(flag){
      vm.displayHomeworkList = vm.homeworkListUnFinish;
    }else{
      vm.displayHomeworkList = vm.homeworkListFinish;
    }
  }
  vm.goToAnswered =function(){
	  vm.isShowUnfinish =false;
	  vm.isShowFinish =true;
	  $scope.className1 = false;
	  $scope.className2 = true;
  }
  vm.goNoAnswered =function(){
	  vm.isShowUnfinish =true;
	  vm.isShowFinish =false;
	  $scope.className1 = true;
	  $scope.className2 = false;
  }
  function showErrMsg(errMsg) {
	     
      dialog.openDialog({
          type: "warning",
          title: "提示",
          content: errMsg
      });
    
  }
}