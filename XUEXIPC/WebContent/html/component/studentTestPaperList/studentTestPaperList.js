import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "studentTestPaperList";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/studentTestPaperList/studentTestPaperList.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
  let vm = this;
  vm.msg = "";
  vm.subject = null;
  vm.paperUnfinish = [];
  vm.paperFinish = [];
  vm.displayPaper = [];
  vm.showFlag = true;
  vm.isShowUnfinish =true;
  vm.isShowFinish =false;
  $scope.className1 = true;
  $scope.className2 = false;
  vm.$onInit = async function(){
    vm.subject = {
        SubjectName:$stateParams.SubjectName,
        SubjectID:$stateParams.SubjectID,
        teacherID :$stateParams.teacherID
	};
	try {
      let rs = await getPaper();
    } catch (error) {
      showErrMsg("初始化页面异常");
    }
  }
  async function  getPaper(){
    vm.papers = await http.get("GetPaper",{
      subjectId:vm.subject.SubjectID,
    });
    let result =[];
    for( var i=0 ;i< vm.papers.length;i++ ){
    	let flag =0;
    	for(var j=0;j<result.length;j++){
        	if(result[j].testpaperID ===vm.papers[i].testpaperID){
        		flag=1;
        		break;
        	}
    	}
    	if(flag===0){
    		result.push(vm.papers[i]);
    	} 	
    	console.log(result);
    }
    vm.papers  = result;
    vm.papers.forEach( (paper)=>{
      paper.createTime = new Date(paper.createTime.time);
    });
    let paperResult = await http.get("GetPaperResult");
    vm.papers.forEach( (paper)=>{
      var findResult = paperResult.find( (result)=>{
        if( result.paper.paperId === paper.testpaperID ){
          paper.resultObj = result;
          return true;
        }
      });
      if( findResult !== undefined ){
        vm.paperFinish.push(paper);
      }else{
        vm.paperUnfinish.push(paper);
      }
    });
    vm.displayPaper = vm.paperUnfinish;
  }

  vm.changePaperType = function(flag){
    vm.showFlag = flag;
    if( flag ){
      vm.displayPaper = vm.paperUnfinish;
    }else{
      vm.displayPaper = vm.paperFinish;
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

  async function showConfrimMsg(confirmMsg) {
      return await dialog.openDialog({
          type: "normal",
          title: "操作提示",
          content: confirmMsg
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