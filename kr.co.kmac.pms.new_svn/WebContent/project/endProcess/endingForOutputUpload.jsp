<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<style>
	.dragAndDropDiv0 {
		border: 2px dashed #92AAB0;
		width: 751px;
		height: 80px;
		color: #92AAB0;
		text-align: center;
		vertical-align: middle;
		padding: 10px 4px 10px 10px;
		font-size: 200%;
		display: table-cell;
	}

	.dragAndDropDiv {
		border: 2px dashed #92AAB0;
		width: 751px;
		height: 80px;
		color: #92AAB0;
		text-align: center;
		vertical-align: middle;
		padding: 10px 4px 10px 10px;
		font-size: 200%;
		display: table-cell;
	}
	.attachOutputType{
		display:inline-block;
		vertical-align: middle;
		color:#30693D;
		width:100px;
		margin-left:5px;
		margin-right:5px;
	}
	.attachOutputName{
		display:inline-block;
		vertical-align: middle;
		color:#30693D;
		width:128px;
		margin-left:5px;
		margin-right:5px;
	}
	.filename{
		display:inline-block;
		vertical-align: middle;
		width:265px;
		margin-left:5px;
		margin-right:5px;
	}
	.filesize{
		display:inline-block;
		vertical-align: middle;
		color:#30693D;
		width:70px;
		margin-left:5px;
		margin-right:5px;
	}
	.progressBar {
		width: 77px;
		height: 22px;
		border: 1px solid #ddd;
		border-radius: 5px; 
		overflow: hidden;
		display:inline-block;
		margin-left:5px;
		margin-right:5px;
		vertical-align:middle;
	}	 
	.progressBar div {
		height: 100%;
		color: #fff;
		text-align: right;
		width: 0;
		padding:4px 0px;
		background-color: #0ba1b5; border-radius: 3px; 
	}
	.abort{
		display: none;
		cursor:pointer;
		width: 24px;
		margin-right: 10px;
		vertical-align:middle;
	}
	.download {
		cursor: hand; 
		display: none;
	}
</style>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
var projectCode = "<c:out value="${projectSummaryData.projectCode}"/>";
var businessType = "<c:out value="${ending.businessTypeCode}"/>";
var processType  = "<c:out value="${ending.processTypeCode}"/>";
var viewMode = "ending";

function draftRequest(){
	var ActionURL = "/action/ProjectEndingAction.do";	
	ActionURL += "?mode=insertAditionalOutput";
	var sFrm = document.forms["endingFrm"];
	
	AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				document.location = "/action/ProjectEndingAction.do?mode=selectEndingForOutputUpload&projectCode=<c:out value="${projectSummaryData.projectCode}" />";
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){
				alert("저장할 수 없습니다.");
			}
		}
	)
}

function open_attachFile() {
	var infomWin;
	var surl = "/project/endProcess/essentialOutputList.jsp";
	var sfeature = "top=0,left=50,width=430,height=300,scrollbars=no";
	infomWin = window.open(surl,"attachWin",sfeature);
	infomWin.focus();
}
function fileDownload(uuid){
	if(uuid != ""){
		if($$("#_targetDownload").length == 0)
			document.body.insertAdjacentHTML('beforeEnd', "<iframe id=\"_targetDownload\" src=\"\" style=\"display:none\"></iframe>");
    	$("_targetDownload").src = "/servlet/RepositoryDownLoadServlet?fileId=" + uuid;
	}
}

