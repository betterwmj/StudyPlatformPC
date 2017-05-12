"use strict"
const gulp = require('gulp');
const clean = require('gulp-clean');

gulp.task('default',['build_lib'],function() {
  // 将你的默认的任务代码放在这
});

gulp.task('build_lib',["clean"],function(){
  let src = [
    "./node_modules/systemjs/dist/system.src.js",
    "./node_modules/angular/angular.js",
    "./node_modules/angular-ui-router/release/angular-ui-router.js",
    "./node_modules/angular-ui-bootstrap/dist/ui-bootstrap-tpls.js",
  ];
  let dest = "./jslib";
  return gulp.src(src).pipe( gulp.dest(dest));
});

gulp.task("clean",[],function(){
  return gulp.src('./jslib', {read: false})
        .pipe(clean({force: true}));
});