<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<acme:display code="remark.body" path="${remark.body}"/>
<spring:message code="remark.picture" />
<a href="${remark.picture}">: <jstl:out value="${remark.picture}"/></a>
<br/>

<jstl:if test="${not empty remark.publicationMoment}">
<jstl:choose>
	<jstl:when test="${lang eq 'en'}">
		<spring:message code="remark.moment"/>: <fmt:formatDate type = "date" pattern = "yy/MM/dd HH:mm"
         value = "${remark.publicationMoment}" />
    </jstl:when>
    <jstl:otherwise>
    	<spring:message code="remark.moment"/>: <fmt:formatDate type = "date" pattern = "dd-MM-yy HH:mm"
         value = "${remark.publicationMoment}" />
    </jstl:otherwise>
</jstl:choose>
</jstl:if>

<jstl:if test="${empty remark.publicationMoment}">
		<spring:message code="remark.moment"/>: <spring:message code="remark.notPublished" />
</jstl:if>

<br/>
<acme:display code="remark.ticker" path="${remark.ticker}"/>

<security:authorize access="hasRole('COMPANY')">
<jstl:if test="${remark.mode eq 'DRAFT' }">

<p style="color: orange;"><spring:message code="remark.draftMode" /></p>
<br/>
<jstl:if test="${bool eq true}">
<acme:button url="remark/company/edit.do?remarkId=${remark.id}" code="remark.edit"/>
</jstl:if>
</jstl:if>

<acme:button url="application/company/show.do?applicationId=${remark.application.id}" code="remark.showApplication" />
<acme:button url="remark/company/list.do" code="remark.myRemarks" />
</security:authorize>

<security:authorize access="hasRole('ROOKIE')">
<acme:button url="application/rookie/show.do?applicationId=${remark.application.id}" code="remark.showApplication" />
<acme:button url="remark/rookie/list.do" code="remark.myRemarks" />
</security:authorize>