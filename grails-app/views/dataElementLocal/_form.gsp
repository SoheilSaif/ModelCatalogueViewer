<%@ page import="uk.co.mcv.model.ConceptualDomain.DataElementLocal" %>



<div class="fieldcontain ${hasErrors(bean: dataElementLocalInstance, field: 'code', 'error')} ">
	<label for="code">
		<g:message code="dataElementLocal.code.label" default="Code" />
		
	</label>
	<g:textField name="code" value="${dataElementLocalInstance?.code}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: dataElementLocalInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="dataElementLocal.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${dataElementLocalInstance?.name}"/>

</div>

