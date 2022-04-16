<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>전사 일정 관리</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function page_load(){

}

function schedule_Info(ssn,sDate,seq){
	var scheduleInfo;
	var sURL = "/action/ScheduleAction.do?mode=scheduleForm";
	sURL += "&ssn=" + ssn;
	sURL += "&sdate=" + sDate;
	sURL += "&seq=" + seq;
	var sFeatures = "width=1030, height=300, top=100, left=100,scrollbars=yes";

	scheduleInfo = window.open(sURL, "scheduleInfo", sFeatures);
	scheduleInfo.focus();
}

function doSearch() {
	document.frm.submit();
}

function viewExpertPoolWorkPeriodList(jobClass, companyPosition){
	var url = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList"
		+ "&jobClass=" + jobClass + "&companyPosition=" + companyPosition;
	var sFeatures = "top=50,left=50,width=790,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList", sFeatures);
	detailWin.focus();
}

function viewExpertPoolWorkPeriodList_exp(jobClass, companyPosition){
	var url = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_exp"
		+ "&jobClass=" + jobClass + "&companyPosition=" + companyPosition;
	var sFeatures = "top=50,left=50,width=720,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_exp", sFeatures);
	detailWin.focus();
}

function viewExpertPoolWorkPeriodList_vac(jobClass, companyPosition, dept){
	var url = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_vac"
		+ "&jobClass=" + jobClass + "&companyPosition=" + companyPosition + "&dept=" + escape(dept);
	var sFeatures = "top=50,left=50,width=790,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_vac", sFeatures);
	detailWin.focus();
}

function viewExpertPoolWorkPeriodList_exp2(jobClass, companyPosition){
	var url = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_exp2"
		+ "&jobClass=" + jobClass + "&companyPosition=" + companyPosition;
	var sFeatures = "top=50,left=50,width=900,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_exp2", sFeatures);
	detailWin.focus();
}

function viewExpertPoolWorkPeriodList_schedule(jobClass, companyPosition){
	var url = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_schedule";
	var sFeatures = "top=50,left=50,width=1140,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_schedule", sFeatures);
	detailWin.focus();
}

function viewExpertPoolWorkPeriodList_customer(jobClass, companyPosition){
	var url = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_customer";
	var sFeatures = "top=50,left=50,width=800,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_customer", sFeatures);
	detailWin.focus();
}

function viewExpertPoolWorkPeriodList_time(jobClass, companyPosition){
	var url = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_time";
	var sFeatures = "top=50,left=50,width=1560,height=800,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_time", sFeatures);
	detailWin.focus();
}

