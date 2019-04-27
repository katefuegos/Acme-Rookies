<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div align="left">
	<a href="#"><img src="${banner}" /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->


		<!-- ANONYMOUS -->

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="register/actor.do?authority=HACKER"><spring:message
								code="master.page.register.hacker" /></a></li>

					<li><a href="register/actor.do?authority=COMPANY"><spring:message
								code="master.page.register.company" /></a></li>

					<security:authorize access="hasRole('ADMIN')">
						<li><a
							href="register/administrator/newActor.do?authority=ADMIN"><spring:message
									code="master.page.register.admin" /></a></li>
					</security:authorize>
				</ul></li>
			<li><a href="position/list.do"><spring:message
						code="master.page.position.list" /></a></li>
			<li><a href="company/list.do"><spring:message
						code="master.page.company.list" /></a></li>
		</security:authorize>

		<!-- AUTHENTICATED -->

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/edit.do"><spring:message
								code="master.page.actor.edit" /> </a></li>
					<li><a href="data/list.do"><spring:message
								code="master.page.data" /> </a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
			<li><a href="position/list.do"><spring:message
						code="master.page.position.list" /></a></li>
			<li class="arrow"></li>
			<li><a href="company/list.do"><spring:message
						code="master.page.company.list" /></a></li>
			<li><a href="notification/actor/list.do"><spring:message
						code="master.page.notification.list" /></a></li>
		</security:authorize>


		<!-- ADMIN -->

		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li><a href="dashboard/administrator/dashboard.do"><spring:message
								code="master.page.administrator.dashboard" /></a> <br></li>
					<li><a href="register/administrator/actor.do?authority=ADMIN"><spring:message
								code="master.page.register.admin" /></a></li>
					<li><a href="configuration/administrator/list.do"><spring:message
								code="master.page.administrator.configuration" /></a></li>

				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.securityBreach" /></a>
				<ul>

					<li><a href="notification/administrator/notifyBreach.do"><spring:message
								code="master.page.broadcast" /></a></li>
					<li><a
						href="notification/administrator/notifyBreachHackers.do"><spring:message
								code="master.page.broadcastHackers" /></a></li>
					<li><a
						href="notification/administrator/notifyBreachCompanies.do"><spring:message
								code="master.page.broadcastCompanies" /></a></li>

				</ul></li>
		</security:authorize>

		<!-- COMPANY -->

		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message
						code="master.page.position.manage" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="position/company/list.do"><spring:message
								code="master.page.position.list" /></a></li>
					<li><a href="position/company/create.do"><spring:message
								code="master.page.position.create" /></a></li>
				</ul></li>

			<li><a href="problem/company/list.do"><spring:message
						code="master.page.company.listProblems" /></a></li>

			<li><a class="fNiv" href="application/company/list.do"><spring:message
						code="master.page.application.list" /></a></li>
		</security:authorize>


		<!-- HACKER -->

		<security:authorize access="hasRole('HACKER')">
			<li><a class="fNiv" href="application/hacker/list.do"><spring:message
						code="master.page.application.list" /></a></li>
			<li><a class="fNiv" href="finder/hacker/listPositions.do"><spring:message
						code="master.page.finder" /></a></li>
			<li><a class="fNiv" href="curricula/hacker/list.do"><spring:message
						code="master.page.curricula.list" /></a></li>
		</security:authorize>


		<!-- PRIVACY POLICY -->

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="welcome/terms.do"><spring:message
						code="master.page.privacyPolicy" /></a></li>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="welcome/terms.do"><spring:message
						code="master.page.privacyPolicy" /></a></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

