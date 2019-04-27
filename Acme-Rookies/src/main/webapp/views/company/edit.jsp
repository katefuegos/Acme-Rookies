<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="company/display.do" modelAttribute="company">
	<form:hidden path="id" />


	<img src="${company.photo}" height="200px" width="auto" />
	<br />
	<acme:textbox code="actor.comercialName" path="comercialName"
		readonly="${readonly}" />
	<acme:textbox code="actor.name" path="name" readonly="${readonly}" />
	<acme:textbox code="actor.surnames" path="surnames"
		readonly="${readonly}" />
	<acme:textbox code="actor.VATnumber" path="VATNumber"
		readonly="${readonly}" />
	<acme:textbox code="actor.email" path="email" readonly="${readonly}" />
	<acme:textbox code="actor.phone" path="phone" readonly="${readonly}" />
	<acme:textbox code="actor.address" path="address"
		readonly="${readonly}" />

	<acme:cancel url="company/list.do" code="position.back" />

</form:form>