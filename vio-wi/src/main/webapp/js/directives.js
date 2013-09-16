'use strict';

angular.module('vio.directives', [])
.directive('scrollafix', function($document) {
  return function(scope, element, attr){
    var ePos=getOffsetPosition(element);
 //   console.log(ePos);  
 var eWidth=$(element).width();
   // console.log(eWidth);

   var isDock=false;

   $document.bind('scroll', function(event) {
      update(getDocScrollTop(this)); //document
    });

   function update(sTop){
    scope.a=sTop;
    if (sTop>ePos.top){
      if (!isDock) {
        element.css({
          position: 'fixed',
          top:'0%',
          width:eWidth+'px'
        });
        isDock=true;
      }
    } else {
      element.css({
        position: 'static'
      });
      isDock=false;
    }
  }

  function getDocScrollTop(doc){
    return $(document).scrollTop();
  }

  function getOffsetPosition(element) {
    return $(element).offset();
  }

}
});

