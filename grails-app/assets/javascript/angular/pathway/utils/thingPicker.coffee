module = angular.module('utils.thingPicker', ["ngTable"])

module.controller('ThingPickerCtrl',['$scope', 'ngTableParams', '$filter',($scope, ngTableParams,$filter) ->

	$scope.compress = false




	$scope.tableParams = new ngTableParams {
		page: 1,  # show first page
		count: 5 # count per page
	}, {
		getData: ($defer, params) ->
			params.total($scope.selectedThings.length)
			$defer.resolve($scope.selectedThings.slice((params.page() - 1) * params.count(), params.page() * params.count()))
	}

])

#
# A widget to select one or more things from a list of things
#
module.directive 'mcThingPicker', ->
	return{
		replace: true,
		templateUrl: '/'+grailsAppName+'/assets/angular/pathway/utils/thingPicker.html',
		scope: {
			widgetName: '@'
			allThingsResource: '='
			selectedThings: '='
		},
		controller: 'ThingPickerCtrl'
	}
