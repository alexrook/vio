'use strict';

// Declare app level module 
angular.module('vio', ['ngRoute', 'vio.directives', 'vio.controllers', 'vio.factory']).
        config(['$routeProvider', /* '$httpProvider',*/ function ($routeProvider/*, $httpProvider*/) {


                /*
                 var TEXT_PLAIN = "text/plain";
                 
                 $httpProvider.defaults.transformResponse =
                 angular.isArray($httpProvider.defaults.transformResponse) ?
                 $httpProvider.defaults.transformResponse : [$httpProvider.defaults.transformResponse];
                 
                 
                 $httpProvider.defaults.transformResponse.unshift(function (data, headers) {
                 
                 var contentType = headers('Content-Type');
                 
                 if ((contentType && contentType.indexOf(TEXT_PLAIN) === 0)) {
                 data = '//' + data ;
                 }
                 
                 return data;
                 });
                 */
                $routeProvider
                        .when('/new',
                                {templateUrl: 'part/edit.html',
                                    controller: 'DocNewCtrl'})
                        .when('/edit/:docId',
                                {templateUrl: 'part/edit.html',
                                    controller: 'DocEditCtrl'})
                        .when('/list',
                                {templateUrl: 'part/list.html',
                                    controller: 'DocListCtrl'})
                        .when('/about',
                                {templateUrl: 'part/about.html',
                                    controller: 'DocCtrl'})
                        .otherwise({redirectTo: '/list'});


            }]);
