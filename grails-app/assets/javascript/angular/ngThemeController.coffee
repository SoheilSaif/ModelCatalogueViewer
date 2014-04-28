angular.module('ngThemeControllerModule',['ngResource','viewerServices'])


.controller('ngThemeController',['$scope','$resource','Grails','$state',($scope,$resource,Grails,$state)  ->
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
				theme: object
				children:[],
				onSelect: (branch) ->
					$scope.currentTheme = branch.theme
					alert branch.label + ' ' +branch.theme.id
					return
			}
])