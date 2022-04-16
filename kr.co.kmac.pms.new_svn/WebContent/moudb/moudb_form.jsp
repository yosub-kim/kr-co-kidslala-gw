<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src='<c:url value="/js/expertPool_Form2.js"/>'></script>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function doSave() {
	var ActionURL = "/action/MoudbAction.do";	
	ActionURL += "?mode=saveMoudb";
	var sFrm = document.forms["frm"];
	

	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					document.location = "/action/MoudbAction.do?mode=selectList";
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;
}

function doChange(chkBox,objstr) {
	var obj = document.getElementById(objstr);
	if(chkBox.checked)
		obj.disabled = false;
	else
		obj.disabled = true;
}

/* function appendContact() {
	var pObj = document.getElementById("tbodyContactList");
	var trHtml = "";
	
	trHtml += "<tr>\n"; 
	trHtml += "	<td><input type=\"checkbox\"	name=\"chkContact\" id=\"contactName\" class=\"btn_check\" ><label for=\"contactName\" /></td>\n";
	trHtml += "	<td><input type=\"text\"		name=\"contactName\" 	class=\"contentInput_left\" value=\"\"></td>\n";
	trHtml += "	<td><input type=\"text\"		name=\"contactGrade\" 	class=\"contentInput_left\" value=\"\"></td>\n";
	trHtml += "	<td><input type=\"text\"		name=\"contactTel\"		class=\"contentInput_left\" value=\"\"></td>\n";
	trHtml += "	<td><input type=\"text\"		name=\"contactMobile\"	class=\"contentInput_left\" value=\"\"></td>\n";
	trHtml += "	<td><input type=\"text\"		name=\"contactEmail\"	class=\"contentInput_left\" value=\"\"></td>\n";
	trHtml += "	<td><input type=\"text\"		name=\"contactJob\"		class=\"contentInput_left\" value=\"\"></td>\n";
	trHtml += "</tr>";

	
	var rowData = HTMLtoDOM(trHtml);
	pObj.appendChild(rowData);
} */

function appendContact() {
	var my_tbody = document.getElementById('tbodyContactList');
	var row = my_tbody.insertRow(my_tbody.rows.length);
	var cell1 = row.insertCell(0)
	var cell2 = row.insertCell(1)
	var cell3 = row.insertCell(2)
	var cell4 = row.insertCell(3)
	var cell5 = row.insertCell(4)
	var cell6 = row.insertCell(5)
	
	/* cell1.innerHTML = '<tr>\n'; */
	cell1.innerHTML = '<td><input type=\"text\"		name=\"contactName\" 	class=\"contentInput_left\" value=\"\"></td>\n';
	cell2.innerHTML = '<td><input type=\"text\"		name=\"contactGrade\" 	class=\"contentInput_left\" value=\"\"></td>\n';
	cell3.innerHTML = '<td><input type=\"text\"		name=\"contactTel\"		class=\"contentInput_left\" value=\"\"></td>\n';
	cell4.innerHTML = '<td><input type=\"text\"		name=\"contactMobile\"	class=\"contentInput_left\" value=\"\"></td>\n';
	cell5.innerHTML = '<td><input type=\"text\"		name=\"contactEmail\"	class=\"contentInput_left\" value=\"\"></td>\n';
	cell6.innerHTML = '<td><input type=\"text\"		name=\"contactJob\"		class=\"contentInput_left\" value=\"\"></td>\n';
	/* cell8.innerHTML = '</tr>'; */
}

function deleteContact() {
	var my_tbody = document.getElementById('tbodyContactList');
	if(my_tbody.rows.length < 1) return;
	my_tbody.deleteRow(my_tbody.rows.length - 1);
}

