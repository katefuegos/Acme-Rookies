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

<form:form action="${requestURI}" modelAttribute="auditForm">
	<form:hidden path="id" />
	<form:hidden path="auditor" />
	<form:hidden path="moment" />


	<acme:textbox code="audit.text" path="text" />
	<acme:textbox code="audit.score" path="score" />
	<form:label path="draftMode">
				<spring:message code="audit.draftMode" />
			</form:label>
			<form:checkbox path="draftMode" readonly="true" />
			<form:errors path="draftMode" cssClass="error" />
			<br />
			
	<jstl:if test="${isRead == false }">		
		<acme:selectCollection items="${positions}" itemLabel="title" code="audit.position" path="position"/>
	</jstl:if>
	<jstl:if test="${isRead == true }">
		<spring:message code="audit.position" />: <a href="position/display.do?positionId=${auditForm.position.id}"><jstl:out value="${auditForm.position.title }"/></a>
	</jstl:if>
	<br>
	<jstl:if test="${isRead == false }">
		
		<acme:submit name="save" code="audit.save" />
	</jstl:if>
	
	<jstl:if test="${isRead == false}">
		<jstl:if test="${auditForm.id != 0 }">
			<acme:delete confirmDelete="audit.confirmDelete" name="delete"
				code="audit.delete" />

		</jstl:if>
	</jstl:if>
	

	<acme:cancel url="audit/auditor/list.do" code="audit.cancel" />

</form:form>