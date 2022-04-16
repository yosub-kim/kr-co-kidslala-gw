<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function resizeFrame() {
	if(parent && parent.DYNIFS){
		parent.DYNIFS.resize('emergency');
	}	
}
function doSearch() {
	document.emergencyForm.target = "";
	document.emergencyForm.action = "/action/ProjectEmergencyAction.do?mode=cfoData2";
	document.emergencyForm.submit();	
}
function doDelaySearch(){
	var deplayListWin;
	var sURL = "/action/ProjectEmergencyAction.do?mode=cfoData2";
	var sFeather = "top=100,left=100,width=800,height=600,scrollbars=yes";
	deplayListWin = window.open(sURL, "deplayListWin", sFeather);
	deplayListWin.focus();
	
}
function goProjectSearchPage(runningDivCode, projectState, delayState, costOver, endProcess, hasDate)  {
	var params="";
	if(runningDivCode != '' && runningDivCode != undefined){
		params = params + "&runningDeptCode="+runningDivCode;
	}
	if(projectState != '' && projectState != undefined){
		params = params + "&projectState="+projectState;
	}
	if(delayState != '' && delayState != undefined){
		params = params + "&delayState="+delayState;
	}
	if(costOver != '' && costOver != undefined){	
		params = params + "&costOver="+costOver;
	}
	if(endProcess != '' && endProcess != undefined){
		params = params + "&endProcess="+endProcess;
	}
	if(hasDate == 'yes'){
		params = params + "&realStartDate="+$('startYear').value+$('startMonth').value;
		params = params + "&realEndDate="+$('endYear').value+$('endMonth').value;
	}
	// 비즈니스 유형 변수에 값이 없으면 BTA로 처리되므로 빈값을 넘긴다
	var businessTypeCode="";
	params = params + "&businessTypeCode="+businessTypeCode;
	//var startDate=$('#startDate').val();
	var startDate=document.emergencyForm.startDate.value;
	var endDate=document.emergencyForm.endDate.value;
	//parent.document.location.href = "/action/ProjectSearchAction.do?mode=getProjectSearchList&backButtonYN=Y"+params;
	var url="/action/ProjectSearchAction.do?mode=getProjectSearchList2"+params+"&startDate="+startDate+"&endDate="+endDate;
	window.open(url, 'pjList', 'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=800,height=580,directories=no,menubar=no');
}
function openDelayProjectList(projectState, delayState) {
	var deplayListWin;
	var startDate=document.emergencyForm.startDate.value;
	var endDate=document.emergencyForm.endDate.value;
	var sURL = "/action/ProjectSearchAction.do?mode=getDelayProjectSearchList2&startDate="+startDate+"&endDate="+endDate;
	sURL += "&projectState=" + projectState + "&delayState=" + delayState;
	var sFeather = "top=100,left=100,width=800,height=600,scrollbars=yes";
	deplayListWin = window.open(sURL, "deplayListWin", sFeather);
	deplayListWin.focus();
}
</script>
</head>
 
<body onload="resizeFrame()">
	<form name="emergencyForm" method="post">
	<div style="display: none;">
	</div>
	<table width="725px" cellpadding="0" cellspacing="0">
		<tr>
			<td width="100%" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0"> 

					<!-- sub 타이틀 영역 시작-->  
					<tr>
						<td>
							<table width="100%" height="24" border="0" cellpadding="0" cellspacing="0">
								<tr><td height='10' colspan="3"></td></tr>
								<tr>
									
									<td width="30%" align="left" valign="middle"><span class="subTitle">프로젝트 현황</span></td>
									<td width="70%" align="right" valign="middle">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr>
												<td align="right">
													<div class="btNbox"></div><b>지연 예정 기간</b>&nbsp&nbsp
																<fmt:parseDate value="${startDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
																<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
																<fmt:parseDate value="${endDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
																<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
															<script>
																jQuery(function(){jQuery( "#startDate" ).datepicker({});});
																jQuery(function(){jQuery( "#endDate" ).datepicker({});});
															</script>
															<input type="text" name="startDate" id="startDate" readonly="readonly" size="9" value="<c:out value="${start}"/>" />&nbsp~&nbsp     
															<input type="text" name="endDate" id="endDate" readonly="readonly" size="9" value="<c:out value="${end}" />" />									
													&nbsp&nbsp<a class="btN3fac0c txt2btn" href="#" onclick="doSearch()" >검색</a>
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<!-- sub 타이틀 영역 종료--> 
					
					
			 		<tr>
						<td height="2" align="left" bgcolor=""></td>
					</tr>				
					
					<tr>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="listTable" >
								<colgroup>
									<col width="*" /> 
									<col width="65" />
									<col width="65" /> 
									<col width="65" /> 
									<col width="65" /> 
									<col width="65" /> 
									<col width="65" /> 
									<col width="65" /> 
									<col width="65" /> 
									<col width="65" /> 
								</colgroup>
								<thead>
									<tr>
										<td rowspan='2'>구분</td>
										<td colspan='3'>진행 프로젝트</td>
										<td colspan='2'>평가 프로젝트</td>
										<td colspan='2'>리뷰 프로젝트</td>
										<td rowspan='2'>종료<br>프로젝트</td>
										<td rowspan='2'>전체<br>프로젝트</td>
									</tr>
									<tr>
										<td>전체</td>
										<td><a href="#" onclick="openDelayProjectList('3', 'R')" >일정지연</a></td>
										<td>예산초과</td>
										<td>전체</td>
										<td>평가지연</td>
										<!-- <td><a href="#" onclick="openDelayProjectList('4', 'ER')" >평가지연</a></td> -->
										<td>전체</td>
										<td>리뷰지연</td>
										<!-- <td><a href="#" onclick="openDelayProjectList('5', 'RR')" >리뷰지연</a></td> -->
									</tr>
								</thead>
								<tbody> 
									<c:if test="${!empty list}">
										<c:forEach var="rs" items="${list.list}">
											<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
												<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>')" ><c:out value="${rs.data_1}"/></a></td>
												<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '3')" ><c:out value="${rs.ingTotal}"/></a></td>
												<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '3', 'R')" ><c:out value="${rs.scheduleDelayCnt}"/></a></td>
												<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '3', '', 'Y')" ><c:if test="${rs.costOver > 0}"><span class="bold colore74c3a"></c:if><c:out value="${rs.costOver}"/><c:if test="${rs.costOver > 0}"></span></c:if></a></td>
												<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '4', '',  '', 'endProcess')" ><c:out value="${rs.evalTotal}"/></a></td>
												<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '4',  'ER' )" ><c:out value="${rs.evalDelayCnt}"/></a></td>
												<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '5', '',  '', 'endProcess')" ><c:out value="${rs.reviewTotal}"/></a></td>
												<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '5',  'RR' )" ><c:out value="${rs.reviewDelayCnt}"/></a></td>
												<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '6', '', '', '','yes')" ><c:out value="${rs.doneTotal}"/></a></td>
												<td style="text-align: center;"><c:out value="${rs.total}"/></td>												
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</td>
					</tr>
					
	
	<!-- 버튼부분
					<tr>
						<td height='7'></td>
					</tr>
					<tr>
						<td>
							<table width='783' height='32' bgcolor='#F3F3F3' cellpadding="0" cellspacing="0">
								<tr>
									<td><p align='right'><img src='/images/dbt_b16on.gif' align="absmiddle" onclick="openExpertPoolPopUpForProjectReport(this)">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
	본문종료-->
					
				</table>
			</td>
		</tr>
	</table>						

	</form>
</body>

</html>					