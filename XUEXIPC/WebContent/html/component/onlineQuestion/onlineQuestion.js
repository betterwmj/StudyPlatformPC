import {app} from "../../bootstrap.js"
export let name = "onlineQuestion";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/onlineQuestion/onlineQuestion.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
		let vm = this;
		vm.questionsList=null;
		vm.currentClass =null;
		vm.getClassQuestion = getClassQuestion;
		vm.currentPage=1;
		vm.pageItems =8;
		vm.maxSize = 5;
		vm.totalItems =0;
		vm.$onInit = async function(){
				let classes = await http.get("GetAllSubject");
				vm.classes = classes;
				vm.classes.forEach( async (item)=>{
						let result =await http.get("GetClassNameByClassId",{"classID":item.classId});
						if(result!=null)
						  item.className=result.className;
				});
				
				if( $stateParams.currentClass !=null){
						vm.currentClass = $stateParams.currentClass;
						vm.currentPage = $stateParams.currentPage;
					     vm.pageItems = $stateParams.pageItems;
					     vm.totalItems = $stateParams.totalItems;
				}else if( vm.classes && vm.classes.length > 0){
						vm.currentClass = vm.classes[0];
				}
				if(vm.currentClass!=null)
				   getClassQuestion();
		}
		vm.pageChanged=function(){
			getClassQuestion();
		 }
       async function  getClassQuestion(){
				console.log(vm.currentClass);
				let result = await http.get("GetClassQuestion",{
						classID:vm.currentClass.classId,
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
						if(studentInfo.length!==0){
							item.studentName =studentInfo[0].realName;
						}
				});
     }
}