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

<display:table name="problems" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">


	<security:authorize access="hasRole('COMPANY')">

		<display:column>
			<a href="problem/company/show.do?problemId=${row.id}"> <spring:message
					code="problem.show" />
			</a>
		</display:column>
		<display:column>
			<jstl:if test="${row.draftmode == true}">
				<a href="problem/company/edit.do?problemId=${row.id}"> <spring:message
						code="problem.edit" />
				</a>
			</jstl:if>
		</display:column>


	</security:authorize>
	<display:column property="title" titleKey="problem.title" />

	<display:column property="statement" titleKey="problem.statement" />

	<display:column property="position.title" titleKey="problem.position" />

	<display:column property="hint" titleKey="problem.hint" />

	<display:column property="attachments" titleKey="problem.attachments" />

</display:table>
<br>
<security:authorize access="hasRole('COMPANY')">
	<a href="problem/company/create.do"> <spring:message
			code="problem.create" />
	</a>
</security:authorize>