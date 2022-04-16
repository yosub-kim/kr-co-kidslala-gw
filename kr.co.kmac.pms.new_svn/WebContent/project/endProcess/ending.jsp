<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />

<%--@ include file="/common/include/includeJavaScript.jsp"
<%-- ====================== 공통 include jsp ============= --%>

<script type="text/javascript" src="<c:url value="/js/AjaxRequest.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/Common.js"/>"></script>
<script type="text/javascript" src='<c:url value="/js/pagination.js"/>'></script>

<%-- ====================== Prototype js include jsp ============= --%>
<script type="text/javascript" src="<c:url value="/js/prototype/prototype.js"/>"></script>

<%-- ====================== Scriptaculous include jsp ============= --%>
<script type="text/javascript" src='<c:url value="/js/scriptaculous/builder.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/scriptaculous/scriptaculous.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/scriptaculous/effects.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/scriptaculous/dragdrop.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/scriptaculous/controls.js"/>'></script>

<%-- ====================== File Upload include jsp ============= --%>
<script type="text/javascript" src="/js/upload/ajaxupload_ending.3.6.js"></script>
 

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
var projectCode = "<c:out value="${projectSummaryData.projectCode}"/>";
var businessType = "<c:out value="${ending.businessTypeCode}"/>";
var processType  = "<c:out value="${ending.processTypeCode}"/>";
var viewMode = "ending";

function draftRequest(){
	var ActionURL = "/action/ProjectEndingAction.do";	
	ActionURL += "?mode=insertEnding";
	var sFrm = document.forms["endingFrm"];
	
	if (processType == "N4" && document.endingFrm.endTaskState.value == "next:review-1-init") {
		if (endingFrm.bizFunction.value == "") {
			alert("사내교육은 교육분야를 선택해야 합니다.");
			return;
		}
	}
	
	AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				alert("등록하였습니다.");
				document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){
				alert("등록할 수 없습니다.");
			}
		}
	);
}

function open_projectRateInfo() {
	var rateWin;
	var surl = "/project/endProcess/projectEndInfoTab.jsp?projectCode=" + projectCode;
	surl += "&businessTypeCode=" + businessType;
	surl += "&processTypeCode=" + processType;
	surl += "&viewMode=" + viewMode;
	surl += "&state=" + document.endingFrm.endTaskState.value;	// 리뷰지 상태 값 전달
	var sfeature = "top=0,left=50,width=823,height=700,scrollbars=yes";
	rateWin = window.open(surl,"rateWin",sfeature);
	rateWin.focus();
}
function open_projectBudgetInfo() {
	var rateWin;
	var sfeature = "top=0,left=50,width=823,height=460,scrollbars=yes";
	//var surl = "/action/ProjectBudjetInfoAction.do?mode=goProjectBudgetInfo&projectCode=" + projectCode;
	//surl += "&viewMode=projectSearch";
	//rateWin = window.open(surl,"rateWin",sfeature);
	var url = "https://newbudget.kmac.co.kr:8080/com/login_chk_pms.jsp"
			+ "?param=<authz:authentication operation="username" />|DWPM30600_LINK|?projid=" + projectCode; 
	rateWin = window.open(url,"rateWin",sfeature);
	rateWin.focus();
}
function open_projectAttachInfo() {
	var attachWin;
	var surl = "/action/ProjectViewAction.do?mode=attachFilePopup&projectCode=" + projectCode;
	var sfeature = "top=0,left=50,width=823,height=700,scrollbars=yes";
	attachWin = window.open(surl,"rateWin",sfeature);
	attachWin.focus();
}
function open_attachFile() {
	var infomWin;
	var surl = "/project/endProcess/essentialOutputList.jsp";
	var sfeature = "top=0,left=50,width=430,height=280,scrollbars=no";
	infomWin = window.open(surl,"attachWin",sfeature);
	infomWin.focus();
}
function open_review() {
	var reviewWin;
	var surl = "/project/endProcess/reviewExample.jsp";
	var sfeature = "top=0,left=50,width=765,height=680,scrollbars=no";
	reviewWin = window.open(surl,"reviewWin",sfeature);
	reviewWin.focus();
}
/*
function fileDownload1(uuid){
	if(uuid != ""){
		if($$("#_targetDownload").length == 0)
			document.body.insertAdjacentHTML('beforeEnd', "<iframe id=\"_targetDownload\" src=\"\" style=\"display:none\"></iframe>");
    	//$("_targetDownload").src = "/servlet/PhotoDownLoadServlet?fileId=" + uuid;
		$("_targetDownload").src = "/servlet/RepositoryDownLoadServlet?fileId=" + uuid;
	}
}
*/
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
				if(levelInfo <= snactionNameList[i].seq){
					snactionNameList[i].value = expertPoolList[n].name;
					snactionSsnList[i].value = expertPoolList[n].ssn;
					n++;
				}
			}catch(e){}
		}
	}
}
function showEntrustInfo(){
	$('entrustInfo').style.top = document.body.scrollHeight-340;
	$('entrustInfo').style.left = 457;
	$('entrustInfo').show();
}

