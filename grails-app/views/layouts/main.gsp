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
        <asset:link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
        <g:layoutHead/>
	</head>
	<body>

    <div class="container">
        <div class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">ModelCatalogue Viewer</a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">DataElement</a></li>
                        <li><a href="#">ValueDomain</a></li>
                        <li><a href="#">DataTypes</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Action<b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Import</a></li>
                                <li><a href="#">Export</a></li>
                                <li><a href="#">Data Visualization</a></li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="">Log out</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="jumbotron">
            <h2>ModelCatalogue Viewer</h2>
            <p>ModelCatalogue Viewer, Read-Only view of DataElements, ValueDoamins, Pahtways and ...</p>
            <p>
                <a class="btn btn-lg btn-primary" href="../../components/#navbar" role="button">View navbar docs »</a>
            </p>
        </div>
        <g:layoutBody/>
    </div>
		<r:layoutResources />


	</body>

<asset:javascript src="application.js"/>
<asset:javascript src="test.js"/>
<asset:javascript src="bootstrap/dist/js/bootstrap.js"/>

</html>
