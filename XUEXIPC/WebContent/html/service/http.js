export default function httpService(app){
  app.factory("http",["$q","$http","$httpParamSerializerJQLike","$rootScope",serviceFunc]);
}

function serviceFunc($q,$http,$httpParamSerializerJQLike,$rootScope,$mdDialog){
  const baseUrl = "http://localhost:8080/XUEXI/";
  let service = {
    post:post,
    get:get,
    submitForm:submitForm
  };

  async function submitForm(url,data){
     url = baseUrl + url;
     let deferred = $q.defer();
     let response = null;
     try {
        response = await $http({
          method: 'POST',
          url: url,
          data: data,
          headers: { 'Content-Type': undefined},
          transformRequest: angular.identity
        });
        let result = response.data;
        if( result.code === 0 ){
          deferred.resolve(result.data);
        }else{
          deferred.reject(result.message);
        }
      } catch (error) {
        console.log(error);
        deferred.reject("操作失败");
      }finally{
        window.setTimeout(function(){
          $rootScope.$applyAsync(null);
        },0);
     }
     return deferred.promise;
  }
  
  async function post(url,data,headers){
    url = baseUrl + url;
    headers = headers || {};
    let deferred = $q.defer();
    let response = null;
    try {
      response = await $http({
        method: 'POST',
        url: url,
        data: data,
        headers:headers
      });
      let result = response.data;
      if( result.code === 0 ){
        deferred.resolve(result.data);
      }else{
        deferred.reject(result.message);
      }
    } catch (error) {
      console.log(error);
      deferred.reject("操作失败");
    }finally{
      window.setTimeout(function(){
        $rootScope.$applyAsync(null);
      },0);
    }
    
    return deferred.promise;
  };

  async function get(url,data){
    url = baseUrl + url;
    let deferred = $q.defer();
    let response = null;
    try {
      response = await $http({
        method: 'GET',
        url: url,
        params: data,
      });
      let result = response.data;
      if( result.code === 0 ){
        deferred.resolve(result.data);
      }else{
        deferred.reject(result.message);
      }
    } catch (error) {
      console.log(error);
      deferred.reject("操作失败");
    }finally{
      window.setTimeout(function(){
        $rootScope.$applyAsync(null);
      },0);
    }
    
    return deferred.promise;
  };

//  function alert(config){
//    let parentEle = config.parent;
//    let title = config.title || "提示信息";
//    let content = config.content;
//    let okBtn = config.okBtn || "确定";
//    let event = config.event || null;
//    let alertDialog = $mdDialog.alert()
//      .parent(parentEle)
//      .clickOutsideToClose(false)
//      .title(title)
//      .textContent(content)
//      .ok(okBtn)
//      .targetEvent(event);
//    return $mdDialog.show(alertDialog);
//  }
//  
//  function confirm(config){
//    let parentEle = config.parent;
//    let title = config.title || "确认信息";
//    let content = config.content;
//    let okBtn = config.okBtn || "确定";
//    let event = config.event || null;
//    let confirmDialog = $mdDialog.confirm()
//      .parent(parentEle)
//      .clickOutsideToClose(false)
//      .title(title)
//      .textContent(content)
//      .ok(okBtn)
//      .cancel("取消")
//      .targetEvent(event);
//    return $mdDialog.show(confirmDialog);
//  }

//  function wait(parent){
//    return $mdDialog.show({
//      controller: [function(){}],
//      template: '<div layout="column" style="width:100px;"><md-progress-circular md-mode="indeterminate"></md-progress-circular><div>',
//      parent: angular.element(document.body),
//    })
  //}
  return service;
}