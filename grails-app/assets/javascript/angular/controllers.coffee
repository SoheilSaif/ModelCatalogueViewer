viewerControllers = angular.module('viewerControllers',['ngTable','ngResource','viewerServices'])


viewerControllers.controller('ngTableController',['$scope','ngTableParams','$resource','Grails','resourceURLs', '$state',($scope,ngTableParams,$resource,Grails,resourceURLs,$state)  ->

	$scope.resourceURLs = resourceURLs



	$scope.tableParams = new ngTableParams(
		page: 1  # show first page
		count: 5 # count per page
		counts: [5,10, 25, 50]
		sorting:
			name: "asc"
	,
		total: 0
		getData: ($defer, params) ->
			Grails.getNestedRestAPIResource($scope.resourceURLs.resource1,$scope.resourceURLs.id1,$scope.resourceURLs.resource2,$scope.resourceURLs.id2).get({max: params.count(),offset:(params.page() - 1) * params.count(),filters:params.filter()},(result, responseHeaders)->
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

	$scope.go = (item) ->
		$state.go("dataElement",{id:item.id})
		console.log item

		return
])

viewerControllers.controller('dataElementController',['$scope','$resource','Grails','catalogueElementId',($scope,$resource,Grails,catalogueElementId)  ->

	Grails.getRestAPIResource("dataelements",catalogueElementId).get({},(result, responseHeaders)->
		$scope.dataElement = result
		return
	, (httpResponse) ->
#				 if(httpResponse.status == 404)
#				 add more error managements here
		return
	)
])