<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>사용자 Role지정</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

var myDate = new Date();
var nowYear = myDate.getYear();
var vRoleNum = "<c:out value="${roleNum}"/>";  //treeDataRetrieve
function Page_OnLoad(){
	var frm = document.frmSearch;
	//DropDownList_DataLoad(frm.indField, "INDUSTRY_TYPE_CODE", "<c:out value="${frmSearch.indField}"/>");
	//document.getElementById("indField").reInitializeSelectBox();
}

function role_Assign(){
	var sfrm = document.frmTobe;
	var checkedCount = 0;
	var vMsg = "";
	var vVal = "";
	for(var i = 0; i < sfrm.elements.length; i++){
		var ele = sfrm.elements[i];
		if(ele.type == "checkbox"){
			if(ele.checked){
				checkedCount ++;
			}
		} 
	}
	
	if(checkedCount > 0){

		var sFrm = document.frmTobe;
		var ActionURL = "/action/AuthorityAction.do";	
		ActionURL += "?mode=Assign_Role";
		var status = AjaxRequest.submit(
				sFrm
				,{ 'url':ActionURL
					,'onSuccess':function(obj){
						location.href = document.location;
					}
					,'onLoading':function(obj){}
					,'onError':function(obj){
						alert("Role 지정에 실패하였습니다.");
					}
				}
		); status = null;
	}	
}

