#= require angular/angular.js
#= require angular-resource/angular-resource.js
#= require angular-xeditable/dist/js/xeditable.js
#= require angular-ui-router/release/angular-ui-router.js
#= require ng-table/ng-table.js
#= require angular/controllers.js
#= require angular/services.js

simpleFunction = ->
	console.log "In app.coffee and just for test if coffee file works fine!"
	return

simpleFunction()


@angularAppName = 'viewerApp'
angular.module @angularAppName, ['viewerControllers','viewerServices']


