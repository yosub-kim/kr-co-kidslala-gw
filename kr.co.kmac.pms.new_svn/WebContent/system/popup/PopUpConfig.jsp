<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>PopUp 설정</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function page_load(){
	//나모에디터의 내용을 보여주는 함수.
	//setTimeout("ContentDataLoad()","500");
}
function ContentDataLoad(){
	var frm = document.forms["frm"];
	document.Wec.MIMEValue = frm.content.value;
}
function wec_OnInitCompleted(){
	document.Wec.Value = frm.content.value;
}
function btnSave_Onclick() {
	var sFrm = document.frm;
	if(sFrm.width.value == ""){
		alert("너비를 입력하세요.");
		return false;
	}
	if(sFrm.height.value == ""){
		alert("높이를 입력하세요.");
		return false;
	}
	//sFrm.content.value = window.editBox.getHTML();
	
	oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.
	
	if(sFrm.content.value == ""){
		alert("내용을 입력하세요.");
		return false;
	}

	var ActionURL = "/action/PopupConfigAction.do";	
	ActionURL += "?mode=updatePopUp";
	
	var status = AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				var res = eval('(' + obj.responseText + ')');
				alert(res.resultMsg);
				location.href = document.location;
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
<body>
<%-- 작업영역 --%>
<form name="frm" method="post">
<div style="margin: 70 0 0 20 ">
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="12" colspan="2">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="PopUp 설정" />
				<jsp:param name="backButtonYN" value="N"/>
			</jsp:include>
		</td>
	</tr>
	<!-- sub 타이틀 영역 시작--> 
	<tr>
		<td>
			<h4 class="subTitle">상태 설정</h4>
		</td>
	</tr>
	<!-- sub 타이틀 영역 종료--> 
	<tr>
		<td>
		<table class="tableStyle03">
			<tr>
				<td class="detailTableTitle_center" width="100">사이즈 설정</td>
				<td class="detailTableField_left" width="*">
					가로 사이즈 <input type="text" name="width" class="textinput_left" style="width:100px" value="<c:out value="${popUp.width}"/>">
					×
					세로 사이즈 <input type="text" name="height" class="textinput_left" style="width:100px" value="<c:out value="${popUp.height}"/>">
				</td>
				<td class="detailTableTitle_center" width="100">활성화 여부</td>
				<td class="detailTableField_center center" width="50">
					<input type="checkbox" name="isEnable" value="Y" <c:if test="${popUp.isEnable == 'Y'}">checked</c:if>>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<!-- sub 타이틀 영역 시작--> 
	<tr>
		<td>
			<h4 class="subTitle mt10">내용 입력</h4>
		</td>
	</tr>
	<!-- sub 타이틀 영역 종료-->
	<tr>
		<td>
			<table class="tableStyle03">
				<tr>
					<td width="100%" style="border-width:1; border-color: #C7C7C7; border-style: solid; " colspan="2">
						<textarea name="content" id="content" rows="10" cols="100" style="width:736px; height:520px; display:none;"><c:out value="${popUp.content}"/></textarea>
						<!-- 웹에디터 시작-->
						<%@ include file="/common/edit/webEditor.jsp"%>
						<!--웹에디터 끝 -->
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td height="15px"></td></tr>
	<tr>
		<td>
			<div class="btNbox txtC">
				<a title="저장" class="btNe006bc6 txt4btn" href="#" onClick="btnSave_Onclick();">저장</a>
			</div>
		</td>
	</tr>				
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
</form>
</div>
</body>
</html>