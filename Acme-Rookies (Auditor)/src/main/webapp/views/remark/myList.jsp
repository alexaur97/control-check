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
	<display:column titleKey="remark.ticker" property="ticker" />
	<display:column titleKey="remark.mode" property="mode" />

	
	
	<display:column titleKey="remark.edit">
	<jstl:if test="${remark.mode eq 'DRAFT'}">
		<acme:cancel url="/remark/auditor/edit.do?remarkId=${remark.id}"
			code="remark.edit" />
		</jstl:if>
	</display:column>
	<display:column titleKey="remark.delete">
	<jstl:if test="${remark.mode eq 'DRAFT'}">
			<acme:cancel url="/remark/auditor/delete.do?remarkId=${remark.id}"
			code="remark.delete" />
		</jstl:if>
	</display:column>
		<display:column titleKey="remark.show">
	
		<acme:cancel url="/remark/auditor/show.do?remarkId=${remark.id}"
			code="remark.show" />
		
	</display:column>
	
	
</display:table>

<br>
<br>
<br>
	
<security:authorize access="hasRole('AUDITOR')">
	<acme:cancel url="/remark/auditor/create.do" code="remark.create" />
</security:authorize>