<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<html>
<head>

<script type="text/javascript">
function tempSaveProjectReport() {
	var attachOutputType = $$('select[name="attachOutputType"]');
	var attachOutputName = $$('input[name="attachOutputName"]');
	for (var i = 0; i < attachOutputType.length; i++) {
		if(attachOutputType[i].value == ''){
			alert((i+1)+"번째 첨부파일 타입을 입력하세요.");
			return;
		}
	}
	for (var i = 0; i < attachOutputName.length; i++) {
		if(attachOutputName[i].value == ''){
			alert((i+1)+"번째 첨부파일 문서명을 입력하세요.");
			return;
		}
	}
		
	var ActionURL = "/action/MonthlyReportAction.do?mode=saveProjectReportTask";
	var sFrm = document.forms["monthlyReportForm"];

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert('저장하였습니다.');
					document.location = "/action/MonthlyReportCabinetAction.do?mode=getMyMonthlyReportList";
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;		
}	

function removeProjectReport() {
	if(confirm("삭제 하시겠습니까?")) {
		var ActionURL = "/action/MonthlyReportAction.do?mode=removeProjectReportTask";
		var sFrm = document.forms["monthlyReportForm"];
	
		var status = AjaxRequest.submit(
				sFrm,
				{	'url':ActionURL,
					'onSuccess':function(obj){
						alert('삭제하였습니다.');
						document.location = "/action/MonthlyReportCabinetAction.do?mode=getMyMonthlyReportList";
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;		
	}
}	

function completeProjectReport(appValue) {
	<c:if test="${viewMode != 'SIMPLE'}">
	var attachOutputType = $$('select[name="attachOutputType"]');
	var attachOutputName = $$('input[name="attachOutputName"]');
	for (var i = 0; i < attachOutputType.length; i++) {
		if(attachOutputType[i].value == ''){
			alert((i+1)+"번째 첨부파일 타입을 입력하세요.");
			return;
		}
	}
	for (var i = 0; i < attachOutputName.length; i++) {
		if(attachOutputName[i].value == ''){
			alert((i+1)+"번째 첨부파일 문서명을 입력하세요.");
			return;
		}
	}
	</c:if>
	
	monthlyReportForm.appYN.value = appValue;
	
	var ActionURL = "/action/MonthlyReportAction.do?mode=executeProjectReportTask";
	var sFrm = document.forms["monthlyReportForm"];
	
	/*
	if($("projectMemberStr").value == "") {
		alert("실행 인력을 등록하세요.");
		document.monthlyReportForm.searchEmp.focus();
		return;
	}*/
	if(!isOpenManpowerPopup){
		//alert('투입 일정을 입력하세요');
		//return;
	}
/* 	if($("projectMemberStr").value == ""){
		alert('투입 일정을 입력하세요');
	   return;
	} */
	/* if($("reportTitle").value == ""){
	   alert('제목을 입력하세요.');
	   document.monthlyReportForm.reportTitle.focus();
	   return;
	} */
	if($("reportContent").value == ""){
	   alert('수행내용을 입력하세요.');
	   document.monthlyReportForm.reportContent.focus();
	   return;
	}
	
	/* 프로젝트 레포트 체크 항목 */
	
	/* <c:if test="${weeklyReport.state == 'writer'}" >
		var str = $("reportContent").value;
		var str_len = str.length;
		
		if(str_len < 50){
			alert('프로젝트 수행내용을 구체적으로 작성해주시기 바랍니다.(50자 이상)');
			return;
		}
		
		var attachOutputType_len = attachOutputType.length;
		
		if(attachOutputType_len < 1){
			alert('산출물 및 관련 파일들을 반드시 첨부해 주시기 바랍니다.');
			return;
		}
	</c:if> */

	
	/*
	if($("etcContent").value == ""){
	   alert('기티의견을 입력하세요.');
	   document.monthlyReportForm.etcContent.focus();
	   return;
	}*/

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert('등록하였습니다.');
					document.location = "/action/MonthlyReportCabinetAction.do?mode=getMyMonthlyReportList";
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;		
}

var levelInfo;
function openExpertPoolPopUp(_levelInfo) {
	levelInfo = _levelInfo;
	orgFinder_Open('setSanctionLevelInfo');	
}

function setSanctionLevelInfo(expertPoolList){
	if(levelInfo == "refMember"){
		expertPoolList.each(function(expertPool) {
			$('refMemberSsn').value = $('refMemberSsn').value + ($('refMemberSsn').value.length > 0 ? ", ": "") + expertPool.ssn;
			$('refMemberName').value = $('refMemberName').value + ($('refMemberName').value.length > 0 ? ", ": "") +  expertPool.name; 	
		});
	}else if(levelInfo == "entrust"){
		expertPoolList.each(function(expertPool) {
			$('entrustUserSsn').value = expertPool.ssn;
			$('entrustUserName').value = expertPool.name; 	
		});
	}else{
		var n=0;
		var snactionNameList = $$('input[sanctionInfo="name"]');
		var snactionSsnList = $$('input[sanctionInfo="ssn"]');
		for (var i = 0; i < snactionNameList.length; ++i) {
			try{
				if(levelInfo <= snactionNameList[i].getAttribute("seq")){
					snactionNameList[i].value = expertPoolList[n].name;
					snactionSsnList[i].value = expertPoolList[n].ssn;
					n++;
				}
			}catch(e){}
		}
	}
}

var isOpenManpowerPopup = false;
function openProjecManpowerList(){
	isOpenManpowerPopup = true;
	window.open("/action/ProjectManpowerAction.do?mode=getProjectManpowerList"
					+ "&viewMode=mreport"
					+ "&projectCode=<c:out value="${monthlyReport.projectCode}" />"
					+ "&assignYear=<c:out value="${monthlyReport.assignYear}" />"
					+ "&assignMonth=<c:out value="${monthlyReport.assignMonth}" />" 
					,
			"mpowerlist", "top=100,left=100,width=785,height=450,scrollbars=yes");
}

/*
function openExpertPoolPopUpForProjectMaster(projectMemberElmt) {
	window.open("/action/MonthlyReportAction.do?mode=loadProjectMemberPopup&projectCode=<c:out value="${monthlyReport.projectCode}" />",
				"acct", "top=0,left=0,width=450,height=350,scrollbars=no");	
}
*/

function changeRatio(){
	var res =Math.round($('realProgress').value/$('planProgress').value*100);
	$('progressRatio_txt').innerHTML=res;
	$('progressRatio').value = res;
	if(res < 80){
		$('progressRatio_txt').setStyle({color: 'red'});
	} else {
		$('progressRatio_txt').setStyle({color: 'black'});
	}
}

function resetProjectMemberStr(){
	var spanList = $('projectMemberStr_html').select('span');
	var txt;
	for ( var i=0;i<spanList.length;i++){ 
		txt =+ spanList[i].innerHTML+', ';
	}
	$('projectMemberStr').value = txt.subsring(0, txt.length-1);
}
</script>
</head>

<body>

<form name="monthlyReportForm" method="post" enctype="multipart/form-data">

	<div id="hiddneValue" style="display: none;">
		<input type="text" name="workId" id="workId" value="<c:out value="${monthlyReport.workId}" />" />
		<input type="text" name="appYN" id="appYN" value="" />
		<input type="text" name="projectCode" id="projectCode" value="<c:out value="${monthlyReport.projectCode}" />" />
		<input type="text" name="projectName" id="projectName" value="<c:out value="${monthlyReport.projectName}" />" />
		<input type="text" name="taskFormTypeId" id="taskFormTypeId" value="<c:out value="${monthlyReport.taskFormTypeId}" />" />
		<input type="text" name="taskFormTypeName" id="taskFormTypeName" value="<c:out value="${monthlyReport.taskFormTypeName}" />" />
		
		<input type="text" name="seq" id="seq" value="<c:out value="${monthlyReport.seq}" />" />
		<input type="text" name="workStartDate" id="workStartDate" value="<c:out value="${monthlyReport.projectStartDate}" />" />
		<input type="text" name="workEndDate" id="workEndDate" value="<c:out value="${monthlyReport.projectEndDate}" />" />
		
		<input type="text" name="assignYear" id="assignYear" value="<c:out value="${monthlyReport.assignYear}" />" />
		<input type="text" name="assignMonth" id="assignMonth" value="<c:out value="${monthlyReport.assignMonth}" />" />
			<input type="text" name="assignDate" id="assignDate" value="<c:out value="${monthlyReport.assignDate}" />" />
		<input type="text" name="weekOfProject" id="weekOfProject" value="<c:out value="${monthlyReport.weekOfProject}" />" />

		<input type="text" name="attach" id="attach" value="<c:out value="${monthlyReport.attach}" />" />
		<input type="text" name="state" id="state" value="<c:out value="${monthlyReport.state}" />" />
	</div>	
			
		
	<c:if test="${!readonly}">
		<div class="location">
			<p class="menu_title">프로젝트 레포트 작성</p>
			<ul>
				<li class="home">HOME</li>
				<li>프로젝트 레포트 작성</li>
			</ul>
		</div>
		<!-- // location -->

		<div class="contents sub">
		<div class="fixed_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1"><c:out value="${monthlyReport.assignYear}" />년 <c:out value="${monthlyReport.assignMonth}" />월 프로젝트 레포트</p>	
				</div>
				<div class="btn_area">
					<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back();'"><i class="mdi mdi-backburger"></i>목록</button>
				</div>
			</div>
	
			<div class="fixed_contents sc">
				<div class="board_box">
	</c:if>
					<div class="sign_area">
						<c:if test="${readonly }">
							<div class="board_contents">
							<p class="sign_title"><c:out value="${monthlyReport.assignYear}" />년 <c:out value="${monthlyReport.assignMonth}" />월 프로젝트 레포트</p>
							</div>
						</c:if>
						<c:if test="${!readonly }">
							<p class="sign_title"></p>
						</c:if>
						<div style="display:none">
							<input type="hidden"" sanctionInfo="ssn" seq="1" name="writerName" value="<c:out value="${monthlyReport.writerName}"/>">
							<input type="hidden"" sanctionInfo="ssn" seq="1" name="writerSsn" value="<c:out value="${monthlyReport.writerSsn}"/>">
							<input type="hidden" sanctionInfo="ssn" seq="2" name="reviewerName" value="<c:out value="${monthlyReport.reviewerName}"/>">
							<input type="hidden" sanctionInfo="ssn" seq="2" name="reviewerSsn" value="<c:out value="${monthlyReport.reviewerSsn}"/>">
							<input type="hidden" sanctionInfo="ssn" seq="3" name="approverName" value="<c:out value="${monthlyReport.approverName}"/>">
							<input type="hidden" sanctionInfo="ssn" seq="3" name="approverSsn" value="<c:out value="${monthlyReport.approverSsn}"/>">
							<input type="hidden" name="writeDate" value="<c:out value="${monthlyReport.writeDate}"/>"/>
							<input type="hidden" name="revieweDate" value="<c:out value="${monthlyReport.revieweDate}"/>"/>
							<input type="hidden" name="approveDate" value="<c:out value="${monthlyReport.approveDate}"/>"/>
						</div>
					
						<ul class="sign_box">
							<li class="draft">
								<p class="subject">작성</p>
								<div class="name_group">
								<p class="name"><expertPool:exportname ssn="${monthlyReport.writerSsn}"/></p>
								</div>
								<p class="date"><fmt:parseDate value="${monthlyReport.writeDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="v1"/><fmt:formatDate value="${v1}" pattern="yyyy-MM-dd" /></p>
							</li>
							<li class="review">
								<p class="subject">검토</p>
								<div class="name_group">
								<p class="name"><expertPool:exportname ssn="${monthlyReport.reviewerSsn}"/></p>
								</div>
								<p class="date"><fmt:parseDate value="${monthlyReport.revieweDate}" type="DATE" dateStyle="SHORT"   pattern="yyyyMMdd" var="v2"/><fmt:formatDate value="${v2}" pattern="yyyy-MM-dd" /></p>
							</li>
							<li class="approved">
								<p class="subject">승인</p>
								<div class="name_group">
								<p class="name"><expertPool:exportname ssn="${monthlyReport.approverSsn}"/></p>
								</div>
								<p class="date"><fmt:parseDate value="${monthlyReport.approveDate}" type="DATE" dateStyle="SHORT"   pattern="yyyyMMdd" var="v3"/><fmt:formatDate value="${v3}" pattern="yyyy-MM-dd" /></p>
							</li>
						</ul>
					</div>
			<!-- // sign_area -->
			
			<div class="board_contents">	
			<table class="tbl-edit weekly">
					<colgroup>
						<col style="width: 16%"/>
						<col style="width: 42%" />
						<col style="width: 42%" />
					</colgroup>
					<tbody>
						<tr>
							<th>프로젝트</th>
							<td colspan="2">
								<ul class="chek_ui">
									<c:out value="${monthlyReport.projectName}" />
								</ul>
							</td>													
						</tr>
					</tbody>
			</table>
			</div>
			
			<div class="board_contents">
				<table class="tbl-edit weekly">
					<colgroup>
						<col style="width: 16%"/>
						<col style="width: *" />
					</colgroup>
					<tbody>
						<tr>
							<th>추진 내용</th>
							<td class="text_area">
								<textarea name="reportContent" id="reportContent" style="width: 100%; height: 300px; overflow: auto;" class="textArea" placeholder="※ 프로젝트 수행사항을 명확히 파악할 수 있도록 구체적으로 작성해주시기 바랍니다." <c:if test="${readonly}">readonly</c:if>><c:out value="${monthlyReport.reportContent}" /></textarea>
							</td>
						</tr>
						<tr>
							<th>비고 (기타사항)</th>
							<td class="text_area etc">
								<textarea name="etcContent" id="etcContent" style="width: 100%; height: 150px; overflow: auto;" class="textArea" <c:if test="${readonly}">readonly</c:if>><c:out value="${monthlyReport.etcContent}" /></textarea>
							</td>
						</tr>
						<tr class="file">
							<th><label for="file">첨부파일</label></th>
							<td>
								<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log" /></jsp:include>
							</td>
						</tr>
					</tbody>
				</table>
			</div>


			<c:if test="${!readonly}">			
					<!-- 버튼부분-->
					<div class="btn_area">
						<c:if test="${monthlyReport.state != 'approver' && monthlyReport.state != 'reviewer'}" >
							<button type="button" class="btn btn_blue" onclick="completeProjectReport('Y');">등록요청</button>
						</c:if>
						<c:if test="${monthlyReport.state == 'approver' || monthlyReport.state == 'reviewer'}" >
							<button type="button" class="btn btn_d_blue" onclick="completeProjectReport('Y');">승인</button>
						</c:if>
						<c:if test="${monthlyReport.state != 'approver' && monthlyReport.state != 'reviewer'}" >
							<button type="button" class="btn btn_aqua" onclick="tempSaveProjectReport();">임시저장</button>
						</c:if>
						<%-- <c:if test="${monthlyReport.state == 'approver' || monthlyReport.state == 'reviewer'}" >
							<button type="button" class="btn btn_purple" onclick="showEntrustInfo();">업무위임</button>
						</c:if> --%>
						<c:if test="${monthlyReport.state != 'approver' && monthlyReport.state != 'reviewer'}" >
							<button type="button" class="btn btn_pink" onclick="removeProjectReport();'">삭제</button>
						</c:if>
						<c:if test="${monthlyReport.state == 'approver' || monthlyReport.state == 'reviewer'}" >
							<button type="button" class="btn btn_w_grey" onclick="completeProjectReport('N');">반려</button>
						</c:if>
					</div>
				<!-- 버튼종료-->
				</c:if>	
				<c:if test="${!readonly}">
					<br>	
				</div>
			</div>		
		</c:if>
		<script type="text/javascript">
			function showEntrustInfo(){
				$('entrustInfo').style.top = document.body.scrollHeight/2;
				$('entrustInfo').style.left = 457;
				$('entrustInfo').show();
			}
			
			function entrustRequest(){
				if($F('entrustUserName') ==""){alert("위임자를 지정하시기 바랍니다.");return;}
				var sFrm = document.forms["monthlyReportForm"];
				var status = AjaxRequest.submit(
						sFrm, 
						{	'url': "/action/MonthlyReportAction.do?mode=entrustProjectReportTask",
							'onSuccess':function(obj){
								alert('업무를 위임하였습니다.');
								document.location = "/action/MonthlyReportCabinetAction.do?mode=getMyMonthlyReportList";
							},
							'onLoading':function(obj){},
							'onError':function(obj){
								alert("execution Fail");
							}
						}
				); status = null;			
			}	
		</script>
	</form>						
</body>
</html>
