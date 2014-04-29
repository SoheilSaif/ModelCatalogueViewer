angular.module('ngThemeControllerModule',['ngResource','viewerServices'])


.controller('ngThemeController',['$scope','$resource','Grails','$state','$rootScope',($scope,$resource,Grails,$state,$rootScope)  ->
	$scope.themes = []
	$scope.currentTheme = null

	resource = Grails.getRestAPIResource("models").get()
	resource.$promise.then((result,responseHeaders) ->
			filleThemes(result.objects)
			return
		,
		(httpResponse) ->
			return
	)

	filleThemes = (objects)->
		for object in objects
			$scope.themes.push {
				label: object.name,
				model: object
				children:[],
				onSelect: (branch) ->
					$scope.currentTheme = branch.model

					$state.go("dataElementList",{id:branch.model.id})

#					alert branch.label + ' ' +branch.model.id
					return
			}
])