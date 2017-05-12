import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "publishPaper";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/publishPaper/publishPaper.html",
    controller:["$scope","$element","$state",'$cookies',"http",controller]
  });
}
function controller($scope,$element,$state,$cookies,http){
  let vm = this;
  vm.papers = [];
  vm.msg = "";
  vm.$onInit = async function(){
    try {
      vm.papers = await http.get("GetPaper");
      vm.papers.forEach( (item)=>{
    	  item.createTime =new Date(item.createTime.time);
      });
      
    } catch (error) {
      showErrMsg("获取试卷信息异常");
    }
  }

  vm.publishPaper = async function(paper){
    try {
      let result = await http.get("PublishPaper",{
        paperID:paper.testpaperID
      });
      if( result === true ){
        showErrMsg("发布试卷成功");
        vm.papers = await http.get("GetPaper");
        vm.papers.forEach( (item)=>{
      	  item.createTime =new Date(item.createTime.time);
        });
      }else{
        showErrMsg("发布试卷失败");
      }
    } catch (error) {
      showErrMsg("发布试卷失败");
    }  
  }
  vm.deletePaper =async function(paperId){
	  let confirm =await showConfrimMsg("是否删除该试卷?");
	     if(confirm ==="确定"){
		     let result = await http.get("DeletePaper",{
		    	 paperID:paperId
		      });
		      if( result === true ){
		        showErrMsg("试卷删除成功");
		        vm.papers = await http.get("GetPaper");
		        vm.papers.forEach( (item)=>{
		      	  item.createTime =new Date(item.createTime.time);
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