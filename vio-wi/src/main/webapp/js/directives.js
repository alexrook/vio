'use strict';

angular.module('drag', []).
directive('draggable', function($document) {

  return  function(scope, element, attr) {
    var startX = 0, startY = 0, x = 0, y = 0;
    element.css({
     position: 'relative',
     backgroundColor: 'lightgrey',
     cursor: 'pointer'
   });
    
    element.bind('mousedown', function(event) {
        // Prevent default dragging of selected content
        event.preventDefault();
        startX = event.screenX - x;
        startY = event.screenY - y;
        $document.bind('mousemove', mousemove);
        $document.bind('mouseup', mouseup);
      });
    
    function mousemove(event) {
      y = event.screenY - startY;
      x = event.screenX - startX;
      element.css({
        top: y + 'px',
        left:  x + 'px'
      });
    }
    
    function mouseup() {
      $document.unbind('mousemove', mousemove);
      $document.unbind('mouseup', mouseup);
    }
  }
}).
directive('scrollafix', function($document) {
  return function(scope, element, attr){
    var pos=getOffsetPosition(element);
    console.log(pos);  
    var isDock=false;
    element.css({
     backgroundColor: 'red',
   });

    $document.bind('scroll', function(event) {
      update(getDocScrollTop(this)); //document
    });

    function update(sTop){
      scope.a=sTop;
      if (sTop>=pos[1]){
        if (!isDock) {
          element.css({
            position: 'fixed',
            top:'0%'
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
      return doc.documentElement.scrollTop;
    }

    function getOffsetPosition(element) {
      var offsetLeft = 0, offsetTop = 0,e=element[0];
      do {
       offsetLeft += e.offsetLeft;
       offsetTop  += e.offsetTop;
     } while (e = e.offsetParent);
     return [offsetLeft, offsetTop];
   }

 }
});

