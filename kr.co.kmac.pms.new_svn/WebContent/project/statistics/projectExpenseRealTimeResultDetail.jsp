<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
<%@page import="java.util.List"%>
<%@page import="net.mlw.vlh.ValueList"%>

<%@page import="kr.co.kmac.pms.project.statistics.data.ExpenseRealTimeResultDetail"%>
<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@page import="kr.co.kmac.pms.expertpool.data.ExpertPool"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>실시간 성과급 내역</title>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" /><!-- 서브페이지에서만 사용 -->		
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" /><!-- 셀렉트 박스 UI -->
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<%@ include file="/common/include/includeJavaScript.jsp"%>
<%
	ExpertPool expertPool = (ExpertPool)request.getAttribute("expertPool");
%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResultDetail&ssn=<c:out value="${ssn}"/>";
	document.form.submit();
}
function rateChangeClose(){
	$('rateChange').style.display = 'none';
}
function rateChangeShow(projectCode, preDate, resRate){
	<c:if test="${popupFlag == 'true'}">
		var mResRate = $$('input[name="mResRate"]');
		if(resRate == '0'){
			mResRate[0].checked = true;
		}else if(resRate == '0.5'){
			mResRate[1].checked = true;
		}else if(resRate == '1'){
			mResRate[2].checked = true;
		}else if(resRate == '1.5'){
			mResRate[3].checked = true;
		}else if(resRate == '2'){
			mResRate[4].checked = true;
		}
		$('mResRate').value = resRate;
		$('rateChange').style.display = 'none';
		$('rateChange').style.top = event.clientY;
		$('rateChange').style.left = '243';
		$('rateChange').style.display= 'inline';
		$('rateChangeDate').innerHTML = preDate;
		$('rateChangeProjectCode').innerHTML = projectCode;
	</c:if>
}
/*
 		String projectCode = request.getParameter("projectCode");
		String ssn = request.getParameter("writerSsn");
		String assignDate = request.getParameter("assignDate");
		String payAmount = request.getParameter("payAmount"); 
 
 */
 var a;
