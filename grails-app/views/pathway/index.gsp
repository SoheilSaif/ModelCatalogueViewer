<%@ page import="uk.co.mcv.pathway.Pathway" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Pathway List</title>
    <asset:stylesheet href="layout.css"/>
    <asset:stylesheet href="application.css"/>
    <r:layoutResources/>
</head>

<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-offset-2  col-md-8">
            <div class="panel panel-primary">
                <div class="panel-heading">Pathway List
                </div>

                <div class="panel-body">
                    <div ng-app="pathway-editor" ng-init="grailsAppName='ModelCatalogueViewer'" class="pathwayEditor">
                        <div ui-view class="">
                        </div>
                    </div>
                </div>
            </div>
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