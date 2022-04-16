<%@ taglib prefix="c"			uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jstl/fmt" %>
<%-- 리스트 사이즈<%@ taglib prefix="fn"			uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%@ taglib prefix="dt"			uri="http://jakarta.apache.org/taglibs/datetime-1.0" %>
<%@ taglib prefix="authz"		uri="http://acegisecurity.org/authz" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="code"		uri="/WEB-INF/commonCode.tld" %>
<%@ taglib prefix="expertPool"	uri="/WEB-INF/expertPool.tld" %>
<%@ taglib prefix="org"			uri="/WEB-INF/org.tld" %>
<%@ taglib prefix="date"		uri="/WEB-INF/date.tld" %>
<%@ taglib prefix="money"		uri="/WEB-INF/moneyFormat.tld" %>
<%
response.setHeader("Cache-Control", "private");
response.setHeader("Expires", "0");
response.setHeader("Pragma", "no-cache");
%>

