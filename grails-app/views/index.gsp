<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcome to Grails</title>
	</head>
	<body>
		%{--<div id="page-body" role="main">--}%
            %{--<div class="jumbotron">--}%
                %{--<h2>ModelCatalogue Viewer</h2>--}%
                %{--<p>ModelCatalogue Viewer, Read-Only view of DataElements, ValueDoamins, Pahtways and ...</p>--}%
                %{--<p>--}%
                    %{--<a class="btn btn-lg btn-primary" href="../../components/#navbar" role="button">View navbar docs »</a>--}%
                %{--</p>--}%
            %{--</div>--}%

			%{--<div id="controller-list" role="navigation">--}%
				%{--<h2>Available Controllers:</h2>--}%
				%{--<ul>--}%
					%{--<g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">--}%
						%{--<li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>--}%
					%{--</g:each>--}%
				%{--</ul>--}%
			%{--</div>--}%
            %{----}%
		%{--</div>--}%



        <g:set var="grailsParams" value="${params.collect { it.key + '=\'' + it.value + '\'' }.join('; ')}"/>
    <div ng-app="viewerApp" class="row" ng-init="${grailsParams};grailsAppName='ModelCatalogueViewer'">
        <div class="col-md-3">
            <div class="panel panel-primary">
                <div class="panel-heading">NHIC Themes</div>
                <div class="panel-body">
                    <div  ng-controller="ngThemeController"  >
                        <abn-tree tree-data="themes"  expand-level = "1"  >
                        </abn-tree>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-9">

            <div ui-view class="panel panel-primary" style="border: 1px solid red"></div>

            %{--<div class="panel panel-primary">--}%
            %{--<div class="panel-heading">Data Elements</div>--}%
            %{--<div class="panel-body">--}%
            %{--<g:set var="grailsParams" value="${params.collect { it.key + '=\'' + it.value + '\'' }.join('; ')}"/>--}%
            %{--<div  ng-init="${grailsParams};grailsAppName='ModelCatalogueViewer'">--}%
            %{--<div ng-controller="tableController" ng-init="init('/ModelCatalogueViewer/api/model.json')">--}%
            %{--<div ng-controller="ngTableController" ng-init="init('/ModelCatalogueViewer/api/model.json')">--}%
            %{--<div ng-controller="ngTableController">--}%
            %{--<table ng-table="tableParams" class="table" show-filter="true" show-sort="true">--}%
            %{--<tr ng-repeat="model in $data">--}%
            %{--<td data-title="'Name'" sortable="name" filter="{ 'name': 'text' }" sortable="'name'">{{model.name}}</td>--}%
            %{--<td data-title="'Description'" sortable="description" filter="{ 'description': 'text' }" sortable="'description'">{{model.description}}</td>--}%
            %{--</tr>--}%
            %{--</table>--}%
            %{--</div>--}%
            %{--</div>--}%
            %{--</div>--}%
            %{--</div>--}%
        </div>
    </div>



    </body>
</html>
