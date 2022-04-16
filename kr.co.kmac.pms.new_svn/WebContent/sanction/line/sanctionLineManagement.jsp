<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<!-- 서브페이지에서만 사용 -->
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

var levelInfo;
function openExpertPoolPopUp(_levelInfo) {
	levelInfo = _levelInfo;
	orgFinder_Open('setSanctionLineInfo');	
}

function setSanctionLineInfo(expertPoolList){
	if(expertPoolList.length > 0) {
		var n=0;
		var snactionNameList = $$('input[sanctionInfo="name"]');
		var snactionSsnList = $$('input[sanctionInfo="ssn"]');
		var snactionDeptList = $$('input[sanctionInfo="dept"]');
		var deptnameList = $$('span[name="DeptName"]');
		for (var i=0; i < expertPoolList.length; i++){
			snactionNameList[levelInfo-1].value = expertPoolList[i].name;
			snactionSsnList[levelInfo-1].value =  expertPoolList[i].ssn;
			snactionDeptList[levelInfo-1].value =  expertPoolList[i].dept;
			deptnameList[levelInfo-1].innerText = expertPoolList[i].deptName;
			levelInfo++;
		}
	}
}

function save_line(){
	var sFrm = document.SanctionLineForm;
	var ActionURL = "/action/SanctionLineAction.do";
	
	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					alert("저장되었습니다.");
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;	
}

