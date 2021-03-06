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

<form:form action="${requestURI}" modelAttribute="applicationForm">
	<form:hidden path="id" />

	<acme:textbox code="application.textAnswer" path="textAnswer" />
	<acme:textbox code="application.linkAnswer" path="linkAnswer" />

	<form:label path="curricula">
		<spring:message code="application.curricula" />:
			</form:label>
	<form:select id="curriculas" path="curricula">
		<form:options items="${curriculas}" itemValue="id"
			itemLabel="fullName" />
	</form:select>
	<form:errors cssClass="error" path="curricula" />
	<br>

	<form:label path="position">
		<spring:message code="application.position" />:
			</form:label>
	<form:select id="positions" path="position">
		<form:options items="${positions}" itemValue="id" itemLabel="title" />
	</form:select>
	<form:errors cssClass="error" path="position" />
	<br>

	<jstl:if test="${isRead == false}">
		<acme:submit name="save" code="application.save" />
	</jstl:if>

	<acme:cancel url="application/rookie/list.do" code="application.cancel" />
</form:form>