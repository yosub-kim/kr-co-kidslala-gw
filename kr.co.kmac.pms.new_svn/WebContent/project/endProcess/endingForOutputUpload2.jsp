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

<style>
	.dragAndDropDiv0 {
		border: 2px dashed #92AAB0;
		width: 38%;
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
		width: 38%;
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

function fileDownloadrr(size){
	
	var sFrm = document.forms["endingFrm"];
	var arr = new Array();
	
	// delay time
	fnSleep = function (delay){
        
        var start = new Date().getTime();
        while (start + delay > new Date().getTime());

    };
	
	for( var i = 0; i < size; i++){
		arr[i] = eval('document.endingFrm.fileList_'+i+'.value');
	}
	for(var i = 0; i < size; i++)
	{
		if(arr[i] != undefined){
			if($$("#_targetDownload").length == 0)
				document.body.insertAdjacentHTML('beforeEnd', "<iframe id=\"_targetDownload\" src=\"\" style=\"display:none\"></iframe>");
	    	$("_targetDownload").src = "/servlet/RepositoryDownLoadServlet?fileId=" + arr[i];
		}else{
			Tab.changeTab("pane12");
		}
		fnSleep(1000);
	}
}
function draftRequest2(){
	var ActionURL = "/action/ProjectEndingAction.do";	
	ActionURL += "?mode=insertAditionalOutput2";
	var sFrm = document.forms["endingFrm"];
	
	// delay time
	fnSleep = function (delay){
        
        var start = new Date().getTime();
        while (start + delay > new Date().getTime());

    };
	
	AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				/* alert("저장하였습니다."); */
				/* document.location = "/action/ProjectEndingAction.do?mode=selectEndingForOutputUpload2&projectCode="+$('projectCode').value; */
				/* location.reload(true); */
				/* fnSleep(2000); */
				/* Tab.changeTab("pane12"); */
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

function fileDownload2(uuid){
	
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
			draftRequest2();
		}
	}); 
}
</script>
</head>
<body>
<%-- 작업영역 --%>
	<form name="endingFrm" method="post">
													
		<!-- Hidden 필드 시작 -->
		<input type="hidden" name="projectCode" id="projectCode" value="<c:out value="${projectSummaryData.projectCode}" />">
		<input type="hidden" name="endGubun" value="">
		<input type="hidden" name="endTaskState" value="<c:out value="${projectSummaryData.endTaskState}" />">
		<input type="hidden" name="pmSsn" value="<c:out value="${projectSummaryData.pmName}" />">
		<input type="hidden" name="fileListSize" id="fileListSize" value="<c:out value="${fileListSize }" />" >
		<!-- Hidden 필드 종료 -->
		<div style="margin:70px 15px 0 15px;">
			<!-- tab menu contents -->
				<div class="board_box">
					<div class="title_both">
						<div class="h1_area">
							<p class="h1">프로젝트 세금계산서 발행내역</p>
						</div>
						<div class="select_box">
							<button type="button" class="btn line btn_blue" onclick="draftRequest2()"><i class="mdi mdi-square-edit-outline"></i>저장</button>
							<button type="button" class="btn line btn_excel" onclick="fileDownloadrr('<c:out value="${fileListSize }"/>')"><i class="mdi mdi-microsoft-excel"></i>EXCEL 저장</button>
						</div>
					</div> 
					 <div class="board_contents">
						<div style="width: 100%; height: 500px; overflow-x: hidden; overflow-y: auto; border:1 solid #C7C7C7;">
								<%@include file="/common/repository/fileReUpload2.jsp" %>
						</div>
						<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
								<c:if test="${!empty fileList1}">
									<c:forEach var="file" items="${fileList1}" begin="0" end="${fileListSize }" varStatus="status">
										<input type="hidden" name="fileList_<c:out value="${status.index }" />" value="<c:out value="${file.attachFileId }" />" />
									</c:forEach>
								</c:if>
						</table>
					</div>
				</div>
		</div>
	</form>
</body>
</html>