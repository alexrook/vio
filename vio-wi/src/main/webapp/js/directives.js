'use strict';

angular.module('vio.directives', [])
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
                                top: '0%',
                                //  width:eWidth+'px'
                                left: ePos.left + 'px'
                            });
                            isDock = true;
                        }
                    } else {
                        element.css({
                            position: 'static'
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
        }).directive('infiniteLoader', ['$rootScope', '$window', '$timeout', function($rootScope,
            $window,
            $timeout) {
        return {
            link: function postLink(scope, elem, attrs) {

                var scrollDistance = 0,
                        loaderEnabled = true,
                        windowElem = angular.element($window);

                function getGeometry() {
                    return {
                        windowBottom: windowElem.height() + windowElem.scrollTop(),
                        elementBottom: elem.offset().top + elem.height(),
                        remaining: function() {
                            return this.elementBottom - this.windowBottom;
                        },
                        shouldMore: function() {
                            return  this.remaining() <= windowElem.height() * scrollDistance;
                        }
                    };
                }

                function handler() {
                    return $timeout(function() {
                        //   console.log(loaderEnabled);
                        if ((attrs.infiniteLoader) && (getGeometry().shouldMore()) && loaderEnabled) {
                            if ($rootScope.$$phase) {
                                scope.$eval(attrs.infiniteLoader);
                            } else {
                                scope.$apply(attrs.infiniteLoader);
                            }
                        }
                    }, 0);

                }

                if (attrs.infiniteLoaderDistance) {
                    scope.$watch(attrs.infiniteScrollDistance, function(value) {
                        return scrollDistance = parseInt(value, 10);
                    });
                }

                if (attrs.infiniteLoaderEvent) {
                    scope.$on(attrs.infiniteLoaderEvent, handler);
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

