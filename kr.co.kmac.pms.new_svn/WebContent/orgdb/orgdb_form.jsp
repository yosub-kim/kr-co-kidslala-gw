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
	var ActionURL = "/action/OrgdbAction.do";	
	ActionURL += "?mode=saveOrgdb";
	var sFrm = document.forms["frm"];
	

	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					document.location = "/action/OrgdbAction.do?mode=selectList";
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

function appendContact() {
	var pObj = document.getElementById("tbodyContactList");
	var trHtml = "";
	trHtml += "<tr>\n"; 
	trHtml += "	<td><input type=\"checkbox\"	name=\"chkContact\" id=\"contactName\" class=\"btn_check\" ><label for=\"contactName\" /></td>\n";
	trHtml += "	<td><input type=\"text\"		name=\"contactName\"		class=\"contentInput_left\" value=\"\"></td>\n";
	trHtml += "	<td><input type=\"text\"		name=\"contactPosition\"	class=\"contentInput_left\" value=\"\"></td>\n";
	trHtml += "	<td><input type=\"text\"		name=\"contactTel\"			class=\"contentInput_left\" value=\"\"></td>\n";
	trHtml += "	<td><input type=\"text\"		name=\"contactEmail\"		class=\"contentInput_left\" value=\"\"></td>\n";
	trHtml += "	<td><input type=\"text\"		name=\"contactWork\"		class=\"contentInput_left\" value=\"\"></td>\n";
	trHtml += "	<input type=\"hidden\"		name=\"contactMobile\"		class=\"contentInput_left\" value=\"\">\n";
	trHtml += "</tr>";

	
	var rowData = HTMLtoDOM(trHtml);
	pObj.appendChild(rowData);
}

function deleteContact() {
	var chkBox = document.getElementsByName("chkContact");
	for(var i = chkBox.length -1 ; i >= 0 ; i--){
		if(chkBox[i].checked){
			var childObj = chkBox[i].parentNode.parentNode;
			childObj.parentNode.removeChild(childObj);
		}
	}
}

