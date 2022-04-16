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
function addRowEval() {
	var tableObj = $('evalTable');

	var tdCount = tableObj.down('tr').down('td').nextSiblings().length + 1 ;
    var newRow=tableObj.insertRow(-1);
	var newCellArr = new Array();
	for ( var i=0;i<tdCount;i++ ){
		newCellArr[i] = newRow.insertCell(-1);
	}
	var tableCount = tableObj.rows.length;
	new Effect.Highlight(newCellArr[0], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[1], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[2], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[3], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[4], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[5], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	
	newCellArr[0].innerHTML = '<input type="checkbox" name="evalCheck" class="btn_check"  id="'+tableCount+'"><label for="'+tableCount+'"></label><input id="customerCode" type="hidden" name="customerCode">';
	newCellArr[1].innerHTML = '<input type="text" name="customerName"  style="width: 100%" >';
	newCellArr[2].innerHTML = '<input type="text" name="customerWorkPName" style="width: 100%" >';
	newCellArr[3].innerHTML = '<input type="text" name="customerWorkPEmail" style="width: 100%" >';
	newCellArr[4].innerHTML = '<input type="text" name="customerWorkPPhone" style="width: 100%" >';
	newCellArr[5].innerHTML = '<button type="button" class="btn line btn_black" onclick="openDMListPopUp(this);"><i class="mdi mdi-account-search"></i>검색</button>';
	
	newCellArr[0].className = "BGC";
	newCellArr[1].className = "BGC";
	newCellArr[2].className = "BGC";
	newCellArr[3].className = "BGC";
	newCellArr[4].className = "BGC";
	newCellArr[5].className = "BGC";
}
var selectedElmtCnt;
function openDMListPopUp(selectedElmt) {
	selectedElmtCnt = $(selectedElmt).up().up().rowIndex -1;
	dmlist_Open('setCustomerWork');	
}
function setCustomerWork(dm) {
	var customerCodeList = $$('input[name="customerCode"]');
	var customerNameList = $$('input[name="customerName"]');
	var customerWorkPNameList = $$('input[name="customerWorkPName"]');
	var customerWorkPEmailList = $$('input[name="customerWorkPEmail"]');
	var customerWorkPPhoneList = $$('input[name="customerWorkPPhone"]');
	
	customerCodeList[selectedElmtCnt].value = dm.idx;
	customerNameList[selectedElmtCnt].value = dm.company;
	customerWorkPNameList[selectedElmtCnt].value = dm.name;
	customerWorkPEmailList[selectedElmtCnt].value = dm.email;
	customerWorkPPhoneList[selectedElmtCnt].value = dm.tel; 
}
function deleteRowEval() {
	var tableObj = $('evalTable');
	var deleteOverAllIds = "";
	var chkBoxEls = $$('input[name="evalCheck"]');
	for ( var i=0;i<chkBoxEls.length;i++){ 
		if ( chkBoxEls[i].checked ) {
			$(chkBoxEls[i]).up().up().remove();
		}
	}
}

function saveEvalInfo() {
	var customerNameList = $$('input[name="customerName"]');
	var customerWorkPNameList = $$('input[name="customerWorkPName"]');
	var customerWorkPEmailList = $$('input[name="customerWorkPEmail"]');
	for(var i=0; customerNameList.length>i; i++){if(customerNameList[i].value == ''){alert(i+1+" 번째 고객사명을 입력하세요");return;}}
	for(var i=0; customerWorkPNameList>i; i++){if(customerWorkPNameList[i].value == ''){alert(i+1+" 번째 업무담당자명을 입력하세요");return;}}
	for(var i=0; customerWorkPEmailList>i; i++){if(customerWorkPEmailList[i].value == ''){alert(i+1+" 번째 E-mail 주소를 입력하세요");return;}}
	
	var ActionURL = "/action/ProjectETCInfoAction.do?mode=insertProjectCsrInfoList";
	var sFrm = document.forms["projectEvalForm"];
	
	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					if($('csrInfoFile').value != ""){
						document.projectEvalForm.target = "";
						document.projectEvalForm.action = "/action/ProjectETCInfoAction.do?mode=uploadProjectCsrInfoList";
						document.projectEvalForm.submit();
					} else{
						alert('저장하였습니다.');
					}
					//window.parent.Windows.close("modal_window");
			},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;
}

