import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "teacheronlineHistoryAnswer";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/teacheronlineHistoryAnswer/teacheronlineHistoryAnswer.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
  let vm = this;
  vm.questionsList=null;
  vm.currentPage=1;
	vm.pageItems =8;
	vm.maxSize = 5;
	vm.totalItems =0;
  vm.$onInit = async function(){
	  if($stateParams.currentPage!==null){
		  vm.currentPage = $stateParams.currentPage;
		  vm.pageItems = $stateParams.pageItems;
		  vm.totalItems = $stateParams.totalItems;
	  }
	  getTeacherQuestion();
  }
	vm.pageChanged=function(){
		getTeacherQuestion();
	 }
	
  async function  getTeacherQuestion(){
	try{	
		    let result = await http.get("GetStudentQuestion",{
		    	currentPage:vm.currentPage,
				pageItems:vm.pageItems
		    });
		    
			vm.questionsList = result;
			 if( result.length!==0){
		          vm.totalItems =result[0].count;
		     }
			vm.questionsList.forEach( async (item)=>{
			   item.createTime =new Date(item.createTime.time);
			   let studentInfo = await http.get("GetStudents",{
					userID:item.studentId
			   });
			   if(studentInfo.length!==0)
			      item.studentName =studentInfo[0].realName;
	        });
	  }catch (error) {
	      showErrMsg("获取历史数据失败");
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