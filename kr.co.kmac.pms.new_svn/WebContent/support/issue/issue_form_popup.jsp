<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<html>
<head>
<title>문서발급대장 등록</title>
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
function doSave() {
	var ActionURL = "/action/IssueAction.do";	
	ActionURL += "?mode=saveIssue";
	var sFrm = document.forms["frm"];
	

	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					alert("정상적으로 입력되었습니다.")
					parent.opener.document.location.reload();
					self.close();
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null; 
}

window.onload=function(){
	layer_open(this, 'pop_register');
} 
</script>
</head>
<body style="padding: 15px;">
	<form name="frm" method="post">
		<div id="pop_register" class="popup_layer pop_register">
			<div class="popup_inner tbl-sc" style="width:550px; ">
				<div class="a-both">
					<p class="h1">문서발급대장 등록</p>
				</div>

				<div class="board_box">
					<div class="title_both">
						<div class="h1_area"></div>
						<div class="select_box">
							<button type="button" class="btn line btn_blue" onclick="javascript:doSave();;"><i class="mdi mdi-square-edit-outline"></i>저장</button>
							<button type="button" class="btn line btn_pink" onclick="javascript:self.close();"><i class="mdi mdi-trash-can-outline"></i>취소</button>
						</div>
					</div>
	
					<div class="board_contents">
						<table class="tbl-edit" width="400" border="0" cellspacing="1" cellpadding="0">
							<tr>
								<td width="80" height="30" bgcolor="#F4F7EB" style="text-align: center;">내 용</td>
								<td width="319" height="30" bgcolor="#FFFFFF"><input name="content" type="text" style="WIDTH: 95%; ime-mode: active;" size="40" ></td>
							</tr>
							<tr>
								<td height="30" bgcolor="#F4F7EB" style="text-align: center;">발신부서</td>
								<td height="30" bgcolor="#FFFFFF"><input name="dept_name" type="text" style="WIDTH: 105px; ime-mode: active;" size="20" value="<c:out value="${dept}"/>" ></td>
							</tr>
							<tr>
								<td height="30" bgcolor="#F4F7EB" style="text-align: center;">수 신 처</td>
								<td height="30" bgcolor="#FFFFFF"><input name="receive" type="text" style="WIDTH: 95%; ime-mode: active;" size="20" ></td>
							</tr>
							<tr>
								<td height="30" bgcolor="#F4F7EB" style="text-align: center;">매 수</td>
								<td height="30" bgcolor="#FFFFFF"><input name="count_t" type="text" onkeypress="Number_Only();" style="WIDTH: 50px; ime-mode: disabled;" size="20" value="1" ></td>
							</tr>
							<tr>
								<td height="30" bgcolor="#F4F7EB" style="text-align: center;">구 분</td>
								<td height="30" bgcolor="#FFFFFF">
									<select class="selectbox" name="gubun">
										<option value="공문">공문</option>
										<option value="인증서">인증서</option>
										<option value="협조전">협조전</option>
										<option value="기타">기타</option>
									</select>
								</td>
							</tr>
						</table>
						<div style="display: none">
							<input type="hidden" name="gaksa" value="<c:out value="${gaksa}"/>">
							<input type="hidden" name="dept" value="<c:out value="${dept}"/>"> 
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>