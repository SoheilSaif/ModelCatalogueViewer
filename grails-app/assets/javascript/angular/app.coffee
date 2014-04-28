#= require angular/angular.js
#= require angular-resource/angular-resource.js
#= require angular-xeditable/dist/js/xeditable.js
#= require angular-ui-router/release/angular-ui-router.js
#= require ng-table/ng-table.js
#= require angular/ngTableController.js
#= require angular/ngThemeController.js
#= require angular/testController.js
#= require angular/services.js
#= require angular/directives.js
#= require angular-bootstrap-nav-tree/dist/abn_tree_directive.js

simpleFunction = ->
	console.log "In app.coffee and just for test if coffee file works fine!"
	return

simpleFunction()

@angularAppName = 'viewerApp'
@grailsAppName = 'ModelCatalogueViewer'
mainApp = angular.module @angularAppName, ['angularBootstrapNavTree','ngThemeControllerModule','viewerControllers','viewerServices','viewerDirectives','ui.router']

mainApp.config ($stateProvider, $urlRouterProvider) ->
	$urlRouterProvider.otherwise "/"

	home =
		name:'homeStatus',
		url:"/home",
		templateUrl: '/' + grailsAppName + '/assets/angular/partials/search.html'


	list =
		name:'listStatus',
		url:"/list",
		templateUrl: '/' + grailsAppName + '/assets/angular/partials/list.html'
		controller : "testController",
		resolve:{
			resourceName: () ->
					return 'models'
		}

	model =
		name: 'modelStatus',
		url: '/model',
		templateUrl: '/' + grailsAppName + '/assets/angular/partials/model.html'
		controller : "ngTableController",
		resolve: {
			RestURI: ($stateParams, UserService) ->
		 		"api/models"
		}

	$stateProvider.state(home)
	$stateProvider.state(list)
	$stateProvider.state(model)