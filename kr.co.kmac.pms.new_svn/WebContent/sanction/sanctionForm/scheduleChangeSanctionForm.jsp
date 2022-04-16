<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>
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
	var doubleSubmitFlag = false;
	function doubleSubmitCheck(){
		if(doubleSubmitFlag){
			return doubleSubmitFlag;
		}else{
			doubleSubmitFlag = true;
			return false;
		}
	}
	function draftRequest(){
		
		var ActionURL;
		if($("subject").value == ""){
			alert('제목을 입력하세요.');
			document.scheduleChangeSanctionForm.subject.focus();
			return;
		}
		if($("content").value == ""){
			alert('품의 내용을 입력하세요.');
			document.scheduleChangeSanctionForm.content.focus();
			return;
		}
		if($F('cioSsn') == ""){
			alert("승인자를 지정하세요.");return;
		}		  
		if($('seq').value == '0'){
			ActionURL = "/action/ScheduleChangeSanctionAction.do?mode=createScheduleChangeSanction";
		}else{
			ActionURL = "/action/ScheduleChangeSanctionAction.do?mode=executeScheduleChangeSanction";
			<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT' && sanctionDoc.state != 'SANCTION_STATE_REJECT_DRAFT' }">
				if(scheduleChangeSanctionForm.isApproved[0].checked == false && scheduleChangeSanctionForm.isApproved[1].checked == false){
					alert('승인여부를 선택하세요.');return;
				}
			</c:if>
		}
		if(doubleSubmitCheck()) return;
		
		var sFrm = document.forms["scheduleChangeSanctionForm"];

		var status = AjaxRequest.submit(
				sFrm,
				{	'url':ActionURL,
					'onSuccess':function(obj){
						alert('등록하였습니다.');
						if($('seq').value == '0'){
							document.location = "/action/MyProjectAction.do?mode=getMyProjectList";
						}else{
							document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
						}
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;		
	}

	function tempSaveWork(){
		var sFrm = document.forms["scheduleChangeSanctionForm"];
		var status = AjaxRequest.submit(
				sFrm,
				{	'url': "/action/ScheduleChangeSanctionAction.do?mode=saveScheduleChangeSanction",
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						if(res.result == 'SUCCESS'){alert('저장하였습니다.');}						
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;				
	}
	
	function deleteWork(){
		if(confirm("삭제 하시겠습니까?")) {
			var sFrm = document.forms["scheduleChangeSanctionForm"];
			var status = AjaxRequest.submit(
					sFrm,
					{	'url': "/action/ScheduleChangeSanctionAction.do?mode=deleteScheduleChangeSanction",
						'onSuccess':function(obj){
							var res = eval('(' + obj.responseText + ')');
							if(res.result == 'SUCCESS'){alert('삭제하였습니다.');
								document.scheduleChangeSanctionForm.target = "";
								document.scheduleChangeSanctionForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
								document.scheduleChangeSanctionForm.submit();								
							}		
						},
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("저장할 수 없습니다.");
						}
					}
			);	 
		}
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

	function refTaskRequest(){
		var sFrm = document.forms["scheduleChangeSanctionForm"];

		var status = AjaxRequest.submit(
				sFrm,
				{	'url': "/action/GeneralSanctionAction.do?mode=executeRefWork",
					'onSuccess':function(obj){
						alert('등록하였습니다.');
						document.location = "/action/WorkCabinetAction.do?mode=getMyRefWorkList";
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;		
	}
</script>
</head>

<body>

<form name="scheduleChangeSanctionForm" method="post" enctype="multipart/form-data">
	<div id="hiddneValue" style="display: none;">
		<input type="text" id="taskId" name="taskId" value="<c:out value="${sanctionDoc.taskId}" />" />
		<input type="text" id="approvalType" name="approvalType" value="<c:out value="${sanctionDoc.approvalType}" />" />
		<input type="text" id="projectCode" name="projectCode" value="<c:out value="${sanctionDoc.projectCode}" />" />
		<input type="text" id="seq" name="seq" value="<c:out value="${sanctionDoc.seq}" />" /> 
		<input type="text" id="scheduleChangeSeq" name="scheduleChangeSeq" value="<c:out value="${scheduleChange.scheduleChangeSeq}" />" /> 
		<input type="text" id="state" name="state" value="<c:out value="${sanctionDoc.state}" />" />
	</div>
	
	<!-- location -->
		<div class="location">
				<p class="menu_title">일정변경품의</p>
				<ul>
					<li class="home">HOME</li>
					<li>MY PROJECT</li>
					<li>일정변경품의</li>
			</ul>
		</div>
	<!-- // location -->	
	
	<div class="contents sub"><!-- 서브 페이지 .sub -->
			<div class="fixed_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1"><i class="mdi mdi-file-document-outline"></i>내용 작성</p>	
					</div>
					<div class="btn_area">
						<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back();'"><i class="mdi mdi-backburger"></i>목록</button>
					</div>
				</div>	
				<div class="fixed_contents sc">
					<!-- sanctionLine area -->
					<%@include file="/sanction/common/sanctionLineInfo.jsp" %>	
					<!-- // sanctionLine area -->
					
					<!-- 기안 내용 -->
					<div class="board_box">
						<div class="title">
							<div class="h1_area">
								<p class="h1">기본 내용</p>
							</div>
						</div>
						<div class="board_contents">
							<table class="tbl-edit">
								<colgroup>
									<col style="width: 13%"/>
									<col style="width: 37%" />
									<col style="width: 13%" />
									<col style="width: 37%" />
								</colgroup>
								<tbody>
									<tr>
										<th>소속</th>
										<td><c:out value="${userDept }" /></td>
										<th>성명 / 직위</th>
										<td><c:out value="${userName }" /> / <c:out value="${companyPositionName }" /></td>
									</tr>
									<tr>
										<th>결재유형</th>
										<td><c:out value="${sanctionTemplate.approvalName}" /></td>
										<th>제목</th>
										<td><input type="text" id="subject" name="subject" class="textInput_left" style="width: 100%"  <c:if test="${readonly}">readonly</c:if> value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${project.projectName}"/> 일정변경품의</c:when><c:otherwise><c:out value="${sanctionDoc.subject}"/></c:otherwise></c:choose>" ></td>
									</tr>
									<tr>
										<th>프로젝트 정보</th>
										<td colspan="3">[<c:out value="${project.projectCode}"/>] <c:out value="${project.projectName}"/></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class="board_box">
						<div class="title_both">
							<div class="h1_area">
								<p class="h1">상세 내용</p>
							</div>
							<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' }">
								<div class="text-R">
										<c:if test="${!readonly}">
										<button type="button" class="btn line btn_blue" onclick="draftRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
										</c:if>
										<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
											<!-- <a class="btNa0a0a0 txt4btn" href="#" onclick="showEntrustInfo();">업무위임</a> -->
										</c:if>
										<c:if test="${sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
											<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>업무 삭제</button>
											<button type="button" class="btn line btn_grey" onclick="tempSaveWork()"><i class="mdi mdi-square-edit-outline"></i>임시 저장</button>
										</c:if>
										<c:if test="${readonly && isRefWork}"> 
											<button type="button" class="btn line btn_blue" onclick="refTaskRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
										</c:if>
										<%-- <c:if test="${readonly && sanctionTemplate.approvalType == 'draft2' && sanctionDoc.state == 'SANCTION_STATE_COMPLETE'}"> 
											<button type="button" class="btn line btn_grey" onclick="print()"><i class="mdi mdi-file-excel-outline"></i>인쇄하기</button>
										</c:if> --%>
								</div>
							</c:when><c:otherwise> </c:otherwise></c:choose>
						</div>
						<div class="board_contents">
							<table class="tbl-edit td-c">
								<colgroup>
									<col style="width: 25%"/>
									<col style="width: 25%" />
									<col style="width: 25%" />
									<col style="width: 25%" />
								</colgroup>
								<tbody>
									<tr>
										<th colspan="2">프로젝트 기간 (변경 전)</th>
										<th colspan="2">프로젝트 기간 (변경 후)</th>
									</tr>
									<tr>
										<th>시작 일자</th>
										<th>종료 일자</th>
										<th>시작 일자</th>
										<th>종료 일자</th>
									</tr>
									<tr>
										<script>
											jQuery(function(){jQuery( "#realStartDate" ).datepicker({});});
											jQuery(function(){jQuery( "#realEndDate" ).datepicker({});});
										</script>
										<c:choose>
											<c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
												<fmt:parseDate value="${project.preStartDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
												<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="formatted1"/>
												<fmt:parseDate value="${project.preEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
												<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="formatted2"/>
												<fmt:parseDate value="${project.realStartDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var3"/>
												<fmt:formatDate value="${var3}" pattern="yyyy-MM-dd" var="formatted3"/>
												<fmt:parseDate value="${project.realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var4"/>
												<fmt:formatDate value="${var4}" pattern="yyyy-MM-dd" var="formatted4"/>
												<td><input type="hidden" name="preStartDate" value="<c:out value="${project.preStartDate}" />" /><c:out value="${formatted1}" /></td>
												<td><input type="hidden" name="preEndDate" value="<c:out value="${project.preEndDate}" />" /><c:out value="${formatted2}" /></td>
												<td><input type="text" name="realStartDate" id="realStartDate" value="<c:out value="${formatted3}" />" style="width: 50%"/></td>
												<td><input type="text" name="realEndDate" id="realEndDate" value="<c:out value="${formatted4}" />" style="width: 50%"/></td>
											</c:when>
											<c:otherwise>
												<fmt:parseDate value="${scheduleChange.preStartDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
												<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="formatted1"/>
												<fmt:parseDate value="${scheduleChange.preEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
												<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="formatted2"/>
												<fmt:parseDate value="${scheduleChange.realStartDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var3"/>
												<fmt:formatDate value="${var3}" pattern="yyyy-MM-dd" var="formatted3"/>
												<fmt:parseDate value="${scheduleChange.realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var4"/>
												<fmt:formatDate value="${var4}" pattern="yyyy-MM-dd" var="formatted4"/>
												<td><input type="hidden" name="preStartDate" value="<c:out value="${formatted1}" />" /><c:out value="${formatted1}" /></td>
												<td><input type="hidden" name="preEndDate" value="<c:out value="${formatted2}" />" /><c:out value="${formatted2}" /></td>
												<td><input type="text" name="realStartDate" id="realStartDate" value="<c:out value="${formatted3}" />" style="width: 50%"/></td>
												<td><input type="text" name="realEndDate" id="realEndDate" value="<c:out value="${formatted4}" />" style="width: 50%"/></td>														
											</c:otherwise>
										</c:choose>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="board_contents">
							<textarea id="content" name="content" class="textArea" style="width: 100%; height: 200px;"
									><c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.templateText}" /></c:when
									><c:otherwise><c:out value="${sanctionDoc.content}" /></c:otherwise></c:choose
									></textarea>
						</div>
					</div>
					
					<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' }">
						<div style="display:none">
							<div class="board_box">
								<div class="title_both">
									<div class="h1_area">
										<c:choose>
										<c:when test="${!readonly }">
											<p class="h1">결재 선택/의견 작성</p>
										</c:when>
										<c:otherwise>
											<p class="h1">승인/반려 내역</p>
										</c:otherwise>
										</c:choose>
									</div>
									<div class="text-R">
										<c:if test="${!readonly}">
										<button type="button" class="btn line btn_blue" onclick="draftRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
										</c:if>
										<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
											<!-- <a class="btNa0a0a0 txt4btn" href="#" onclick="showEntrustInfo();">업무위임</a> -->
										</c:if>
										<c:if test="${sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
											<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>업무 삭제</button>
											<button type="button" class="btn line btn_grey" onclick="tempSaveWork()"><i class="mdi mdi-square-edit-outline"></i>임시 저장</button>
										</c:if>
									</div>
								</div>
								<div class="board_contents">
									<!-- sanctionApprove -->
									<%@include file="/sanction/common/sanctionApproveInfo.jsp" %>
									<!-- // sanctionApprove -->
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="board_box">
								<div class="title_both">
									<div class="h1_area">
										<c:choose>
										<c:when test="${!readonly }">
											<p class="h1">결재 선택/의견 작성</p>
										</c:when>
										<c:otherwise>
											<p class="h1">승인/반려 내역</p>
										</c:otherwise>
										</c:choose>
									</div>
									<div class="text-R">
										<c:if test="${!readonly}">
										<button type="button" class="btn line btn_blue" onclick="draftRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
										</c:if>
										<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
											<!-- <a class="btNa0a0a0 txt4btn" href="#" onclick="showEntrustInfo();">업무위임</a> -->
										</c:if>
										<c:if test="${sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
											<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>업무 삭제</button>
											<button type="button" class="btn line btn_grey" onclick="tempSaveWork()"><i class="mdi mdi-square-edit-outline"></i>임시 저장</button>
										</c:if>
										<c:if test="${readonly && isRefWork}"> 
											<button type="button" class="btn line btn_blue" onclick="refTaskRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
										</c:if>
										<%-- <c:if test="${readonly && sanctionTemplate.approvalType == 'draft2' && sanctionDoc.state == 'SANCTION_STATE_COMPLETE'}"> 
											<button type="button" class="btn line btn_grey" onclick="print()"><i class="mdi mdi-file-excel-outline"></i>인쇄하기</button>
										</c:if> --%>
									</div>
								</div>
								<div class="board_contents">
									<!-- sanctionApprove -->
									<%@include file="/sanction/common/sanctionApproveInfo.jsp" %>
									<!-- // sanctionApprove -->
								</div>
							</div>
					</c:otherwise></c:choose>
				</div>
			</div>
		</div>
						
		<!-- 버튼부분-->
		</table>
		<script type="text/javascript">
		function showEntrustInfo(){
			$('entrustInfo').style.top = document.body.scrollHeight-120;
			$('entrustInfo').style.left = 457;
			$('entrustInfo').show();
		}
		
		function entrustRequest(){
			if($F('entrustUserName') ==""){alert("위임자를 지정하시기 바랍니다.");return;}
			var sFrm = document.forms["scheduleChangeSanctionForm"];
			var status = AjaxRequest.submit(
					sFrm, 
					{	'url': "/action/GeneralSanctionAction.do?mode=entrustGeneralSanction",
						'onSuccess':function(obj){
							alert('업무를 위임하였습니다.');
							document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
						},
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("execution Fail");
						}
					}
			); status = null;			
		}	
		</script>
		<div id="entrustInfo" 
			style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 3px;">
			<table style="" cellpadding="3" cellspacing="3">
				<tr>
					<td><h4 style="subTitle">업무위임 요청</h4></td>
				</tr>
				<tr>
					<td><table id="delayInfoTable" width="290px">
						<thead id="delayInfoTableHeader">
							<tr>
								<td class="detailTableTitle_center" width="27%">위임자</td>
								<td class="detailTableField_left" width="65%">
								<input type="hidden" id="entrustUserSsn" name="entrustUserSsn">
								<input type="Text" id="entrustUserName" name="entrustUserName" class="textInput_left" readonly></td>
								<td class="detailTableField_left" width="8%">
								<img alt="위임자 선택" src="/images/btn_glass.jpg"  style="cursor: hand;" onclick="openExpertPoolPopUp('entrust')"></td>
							</tr>
						</thead>
						<tbody id="delayInfoTableBody">
						</tbody>
					</table></td>
				</tr>
				<tr align="right">
					<td colspan="2">
						<div class="btNbox txtR">
							<a class="btNe006bc6 txt2btn" href="javascript: entrustRequest()">위임 실행</a>
							<a class="btNa0a0a0 txt2btn" href="#" onclick="$('entrustInfo').hide()">취소</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>						
</body>
</html>
