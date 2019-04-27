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

<a href="finder/hacker/update.do"> <spring:message
		code="finder.update" />
</a><br/>

<display:table name="positions" id="row" requestURI="${requestURI}"
	pagesize="15" class="displaytag">
	
		<display:column property="ticker" titleKey="position.ticker" />
		<display:column property="title" titleKey="position.title" />
		<display:column property="skills" titleKey="position.skills" />
		<display:column property="profile" titleKey="position.profile" />
		<display:column property="tecnologies" titleKey="position.tecnologies" />
		<display:column property="salary" titleKey="position.salary" />

		<display:column property="cancelled" titleKey="position.cancel" />

		<display:column >
			<a href="position/display.do?positionId=${row.id}"><spring:message code="position.display" /> </a>
		</display:column>
		<display:column >
			<a href="company/display.do?companyId=${row.company.id}"><spring:message code="position.display.company" /> </a>
		</display:column>

</display:table>


