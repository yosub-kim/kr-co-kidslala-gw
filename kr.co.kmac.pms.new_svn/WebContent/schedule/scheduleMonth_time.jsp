<%@page import="kr.co.kmac.pms.common.util.DateUtil"%>
<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>개인근무시간</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<link rel="stylesheet" href="/css/calendar.css" type="text/css">
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script src="/m/js/moment.js"></script>	
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

// 선택적 근무시간 승인 화면
function schedule_check(){
	var userid = '<%=session.getAttribute("userId") %>';
	var thisDate = new Date();
	var thisYear = thisDate.getFullYear();
	var thisMonth = thisDate.getMonth()+2;
	if(thisMonth == 13){
		thisMonth = "1";
	}
	var detailWin = window.open("http://intranet.kmac.co.kr/kmac/newschedule/working/schedule_working.asp?_id="+userid+"&mode=chk&year="+thisYear+"&month="+thisMonth, sFeatures);
	detailWin.focus();
}

function schedule_Info(ssn,sDate,seq){
	var scheduleInfo;
	var sURL = "/action/ScheduleAction.do?mode=scheduleForm_time";
	sURL += "&ssn=" + ssn;
	sURL += "&sdate=" + sDate;
	sURL += "&seq=" + seq;
	
	<c:if test="${isManager == true}">
	sURL += "&isManager=MANAGER";
	</c:if>
	
	var sFeatures = "width=750, height=400, top=100, left=100,scrollbars=yes";

	var today = new Date();
	var resultDate = today.getDate();

	/* if(resultDate > 25){
		alert("25일 이전에 근무시간을 입력해주시기 바랍니다.");
		return false;
	}else{
		scheduleInfo = window.open(sURL, "scheduleInfo", sFeatures);
		scheduleInfo.focus();
	} */
	scheduleInfo = window.open(sURL, "scheduleInfo", sFeatures);
	scheduleInfo.focus();
}

function clist_move(){
	location = 'http://intranet.kmac.co.kr/kmac/comlist/clist.asp';
}

function moveNext(){
	var year = document.frm.selectedYear.value;
	var month = document.frm.selectedMonth.value;
	if (month == "12") {
		document.frm.selectedYear.value = parseInt(year) + 1;
		document.frm.selectedMonth.value = 1;
	}
	else {
		document.frm.selectedMonth.value = parseInt(month) + 1;
	}
	document.frm.submit();
}

function movePrevious(){
	var year = document.frm.selectedYear.value;
	var month = document.frm.selectedMonth.value;
	if (month == "1") {
		document.frm.selectedYear.value = parseInt(year) - 1;
		document.frm.selectedMonth.value = 12;
	}
	else {
		document.frm.selectedMonth.value = parseInt(month) - 1;
	}
	document.frm.submit();
}

//var clientId = '538553006464.apps.googleusercontent.com';
//var scopes =  'https://www.googleapis.com/auth/calendar';
//var apiKey = 'AIzaSyD4TUyoZqljXZPBmc-TgScUdcGwJ5wdOxk';
var clientId = '288988552136-6a04df1qemkknlfhn76i7uhs6n9mm43d.apps.googleusercontent.com';
var scopes =  'https://www.googleapis.com/auth/calendar';
var apiKey = 'AIzaSyBbT5USVAQsHtJwvNAoM3b3Zh75KfiH9RQ';
var googleSyncIdArray = new Array();
var googleLoginConfig = {'client_id': clientId, 'scope': scopes};

function handleClientLoad() {
  gapi.client.setApiKey(apiKey);
  window.setTimeout(checkAuth,1);
}

function checkAuth() {
	gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, handleAuthResult);
}
	
function handleAuthResult(authResult) {
	//var authorizeButton = document.getElementById('googleLinkBtn');
	if (authResult && !authResult.error) {
		//authorizeButton.style.visibility = 'hidden';
		loadGoogleCal();
	} else {
		//alert("구글 로그인 필요");
		//authorizeButton.style.visibility = '';
		//authorizeButton.onclick = handleAuthClick;
	}
}

function googleAuth() {
	gapi.auth.authorize(googleLoginConfig, function() {
		//console.log('login complete');
		//console.log(gapi.auth.getToken());
		loadGoogleCal();
		alert("입력하신 Google계정과 동기화 되었습니다.");
	});
}
function loadGoogleCal() {
	if(gapi.auth.getToken() != null){
		gapi.client.load('calendar', 'v3', function() {
			var request = gapi.client.calendar.events.list({
				"calendarId": "primary",
				"showDeleted": false,
				"timeMin": "<c:out value="${intYear}"/>-<c:out value="${intMonth }"/>-01T00:00:00.000+09:00",
				"timeMax": "<c:out value="${intYear}"/>-<c:out value="${intMonth }"/>-<%=DateUtil.getLastDayOfMon(request.getAttribute("intYear")+""+request.getAttribute("intMonth"))%>T23:59:00.000+09:00"
		}); 
			request.execute(function(resp) {
				if(resp.items != undefined){
					for (var i = 0; i < resp.items.length; i++) {
						var startDayStr;if(resp.items[i].start.date==undefined){startDayStr=resp.items[i].start.dateTime;}else{startDayStr=resp.items[i].start.date;}
						var startDayObj;if(startDayStr.length>10){startDayObj=moment(startDayStr,"YYYY-MM-DDTHH:mm:ssZ").toDate();}else{startDayObj=moment(startDayStr,"YYYY-MM-DD").toDate();};
						var summary=resp.items[i].summary==undefined?"":resp.items[i].summary;
						$("cal"+moment(startDayObj).format("D")).innerHTML = $("cal"+moment(startDayObj).format("D")).innerHTML +  
							(($("cal"+moment(startDayObj).format("D")).innerHTML) == "" ? "" : "<br/>")+ summary;
					}
				}
			});
		});
	}
}
</script>
<script src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>
</head>
<body onload="">

