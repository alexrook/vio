'use strict';

angular.module('vio.factory', []).
        factory('Documents', function($http) {

            var Documents = function(nextSuccess, nextError, itemsPerPage) {

                angular.extend(this, {
                    url: 'rst/doc',
                    items: [],
                    busy: false,
                    nextSuccess: nextSuccess,
                    nextError: nextError,
                    range: {
                        itemsPerPage: Number(itemsPerPage) || 15,
                        finish: 0,
                        start: 0,
                        direction: 1 //0- refresh, -1 - backward, +1 - forward
                    }
                });

            };

            Documents.prototype.buildRangeHeaderStr = function() {
                var start, finish, range = this.range;
                if (range.direction !== 0) {
                    start = (range.direction > 0)
                            ? range.finish //forward
                            : range.start - range.itemsPerPage;
                    finish = (range.direction > 0)
                            ? range.finish + range.itemsPerPage //forward
                            : range.start;
                } else { //refresh items
                    start = range.start;
                    finish = range.start + range.itemsPerPage;
                }
                return start + '-' + finish;
            };

            Documents.prototype.setRange = function(rangeStr) {
                var sr = rangeStr.split('-');
                this.range.start = Number(sr[0]);
                this.range.finish = Number(sr[1]);
            };

            Documents.prototype.nextPage = function() {
                if (this.busy)
                    return;
                this.busy = true;

                $http.get('rst/doc', {headers: {"X-Range": buildRangeHeaderStr()}})
                        .success(function(data, status, headers) {
                            this.setRange(headers('X-Content-Range'));
                            for (var i = 0; i < data.lenght; i++) {
                                this.items.push(data[i]);
                            }
                            if (angular.isFunction(this.nextSuccess)) {
                                this.nextSuccess();
                            }
                        }.bind(this));

            };

            return Documents;
        });