function updateResReportRate(){
		var mResRate = $$('input[name="mResRate"]');
		if(mResRate[0].checked){
			a = 0;
		}else if(mResRate[1].checked){
			a = 0.5;
		}else if(mResRate[2].checked){
			a = 1;			
		}else if(mResRate[3].checked){
			a = 1.5;
		}else if(mResRate[4].checked){
			a = 2;
		}
		
		AjaxRequest.post (
			{	'url': "/action/ProjectReportInfoAction.do?mode=updatePreportPayAmount",
				'parameters' : { "projectCode": $("rateChangeProjectCode").innerHTML , 
								"writerSsn": '<%=expertPool.getSsn()%>', 
								"assignDate": $('rateChangeDate').innerHTML , 
								"payAmount" : a}, 
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					if(res.result == 'SUCCESS'){
						AjaxRequest.post (
								{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
									'parameters' : {"projectCode": $("rateChangeProjectCode").innerHTML },
									'onSuccess':function(obj){
										var res = eval('(' + obj.responseText + ')');
										if(res.result == 'SUCCESS'){
											alert('데이터를 갱신하였습니다.');
										}
									},
									'onLoading':function(obj){},
									'onError':function(obj){
										alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
										
									}
								}
						); 						
						document.form.target = "";		
						document.form.action = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResultDetail&ssn=<c:out value="${ssn}"/>&projectCode=<c:out value="${projectCode}"/>";
						document.form.submit();
					}
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
		);
} 

function projectExpenseRealTimeRestResultDetail(ssn){
	var url = "/action/RealTimeProjectExpenseAction.do?mode=getProjectExpenseRealTimeRestResultDetail"
				+ "&ssn=" + ssn;
	var sFeatures = "top=50,left=50,width=790,height=500,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "projectExpenseRealTimeRestResultDetail", sFeatures);
	detailWin.focus();
}
</script> 
</head>  
<body>
	<form name="form" method="post">
		<div style='display: none;'>
			<input type="hidden" name="pageNo"> 
			<input type="hidden" name="year" value="<c:out value="${year}"/>">  
			<input type="hidden" name="month"  value="<c:out value="${month}"/>">
			<input type="hidden" name="role" value="<c:out value="${role}"/>">
			<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>">
			<input type="hidden" name="divCode" value="<c:out value="${divCode}"/>">
		</div>
		
		<!-- location -->
		<div class="location">
			<p class="menu_title">실시간 성과급 예상 내역 (<c:out value="${expertPool.name }" />)</p>
			<ul>
				<li class="home">HOME</li>
				<li>업무지원</li>
				<li>내역보기</li>
				<li>실시간 성과급 예상 내역</li>
			</ul>
		</div>
		<!-- // location -->
		<div class="contents sub">
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">성과급 내역 </p>
					</div>
				</div>
				
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<thead>
						<colgroup>
							<col style="width: *%"/>
							<col style="width: 20%"/>
							<col style="width: 20%" />
							<col style="width: 20%" />
						</colgroup>
						<tr>
							<th>프로젝트 명</th>
							<th>프로젝트 코드</th>
							<th>성과급 소계</th>
							<th>품의 진행 여부</th>
						</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty list2.list}">
									<c:forEach var="rs1" items="${list2.list}">
									<tr>
										<td style="text-align:center"><c:out value="${rs1.projectName}" /></td>
										<td style="text-align:center"><c:out value="${rs1.projectCode}" /></td>
										<td style="text-align: right">
											<fmt:formatNumber value="${rs1.eachSalary}" type="number" var="eachSalary" pattern="#,###"/>
											<c:out value="${eachSalary}" />
										</td>
										<td style="text-align:center"><c:out value="${rs1.isSanction}" /></td>
									</tr>
									</c:forEach>
									<tr>
										<td style="background: #F5F7F8; text-align:center;"  colspan="2"><b>현재 성과급 합계</b></td>
										<td style="text-align:right" colspan="2">
											<fmt:formatNumber value="${list2.list[0].totalRealTimeSalary}" type="number" var="totalSalary"  pattern="#,###"/>
											<c:out value="${totalSalary}" />
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr style="text-align:center">
										<td align="center" colspan="4">검색된 데이터가 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<c:choose>
							<c:when test="${year <= 2021 and month <= 04 }">
								<p class="h1">20% 성과급 내역</p>
							</c:when>
							<c:otherwise>
								<p class="h1">인센티브 내역</p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<thead>
						<colgroup>
							<col style="width: *%"/>
							<col style="width: 20%"/>
							<col style="width: 20%" />
							<col style="width: 20%" />
						</colgroup>
						<tr>
							<th>프로젝트 명</th>
							<th>프로젝트 코드</th>
							<th>성과급 소계</th>
							<th>품의 진행 여부</th>
						</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty list3.list}">
									<c:forEach var="rs1" items="${list3.list}">
									<tr>
										<td style="text-align:center"><c:out value="${rs1.projectName}" /></td>
										<td style="text-align:center"><c:out value="${rs1.projectCode}" /></td>
										<td style="text-align: right">
											<fmt:formatNumber value="${rs1.eachSalary}" type="number" var="eachSalary" pattern="#,###"/>
											<c:out value="${eachSalary}" />
										</td>
										<td style="text-align:center"><c:out value="${rs1.isSanction}" /></td>
									</tr>
									</c:forEach>
									<tr>
										<td style="background: #F5F7F8; text-align:center;"  colspan="2">
											<c:choose>
												<c:when test="${year <= 2021 and month <= 04 }">
													<b>현재 20%  합계</b>
												</c:when>
												<c:otherwise>
													<b>현재 인센티브 합계</b>
												</c:otherwise>
											</c:choose>
										</td>
										<td style="text-align:right" colspan="2">
											<fmt:formatNumber value="${list3.list[0].totalRealTimeSalary}" type="number" var="totalSalary"  pattern="#,###"/>
											<c:out value="${totalSalary}" />
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr style="text-align:center">
										<td align="center" colspan="4">검색된 데이터가 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>

<div id="rateChange" 
	style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 2px;">
	<table cellpadding="3" cellspacing="3">
		<tr>
			<td><h4 class="subTitle">지도율 수정</h4></td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td class="detailTableTitle_center" width="80">PJ코드</td>
						<td class="detailTableField_left" width="220"><span id="rateChangeProjectCode"></span></td>
					</tr>
					<tr>
						<td class="detailTableTitle_center">지도일</td>
						<td class="detailTableField_left"><span id="rateChangeDate"></span></td>
					</tr>
					<tr>
						<td class="detailTableTitle_center">지도율</td>
						<td class="detailTableField_left">
							<input type="radio" name="mResRate" id="mResRate">0&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="mResRate" id="mResRate">0.5&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="mResRate" id="mResRate">1&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="mResRate" id="mResRate">1.5&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="mResRate" id="mResRate">2 
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<div class="btNbox pt5 pb10 txtR">
					<a class="btNe006bc6" style="padding: 4px 10px" href="#" onclick="updateResReportRate()">수정</a>
					<a class="btNa0a0a0" style="padding: 4px 10px" href="#" onclick="rateChangeClose()">닫기</a>
				</div>
			</td>
		</tr>
	</table>
</div>

</html>