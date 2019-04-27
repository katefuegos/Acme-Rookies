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

<display:table name="companys" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="comercialName" titleKey="actor.comercialName" />
	<display:column property="name" titleKey="actor.name" />
	<display:column property="email" titleKey="actor.email" />
	<display:column property="phone" titleKey="actor.phone" />
	<display:column property="VATNumber" titleKey="actor.VATnumber" />

	<jstl:if test="${isRead==true}">
		<img src="${row.photo}" height="50px" width="auto" />
		<br />
	</jstl:if>

	<display:column>
		<a href="company/display.do?companyId=${row.id}"><spring:message
				code="position.display" /> </a>
	</display:column>
	<display:column>
		<a href="position/listByCompany.do?companyId=${row.id}"><spring:message
				code="company.display.position" /> </a>
	</display:column>

</display:table>

