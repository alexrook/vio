'use strict';

angular.module('vio.factory', [])
        .factory('Shared', function() {
            return {
                state: "list",
                isNewState: function() {
                    return this.state === 'new';
                },
                isListState: function() {
                    return this.state === 'list';
                }
            };
        })
        .factory('Events', function() {
            var events = {};
            return {
                on: function(event, callback) {
                    // console.log('on');
                    // console.log(events);
                    if (!events[event]) {
                        events[event] = $.Callbacks();
                    }
                    events[event].add(callback);
                },
                fire: function(event, data) {
                    if (events[event]) {
                        events[event].fire(data);
                    }
                },
                off: function(event, callback) {
                    //console.log('off');
                    //console.log(events);
                    if (events[event]) {
                        events[event].remove(callback);
                    }
                    console.log(events);
                }
            };
        })
        .factory('Documents', ['$http', 'Events', function($http, events) {
                // console.log(events);

                var EV_GET_LIST = 'listDocs',
                        EV_GET_DOC = 'getDoc',
                        url = (window.appdeb.urlprefix || '') + 'rst/doc',
                        busy = false,
                        paramsFunc = angular.noop;

                return {
                    items: [],
                    item: {},
                    noMoreData: false,
                    range: {
                        itemsPerPage: 35,
                        finish: 0,
                        start: 0,
                        direction: 1 //0- refresh, -1 - backward, +1 - forward
                    },
                    setParams: function(callback) {
                        paramsFunc = callback;
                    },
                    onListDocs: function(callback) {
                        events.on(EV_GET_LIST, callback);
                    },
                    offListDocs: function(callback) {
                        events.off(EV_GET_LIST, callback);
                    },
                    onGetDoc: function(callback) {
                        events.on(EV_GET_DOC, callback);
                    },
                    offGetDoc: function(callback) {
                        events.off(EV_GET_DOC, callback);
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
                        console.log("nextpage");
                        if (busy)
                            return;
                        busy = true;

                        $http.get(url, {
                            headers: {"X-Range": this.buildRangeHeaderStr()},
                            params: paramsFunc()
                        })
                                .success(function(data, status, headers) {

                                    for (var i = 0; i < data.length; i++) {
                                        this.items.push(data[i]);
                                    }

                                    busy = false;

                                    this.noMoreData = data.length === 0;

                                    if (!this.noMoreData) {
                                        this.setRange(headers('X-Content-Range'));
                                    }

                                    events.fire(EV_GET_LIST, data.length > 0);


                                }.bind(this));

                    },
                    getDoc: function(docId) {
                        if (busy)
                            return;
                        busy = true;
                        var docUrl = url + '/' + docId;
                        //  console.log('docUrl=' + docUrl);
                        $http.get(docUrl)
                                .success(function(data, status, headers) {

                                    this.item = data.document ? data.document : data;

                                    busy = false;

                                    events.fire(EV_GET_DOC, this.item);

                                }.bind(this));

                    }
                };

            }])
        .factory('DocumentTypes', ['$http', 'Events', function($http, events) {

                var EV_GET_LIST = 'listDocTypes',
                        EV_GET_ITEM = 'getDocType',
                        url = (window.appdeb.urlprefix || '') + 'rst/doctype',
                        busy = false,
                        paramsFunc = angular.noop;

                return {
                    items: [],
                    getItem: function(itemId) {
                        if (busy)
                            return;
                        busy = true;
                        var Url = url + '/' + itemId;

                        $http.get(Url)
                                .success(function(data, status, headers) {

                                    this.item = data.doctype ? data.doctype : data;

                                    busy = false;

                                    events.fire(EV_GET_ITEM, this.item);

                                }.bind(this));

                    },
                    getList: function() {
                        if (busy)
                            return;
                        busy = true;
                        $http.get(url, {
                            headers: {"X-Range": "0-99999"},
                            params: paramsFunc})
                                .success(function(data, status, headers) {

                                    for (var i = 0; i < data.length; i++) {
                                        this.items.push(data[i]);
                                    }
                                    busy = false;
                                    events.fire(EV_GET_LIST, data.length > 0);

                                }.bind(this));
                    }
                };




            }])
        .factory('Colors', ['$http', 'Events', function($http, events) {

                var EV_GET_LIST = 'listColors',
                        EV_GET_ITEM = 'getColor',
                        url = (window.appdeb.urlprefix || '') + 'rst/color',
                        busy = false,
                        paramsFunc = angular.noop;

                return {
                    items: [],
                    getItem: function(itemId) {
                        if (busy)
                            return;
                        busy = true;
                        var Url = url + '/' + itemId;

                        $http.get(Url)
                                .success(function(data, status, headers) {

                                    this.item = data.doctype ? data.doctype : data;

                                    busy = false;

                                    events.fire(EV_GET_ITEM, this.item);

                                }.bind(this));

                    },
                    getList: function() {
                        if (busy)
                            return;
                        busy = true;
                        $http.get(url, {
                            headers: {"X-Range": "0-99999"},
                            params: paramsFunc})
                                .success(function(data, status, headers) {

                                    for (var i = 0; i < data.length; i++) {
                                        this.items.push(data[i]);
                                    }
                                    busy = false;
                                    events.fire(EV_GET_LIST, data.length > 0);

                                }.bind(this));
                    }
                };

            }])
        .factory('Formats', ['$http', 'Events', function($http, events) {

                var EV_GET_LIST = 'listFormats',
                        EV_GET_ITEM = 'getFormat',
                        url = (window.appdeb.urlprefix || '') + 'rst/format',
                        busy = false,
                        paramsFunc = angular.noop;

                return {
                    items: [],
                    getItem: function(itemId) {
                        if (busy)
                            return;
                        busy = true;
                        var Url = url + '/' + itemId;

                        $http.get(Url)
                                .success(function(data, status, headers) {

                                    this.item = data.doctype ? data.doctype : data;

                                    busy = false;

                                    events.fire(EV_GET_ITEM, this.item);

                                }.bind(this));

                    },
                    getList: function() {
                        if (busy)
                            return;
                        busy = true;
                        $http.get(url, {
                            headers: {"X-Range": "0-99999"},
                            params: paramsFunc})
                                .success(function(data, status, headers) {

                                    for (var i = 0; i < data.length; i++) {
                                        this.items.push(data[i]);
                                    }
                                    busy = false;
                                    events.fire(EV_GET_LIST, data.length > 0);

                                }.bind(this));
                    }
                };

            }]);