/* function deleteContact() {
	var chkBox = document.getElementsByName("chkContact");
	for(var i = chkBox.length -1 ; i >= 0 ; i--){
		if(chkBox[i].checked){
			var childObj = chkBox[i].parentNode.parentNode;
			childObj.parentNode.removeChild(childObj);
		}
	}
} */
</script>
</head>
<body>
	<form name="frm">

		<!-- location -->
		<div class="location">
			<p class="menu_title">MOU 등록</p>
			<ul>
				<li class="home">HOME</li>
				<li>네트워크</li>
				<li>Infra 관리</li>
				<li>MOU 리스트</li>
			</ul>
		</div>
		<!-- // location -->
		<div style="display: none">
			<input type="hidden" name="idx" value="<c:out value="${moudbDetail.idx}"/>">
		</div>
		<div class="contents sub">
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">기관 기본 정보</p>
					</div>
					<div class="select_box">
						<button type="button" class="btn line btn_blue" onclick="doSave()"><i class="mdi mdi-clipboard-check-outline"></i>저장</button>
					</div>
				</div>
				<div class="board_contents">
					<table id="projectProgressTable" class="tbl-edit td-c">
						<!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 15%" />
							<col style="width: 35%" />
							<col style="width: 15%" />
							<col style="width: 35%" />
						</colgroup>
						<tr>
							<th>기관명</th>
							<td><input type="text" name="com_name" value="<c:out value="${moudbDetail.com_name}"/>"></td>
							<th>대표자</th>
							<td><input type="text" name="com_boss" value="<c:out value="${moudbDetail.com_boss}"/>"></td>
						</tr>
						<tr>
							<th>국가(지역)</th>
							<td><input type="text" name="country" value="<c:out value="${moudbDetail.country}"/>"></td>
							<th>주소</th>
							<td><input type="text" name="com_addr" value="<c:out value="${moudbDetail.com_addr}"/>"></td>
						</tr>
						<tr>
							<th>홈페이지</th>
							<td><input type="text" name="homepage" value="<c:out value="${moudbDetail.homepage}"/>"></td>
							<th>대표전화</th>
							<td><input type="text" name="com_tel" value="<c:out value="${moudbDetail.com_tel}"/>"></td>
						</tr>
						<tr>
							<th>기관특성</th>
							<td><input type="text" name="com_special_quality" value="<c:out value="${moudbDetail.com_special_quality}"/>"></td>
							<th>전문분야</th>
							<td><input type="text" name="com_expert" value="<c:out value="${moudbDetail.com_expert}"/>"></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">KMAC 협력 정보</p>
					</div>
				</div>
				<div class="board_contents">
					<table id="projectProgressTable" class="tbl-edit td-c">
						<!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 15%" />
							<col style="width: 50%" />
							<col style="width: 15%" />
							<col style="width: 20%" />
						</colgroup>
						<tr>
							<th>협력분야</th>
							<td><input type="text" name="com_cooperation"
								value="<c:out value="${moudbDetail.com_cooperation}"/>"></td>
							<th>관계수준</th>
							<td>
								<ul class="chek_ui">
									<li><input type="radio" name="relation_level" id="1" value="상" class="btn_radio" <c:if test="${moudbDetail.relation_level == '상'}">checked</c:if>>상<label for="1"></label></li>
					              	<li><input type="radio" name="relation_level" id="2" value="중" class="btn_radio" <c:if test="${moudbDetail.relation_level == '중'}">checked</c:if>>중<label for="2"></label></li>
					              	<li><input type="radio" name="relation_level" id="3" value="하" class="btn_radio" <c:if test="${moudbDetail.relation_level == '하'}">checked</c:if>>하<label for="3"></label></li>
								</ul>
							</td>
						</tr>
						<tr>
							<th>공동사업실적</th>
							<td colspan="3"><textarea name="join_project_result" class="textArea" style="width: 97%;" rows="3"><c:out value="${moudbDetail.join_project_result}" /></textarea></td>
					</table>
				</div>
			</div>

			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">담당자 추가</p>
					</div>
					<div class="select_box">
						<button type="button" class="btn line btn_blue" onclick="appendContact()"><i class="mdi mdi-arrow-up-thin-circle-outline"></i>행 추가</button>
						<button type="button" class="btn line btn_pink" onclick="deleteContact()"><i class="mdi mdi-arrow-down-thin-circle-outline"></i>행 삭제</button>
					</div>
				</div>
				<div class="board_contents">
					<table id="projectProgressTable" class="tbl-edit td-c">
						<!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 10%" />
							<col style="width: 10%" />
							<col style="width: 20%" />
							<col style="width: 20%" />
							<col style="width: 20%" />
							<col style="width: 15%" />
						</colgroup>
						<tr>
							<th>이름</th>
							<th>직책</th>
							<th>전화번호</th>
							<th>휴대폰</th>
							<th>메일</th>
							<th>담당분야</th>
						</tr>
						<tbody id="tbodyContactList">
							<c:forEach var="contact" items="${moudbDetail.contactList}" varStatus="i">
								<tr>
									<td><input type="text" name="contactName" 	class="contentInput_left" 	value="<c:out value="${moudbDetailContactPoint.contactName}"/>"></td>
									<td><input type="text" name="contactGrade" 	class="contentInput_left" 	value="<c:out value="${moudbDetailContactPoint.contactGrade}"/>"></td>
									<td><input type="text" name="contactTel" 	class="contentInput_left" 	value="<c:out value="${moudbDetailContactPoint.contactTel}"/>"></td>
									<td><input type="text" name="contactMobile" class="contentInput_left" 	value="<c:out value="${moudbDetailContactPoint.contactMobile}"/>"></td>
									<td><input type="text" name="contactEmail" 	class="contentInput_left" 	value="<c:out value="${moudbDetailContactPoint.contactEmail}"/>"></td>
									<td><input type="text" name="contactJob" 	class="contentInput_left" 	value="<c:out value="${moudbDetailContactPoint.contactJob}"/>"></td>
								</tr>
							</c:forEach>
						</tbody>
						<%-- <c:if test="${empty moudbDetail.contactList}">
							<tr><td style="text-align: center;" colspan="7">연락 담당자가 없습니다.</td></tr>
						</c:if> --%>
					</table>
				</div>
			</div>

			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">MOU 파일 추가</p>
					</div>
				</div>
				<div class="board_contents">
					<table id="projectProgressTable" class="tbl-edit td-c">
						<!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 15%" />
							<col style="width: *" />
						</colgroup>
						<tr>
							<th>첨부파일</th>
							<td><jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log" /></jsp:include></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>