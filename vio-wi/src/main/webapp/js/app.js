'use strict';


// Declare app level module 
angular.module('vio', ['vio.directives', 'vio.controllers','vio.factory']).
  config(['$routeProvider', function($routeProvider) {

    $routeProvider
                .when('/edit',
                        {   templateUrl: 'part/edit.html',
                            controller: 'DocEditCtrl'})
                .when('/list',
                        {   templateUrl: 'part/list.html',
                            controller: 'DocCtrl'})
                .otherwise({redirectTo: '/list'});
                
  }]);
