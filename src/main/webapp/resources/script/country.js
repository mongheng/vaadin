/**
 * 
 */
angular.module('vaadinproject', []).controller('ctrl', function($scope) {
	$scope.names = [ {
		name : 'Jani',
		country : 'Norway'
	}, {
		name : 'Hege',
		country : 'Sweden'
	}, {
		name : 'Kai',
		country : 'Denmark'
	} ];
});