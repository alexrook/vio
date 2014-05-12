'use strict';

/* Controllers */

angular.module('vio.controllers', [])
        .controller('MainCtrl',
                ['$scope', function($scope) {

                        $scope.setSearch = function() {
                            $scope.searchQuery = $scope.searchQueryText;
                            $scope.$emit('getdocs');
                            console.log($scope.searchQuery);
                        };

                    }])
        .controller('DocCtrl',
                ['$scope', 'Documents', 'DocumentTypes',
                    function($scope, Documents, DocumentTypes) {

                        $scope.documents = new Documents(function() {
                            if ($scope.selectedDocType) {
                                return {
                                    "doctypeId": parseInt($scope.selectedDocType)
                                };
                            } else {
                                return {};
                            }
                        },
                                function(success) {
                                    if (success) {
                                        $scope.$emit('getdocs');
                                    }
                                }
                        );

                        $scope.docCheck = function(doc) {
                            return doc.document ? doc.document : doc;
                        };

                        $scope.doctypes = new DocumentTypes(angular.noop());

                        $scope.doctypes.getList();

                    }])
        .controller('DocEditCtrl',
                ['$scope', function($scope) {


                    }]);