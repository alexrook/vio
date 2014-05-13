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
			
			$scope.aaa='erf';

	}])
        .controller('DocListCtrl',
                ['$scope', 'Documents',
                    function($scope, documents) {
			
			documents.nextSuccess=function(success) {
                                    if (success) {
                                        $scope.$emit('getdocs');
                                    }
                                };
				
			$scope.documents=documents;

	}])
        .controller('DocEditCtrl',
                ['$scope','$routeParams', 'Documents',
			function($scope,$routeParams,documents) {
		
		console.log($scope.aaa);
		
		$scope.legend='Редактирование';
		$scope.routeParams=$routeParams;
		
		documents.nextSuccess=function(success) {
                                    if (success) {
                                        $scope.$emit('getdoc');
                                    }
                                };
				
		$scope.documents =documents;

                $scope.documents.getDoc($routeParams.docId);

        }])
        .controller('DocNewCtrl',
                ['$scope', function($scope) {
			
		$scope.legend='Новый';

	}]);