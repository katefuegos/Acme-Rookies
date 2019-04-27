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

<form:form action="${requestURI}" modelAttribute="problemForm">
	<form:hidden path="id" />


	<acme:textbox code="problem.title" path="title" />
	<acme:textbox code="problem.statement" path="statement" />
	<acme:textbox code="problem.hint" path="hint" />
	<acme:textbox code="problem.attachments" path="attachments" />
	<jstl:if test="${id == 0}">
		<acme:selectCollection items="${positions}" itemLabel="title"
			code="problem.position" path="position" />
	</jstl:if>

	<form:label path="draftMode">
		<spring:message code="problem.draftMode" />
	</form:label>
	<form:checkbox path="draftMode" />
	<form:errors path="draftMode" cssClass="error" />
	<br />


	<jstl:if test="${isRead == false}">
		<acme:submit name="save" code="problem.save" />
		<jstl:if test="${id != 0}">
			<acme:delete confirmDelete="problem.confirmDelete" name="delete"
				code="problem.delete" />

		</jstl:if>
		<acme:cancel url="problem/company/list.do" code="problem.cancel" />
	</jstl:if>


	<jstl:if test="${isRead == true}">
		<acme:cancel url="problem/company/list.do" code="problem.cancel" />

	</jstl:if>

</form:form>