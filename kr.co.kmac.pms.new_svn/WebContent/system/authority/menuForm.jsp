<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>메뉴 <c:out value="${ ttlGbn }" /></title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function page_load(){
	var sfrm = document.frm;
	if(sfrm.menuNum.value == ""){
		sfrm.menuSaveMode.value = "INSERT";
	} else {
		sfrm.menuSaveMode.value = "UPDATE";
	}
}
function btnSave_Onclick() {
	var sfrm = document.frm;
	if(sfrm.menuName.value == "") {
		alert("메뉴명을 입력해주세요.");
		sfrm.menuName.focus();
		return false;
	}
	if(sfrm.menuPath.value == "") {
		alert("메뉴위치를 입력해주세요.");
		sfrm.menuPath.focus();
		return false;
	}
	
	menu_Save();
}

function btnClose_Onclick() {
	window.close();
}

function menu_Save(){
	
	var ActionURL = "/action/AuthorityAction.do";	
	ActionURL += "?mode=saveMenu";
	var sFrm = document.forms["frm"];	

	var status = AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				opener.document.location.reload();
				window.close();
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){				
				alert("저장할 수 없습니다.");
			}
		}
	); status = null;
}
</script>
</head>
<body onload="page_load();" style="width:430">
<%-- 작업영역 --%>
<div id="PageFull">
	<h4 class="subTitle">메뉴 <c:out value="${ ttlGbn }" /></h4>
	
	<table cellSpacing="0" cellpadding="0" width="100%" >
		<form name="frm" method="post">
		<tr height="25px">
			<td class="detailTableTitle_center" width="100">분류</td>
			<td class="detailTableField_left">
				<input type="hidden" name="menuNum" value="<c:out value="${ menuInfo[0].menuNum }" />">
				<input type="hidden" name="menuSaveMode" value="">
				<input type="text" name="menuSort" value="<c:out value="${ menuInfo[0].menuSort }" />" class="contentInput_left" style="width:100%" size="50">
			</td>
		</tr>
		<tr height="25px">
			<td class="detailTableTitle_center">메뉴명</td>
			<td class="detailTableField_left">
				<input type="text" name="menuName" value="<c:out value="${ menuInfo[0].menuName }" />" class="contentInput_left" style="width:100%" size="50">
			</td>
		</tr>
		<tr height="25px">
			<td class="detailTableTitle_center">물리적 경로</td>
			<td class="detailTableField_left">
				<input type="text" name="menuPath" value="<c:out value="${ menuInfo[0].menuPath }" />" class="contentInput_left" style="width:100%" size="50" >
			</td>
		</tr>
		<tr height="25px">
			<td class="detailTableTitle_center">레벨</td>
			<td class="detailTableField_left">
				<select name="menuType" class="selectboxPopup" style="width:80px">
					<option value="1" <c:if test="${menuInfo[0].menuType == '1'}">selected</c:if>>1레벨</option>
					<option value="2" <c:if test="${menuInfo[0].menuType == '2'}">selected</c:if>>2레벨</option>
					<option value="3" <c:if test="${menuInfo[0].menuType == '3'}">selected</c:if>>3레벨</option>
				</select>
			</td>
		</tr>
		<tr height="25px">
			<td class="detailTableTitle_center">사용 여부</td>
			<td class="detailTableField_left">
				<select name="menuUseInfo" class="selectboxPopup" style="width:100px">
					<option value="Y" <c:if test="${menuInfo[0].menuUseInfo == 'Y' }">selected</c:if>>사 용 함</option>
					<option value="N" <c:if test="${menuInfo[0].menuUseInfo == 'N' }">selected</c:if>>사용안함</option>
				</select>
			</td>
		</tr>
		</form>
	</table>
	<div class="btNbox mt15 txtC">
		<a title="저장" class="btNe006bc6 txt2btn" href="#" onclick="btnSave_Onclick();">저장</a>
		<a title="취소" class="btNa0a0a0 txt2btn" href="#" onclick="btnClose_Onclick();">닫기</a>
	</div>					
		
</div>
</body>
</html>