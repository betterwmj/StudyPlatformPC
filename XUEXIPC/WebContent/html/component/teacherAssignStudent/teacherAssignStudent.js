import {app} from "../../bootstrap.js"
import {dialog} from "../cwDialog/cwDialog.js";
export let name = "teacherAssignStudent";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/teacherAssignStudent/teacherAssignStudent.html",
    controller:["$scope","$element","$state",'$cookies',"http",controller]
  });
}
function controller($scope,$element,$state,$cookies,http){
  let vm = this;
  vm.msg = "";
  let result=null;
  vm.$onInit = async function(){
	  getStudents();
  }
  async function getStudents(){
	  try {
	      vm.userInfo = $cookies.getObject("userInfo");
	      vm.classes = await http.get("GetTeacherClasses");
	      if( vm.classes.length > 0){
	        vm.currentClass =  vm.classes[0];;
	      }
	      result = await http.get("GetStudents");
	      vm.students=result;
	    } catch (error) {
	      showErrMsg("加载数据异常");
	    }
  }
  vm.search = async function(){
	  if( vm.user_number ==="" ||vm.user_number ===undefined){
		  getStudents();
	  }else{
		  let result= await http.get("GetUserInfoByName",{user_number:vm.user_number,type:0});
		  vm.students.splice(0,vm.students.length);
		  vm.students.push(result);
	  }
	  
	  
  }
  vm.toAssign = async function(){
    vm.msg = "";
    let data = {
      classId:vm.currentClass.classes.classId,
      studentIds:[],
    };
    vm.students.forEach( (student)=>{
      if( student.isCheck === true){
        data.studentIds.push(student.userID);
      }
    });
    let result = await http.post('AssignStudent',data);
    if(result === true){
      showErrMsg("分配班级成功");
    }
    else if( result === false ){
      showErrMsg("分配班级失败");
    }else{
      let students =vm.students.find( (item) =>{
    	  return result.userID === item.userID;
      });
      showErrMsg(students.realName+"已加入该班级");
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