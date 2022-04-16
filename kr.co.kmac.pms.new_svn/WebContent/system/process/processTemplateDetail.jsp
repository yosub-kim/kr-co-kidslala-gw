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

function addRowProcessTemplateDetail() {
    var tableObj = $('processTemplateDetailTable');

    var tdCount = tableObj.down('tr').down('td').nextSiblings().length + 1 ;
    
    var newRow=tableObj.insertRow(-1);
	var newCellArr = new Array();
	for ( var i=0;i<tdCount;i++ ){
		newCellArr[i] = newRow.insertCell(-1);
	}
	new Effect.Highlight(newCellArr[0], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[1], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[2], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[3], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[4], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[5], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[6], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[7], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 

	var tbRowId = (tableObj.rows.length-1);
	
	newCellArr[0].innerHTML = "<input name='processTemplateDetailCheck' type='checkbox' />";
	newCellArr[1].innerHTML = "<input name='workSeq' type='text' value='' class='textInput_left' style='width: 100%;'>";
	newCellArr[2].innerHTML = "<select name='level' class='selectbox'><option value='0'>0</option><option value='1'>1</option></select>";
	newCellArr[3].innerHTML = "<input name='parentWorkSeq' type='text' value='-1' class='textInput_left' style='width: 100%;'>";
	newCellArr[4].innerHTML = "<input name='activityName' type='text' value='' class='textInput_left' style='width: 100%;'>";
	newCellArr[5].innerHTML = "<select name='workType' class='selectbox'><option value='0'>정보입력</option><option value='1'>결재업무</option></select>";
	newCellArr[6].innerHTML = "<select name='editable' class='selectbox'><option value='true'>Y</option><option value='false'>N</option></select>";
	newCellArr[7].innerHTML = "<select name='necessary' class='selectbox'><option value='true'>Y</option><option value='false'>N</option></select>";
	newCellArr[8].innerHTML = "<a title='산출물관리' class='btN006bc6 txt2btn' href='#' onclick='goProcessTemplateDetailAttach(this);'>산출물</a>";
	
}

function deleteRowProcessTemplateDetail() {
	var deleteOverAllIds = "";
	var chkBoxEls = $$('input[name="processTemplateDetailCheck"]');
	for ( var i=0;i<chkBoxEls.length;i++){ 
		if ( chkBoxEls[i].checked ) {
			//해당 데이터 삭제 로직
			var workSeq = $(chkBoxEls[i]).up('td').nextSiblings()[0].down().value;
			
			var ActionURL = "/action/ProcessTemplateAction.do?mode=deleteProcessTemplateDetailEntity";
			var status = AjaxRequest.post (
					{	'url': ActionURL,
						'parameters' : { "processTemplateCode": $("processTemplateCode").value, "workSeq" : workSeq },
						'onSuccess':function(obj){
						},
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("삭제 할 수 없습니다.");
						}
					}
			); status = null;	
				
			$(chkBoxEls[i]).up().up().remove();
		}
	}
}

function goProcessTemplateDetailAttach(obj) {
	var workSeq = $(obj).up('td').siblings('td')[1].down().value;
	var activityName = $(obj).up('td').siblings('td')[4].down().value;

	var win = new Window('modal_window', {
		className : "dialog",
		title : activityName + " 관련 산출물 목록",
		top : 50,
		left : 50,
		width : 664,
		height : 350,
		zIndex : 150,
		opacity : 1,
		resizable : true,
		showEffectOptions : {duration : 0.1},
		hideEffectOptions : {duration : 0.1},		
		url : "/action/ProcessTemplateAction.do?mode=getProcessTemplateAttach&processTemplateCode=<c:out value="${processTemplate.processTemplateCode}"/>&workSeq="+workSeq
	});
	win.show(true);
	win.setDestroyOnClose();
}

function storeProcessTemplateDetail(){
	var ActionURL = "/action/ProcessTemplateAction.do?mode=storeProcessTemplateDetail";
	var sFrm = document.forms["processTemplateDetailForm"];

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert("저장하였습니다.");
					document.location = "/action/ProcessTemplateAction.do?mode=getProcessTemplateDetail&processTemplateCode=<c:out value="${processTemplate.processTemplateCode}"/>";
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;		
}

function deleteProcessTemplateDetail() {
	var ActionURL = "/action/ProcessTemplateAction.do?mode=deleteProcessTemplateDetail";
	var sFrm = document.forms["processTemplateDetailForm"];

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert("삭제하였습니다.");
					//document.location = "/action/ProcessTemplateAction.do?mode=getProcessTemplateDetail&processTemplateCode="+<c:out value="${processTemplate.processTemplateCode}"/>;
					document.location = "/system/process/processManagement.jsp?pane=template#";
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("삭제할 수 없습니다.");
				}
			}
	); status = null;		
}

function goProcessTemplateList(){
	document.location = "/system/process/processManagement.jsp?pane=template";
}
</script>
</head>

