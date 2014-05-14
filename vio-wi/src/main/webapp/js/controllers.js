'use strict';

/* Controllers */

angular.module('vio.controllers', [])
        .controller('MainCtrl',
                ['$scope','Documents', function($scope,documents) {

			documents.setNextSuccess(function(success){
						if (success) {
							$scope.$emit('getdocs');
						}
					});
		
                        $scope.setSearch = function() {
                            $scope.searchQuery = $scope.searchQueryText;
                            $scope.$emit('getdocs','setsearch');
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
			$scope.documents={};
	
			/*
			 * для большого массива documents.items наблюдается
			 * низкая производительность работы при переходе
			 * редактирование ->список
			 * для избежания этого массив урезается до шапки размером
			 * 0...itemsPerPage*2
			 *
			 * TODO: переписать директиву infiniteLoader (управлять размерами массива?)
			 */
			
			if (documents.items.length>documents.range.itemsPerPage*2) {
				documents.items=documents.items.slice(0,
								      documents
								      .range
								      .itemsPerPage*2);
			}
			
			 
			$scope.documents=documents;
			
			//$scope.$emit('getdocs');
			
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