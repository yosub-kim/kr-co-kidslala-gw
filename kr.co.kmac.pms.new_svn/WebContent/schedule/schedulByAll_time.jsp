<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>근무 일정 관리</title>
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
	var sFeatures = "width=750, height=400, top=100, left=100,scrollbars=yes";

	scheduleInfo = window.open(sURL, "scheduleInfo", sFeatures);
	scheduleInfo.focus();
}

function doSearch() {
	document.frm.submit();
}
</script>
</head>
<body onload="page_load();">
<%-- 작업영역 --%>
<table width="765" cellpadding="0" cellspacing="0">
	<tr>
		<td height="12">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="근무일정관리" />
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
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
							<td class="searchTitle_center">기간</td> 
							<td class="searchField_center">
								<fmt:parseDate value="${startDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
								<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
								<fmt:parseDate value="${endDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
								<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
							<script>
								jQuery(function(){jQuery( "#startDate" ).datepicker({});});
								jQuery(function(){jQuery( "#endDate" ).datepicker({});});
							</script>
							<input type="text" name="startDate" id="startDate" readonly="readonly" size="13" value="<c:out value="${start}" />" />&nbsp~&nbsp     
							<input type="text" name="endDate" id="endDate" readonly="readonly" size="13" value="<c:out value="${end}" />" />									
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
		<td height="5"></td>
	</tr>
	<tr>
		<td align="center">
			<table cellSpacing="0" width="100%" border="0" class="tableStyle03" style="table-layout:fixed">
				<colgroup>
					<col width="84px"/>					
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
									<td style="word-break:break-all;text-align:center; background : #E1DFD9;"><c:out value="${group.groupName}"/></td>
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