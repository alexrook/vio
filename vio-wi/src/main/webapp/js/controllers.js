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
			
			
                        $scope.docCheck = function(doc) {
                            return doc.document ? doc.document : doc;
                        };

	}])
        .controller('DocListCtrl',
                ['$scope', 'Documents',
                    function($scope, Documents) {

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


	}])
        .controller('DocEditCtrl',
                ['$scope','$routeParams', 'Documents',
			function($scope,$routeParams,Documents) {
		
		$scope.legend='Редактирование';
		$scope.rp=$routeParams;
		
		$scope.documents = new Documents(angular.noop(),
                                function(success) {
                                    if (success) {
                                        $scope.$emit('getdoc');
                                    }
                                }
                        );

                $scope.documents.getDoc($routeParams.docId);

        }])
        .controller('DocNewCtrl',
                ['$scope', function($scope) {
			
		$scope.legend='Новый';

	}]);