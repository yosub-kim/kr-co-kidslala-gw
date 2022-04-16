<%@ taglib prefix="c" 			uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="expertPool"	uri="/WEB-INF/expertPool.tld" %>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
	<script src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>
</head>
<body> 
<div data-role="page">
		
	<div data-role="header" data-theme="a">
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="javascript:history.back();" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
		<!-- 
		<a href="/m/web/schedule/scheduleWrite.jsp?saveMode=INSERT" data-icon="arrow-r" >편집</a>
		 -->
	</div><!-- /header -->

	<div data-role="content" id="scheduleDateView">
		<input type="hidden" id="scheduleDateView_date" value="<c:out value="${scheduleInfo.year }"/>-<c:out value="${scheduleInfo.month }"/>-<c:out value="${scheduleInfo.day }"/>">
		<input type="hidden" id="scheduleDateView_seq" value="<c:out value="${scheduleInfo.seq }"/>">
		<input type="hidden" id="scheduleDateView_ssn" value="<c:out value="${scheduleInfo.ssn }"/>">
		<input type="hidden" id="scheduleDateView_googleSyncId" value="<c:out value="${scheduleInfo.googleSyncId }"/>">
			
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview" id="customerListView" style="margin-bottom: 5px;">
				<li data-role="list-divider">일정조회 (<c:out value="${scheduleInfo.year }"/>-<c:out value="${scheduleInfo.month }"/>-<c:out value="${scheduleInfo.day }"/>)</li>
			</ul>
		</div>
		<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
			<label for="sdate" class="ui-input-text">시작시간:</label>
			<span>
				<c:out value="${scheduleInfo.year }"/>-<c:out value="${scheduleInfo.month }"/>-<c:out value="${scheduleInfo.day }"/>
				<c:out value="${scheduleInfo.startHour }"/> : <c:out value="${scheduleInfo.startMin }"/>
				~
				<c:out value="${scheduleInfo.endHour }"/> : <c:out value="${scheduleInfo.endMin }"/>
			</span>
		</div>
		<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
			<label for="type" class="ui-input-text">업무유형:</label>
			<span><c:out value="${scheduleInfo.type }"/></span>
		</div>
		
		<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
			<fieldset data-role="controlgroup" data-type="horizontal" >
				<legend for="workType" class="ui-input-text">외/내근 유형:</legend>
				<input type="radio" name="workType" id="workType1" value="E" <c:if test="${scheduleInfo.workType == 'E'}">checked="checked"</c:if>><label for="workType1">외근</label>
				<input type="radio" name="workType" id="workType2" value="I" <c:if test="${scheduleInfo.workType == 'I'}">checked="checked"</c:if>><label for="workType2">내근</label>
			</fieldset>		
		</div>
				
		<label for="secretYN" class="ui-body ui-br"><input type="checkbox" name="secretYN" id="secretYN" class="custom" value="Y" <c:if test="${scheduleInfo.type eq 'Y' }">checked="checked"</c:if>/>비공개</label>	
		<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
			<label for="content" class="ui-input-text">업무내용:</label>
			<span><c:out value="${scheduleInfo.content }"/></span>	
		</div>
		<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
			<label for="customerName" class="ui-input-text">관련회사:</label>
			<span><c:out value="${scheduleInfo.customerName }"/></span>	
		</div>
		<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
			<label for="relationUser" class="ui-input-text">관련자:</label>
			<span><c:out value="${scheduleInfo.relationUser }"/></span>	
		</div>
		<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
			<label for="place" class="ui-input-text">장소:</label>
			<span><c:out value="${scheduleInfo.place }"/></span>	
		</div>
		
		<div class="ui-grid-solo" style="text-align: center; padding-top: 0px;">
			<a href="#" data-role="button" data-icon="plus" data-theme="e" data-corners="false" id="scheduleRemove2" 
				onclick="deleteSchedule('date')">
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