<body>
	<form name="processTemplateDetailForm" method="post">
		<div style="display: none;">
			<input type="text" name="processTemplateCode" id="processTemplateCode" value="<c:out value="${processTemplate.processTemplateCode}"/>"/>
			<input type="text" name="processTemplateName" id="processTemplateName" value="<c:out value="${processTemplate.processTemplateName}"/>"/>
		</div>
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
		<!-- 타이틀 영역 시작-->
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="프로세스 템플릿 수정" />
						<jsp:param name="backButtonYN" value="Y" />
					</jsp:include>
				</td>
			</tr>
		<!-- 타이틀 영역 종료-->
		
		<!-- sub 타이틀 영역 시작-->
			<tr>
				<td>
					<h4 class="subTitle mt15 mb5"><c:out value="${processTemplate.processTemplateName}"/></h4>
				</td>
			</tr>
						
		<!-- sub 타이틀 영역 종료-->

		<!-- 본문 영역 시작-->
			<tr>
				<td>
					<table id="processTemplateDetailTable" class="tableStyle05" style="table-layout: fixed">
						<colgroup>
							<col width="40px">
							<col width="40px">
							<col width="45px">
							<col width="55px">
							<col width="*">
							<col width="95px">
							<col width="60px">
							<col width="60px">
							<col width="100px">
						</colgroup>
						<thead id="processTemplateDetailTableHeader">
							<tr> 
								<td style="padding-left: 0px; padding-right: 0px">선택</td>
								<td style="padding-left: 0px; padding-right: 0px">순번</td>
								<td>Level</td>
								<td style="padding-left: 0px; padding-right: 0px">부모순번</td>
								<td>Activity 명</td>
								<td>workType</td>
								<td style="padding-left: 0px; padding-right: 0px">편집여부</td>
								<td style="padding-left: 0px; padding-right: 0px">필수여부</td>
								<td>산출물정의</td>
							</tr> 
						</thead>
						<tbody id="processTemplateDetailTableBody">
							<c:if test="${!empty processTemplateDetailList}">
								<c:forEach var="rs" items="${processTemplateDetailList}">
									<tr>
										<td><input name="processTemplateDetailCheck" id="processTemplateDetailCheck" type="checkbox" /></td>
										<td><input name="workSeq" id="workSeq" type="text" value="<c:out value="${rs.workSeq}"/>" class="textInput_left" style="width: 100%;"></td>
										<td><select name="level" id="level" class="selectbox"><option value="0" <c:if test="${rs.level == '0'}">selected</c:if>>0</option><option value="1" <c:if test="${file.level == '1'}">selected</c:if>>1</option></select></td>
										<td><input name="parentWorkSeq" id="parentWorkSeq" type="text" value="<c:out value="${rs.parentWorkSeq}"/>" class="textInput_left" style="width: 100%;"></td>
										<td><input name="activityName" id="activityName" type="text" value="<c:out value="${rs.activityName}"/>" class="textInput_left" style="width: 100%;"></td>
										<td><select name="workType" id="workType" class="selectbox"><option value="0" <c:if test="${rs.level == '0'}">selected</c:if>>정보입력</option><option value="1" <c:if test="${file.level == '1'}">selected</c:if>>결재업무</option></select></td>
										<td><select name="editable" id="editable" class="selectbox"><option value="true" <c:if test="${rs.editable}">selected</c:if>>Y</option><option value="false" <c:if test="${!rs.editable}">selected</c:if>>N</option></select></td>
										<td><select name="necessary" id="necessary" class="selectbox"><option value="true" <c:if test="${rs.necessary}">selected</c:if>>Y</option><option value="false" <c:if test="${!file.necessary}">selected</c:if>>N</option></select></td>
										<td><a title="산출물관리" class="btN006bc6 txt2btn" href="#" onclick="goProcessTemplateDetailAttach(this);">산출물</a></td>
									</tr> 
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</td> 
			</tr>
			<tr>
				<td>
					<div class="btNbox mb10 mt10 txtR">
						<a class="btN3fac0c txt2btn" href="#" onclick="javascript:addRowProcessTemplateDetail()">행추가</a>
						<a class="btNe14f42 txt2btn" href="#" onclick="javascript:deleteRowProcessTemplateDetail()">행 삭제</a>
					</div>
				</td>
			</tr>	
									
		<!-- 본문 영역 종료-->
		
		
		<!-- 버튼부분-->
			<tr>
				<td height="10"></td>
			</tr>
			<tr>
				<td>
					<table width="100%" height="36" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="btNbox txtR">
									<a class="btNe006bc6 txt2btn" href="#" onclick="storeProcessTemplateDetail()">저장</a>
									<a class="btNe14f42 txt2btn" href="#" onclick="deleteProcessTemplateDetail()">삭제</a>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		<!-- 버튼종료-->
		</table>						
	</form>					
</body>

</html>					