</script>
</head>
<body onload="page_load();">
<%-- 작업영역 --%>
<table width="770" cellpadding="0" cellspacing="0">
	<tr>
		<td height="12">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="일정관리" />
			</jsp:include>
		</td>
	</tr>
	<tr>
		<!-- <td height="10"></td> -->
	</tr>
	<form name="frm">
			
	<!-- 검색 영역 -->
	<tr>
		<td height="21" align="left" valign="top">
			<%@ include file="/common/include/searchBox_Header.jsp"%>
			<table border="0" width="100%" style="border-collapse: collapse; ">
				<colgroup> 
					<col width="90px">
					<col width="110px">
					<col width="90px">
					<col width="110px">
					<col width="90px">
					<col width="*">
				</colgroup>
				<tr>
					<td class="searchTitle_center">연도</td> 
					<td class="searchField_left">
						<input type="hidden" name="mode" value="scheduleByAll">
						<select name="selectedYear" class="selectbox" style="width:100%;" >
							<c:forEach begin="${intYear - 2 }" end="${intYear + 1 }" var="year">
								<option value="<c:out value="${year}"/>" <c:if test="${year == intYear }">selected</c:if>><c:out value="${year}"/></option>
							</c:forEach>
						</select>
					</td>
					<td class="searchTitle_center">월</td>
					<td class="searchField_left">
						<select name="selectedMonth" class="selectbox" style="width:100%;" >
							<option value="1" <c:if test="${'01' == intMonth }">selected</c:if>>1</option>
							<option value="2" <c:if test="${'02' == intMonth }">selected</c:if>>2</option>
							<option value="3" <c:if test="${'03' == intMonth }">selected</c:if>>3</option>
							<option value="4" <c:if test="${'04' == intMonth }">selected</c:if>>4</option>
							<option value="5" <c:if test="${'05' == intMonth }">selected</c:if>>5</option>
							<option value="6" <c:if test="${'06' == intMonth }">selected</c:if>>6</option>
							<option value="7" <c:if test="${'07' == intMonth }">selected</c:if>>7</option>
							<option value="8" <c:if test="${'08' == intMonth }">selected</c:if>>8</option>
							<option value="9" <c:if test="${'09' == intMonth }">selected</c:if>>9</option>
							<option value="10" <c:if test="${'10' == intMonth }">selected</c:if>>10</option>
							<option value="11" <c:if test="${'11' == intMonth }">selected</c:if>>11</option>
							<option value="12" <c:if test="${'12' == intMonth }">selected</c:if>>12</option>
						</select>
					</td>
					<td class="searchTitle_center">구분</td>
					<td class="searchField_left">
						<select name="jobClass" class="selectbox" style="width:100%;" >
							<option value="A" <c:if test="${jobClass == 'A' }"> selected</c:if>>상근</option>
							<%-- <option value="B" <c:if test="${jobClass == 'B' }"> selected</c:if>>상근(2)</option> --%>
							<option value="J" <c:if test="${jobClass == 'J' }"> selected</c:if>>상임</option>
							<%-- <option value="H" <c:if test="${jobClass == 'H' }"> selected</c:if>>AA(Ⅰ~Ⅲ)</option> --%>
							<option value="H2" <c:if test="${jobClass == 'H2' }"> selected</c:if>>RA</option>
							<%-- <option value="C" <c:if test="${jobClass == 'C' }"> selected</c:if>>엑스퍼트</option> --%>
						</select>
					</td>
				</tr>
			</table>
			<jsp:include page="/common/include/searchBox_Footer.jsp" flush="true">
				<jsp:param name="hasButton" value="Y" />
			</jsp:include>
		</td>
	</tr>
	<!-- 검색 영역 -->
	
	<!-- SPACER -->						
	</form>						

	<tr>
		<td>
			<table cellSpacing="0" width="100%" border="0" style="table-layout:fixed">
				<tr>
					<td style="width:160; height:20; text-align:left">
					<!-- 개인 일정 현황 -->
					<%-- <c:if test="${jobClass == 'A' }">
						<%if(session.getAttribute("userId").equals("5815choi") || session.getAttribute("userId").equals("a0615222") || session.getAttribute("dept").equals("9071") || session.getAttribute("dept").equals("9073")) {%>
							<a class="btN3fac0c txt2btn" onclick="viewExpertPoolWorkPeriodList_schedule('C', '!64GT');" href="#">개인 일정 현황</a>
						<%} %>
					</c:if> --%>
					
					<c:if test="${jobClass == 'H2' }">
						<%if(session.getAttribute("dept").equals("9261") || session.getAttribute("dept").equals("9263") || session.getAttribute("userId").equals("5815choi")){%>
							<a class="btN3fac0c txt2btn" onclick="viewExpertPoolWorkPeriodList('N','64GT');" href="#">RA 계약기간</a>
						<%}else if (session.getAttribute("companyPosition").equals("08CF") || session.getAttribute("companyPosition").equals("09CJ") || session.getAttribute("companyPosition").equals("06CB")){%>
							<a class="btN3fac0c txt2btn" onclick="viewExpertPoolWorkPeriodList_vac('N','64GT',<%=session.getAttribute("dept")%>);" href="#">RA 계약기간</a>
						<%} %>
					</c:if>
					
					<c:if test="${jobClass == 'A' }">
						<%-- <%if(session.getAttribute("dept").equals("9261") || session.getAttribute("dept").equals("9263") || session.getAttribute("userId").equals("5815choi")){ %>
							<a class="btN3fac0c txt2btn" onclick="viewExpertPoolWorkPeriodList('H','!64GT');" href="#">AA 계약기간</a>
						<%}else if (session.getAttribute("companyPosition").equals("08CF") || session.getAttribute("companyPosition").equals("09CJ") || session.getAttribute("companyPosition").equals("06CB")){%>
							<a class="btN3fac0c txt2btn" onclick="viewExpertPoolWorkPeriodList_vac('H','!64GT',<%=session.getAttribute("dept")%>);" href="#">AA 계약기간</a>
						<%} %> --%>
						<%if(session.getAttribute("userId").equals("yskim") || session.getAttribute("userId").equals("eun") || session.getAttribute("userId").equals("mwji") || session.getAttribute("userId").equals("lokal07") || session.getAttribute("userId").equals("hyunkyui")){ %>
							<a class="btN3fac0c txt2btn" onclick="viewExpertPoolWorkPeriodList_exp2('C', '!64GT');" href="#">엑스퍼트 현황</a>
						<%} %>
					</c:if>
					<!-- 엑스퍼트 현황 -->
					<%-- <%if(session.getAttribute("userId").equals("5815choi") || session.getAttribute("userId").equals("chang") || session.getAttribute("userId").equals("jerome77") || session.getAttribute("userId").equals("shhan912")){ %>
						<a class="btN3fac0c txt2btn" onclick="viewExpertPoolWorkPeriodList_exp('C', '!64GT');" href="#">엑스퍼트 운영현황</a>
					<%}else if (session.getAttribute("userId").equals("mwji") || session.getAttribute("userId").equals("lokal07") || session.getAttribute("userId").equals("yskim") 
							|| session.getAttribute("userId").equals("hyunkyui") || session.getAttribute("userId").equals("greentry") || session.getAttribute("userId").equals("eun")) {%>
						<a class="btN3fac0c txt2btn" onclick="viewExpertPoolWorkPeriodList_exp2('C', '!64GT');" href="#">엑스퍼트 운영현황</a>
					<%} %>
					</c:if> --%>
					</td>
						<tr>
						<td style="text-align:right">
								<img src="/images/calendar/1.png" align="absmiddle"><span class="LF"> : 내부일정</span>&nbsp;
								<img src="/images/calendar/2.png" align="absmiddle"><span class="LF"> : 외부일정</span>&nbsp;
							<c:if test="${jobClass != 'C' }">
								<img src="/images/calendar/4.png" align="absmiddle"><span class="LF"> : 고객정보</span>&nbsp;
							</c:if>						
								<img src="/images/calendar/3.png" align="absmiddle"><span class="LF"> : 프로젝트</span>&nbsp;
							<c:if test="${jobClass != 'C' }">
								<img src="/images/calendar/98.png" align="absmiddle"><span class="LF"> : 교육</span>&nbsp;	
							</c:if>
							<c:if test="${jobClass != 'J' && jobClass != 'C' }">				
								<img src="/images/calendar/99.png" align="absmiddle"><span class="LF"> : 휴가</span>&nbsp;
							</c:if>
							<c:if test="${jobClass == 'J' }">
								<img src="/images/calendar/100.png" align="absmiddle"><span class="LF"> : 개인휴무</span>&nbsp;
							</c:if>	
							<c:if test="${jobClass == 'A'  || jobClass == 'H2' }" >
								<img src="/images/calendar/54321-2.png" align="absmiddle"><span class="LF"> : 재택근무</span>
							</c:if>
							<!-- RA 활동보고서 -->
							<%-- <c:if test="${jobClass == 'H2'}" >
								<img src="/images/calendar/101010.png" align="absmiddle"><span class="LF"> : 활동보고서</span>
							</c:if> --%>
						</td>
						</tr>
					</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="5"></td>
	</tr>
	<tr>
		<td align="center">
			<table cellSpacing="0" width="100%" border="0" class="tableStyle03" style="table-layout:fixed">
				<colgroup>
					<col width="105px"/>					
					<c:forEach var="i" items="${weekDay}">						
						<c:choose>						
						<c:when test="${i == 'SUN'}">
						<col style="background : #FFDFFF"/>
						</c:when>
						<c:when test="${i == 'SAT'}">
						<col style="background : #D9ECFF;"/>
						</c:when>
						<c:when test="${i == 'HOL'}">
						<col style="background : #FFDFFF"/>
						</c:when>
						<c:otherwise>
						<col style="background : white"/>
						</c:otherwise>
						</c:choose>
					</c:forEach>
				</colgroup>
				<tbody>
					<c:forEach var="group" items="${groupList}" varStatus="i">
						<c:forEach var="user" items="${group.userList}" varStatus="idx">
						
							<c:if test="${idx.first}">
								<tr>
									<td style="word-break:break-all; text-align:center; background:#E1DFD9;"><c:out value="${group.groupName}"/></td>
									<c:forEach var="i" begin="1" end="31">
										<td style="background:#E1DFD9;text-align:center;"><c:out value="${i}"/></td>
									</c:forEach>
								</tr>
							</c:if>
							<tr>
								<td style="text-align:center;background:#F6F6F6;"><a href="/action/ScheduleAction.do?mode=scheduleOfMonth&history=true&ssn=<c:out value="${user.ssn}"/>"><c:out value="${user.userName}"/></a></td>
																
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d01Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d01Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-01','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d01Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d02Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d02Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-02','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d02Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d03Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d03Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-03','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d03Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d04Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d04Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-04','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d04Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d05Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d05Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-05','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d05Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d06Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d06Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-06','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d06Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d07Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d07Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-07','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d07Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d08Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d08Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-08','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d08Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d09Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d09Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-09','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d09Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d10Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d10Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-10','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d10Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d11Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d11Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-11','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d11Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d12Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d12Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-12','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d12Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d13Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d13Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-13','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d13Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d14Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d14Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-14','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d14Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d15Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d15Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-15','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d15Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d16Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d16Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-16','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d16Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d17Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d17Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-17','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d17Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d18Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d18Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-18','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d18Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d19Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d19Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-19','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d19Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d20Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d20Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-20','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d20Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d21Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d21Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-21','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d21Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d22Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d22Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-22','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d22Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d23Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d23Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-23','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d23Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d24Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d24Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-24','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d24Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d25Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d25Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-25','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d25Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d26Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d26Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-26','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d26Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d27Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d27Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-27','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d27Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d28Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d28Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-28','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d28Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d29Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d29Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-29','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d29Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d30Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d30Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-30','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d30Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
								<c:choose><c:when test="${user.scheduleDailyMasterInfo.d31Icon eq ''}"><td></td></c:when><c:otherwise><td style="cursor: hand<c:if test="${user.scheduleDailyMasterInfo.d31Icon=='100.png'}">;background:#d9ecff</c:if>;" onclick="schedule_Info('<c:out value="${user.ssn}"/>','<c:out value="${intYear}"/>-<c:out value="${intMonth}"/>-31','');" ><img src="/images/calendar/<c:out value="${user.scheduleDailyMasterInfo.d31Icon}" escapeXml="true"/>"/></td></c:otherwise></c:choose>
							</tr>
						</c:forEach>
					</c:forEach>
				</tbody>				
			</table>
		</td>
	</tr>
	<tr>
		<td height="30px">&nbsp;</td>
	</tr>
</table>
</body>
</html>