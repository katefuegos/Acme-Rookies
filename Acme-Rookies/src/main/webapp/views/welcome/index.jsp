<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<p>${welomeMessage}</p>

<p>
	<jstl:if test="${moment!=null}">
		<spring:message code="welcome.greeting.current.time" />
	${moment}</jstl:if>
</p>
<jstl:if test="${message != null}">
	<br />
	<span class="message"><spring:message code="${oops}" /></span>
</jstl:if>