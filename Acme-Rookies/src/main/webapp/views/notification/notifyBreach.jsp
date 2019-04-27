<%--
 * action-1.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}"
	modelAttribute="notificationForm">
	<form:hidden path="id" />
	<form:hidden path="moment" />

	<acme:textarea code="notification.subject" path="subject" />
	<acme:textarea code="notification.body" path="body" />

	<input type="submit" name="save"
		value="<spring:message code="notification.save" />" />&nbsp; 
		<input type="button" name="cancel"
		value="<spring:message code="notification.cancel2" />"
		onclick="javascript: relativeRedir('notification/actor/list.do');" />
	<br />
</form:form>