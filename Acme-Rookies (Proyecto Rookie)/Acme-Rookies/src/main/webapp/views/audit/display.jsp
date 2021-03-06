<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:display code="audit.text" path="${audit.text}"/>
<acme:display code="audit.moment" path="${audit.moment}"/>
<acme:display code="audit.score" path="${audit.score}"/>
<acme:display code="audit.mode" path="${audit.mode}"/>
<acme:display code="audit.position" path="${audit.position.title}"/>
<acme:display code="audit.auditor" path="${audit.auditor.name}"/>


<acme:button url="/position/show.do?positionId=${audit.position.id}" code="audit.back"/>
<br/>