function role_Dissolve(){
	var sfrm = document.frmAsis;
	var checkedCount = 0;
	var vMsg = "";
	var vVal = "";
	for(var i = 0; i < sfrm.elements.length; i++){
		var ele = sfrm.elements[i];
		if(ele.type == "checkbox"){
			if(ele.checked){
				checkedCount ++;
			}
		} 
	}	
	if(checkedCount > 0){

		var sFrm = document.frmAsis;
		var ActionURL = "/action/AuthorityAction.do";	
		ActionURL += "?mode=Assign_Role";
		var status = AjaxRequest.submit(
				sFrm
				,{ 'url':ActionURL
					,'onSuccess':function(obj){
						location.href = document.location;
					}
					,'onLoading':function(obj){}
					,'onError':function(obj){
						alert("Role 지정에 실패하였습니다.");
					}
				}
		); status = null;
	}
}
function doSearch() {
	document.frmSearch.submit();
}
</script>
<style type="text/css">
<!--
.out_Line {
	margin: 1px;padding: 1px; height: 340px; width: 99%; border-width:1px; border-color:#B2BEC1; border-style: solid; overflow: auto;
}
-->
</style>
</head>
<body style="margin-top: 0px; padding-left: 0px; margin-left: 5px; padding-top: 5px; position: static;" onload="Page_OnLoad();">
<%-- 작업영역 --%>
<div id="PageFull">
<table align="center" width="99%" height="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td>
			<h4 class="subTitle">&nbsp;Role 명: <c:out value="${ roleInfo[0].roleName }"/></h4>
		</td>
	</tr>
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0" width="99%" border="0" align="center">
				<tr>
					<td>
						<table width="100%" cellpadding="0" cellspacing="0" border="0">
							<colgroup> 
								<col width="90px">
								<col width="150px">
								<col width="90px">
								<col width="*">
								<col width="50px">
							</colgroup>
							<form name="frmSearch">
								<input type="hidden" name="mode" value="userSet">
								<input type="hidden" name="roleNum" value="<c:out value="${ roleNum }"/>">
							<tr>
								<td class="searchTitle_center">산업구분</td>
								<td class="searchField_left">
									<!-- 
									<code:codelist tableName="INDUSTRY_TYPE_CODE" attribute="name='indField' style='width:100%'" isSelectBox="Y" all="Y" selectedInfo="" />
									-->
									<select name='indField' class='selectbox' style='width:100%'>
										<option value='' <c:if test="${frmSearch.indField == ''}">selected</c:if>>전체</option>
										<option value='ITA' <c:if test="${frmSearch.indField == 'ITA'}">selected</c:if>>제조업</option>
										<option value='ITB'<c:if test="${frmSearch.indField == 'ITB'}">selected</c:if>>서비스업</option>
										<option value='ITC'<c:if test="${frmSearch.indField == 'ITC'}">selected</c:if>>행정자치</option>
										<option value='ITD'<c:if test="${frmSearch.indField == 'ITD'}">selected</c:if>>사회공공</option>
										<option value='ITE'<c:if test="${frmSearch.indField == 'ITE'}">selected</c:if>>산업계전반</option>
									</select>
								</td>
								<td class="searchTitle_center">키워드</td>
								<td class="searchField_left">
									<select name="keyWordCon" class="selectbox" style="width:100px">
										<option value="name" <c:if test="${frmSearch.keyWordCon == 'name'}">selected</c:if>>이름</option>
										<option value="company" <c:if test="${frmSearch.keyWordCon == 'company'}">selected</c:if>>소속기관</option>
										<option value="createid" <c:if test="${frmSearch.keyWordCon == 'createid'}">selected</c:if>>등록자</option>
									</select>
									<input type="text" name="keyWord" class="textInput_left" style="width:240px" value="<c:out value="${frmSearch.keyWord}"/>">
									<!-- <input type="submit" name="btnSearch" value="검  색"> -->
								</td>
								<td class="searchField_center" style="border-left:0px"><a href="javascript:doSearch();"><img src="/images/sub/btnSrch.gif" height="27" width="27"></a></td>
							</tr>
							</form>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<td>
		<!-- inline table -->
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
				   	<td width="47%" align="center">
				    	<div class="out_Line" style="text-align:center">
				    		<!-- left table -->
							<table width="100%" cellpadding="0" cellspacing="0" border="0" class="listTable" align="center">
								<thead>
									<tr height="25px">
										<td width="30">선택</td>
										<td width="60">성명</td>
										<td width="80">소속</td>
										<td width="*">전문분야</td>
									</tr>
								</thead>
								<form name="frmTobe" method="post">
									<input type="hidden" name="roleNum" value="<c:out value="${roleNum}"/>">
								<tbody id="ListData">
									<c:if test="${!empty tobeExperts}">
									<c:forEach var="expert" items="${tobeExperts}"> 
										<tr>
											<td class="detailTableField_center">
												<input type="checkbox" name="chkExpert" value="<c:out value="${expert.ssn}" />">
											</td>
											<td class="detailTableField_center"><c:out value="${expert.name}"/></td>
											<td class="detailTableField_center"><c:out value="${expert.company}" escapeXml="false"/></td>
											<td><expertPool:exportmajor code="${expert.consultingMajor}" />&nbsp;</td>
										</tr>
									</c:forEach>
									</c:if>
									<c:if test="${empty tobeExperts}">
										<tr>
											<td colspan="4" align="center">검색 결과가 없습니다.</td>
										</tr>
									</c:if>
								</tbody>
								</form>
							</table>
				    	</div>
			    	</td>
				    <td width="6%" align="center">
						<a href="javascript:role_Assign();"><img src="/images/btn_right_arrow.gif" class="IMGBTN"></a>
						<br>
						<br>
						<br>
						<a href="javascript:role_Dissolve();"><img src="/images/btn_left_arrow.gif"  class="IMGBTN"></a>
				    </td>
				    <td width="47%">
			    		<div class="out_Line" style="text-align:center">
			    			<!-- right table -->
							<table width="100%" border="0" class="listTable">
								<thead>
									<tr height="25px">
										<td width="30">선택</td>
										<td width="60">성명</td>
										<td width="80">소속</td>
										<td width="*">전문분야</td>
									</tr>
								</thead>
								<form name="frmAsis" method="post">
									<input type="hidden" name="roleNum" value="<c:out value=""/>">
								<tbody id="ListData">
									<c:forEach var="expert" items="${asisExperts}"> 
										<tr>
											<td class="detailTableField_center">
												<input type="checkbox" name="chkExpert" value="<c:out value="${expert.ssn}" />">
											</td>
											<td class="detailTableField_center"><c:out value="${expert.name}"/></td>
											<td class="detailTableField_center"><c:out value="${expert.company}" escapeXml="false"/></td>
											<td><expertPool:exportmajor code="${expert.consultingMajor}" />&nbsp;</td>
										</tr>
									</c:forEach>
								</tbody>
								</form>
							</table>
			    		</div>
			    	</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
</body>
</html>