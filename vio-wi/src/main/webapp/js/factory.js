'use strict';

var RestStorage = function ($http, events, url,
        itemCheckName, paramsFunc) {

    this.http = $http;
    this.events = events;
    this.baseUrl = this.debugUrl() + url;
    this.headers = {"X-Range": "0-99999"};
    this.paramsFunc = angular.isFunction(paramsFunc) ? paramsFunc : angular.noop;
    this.itemCheckName = itemCheckName;
    this.item = {};
    this.items = [];
    this.noMoreData = false;
    this.range = {
        itemsPerPage: 35,
        finish: 0,
        start: 0,
        direction: 1 //0- refresh, -1 - backward, +1 - forward
    };
    this.EV_GET_LIST = 'list' + this.itemCheckName.toUpperCase() + 's';
    this.EV_GET_ITEM = 'get' + this.itemCheckName.toUpperCase();
    this.EV_FILL_ITEM = 'fill' + this.itemCheckName.toUpperCase();
};

RestStorage.prototype.debugUrl = function () {
    var result = '';
    if (window.appdeb) {
        result = window.appdeb.urlprefix ? window.appdeb.urlprefix : '';
    }
    ;
    return result;
}
RestStorage.prototype.getItem = function (itemId) {

    var Url = this.baseUrl + '/' + itemId;

    return this.http.get(Url)
            .then(function (response) {

                this.item = angular.extend(this.item,
                        response.data[this.itemCheckName] ? response.data[this.itemCheckName] : response.data);

                this.busy = false;

                this.events.fire(this.EV_GET_ITEM, this.item);

                return response;
            }.bind(this));

};

/*
 * returns lazy fields
 */
RestStorage.prototype.getItemField = function (itemId, fieldName) {

    var Url = this.baseUrl + '/' + itemId + '/' + fieldName.toLowerCase();

    return this.http.get(Url)
            .then(function (response) {

            //    console.log(response.headers("Content-Type"));

                this.item[fieldName] = response.data[fieldName] ?
                        response.data[fieldName] : response.data;

                this.events.fire(this.EV_FILL_ITEM, this.item);

                return response;
            }.bind(this));

};

RestStorage.prototype.refreshItemsList = function () {
    //TODO: move refresh logic to getItemList(nextPage?)
    //for cleanup/splice items array relying on range.direction value

    this.items = [];
    return this.getItemsList();
};

RestStorage.prototype.getItemsList = function () {

    return this.http.get(this.baseUrl, {
        headers: this.headers,
        params: this.paramsFunc()})
            .then(function (response) {

                for (var i = 0; i < response.data.length; i++) {
                    this.items.push(response.data[i]);
                }
                this.busy = false;
                this.events.fire(this.EV_GET_LIST, response.data.length > 0);


                return response;

            }.bind(this));

};

RestStorage.prototype.buildRangeHeaderStr = function () {
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
};

RestStorage.prototype.setRange = function (rangeStr) {
    var sr = rangeStr.split('-');
    this.range.start = Number(sr[0]);
    this.range.finish = Number(sr[1]);
};

RestStorage.prototype.nextPage = function () {
    console.log("nextpage");

    this.headers = {"X-Range": this.buildRangeHeaderStr()};

    return this.getItemsList()
            .then(function (response) {

                //  console.log(response);
                this.noMoreData = response.data.length === 0;

                if (!this.noMoreData) {
                    this.setRange(response.headers('X-Content-Range'));
                }
            }.bind(this));
};



angular.module('vio.factory', [])
        .factory('Shared', function () {
            return {
                state: "list",
                isNewState: function () {
                    return this.state === 'new';
                },
                isEditState: function () {
                    return this.state === 'edit';
                },
                isListState: function () {
                    return this.state === 'list';
                }
            };
        })
        .factory('Events', function () {
            var events = {};
            return {
                on: function (event, callback) {
                    if (!events[event]) {
                        events[event] = $.Callbacks();
                    }
                    events[event].add(callback);
                },
                fire: function (event, data) {
                    if (events[event]) {
                        events[event].fire(data);
                    }
                },
                off: function (event, callback) {
                    if (events[event]) {
                        events[event].remove(callback);
                    }
                }
            };
        })
        .factory('Documents', ['$http', 'Events', function ($http, events) {


                //  console.log(baseUrl);
                return angular.extend(new RestStorage($http, events,
                        'rst/doc',
                        'document'),
                        {
                            setParams: function (callback) {
                                this.paramsFunc = callback;
                            },
                            onListDocs: function (callback) {
                                events.on(this.EV_GET_LIST, callback);
                            },
                            offListDocs: function (callback) {
                                events.off(this.EV_GET_LIST, callback);
                            },
                            onGetDoc: function (callback) {
                                events.on(this.EV_GET_ITEM, callback);
                            },
                            offGetDoc: function (callback) {
                                events.off(this.EV_GET_ITEM, callback);
                            },
                            getDocumentType: function (itemId) {
                                return this.getItemField(itemId, 'docType');
                            },
                            getDocumentDesc: function (itemId) {
                                return this.getItemField(itemId, 'description');
                            }

                        });



            }])
        .factory('DocumentTypes', ['$http', 'Events', function ($http, events) {
                return new RestStorage($http, events,
                        'rst/doctype',
                        'doctype');

            }])
        .factory('Colors', ['$http', 'Events', function ($http, events) {
                return new RestStorage($http, events,
                        'rst/color',
                        'color');

            }])
        .factory('Formats', ['$http', 'Events', function ($http, events) {
                return new RestStorage($http, events,
                        'rst/format',
                        'format');
            }]);