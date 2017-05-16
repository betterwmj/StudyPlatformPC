import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "publishPaper";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/publishPaper/publishPaper.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
  let vm = this;
  vm.papers = [];
  vm.msg = "";
  vm.paperLink = null;
  vm.isDownload =false;
  vm.currentPage=1;
  vm.pageItems =8;
  vm.maxSize = 5;
  vm.totalItems =0;
  vm.$onInit = async function(){
	  if( $stateParams.currentPage !=null){
			 vm.currentPage = $stateParams.currentPage;
		     vm.pageItems = $stateParams.pageItems;
		     vm.totalItems = $stateParams.totalItems;
	  }
	  getPaper();
	 
  }
  vm.pageChanged=function(){
	  getPaper();
  }
  async function getPaper(){
	  try {
	      vm.papers = await http.get("GetPaper",{
	    	    currentPage:vm.currentPage,
				pageItems:vm.pageItems
	      });
	      
	      vm.papers.forEach( (item)=>{
	    	  item.createTime =new Date(item.createTime.time);
	      });
	      if( vm.papers.length!==0){
	          vm.totalItems =vm.papers[0].count;
	      }
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
          let downLink = document.getElementById("download_paper");
          let event = document.createEvent("MouseEvents"); 
          event.initEvent("click", false, false); 
          downLink.dispatchEvent(event);
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