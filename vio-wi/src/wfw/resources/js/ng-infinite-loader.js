
var mod = angular.module('infinite-loader', []);

mod.directive('infiniteLoader', ['$rootScope','$window','$timeout',function($rootScope,
                                                                            $window,
                                                                            $timeout){
        return {
            link: function postLink(scope, elem, attrs){
                    
                    var scrollDistance=0,
                        eventName=attrs.infiniteLoaderEvent||false;
                    
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
                        /*
                         * https://github.com/angular/angular.js/issues/734#issuecomment-3657272
                         *The $evalAsync is after the DOM construction but before the browser renders.
                         *I believe that is the time you want to attach the jquery plugins.
                         *otherwise you will have flicker.
                         *if you really want to do after the browser render you can do $timeout(fn, 0);
                         */

                        return $timeout(function(){
                            if ((attrs.infiniteLoader)&&(getGeometry().shouldMore())) {
                                if ($rootScope.$$phase){
                                    scope.$eval(attrs.infiniteLoader);
                                } else {
                                    scope.$apply(attrs.infiniteLoader);
                                }
                            }
                        },0);
                    }
                                        
                    if (attrs.infiniteLoaderDistance) {
                        scope.$watch(attrs.infiniteScrollDistance, function(value) {
                            return scrollDistance = parseInt(value, 10);
                        });
                    }
                    scope.$on('loadmore',handler);  
                    
                    windowElem.on('scroll',handler);
                    
                    handler();
                 
            }
        }
    }]);

