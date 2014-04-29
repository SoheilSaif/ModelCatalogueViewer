viewerControllers = angular.module('viewerControllers',['ngTable','ngResource','viewerServices'])


viewerControllers.controller('ngTableController',['$scope','ngTableParams','$resource','Grails','resourceURLs',($scope,ngTableParams,$resource,Grails,resourceURLs)  ->
	$scope.resourceURLs = resourceURLs

	$scope.tableParams = new ngTableParams(
		page: 1  # show first page
		count: 5 # count per page
		sorting:
			name: "asc"
	,
		total: 0
		getData: ($defer, params) ->
			Grails.getNestedRestAPIResource($scope.resourceURLs.resource1,$scope.resourceURLs.id1,$scope.resourceURLs.resource2,$scope.resourceURLs.id2).get({max: params.count(),offset:(params.page() - 1) * params.count(),filters:params.filter()},(result, responseHeaders)->
				params.total(result.total);
				console.log result.objects
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
