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

<form:form action="${requestURI}" modelAttribute="educationDataForm">
	<form:hidden path="id" />
	<form:hidden path="curricula" />
	
	
	<acme:textbox code="educationData.degree" path="degree"/>
	<acme:textbox code="educationData.institution" path="institution"/>
	<acme:textbox code="educationData.mark" path="mark"/>
	<acme:textbox code="educationData.startDate" path="startDate"/>
	<acme:textbox code="educationData.endDate" path="endDate"/>
	


	<jstl:if test="${isRead == false}">
			<acme:submit name="save" code="educationData.save"/>
		<jstl:if test="${id != 0}">
			<acme:delete confirmDelete="educationData.confirmDelete" name="delete" code="educationData.delete"/>
		
		</jstl:if>
		<acme:cancel url="curricula/hacker/list.do" code="educationData.cancel"/>
	</jstl:if>


	<jstl:if test="${isRead == true}">
		<acme:cancel url="curricula/hacker/list.do" code="educationData.cancel"/>

	</jstl:if>

</form:form>