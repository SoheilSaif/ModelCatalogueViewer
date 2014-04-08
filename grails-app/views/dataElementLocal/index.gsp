
<%@ page import="modelcatalogueviewer.DataElementLocal" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataElementLocal.label', default: 'DataElementLocal')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-dataElementLocal" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-dataElementLocal" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
						<g:sortableColumn property="code" title="${message(code: 'dataElementLocal.code.label', default: 'Code')}" />
						<g:sortableColumn property="name" title="${message(code: 'dataElementLocal.name.label', default: 'Name')}" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${dataElementLocalInstanceList}" status="i" var="dataElementLocalInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${dataElementLocalInstance.id}">${fieldValue(bean: dataElementLocalInstance, field: "code")}</g:link></td>
					
						<td>${fieldValue(bean: dataElementLocalInstance, field: "name")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${dataElementLocalInstanceCount ?: 0}" />
			</div>
		</div>

        %{--<div ng-app="viewerApp">--}%
            %{--<div ng-controller="myController">--}%
                %{--<p>{{item}}</p>--}%
            %{--</div>--}%

            %{--<div ng-controller="myController">--}%
                %{--<p><strong>Page:</strong> {{tableParams.page()}}</p>--}%
                %{--<p><strong>Count per page:</strong> {{tableParams.count()}}</p>--}%
%{----}%
                %{--<table ng-table="tableParams" class="table" >--}%
                    %{--<tr ng-repeat="user in $data">--}%
                        %{--<td data-title="'Name'" sortable="name" filter="{ 'name': 'text' }" >{{user.name}}</td>--}%
                        %{--<td data-title="'Age'"  sortable="age">{{user.age}}</td>--}%
                    %{--</tr>--}%
                %{--</table>--}%
            %{--</div>--}%
<<<<<<< HEAD
        </div>
=======
        %{--</div>--}%
>>>>>>> 306098a96d1c85d07997af1115f219f2bab2793e
	</body>
</html>