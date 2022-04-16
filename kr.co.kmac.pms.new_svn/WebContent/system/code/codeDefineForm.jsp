<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><c:out value="${ title }" /></title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function page_load(){
	
}
function btnSave_Onclick() {
	var sFrm = document.forms["frm"];
	if(sFrm.tableName.value == "") {
		alert("기준이 되는 정보는 입력해야 합니다.");
		sFrm.key1.focus();
		return false;
	}
	
	var ActionURL = "/action/CommonCodeAction.do";	
	ActionURL += "?mode=saveDefinition";
	
	var status = AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				opener.location.href = opener.document.location;
				window.close();
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){				
				alert("저장할 수 없습니다.");
			}
		}
	); status = null;
}

function btnClose_Onclick() {
	window.close();
}
</script>
</head>
<body onload="page_load();" style="width:590; padding-left: 4px; padding-right: 4px;">
<%-- 작업영역 --%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<%
		String title = (String) request.getAttribute("title");
		%>
		<td height="12" colspan="2">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="<%= title %>" />
				<jsp:param name="backButtonYN" value="N"/>
			</jsp:include>
		</td>
	</tr>
	<form name="frm" method="post">
	<tr>
		<td align="center">
			<table width="100%" cellpadding="0" cellspacing="0">
				<colgroup>
					<col style="width:100px;"/>
					<col style="width:200px;"/>
					<col style="width:100px;"/>
					<col style="width:200px;"/>
				</colgroup>			
				<tbody>
					<tr height="25px">
						<td class="detailTableTitle_center">기준코드</td>
						<td class="detailTableField_left">
							<input type="text" name="tableName" class="contentInput_left" value="<c:out value="${ codeDefine.tableName }" />" <c:if test="${ saveMode == 'UPDATE' }">readonly</c:if>>
							<input type="hidden" name="saveMode" value="<c:out value="${ saveMode }" />">
						</td>
						<td class="detailTableTitle_center">기준코드 설명</td>
						<td class="detailTableField_left">
							<input type="text" name="tableDesc" class="contentInput_left" value="<c:out value="${ codeDefine.tableDesc }" />">
						</td>
					</tr>
					<tr height="25px">
						<td class="detailTableTitle_center">key1 제목</td>
						<td class="detailTableField_left">
							<input type="text" name="key1Label" class="contentInput_left" value="<c:out value="${ codeDefine.key1Label }" />">
						</td>
						<td class="detailTableTitle_center">key2 제목</td>
						<td class="detailTableField_left">
							<input type="text" name="key2Label" class="contentInput_left" value="<c:out value="${ codeDefine.key2Label }" />">
						</td>
					</tr>
					<tr height="25px">
						<td class="detailTableTitle_center">key3 제목</td>
						<td class="detailTableField_left" colspan="3">
							<input type="text" name="key3Label" class="contentInput_left" value="<c:out value="${ codeDefine.key3Label }" />">
						</td>
					</tr>
				</tbody>
			</table>
			<br>
			<table width="100%" cellpadding="0" cellspacing="0">
				<colgroup>
					<col style="width:100px;"/>
					<col style="width:200px;"/>
					<col style="width:100px;"/>
					<col style="width:200px;"/>
				</colgroup>					
				<tbody>
					<tr height="25px">
						<td class="detailTableTitle_center" width="100">data1 제목</td>
						<td class="detailTableField_left">
							<input type="text" name="data1Label" class="contentInput_left" value="<c:out value="${ codeDefine.data1Label }" />">
						</td>
						<td class="detailTableTitle_center" width="100">data2 제목</td>
						<td class="detailTableField_left">
							<input type="text" name="data2Label" class="contentInput_left" value="<c:out value="${ codeDefine.data2Label }" />">
						</td>
					</tr>
					<tr height="25px">
						<td class="detailTableTitle_center" width="100">data3 제목</td>
						<td class="detailTableField_left">
							<input type="text" name="data3Label" class="contentInput_left" value="<c:out value="${ codeDefine.data3Label }" />">
						</td>
						<td class="detailTableTitle_center" width="100">data4 제목</td>
						<td class="detailTableField_left">
							<input type="text" name="data4Label" class="contentInput_left" value="<c:out value="${ codeDefine.data4Label }" />">
						</td>
					</tr>
					<tr height="25px">
						<td class="detailTableTitle_center" width="100">data5 제목</td>
						<td class="detailTableField_left">
							<input type="text" name="data5Label" class="contentInput_left" value="<c:out value="${ codeDefine.data5Label }" />">
						</td>
						<td class="detailTableTitle_center" width="100">data6 제목</td>
						<td class="detailTableField_left">
							<input type="text" name="data6Label" class="contentInput_left" value="<c:out value="${ codeDefine.data6Label }" />">
						</td>
					</tr>
					<tr height="25px">
						<td class="detailTableTitle_center" width="100">data7 제목</td>
						<td class="detailTableField_left">
							<input type="text" name="data7Label" class="contentInput_left" value="<c:out value="${ codeDefine.data7Label }" />">
						</td>
						<td class="detailTableTitle_center" width="100">data8 제목</td>
						<td class="detailTableField_left">
							<input type="text" name="data8Label" class="contentInput_left" value="<c:out value="${ codeDefine.data8Label }" />">
						</td>
					</tr>
					<tr height="25px">
						<td class="detailTableTitle_center" width="100">data9 제목</td>
						<td class="detailTableField_left">
							<input type="text" name="data9Label" class="contentInput_left" value="<c:out value="${ codeDefine.data9Label }" />">
						</td>
						<td class="detailTableTitle_center" width="100">data10 제목</td>
						<td class="detailTableField_left">
							<input type="text" name="data10Label" class="contentInput_left" value="<c:out value="${ codeDefine.data10Label }" />">
						</td>
					</tr>
				</tbody>
			</table>
			<div class="btNbox pt10 pb10 txtC">
				<a class="btNe006bc6 txt2btn" href="javascript:btnSave_Onclick();">저장</a>
				<a class="btNa0a0a0 txt2btn" href="javascript:btnClose_Onclick();">닫기</a>
			</div>					
		</td>
	</tr>
	</form>
</table>
</div>
</body>
</html>