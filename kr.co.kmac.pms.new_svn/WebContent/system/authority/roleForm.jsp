<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Role <c:out value="${ ttlGbn }" /></title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function page_load(){
	var sfrm = document.frm;
	if(sfrm.roleNum.value == ""){
		sfrm.roleSaveMode.value = "INSERT";
	} else {
		sfrm.roleSaveMode.value = "UPDATE";
	}
}
function btnSave_Onclick() {
	var sfrm = document.frm;
	if(sfrm.roleName.value == "") {
		alert("Role 명을 입력해주세요.");
		sfrm.roleName.focus();
		return false;
	}
	
	role_Save();
}

function btnClose_Onclick() {
	window.close();
}

function role_Save(){
	
	var ActionURL = "/action/AuthorityAction.do";	
	ActionURL += "?mode=saveRole";
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
<body onload="page_load();" style="width:100%; margin-right:5px; margin-left:0px">
<%-- 작업영역 --%>
<div id="PageFull">
	<table align="center" width="100%">
		<tr>
			<td>
				<h4 class="subTitle">Role <c:out value="${ ttlGbn }" /></h4>
			</td>
		</tr>
		<tr>
			<td align="center">
				<table cellSpacing="0" cellpadding="0" width="100%">
					<form name="frm" method="post">
					<tr height="25px">
						<td class="detailTableTitle_center" width="100">메뉴명</td>
						<td class="detailTableField_left">
							<input type="hidden" name="roleNum" value="<c:out value="${ roleInfo[0].roleNum }" />">
							<input type="hidden" name="roleSaveMode" value="">
							<input type="text" name="roleName" value="<c:out value="${ roleInfo[0].roleName }" />" class="contentInput_left" size="30">
						</td>
					</tr>
					<tr height="25px">
						<td class="detailTableTitle_center">정렬 순서</td>
						<td class="detailTableField_left">
							<input type="text" name="roleSeq" value="<c:out value="${ roleInfo[0].roleSeq }" />" class="contentInput_left" size="30">
						</td>					
					</tr>
					<tr height="25px">
						<td class="detailTableTitle_center">사용 여부</td>
						<td class="detailTableField_left">
							<select name="roleUseInfo" class="selectboxPopup" style="width:100px">
								<option value="Y" <c:if test="${roleInfo[0].roleUseInfo == 'Y' }">selected</c:if>>사 용 함</option>
								<option value="N" <c:if test="${roleInfo[0].roleUseInfo == 'N' }">selected</c:if>>사용안함</option>
							</select>
						</td>
					</tr>
					</form>
				</table>
				<br>
				<div class="btNbox mt10 txtC">
					<a class="btNe006bc6 txt2btn" href="#" onclick="btnSave_Onclick();">저장</a>
					<a class="btNa0a0a0 txt2btn" href="#" onclick="btnClose_Onclick();">닫기</a>
				</div>					
			</td>
		</tr>
	</table>
</div>
</body>
</html>