function callBackByZipWin(zipcode, address){
		
	frm.zipCode.value = zipcode; //.replaceAll("-","");
	frm.address1.value = address;
	frm.address2.focus();
}
function setWorkType() {
	if(document.frm.relWithkmac1.value == "기타"){
		document.getElementById("specialField").style.display = "";
	}else{
		document.getElementById("specialField").style.display ="none";
	}
}
</script>
</head>
<body>
<%-- 작업영역 --%>
<form name="frm" method="post">
<span id="guide" style="color:#FFFFFF"></span>
	<!-- location -->
		<div class="location">
			<p class="menu_title">협력사 등록 관리</p>
			<ul>
				<li class="home">HOME</li>
				<li>네트워크</li>
				<li>Infra 관리</li>
				<li>협력사 등록 관리</li>
			</ul>
		</div>
	<!-- // location -->

	<div sytle="display:none">
		<input type="hidden" name="orgCode" value="<c:out value="${orgdbDetail.orgCode}"/>">
	</div>
	
	<div class="contents sub">
			<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">협력사 정보</p>
				</div>
				<div class="select_box">
					<button type="button" class="btn line btn_blue" onclick="doSave()"><i class="mdi mdi-clipboard-check-outline"></i>저장</button>
				</div>
			</div>
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 15%"/>
								<col style="width: 22%"/>
								<col style="width: 15%" />
								<col style="width: 21%" />
								<col style="width: 15%" />
								<col style="width: 22%" />
							</colgroup>
							<tr>
								<th>회사명(사업자등록증)</th>
								<td><input type="text" name="orgName" value="<c:out value="${orgdbDetail.orgName}"/>"></td>
								<th>대표자명</th>
								<td><input type="text" name="repName" value="<c:out value="${orgdbDetail.repName}"/>"></td>
								<th>전문분야</th>
								<td>
								<input type="hidden" name="specialField2" value="" />
								<input type="hidden" name="specialField3" value="" />
								<input type="hidden" name="specialField4" value="" />
								<input type="hidden" name="relWithkmac2" value="" />
								<input type="hidden" name="relWithkmac3" value="" />
								<input type="hidden" name="relWithkmac4" value="" />
								<select name="relWithkmac1" class="selectboxPopup" onchange="setWorkType();">
								<option value="컨설팅" <c:if test="${'컨설팅'==orgdbDetail.relWithkmac1}">selected</c:if>>컨설팅</option>
								<option value="리서치" <c:if test="${'리서치'==orgdbDetail.relWithkmac1}">selected</c:if>>리서치</option>
								<option value="교육/연수" <c:if test="${'교육/연수'==orgdbDetail.relWithkmac1}">selected</c:if>>교육/연수</option>
								<option value="행사지원" <c:if test="${'행사지원'==orgdbDetail.relWithkmac1}">selected</c:if>>행사지원</option>
								<option value="인쇄/디자인/제작" <c:if test="${'인쇄/디자인/제작'==orgdbDetail.relWithkmac1}">selected</c:if>>인쇄/디자인/제작</option>
								<option value="광고/홍보" <c:if test="${'광고/홍보'==orgdbDetail.relWithkmac1}">selected</c:if>>광고/홍보</option>
								<option value="영상/촬영" <c:if test="${'영상/촬영'==orgdbDetail.relWithkmac1}">selected</c:if>>영상/촬영</option>
								<option value="운송/교통" <c:if test="${'운송/교통'==orgdbDetail.relWithkmac1}">selected</c:if>>운송/교통</option>
								<option value="장소대관" <c:if test="${'장소대관'==orgdbDetail.relWithkmac1}">selected</c:if>>장소대관</option>
								<option value="사무용품/집기" <c:if test="${'사무용품/집기'==orgdbDetail.relWithkmac1}">selected</c:if>>사무용품/집기</option>
								<option value="시스템 지원/유지보수" 	<c:if test="${'시스템 지원/유지보수'==orgdbDetail.relWithkmac1}">selected</c:if>>시스템 지원/유지보수</option>
								<option value="법률자문" <c:if test="${'법률자문'==orgdbDetail.relWithkmac1}">selected</c:if>>법률자문</option>
								<option value="기타" 	<c:if test="${'기타'==orgdbDetail.relWithkmac1}">selected</c:if>>기타</option>
								</select>
								<td>									
							</tr>
							<div id="specialField" style="display:none;">
								<input type="hidden" name="pmName" value="<c:out value="${orgdbDetail.pmName}"/>">
								<input type="text" name="specialField1" value="<c:out value="${orgdbDetail.specialField1 }" />" class="contentInput_left" />
							</div>
							<tr>
								<th>대표 연락처</th>
								<td><input type="text" name="telNo" value="<c:out value="${orgdbDetail.telNo}"/>"></td>
								<th>FAX</th>
								<td><input type="text" name="faxNo" value="<c:out value="${orgdbDetail.faxNo}"/>"></td>
								<th>홈페이지</th>
								<td><input type="text" name="homepage" value="<c:out value="${orgdbDetail.homepage}"/>"></td>										
							</tr>
							<tr>
								<th>주소</th>
								<td colspan="5">
									<input type="hidden" id="zipCode" name="zipCode" value="<c:out value="${orgdbDetail.zipCode}"/>">
									<input type="text" id="zipCodeview" name="zipCodeview" value="<c:out value="${orgdbDetail.zipCode}"/>" style="width:23%;">&nbsp
									<button type="button" class="btn line btn_grey" onclick="zipCodeWin_Open('callBackByZipWin1')"><i class="mdi mdi-post"></i>우편번호</button>
									<input type="text" id="address1" name="address1" value="<c:out value="${orgdbDetail.address1}"/>" style="width:65%;"><br>
									<input type="text" id="address2" name="address2" value="<c:out value="${orgdbDetail.address2}"/>" style="width:97%;">
								</td>								
							</tr>
							<tr>
								<th>담당역할 및 </br>회사소개</th>
								<td colspan="5">
								<div style="paddin: 10 0 10 0">
								<textarea name="workComment" class="textArea" style="width:97%;"><c:out value="${orgdbDetail.workComment}"/></textarea>
								</div>
								</td>
							</tr>
					</table>
				</div>
				</div>
				<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">담당자 정보</p>
				</div>
				<div class="select_box">
					<button type="button" class="btn line btn_blue" onclick="appendContact()"><i class="mdi mdi-arrow-up-thin-circle-outline"></i>행 추가</button>
					<button type="button" class="btn line btn_pink" onclick="deleteContact()"><i class="mdi mdi-arrow-down-thin-circle-outline"></i>행 삭제</button>
				</div>
			</div>
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 10%"/>
								<col style="width: 15%" />
								<col style="width: 15%" />
								<col style="width: 20%" />
								<col style="width: 20%" />
								<col style="width: 20%" />
							</colgroup>
							<tr>
								<th>순서</th>
								<th>담당자</th>
								<th>직위</th>
								<th>전화번호</th>
								<th>이메일</th>
								<th>담당 업무</th>
							</tr>
							<tbody id="tbodyContactList">
								<c:forEach var="contact" items="${orgdbDetail.contactList}" varStatus="i">
									<tr>
										<td><input type="checkbox"	name="chkContact" class="btn_check" id="<c:out value="${contact.contactName}" />"><label for="<c:out value="${contact.contactName }" />"/></td>
										<td><input type="text"		name="contactName"		class="contentInput_left" value="<c:out value="${contact.contactName}"/>"></td>
										<td><input type="text"		name="contactPosition"	class="contentInput_left" value="<c:out value="${contact.contactPosition}"/>"></td>
										<td><input type="text"		name="contactTel"		class="contentInput_left" value="<c:out value="${contact.contactTel}"/>"></td>
										<td><input type="text"		name="contactEmail"		class="contentInput_left" value="<c:out value="${contact.contactEmail}"/>"></td>
										<td><input type="text"		name="contactWork"		class="contentInput_left" value="<c:out value="${contact.contactWork}"/>"></td>
										<input type="hidden"		name="contactMobile"	class="contentInput_left" value="<c:out value="${contact.contactMobile}"/>">
									</tr>
								</c:forEach>
								</tbody>
							<%-- <c:if test="${empty orgdbDetail.contactList}">
								<tr><td align="center" colspan="6">연락 담당자가 없습니다.</td></tr>
							</c:if> --%>
					</table>
				</div>
			</div>
			
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">사업자 등록증, 통장사본, 회사 소개서/제안서</p>
					</div>
				</div>
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 15%" />
							<col style="width: *"/>
						</colgroup>
						<tr>
							<th>첨부파일</th>
							<td><jsp:include page="/common/repository/fileUpload_orgdb.jsp"><jsp:param value="Y" name="log"/></jsp:include></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
</form>
<div id="xxxTempDivForTemplete" style="display:none"></div>
</div>
</body>
</html>