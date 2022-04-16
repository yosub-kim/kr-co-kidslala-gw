<%@ taglib prefix="c" 			uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="expertPool"	uri="/WEB-INF/expertPool.tld" %>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head>
<body> 
<div data-role="page">
	
	<div data-role="header" data-theme="a">
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="javascript:history.back();" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
	</div><!-- /header -->

	<div data-role="content" id="scheduleProjectView">
		<input type="hidden" id="scheduleDateView_projectCode" value="<c:out value="${scheduleInfo.projectCode }"/>">
		<input type="hidden" id="scheduleDateView_year" value="<c:out value="${scheduleInfo.year }"/>">
		<input type="hidden" id="scheduleDateView_month" value="<c:out value="${scheduleInfo.month }"/>">
		<input type="hidden" id="scheduleDateView_day" value="<c:out value="${scheduleInfo.day}"/>">
		<input type="hidden" id="scheduleDateView_ssn" value="<c:out value="${scheduleInfo.chargeSsn}"/>">
		
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview" id="customerListView" style="margin-bottom: 5px;">
				<li data-role="list-divider">일정조회 </li>
			</ul>
		</div>
		<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
			<label for="sdate" class="ui-input-text">지도일자:</label>
			<span><c:out value="${scheduleInfo.year }"/>-<c:out value="${scheduleInfo.month }"/>-<c:out value="${scheduleInfo.day }"/></span>
		</div>
		<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
			<label for="type" class="ui-input-text">업무유형:</label>
			<span>지도일지 작성</span>
		</div>
		<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
			<label for="content" class="ui-input-text">프로젝트명:</label>
			<span><c:out value="${scheduleInfo.projectName }"/></span>	
		</div>
		<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
			<label for="customerName" class="ui-input-text">관련회사:</label>
			<span><c:out value="${scheduleInfo.customerName }"/></span>	
		</div>
		<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
			<label for="relationUser" class="ui-input-text">관련자:</label>
			<span><c:out value="${scheduleInfo.relationUser }"/></span>	
		</div>
		
		<div class="ui-grid-solo" style="text-align: center; padding-top: 0px;">
			<a href="#" data-role="button" data-icon="plus" data-theme="e" data-corners="false" id="scheduleRemove1" 
				onclick="deleteSchedule('project')">
				일정삭제
			</a>			
		</div>		
	</div><!-- /content -->
	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include><!-- /footerx	 -->	

</div><!-- /page -->
</body>
</html>