function closeEvalInfo() {
	window.parent.Windows.close("modal_window");
}

/*popup layout*/
window.onload=function(){
	layer_open(this, 'pop_register');
}

</script> 
</head>

<body style="width:680px">
	<form name="projectEvalForm" method="post"  enctype="multipart/form-data">	
		<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>"/>
		
		<div id="pop_register" class="popup_layer pop_register">
		<!-- <div class="popup_bg"></div> -->
			<div class="popup_inner tbl-sc" style="width:1000px; ">
				<div class="a-both">
					<p class="h1">고객사 추가</p>
				</div>
				
				<div class="board_box">
					<div class="title_both">
						<div class="h1_area">
							<p class="term">추가 등록</p>
						</div>
						<div class="btn_area"> 
							<button type="button" class="btn line btn_blue" onclick="saveEvalInfo();"><i class="mdi mdi-square-edit-outline"></i>저장</button>
							<button type="button" class="btn line btn_pink" onclick="window.close()"><i class="mdi mdi-trash-can-outline"></i>닫기</button>
						</div>
					</div>
					<div class="board_contents">
						<table id="evalTable" class="tbl-edit td-c pd-none"><!-- td 영역이 가운데 정렬 일 경우 td-c --> 
							<colgroup>
								<col style="width: 10%">
								<col style="width: *">
								<col style="width: 15%">
								<col style="width: 15%">
								<col style="width: 15%">
								<col style="width: 15%">
							</colgroup>
							<tbody id="projectRateTableBody">
								<tr>
									<td>선택</td>
									<td>고객사</td>
									<td>담당자</td>
									<td>이메일</td>
									<td>전화번호</td>
									<td>찾기</td>
								</tr>
								<c:if test="${!empty list }">
									<c:forEach var="item" items="${list}"> 
										<tr>
											<td><input id="eval" type="checkbox" name="evalCheck"><input id="customerCode" type="hidden" name="customerCode"  value="<c:out value="${item.customerCode}"/>"></td>
											<td><input type="text" name="customerName" value="<c:out value="${item.customerName}"/>"></td>
											<td><input type="text" name="customerWorkPName" value="<c:out value="${item.customerWorkPName}"/>"></td>
											<td><input type="text" name="customerWorkPEmail" value="<c:out value="${item.customerWorkPEmail}"/>"></td>
											<td><input type="text" name="customerWorkPPhone" value="<c:out value="${item.customerWorkPPhone}"/>"></td>
											<td><a href="#" onclick="openDMListPopUp	(this);"><img alt="편집" src="/images/btn_glass.jpg" ></a></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody> 
						</table>
					</div>
					<div class="btn_area">
						<button type="button" class="btn btn_aqua" onclick="addRowEval()">행추가</button>
						<button type="button" class="btn btn_purple" onclick="deleteRowEval()">행삭제</button>
					</div>
					</br>
				</div>
				
				<div class="board_box">
					<div class="title_both">
						<div class="h1_area">
							<p class="term">엑셀 등록</p>
						</div>
						<div class="btn_area"> 
							<button type="button" class="btn line btn_excel" onclick="location.href='/project/endProcess/customerUpload.xls'"><i class="mdi mdi-microsoft-excel"></i>등록 양식</button>
						</div>
					</div>
					<div class="board_contents">
						<div class="file_upload">
							<input type="text" id="file-name-csrInfoFile" readonly="readonly" />
							<input class="file" type="file"  name="csrInfo" id="csrInfoFile" style="display: none"/>
							<label class="btn line btn_black btn_upload" for="csrInfoFile"><i class="mdi mdi-upload"></i>파일찾기</label>
							<!-- <input class="file" type="file" name="csrInfo" id="csrInfoFile" > -->
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</form>
</body>

</html>					