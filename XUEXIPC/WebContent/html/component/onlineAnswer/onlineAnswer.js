import {app} from "../../bootstrap.js"
export let name = "onlineAnswer";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/onlineAnswer/onlineAnswer.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
	let vm = this;
	vm.questionsList=null;
	vm.currentClass =null;
	vm.getClassQuestion =getClassQuestion;
	vm.$onInit = async function(){
		let classes = await http.get("GetTeacherClasses");
		vm.classes = classes;
		
		if( $stateParams.currentClass !=null){
			vm.currentClass = $stateParams.currentClass;
			getClassQuestion();
		}else if( vm.classes.length > 0){
	        vm.currentClass =  vm.classes[0];;
	        getClassQuestion();
	      }
		
    }
    async function  getClassQuestion(){
	    console.log(vm.currentClass);
		let result = await http.get("GetClassQuestion",{
			classID:vm.currentClass.classes.classId
		});
		vm.questionsList = result;
		vm.questionsList.forEach( async (item)=>{
		   item.createTime =new Date(item.createTime.time);
		   let studentInfo = await http.get("GetStudents",{
				userID:item.studentId
		   });
		   if(studentInfo.length!==0)
		      item.studentName =studentInfo[0].realName;
	    });
     }
  
}