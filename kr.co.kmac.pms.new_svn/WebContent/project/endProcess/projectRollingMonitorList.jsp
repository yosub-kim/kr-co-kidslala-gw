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
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("메일 발송 실패.");
				}
			});
}
function customerChargerChange(projectCode, seq, surveyEndDate) {
	var detailWin;
	var surl = "http://intranet.kmac.co.kr/kmac/vocinfo/customerchargerchange.asp";
	surl += "?projectCode=" + projectCode;
	surl += "&seq=" + seq;
	surl += "&surveyEndDate=" + surveyEndDate;

	var sFeatures = "toolbar=no,resizable=no,location=no,menubar=no,scrollbars=0,width=510,height=240";
	
	detailWin = window.open(surl,"customerChargerChange", sFeatures);
	detailWin.focus();
}
function viewIcsiResult(projectCode, receiveEmail) {
	var detailWin;
	var surl = "http://intranet.kmac.co.kr/kmac/vocinfo/icsiview_new.asp";
	surl += "?projectCode="+projectCode+"&receiveEmail="+receiveEmail;

	var sFeatures = "toolbar=no,resizable=no,location=no,menubar=no,scrollbars=1,width=820,height=600";
	detailWin = window.open(surl,"projectIcsiResult", sFeatures);
	detailWin.focus();
}
function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/ProjectRollingAction.do?mode=getProjectRollingMonitor";
	document.form.submit();
}
function doSearch() {
	document.form.target = "";		
	document.form.action = "/action/ProjectRollingAction.do?mode=getProjectRollingMonitor";
	document.form.submit();
}
</script>
</head>

<body>
<form name="form" method="post">	
<input type="hidden" name="pageNo" id="pageNo"> 
<input type="hidden" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" >

<!-- location -->
		<div class="location">
			<p class="menu_title">고객만족도현황(관)</p>
			<ul>
				<li class="home">HOME</li>
				<li>관리자</li>
				<li>고객만족도현황(관)</li>
			</ul>
		</div>
		<!-- // location -->
		
			<div class="contents sub"><!-- 서브 페이지 .sub -->
			<!-- 검색 창 -->					
			<div class="box">

				<div class="search_box total">
					<div class="select_group">
						<div class="select_box">
							<div class="label_group">
								<p>비즈니스 타입</p>
								<select name="businessTypeCode" class="select">
									<option value="ALL">전체</option>
									<option value="BTA" <c:if test="${businessTypeCode=='BTA'}">selected</c:if>>컨설팅</option>
									<option value="BTD" <c:if test="${businessTypeCode=='BTD'}">selected</c:if>>리서치</option>
									<option value="N1"  <c:if test="${businessTypeCode=='N1'}">selected</c:if>>공개교육(장기)</option>
									<option value="N4"  <c:if test="${businessTypeCode=='N4'}">selected</c:if>>사내교육</option>
									<option value="DE"  <c:if test="${businessTypeCode=='DE'}">selected</c:if>>수주연수</option>
								</select>
							</div>
							<div class="label_group">
								<p>조직단위</p>
								<org:divList enabled="1" depth="2" attribute=" name='runningDeptCode' class='select' " 
									divType="A" all="Y" isSelectBox="Y" selectedInfo="${runningDeptCode}"></org:divList>
							</div>
							<div class="label_group">
								<p>고객응답여부</p>
								<select name="isFinish" class="select">
									<option value="">응답여부</option>
									<option value="YES" <c:if test="${isFinish=='YES'}">selected</c:if>>예</option>
									<option value="NO" <c:if test="${isFinish=='NO'}">selected</c:if>>아니오</option>
								</select>
							</div>
						</div>
						<div class="select_box">
							<div class="label_group">
								<p>설문발송일</p>
								<date:year beforeYears="3" afterYears="3" hasAll="Y" attribute=" name='year' class='select' style='width:49%' " selectedInfo="${year}" />
							 	<date:month hasAll="Y" attribute=" name='month' class='select' style='width:49%' " selectedInfo="${month}" />
							</div>
							<div class="label_group">
								<p>프로젝트</p>
								<input type="text" name="projectName" id="projectName" value="<c:out value="${projectName}"/>" onkeypress="if(event.keyCode == 13) doSearch();">
							</div>
						</div>
					</div>
				<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색<i class="mdi mdi-magnify"></i></button></div>
			</div>
		</div>
		
		<div class="box">
			<div class="a-both total">
				<p>총<span><c:out value="${projectRollingMonitorList.valueListInfo.totalNumberOfEntries}"/></span>건</p>
			</div>
			<div class="tbl-wrap sc">
				<table id="projectRollingMonitorTable"  class="tbl-list myproject">
					<thead>
						<th>비즈니스 타입</th> 
						<th>조직단위</th>  
						<th>프로젝트명</th> 
						<th>상태 </th> 
						<th>종료일</th>  
						<th>설문 발송일</th> 
						<th>고객 수신일</th> 
						<th>설문 응답일</th> 
						<th>설문 재발송 </th> 
						<th>결과</th> 
					</thead>
					<tbody id="projectRollingMonitorTableBody"> 
						<c:if test="${!empty projectRollingMonitorList.list}">
							<c:forEach var="rs" items="${projectRollingMonitorList.list}">
								<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
									<td><c:out value="${rs.businessTypeName}"/></td> 
									<td><c:out value="${rs.runningDiv}"/></td> 
									<td class="subject" onclick="customerChargerChange('<c:out value="${rs.projectCode}"/>','<c:out value="${rs.customerSeq}"/>','<c:out value="${rs.surveyEndDate}"/>')" style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis; cursor: hand;" text-align="left"><c:out value="${rs.projectName}"/></td> 
									<td <%-- <c:if test="${rs.evalDelayDate > 30}"> bgcolor="#ffdfff" </c:if> --%>><c:out value="${rs.projectStateName}"/><c:if test="${rs.evalDelayDate > 30}">지연</c:if></td> 
									<td><c:out value="${rs.realEndDate}"/></td> 
									<td><c:out value="${rs.surveySendDate}"/></td>
									<td><c:out value="${rs.receiveDate}"/></td>
									<td><c:out value="${rs.surveyEndDate}"/></td>
									<td>
										<c:if test="${rs.projectState == '4' && rs.surveyEndDate == null}">
											<div class="btn_area"><button type="button" class="btn line btn_blue" onclick="sendProjectCSMailAgain('<c:out value="${rs.projectCode}"/>','<c:out value="${rs.customerSeq}"/>')"><i class="mdi mdi-email"></i>발송</button></div>
										</c:if> 
									</td>
									<td>
										<c:if test="${rs.surveyEndDate != null}">
											<a href="javascript:viewIcsiResult('<c:out value="${rs.projectCode}"/>','<c:out value="${rs.receiveEmail}"/>')" class="ico-file">
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty projectRollingMonitorList.list}"><tr><td colspan="10"><br>검색 결과가 존재하지 않습니다 .<br><br></td></tr></c:if>
					</tbody>
				</table>
				<div class="paging_area">
					<SCRIPT LANGUAGE="JavaScript">
						document.write( makePageHtml2( 
								<c:out value="${projectRollingMonitorList.valueListInfo.pagingPage}" default="1"/>, 
								10, 
								<c:out value="${projectRollingMonitorList.valueListInfo.totalNumberOfEntries}" default="0"/> , 
								10)
						) ;
					</SCRIPT>
				</div>
			</div>
		</div>
	</div>
</form>
</body>

</html>					