
var mod = angular.module('infinite-loader', []);

mod.directive('infiniteLoader', ['$rootScope','$window','$timeout',function($rootScope,
                                                                            $window,
                                                                            $timeout){
        return {
            link: function postLink(scope, elem, attrs){
                    
                    var scrollDistance=0,eventName=attrs.infiniteLoaderEvent||false;
                    
                    windowElem = angular.element($window);
                    
                    function getGeometry(){
                        return {
                            windowBottom :windowElem.height() + windowElem.scrollTop(),
                            elementBottom : elem.offset().top + elem.height(),
                            remaining:function(){
                                return this.elementBottom - this.windowBottom;
                            },
                            shouldMore:function(){
                                return this.remaining() <= windowElem.height() * scrollDistance;
                            }
                        }
                    }
                    
                    function handler(){
                        if (attrs.infiniteLoader) {
                            if ($rootScope.$$phase){
                                scope.$eval(attrs.infiniteLoader);
                            } else {
                                scope.$apply(attrs.infiniteLoader);
                            }
                        }
                    }
                                        
                    if (attrs.infiniteLoaderDistance) {
                        scope.$watch(attrs.infiniteScrollDistance, function(value) {
                            return scrollDistance = parseInt(value, 10);
                        });
                    }
                    
                    if (eventName) {
                     //   console.log(scope[watcherName]);
                        scope.$on(eventName, function(value) {
                            if (value) {
                                if (getGeometry().shouldMore()){
                                        handler();
                                }
                            } 
                        });
                    }
                    
                    handler();

            }
        }
    }]);

