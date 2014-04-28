services = angular.module 'viewerServices',['ngResource']

services.service 'Grails',($resource) ->

	# This is the preferred method for retrieving resources
	#
	# 	controller: the API endpoint, following "api/", e.g. 'pathways'
	#	id: (optional) the ID of the resource to retrieve.
	getRestAPIResource: (controller, id) ->
		$resource "/:grailsAppName/api/:controller/:id.json", {grailsAppName: grailsAppName || '', controller: controller || '', id: id || ''}, { 'get': { method: 'GET', isArray: false }, 'update': { method: 'PUT'} }
	# if ID set -> GET, UPDATE, DELETE, all URL api/controller/id
	# else 		-> GET, POST, all URL api/controller

	getRestResource: (scope) ->
		isArray = !scope.id?
		$resource "/:grailsAppName/:controller/:id.json", {grailsAppName: scope.grailsAppName || '', controller: scope.controller || '', id: scope.id || ''}, { 'get': { method: 'GET', isArray: isArray, }, 'update': { method: 'PUT'} }

	getResource: (scope) ->
		$resource "/:grailsAppName/:controller/:action/:id.json", {grailsAppName: scope.grailsAppName || '', controller: scope.controller || '', action: scope.action || '', id: scope.id || ''}