import {app} from "../../bootstrap.js"
export let name = "studentTest";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/studentTest/studentTest.html",
    controller:["$scope","$element","$state",'$cookies',"http","$stateParams",controller]
  });
}
function controller($scope,$element,$state,$cookies,http,$stateParams){
  let vm = this;
  vm.msg = "";
  vm.subjects = [];
  vm.$onInit = async function(){
    try {
        vm.subjects = await http.get("GetAllSubject");  
        let result =[];
        for( var i=0 ;i< vm.subjects.length;i++ ){
        	let flag =0;
        	for(var j=0;j<result.length;j++){
	        	if(result[j].SubjectID ===vm.subjects[i].SubjectID){
	        		flag=1;
	        		break;
	        	}
        	}
        	if(flag===0){
        		result.push(vm.subjects[i]);
        	} 	
        	console.log(result);
        }
        vm.subjects  = result;
            
    } catch (error) {
      
    }finally{
      $scope.$applyAsync(null);
    }
  }
}