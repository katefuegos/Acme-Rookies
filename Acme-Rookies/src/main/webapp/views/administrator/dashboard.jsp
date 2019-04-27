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

<h3>
	<spring:message code="administrator.dashboard.C1" />
</h3>

<ul>
	<li><spring:message code="administrator.avg" />: <jstl:out
			value="${avgC1}" /></li>
	<li><spring:message code="administrator.max" />: <jstl:out
			value="${maxC1}" /></li>
	<li><spring:message code="administrator.min" />: <jstl:out
			value="${minC1}" /></li>
	<li><spring:message code="administrator.stddev" />: <jstl:out
			value="${stddevC1}" /></li>
</ul>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.C2" />
</h3>

<ul>
	<li><spring:message code="administrator.avg" />: <jstl:out
			value="${avgC2}" /></li>
	<li><spring:message code="administrator.max" />: <jstl:out
			value="${maxC2}" /></li>
	<li><spring:message code="administrator.min" />: <jstl:out
			value="${minC2}" /></li>
	<li><spring:message code="administrator.stddev" />: <jstl:out
			value="${stddevC2}" /></li>
</ul>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.C3" />
</h3>
<display:table name="queryC3" id="row" class="displaytag">
	<display:column property="name"
		titleKey="actor.name" />
	<display:column property="count"
		titleKey="administrator.count" />
</display:table>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.C4" />
</h3>
<display:table name="queryC4" id="row" class="displaytag">
 	<display:column property="name"
		titleKey="actor.name" />
	<display:column property="count"
		titleKey="administrator.count" />
</display:table>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.C5" />
</h3>

<ul>
	<li><spring:message code="administrator.avg" />: <jstl:out
			value="${avgC5}" /></li>
	<li><spring:message code="administrator.max" />: <jstl:out
			value="${maxC5}" /></li>
	<li><spring:message code="administrator.min" />: <jstl:out
			value="${minC5}" /></li>
	<li><spring:message code="administrator.stddev" />: <jstl:out
			value="${stddevC5}" /></li>
</ul>
<br />
<br />

<h3>
	<spring:message code="administrator.dashboard.C6" />
</h3>
<display:table name="queryC6" id="row" requestURI="${requestURI}"
	pagesize="15" class="displaytag">
	
		<display:column property="ticker" titleKey="position.ticker" />
		<display:column property="title" titleKey="position.title" />
		<display:column property="salary" titleKey="position.salary" />

		<display:column >
			<a href="position/display.do?positionId=${row.id}"><spring:message code="position.display" /> </a>
		</display:column>
		<display:column >
			<a href="company/display.do?companyId=${row.company.id}"><spring:message code="position.display.company" /> </a>
		</display:column>

</display:table>
<br />
<br />
<hr>
<br />
<br />

<h3>
	<spring:message code="administrator.dashboard.B1" />
</h3>

<ul>
	<li><spring:message code="administrator.avg" />: <jstl:out
			value="${avgB1}" /></li>
	<li><spring:message code="administrator.max" />: <jstl:out
			value="${maxB1}" /></li>
	<li><spring:message code="administrator.min" />: <jstl:out
			value="${minB1}" /></li>
	<li><spring:message code="administrator.stddev" />: <jstl:out
			value="${stddevB1}" /></li>
</ul>

<br />
<br />

<h3>
	<spring:message code="administrator.dashboard.B2" />
</h3>

<ul>
	<li><spring:message code="administrator.avg" />: <jstl:out
			value="${avgB2}" /></li>
	<li><spring:message code="administrator.max" />: <jstl:out
			value="${maxB2}" /></li>
	<li><spring:message code="administrator.min" />: <jstl:out
			value="${minB2}" /></li>
	<li><spring:message code="administrator.stddev" />: <jstl:out
			value="${stddevB2}" /></li>
</ul>


<br />
<br />

<h3>
	<spring:message code="administrator.dashboard.B3" />
</h3>
<ul>
	<li><spring:message code="administrator.ratioNotEmpty" />: <jstl:out
			value="${ratioNotEmpty}" /></li>
	<li><spring:message code="administrator.ratioEmpty" />: <jstl:out
			value="${ratioEmpty}" /></li>
</ul>