function reset_Field(_levelInfo)
{
	levelInfo = _levelInfo;
	var snactionNameList = $$('input[sanctionInfo="name"]');
	var snactionSsnList = $$('input[sanctionInfo="ssn"]');
	var snactionDeptList = $$('input[sanctionInfo="dept"]');
	var deptnameList = $$('span[name="DeptName"]');

	snactionNameList[levelInfo-1].value = "";
	snactionSsnList[levelInfo-1].value =  "";
	snactionDeptList[levelInfo-1].value =  "";
	deptnameList[levelInfo-1].innerText = "";
}
</script>
</head>
<body>
<%-- 작업영역 --%>
<div class="board_box">
	<div class="title_both">
		<div class="h1_area">
			<p class="h1">결재선 관리</p>
		</div>
		<div class="select_box">
			<button type="button" class="btn line btn_blue" onclick="save_line()"><i class="mdi mdi-square-edit-outline"></i>저장</button>
		</div>
	</div>
	<div class="board_contents">
		<table class="tbl-edit td-c">
		<form name="SanctionLineForm" method="post" action="SanctionLineAction.do">
			<colgroup>
				<col style="width:15%"/>
				<col style="width:*"/>
				<col style="width:15%"/>
				<col style="width:15%"/>
				<col style="width:15%"/>
			</colgroup>
			<tr>
				<th>구분</th>
				<th>소속</th>
				<th>성명</th>
				<th>찾기</th>
				<th>삭제</th>
			</tr>
			<tr>
				<td>기안</td>
				<td><span name="DeptName"><org:group id="${sanctionLine.registerDept}"/></span></td>
				<td><input type="text" style="width: 100%;" sanctionInfo="name" seq="1" class="contentInput_center" readonly="readonly"  name="registerName" id="registerName" value="<c:out value="${sanctionLine.registerName}"/>"></input></td>
				<td>
					<button type="button" class="btn line btn_aqua" onclick="openExpertPoolPopUp('1');"><i class="mdi mdi-check-decagram"></i>찾기</button>
				</td>
				<td>
					<!-- 
					<img alt="지우기" src="/images/frame/glo_out.gif" border="0" style="cursor: hand;" onclick="reset_Field('1');">
					-->
				</td>
			</tr>
			<tr>
				<td>검토</td>
				<td><span name="DeptName"><org:group id="${sanctionLine.teamManagerDept}"/></span></td>
				<td><input type="text" style="width: 100%;" style="width: 78px;" sanctionInfo="name" seq="2" class="contentInput_center" readonly="readonly" name="teamManagerName" id="teamManagerName" value="<c:out value="${sanctionLine.teamManagerName}"/>"></td>
				<td>
					<button type="button" class="btn line btn_aqua" onclick="openExpertPoolPopUp('2');"><i class="mdi mdi-check-decagram"></i>찾기</button>
				</td>
				<td>
					<button type="button"  class="btn line btn_pink" onclick="reset_Field('2');"><i class="mdi mdi-trash-can-outline"></i>삭제</button>
				</td>
			</tr>
			<tr>
				<td>확인</td>
				<td><span name="DeptName"><org:group id="${sanctionLine.cfoDept}"/></span></td>
				<td><input type="text" style="width: 100%;" sanctionInfo="name" seq="3" class="contentInput_center" readonly="readonly" name="cfoName" id="cfoName" value="<c:out value="${sanctionLine.cfoName}"/>"></td>	
				<td>
					<button type="button" class="btn line btn_aqua" onclick="openExpertPoolPopUp('3');"><i class="mdi mdi-check-decagram"></i>찾기</button>
				</td>
				<td>
					<button type="button"  class="btn line btn_pink" onclick="reset_Field('3');"><i class="mdi mdi-trash-can-outline"></i>삭제</button>	
				</td>				
			</tr>
			<tr>
				<td>승인</td>
				<td><span name="DeptName"><org:group id="${sanctionLine.cioDept}"/></span></td>
				<td><input type="text" style="width: 100%;" sanctionInfo="name" seq="4" class="contentInput_center" readonly="readonly" name="cioName" id="cioName" value="<c:out value="${sanctionLine.cioName}"/>"></td>	
				<td>
					<button type="button" class="btn line btn_aqua" onclick="openExpertPoolPopUp('4');"><i class="mdi mdi-check-decagram"></i>찾기</button>
				</td>
				<td>
					<button type="button"  class="btn line btn_pink" onclick="reset_Field('4');"><i class="mdi mdi-trash-can-outline"></i>삭제</button>
				</td>				
			</tr>
			<tr>
				<td>협의</td>
				<td><span name="DeptName"><org:group id="${sanctionLine.assistant1Dept}"/></span></td>
				<td><input type="text" style="width: 100%;" sanctionInfo="name" seq="5" class="contentInput_center" readonly="readonly" name="assistant1Name" id="assistant1Name" value="<c:out value="${sanctionLine.assistant1Name}"/>"></td>
				<td>
					<button type="button" class="btn line btn_aqua" onclick="openExpertPoolPopUp('5');"><i class="mdi mdi-check-decagram"></i>찾기</button>
				</td>
				<td>
					<button type="button"  class="btn line btn_pink" onclick="reset_Field('5');"><i class="mdi mdi-trash-can-outline"></i>삭제</button>
				</td>
			</tr>
			<%-- <tr>
				<td >협의2</td>
				<td><span name="DeptName"><org:group id="${sanctionLine.assistant2Dept}"/></span></td>
				<td><input type="text" style="width: 78px;" sanctionInfo="name" seq="5" class="contentInput_center" readonly="readonly" name="assistant2Name" id="assistant2Name" value="<c:out value="${sanctionLine.assistant2Name}"/>"></td>
				<td>
					<a href="#" onclick="openExpertPoolPopUp('5')">
						<img alt="협의자 검색" src="/images/btn_glass.jpg" border="0" style="cursor: hand;">&nbsp;
					</a>
				</td>
				<td>
					<img alt="리셋" src="/images/frame/glo_out.gif" border="0" style="cursor: hand;" onclick="reset_Field('5');">
				</td>
			</tr> --%>
			<tr>
				<td>대표이사</td>
				<td><span name="DeptName"><org:group id="${sanctionLine.ceoDept}"/></span></td>
				<td><input type="text" style="width: 100%;" sanctionInfo="name" seq="6" class="contentInput_center" readonly="readonly" name="ceoName" id="ceoName" value="<c:out value="${sanctionLine.ceoName}"/>"></td>
				<td>
					<button type="button" class="btn line btn_aqua" onclick="openExpertPoolPopUp('6');"><i class="mdi mdi-check-decagram"></i>찾기</button>
				</td>
				<td>
					<button type="button"  class="btn line btn_pink" onclick="reset_Field('6');"><i class="mdi mdi-trash-can-outline"></i>삭제</button>
				</td>
			</tr>
		<div style="display: none;">
			<input type="hidden" name="mode" id="mode" value="storeSanctionList">
			<input type="hidden" sanctionInfo="ssn" seq="1" name="registerSsn" id="registerSsn" value="<c:out value="${sanctionLine.registerSsn}"/>">
			<input type="hidden" sanctionInfo="dept" seq="1" name="registerDept" id="registerDept" value="<c:out value="${sanctionLine.registerDept}"/>">
			<input type="hidden" sanctionInfo="ssn" seq="2" name="teamManagerSsn" id="teamManagerSsn" value="<c:out value="${sanctionLine.teamManagerSsn}"/>">
			<input type="hidden" sanctionInfo="dept" seq="2" name="teamManagerDept" id="teamManagerDept" value="<c:out value="${sanctionLine.teamManagerDept}"/>">
			<input type="hidden" sanctionInfo="ssn" seq="3" name="cfoSsn" id="cfoSsn" value="<c:out value="${sanctionLine.cfoSsn}"/>">
			<input type="hidden" sanctionInfo="dept" seq="3" name="cfoDept" id="cfoDept" value="<c:out value="${sanctionLine.cfoDept}"/>">
			<input type="hidden" sanctionInfo="ssn" seq="4" name="cioSsn" id="cioSsn" value="<c:out value="${sanctionLine.cioSsn}"/>">
			<input type="hidden" sanctionInfo="dept" seq="4" name="cioDept" id="cioDept" value="<c:out value="${sanctionLine.cioDept}"/>">
			<input type="hidden" sanctionInfo="ssn" seq="5" name="assistant1Ssn" id="assistant1Ssn" value="<c:out value="${sanctionLine.assistant1Ssn}"/>">
			<input type="hidden" sanctionInfo="dept" seq="5" name="assistant1Dept" id="assistant1Dept" value="<c:out value="${sanctionLine.assistant1Dept}"/>">		
			<%-- <input type="hidden" sanctionInfo="ssn" seq="5" name="assistant2Ssn" id="assistant2Ssn" value="<c:out value="${sanctionLine.assistant2Ssn}"/>">
			<input type="hidden" sanctionInfo="dept" seq="5" name="assistant2Dept" id="assistant2Dept" value="<c:out value="${sanctionLine.assistant2Dept}"/>"> --%>
			<input type="hidden" sanctionInfo="ssn" seq="6" name="ceoSsn" id="ceoSsn" value="<c:out value="${sanctionLine.ceoSsn}"/>">
			<input type="hidden" sanctionInfo="dept" seq="6" name="ceoDept" id="ceoDept" value="<c:out value="${sanctionLine.ceoDept}"/>">			
		</div>
		</form>
		</table> 
	</div>
</div>
</body>
</html>