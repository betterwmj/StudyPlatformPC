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
  vm.paperLink = null;
  vm.isDownload =false;
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
  vm.downloadPaper =async function(paperId){
	  let result = await http.get("DownLoadPaper",{paperID:paperId});
	  vm.paperLink = result;
      dialog.openDialog({
          type: "normal",
          title: "提示信息",
          content: "试卷生成成功，文件路径如下：\n" +  vm.paperLink + "\n"
      })
      .then( (btn) => {
          if (btn === "确定") {
        	  vm.isDownload =true;
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
  async function showConfrimMsg(confirmMsg) {
      return await dialog.openDialog({
          type: "normal",
          title: "操作提示",
          content: confirmMsg
      });
  }
}