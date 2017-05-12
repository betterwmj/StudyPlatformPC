
import {app} from "../../bootstrap.js"

export let dialog = {
    openDialog:null
};
export let name = "cwDialog";
export default function root(app){
  app.component(name,{
    templateUrl:"./component/cwDialog/cwDialog.html",
    controller:["$scope","$element","$state","$q",controller]
  });
}
function controller($scope, $element,$state,$q){
    var vm = this;
    vm.dialogList = [];
    vm.$onInit = function(){
        dialog.openDialog = openDialog;
    };

    function openDialog(params){
        let type = params.type || "normal";
        let deferred = $q.defer();

        let defaultDialog = {
            title:"提示信息",
            content:"",
        };
        switch (type){
            case "normal":
                defaultDialog.btnList = ['取消'];
            break;
            case "warning":
                defaultDialog.btnList = [];
            break
        }
        let dialog = angular.merge(defaultDialog,params);
        dialog.result = deferred.promise
        dialog.click = function(btn){
            deferred.resolve(btn);
            vm.dialogList.shift();
        };
        vm.dialogList.push(dialog);
        let onceChange = $scope.$watch("$ctrl.dialogList[0].content", ()=>{
            document.getElementsByTagName("p")[1].innerHTML = dialog.content.replace(/\n/g, "<br>");
            onceChange();
        });
        return deferred.promise;
    }
}

