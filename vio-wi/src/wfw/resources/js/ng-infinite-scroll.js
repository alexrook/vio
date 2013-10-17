
var mod;

mod = angular.module('infinite-scroll', []);

mod.directive('infiniteScroll', [
    '$rootScope', '$window', '$timeout', function($rootScope, $window, $timeout) {
        return {
            link: function(scope, elem, attrs) {
                var checkWhenEnabled, handler, scrollDistance, scrollEnabled;
                $window = angular.element($window);
                scrollDistance = 0;
                if (attrs.infiniteScrollDistance != null) {
                    scope.$watch(attrs.infiniteScrollDistance, function(value) {
                        return scrollDistance = parseInt(value, 10);
                    });
                }
                scrollEnabled = true;
                checkWhenEnabled = false;
                
                if (attrs.infiniteScrollDisabled != null) {
                    scope.$watch(attrs.infiniteScrollDisabled, function(value) {
                        scrollEnabled = !value;
                        if (scrollEnabled && checkWhenEnabled) {
                            checkWhenEnabled = false;
                            return handler();
                        }
                    });
                }

                handler = function() {
                    console.log('handler');
                    
                    function scrollInformer(){
                        return {
                            windowHeight:$window.height(),
                            windowBottom :$window.height() + $window.scrollTop(),
                            elementBottom : elem.offset().top + elem.height(),
                            remaining:function(){
                                return this.elementBottom - this.windowBottom;
                            },
                            shouldScroll:function(){
                                return this.remaining() <= $window.height() * scrollDistance;
                            }
                        }
                    }
                    
                    var info=scrollInformer();
                    
                    if (attrs.infiniteScrollConsole) {//debugger
                        
                        var updateConsole=function(){
                           scope[attrs.infiniteScrollConsole]=info;
                        }

                        if ($rootScope.$$phase) {
                            scope.$eval(updateConsole);
                        } else {
                            scope.$apply(updateConsole);
                        }
                    }//debugger

                    if (info.shouldScroll && scrollEnabled) {
                        if ($rootScope.$$phase) {
                           // return scope.$eval(attrs.infiniteScroll);
                           scope.$eval(attrs.infiniteScroll);
                           return info;
                        } else {
                           // return scope.$apply(attrs.infiniteScroll);
                           scope.$apply(attrs.infiniteScroll);
                           return info;
                        }
                    } else if (info.shouldScroll) {
                        return checkWhenEnabled = true;
                    }
                }; //handler

                $window.on('scroll', handler);
                scope.$on('$destroy', function() {
                    return $window.off('scroll', handler);
                });

                return $timeout((function() {
                    if (attrs.infiniteScrollImmediateCheck) {
                        if (scope.$eval(attrs.infiniteScrollImmediateCheck)) {
                            return handler();
                        }
                    } else {
                        return handler();
                    }
                }), 0);
            }
        };
    }
]);
