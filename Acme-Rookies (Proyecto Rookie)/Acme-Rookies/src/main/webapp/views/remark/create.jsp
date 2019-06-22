<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="remark/company/create.do?applicationId=${application.id}" modelAttribute="remark">
<form:hidden path="id" />
<form:hidden path="version" />
<acme:textbox code="remark.body" path="body" />
<acme:textbox code="remark.picture" path="picture" />
<spring:message code="remark.mode" />
<form:select path="mode">
	<form:option value="DRAFT" />
	<form:option value="FINAL" />
</form:select>
<br/>
<acme:submit name="save" code="remark.save"/>
<acme:cancel url="application/company/show.do?applicationId=${application.id}" code="remark.cancel" />
</form:form>

