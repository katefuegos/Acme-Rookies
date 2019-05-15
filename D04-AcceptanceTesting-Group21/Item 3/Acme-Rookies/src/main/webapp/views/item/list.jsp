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


<display:table name="items" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
		<security:authorize access="hasRole('PROVIDER')">
		<display:column>
			<a href="item/provider/show.do?itemId=${row.id}"> <spring:message
					code="item.show" />
			</a>
		</display:column>
		
		<display:column>
			<a href="item/provider/edit.do?itemId=${row.id}"> <spring:message
					code="item.edit" />
			</a>
		</display:column>
	</security:authorize>

	<display:column property="name" titleKey="item.name" />

	<display:column property="description" titleKey="item.description" />

	<display:column property="link" titleKey="item.link" />
	
	<display:column property="picture" titleKey="item.picture" />
	
	<display:column>
			<a href="provider/listByItem.do?itemId=${row.id}"> <spring:message
					code="item.display.provider" />
			</a>
		</display:column>

</display:table>
<br>
<br>
<security:authorize access="hasRole('PROVIDER')">
			<a href="item/provider/create.do"> <spring:message code="item.create" />
			</a>
	</security:authorize>
<br>
