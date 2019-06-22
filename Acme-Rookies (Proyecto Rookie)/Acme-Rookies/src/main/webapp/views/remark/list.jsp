<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table pagesize="5" requestURI="${requestURI}" class="displaytag" name="remarks" id="remark">
<display:column titleKey="remark.body"  property="body" />
<display:column titleKey="remark.picture"  property="picture" />
<display:column titleKey="remark.moment"  property="publicationMoment" />
<display:column titleKey="remark.ticker"  property="ticker" />
<security:authorize access="hasRole('COMPANY')">
<display:column titleKey="remark.mode"  property="mode" />
</security:authorize>
<display:column titleKey="remark.show">
<security:authorize access="hasRole('COMPANY')">
<acme:button url="remark/company/show.do?remarkId=${remark.id}" code="remark.show"/>
</security:authorize>
<security:authorize access="hasRole('ROOKIE')">
<acme:button url="remark/rookie/show.do?remarkId=${remark.id}" code="remark.show"/>
</security:authorize>
</display:column>
</display:table>

<acme:button url="#" code="remark.back"/>