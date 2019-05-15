<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


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

<display:table name="configuration" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('ADMIN')">


		<display:column titleKey="configuration.edit">
			<a
				href="configuration/administrator/edit.do?configurationId=${row.id}">
				<spring:message code="configuration.edit" />
			</a>
		</display:column>

		<display:column property= "systemName" titleKey="configuration.systemName">
		</display:column>

	</security:authorize>

	<display:column titleKey="configuration.details">
		<a
			href="configuration/administrator/show.do?configurationId=${row.id}">
			<spring:message code="configuration.show" />
		</a>
	</display:column>



</display:table>
<br />
