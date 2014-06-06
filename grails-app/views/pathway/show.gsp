<%@ page import="uk.co.mcv.pathway.Pathway" %>

<!DOCTYPE html>
<html>
<head>

    <meta name="layout" content="main">
    <g:set var="entityName"
           value="${message(code: 'pathway.label', default: 'Pathway')}"/>
    <title>Pathway Browser</title>

    <parameter name="name" value="Pathway Editor"/>

    <asset:stylesheet href="layout.css"/>
    <asset:stylesheet href="application.css"/>

    <r:layoutResources/>


</head>

<body>
<g:set var="grailsParams" value="${params.collect { it.key + '=\'' + it.value + '\'' }.join('; ')}"/>
<!-- FIXME remove hardcoded grails app name and put it in params -->
<div ng-app="pathway-editor" ng-init="${grailsParams}; grailsAppName='ModelCatalogueViewer'" class="pathwayEditor">
    <div ng-controller="PathwayEditorCtrl"  >
        <div class="row">
            <div class="col-xs-12">
                <div class="pull-right">
                    <small id="pathwayDescription">
                            {{pathway.description || 'This pathway doen\'t have any description.'}}
                        <small><i class="fa fa-edit" ng-click="pathwayDescriptionForm.$show()" ng-hide="pathwayDescriptionForm.$visible"></i></small>
                    </small>
                </div>

                <div>
                    <h3>
                        <span id="pathwayName"
                              e-style="width: 25em">{{ pathway.name || 'Please set the pathway name' }}</span>
                        <small id="userVersion"
                               e-style="width: 5em">{{pathway.userVersion}}</small>
                    </h3>
                </div>
            </div>
        </div>


        <div id="container" class="row">

            <div class="ui-layout-west">

                <div class="panel panel-primary">
                    <div class="panel-body" ng-controller="TreeViewCtrl">
                        <h4>Tree view</h4>
                        <ul>
                            <li class="tree-node"
                                ng-keyup="deleteKeyPressed($event, node)" tabindex="{{100 + $index}}"
                                ng-repeat="node in rootPathway.nodes"
                                ng-include="'templates/pathway/pathwayTreeView.html'"></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="ui-layout-center panel panel-primary">
                <div mc-graph-container
                     pathway="pathway"
                     up-a-level="goUp()"
                     add-node="addNode()"
                     un-select-item="unSelectItem()"
                     ng-controller="GraphCanvasCtrl"
                     class="jsplumb-container canvas">
                    <div class="palette">
                        <span class="fa-stack">
                            <i class="fa fa-stack-2x fa-reply" ng-click="upALevel()" ng-show="levelsAbove"></i>
                        </span>
                        <br>
                    </div>

                    <div mc-graph-node graph-node="node"
                         select-node="selectItem(node,'node')"
                         dbl-click="viewSubpathway(node)"
                         ng-keyup="deleteKeyPressed($event, node)"
                         tabindex="{{100 + $index}}"
                         is-selected="isItemSelected(node,'node')"
                         ng-repeat="node in pathway.nodes"></div>

                    <div mc-graph-link graph-link="link" select-link="selectItem(link,'link')"
                         ng-repeat="link in pathway.links">{{link.name}}</div>
                </div>
            </div>

            <!-- If selectedItem is undefined, the right panel will be empty -->
            <div class="ui-layout-east" width="100px" >
                <div ui-view class="panel panel-primary"></div>
            </div>


        </div>

        <!-- FIXME refactor into a separate file -->
        <script type="text/ng-template" id="templates/pathway/pathwayTreeView.html">
            <span ng-click="selectItem(node,'node')"
                  ng-class="{selectedItem: isItemSelected(node)}">{{node.name}}</span>
            <ul>
                <li class="tree-node"
                ng-keyup="deleteKeyPressed($event, node)"
                tabindex="{{100 + $index}}"
                ng-repeat="node in node.nodes" ng-include="'templates/pathway/pathwayTreeView.html'" pathway="node"></li>
            </ul>
        </script>

        <script type="text/ng-template" id="templates/pathway/jsPlumbNode.html">
            <div class="node" id="node{{node.id}}"

            ng-click="selectNode(node,'node')"
            ng-dblclick="dblClick()"
            ng-class="{selectedItem: isSelected(node,'node')}"

            style="left: {{node.x}}px; top: {{node.y}}px">
                <div><i class="fa " ng-class="{'fa-sitemap': node.nodes.length > 0}"></i> {{ node.name || "empty" }}</div>
                <div class="fa fa-arrow-circle-o-right ep right"></div>
                <div class="fa fa-arrow-circle-o-left ep left"></div>
                <div class="fa fa-arrow-circle-o-up ep up"></div>
                <div class="fa fa-arrow-circle-o-down ep down"></div>
            </div>
        </script>


            <script type="text/ng-template" id="templates/pathway/jsPlumbLink.html">
            <div id="link{{link.id}}" ng-click="selectLink(link,'link')" class="link">
        </div>
        </script>
    </div>
</div>

<asset:javascript src="jquery/dist/jquery.js"/>
<asset:javascript src="jquery-ui/ui/jquery-ui.js"/>
<asset:javascript src="bootstrap/dist/js/bootstrap.js"/>
<asset:javascript src="datatables/media/js/jquery.dataTables.js"/>
<asset:javascript src="jquery.layout/dist/jquery.layout-latest.js"/>
<asset:javascript src="pathwaysLayout.js"/>
<asset:javascript src="angular/pathway/app.js"/>

</body>




</html>