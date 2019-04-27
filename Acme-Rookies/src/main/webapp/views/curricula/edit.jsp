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

<form:form action="${requestURI}" modelAttribute="curriculaForm">
	<form:hidden path="id" />
	<form:hidden path="hacker" />


	<acme:textbox code="curricula.fullName" path="fullName" />
	<acme:textbox code="curricula.statement" path="statement" />
	<acme:textbox code="curricula.phoneNumber" path="phoneNumber" />
	<acme:textbox code="curricula.githubProfile" path="githubProfile" />
	<acme:textbox code="curricula.linkedInProfile" path="linkedInProfile" />

	<jstl:if test="${isRead == false}">
		<acme:submit name="save" code="curricula.save" />
		<jstl:if test="${id != 0}">
			<acme:delete confirmDelete="curricula.confirmDelete" name="delete"
				code="curricula.delete" />

		</jstl:if>
	</jstl:if>

	<acme:cancel url="curricula/hacker/list.do" code="curricula.cancel" />

</form:form>