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


<display:table name="curriculas" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
		<security:authorize access="hasRole('HACKER')">
		<display:column>
			<a href="curricula/hacker/show.do?curriculaId=${row.id}"> <spring:message
					code="curricula.show" />
			</a>
		</display:column>
		
		<display:column>
			<a href="curricula/hacker/edit.do?curriculaId=${row.id}"> <spring:message
					code="curricula.edit" />
			</a>
		</display:column>
	</security:authorize>

	<display:column property="fullName" titleKey="curricula.fullName" />

	<display:column property="statement" titleKey="curricula.statement" />

	<display:column property="phoneNumber" titleKey="curricula.phoneNumber" />
	
	<display:column property="githubProfile" titleKey="curricula.githubProfile" />
	
	<display:column property="linkedinprofile" titleKey="curricula.linkedInProfile" />

	<security:authorize access="hasRole('HACKER')">
		<display:column>
			<a href="curricula/hacker/listData.do?curriculaId=${row.id}"> <spring:message
					code="curricula.listData" />
			</a>
		</display:column>
		
		<display:column>
			<a href="positionData/hacker/create.do?curriculaId=${row.id}"> <spring:message
					code="curricula.createPD" />
			</a>
		</display:column>
		<display:column>
			<a href="miscellaneousData/hacker/create.do?curriculaId=${row.id}"> <spring:message
					code="curricula.createMD" />
			</a>
		</display:column>
		<display:column>
			<a href="educationData/hacker/create.do?curriculaId=${row.id}"> <spring:message
					code="curricula.createED" />
			</a>
			
		</display:column>
	</security:authorize>

</display:table>
<br>
<br>
<security:authorize access="hasRole('HACKER')">
			<a href="curricula/hacker/create.do"> <spring:message code="curricula.create" />
			</a>
	</security:authorize>
<br>
