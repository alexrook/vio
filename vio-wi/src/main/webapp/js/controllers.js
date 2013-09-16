'use strict';

/* Controllers */

angular.module('vio.controllers', []).
controller('DocCtrl', 
	['$scope','$http',
	function($scope,$http){

		$scope.range = {
			finish: 0,
			count: 15,
			start: 0,
        forward: 1 //0- refresh, -1 - backward, +1 - forward
    };

    function getRangeHeaderStr() {
    	var start, finish;
    	if ($scope.range.forward !== 0) {
    		var start = ($scope.range.forward > 0) ? $scope.range.finish
    		: $scope.range.start - $scope.range.count;
    		var finish = ($scope.range.forward > 0) ? Number($scope.range.finish) + Number($scope.range.count)
    		: Number($scope.range.start);
    	} else {
    		start = $scope.range.start;
    		finish = $scope.range.start + $scope.range.count;
    	}
    	return start + '-' + finish;
    }


    function setRange(rangeStr) {
    	var sr = rangeStr.split('-');
    	$scope.range.start = Number(sr[0]);
    	$scope.range.finish = Number(sr[1]);
    }


    $scope.setFetchNarrow = function(narrow) {
    	$scope.range.forward = narrow;
    };

    $scope.getDocs = function() {
    	$http.get('rst/doc', {
    		headers: {"X-Range": getRangeHeaderStr()}
    	}).success(function(data, status, headers) {
                        //   console.log(headers('Content-Range'));
                        setRange(headers('X-Content-Range'));
                        $scope.docs = data;
                    });
    };

    $scope.setSearch = function() {
    	$scope.searchQuery = $scope.searchQueryText;
    	console.log($scope.searchQuery);
    };

    $scope.selectChange = function() {
    	if (($scope.docs) && ($scope.docs.length < $scope.range.count)) {
    		$scope.range.forward = 0;
    		$scope.getDocs();
    	}
    };

    $scope.docCheck = function(doc) {
    	return doc.document ? doc.document : doc;
    };

    $scope.rowsPerPage = [15, 20, 30, 40, 50];

    $scope.getDocs();

}]);