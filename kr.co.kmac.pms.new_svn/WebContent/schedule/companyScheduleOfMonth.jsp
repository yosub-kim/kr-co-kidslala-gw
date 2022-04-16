<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>주요행사</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<link rel="stylesheet" href="/css/calendar.css" type="text/css">
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function page_load(){
	// do nothing;
}

function schedule_Info(sDate,startHour,startMin){
	var scheduleInfo;
	var sURL = "/action/CompanyScheduleAction.do?mode=companyScheduleForm";
	sURL += "&sdate=" + sDate;
	sURL += "&startHour=" + startHour;
	sURL += "&startMin=" + startMin;
	var sFeatures = "width=750, height=400, top=100, left=100,scrollbars=yes";

	scheduleInfo = window.open(sURL, "scheduleInfo", sFeatures);
	scheduleInfo.focus();
}

function open_url(fileName,openwin,width,height)
{
	var url = "http://intranet.kmac.co.kr/kmac/Reserve/";
	url = url + fileName;
	open(url,openwin, "width="+width+", height="+height+", toolbar=no, location=no, scrollbars=yes");
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
</script>
</head>
<body onload="page_load();">
<%-- 작업영역 --%>

		
	<table width="765" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
								<jsp:param name="title" value="KMAC 주요행사" />
							</jsp:include>
						</td>
						<td align="right">
						<%if(session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381")){%>
	<!-- 						<input align="absmiddle" style="FONT-SIZE: 9pt; COLOR: 14311c; WIDTH: 100px; HEIGHT: 25px; BACKGROUND-COLOR: #f2f2f2;cursor:hand" type="button" value="공휴일 설정" onclick="open_url('holiday.asp','holiday','530','500')"> -->
							<div class="btNdiv txtR">
								<a class="btNaaa txt4btn" href="javascript:open_url('holiday.asp','holiday','530','500')">공휴일 설정</a>
							</div>
						<%}%>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<form name="frm">
			<input type="hidden" name="mode" value="companyScheduleOfMonth">
		<tr>
			<td>
				<table  class='TB' cellpadding="0" cellspacing="0" width="765">
					<tr>
						<td width="718"><img src='/images/calendar/T_main_C1.jpg'></td>
					</tr>
					<tr>
						<td align="center" background='/images/calendar/T_main_Cback.jpg'>
							<table class='TB' width='680' cellspacing='0' cellpadding='0' align='center'>
								<tr>
	        						<td width='680' height='27' background='/images/calendar/T_main_C2.jpg'>
	        							<!-- 상단아이콘, 날짜, 검색조건 시작-->
	            						<table  class='TB' cellspacing=0 cellpadding=0 width='721'>
	             							<tr>
	                							<td width='200'>&nbsp;<span class="LF"><c:out value="${intYear}"/>년 <c:out value="${intMonth }"/>월 </span></td>
	                							<td width="*" align='center' valign="middle">&nbsp;</td>
	                							<td width='311' align='right'>
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
	                							</td>
	              							<tr>
										</table>
										<!-- 상단아이콘, 날짜, 검색조건 종료-->
									</td>
								</tr>
	      						<tr>
									<td width='680'><img src='/images/calendar/T_main_C4.jpg'></td>
								</tr>
								<tr>
									<td height='3'></td>
								</tr>
								<tr>
									<td width='680'>
										<table class='TB' width='720' cellspacing='0' cellpadding='0' style="table-layout:fixed;">
											<c:forEach var="week" items="${calendar}">
											<tr>
												<c:forEach var="nDay" items="${week}" varStatus="i">
												<td 
													<c:choose>
														<c:when test="${nDay.date!=''}"> onclick="schedule_Info('<c:out value="${nDay.date}"/>','','');"
															<c:choose>
																<c:when test="${nDay.date == NowDate }"> class="T1"</c:when>
																<c:otherwise> class="T0"</c:otherwise>														
															</c:choose>
														</c:when>
														<c:otherwise> class="T2"</c:otherwise>
													</c:choose>>
													
													<!-- 날짜에 아이콘(색상A) 적용시작 -->
													<table height='66' width='90' align='center' cellspacing='0' cellpadding='0' style="table-layout:fixed;">
														<tr>
															<td width='90' height='16'>
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
																		<font <c:choose><c:when test="${i.first}">class="DAY1"</c:when><c:when test="${i.last}">class="DAY2"</c:when><c:otherwise>class="DAY3"</c:otherwise></c:choose>><c:out value="${nDay.day}"/></font>
																	</c:otherwise>
																</c:choose>
															</td>
														</tr>
														<tr>
															<td width='90' height='40' align='left' class="SubT1"  style="white-space: nowrap;">
																<div style="padding-left: 4px; text-overflow: hidden;white-space: nowrap;" id="F<c:out value="${nDay.dayId}"/>">
																	<c:if  test="${!empty nDay.dailyScheduleLst}"> 
																		<c:forEach var="dailyScheduleInfo" items="${nDay.dailyScheduleLst}" varStatus="s">
																			<c:if test="${!s.first}"><br></c:if><a title="<c:out value="${dailyScheduleInfo.content}"/>" href="javascript:schedule_Info('<c:out value="${nDay.date}"/>','<c:out value="${dailyScheduleInfo.startHour}"/>','<c:out value="${dailyScheduleInfo.startMin}"/>');" 
																				><c:out value="${dailyScheduleInfo.content}"/></a>
																		</c:forEach>
																	</c:if>
																</div>
	
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
	    				<td width="718"><img src='/images/calendar/T_main_C3.jpg' width="765" height="19"></td>
					</tr>
				</table>
			</td>
		</tr>
		</form>
	</table>

</body>
</html>