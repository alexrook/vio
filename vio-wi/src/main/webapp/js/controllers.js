'use strict';

/* Controllers */

angular.module('vio.controllers', []).
        controller('DocCtrl',
                ['$scope','Documents','DocumentTypes',
                    function($scope, Documents,DocumentTypes) {

                        $scope.documents = new Documents(function(){
								if ($scope.selectedDocType) {
									return {
									"doctypeId":parseInt($scope.selectedDocType)
									}
								} else {
								return {};
							}
							 },
							 function(success) {
								if (success){
								    $scope.$emit('getdocs');
								} 
							}
			);

                        $scope.setSearch = function() {
                            $scope.searchQuery = $scope.searchQueryText;
                            $scope.$emit('getdocs');
                            console.log($scope.searchQuery);
                        };

                        $scope.docCheck = function(doc) {
                            return doc.document ? doc.document : doc;
                        };
			
			$scope.doctypes = new DocumentTypes(angular.noop());
			
			$scope.doctypes.getList();

                    }]);