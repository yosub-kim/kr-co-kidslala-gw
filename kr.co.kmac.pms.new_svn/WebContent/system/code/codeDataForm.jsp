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
	document.frm.key1.focus();
}
function btnSave_Onclick() {
	var tabName = "<c:out value="${ tableName }" />";
	var sFrm = document.forms["frm"];
	if(sFrm.key1.value == "") {
		alert("기준이 되는 정보는 입력해야 합니다.");
		sFrm.key1.focus();
		return false;
	}
	
	var ActionURL = "/action/CommonCodeAction.do";	
	ActionURL += "?mode=saveData";
	
	var status = AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				opener.getDataList(tabName);
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
<body onload="page_load();" style="padding-left: 4px; padding-right: 4px; width:480">
<%
	String title = (String) request.getAttribute("title");
%>
<%-- 작업영역 --%>
	<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
		<jsp:param name="title" value="<%= title %>" />
		<jsp:param name="backButtonYN" value="N"/>
	</jsp:include>

	<table cellSpacing="0" width="100%">
		<form name="frm" method="post">
		<thead>
			<tr height="25px">
				<td class="detailTableTitle_center" width="100">기준코드명</td>
				<td class="detailTableField_left" width="100">
					<input type="hidden" name="tableName" value="<c:out value="${ tableName }" />">
					<input type="hidden" name="saveMode"  value="<c:out value="${ saveMode  }" />">
					<input type="hidden" name="saveKey"   value="<c:out value="${ saveKey   }" />">
					<c:out value="${ tableName }" />
				</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${commonCodeArr}">
				<tr height="25px" style="display:<c:out value="${ data.codeDisplay }" />;">
					<td class="detailTableTitle_center" width="100"><c:out value="${ data.codeLabel }" /></td>
					<td class="detailTableField_left" width="100">
						<input type="text" name="<c:out value="${ data.codeId }" />" class="contentInput_left" style="ime-mode:inactive" value="<c:out value="${ data.codeValue }" />">
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="btNbox pb10 pt10 txtC">
		<a class="btNe006bc6 txt2btn" href="javascript:btnSave_Onclick();">저장</a>
		<a class="btNa0a0a0 txt2btn" href="javascript:btnClose_Onclick();">닫기</a>
	</div>					
</body>
</html>