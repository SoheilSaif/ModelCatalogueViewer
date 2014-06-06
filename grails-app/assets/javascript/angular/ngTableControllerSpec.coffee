'use strict';

describe 'ngTableController', ->

	beforeEach module('viewerControllers')
	beforeEach inject ($rootScope, $controller) ->
		this.$scope = $rootScope.$new()
		this.createController = (scope) ->
			return $controller 'ngTableController', {
				'$scope': scope
			}
		this.controller = this.createController(this.$scope)

	describe 'ngTableController initialized correctly', ->
		it 'should be initialized correctly', ->

			config = { RestURI : 'www' };
			controller.init(config);

			expect(this.$scope.RestURI).toBe("www")
#			a sample test that handles all we require for that
#		  it is a simpl test for here
			expcet(this)