function entrustRequest(){
	if($F('entrustUserName') ==""){alert("위임자를 지정하시기 바랍니다.");return;}
	var sFrm = document.forms["endingFrm"];
	var status = AjaxRequest.submit(
			sFrm, 
			{	'url': "/action/ProjectEndingAction.do?mode=entrustEnding",
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
</head>
<body>
<!-- location -->
	<div class="location">
		<p class="menu_title">리뷰지</p>
		<ul>
			<li class="home">HOME</li>
			<li>리뷰지</li>
		</ul>
	</div>
	
	<form name="endingFrm" method="post">
		<div id="entrustInfo" style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 3px;">
			<table style="" cellpadding="3" cellspacing="3">
				<tr>
					<td><img alt="" src="/images/title_de02.jpg" align="top"><span style="subTitle"> <b>업무위임 요청</b></span></td>
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
						<img alt="위임 실행" src="/images/btn_regist.jpg" onclick="entrustRequest()" style="cursor: hand;"/>
						<img alt="취소" src="/images/btn_close.jpg" onclick="$('entrustInfo').hide()" style="cursor: hand;"/>
					</td>
				</tr>
			</table>
		</div>	
		
		<!-- Hidden 필드 시작 -->
		<input type="hidden" name="projectCode" value="<c:out value="${projectSummaryData.projectCode}" />">
		<input type="hidden" name="endGubun" value="">
		<input type="hidden" name="endTaskState" value="<c:out value="${projectSummaryData.endTaskState}" />">
		<input type="hidden" name="workId" value="<c:out value="${workId}" />">
		
		<c:if test="${projectSummaryData.endTaskState != 'next:approve'}">
			<input type="hidden" name="groupComment" value="<c:out value="${ending.groupComment}" escapeXml="false"/>">
		</c:if>
			
		<c:if test="${projectSummaryData.endTaskState != 'next:fin'}">
			<input type="hidden" name="cfoComment" value="<c:out value="${ending.cfoComment}" escapeXml="false"/>">
		</c:if>
		
		<c:if test="${projectSummaryData.endTaskState != 'next:complete'}">
			<input type="hidden" name="cboComment" value="<c:out value="${ending.cboComment}" escapeXml="false"/>">
		</c:if>
		<!-- Hidden 필드 종료 -->
	
		<div class="contents sub">
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">프로젝트 개요</p>
					</div>
					<div class="text-R">
						<button type="button" class="btn btn_aqua" onclick="open_projectRateInfo()">프로젝트 평가</button>
						<c:if test="${projectSummaryData.businessTypeCode == 'BTA'
										|| projectSummaryData.businessTypeCode == 'BTD'
										|| projectSummaryData.businessTypeCode == 'BTF'
										|| projectSummaryData.businessTypeCode == 'BTE'}">
						<button type="button" class="btn btn_pink" onclick="open_projectBudgetInfo()">예/결산 정보</button>
						</c:if>
						<button type="button" class="btn btn_blue" onclick="draftRequest()">등록요청</button>
					<!-- <a class="btN3fac0c txt2btn" href="#" onclick="showEntrustInfo();">업무위임</a> -->
					</div>
				</div>
				
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 20%"/>
							<col style="width: 30%"/>
							<col style="width: 20%"/>
							<col style="width: 30%"/>
						</colgroup>
						<tr>
							<th>프로젝트명</th>
							<td><c:out value="${projectSummaryData.projectName}" /></td>
							<th>담당 CBO</th>
							<td><c:out value="${projectSummaryData.runningDivName}" escapeXml="false" /></td>
						</tr>
						<tr>
							<th>고객사</th>
							<td><c:out value="${projectSummaryData.customerName}" /></td>
							<th>컨택포인트</th>
							<td><c:out value="${projectSummaryData.customerContPName}" /></td>
						</tr>
						<tr>
							<th>산업구분</th>
							<td><code:code code="${projectSummaryData.industryTypeCode}" /></td>
							<th>추진기간</th>
								<fmt:parseDate value="${projectSummaryData.realStartDate}" pattern="yyyyMMdd" var="realStartDate" />
								<fmt:formatDate value="${realStartDate}" pattern="yyyy-MM-dd" dateStyle="short" var="formatDate1" />
								<fmt:parseDate value="${projectSummaryData.realEndDate}" pattern="yyyyMMdd" var="realEndDate" />
								<fmt:formatDate value="${realEndDate}" pattern="yyyy-MM-dd" dateStyle="short" var="formatDate2" />
							<td><c:out value="${formatDate1}" /> ~ <c:out value="${formatDate2}" /></td>
						</tr>
						<tr>
							<th>비즈니스 타입</th>
							<td><code:code tableName="BUSINESS_TYPE_CODE" code="${projectSummaryData.businessTypeCode}" /></td>
							<th>비즈니스 솔루션</th>
							<td><code:code code="${projectSummaryData.projectDetailCode}" /></td>
						</tr>
						<tr>
							<th>PM</th>
							<td><expertPool:exportname ssn="${projectSummaryData.pmName}" /></td>
							<th>PL</th>
							<td><expertPool:exportname ssn="${projectSummaryData.plName}" /></td>
						</tr>
						<tr>
							<th>참여 컨설턴트</th>
							<td colspan="3"><c:out value="${projectSummaryData.memberName}" /></td>
						</tr>
					</table>
				</div>
			</div>
		
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">프로젝트 리뷰 내용</p>
					</div>
				</div>
				
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 20%"/>
							<col style="width: *%"/>
						</colgroup>
					
						<tr height="110px">
							<th><c:out value="${objTitle.strA}" escapeXml="false"/></th>
							<td><div style="padding: 10 10 10 10">
								<textarea name="endBackground" class="textArea" style="width:100%; height: 100%;"><c:out value="${ending.endBackground}" /></textarea>
							</div></td>
						</tr>
						<tr height="110px">
							<th><c:out value="${objTitle.strB}" escapeXml="false"/></th>
							<td><div style="padding: 10 10 10 10">
								<textarea name="endResult" class="textArea" style="width:100%; height: 100%;"><c:out value="${ending.endResult}" /></textarea>
							</div></td>
						</tr>
						<tr height="110px">
							<th><c:out value="${objTitle.strC}" escapeXml="false"/></th>
							<td><div style="padding: 10 10 10 10">
								<textarea name="endReview" class="textArea" style="width:100%; height: 100%;"><c:out value="${ending.endReview}" /></textarea>
							</div></td>
						</tr> 
						<tr height="110px">
							<th><c:out value="${objTitle.strD}" escapeXml="false"/></th>
							<td><div style="padding: 10 10 10 10">
								<textarea name="endSuggestion" class="textArea" style="width:100%; height: 100%;"><c:out value="${ending.endSuggestion}" /></textarea>
							</div></td>
						</tr>
					</table>
				</div>
			</div>
		
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">프로젝트 산출물 (현재까지 프로젝트에 등록 된 산출물)</p>
					</div>
				</div>
				
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c">
						<colgroup>
							<col style="width: 20%"/>
							<col style="width: *%"/>
						</colgroup>
						<tr>
							<th>첨부파일</th>
							<td><jsp:include page="/common/repository/fileUpload_ending.jsp"><jsp:param value="Y" name="log"  /></jsp:include></td>
						</tr>
					</table>
				</div>
			</div>
		
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">의견 내용</p>
					</div>
				</div>
				
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 20%"/>
							<col style="width: *%"/>
						</colgroup>
							<tr height="110px">
								<th>검토자 의견</th>
								<td>
									<c:choose>
										<c:when test="${projectSummaryData.endTaskState == 'next:approve'}">
											<div style="padding: 10 10 10 10">
											<textarea name="groupComment" class="textArea"  style="width:100%; height: 100%"><c:out value="${ending.groupComment}" escapeXml="false"/></textarea>
											</div>
										</c:when>
										<c:otherwise>
											<div style="padding: 10 10 10 10">
											<textarea name="groupCommentview" class="textArea"  style="width:100%; height: 100%" disabled="disabled"><c:out value="${ending.groupComment}" escapeXml="false"/></textarea>
											</div>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr height="110px">
								<th>승인자 의견</th>
								<td>
									<c:choose>
										<c:when test="${projectSummaryData.endTaskState == 'next:fin'}">
											<div style="padding: 10 10 10 10">
											<textarea name="cfoComment" class="textArea"  style="width:100%; height: 100%"><c:out value="${ending.cfoComment}" escapeXml="false"/></textarea>
											</div>
										</c:when>
										<c:otherwise>
											<div style="padding: 10 10 10 10">
											<textarea name="cfoCommentview" class="textArea"  style="width:100%; height: 100%" disabled="disabled"><c:out value="${ending.cfoComment}" escapeXml="false"/></textarea>
											</div>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr height="110px">
								<th>검증자 의견</th>
								<td>
									<c:choose>
										<c:when test="${projectSummaryData.endTaskState == 'next:complete'}">
											<div style="padding: 10 10 10 10">
											<textarea name="cboComment" class="textArea"  style="width:100%; height:100%"><c:out value="${ending.cboComment}" escapeXml="false"/></textarea>
											</div>
										</c:when>
										<c:otherwise>
											<div style="padding: 10 10 10 10">
											<textarea name="cboCommentview" class="textArea"  style="width:100%; height:100%" disabled="disabled"><c:out value="${ending.cboComment}" escapeXml="false"/></textarea>
											</div>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<c:choose>
								<c:when test="${projectSummaryData.endTaskState != 'next:review-1-init' && projectSummaryData.endTaskState != 'next:review-2-init'}">
								<tr>
									<th>승인여부</th>
									<td>
										<ul class="chek_ui">
											<li><input type="radio" name="approveYn" id="approveYn_Y" class="btn_radio" value="Y" checked><label for="approveYn_Y"><p>승인</p></label></li>
											<li><input type="radio" name="approveYn" id="approveYn_N" class="btn_radio" value="N"><label for="approveYn_N"><p>반려</p></label></li>
											<input type="hidden" name="approveYn_T" id="approveYn_T" class="btn_radio" value="">
										</ul>
									</td>
								</tr>
								</c:when>
								<c:otherwise>
									<input type="hidden" name="approveYn" value="Y" />
								</c:otherwise>
							</c:choose>
					</table>
				</div>
			</div>
		
		
			<c:if test="${projectSummaryData.processTypeCode == 'N4' && projectSummaryData.endTaskState == 'next:review-1-init'}">
				<div class="board_box">
					<div class="title_both">
						<div class="h1_area">
							<p class="h1">사내교육 분야 선택</p>
						</div>
					</div>
					
					<div class="board_contents">
						<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 20%"/>
								<col style="width: *%"/>
							</colgroup>
							<tbody>
								<tr>
									<th>분야 선택</th>
									<td>
										<SELECT name="bizFunction" id="bizFunction" class="select">
											<option value=""  >-- 분야를 선택하세요 --</option>
											<option value="PRIV01" >경영전략</option>
											<option value="PRIV02" >인사조직</option>
											<option value="PRIV03" >CS경영</option>
											<option value="PRIV04" >C&C</option>
											<option value="PRIV05" >마케팅</option>
											<option value="PRIV06" >세일즈역량</option>
											<option value="PRIV07" >경영품질</option>
											<option value="PRIV08" >프로세스혁신</option>
											<option value="PRIV09" >생산혁신</option>
											<option value="PRIV10" >기술경영</option>
											<option value="PRIV11" >에너지/환경</option>
											<option value="PRIV12" >SCM</option>
											<option value="PRIV13" >리서치</option>
											<option value="PRIV14" >의식행동혁신교육</option>
											<option value="PRIV15" >컨버전스교육</option>
										</SELECT>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</c:if>
	
			<c:choose>
				<c:when test="${projectSummaryData.endTaskState == 'next:complete'}">
					<div class="board_box">
						<div class="title_both">
							<div class="h1_area">
								<p class="h1">KDB 이관 여부</p>
							</div>
						</div>
					
					<div class="board_contents">
						<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 20%"/>
								<col style="width: *%"/>
							</colgroup>
							<tbody>
								<tr>
									<th>이관여부</th>
									<td>
										<ul class="chek_ui">
											<li><input type="radio" name="kdbYn" class="btn_radio" id="kdbYn_Y" value="Y"><label for="kdbYn_Y"><p>예</p></label></li>
											<li><input type="radio" name="kdbYn" class="btn_radio" id="kdbYn_N" value="N" checked><label for="kdbYn_N"><p>아니오</p></label></li>
											<input type="hidden" name="kdbYn_T" id="approveYn_T" class="btn_radio" value="">
										</ul>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				</c:when>
				<c:otherwise>
					<tr>
						<td><input type="hidden" name="kdbYn" value="" /></td>
					</tr>
				</c:otherwise>
			</c:choose>
			
		</div>
	</form>
</body>
</html>