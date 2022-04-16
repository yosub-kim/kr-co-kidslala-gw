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
function goPage(next_page) {
	document.sanctionPresentStateForm.pageNo.value = next_page ;
	document.sanctionPresentStateForm.target = "";		
	document.sanctionPresentStateForm.action = "/action/SanctiontPresentStateAction.do?mode=getSanctionCountOnProject";
	document.sanctionPresentStateForm.submit();
}
function doSearch() {
	document.sanctionPresentStateForm.target = "";		
	document.sanctionPresentStateForm.action = "/action/SanctiontPresentStateAction.do?mode=getSanctionCountOnProject";
	document.sanctionPresentStateForm.submit();
}
function sanctionItemClick(projectCode, approvalType, seq, workType) { 
	var status = AjaxRequest.post (
			{	'url': "/action/WorkCabinetAction.do?mode=getWorkType",
				'parameters' : { "workTypeId": workType },
				'onSuccess': function(obj){
		           	var res = eval('(' + obj.responseText + ')');
		           	var rsObj = res.workType.formUrl;
	           		document.sanctionPresentStateForm.action = rsObj+"&readonly=true&projectCode="+projectCode+"&approvalType="+approvalType+"&seq="+seq;
					document.sanctionPresentStateForm.target = "";		
					document.sanctionPresentStateForm.submit();
				},
				'onLoading': function(obj){},
				'onError': function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;	
}
</script>
</head>

<body>
<%@ include file="/common/include/includeBodyTop.jsp"%>
<form name="sanctionPresentStateForm" method="post"><input type="hidden" name='pageNo'> <input type="hidden" name='seq'>

<table width="765" cellpadding="0" cellspacing="0">
	<!-- SPACER -->
	<tr>
		<td width="765" height="8">&nbsp;</td>
	</tr>

	<tr>
		<td><jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
			<jsp:param name="title" value="프로젝트  결재현황보기" />
		</jsp:include></td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>




	<!-- 검색 영역 -->
	<tr>
		<td height="21" align="left" valign="top"><%@ include file="/common/include/searchBox_Header.jsp"%>
		<table border="0" width="100%" height="26" cellpadding="0" cellspacing="0">
			<colgroup>
				<col style="padding-left: 3px; padding-right: 3px; text-align: right;" width="80px">
				<col style="padding-left: 3px; padding-right: 3px; text-align: left;" width="165px">
				<col style="padding-left: 3px; padding-right: 3px; text-align: right;" width="70px">
				<col style="padding-left: 3px; padding-right: 3px; text-align: left;" width="85px">
				<col style="padding-left: 3px; padding-right: 3px; text-align: right;" width="90px">
				<col style="padding-left: 3px; padding-right: 3px; text-align: right;" width="120px">
				<col style="padding-left: 3px; padding-right: 3px; text-align: left;" width="*">
			</colgroup>
			<tr>
				<td class="searchTitle_center">결재유형</td>
				<td class="searchField_center">
					<select name="approvalType" class="selectbox" style="width: 100%;">
						<!--
						<option value="">결재 유형</option>
						<c:forEach var="item" items="${sanctionTemplateList}">
							<option value="<c:out value="${item.approvalType}"/>" <c:if test="${approvalType == item.approvalType}">selected</c:if>><c:out value="${item.approvalName}" /></option>
						</c:forEach>
						-->
						<option value="">결재 유형</option>
						<option value="M" <c:if test="${approvalType == 'M' }">selected</c:if>>인력변동</option>
						<option value="S" <c:if test="${approvalType == 'S' }">selected</c:if>>일정변경</option>
						<option value="draft11" <c:if test="${approvalType == 'draft11' }">selected</c:if>>사업관련</option>
						<option value="draft14" <c:if test="${approvalType == 'draft14' }">selected</c:if>>예산변경</option>
						<option value="R" <c:if test="${approvalType == 'R' }">selected</c:if>>기획사업재품의</option>
						<option value="C" <c:if test="${approvalType == 'C' }">selected</c:if>>기획사업취소</option>
					</select>
				</td>
				<td class="searchTitle_center">연도</td>
				<td class="searchField_center">
					<date:year beforeYears="4" afterYears="4" attribute=" name='searchYear' class='selectbox' style='width:100pt' " selectedInfo="${searchYear}" />
				</td>
				<td class="searchTitle_center">조직단위</td>
				<td class="searchField_center">
					<org:divList enabled="1" depth="2" divType="" isSelectBox="Y" selectedInfo="${divCode}" attribute=" name='divCode'  class='selectbox' " all="Y"></org:divList>
				</td>
			</tr>
		</table>
		<%@ include file="/common/include/searchBox_Footer.jsp"%></td>
	</tr>


	<!-- SPACER -->
	<tr>
		<td height="18">&nbsp;</td>
	</tr>




	<!-- 본문 리스트 시작 -->
	<tr>
		<td align="left" valign="top">
		<table width="765" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="2" align="left"><!-- 페이지 정보, 버튼 -->
				<table width="765" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="18" width="*"><strong><c:out value="${list.valueListInfo.totalNumberOfEntries}" /> Total- Page(<c:out
							value="${list.valueListInfo.pagingPage}" /> of <c:out value="${list.valueListInfo.totalNumberOfPages}" />)</strong></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td align="left">
				<table width="765" class="listTable" style="table-layout: fixed;">
					<colgroup>
						<col width="*" align="left">
						<col width="95x" align="center">
						<col width="60px" align="center">
						<col width="60px" align="center">
						<col width="60px" align="center">
						<col width="60px" align="center">
						<col width="60px" align="center">
						<col width="60px" align="center">
					</colgroup>
					<thead>
						<tr style="height: 32;">
							<td>프로젝트명</td>
							<td>조직단위</td>
							<td>인력<br/>변동</td>
							<td>일정<br/>변경</td>
							<td>사업<br/>관련</td>
							<td>예산<br/>변경</td>
							<td>기획사업<br/>재품의</td>
							<td>기획사업<br/>취소</td>
						</tr>
					</thead>
					<tbody id="ListData">
						<c:if test="${!empty list.list}">
							<c:forEach var="rs" items="${list.list}">
								<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
									<td>[<c:out value="${rs.projectCode}" />] <c:out value="${rs.projectName}" /></td>
									<td><c:out value="${rs.divName}" /></td>
									<td><c:out value="${rs.m0000}" /></td>
									<td><c:out value="${rs.s0000}" /></td>
									<td><c:out value="${rs.draft11}" /></td>
									<td><c:out value="${rs.draft14}" /></td>
									<td><c:out value="${rs.r0000}" /></td>
									<td><c:out value="${rs.c0000}" /></td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty list.list}">
							<td align="center" colspan="8">검색된 데이터가 없습니다.</td>
						</c:if>
					</tbody>
				</table>
				</td>
			</tr>
			<!-- 본문 리스트 종료 -->





			<!-- 본문종료-->
			<!-- 버튼부분-->
			<!-- 
						<tr>
							<td height='7'></td>
						</tr>
						<tr>
							<td>
								<table width='793' height='32' bgcolor='#F3F3F3' cellpadding="0" cellspacing="0">
									<tr>
										<td><p align='right'><img src='/images/dbt_b16on.gif' align="absmiddle" onclick="javascirpt:btnNew_onclick()">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
 -->
			<!-- 버튼종료-->
		</table>
		</td>
	</tr>
</table>

</form>

<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>
</html>