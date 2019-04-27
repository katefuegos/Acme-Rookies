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

<form:form action="${requestURI}" id="row" modelAttribute="miscellaneousDataForm">
	<form:hidden path="id" />
	<form:hidden path="curricula" />
	
	
	<acme:textbox code="miscellaneousData.text" path="text"/>
	<acme:textbox code="miscellaneousData.attachments" path="attachments"/>
	<!--<acme:selectCollection items="${curriculas}" itemLabel="fullName" code="miscellaneousData.curricula" path="curricula"/> -->


	<jstl:if test="${isRead == false}">
			<acme:submit name="save" code="miscellaneousData.save"/>
		<jstl:if test="${id != 0}">
			<acme:delete confirmDelete="miscellaneousData.confirmDelete" name="delete" code="miscellaneousData.delete"/>
		
		</jstl:if>
		<acme:cancel url="curricula/hacker/list.do" code="miscellaneousData.cancel"/>
	</jstl:if>


	<jstl:if test="${isRead == true}">
		<acme:cancel url="curricula/hacker/list.do" code="miscellaneousData.cancel"/>

	</jstl:if>

</form:form>