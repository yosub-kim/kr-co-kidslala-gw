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
function btnNew_onclick(){
	document.location = "/action/ExpertPoolManagerAction.do?mode=loadFormExpertPool";
}
function modifyForm(menuNum){
	var inputForm;
	var sURL      = "/action/AuthorityAction.do?mode=menuForm&menuNum=" + menuNum;
	var sFeatures = "width=500, height=220, top=100, left=100";
	inputForm = window.open(sURL, "inputForm", sFeatures);
	inputForm.focus();
}

function menu_Append(){
	modifyForm("");
}

function menu_Delete(){
	var sfrm = document.frm;
	var checkedCount = 0;
	var vMsg = "";
	for(var i = 0; i < sfrm.elements.length; i++){
		var ele = sfrm.elements[i];
		if(ele.name == "chkMenu"){
			if(ele.checked){
				vMsg += ((checkedCount == 0) ? "" : ", \n") + ele.menuName;
				checkedCount ++;
			}
		} 
	}
	if(checkedCount > 0){
		var sMsg = vMsg + "\n\n을 정말로 삭제 하시겠습니까?            ";
		if(confirm(sMsg)) {
			menuDelete();
		}
	} else {
		alert("삭제할 메뉴를 선택하십시오.");
	}
}

function menuDelete(){

	var ActionURL = "/action/AuthorityAction.do";	
	ActionURL += "?mode=saveMenu";
	var sFrm = document.forms["frm"];
	

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
function doSearch(){
	searchfrm.submit();
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
				<jsp:param name="title" value="메뉴 관리" />
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<td>
			<%@ include file="/common/include/searchBox_Header.jsp"%>
				<table cellSpacing="0" cellpadding="0" width="100%" class="tableStyle03" style="table-layout:fixed;">
					<thead>
						<form name="searchfrm">
						<tr height="25px">
							<th class="col_Header">분류</td>
							<th class="col_Header">메뉴명</td>
							<th class="col_Header">물리적 경로</td>
							<th class="col_Header">레벨</td>
							<th class="col_Header">사용</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="detailTableField_left">
								<input type="text" name="searchMenuSort" value="<c:out value="${ searchMenuSort }" />"  class="textInput_left">
								<input type="hidden" name="mode" value="menuRetrieve">
							</td>
							<td class="detailTableField_left">
								<input type="text" name="searchMenuName" value="<c:out value="${ searchMenuName }" />" class="textInput_left">
							</td>
							<td class="detailTableField_left">
								<input type="text" name="searchMenuPath" value="<c:out value="${ searchMenuPath }" />"  class="textInput_left">
							</td>
							<td class="detailTableField_left">
								<select name="searchMenuType" class="selectbox">
									<option value="">전체</option>
									<option value="1" <c:if test="${searchMenuType == '1'}">selected</c:if>>1레벨</option>
									<option value="2" <c:if test="${searchMenuType == '2'}">selected</c:if>>2레벨</option>
									<option value="3" <c:if test="${searchMenuType == '3'}">selected</c:if>>3레벨</option>
								</select>
							</td>
							<td class="detailTableField_left">
								<select name="searchUseYN" class="selectbox">
									<option value="">전체</option>
									<option value="Y" <c:if test="${searchUseYN == 'Y' }">selected</c:if>>사 용 함</option>
									<option value="N" <c:if test="${searchUseYN == 'N' }">selected</c:if>>사용안함</option>
								</select>
							</td>
						</tr>
						</form>
					</tbody>
				</table>
			<%@ include file="/common/include/searchBox_Footer.jsp"%>
		</td>
	</tr>
	<tr>
		<td height="36">
			<div class="btNbox txtR">
				<a title="메뉴추가" class="btN3fac0c txt2btn" name="btnAppend" href="#" onClick="menu_Append();">추가</a>
				<a title="메뉴삭제" class="btNe14f42 txt2btn" name="btnDelete" href="#" onClick="menu_Delete();">삭제</a>
			</div>
		</td>
	</tr>
	<tr>
		<td align="center">
			<table class="listTable" style="table-layout:fixed;">
				<colgroup>
					<col width="35px"/>
					<col width="60px"/>
					<col width="140px"/>
					<col width="*"/>
					<col width="70px"/>
					<col width="70px"/>
				</colgroup>
				<thead>
					<tr height="25px">
						<td >선택</td>
						<td >분류</td>
						<td >메뉴명</td>
						<td >물리적 경로</td>
						<td >레벨</td>
						<td >사용</td>
					</tr>
				</thead>
				<form name="frm" method="post">
				<input type="hidden" name="menuSaveMode" value="DELETE">
				<tbody id="ListData">
					<c:forEach var="menu" items="${menuList}"> 
						<tr onmouseover="row_over(this);" onmouseout="row_out(this);">
							<td >
								<input type="checkbox" name="chkMenu" value="<c:out value="${menu.menuNum}" />" menuName="<c:out value="${menu.menuName}" />">
							</td>
							<td ><c:out value="${menu.menuSort}"/></td>
							<td><a href="javascript:modifyForm('<c:out value="${menu.menuNum}" />');"> <c:out value="${menu.menuName}" escapeXml="false"/> </a></td>
							<td class="myoverflow" title="<c:out value="${menu.menuPath}"/>"><c:out value="${menu.menuPath}"/></td>
							<td ><c:out value = "${menu.menuType}" /> 레벨</td>
							<td>
								<c:choose>
									<c:when test="${menu.menuUseInfo == 'Y' }">사용함</c:when>
									<c:otherwise>사용안함</c:otherwise>
								</c:choose>
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
<br>
</body>
</html>