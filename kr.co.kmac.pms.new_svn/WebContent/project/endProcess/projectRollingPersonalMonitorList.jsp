<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function sendProjectCSMailAgain(projectCode, customerSeq){

	var ActionURL = "/action/ProjectRollingAction.do";	
	ActionURL += "?mode=sendProjectCSMailAgain&projectCode="+projectCode+"&customerSeq="+customerSeq;
	var status = AjaxRequest.get(
			{	'url':ActionURL,
				'async' : false,
				'anotherParameter':'false',
				'onSuccess':function(obj){ 
		        	//document.form.target = "";		
		        	//document.form.action = "/action/ProjectRollingAction.do?mode=getProjectRollingMonitor";
		        	//document.form.submit();
		           	alert('메일을 발송하였습니다.');
		           	doSearch();
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("메일 발송 실패.");
				}
			});
}
function customerChargerChange(projectCode, seq, surveyEndDate) {
	var detailWin;
	var surl = "https://intranet.kmac.co.kr/kmac/vocinfo/customerchargerchange.asp";
	surl += "?projectCode=" + projectCode;
	surl += "&seq=" + seq;
	surl += "&surveyEndDate=" + surveyEndDate;
	
	var sFeatures = "toolbar=no,resizable=no,location=no,menubar=no,scrollbars=0,width=510,height=240";
	
	detailWin = window.open(surl,"customerChargerChange", sFeatures);
	detailWin.focus();
}
function viewIcsiResult(projectCode, receiveEmail) {
	var detailWin;
	var surl = "https://intranet.kmac.co.kr/kmac/vocinfo/icsiview_new.asp";
	surl += "?projectCode="+projectCode+"&receiveEmail="+receiveEmail;

	var sFeatures = "toolbar=no,resizable=no,location=no,menubar=no,scrollbars=1,top=120, left=120, width=1200,height=800";
	
	detailWin = window.open(surl,"projectIcsiResult", sFeatures);
	detailWin.focus();
}
function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/ProjectRollingAction.do?mode=getProjectRollingPersonalMonitor";
	document.form.submit();
}
function doSearch() {
	document.form.target = "";		
	document.form.action = "/action/ProjectRollingAction.do?mode=getProjectRollingPersonalMonitor";
	document.form.submit();
}
</script>
</head>

