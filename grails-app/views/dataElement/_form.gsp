<%@ page import="modelcatalogueviewer.DataElement" %>



<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'code', 'error')} ">
	<label for="code">
		<g:message code="dataElement.code.label" default="Code" />
		
	</label>
	<g:textField name="code" value="${dataElementInstance?.code}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="dataElement.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${dataElementInstance?.name}"/>

</div>

