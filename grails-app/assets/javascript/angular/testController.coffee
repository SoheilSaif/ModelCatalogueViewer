viewerControllers = angular.module('viewerControllers',['ngTable','ngResource','viewerServices'])


viewerControllers.controller('testController',['$scope','ngTableParams','$resource','Grails','resourceName',($scope,ngTableParams,$resource,Grails,resourceName)  ->
	$scope.resourceName = resourceName

	$scope.tableParams = new ngTableParams(
		page: 1  # show first page
		count: 5 # count per page
		sorting:
			name: "asc"
	,
		total: 0
		getData: ($defer, params) ->
			console.log  $scope.resourceName
			Grails.getRestAPIResource(resourceName).get({max: params.count(),offset:(params.page() - 1) * params.count(),filters:params.filter()},(result, responseHeaders)->
				params.total(result.total);
				$defer.resolve result.objects
				return
			, (httpResponse) ->
#				 if(httpResponse.status == 404)
#				 add more error managements here
				return
			)
			return
	)

])
