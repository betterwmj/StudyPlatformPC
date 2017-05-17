import angular from "angular";
import angularCookies from "angular-cookies";
import angularUIRouter from "angular-ui-router";
import angularUIBootstrap from "angular-ui-bootstrap";
import root from "./component/root/root.js";
import commonDialog from "./component/commonDialog/commonDialog.js";
import login from "./component/login/login.js";
import tophead from "./component/tophead/tophead.js";
import studentSidebar from "./component/studentSidebar/studentSidebar.js";
import teacherSidebar from "./component/teacherSidebar/teacherSidebar.js";
import registerStudent from "./component/registerStudent/registerStudent.js"
import registerTeacher from "./component/registerTeacher/registerTeacher.js";
import teacherCenter from "./component/teacherCenter/teacherCenter.js";
import homeworkDetail from "./component/homeworkDetail/homeworkDetail.js";
import teacherManagerClassCreate from "./component/teacherManagerClassCreate/teacherManagerClassCreate.js";
import teacherClassStudent from "./component/teacherClassStudent/teacherClassStudent.js";

import teacherAssignStudent from "./component/teacherAssignStudent/teacherAssignStudent.js";
import teacherPaper from "./component/teacherPaper/teacherPaper.js";
import teacherPaperNavigation from "./component/teacherPaperNavigation/teacherPaperNavigation.js";
import publishPaper from "./component/publishPaper/publishPaper.js";
import paperDetail from "./component/paperDetail/paperDetail.js";
import paperResult from "./component/paperResult/paperResult.js";
import paperResultDetail from "./component/paperResultDetail/paperResultDetail.js";
import studentTest from "./component/studentTest/studentTest.js";
import studentTestPaper from "./component/studentTestPaper/studentTestPaper.js";
import studentTestPaperList from "./component/studentTestPaperList/studentTestPaperList.js";
import teacherHomeWorkNavigation from "./component/teacherHomeworkNavigation/teacherHomeworkNavigation.js";
import homeworkCreate from "./component/homeworkCreate/homeworkCreate.js";
import homeWorkHistory from "./component/homeWorkHistory/homeWorkHistory.js";
import teacherHomeworkResultList from "./component/teacherHomeworkResultList/teacherHomeworkResultList.js";
import teacherHomeworkResultDetail from "./component/teacherHomeworkResultDetail/teacherHomeworkResultDetail.js";
import onlineAnswer from "./component/onlineAnswer/onlineAnswer.js";
import teacherOnlineAnswerNavigation from "./component/teacherOnlineAnswerNavigation/teacherOnlineAnswerNavigation.js";
import teacheronlineHistoryAnswer from "./component/teacheronlineHistoryAnswer/teacheronlineHistoryAnswer.js";
import teacherUploadFile from "./component/teacherUploadFile/teacherUploadFile.js";


import studentTestPaperResultDetail from "./component/studentTestPaperResultDetail/studentTestPaperResultDetail.js";
import studentHomework from "./component/studentHomework/studentHomework.js";
import studentHomeworkList from "./component/studentHomeworkList/studentHomeworkList.js";
import studentHomeworkResultDetail from "./component/studentHomeworkResultDetail/studentHomeworkResultDetail.js";
import onlineQuestion from "./component/onlineQuestion/onlineQuestion.js";
import studentCenter from "./component/studentCenter/studentCenter.js";
import teacherUserInfo from "./component/teacherUserInfo/teacherUserInfo.js";
import publishHomework from "./component/publishHomework/publishHomework.js";
import teacherOnlineAnswerDetail from "./component/teacherOnlineAnswerDetail/teacherOnlineAnswerDetail.js";
import studentHomeworkDetail from "./component/studentHomeworkDetail/studentHomeworkDetail.js";
import studentOnlineNavigation from "./component/studentOnlineNavigation/studentOnlineNavigation.js";
import studentPostQuestion from "./component/studentPostQuestion/studentPostQuestion.js";

import studentOnlineAnswerDetail from "./component/studentOnlineAnswerDetail/studentOnlineAnswerDetail.js";
import studentQuestionHistory from "./component/studentQuestionHistory/studentQuestionHistory.js";

import adminCenter from "./component/adminCenter/adminCenter.js";
import adminSidebar from "./component/adminSidebar/adminSidebar.js";
import httpService from "./service/http.js";
import * as settingRouter from "./config/router.js";

import  cwDialogComponent from "./component/cwDialog/cwDialog.js";
export let name = "app";
export let dependent = [
  'ngCookies','ui.router','ui.bootstrap','ng',
];
export let app = angular.module(name,dependent);
app.config(["$cookiesProvider",function($cookiesProvider){
  let curr = new Date().getTime();
  curr += 1000*60*60*24*120;//120天cookie过期
  $cookiesProvider.defaults.expires = new Date(curr);
}]);
commonDialog(app);
root(app);
login(app);
tophead(app);
studentSidebar(app);
teacherSidebar(app);
registerTeacher(app);
registerStudent(app);
teacherCenter(app);
homeworkDetail(app);
teacherManagerClassCreate(app);
teacherAssignStudent(app);
teacherPaper(app);
teacherPaperNavigation(app);
teacherUploadFile(app);

publishPaper(app);
paperResult(app);
httpService(app);
paperDetail(app);
paperResultDetail(app);
teacherHomeWorkNavigation(app);
homeworkCreate(app);
homeWorkHistory(app);
teacherHomeworkResultList(app);
teacherHomeworkResultDetail(app);
onlineAnswer(app);
teacherOnlineAnswerDetail(app);
teacherOnlineAnswerNavigation(app);
teacheronlineHistoryAnswer(app);
teacherUserInfo(app);
teacherClassStudent(app);

studentTest(app);
studentTestPaperList(app);
studentTestPaper(app);
studentTestPaperResultDetail(app);
studentHomework(app);
onlineQuestion(app);
studentCenter(app);
studentHomeworkList(app);
publishHomework(app);
studentHomeworkDetail(app);
studentHomeworkResultDetail(app);
studentOnlineNavigation(app);
studentPostQuestion(app);
studentQuestionHistory(app);
studentOnlineAnswerDetail(app);
adminCenter(app);
adminSidebar(app);
cwDialogComponent(app);
app.config(["$stateProvider",settingRouter.routerConfig]);


