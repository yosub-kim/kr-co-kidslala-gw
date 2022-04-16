<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
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
	document.emergencyForm.action = "/action/ProjectEmergencyAction.do?mode=PUData";
	document.emergencyForm.submit();	
}
function goProjectSearchPage(runningPUCode, projectState, delayState, costOver, endProcess, hasDate)  {
	var params="";
	if(runningPUCode != '' && runningPUCode != undefined){
		params = params + "&runningPUCode="+runningPUCode;
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
	//alert(params);
	document.emergencyForm.target = "rightFrame";
	document.emergencyForm.action = "/action/ProjectSearchAction.do?mode=getProjectSearchList"+params;
	document.emergencyForm.submit();	
}
</script>
</head>
 
<body onload="resizeFrame()">
	<form name="emergencyForm" method="post">
	<div style="display: none;">
	</div>
	<table width="753" cellpadding="0" cellspacing="0">
		<tr>
			<td width="753" valign="top">
				<table width="753" cellpadding="0" cellspacing="0"> 

					<!-- sub 타이틀 영역 시작-->  
					<tr>
						<td>
							<table width="753" height="24" border="0" cellpadding="0" cellspacing="0">
								<tr><td height='10' colspan="3"></td></tr>
								<tr>
									
									<td width="30%" align="left" valign="middle"><span class="subTitle">&nbsp;프로젝트 현황</span></td>
									<td width="70%" align="right" valign="middle">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr>
												<td align="right">
													<date:year beforeYears="4" afterYears="4" attribute=" name=startYear class=selectbox style='width: 50px' " selectedInfo="${startYear}" />년&nbsp;
													<date:month attribute=" name=startMonth class=selectbox style='width: 40px' " selectedInfo="${startMonth}" />월&nbsp;~&nbsp;
													<date:year beforeYears="4" afterYears="4" attribute=" name=endYear class=selectbox style='width: 50px' " selectedInfo="${endYear}" />년&nbsp;
													<date:month attribute=" name=endMonth class=selectbox style='width: 40px' " selectedInfo="${endMonth}" />월&nbsp;
													<a href="#" onclick="doSearch()" ><img src="/images/btn_search.jpg" alt="검색" border="0"></a>
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
							<table width='753' cellpadding="0" cellspacing="0" class="listTable" >
								<colgroup>
									<col width="*" align="center"> 
									<col width="67px" align="center">
									<col width="67px" align="center"> 
									<col width="67px" align="center"> 
									<col width="67px" align="center"> 
									<col width="67px" align="center"> 
									<col width="67px" align="center"> 
									<col width="67px" align="center"> 
									<col width="67px" align="center"> 
									<col width="67px" align="center"> 
								</colgroup>
								<thead>
									<tr>
										<td rowspan='2'>구분</td>
										<td colspan='3'>진행 프로젝트</td>
										<td colspan='3'>종료처리 프로젝트</td>
										<td rowspan='2'>종료<br>프로젝트</td>
										<td rowspan='2'>전체<br>프로젝트</td>
									</tr>
									<tr>
										<td>프로젝트수</td>
										<td>일정지연</td>
										<td>예산초과</td>
										<td>프로젝트수</td>
										<td>평가지연</td>
										<td>리뷰지연</td>
									</tr>
								</thead>
								<tbody> 
									<c:if test="${!empty list}">
										<c:forEach var="rs" items="${list.list}">
											<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
												<td><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>')" ><c:out value="${rs.data_1}"/></a></td>
												<td><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '3')" ><c:out value="${rs.ingTotal}"/></a></td>
												<td><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '3', 'R')" ><c:out value="${rs.delay}"/></a></td>
												<td><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '3',   '', 'Y')" ><c:out value="${rs.costOver}"/></a></td>
												<td><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>',   '',     '',  '', 'endProcess')" ><c:out value="${rs.endingTotal}"/></a></td>
												<td><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>',   '', 'ER' )" ><c:out value="${rs.evalTotal}"/></a></td>
												<td><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>',   '', 'RR' )" ><c:out value="${rs.reviewTotal}"/></a></td>
												<td><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '6',     '' , '', '','yes')" ><c:out value="${rs.doneTotal}"/></a></td>
												<td><c:out value="${rs.total}"/></td>
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