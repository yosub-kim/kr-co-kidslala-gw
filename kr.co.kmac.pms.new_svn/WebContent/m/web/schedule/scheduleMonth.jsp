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
<div data-role="page" id="scheduleMonthListPage">
		<input type="hidden" name="intYear" id="intYear" value="<c:out value="${intYear}"/>" />
		<input type="hidden" name="intMonth" id="intMonth" value="<c:out value="${intMonth }"/>" />
		
		<div data-role="header" data-theme="a">
			<h1 onclick="goHOME();">KMAC 인트라넷</h1>
			<%--
			<a href="/m/index.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
			 --%>
			<a id="googleLinkBtn" href="#" data-icon="grid" data-iconpos="" onclick="$('#googlePopupDialog').popup('open');">Google</a>
			<a href="/m/web/schedule/scheduleWrite.jsp?saveMode=INSERT" data-icon="plus" data-iconpos="" >추가</a>
		</div><!-- /header -->
	
		<div data-role="content" style="padding-left: 0px; padding-right: 0px;">
			<table style="width: 100%; border-collapse: collapse; margin: 0px;"> 
				<tr style="text-align: center; font-weight: bold; border-collapse: collapse; font-size: 18px;">
					<td><a href="/action/ScheduleMobileAction.do?mode=scheduleOfMonth&selectedYear=<c:out value="${intYear}"/>&selectedMonth=<c:out value="${intMonth }"/>&step=prev" rel="external" >◀</a></td>
					<td colspan="5">
						<c:out value="${intYear}"/>년 <c:out value="${intMonth }"/>월 (<c:out value="${ scheduleUserName }"/>)
					</td>
					<td><a href="/action/ScheduleMobileAction.do?mode=scheduleOfMonth&selectedYear=<c:out value="${intYear}"/>&selectedMonth=<c:out value="${intMonth }"/>&step=next" rel="external" >▶</a></td>
				</tr>
				<tr style="font-size: 8px; text-align: center;">
					<td>Sun</td>
					<td>Mon</td>
					<td>Tue</td>
					<td>Wed</td>
					<td>Thu</td>
					<td>Fri</td>
					<td>Sat</td>
				</tr>
				<c:forEach var="week" items="${calendar}">
					<tr style="font-weight: bold; font-size: 18px; vertical-align: middle; text-align: center; ">
						<c:forEach var="nDay" items="${week}" varStatus="i">
							<td style="<c:if test="${nDay.date == NowDate }">background-color: #c6f4fd;</c:if> border-collapse: collapse; border-style:solid; border-width: 1px; border-color: #1d3b5e; "
								onmouseover="scheduleChageColor('over', this)"
								onmouseout="scheduleChageColor('out', this)"
								onClick="showEventList('<c:out value="${intYear}"/>-<c:out value="${intMonth }"/>-<c:out value="${nDay.day}"/>');"
								isToday="<c:if test="${nDay.date == NowDate }">true</c:if>"
							>								
								<c:choose>
									<c:when test="${!empty nDay.holidayLst}">
										<font <c:if test="${!empty nDay.holidayLst}">color="red"</c:if>><c:out value="${nDay.day}"/></font>
									</c:when>
									<c:otherwise>
										<font <c:choose><c:when test="${i.first}">color="red"</c:when><c:when test="${i.last}">color="blue"</c:when></c:choose>><c:out value="${nDay.day}"/></font>
									</c:otherwise>
								</c:choose>
								<c:if test="${!empty nDay.day}">
									<c:choose>
										<c:when test="${(nDay.dailyProjectLstCount != 0) or (nDay.dailyScheduleLstCount != 0)}"><br/><span id="cal<c:out value="${nDay.day}"/>">.</span></c:when>
										<c:otherwise><br/><span id="cal<c:out value="${nDay.day}"/>">&nbsp;</span></c:otherwise>
									</c:choose>
								</c:if>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
			<div style="padding-left: 5px; padding-right: 10px;">
				<div class="ui-grid-solo" style="text-align: center; margin-top: 15px; margin-bottom: 40px;">
					<ul data-role="listview" id="scheduleListView">
						<li data-role="list-divider" style="display: none">일정목록</li>
					</ul>
				</div>
			</div>
			<%--
			<a href="#googlePopupDialog" data-rel="popup">Open Popup</a>
			 --%>

		</div><!-- /content -->
		
		<jsp:include page="/m/web/common/footer.jsp" >
			<jsp:param value="data_position" name=""/>
		</jsp:include><!-- /footerx	 -->	
	
			<div data-role="popup" id="googlePopupDialog" data-overlay-theme="a" data-theme="e" style="max-width:400px;" class="ui-corner-all">
				<div data-role="header" data-theme="e" class="ui-corner-top" style="padding: 10px; text-align: center;">
					Google Calendar Sync
				</div>
				<div data-role="content" data-theme="d" class="ui-corner-bottom ui-content" style="text-align: center;">
					<p>동기화 버튼을 누르시면 입력하신 Google계정의 Calendar 정보를 가져옵니다.</p>
					<a href="#" data-role="button" data-inline="true" data-theme="c" onclick="googleAuth();">동기화</a>    
					<a href="#" data-role="button" data-inline="true" data-theme="c" onclick="$('#googlePopupDialog').popup('close')">닫&nbsp;&nbsp;기</a>    
				</div>
			</div>			
</div><!-- /page -->

</body>
</html>
