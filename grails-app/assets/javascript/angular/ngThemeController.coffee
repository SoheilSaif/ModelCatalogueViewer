angular.module('ngThemeControllerModule',['ngResource','viewerServices'])


.controller('ngThemeController',['$scope','$resource','Grails','$state','$rootScope',($scope,$resource,Grails,$state,$rootScope)  ->
	$scope.themes = []
	$scope.currentTheme = null

	resource = Grails.getRestAPIResource("models").get()
	resource.$promise.then((result,responseHeaders) ->
			root = {subModels:result.objects,name:"NHIC"}
			dd = filleThemes(root)
			debugger
			$scope.themes.push dd
			return
		,
		(httpResponse) ->
			return
	)

	filleThemes = (root)->
		if (root.subModels.length == 0 )
			item = {
				label: root.name,
				model: root
				children:[],
				onSelect: (branch) ->
					$scope.currentTheme = branch.model
					$state.go("dataElementList",{id:branch.model.id})
					return
				}
			return item
		else
			treeRootItem = {
				label: root.name,
				model: root,
				children:[],
				onSelect: (branch) ->
					$scope.currentTheme = branch.model
					$state.go("dataElementList",{id:branch.model.id})
					return
				}
			for child in root.subModels
				treeChildItem = filleThemes(child)
				treeRootItem.children.push(treeChildItem)

			return treeRootItem


#	filleThemes = (objects)->
#		for object in objects
#			$scope.themes.push {
#				label: object.name,
#				model: object
#				children:[{label:"A"},{label:"A"},{label:"A"},{label:"A"}],
#				onSelect: (branch) ->
#					$scope.currentTheme = branch.model
#					$state.go("dataElementList",{id:branch.model.id})
##					alert branch.label + ' ' +branch.model.id
#					return
#			}

])