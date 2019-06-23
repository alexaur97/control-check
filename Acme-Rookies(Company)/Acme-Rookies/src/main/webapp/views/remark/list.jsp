<%--
 * list.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
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

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table pagesize="5" name="remarks" id="remark"
	requestURI="${requestURI}" class="displaytag table">
	
	
	<jstl:if test="${remark.mode eq 'FINAL'}">
		<jstl:if test="${((date-remark.moment.time)/86400000)<30}">
		<display:column style="color:indigo" titleKey="remark.ticker" property="ticker" />
	<display:column style="color:indigo"  titleKey="remark.mode" property="mode" />
			</jstl:if>
			<jstl:if test="${((date-remark.moment.time)/86400000)>30}">
			<jstl:if test="${((date-remark.moment.time)/86400000)<60}">
		<display:column style="color:darkSlateGrey" titleKey="remark.ticker" property="ticker" />
	<display:column style="color:darkSlateGrey" titleKey="remark.mode" property="mode" />
			</jstl:if>
			</jstl:if>
			<jstl:if test="${((date-remark.moment.time)/86400000)>60}">
		<display:column style="color:papayaWhip" titleKey="remark.ticker" property="ticker" />
	<display:column style="color:papayaWhip" titleKey="remark.mode" property="mode" />
			</jstl:if>
		</jstl:if>

	<jstl:if test="${remark.mode eq 'DRAFT'}">
<display:column titleKey="remark.ticker" property="ticker" />
	<display:column style="color:indigo" titleKey="remark.mode" property="mode" />
			</jstl:if>
	
	
	<display:column titleKey="remark.edit">
	<jstl:if test="${remark.mode eq 'DRAFT'}">
		<acme:cancel url="/remark/company/edit.do?remarkId=${remark.id}"
			code="remark.edit" />
		</jstl:if>
	</display:column>
	
	
	<display:column titleKey="remark.show">
	<jstl:if test="${c}">
		<acme:cancel url="/remark/company/show.do?remarkId=${remark.id}"
			code="remark.show" />
	</jstl:if>
	<jstl:if test="${a}">
		<acme:cancel url="/remark/auditor/show.do?remarkId=${remark.id}"
			code="remark.show" />
	</jstl:if>
	</display:column>
	
	
</display:table>

<br>
<br>
<br>
	
<security:authorize access="hasRole('COMPANY')">
	<acme:cancel url="/remark/company/create.do" code="remark.create" />
</security:authorize>