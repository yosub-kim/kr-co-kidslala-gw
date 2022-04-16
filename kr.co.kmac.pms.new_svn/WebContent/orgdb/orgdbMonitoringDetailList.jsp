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
function orgdb_modify(skey1,skey2) {
	document.frm.key1.value = skey1;
	document.frm.key2.value = skey2;
	relationCompanyWin_Open("callBackByCompanyWin");
}
function callBackByCompanyWin(companyList) {
	if(companyList.length > 0) {
		if(confirm("정말 수정 하시겠습니까?")) {
			frm.orgCode.value = companyList[0].OrgCODE;
			frm.orgName.value = companyList[0].name;
			var ActionURL = "/action/OrgdbMonitoringAction.do";	
			ActionURL += "?mode=updateOrgdbDetail";
			
			var sFrm = document.forms["frm"];			

			var status = AjaxRequest.submit(
				sFrm
				,{ 'url':ActionURL
					,'onSuccess':function(obj){
						document.location.reload();
					}
					,'onLoading':function(obj){}
					,'onError':function(obj){
						alert("수정할 수 없습니다.");
					}
				}
			); status = null;
		}
	}
}
</script>
</head>
<body>
<%-- 작업영역 --%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="12">
			<table width="100%"><tr>
				<td widht="50%">
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="프로젝트 협력사" />
						<jsp:param name="backButtonYN" value="Y" />
					</jsp:include>
				</td>
			</tr></table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="listTable" style="table-layout:fixed;">
				<colgroup>
					<col width="300px"/>
					<col width="300px"/>
					<col width="*" align="center"/>
				</colgroup>
				<thead>
					<tr height="25px">
						<td><c:out value="${title}"/></td>
						<td>협력사 명</td>
						<td>수 정</td>
					</tr>
				</thead>
				<tbody id="ListData">
					<c:forEach var="list" items="${result.list}">
						<tr>
							<td><c:out value="${list.title}"/></td>
							<td><c:out value="${list.orgName}"/></td>
							<td>
								<a class="btN006bc6 txt2btn" href="javascript:orgdb_modify('<c:out value="${list.key1}"/>','<c:out value="${list.key2}"/>');">수정</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>				
		</td>
	</tr>
	<form name="frm" mehtod="post">
	<tr>
		<td>
			<input type="hidden" name="category" value="<c:out value="${categoryCode}"/>">
			<input type="hidden" name="key1" value="">
			<input type="hidden" name="key2" value="">
			<input type="hidden" name="orgCode" value="">
			<input type="hidden" name="orgName" value="">
		</td>
	</tr>
	</form>
</table>
</body>
</html>