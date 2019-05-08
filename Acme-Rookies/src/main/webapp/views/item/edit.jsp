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

<form:form action="${requestURI}" modelAttribute="itemForm">
	<form:hidden path="id" />
	<form:hidden path="provider" />


	<acme:textbox code="item.name" path="name" />
	<acme:textbox code="item.link" path="link" />
	<acme:textbox code="item.description" path="description" />
	<acme:textbox code="item.picture" path="picture" />
	

	<jstl:if test="${isRead == false }">
		<acme:submit name="save" code="item.save" />
	</jstl:if>
	
	<jstl:if test="${isRead == false && row.id != 0}">
		<jstl:if test="${id != 0 }">
			<acme:delete confirmDelete="item.confirmDelete" name="delete"
				code="item.delete" />

		</jstl:if>
	</jstl:if>
	

	<acme:cancel url="item/provider/list.do" code="item.cancel" />

</form:form>