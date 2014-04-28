<%@ page import="uk.co.mcv.model.ConceptualDomain.DataElementLocal" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'dataElementLocal.label', default: 'DataElementLocal')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
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