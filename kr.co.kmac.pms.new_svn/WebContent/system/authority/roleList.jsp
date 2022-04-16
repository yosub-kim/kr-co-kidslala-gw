<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

var myDate = new Date();
var nowYear = myDate.getYear();

function Role_Edit(roleNum) {
	var roleForm;
	var sURL = "/action/AuthorityAction.do?mode=roleDetailForm&roleNum=" + roleNum;
	var sFeatures = "width=720, height=400, top=100, left=100";

	roleForm = window.open(sURL, "roleForm", sFeatures);
	roleForm.focus();
}

function Role_Append(){  // Role_Append
	Role_Modify('');
}

function Role_Modify(roleNum) {
	var roleForm;
	var sURL = "/action/AuthorityAction.do?mode=roleForm&roleNum=" + roleNum;
	var sFeatures = "width=500, height=180, top=100, left=100";

	roleForm = window.open(sURL, "roleForm", sFeatures);
	roleForm.focus();
}

function Role_Delete(){
	var sfrm = document.frm;
	var checkedCount = 0;
	var vMsg = "";
	for(var i = 0; i < sfrm.elements.length; i++){
		var ele = sfrm.elements[i];
		if(ele.name == "chkRole"){
			if(ele.checked){
				vMsg += ((checkedCount == 0) ? "" : ", \n") + ele.roleName;
				checkedCount ++;
			}
		} 
	}
	if(checkedCount > 0){
		var sMsg = vMsg + "\n\n을 정말로 삭제 하시겠습니까?            ";
		if(confirm(sMsg)) {
			var ActionURL = "/action/AuthorityAction.do";	
			ActionURL += "?mode=saveRole";
			var sFrm = document.forms["frm"];
			sFrm.roleSaveMode.value = "DELETE";
			var status = AjaxRequest.submit(
					sFrm
					,{ 'url':ActionURL
						,'onSuccess':function(obj){
							document.location.reload();
						}
						,'onLoading':function(obj){}
						,'onError':function(obj){
							alert("삭제할 수 없습니다.");
						}
					}
			); status = null;
		}
	}
	else
	{
		alert("삭제할 Role을 선택해 주세요.")
	}
}

function Role_Copy(){
	var sfrm = document.frm;
	var checkedCount = 0;
	var vMsg = "";
	for(var i = 0; i < sfrm.elements.length; i++){
		var ele = sfrm.elements[i];
		if(ele.name == "chkRole"){
			if(ele.checked){
				vMsg += ((checkedCount == 0) ? "" : ", \n") + ele.roleName;
				checkedCount ++;
			}
		} 
	}
	if(checkedCount > 0){
		var sMsg = vMsg + "\n\n을 정말로 복제 하시겠습니까?            ";
		if(confirm(sMsg)) {
			var ActionURL = "/action/AuthorityAction.do";	
			ActionURL += "?mode=saveRole";
			var sFrm = document.forms["frm"];
			sFrm.roleSaveMode.value = "COPY";
			var status = AjaxRequest.submit(
					sFrm
					,{ 'url':ActionURL
						,'onSuccess':function(obj){
							document.location.reload();
						}
						,'onLoading':function(obj){}
						,'onError':function(obj){
							alert("복사할 수 없습니다.");
						}
					}
			); status = null;
		}
	}
	else
	{
		alert("복사할 Role을 선택해 주세요.")
	}
}
function User_Set() {
	var sfrm = document.frm;
	var checkedCount = 0;
	var roleNum;
	for(var i = 0; i < sfrm.elements.length; i++){
		var ele = sfrm.elements[i];
		if(ele.name == "chkRole"){
			if(ele.checked){
				roleNum = ele.value;
				checkedCount ++;
			}
		} 
	}
	if(checkedCount == 0){
		alert("Role을 선택해 주세요.");
		return false;
	} else if(checkedCount > 1) {
		alert("Role을 하나만 선택해주세요.");
		return false;
	} else {
		var userSetWin;
		var sURL = "/action/AuthorityAction.do?mode=userSet&roleNum=" + roleNum;
		var sFeatures = "width=800, height=450, top=100, left=100";
		userSetWin = window.open(sURL, "userSetWin", sFeatures);
		userSetWin.focus();
	}
}
function ExtUser_Set() {
	var sfrm = document.frm;
	var checkedCount = 0;
	var roleNum;
	for(var i = 0; i < sfrm.elements.length; i++){
		var ele = sfrm.elements[i];
		if(ele.name == "chkRole"){
			if(ele.checked){
				roleNum = ele.value;
				checkedCount ++;
			}
		} 
	}
	if(checkedCount == 0){
		alert("Role을 선택해 주세요.");
		return false;
	} else if(checkedCount > 1) {
		alert("Role을 하나만 선택해주세요.");
		return false;
	} else {
		var userSetWin;
		var sURL = "/action/AuthorityAction.do?mode=extUserSet&roleNum=" + roleNum;
		var sFeatures = "width=800, height=450, top=100, left=100";
		userSetWin = window.open(sURL, "userSetWin", sFeatures);
		userSetWin.focus();
	}
}
</script>
</head>
<body>
<%-- 작업영역 --%>
<div style="margin: 70 0 0 20 ">
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="12">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="Role 관리" />
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td>
			<div class="btNbox mb10 txtR">
				<a class="btN006bc6 txt2btn" href="#" onClick="Role_Append();" name="btnAppend">추가</a>
				<a class="btNe14f42 txt2btn" href="#" onClick="Role_Delete();" name="btnDelete">삭제</a>
				<a class="btN3fac0c txt2btn" href="#" onClick="Role_Copy();" name="btnCopy">복사</a>
				<a class="btNa0a0a0 txt2btn" href="#" onClick="User_Set();" name="btnUserSet">사용자 지정</a>
				<a class="btNa0a0a0 txt2btn" href="#" onClick="ExtUser_Set();" name="btnExtUser">외부사용자 지정</a>
			</div>
		</td>
	</tr>
	<tr>
		<td align="center">
			<table class="listTable">
				<thead>
					<tr height="25px">
						<td width="40">선택</td>
						<td width="40">순서</td>
						<td class="col_Header">Role</td>
						<td width="70">사용여부</td>
						<td width="100">Role 편집</td>
					</tr>
				</thead>
				<form name="frm" method="post">
				<input type="hidden" name="roleSaveMode" value="DELETE">
				<tbody id="ListData">
					<c:forEach var="role" items="${roleList}"> 
						<tr onmouseover="row_over(this);" onmouseout="row_out(this);">
							<td>
								<input type="checkbox" name="chkRole" value="<c:out value="${role.roleNum}"/>" roleName="<c:out value="${role.roleName}" escapeXml="false"/>">
							</td>
							<td><c:out value="${role.roleSeq}"/></td>
							<td class="myoverflow">
								<a href="javascript:Role_Modify('<c:out value="${role.roleNum}"/>');">&nbsp;<c:out value="${role.roleName}" escapeXml="false"/>&nbsp;</a>
							</td>
							<td>
								<c:choose>
									<c:when test="${role.roleUseInfo == 'Y' }">사용함</c:when>
									<c:otherwise>사용안함</c:otherwise>
								</c:choose>
							</td>
							<td>
								<div class="btNbox txtC">
									<a class="btN006bc6 txt2btn" href="javascript:Role_Edit('<c:out value="${role.roleNum}"/>');">수정</a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				</form>
			</table>
			<div>
			
			</div>					
		</td>
	</tr>
</table>
</div>
</body>
</html>