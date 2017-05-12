import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "studentHomeworkDetail";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/studentHomeworkDetail/studentHomeworkDetail.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
  let vm = this;
  let index = 0;
  vm.currIndex = 0;
  vm.$onInit = async function(){
    try {
      vm.homework = {
        homeWorkName:$stateParams.homeWorkName,
        homeId:$stateParams.homeworkId,
      };
      vm.homeDetail = await http.get("GetHomeworkDetail",{
        homeworkID:vm.homework.homeId
      });
      vm.curr = vm.homeDetail[index];
    } catch (error) {
  
      showErrMsg("初始化页面异常");
    }
  }
  vm.upOrNext = function(type){
    if( type === "up"){
      --index;
      index = index >= 0 ? index : 0;
    }else{
      ++index;
      index = index < vm.homeDetail.length ? index : --index;
    }
    vm.curr = vm.homeDetail[index];
    vm.currIndex = index;
  }

  vm.submit = async function(){
    let data = {
      homeworkID:vm.homework.homeId,
      evaluation:"",
      homeworkResult:[]
    };
    vm.homeDetail.forEach( ( item )=>{
      data.homeworkResult.push({
        questionID:item.itemId,
        answer:item.studentAnswer
      });
    });
    try {
      let result = await http.post("SubmitHomework",data);
      if( result === true ){
          showErrMsg("提交作业成功");
          $state.go('student.homework');
      }else{
        showErrMsg("提交作业失败");
      }
    } catch (error) {
    
      showErrMsg("提交作业失败");
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