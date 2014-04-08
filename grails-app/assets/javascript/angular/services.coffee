services = angular.module 'viewerServices',[]

#services.service 'Grails',($resource) ->
#	$resource "/mocelcatalogueViewer/:controller/:action/:id",
#		{controller: scope.controller || '', action: scope.action || '', id: scope.id || ''}, ->
#
#	getRestResource: (scope) ->
#		isArray = !scope.id?
#		$resource "/:grailsAppName/:controller/:id.json", {grailsAppName: scope.grailsAppName || '', controller: scope.controller || '', id: scope.id || ''}, { 'get': { method: 'GET', isArray: isArray, }, 'update': { method: 'PUT'} }
#
#	getResource: (scope) ->
#		$resource "/:grailsAppName/:controller/:action/:id.json", {grailsAppName: scope.grailsAppName || '', controller: scope.controller || '', action: scope.action || '', id: scope.id || ''}
