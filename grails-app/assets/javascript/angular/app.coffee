#= require angular/angular.js
#= require angular-resource/angular-resource.js
#= require angular-ui-router/release/angular-ui-router.js
#= require ng-table/ng-table.js
#= require angular/controllers.js
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


	dataElementList =
		name:'dataElementList',
		url:"/model/:id/dataelement",
		templateUrl: '/' + grailsAppName + '/assets/angular/partials/list.html'
		controller : "ngTableController",
		resolve:{
			resourceDetails: ($stateParams) ->
				  #call to Grails REST API like models/1/dataelements
					return {resourceRestAPI:'models/'+$stateParams.id+'/dataelements',resourceId:null,resourceName:"dataelements",resourceState:"dataElement"}
		}

	dataElement =
		name:'dataElement',
		url:"/dataelements/:id",
		templateUrl: '/' + grailsAppName + '/assets/angular/partials/dataElement.html'
		controller : "dataElementController",
		resolve: {
			catalogueElementId: ($stateParams) ->
				return $stateParams.id
		}


	modelList =
		name: 'modelList',
		url: '/model',
		templateUrl: '/' + grailsAppName + '/assets/angular/partials/list.html'
		controller : "ngTableController",
		resolve: {
			resourceDetails: ($stateParams) ->
				return {resourceRestAPI:'models',resourceId:null,resourceName:"models",resourceState:"model"}
		}

	$stateProvider.state(home)
	$stateProvider.state(dataElementList)
	$stateProvider.state(dataElement)
	$stateProvider.state(modelList)