<table width="765" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table width="100%">
			<tr>
				<td width="100px">
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="개인근무시간" />
						<jsp:param name="backButtonYN" value="N" />
					</jsp:include>
				</td>
				<td width="70%"><h4 class="mainTitle"><font color="black">(※매월 25일까지 차월 근무시간 등록)</font></h4></td>
				<c:if test="${chkOption == 'Y'}" >
				<td width="70%"><h4 class="mainTitle">(<c:out value="${scheduleUserName}" />)</h4></td>
				</c:if>
				<td width="160px" style="text-align: right;">
				<!--  
				<span onclick="googleAuth()"><img alt="Google 동기화" src="/images/btn_SyncGoogle.jpg" border="0" style="cursor: hand;"></span>
				-->
				
				<%if(!(session.getAttribute("ssn").equals("A000006") || session.getAttribute("ssn").equals("A000002")
						 || session.getAttribute("ssn").equals("A000003") || session.getAttribute("ssn").equals("A000004")
						 || session.getAttribute("ssn").equals("A000006") || session.getAttribute("ssn").equals("A000009")
						 || session.getAttribute("ssn").equals("A001559") || session.getAttribute("ssn").equals("E000817"))) {%>
				<c:if test="${isManager != true}">
					<div class="btNbox txtR"><a class="btN3fac0c txt2btn" onclick="window.open('http://intranet.kmac.co.kr/kmac/newschedule/working/schedule_working.asp?user_id=<%=session.getAttribute("userId") %>&year=<c:out value="${intYear}"/>&month=<c:out value="${intMonth }" />','working','top=50,left=50,width=790,height=400,resizable=no,scrollbars=yes');" href="#"><font color="white">근무시간 확정 현황</font></a></div>
				</c:if>
				<%} %>
					<%-- <c:if test="${isManager == true}">
						<div class="btNbox txtR">
							<a class="btNaaa txt4btn" href="javascript:history.back();">이전</a>
						</div>
					</c:if> --%>

				</td>			
			</tr>
			</table>
		</td>
	</tr>
	<form name="frm">
		<input type="hidden" name="mode" value="scheduleOfMonth_time">
		<input type="hidden" name="history" value="<c:out value="${history}"/>">
		<input type="hidden" name="ssn" value="<c:out value="${ssn}"/>">
		<input type="hidden" name="cSsn" value="<c:out value="${cSsn}"/>">
	<tr>
		<td>
			<table  class="TB" cellpadding="0" cellspacing="0" width="765">
				<tr>
					<td width="718"><img src="/images/calendar/T_main_C1.jpg"></td>
				</tr>
				<tr>
					<td align="center" background="/images/calendar/T_main_Cback.jpg">
						<table class="TB" width="680" cellspacing="0" cellpadding="0" align="center">
							<tr>
        						<td width="680" height="27" background="/images/calendar/T_main_C2.jpg">
        							<!-- 상단아이콘, 날짜, 검색조건 시작-->
            						<table  class="TB" cellspacing="0" cellpadding="0" width="721">
             							<tr>
                							<td width="180">&nbsp;<span class="LF"><c:out value="${intYear}"/>년 <c:out value="${intMonth }"/>월 <c:if test="${chkOption != 'Y'}" >(<c:out value="${ scheduleUserName }"/>)</c:if></span></td>
                							<td width="*" align="center" valign="middle">
                							</td>
                							<td width="300" align="right">
                								<img src="/images/calendar/leftArrow.gif" border="0" alt="이전 달 보기" align="absmiddle" style="cursor:hand;" onclick="movePrevious()"/>
                								<select name="selectedYear" class="selectboxPopup" style="width:60px;" onchange="this.form.submit();">
													<c:forEach begin="${intYear - 2 }" end="${intYear + 1 }" var="year">
													<option value="<c:out value="${year}"/>" <c:if test="${year == intYear }">selected</c:if>><c:out value="${year}"/></option>
													</c:forEach>
												</select> 년&nbsp;
                  								<select name="selectedMonth" class="selectboxPopup" style="width:40px;" onchange="this.form.submit();">
													<c:forEach begin="1" end="12" var="month">
													<option value="<c:out value="${month}"/>" <c:if test="${month == intMonth }">selected</c:if>><c:out value="${month}"/></option>
													</c:forEach>
												</select> 월
                								<img src="/images/calendar/rightArrow.gif" border="0" alt="다음 달 보기" align="absmiddle" style="cursor:hand;" onclick="moveNext()"/>&nbsp;
                								<%-- <input type="checkbox" name="chkOption" value="Y"  <c:if test="${chkOption == 'Y' }">checked</c:if> onclick="this.form.submit();">
                							 	<span class="LF">요약보기</span>&nbsp;&nbsp; --%>
                							</td>
              							<tr>
									</table>
									<!-- 상단아이콘, 날짜, 검색조건 종료-->
								</td>
							</tr>
      						<tr>
								<td width="680"><img src="/images/calendar/T_main_C4.jpg"></td>
							</tr>
							<tr>
								<td height="3"></td>
							</tr>
							<tr>
								<td width="680">
									<table class="TB" width="720" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
										<c:forEach var="week" items="${calendar}">
										<tr>
											<c:forEach var="nDay" items="${week}" varStatus="i">
											<td 
												<c:choose>
													<c:when test="${nDay.date!=''}"> onclick="schedule_Info('<c:out value="${ssn}"/>','<c:out value="${nDay.date}"/>','');"
														<c:choose>
															<c:when test="${nDay.date == NowDate }"> class="T1"</c:when>
															<c:otherwise> class="T0"</c:otherwise>														
														</c:choose>
													</c:when>
													<c:otherwise> class="T2"</c:otherwise>
												</c:choose>>
												
												<!-- 날짜에 아이콘(색상A) 적용시작 -->
												<table height="66" width="90" align="center" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
													<tr>
														<td width="90" height="16">
															<c:choose>
																<c:when test="${!empty nDay.holidayLst}">
																	<font class="DAY1"><c:out value="${nDay.day}"/>
																	<c:forEach var="holidayInfo" items="${nDay.holidayLst}">
																		<c:out value="${holidayInfo.holidayName}" />
																	</c:forEach>
																	</font>
																</c:when>
																<c:when test="${intMonth eq '12' && nDay.day eq '21' }">
																	<font style="color: #2962A7; font-weight: bold"><c:out value="${nDay.day}"/> 창립기념일</font>
																</c:when>
																<c:otherwise>
																	<font 
																		<c:choose>
																			<c:when test="${i.first}">class="DAY1"</c:when>
																			<c:when test="${i.last}">class="DAY2"</c:when>
																			<c:otherwise>class="DAY3"</c:otherwise>
																		</c:choose>
																	><c:out value="${nDay.day}"/> <!-- ( <c:out value="${nDay.dailyProjectLstCount}"/> | <c:out value="${nDay.dailyScheduleLstCount}"/> | <c:out value="${nDay.customerPickerLstCount}"/> )</font> -->
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td width="90" height="40" align="left" class="SubT1" style="white-space: nowrap;">
															<div style="padding-left: 4px; text-overflow: hidden;white-space: nowrap;" 
																id="F<c:out value="${nDay.dayId}"/>" >
																<c:if  test="${!empty nDay.dailyScheduleLst_time}">								
																 	<c:forEach var="dailyProjectInfo" items="${nDay.dailyScheduleLst_time}">
																		<a title="<c:out value="${dailyProjectInfo.year}"/>" href="javascript:schedule_Info('<c:out value="${ssn}"/>','<c:out value="${nDay.date}"/>','1');">
																			<c:out value="${dailyProjectInfo.startHour}"/>:<c:out value="${dailyProjectInfo.startMin}"/> ~ 
																			<c:out value="${dailyProjectInfo.endHour}"/>:<c:out value="${dailyProjectInfo.endMin}"/>
																			</a><br>
																	</c:forEach>
																</c:if>
																<span id="cal<c:out value="${nDay.day}"/>"></span>
															</div>
															<%-- <div id="C<c:out value="${nDay.dayId}"/>"  style="vertical-align: middle; text-align:center; <c:if test="${chkOption == 'N'}">display:none;</c:if>">
																<c:if test="${!empty nDay.day}">
																<table>
																	<c:if  test="${!empty nDay.dailyScheduleLst_time}">								
																 			<c:forEach var="dailyProjectInfo" items="${nDay.dailyScheduleLst_time}">
																			<a title="<c:out value="${dailyProjectInfo.year}"/>" href="javascript:schedule_Info('<c:out value="${ssn}"/>','<c:out value="${nDay.date}"/>','');"
																			><c:out value="${dailyProjectInfo.startHour}"/>:<c:out value="${dailyProjectInfo.startMin}"/> ~ 
																			<c:out value="${dailyProjectInfo.endHour}"/>:<c:out value="${dailyProjectInfo.endMin}"/>
																			</a><br>
																			</c:forEach>
																	</c:if>
																</table>
																</c:if>							
															</div> --%>
														</td>
													</tr>
												</table>
												<!-- 날짜에 아이콘 적용종료 -->
											</td>
											</c:forEach>
										</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
    				<td width="718"><img src="/images/calendar/T_main_C3.jpg" width="765" height="19"></td>
				</tr>
			</table>
		</td>
	</tr>
	</form>
</table>
</body>
</html>