function deleteRowAttachFile(obj, fileId){	
	if(!confirm("파일을 삭제하시겠습니까?")) return;
	var deleteURL = "/action/RepositoryAction.do?mode=deleteFile"; //Upload URL
	var extraData ={"fileId":fileId}; //Extra Data.
	var jqXHR=j$.ajax({
		url: deleteURL,
		type: "GET",
		cache: false,
		data: extraData,
		success: function(data){
			var tr = j$(obj).parent().parent();
			tr.remove();
		}
	}); 
	
}
</script>
</head>
<body>
<%-- 작업영역 --%>
<div style="margin: 70 0 0 20 ">
	<form name="endingFrm" method="post">
													
		<!-- Hidden 필드 시작 -->
		<input type="hidden" name="projectCode" value="<c:out value="${projectSummaryData.projectCode}" />">
		<input type="hidden" name="endGubun" value="">
		<input type="hidden" name="endTaskState" value="<c:out value="${projectSummaryData.endTaskState}" />">
		<input type="hidden" name="pmSsn" value="<c:out value="${projectSummaryData.pmName}" />">
		
		<!-- Hidden 필드 종료 -->
	
		<table width="765" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
					<jsp:param name="title" value="산출물 업로드" />
					<jsp:param name="backButtonYN" value="Y" />
					</jsp:include>
				</td>
			</tr>

							
			<!-- sub 타이틀 영역 시작--> 
			<tr>
				<td><h4 class="subTitle">프로젝트 개요</h4></td>
			</tr>
			<!-- sub 타이틀 영역 종료--> 		
										
	
			<tr>
				<td>
					<table cellSpacing="0" cellpadding="0" width="765" >
						<tr>
							<td width="120px" class="detailTableTitle_center">프로젝트명</td>
							<td class="detailTableField_left"><c:out value="${projectSummaryData.projectName}" /></td>
							<td width="120px" class="detailTableTitle_center">담당 CBO</td>
							<td class="detailTableField_left"><c:out value="${projectSummaryData.runningDivName}" escapeXml="false" /></td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">고객사</td>
							<td class="detailTableField_left"><c:out value="${projectSummaryData.customerName}" /></td>
							<td class="detailTableTitle_center">컨택포인트</td>
							<td class="detailTableField_left"><c:out value="${projectSummaryData.customerContPName}" /></td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">산업구분</td>
							<td class="detailTableField_left"><code:code code="${projectSummaryData.industryTypeCode}" /></td>
							<td class="detailTableTitle_center">추진기간</td>
								<fmt:parseDate value="${projectSummaryData.realStartDate}" pattern="yyyyMMdd" var="realStartDate" />
								<fmt:formatDate value="${realStartDate}" pattern="yyyy-MM-dd" dateStyle="short" var="formatDate1" />
								<fmt:parseDate value="${projectSummaryData.realEndDate}" pattern="yyyyMMdd" var="realEndDate" />
								<fmt:formatDate value="${realEndDate}" pattern="yyyy-MM-dd" dateStyle="short" var="formatDate2" />
							<td class="detailTableField_left"><c:out value="${formatDate1}" /> ~ <c:out value="${formatDate2}" /></td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">비즈니스 타입</td>
							<td class="detailTableField_left"><code:code tableName="BUSINESS_TYPE_CODE" code="${projectSummaryData.businessTypeCode}" /></td>
							<td class="detailTableTitle_center">비즈니스 솔루션</td>
							<td class="detailTableField_left"><code:code code="${projectSummaryData.projectDetailCode}" /></td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">PM</td>
							<td class="detailTableField_left"><expertPool:exportname ssn="${projectSummaryData.pmName}" /></td>
							<td class="detailTableTitle_center">PL</td>
							<td class="detailTableField_left"><expertPool:exportname ssn="${projectSummaryData.plName}" /></td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">참여 컨설턴트</td>
							<td class="detailTableField_left" colspan="3"><c:out value="${projectSummaryData.memberName}" /></td>
						</tr>
					</table>
				</td>
			</tr>

			
			<!-- sub 타이틀 영역 시작--> 
			<tr>
				<td>
					<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
						<tr><td height='10'></td></tr>
						<tr>
							<td width="70%" align="left"><h4 class="subTitle">필수 산출물</h4></td>
							<td width="30%">
								<div class="btNbox txtR">
									<a class="btN3fac0c" style="padding: 4px 10px" title="필수 산출물 안내" href="#" onclick="open_attachFile()">필수 산출물 안내</a>
								</div>
							</td>
						</tr>
					</table>
					<div style="width: 100%; height: 250px; overflow-x: hidden; overflow-y: auto; border:1 solid #C7C7C7;">
						<table width="745px;" border="0" cellpadding="0" cellspacing="0">
							<%@include file="/common/repository/fileReUpload1.jsp" %>
						</table>
					</div>
					<%--
					<c:if test="${fileMode == 'WRITE'}">
						<div class="btNbox mt15 txtR">
							<a class="btNa0a0a0 txt2btn" href="#" onclick="addRowAttachFile1('attachFileTable1')">행 추가</a>
							<a class="btNe14f42 txt2btn" href="#"  onclick="deleteRowAttachFile()">행 삭제</a>
						</div>
					</c:if>							
					 --%>
				</td>
			</tr>
			<!-- sub 타이틀 영역 종료--> 
								
			<!-- sub 타이틀 영역 시작--> 
			<tr>
				<td>
					<h4 class="subTitle">비필수 산출물</h4>
					<div style="width: 100%; height: 250px; overflow-x: hidden; overflow-y: auto; border: 1 solid #C7C7C7;">
						<table width="745px;" border="0" cellpadding="0" cellspacing="0">
							<%@include file="/common/repository/fileReUpload0.jsp" %>
						</table>
					</div>
					<%--
					<c:if test="${fileMode == 'WRITE'}">
						<div class="btNbox mt15 txtR">
							<a class="btNa0a0a0 txt2btn" href="#" onclick="addRowAttachFile0('attachFileTable0')">행 추가</a>
							<a class="btNe14f42 txt2btn" href="#" onclick="deleteRowAttachFile()">행 삭제</a>
						</div>
					</c:if>
					 --%>
				</td>
			</tr>
			<!-- sub 타이틀 영역 종료--> 
								
			</form>
			<tr>
				<td height="15"></td>
			</tr>
			<tr>
				<td style="background:#f3f3f3; height:36px; ">
					<div class="btNbox txtR">
						<a class="btNe006bc6 txt4btn" href="#" onclick="draftRequest()">저장</a>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>