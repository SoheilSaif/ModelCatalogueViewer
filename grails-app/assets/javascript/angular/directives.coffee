viewerDirectives = angular.module('viewerDirectives',[])

viewerDirectives.directive("fundooRating", ->
	return {
		restrict: 'A',
		link: (scope,element,attib)->
			console.log 'directive link method'
			alert('here')
	}
)

