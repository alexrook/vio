'use strict';

/* Controllers */

angular.module('vio.controllers', [])
        .controller('MainCtrl',
                ['$scope', 'Documents', 'Shared', function($scope, documents, shared) {

                        $scope.shared = shared;

                        $scope.setSearch = function() {
                            $scope.searchQuery = $scope.searchQueryText;
                            $scope.$emit('getdocs', 'setsearch');
                            console.log($scope.searchQuery);
                            console.log('in mainscope');
                        };


                        $scope.docCheck = function(doc) {
                            return doc.document ? doc.document : doc;
                        };


                    }])
        .controller('DocListCtrl',
                ['$scope', 'Documents', 'Shared',
                    function($scope, documents, shared) {

                        shared.state = 'list';

                        var listDocsHndl = function(success) {
                            if (success) {
                                console.log('in doclistscope');
                                $scope.$emit('getdocs');
                            }
                        };

                        $scope.documents = {};

                        documents.onListDocs(listDocsHndl);
                        /*
                         * для большого массива documents.items наблюдается
                         * низкая производительность работы при переходе
                         * редактирование ->список
                         * для избежания этого массив урезается до шапки размером
                         * 0...itemsPerPage*2
                         *
                         * TODO: переписать директиву infiniteLoader (управлять размерами массива?)
                         */
                        /*
                         if (documents.items.length>documents.range.itemsPerPage*2) {
                         documents.items=documents.items.slice(0,
                         documents
                         .range
                         .itemsPerPage*2);
                         }*/

                        $scope.documents = documents;

                        $scope.$on('$destroy', function() {
                            documents.offListDocs(listDocsHndl);
                        });
                        //$scope.$emit('getdocs');

                    }])
        .controller('DocEditCtrl',
                ['$scope', '$routeParams', 'Documents', 'DocumentTypes', 'Colors', 'Shared',
                    function($scope, $routeParams, documents, doctypes, colors, shared) {
                        shared.state = 'edit';
                        $scope.legend = 'Редактирование';
                        $scope.routeParams = $routeParams;

                        $scope.documents = documents;
                        $scope.doctypes = doctypes;
                        $scope.colors = colors;

                        $scope.documents.getItem($routeParams.docId);
                        $scope.doctypes.getItemsList();
                        $scope.colors.getItemsList();

                    }])
        .controller('DocNewCtrl',
                ['$scope', 'Shared', function($scope, shared) {
                        shared.state = 'new';
                        $scope.legend = 'Новый';

                    }]);