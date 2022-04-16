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

function addRowProcessTemplateAttach() {
    var tableObj = $('processTemplateAttachTable');

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

	var tbRowId = (tableObj.rows.length-1);
	
	newCellArr[0].innerHTML = "<input name='processTemplateCheck' type='checkbox' />";
	newCellArr[1].innerHTML = "<input name='attachSeq' type='text' class='textInput_left' style='width: 100%;'>";
	newCellArr[2].innerHTML = "<select name='bizType' class='selectbox' onchange='changeOutputType(this)' style='width: 100%;'>"
										+ "						<option value=''>--선택--</option>"
										+ "						<c:forEach var="item" items="${bizTypeCodeList}" >"
										+ "							<option value='<c:out value="${item.key1}"/>'><c:out value="${item.data1}"/></option>"
										+ "						</c:forEach>"
										+ "					</select>";
	newCellArr[3].innerHTML = "<select name='outputType' class='selectbox' style='width: 100%;'>"
										+ "						<option value=''>--선택--</option>"
										+ "						<c:forEach var="item" items="${attachTypeCodeList}" >	"
										+ "							<option bizType='<c:out value="${item.key2}"/>' value='<c:out value="${item.key1}"/>'><c:out value="${item.data1}"/></option>"
										+ "						</c:forEach>"
										+ "					</select>";
	newCellArr[4].innerHTML = "<input name='outputName' type='text' class='textInput_left' style='width: 100%;'>";
	newCellArr[5].innerHTML = "<select name='necessary' class='selectbox' style='width: 100%;'><option value='true'>Y</option><option value='false'>N</option></select>";

}

function changeOutputType(obj){
	var selectedBizType = $(obj).value;
	var outputType = $(obj).up('td').nextSiblings()[0].down('select');

	outputType.options.length = null;
	var optObjs = $("allOutputType").descendants();
	for(i = 0; i < optObjs.length; i++) {
		var bizType = optObjs[i].readAttribute("bizType");
		if(bizType == selectedBizType) {
			var opt = document.createElement("option");
			opt.text = optObjs[i].text;
			opt.value = optObjs[i].value;
			outputType.add(opt);
		}
	}

}

function deleteRowProcessTemplateAttach() {
	var deleteOverAllIds = "";
	var chkBoxEls = $$('input[name="processTemplateDetailCheck"]');
	for ( var i=0;i<chkBoxEls.length;i++){ 
		if ( chkBoxEls[i].checked ) {
			//해당 데이터 삭제 로직
			var attachSeq = $(chkBoxEls[i]).up('td').nextSiblings()[0].down().value;
			if(attachSeq != null && attachSeq != ""){
				var ActionURL = "/action/ProcessTemplateAction.do?mode=deleteProcessTemplateAttachEntity";
				var status = AjaxRequest.post (
						{	'url': ActionURL,
							'parameters' : { "processTemplateCode": <c:out value="${processTemplateDetail.processTemplateCode}"/>, "workSeq" : <c:out value="${processTemplateDetail.workSeq}" />,"attachSeq" : attachSeq },
							'onSuccess':function(obj){
							},
							'onLoading':function(obj){},
							'onError':function(obj){
								alert("삭제할 수 없습니다.");
							}
						}
				); status = null;	
			}
			$(chkBoxEls[i]).up().up().remove();
		}
	}
}

function storeProcessTemplateAttach(){
	var ActionURL = "/action/ProcessTemplateAction.do?mode=storeProcessTemplateAttach";
	var sFrm = document.forms["processTemplateAttachForm"];

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert('저장하였습니다.');
					window.parent.Windows.close("modal_window");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;		
}

function deleteProcessTemplateAttach() {
	var ActionURL = "/action/ProcessTemplateAction.do?mode=deleteProcessTemplateAttach";
	var sFrm = document.forms["processTemplateAttachForm"];

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert('삭제하였습니다.');
					window.parent.Windows.close("modal_window");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("삭제할 수 없습니다.");
				}
			}
	); status = null;		
}

function goProcessTemplateDetailList() {
	window.parent.Windows.close("modal_window");
}
</script>
</head>

