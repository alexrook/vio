'use strict';

window.requestAnimFrame = (function(){
  return  window.requestAnimationFrame       ||
    window.webkitRequestAnimationFrame ||
    window.mozRequestAnimationFrame    ||
    window.oRequestAnimationFrame      ||
    window.msRequestAnimationFrame     ||
    function(/* function */ callback, /* DOMElement */ element){
      window.setTimeout(callback, 1000 / 60);
    };
})();

angular.module('vio.directives', [])
        /* http://stackoverflow.com/questions/14201753/angular-js-how-when-to-use-ng-click-to-call-a-route
         * Click to navigate
         * similar to <a href="#/partial"> but hash is not required, e.g. <div click-link="/partial">
         */
        .directive('clickLink', ['$location', function($location) {
                return {
                    link: function(scope, element, attrs) {
                        element.on('click', function() {
                            scope.$apply(function() {
                                $location.path(attrs.clickLink);
                            });
                        });
                    }
                };
            }])
        .directive('scrollafix', function($document) {
            return function(scope, element, attr) {
                //console.log(attr);
                                var ePos;
                if (attr.scrollafix) { //must be an id
                    ePos = getOffsetPosition('#' + attr.scrollafix);
                } else {
                    ePos = getOffsetPosition(element);
                }
                //   console.log(ePos);  
                //var eWidth = $(element).width();
                // console.log(eWidth);

                var isDock = false;

                $document.bind('scroll', function(event) {
                    update(getDocScrollTop(this)); //document
                });

                function update(sTop) {
                    //scope.a = sTop;
                    if (sTop > ePos.top) {
                        if (!isDock) {
                            element.css({
                                position: 'fixed',
                                'z-index': 3,
                                top: '0%',
                                //  width:eWidth+'px'
                                left: ePos.left + 'px'
                            });
                            isDock = true;
                        }
                    } else {
                        element.css({
                            position: 'static',
                            'z-index': 0
                        });
                        isDock = false;
                    }
                }

                function getDocScrollTop(doc) {
                    return $(document).scrollTop();
                }

                function getOffsetPosition(element) {
                    return $(element).offset();
                }

            };
        }).directive('infiniteLoader', ['$rootScope', '$window', '$timeout',
                                        function($rootScope,
                                                $window,
                                                $timeout) {
        //based on http://binarymuse.github.com/ngInfiniteScroll/
        return {
            link: function postLink(scope, elem, attrs) {

                var scrollDistance = 0,
                        loaderEnabled = true,
                        windowElem = angular.element($window),
                        wElement=angular.element(elem);

                function getGeometry() {
                    console.log(wElement);
                    console.log(wElement.offset().top);
                    console.log(wElement.height());
                    var e=wElement.offset().top + wElement.height();
                    return {
                        windowBottom: windowElem.height() + windowElem.scrollTop(),
                        elementBottom: e,
                        remaining: function() {
                            return this.elementBottom - this.windowBottom;
                        },
                        shouldMore: function() {
                            return  this.remaining() <= windowElem.height() * scrollDistance;
                        }
                    };
                }

                function handler(event) {
                //    console.log(event);
                    if ((attrs.infiniteLoader)) {
                        requestAnimFrame(function() {
                                var g=getGeometry();
                                var sm=g.shouldMore();
                                //console.log(g); console.log(sm);
                            if (sm) {
                                  if ($rootScope.$$phase) {
                                        console.log('phase='+g.elementBottom);
                                        scope.$eval(attrs.infiniteLoader);
                                    } else {
                                        console.log(wElement);
                                        console.log('apply='+g.elementBottom);
                                        scope.$apply(attrs.infiniteLoader);
                                    }
                                   
                            }

                          }
                        );
                        /*
                        return $timeout(function() {
                                                
                            if ($rootScope.$$phase) {
                                scope.$eval(attrs.infiniteLoader);
                            } else {
                                scope.$apply(attrs.infiniteLoader);
                            }
                            loaderEnabled=true;
                        }, 0);
                        */
                    } 
                }

                if (attrs.infiniteLoaderDistance) {
                    scope.$watch(attrs.infiniteScrollDistance, function(value) {
                        return scrollDistance = parseInt(value, 10);
                    });
                }

                if (attrs.infiniteLoaderEvent) {
                    $rootScope.$on(attrs.infiniteLoaderEvent, handler);
                }

                windowElem.on('scroll', handler);
                /*  
                 if (attrs.infiniteLoaderEnabled !== null) {
                 scope.$watch(attrs.infiniteLoaderEnabled, function(value) {
                 // console.log('watch='+value);
                 loaderEnabled = value;
                 handler();
                 
                 });
                 }
                 */
                handler();

            }
        };
    }]);

