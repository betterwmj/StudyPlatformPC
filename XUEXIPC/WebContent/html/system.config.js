SystemJS.config({
  baseURL: '/',
  meta:{
    './jslib/angular.js': {
      format: 'global', // load this module as a global
      exports: 'angular', // the global property to take as the module value
      deps: []
    },
    './jslib/angular-cookies.min.js': {
      format: 'global', // load this module as a global
      deps: ['angular']
    },
    './jslib/angular-ui-router.js': {
      format: 'amd', // load this module as a global
      deps: ['angular']
    },
    './jslib/ui-bootstrap-tpls.js': {
      format: 'global', // load this module as a global
      deps: ['angular']
    },
   
  },
  map:{
    'plugin-babel': './jslib/plugin-babel.js',
    'systemjs-babel-build': './jslib/systemjs-babel-browser.js',
    "angular":"./jslib/angular.js",
    "angular-cookies":"./jslib/angular-cookies.min.js",
    "angular-ui-router":"./jslib/angular-ui-router.js",
    "angular-ui-bootstrap":"./jslib/ui-bootstrap-tpls.js",
    "bootstrap":"XUEXIPC/html/bootstrap.js"
  },
  transpiler: 'plugin-babel'
});

SystemJS.import("bootstrap");