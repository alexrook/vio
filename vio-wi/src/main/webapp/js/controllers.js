'use strict';

/* Controllers */

angular.module('vio.controllers', []).
        controller('DocCtrl',
                ['$scope',
                    function($scope, Documents) {

                        $scope.documents = new Documents(function() {
                            $scope.$emit('getdocs');
                        });

                        $scope.setSearch = function() {
                            $scope.searchQuery = $scope.searchQueryText;
                            console.log($scope.searchQuery);
                        };

                        $scope.docCheck = function(doc) {
                            return doc.document ? doc.document : doc;
                        };

                    }]);