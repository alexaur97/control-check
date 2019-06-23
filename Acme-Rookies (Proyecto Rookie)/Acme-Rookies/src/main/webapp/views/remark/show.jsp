<%--
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

<spring:message code="remark.ticker"/>: <jstl:out value="${remark.ticker}"></jstl:out>
<br/>
<spring:message code="remark.body"/>: <jstl:out value="${remark.body}"></jstl:out>
<br/>
<jstl:choose>
	<jstl:when test="${lang eq 'en'}">
		<spring:message code="remark.moment"/>: <fmt:formatDate type = "date" pattern = "yy/MM/dd HH:mm"
         value = "${remark.moment}" />
    </jstl:when>
    <jstl:otherwise>
    	<spring:message code="remark.moment"/>: <fmt:formatDate type = "date" pattern = "dd-MM-yy HH:mm"
         value = "${remark.moment}" />
    </jstl:otherwise>
</jstl:choose>
<br/>
<spring:message code="remark.mode"/>: <jstl:out value="${remark.mode}"></jstl:out>
<br/>
<spring:message code="remark.company"/>: <jstl:out value="${remark.audit.position.company.commercialName}"></jstl:out>
<br/>
<spring:message code="remark.audit"/>: <jstl:out value="${remark.audit.text} - ${remark.audit.position.title}"></jstl:out>
<br/>





	