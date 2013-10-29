
var mod = angular.module('tree-menu', []);

mod.directive('treeMenu', function() {
    return {
        /*
         restrict: 'E',
         scope: {
         customer: '=customer'
         },
         */
        templateUrl: 'partials/widgets/tree-menu.html'
    };
});
    