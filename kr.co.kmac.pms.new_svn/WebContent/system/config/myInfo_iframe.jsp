<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<c:choose>
	<c:when test="${param.type eq 'loadMyInfoForm' }">
		<iframe src="/action/ExpertPoolManagerAction.do?mode=loadMyInfoForm"	frameborder="0" style="width:100%; height:100%" noborder scrolling="no"></iframe>
	</c:when>
	<c:when test="${param.type eq 'loadMyInfoAddForm' }">
		<iframe src="/action/ExpertPoolManagerAction.do?mode=loadMyInfoAddForm"	frameborder="0" style="width:100%; height:100%" noborder scrolling="no"></iframe>
	</c:when>
	<c:when test="${param.type eq 'sanctionLine' }">
		<iframe src="/action/SanctionLineAction.do?	mode=getSanctionLine" 		frameborder="0" style="width:100%; height:100%" noborder scrolling="no"></iframe>
	</c:when>
	<c:when test="${param.type eq 'myGadgetConfig' }">
		<iframe src="/action/GadgetAction.do?mode=myGadgetConfig"				frameborder="0" style="width:100%; height:100%" noborder scrolling="no"></iframe>
	</c:when>
	<c:when test="${param.type eq 'specialField' }">
		<iframe src="/action/ExpertPoolSpecialFieldAction.do?mode=loadSpecialField&ssn=<authz:authentication operation="username" />" 
			frameborder="0" style="width:100%; height:460px" noborder scrolling="no"></iframe>
	</c:when>
</c:choose>