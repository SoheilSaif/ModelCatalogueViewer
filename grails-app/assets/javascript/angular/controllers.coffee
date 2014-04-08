viewerControllers = angular.module('viewerControllers',['ngTable'])


viewerControllers.controller('myController',['$scope','ngTableParams',($scope,ngTableParams)  ->


	data = [{name: "Moroni", age: 50},
		{name: "Tiancum", age: 43},
		{name: "Jacob", age: 27},
		{name: "Nephi", age: 29},
		{name: "Enos", age: 34},
		{name: "Tiancum", age: 43},
		{name: "Jacob", age: 27},
		{name: "Nephi", age: 29},
		{name: "Enos", age: 34},
		{name: "Tiancum", age: 43},
		{name: "Jacob", age: 27},
		{name: "Nephi", age: 29},
		{name: "Enos", age: 34},
		{name: "Tiancum", age: 43},
		{name: "Jacob", age: 27},
		{name: "Nephi", age: 29},
		{name: "Enos", age: 34}]


	$scope.tableParams = new ngTableParams(
		 page: 1 # show first page
		 count: 5 # count per page
	 ,
		 total: data.length # length of data
		 getData: ($defer, params) ->
			 $defer.resolve data.slice((params.page() - 1) * params.count(), params.page() * params.count())
			 return
	)

	alert('here in controller')
])