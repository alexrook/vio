'use strict';

/* Controllers */

angular.module('vio.controllers', []).
        controller('DocCtrl',
                ['$scope','Documents',
                    function($scope, Documents) {

                        $scope.documents = new Documents(function(success) {
                            
                            if (success){
                                $scope.$emit('getdocs');
                            } 
                        });

                        $scope.setSearch = function() {
                            $scope.searchQuery = $scope.searchQueryText;
                            $scope.$emit('getdocs');
                            console.log($scope.searchQuery);
                        };

                        $scope.docCheck = function(doc) {
                            return doc.document ? doc.document : doc;
                        };

                    }]);