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
	<script src="/m/js/moment.js"></script>	
	
</head>
<body> 
<div data-role="page">
	<form id="scheduleForm" action="">
		<input type="hidden" name="saveMode" value="<c:out value="${param.saveMode}" />" />
		
		<div data-role="header" data-theme="a">
			<h1 onclick="goHOME();">KMAC 인트라넷</h1>
			<a href="/action/ScheduleMobileAction.do?mode=scheduleOfMonth" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
			<a href="" data-icon="plus" onclick="createSchedule()">등록</a>
		</div><!-- /header -->
	
		<div data-role="content" >
			<div class="ui-grid-solo" style="text-align: center; ">
				<ul data-role="listview" id="customerListView" style="margin-bottom: 5px;">
					<li data-role="list-divider">일정등록</li>
				</ul>
			</div>			
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="sdate" class="ui-input-text">시작시간:</label>
				<div class="ui-grid-a">
					<div class="ui-block-a"><input type="date" name="sDate" id="sDate" value="" required="required"></div>
					<div class="ui-block-b"><input type="time" name="sTime" id="sTime" value="" required="required"></div>
				</div>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="edate" class="ui-input-text">종료시간:</label>
				<div class="ui-grid-a">
					<div class="ui-block-a"><input type="date" name="eDate" id="eDate" value="" required="required"></div>
					<div class="ui-block-b"><input type="time" name="eTime" id="eTime" value="" required="required"></div>
				</div>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="type" class="ui-input-text">업무유형:</label>
				<select name="type" id="type" data-mini="true" >
					<option value="사업관리">사업관리</option>
					<option value="전사행사">전사행사</option>
					<option value="교육참석">교육참석</option>
					<option value="회의일정">회의일정</option>
					<option value="휴가">휴가</option>
					<option value="개인휴무">개인휴무</option>
					<option value="기타">기타</option>				
				</select> 	
			</div>
			<label for="secretYN" class="ui-body ui-br"><input type="checkbox" name="secretYN" id="secretYN" class="custom" value="Y"/>체크시 비공개 됨</label>	
			<label for="inWeekYN" class="ui-body ui-br"><input type="checkbox" name="inWeekYN" id="inWeekYN" class="custom" value="Y"/>체크시 주말(토,일)일정도  입력됨</label>	

			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<fieldset data-role="controlgroup" data-type="horizontal" >
					<legend for="workType" class="ui-input-text">외/내근 유형:</legend>
					<input type="radio" name="workType" id="workType1" value="E" ><label for="workType1">외근</label>
					<input type="radio" name="workType" id="workType2" value="I" ><label for="workType2">내근</label>
				</fieldset>		
			</div>			
							
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="content" class="ui-input-text">업무내용:</label>
				<input type="text" name="content" id="content" data-mini="true" placeholder="업무내용" value="<c:out value="${projectReportContent.title}"/>" />	
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="customerName" class="ui-input-text">관련회사:</label>
				<input type="text" name="customerName" id="customerName" data-mini="true" placeholder="관련회사" value="<c:out value="${projectReportContent.title}"/>" />	
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="relationUser" class="ui-input-text">관련자:</label>
				<input type="text" name="relationUser" id="relationUser" data-mini="true" placeholder="관련자" value="<c:out value="${projectReportContent.title}"/>" />	
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="place" class="ui-input-text">장소:</label>
				<input type="text" name="place" id="place" data-mini="true" placeholder="장소" value="<c:out value="${projectReportContent.title}"/>" />	
			</div>
			<label for="saveGoogle" class="ui-body ui-br"><input type="checkbox" name="saveGoogle" id="saveGoogle" class="custom" value="Y"/>Google 캘린더에 저장하기</label>
			<div class="ui-grid-solo" style="text-align: center; padding-top: 0px;">
				<a href="#" data-role="button" data-icon="plus" data-theme="e" data-corners="false" id="scheduleAdd" onclick="createSchedule()">
					일정 등록하기
				</a>
			</div>					
		</div><!-- /content -->
		
		<jsp:include page="/m/web/common/footer.jsp" >
			<jsp:param value="data_position" name=""/>
		</jsp:include><!-- /footerx	 -->	
	
	</form>
</div><!-- /page -->
</body>
</html>
