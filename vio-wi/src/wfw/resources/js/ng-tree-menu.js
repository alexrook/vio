
var mod = angular.module('tree-menu', []);

mod.directive('treeMenu', function() {
    return {
        /*
         *  When you create a directive, it is restricted to attribute only by default. 
         * In order to create directives that are triggered by element name, 
         * you need to use the restrict option.
         restrict: 'A',
         scope: {
         customer: '=customer'
         },
         */
        templateUrl: 'partials/widgets/tree-menu.html'
    };
});
    