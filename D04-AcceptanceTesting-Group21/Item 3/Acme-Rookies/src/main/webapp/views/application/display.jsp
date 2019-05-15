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
	<br>
	<form:label path="textAnswer">
		<spring:message code="application.textAnswer" />:
		</form:label>
	<form:input path="textAnswer" readonly="${isRead}" />
	<br>
	<form:label path="linkAnswer">
		<spring:message code="application.linkAnswer" />:
		</form:label>
	<form:input path="linkAnswer" readonly="${isRead}" />
	<br>
	<form:label path="position.title">
		<spring:message code="application.position.title" />:
		</form:label>
	<form:input path="position.title" readonly="${isRead}" />
	<br>
	<form:label path="curricula.fullName">
		<spring:message code="application.curricula" />:
		</form:label>
	<form:input path="curricula.fullName" readonly="${isRead}" />
	<br>
	<form:label path="rookie.name">
		<spring:message code="application.rookie.name" />:
		</form:label>
	<form:input path="rookie.name" readonly="${isRead}" />
	<br>
	<form:label path="company.comercialName">
		<spring:message code="application.company.comercialName" />:
		</form:label>
	<form:input path="company.comercialName" readonly="${isRead}" />
	<br>
	
	
	<form:label path="publicationMoment">
		<spring:message code="application.publicationMoment" />:
		</form:label>
	<form:input path="publicationMoment" readonly="${isRead}" />
	<br>
	<form:label path="submissionMoment">
		<spring:message code="application.submissionMoment" />:
		</form:label>
	<form:input path="submissionMoment" readonly="${isRead}" />
	<br>
	<form:label path="status">
		<spring:message code="application.status" />:
		</form:label>
	<form:input path="status" readonly="${isRead}" />
	<br>
	
	<h5>
		<spring:message code="application.problem.title"/>
	</h5>
	
	<form:label path="problem.title">
		<spring:message code="problem.title" />:
		</form:label>
	<form:input path="problem.title" readonly="true" />
	<br>
	<form:label path="problem.statement">
		<spring:message code="problem.statement" />:
		</form:label>
	<form:input path="problem.statement" readonly="true" />
	<br>
	<form:label path="problem.hint">
		<spring:message code="problem.hint" />:
		</form:label>
	<form:input path="problem.hint" readonly="true" />
	<br>
	<form:label path="problem.attachments">
		<spring:message code="problem.attachments" />:
		</form:label>
	<form:input path="problem.attachments" readonly="true" />
	<br>
	
	
	<security:authorize access="hasRole('ROOKIE')">
		<acme:cancel url="application/rookie/list.do" code="application.back" />
	</security:authorize>
	<security:authorize access="hasRole('COMPANY')">
		<acme:cancel url="application/company/list.do" code="application.back" />
	</security:authorize>
</form:form>