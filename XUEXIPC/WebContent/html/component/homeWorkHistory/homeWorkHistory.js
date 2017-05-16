import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "homeWorkHistory";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/homeWorkHistory/homeWorkHistory.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams,){
  let vm = this;
  vm.currentPage=1;
  vm.pageItems =4;
  vm.maxSize = 5;
  vm.totalItems =0;
  vm.$onInit = async function(){
	  if( $stateParams.currentPage !=null){
		  vm.currentPage = $stateParams.currentPage;
		     vm.pageItems = $stateParams.pageItems;
		     vm.totalItems = $stateParams.totalItems;
	  }
	  getHomework();
  }
  vm.pageChanged=function(){
	  getHomework();
  }
  async function getHomework(){
	  try {
	      vm.homeworks = await http.get("GetHomework",{
	    	  currentPage:vm.currentPage,
			  pageItems:vm.pageItems
	      });
	      if( vm.homeworks.length!==0){
	          vm.totalItems =vm.homeworks[0].count;
	      }
	      vm.homeworks.forEach( (item)=>{
	        if( item.finishTime ){
	          item.finishTime = new Date(item.finishTime.time);
	        }
	      });
	    } catch (error) {
	    	showErrMsg("获取作业列表异常");
	    }
  }
  vm.deleteHomeWork =async function(homeId){
	  let confirm =await showConfrimMsg("是否删除该作业?");
	     if(confirm ==="确定"){
		     let result = await http.get("DeleteHomeWork",{
		    	 homeworkID:homeId
		      });
		      if( result === true ){
		        showErrMsg("作业删除成功");
		        vm.homeworks = await http.get("GetHomework");
		        vm.homeworks.forEach( (item)=>{
		          if( item.finishTime ){
		            item.finishTime = new Date(item.finishTime.time);
		          }
		        });
		      }else{
		        showErrMsg("删除失败");
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