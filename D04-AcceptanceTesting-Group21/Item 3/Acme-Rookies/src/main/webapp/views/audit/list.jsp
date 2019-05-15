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


<display:table name="audits" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('AUDITOR')">
		<display:column>
			<a href="audit/auditor/show.do?auditId=${row.id}"> <spring:message
					code="audit.show" />
			</a>
		</display:column>

		<display:column>
			<jstl:if test="${row.draftMode==true}">
				<a href="audit/auditor/edit.do?auditId=${row.id}"> <spring:message
						code="audit.edit" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column property="moment" titleKey="audit.moment" />

	<display:column property="text" titleKey="audit.text" />

	<display:column property="score" titleKey="audit.score" />

</display:table>
<br>
<br>
<security:authorize access="hasRole('AUDITOR')">
	<a href="audit/auditor/create.do"> <spring:message
			code="audit.create" />
	</a>
</security:authorize>
<br>
