<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">



        <asset:stylesheet href="bootstrap/dist/css/bootstrap.css"/>
        <asset:stylesheet href="angular-bootstrap-nav-tree/dist/anb_tree.css"/>
        <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.12/angular-animate.js"></script>



        <asset:link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>


        %{--Styles for ng-Table--}%
        <asset:stylesheet href="style.css"/>
        %{--ng-Table default Style--}%
        <asset:stylesheet href="ng-table/ng-table.css"/>


        <g:layoutHead/>
	</head>
	<body>


    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">ModelCatalogue Browser</a>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="/ModelCatalogueViewer">Home</a></li>
                    <li><a href="/ModelCatalogueViewer">Data Model</a></li>
                    <li><a href="/ModelCatalogueViewer/pathway/show/1/">Pathways</a></li>
                    <li><a href="#contact">Search</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">More <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">Import</a></li>
                            <li><a href="#">Export</a></li>
                            <li class="divider"></li>
                            <li class="dropdown-header"></li>
                            <li><a href="#">Data Visualization</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>

   <div class="fullwidthContainer" style="margin-top: 70px;" role="main">
       <g:layoutBody/>
   </div>
 		<r:layoutResources />
	</body>

%{--<asset:javascript src="test.js"/>--}%

</html>