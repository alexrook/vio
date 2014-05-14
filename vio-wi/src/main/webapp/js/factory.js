'use strict';

angular.module('vio.factory', []).
        factory('Documents', function($http) {

            var Documents = function(paramsFunc, nextSuccess, nextError, itemsPerPage) {

                angular.extend(this, {
                    url: (window.appdeb.urlprefix || '') + 'rst/doc',
                    items: [],
                    item: {},
                    busy: false,
                    nextSuccess: angular.isFunction(nextSuccess) ? nextSuccess : angular.noop,
                    nextError: angular.isFunction(nextError) ? nextError : angular.noop,
                    noMoreData: false,
                    paramsFunc: angular.isFunction(paramsFunc) ? paramsFunc : angular.noop,
                    range: {
                        itemsPerPage: Number(itemsPerPage) || 35,
                        finish: 0,
                        start: 0,
                        direction: 1 //0- refresh, -1 - backward, +1 - forward
                    },
                    setNextSuccess:function(f){
                        this.nextSuccess=
                        angular.isFunction(f) ? f : angular.noop;
                    },
                    buildRangeHeaderStr: function() {
                        var start, finish, range = this.range;
                        if (range.direction !== 0) {
                            start = (range.direction > 0)
                                    //? range.finish //forward
                                    ? this.items.length //forward
                                    : range.start - range.itemsPerPage;
                            finish = (range.direction > 0)
                                   // ? range.finish + range.itemsPerPage //forward
                                    ? this.items.length + range.itemsPerPage //forward
                                    : range.start;
                        } else { //refresh items
                            start = range.start;
                            finish = range.start + range.itemsPerPage;
                        }
                        return start + '-' + finish;
                    },
                    setRange: function(rangeStr) {
                        var sr = rangeStr.split('-');
                        this.range.start = Number(sr[0]);
                        this.range.finish = Number(sr[1]);
                    },
                    nextPage: function() {
                        if (this.busy)
                            return;
                        this.busy = true;

                        $http.get(this.url, {
                            headers: {"X-Range": this.buildRangeHeaderStr()},
                            params: this.paramsFunc()
                        })
                                .success(function(data, status, headers) {

                                    for (var i = 0; i < data.length; i++) {
                                        this.items.push(data[i]);
                                    }

                                    this.busy = false;

                                    this.noMoreData = data.length === 0;

                                    if (!this.noMoreData) {
                                        this.setRange(headers('X-Content-Range'));
                                    }

                                    this.nextSuccess(data.length > 0);


                                }.bind(this));

                    },
                    getDoc: function(docId) {
                        if (this.busy)
                            return;
                        this.busy = true;
                        var docUrl = this.url + '/' + docId;
                        console.log('docUrl=' + docUrl);
                        $http.get(docUrl)
                                .success(function(data, status, headers) {

                                    this.item = data.document ? data.document : data;

                                    this.busy = false;
                                  
                                    this.nextSuccess(docId);
                                  
                                }.bind(this));

                    }
                });

            };

            return new Documents();
        })
        .factory('DocumentTypes', function($http) {

            var DocumentTypes = function(nextSuccess, nextError) {

                angular.extend(this, {
                    url: (window.appdeb.urlprefix || '') + 'rst/doctype',
                    items: [],
                    busy: false,
                    nextSuccess: angular.isFunction(nextSuccess) ? nextSuccess : angular.noop,
                    nextError: angular.isFunction(nextError) ? nextError : angular.noop,
                    getList: function() {
                        if (this.busy)
                            return;
                        this.busy = true;

                        $http.get(this.url, {})
                                .success(function(data, status, headers) {
                                    for (var i = 0; i < data.length; i++) {
                                        this.items.push(data[i]);
                                    }
                                    this.busy = false;
                                    if (angular.isFunction(this.nextSuccess)) {
                                        this.nextSuccess(data.length > 0);
                                    }
                                   
                                }.bind(this));
                    }
                });

            };

            return new DocumentTypes();
        });
