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


<spring:message code="application.accepted" />
<br>
<display:table name="applicationsAccepted" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">

	<display:column property="publicationMoment"
		titleKey="application.publicationMoment" />
	<display:column property="submissionMoment"
		titleKey="application.submissionMoment" />
	<display:column property="position.title"
		titleKey="application.position.title" />
	<display:column property="problem.title"
		titleKey="application.problem.title" />
	<display:column property="position.company.comercialName"
		titleKey="application.company.comercialName" />

	<display:column>
		<a href="application/hacker/display.do?applicationId=${row.id}"><spring:message
				code="application.display" /> </a>
	</display:column>

</display:table>
<br>
<br>
<spring:message code="application.rejected" />
<br>
<display:table name="applicationsRejected" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">

	<display:column property="publicationMoment"
		titleKey="application.publicationMoment" />
	<display:column property="submissionMoment"
		titleKey="application.submissionMoment" />
	<display:column property="position.title"
		titleKey="application.position.title" />
	<display:column property="problem.title"
		titleKey="application.problem.title" />
	<display:column property="position.company.comercialName"
		titleKey="application.company.comercialName" />

	<display:column>
		<a href="application/hacker/display.do?applicationId=${row.id}"><spring:message
				code="application.display" /> </a>
	</display:column>

</display:table>
<br>
<br>
<spring:message code="application.submitted" />
<br>
<display:table name="applicationsSubmitted" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">

	<display:column property="publicationMoment"
		titleKey="application.publicationMoment" />
	<display:column property="submissionMoment"
		titleKey="application.submissionMoment" />
	<display:column property="position.title"
		titleKey="application.position.title" />
	<display:column property="problem.title"
		titleKey="application.problem.title" />
	<display:column property="position.company.comercialName"
		titleKey="application.company.comercialName" />

	<display:column>
		<a href="application/hacker/display.do?applicationId=${row.id}"><spring:message
				code="application.display" /> </a>
	</display:column>

</display:table>
<br>
<br>
<spring:message code="application.pending" />
<br>
<display:table name="applicationsPending" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">

	<display:column property="publicationMoment"
		titleKey="application.publicationMoment" />
	<display:column property="position.title"
		titleKey="application.position.title" />
	<display:column property="problem.title"
		titleKey="application.problem.title" />
	<display:column property="position.company.comercialName"
		titleKey="application.company.comercialName" />

	<display:column>
		<a href="application/hacker/display.do?applicationId=${row.id}"><spring:message
				code="application.display" /> </a>
	</display:column>

	<display:column>
		<a href="application/hacker/edit.do?applicationId=${row.id}"><spring:message
				code="application.edit" /> </a>
	</display:column>

</display:table>

<br>
<br>
<a href="application/hacker/create.do"> <spring:message
		code="application.create" />
</a>
