<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Model Catalogue Browser</title>
	</head>
	<body>


    <div class="container-fluid">
        <div ng-app="viewerApp"  class="row">
            <div class="col-md-3">
                <div class="panel panel-primary">
                    <div class="panel-heading">Themes</div>
                    <div class="panel-body">
                        <div  ng-controller="ngThemeController"  >
                            <abn-tree tree-data="themes"  expand-level = "2" icon-leaf = "fa fa-file"                >
                            </abn-tree>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-9">
                <div class="panel panel-primary">
                    <div class="panel-heading">Data Elements</div>
                    <div class="panel-body">
                        <div ui-view>
                        </div>
                    </div>
                </div>
             </div>
            </div>
        </div>
    </div>

    <asset:javascript src="angular/app.js"/>

    </body>

</html>