<body style="width: 620px">

	<form name="processTemplateAttachForm" method="post">
		<div style="display: none;">
			<input type="text" name="processTemplateCode" value="<c:out value="${processTemplateDetail.processTemplateCode}"/>"/>
			<input type="text" name="processTemplateName" value="<c:out value="${processTemplateDetail.processTemplateName}"/>"/>
			<input type="text" name="workSeq" value="<c:out value="${processTemplateDetail.workSeq}"/>"/>
		</div>
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<h4 class="subTitle"><c:out value="${processTemplateDetail.processTemplateName}"/>: <c:out value="${processTemplateDetail.activityName}" /></h4>
				</td>
			</tr>
		<!-- sub 타이틀 영역 종료-->

		<!-- 본문 영역 시작-->
			<tr>
				<td>
					<table id="processTemplateAttachTable" class="tableStyle05">
						<colgroup> 
							<col width="45px">
							<col width="45px">
							<col width="110px">
							<col width="130px">
							<col align="left" width="*">
							<col width="50px">
						</colgroup>
						<thead id="processTemplateAttachTableHeader">
							<tr> 
								<td>선택</td>
								<td>순번</td>
								<td>BizType</td>
								<td>OutputType</td>
								<td>산출물명</td>
								<td>필수</td>
							</tr> 
						</thead>
						<tbody id="processTemplateAttachTableBody">
							<c:if test="${!empty processTemplateAttachList}">
								<c:forEach var="rs" items="${processTemplateAttachList}">
									<tr>
										<td><input name="processTemplateCheck" type="checkbox" /></td>
										<td><input name="attachSeq" type="text" value="<c:out value="${rs.attachSeq}"/>" class="textInput_left" style="width: 100%;"></td>
										<td><select name="bizType" class='selectbox' style="width: 100%;" onchange='changeOutputType(this)'>
											<c:forEach var="item" items="${bizTypeCodeList}" >
												<option value="<c:out value="${item.key1}"/>" <c:if test="${item.key1 == rs.bizType}">selected</c:if>><c:out value="${item.data1}"/></option>
											</c:forEach>
										</select></td>
										<td><select name="outputType" class='selectbox' style="width: 100%;">
											<c:forEach var="item" items="${attachTypeCodeList}" >
												<option bizType="<c:out value="${item.key2}"/>" value="<c:out value="${item.key1}"/>" <c:if test="${item.key2 == rs.bizType && item.key1 == rs.outputType}">selected</c:if>><c:out value="${item.data1}"/></option>
											</c:forEach>
										</select></td>
										<td><input name="outputName" type="text" value="<c:out value="${rs.outputName}"/>" class="textInput_left" style="width: 100%;"></td>
										<td><select name="necessary" class='selectbox' style='width: 100%;'><option value="true" <c:if test="${rs.necessary}">selected</c:if>>Y</option><option value="false" <c:if test="${!file.necessary}">selected</c:if>>N</option></select></td>
									</tr> 
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td height="36">
					<div class="btNbox txtR">
						<a title="행추가" class="btN3fac0c txt2btn" href="#" onclick="javascript:addRowProcessTemplateAttach()">행 추가</a>
						<a title="행삭제" class="btNe14f42 txt2btn" href="#" onclick="javascript:deleteRowProcessTemplateAttach()">행 삭제</a>
					</div>
				</td>
			</tr>			
			<!-- 본문 영역 종료-->
			
			
			<!-- 버튼부분-->
			<tr>
				<td height='7'></td>
			</tr>
			<tr>
				<td>
					<table width="100%" height="36" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="btNbox txtR">
									<a title="저장" class="btNe006bc6 txt2btn" href="#" onclick="storeProcessTemplateAttach()">저장</a>
									<a title="삭제" class="btNe14f42 txt2btn" href="#" onclick="deleteProcessTemplateAttach()">삭제</a>
									<a title="취소" class="btNa0a0a0 txt2btn" href="#" onclick="goProcessTemplateDetailList()">닫기</a>
								</div>
							<td>
						</tr>
					</table>
				</td>
			</tr>
		<!-- 버튼종료-->
		</table>						
	</form>					
	<div style="display: none;">
		<select name="allOutputType" id="allOutputType">
			<c:forEach var="item" items="${attachTypeCodeList}" >
				<option bizType="<c:out value="${item.key2}"/>" value="<c:out value="${item.data1}"/>"><c:out value="${item.data1}"/></option>
			</c:forEach>
		</select>
	</div>
</body>

</html>					