<body>
<form name="form" method="post">	
<input type="hidden" name="pageNo"> 
<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>" >

		<!-- location -->
		<div class="location">
			<p class="menu_title">고객만족도관리</p>
			<ul>
				<li class="home">HOME</li>
				<li>PMS</li>
				<li>프로젝트 관리</li>
				<li>고객만족도 관리</li>
			</ul>
		</div>
		<!-- // location -->
		
			<!-- contents sub -->
	<div class="contents sub">
	
		<!-- 신규 프로젝트 등록 -->
		<div class="box">

				<div class="search_box total">
					<div class="select_group">
						<div class="select_box">
							<div class="label_group">
								<p>비즈니스 유형</p>
								<select name='businessTypeCode' class='select'>
										<option value='ALL'>전체</option>
										<option value='BTA' <c:if test="${businessTypeCode=='BTA'}">selected</c:if>>컨설팅</option>
										<option value='BTD' <c:if test="${businessTypeCode=='BTD'}">selected</c:if>>리서치</option>
										<option value='N1'  <c:if test="${businessTypeCode=='N1'}">selected</c:if>>공개교육(장기)</option>
										<option value='N4'  <c:if test="${businessTypeCode=='N4'}">selected</c:if>>사내교육</option>
										<option value='DE'  <c:if test="${businessTypeCode=='DE'}">selected</c:if>>수주연수</option>
								</select>
							</div>
							<div class="label_group">
								<p>설문 발송일</p>
								<date:year beforeYears="2" afterYears="1" hasAll="Y" attribute=" name='year' class='select' style='width:48%;' " selectedInfo="${year}" />&nbsp
							 		<date:month hasAll="Y" attribute=" name='month' class='select' style='width:48%;' " selectedInfo="${month}" />
							</div>
							<div class="label_group">
								<p>응답 여부</p>
								<select name='isFinish' class='select' style='width: 100%;'>
								<option value="">응답여부</option>
								<option value='YES' <c:if test="${isFinish=='YES'}">selected</c:if>>예</option>
								<option value='NO' <c:if test="${isFinish=='NO'}">selected</c:if>>아니오</option>
								</select>
							</div>
						</div>
						<div class="select_box">
							<div class="label_group">
								<p>프로젝트명</p>
								<input type="text" name="projectName" value="<c:out value="${projectName}"/>" class="textInput_left" style="width: 100%;" onkeypress="if(event.keyCode == 13) doSearch();">
							</div>
						</div>
					</div>
					<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색 <i class="mdi mdi-magnify"></i></button></div>
					</div>
			</div>
			
			<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">프로젝트별 고객만족도</p>
				</div>
			</div>
		
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: *" />
								<col style="width: 10%" />
								<col style="width: 10%" />
								<col style="width: 10%" />
								<col style="width: 10%" />
								<col style="width: 10%" />
								<col style="width: 15%" />
							</colgroup>
							<tr>
								<th>프로젝트명</th>
								<th>상태</th>
								<th>설문 발송일</th>
								<th>설문 수신일</th>
								<th>설문 응답일</th>
								<th>설문 재발송</th>
								<th>설문 결과</th>			
							</tr>
							<c:if test="${!empty projectRollingPersonalMonitorList.list}">
								<c:forEach var="rs" items="${projectRollingPersonalMonitorList.list}">
								<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
									<td style="cursor:hand;" onclick="customerChargerChange('<c:out value="${rs.projectCode}"/>','<c:out value="${rs.customerSeq}"/>','<c:out value="${rs.surveyEndDate}"/>')" class="myoverflow"><c:out value="${rs.projectName}"/></td> 
									<td <%-- <c:if test="${rs.evalDelayDate > 30}"> bgcolor="#ffdfff" </c:if> --%>><c:out value="${rs.projectStateName}"/><c:if test="${rs.evalDelayDate > 30}">지연</c:if></td> 
									<td><c:out value="${rs.surveySendDate}"/></td>
									<td><c:out value="${rs.receiveDate}"/></td>
									<td><c:out value="${rs.surveyEndDate}"/></td>
									<td>
										<c:if test="${rs.projectState == '4' && rs.surveyEndDate == null}">
											<div class="btNbox txtC">
												<button type="button" class="btn line btn_blue" onclick="sendProjectCSMailAgain('<c:out value="${rs.projectCode}"/>','<c:out value="${rs.customerSeq}"/>')"><i class="mdi mdi-post"></i>발송</button>
												<%-- <a class="btNa0a0a0 txt0btn" href="#" onclick="sendProjectCSMailAgain('<c:out value="${rs.projectCode}"/>','<c:out value="${rs.customerSeq}"/>')" >발송</a> --%>
											</div>
										</c:if> 
									</td>
									<td>
										<c:if test="${rs.surveyEndDate != null}">
											<span class="pink"><c:out value="${rs.answer }" />점</span> &nbsp<button type="button" class="btn line btn_d_grey" onclick="viewIcsiResult('<c:out value="${rs.projectCode}"/>','<c:out value="${rs.receiveEmail}"/>')"><i class="mdi mdi-progress-check"></i>상세</button>
											<%-- <img src="/images/btn_glass.jpg" border="0" style="cursor: hand;" onclick="viewIcsiResult('<c:out value="${rs.projectCode}"/>','<c:out value="${rs.receiveEmail}"/>')" > --%>
										</c:if>
									</td>
								</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty projectRollingPersonalMonitorList.list}"><tr><td colspan="7"><br>검색 결과가 존재하지 않습니다 .<br><br></td></tr></c:if>
					</table>
					<div class="paging_area">
						<SCRIPT LANGUAGE="JavaScript">
								document.write( makePageHtml2( 
										<c:out value="${projectRollingPersonalMonitorList.valueListInfo.pagingPage}" default="1"/>, 
										10, 
										<c:out value="${projectRollingPersonalMonitorList.valueListInfo.totalNumberOfEntries}" default="0"/> , 
										10)
								) ;
						</SCRIPT>
				</div>
				</div>
			</div>
		</div>
		<!-- // 신규 프로젝트 등록 -->
		
		
		
		<%-- 페이징 영역 끝--%>
</form>
</body>

</html>					