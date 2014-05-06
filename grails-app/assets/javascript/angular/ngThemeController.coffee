angular.module('ngThemeControllerModule',['ngResource','viewerServices'])


.controller('ngThemeController',['$scope','$resource','Grails','$state','$rootScope',($scope,$resource,Grails,$state,$rootScope)  ->
	$scope.themes = []
	$scope.currentTheme = null

	resource = Grails.getRestAPIResource("conceptualdomains").get()
	resource.$promise.then((result,responseHeaders) ->


#			for each concepturalDomains, get its top models
			for conceptualDomain in result.objects
				topLevelModels = []
				for model in conceptualDomain.models
					if(model.parentModelId == null)
						topLevelModels.push(model)

#				build sub trees, based on the top level models
				root = {subModels:topLevelModels,name:conceptualDomain.name,type:"ConceptualDomain"}
				$scope.themes.push filleThemes(root)
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
					if(root.type != "ConceptualDomain")
						$state.go("dataElementList",{id:branch.model.id})
					else
						$state.go("homeStatus")
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
					if(root.type != "ConceptualDomain")
						$state.go("dataElementList",{id:branch.model.id})
					else
						$state.go("homeStatus")
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