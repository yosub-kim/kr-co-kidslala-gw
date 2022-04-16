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

function goProjectDetail(projectCode) {
	document.expenseResultListForm.target = "new";
	document.expenseResultListForm.action = "/action/ProjectBudjetInfoAction.do?mode=goProjectBudgetInfo&projectCode="+projectCode+"&viewMode=projectSearch";
	document.expenseResultListForm.submit();	
	//alert("준비중입니다.");
}

function goBudgetDetail(projectCode) {
	var url = "https://newbudget.kmac.co.kr:8080/com/login_chk_pms.jsp"
		+ "?param=<authz:authentication operation="username" />|DWPM30600_LINK|"
		+ "?projid=" + projectCode; 
	var sFeatures = "top=100,left=100,width=800,height=600,resizable=yes,scrollbars=yes";
	detailWin = window.open(url,"budget", sFeatures);
	detailWin.focus();
}

function doSearch(){
	document.expenseResultListForm.target = "";
	document.expenseResultListForm.searchOK.value = "1";
	document.expenseResultListForm.action = "/action/ExpenseManagerAction.do?mode=getValueProjectReportListRA";
	document.expenseResultListForm.submit();
}

function refresh() {
	AjaxRequest.post (
			{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpData",
				'parameters' : { },
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					if(res.result == 'SUCCESS'){
						alert('데이터를 갱신하였습니다.');
						document.location.href="/action/ExpenseManagerAction.do?mode=getValueProjectReportListRA";
					}
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("애러발생.");
					
				}
			}
	); 
}

</script>
</head>


<body>
<form name="expenseResultListForm" method="post">
<input type="hidden" name="expenseMode" value="<c:out value="${expenseMode}"/>">
<input type="hidden" name="searchOK">
	<table width="765" cellpadding="0" cellspacing="0">
		<!-- 타이틀 영역 -->
		<tr>
			<td height="12">
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
					<jsp:param name="title" value="컨설팅 프로젝트 인건비 원가 현황(RA)" />
				</jsp:include>
			</td>
		</tr>
		<tr>
			<td height="10"></td>
		</tr>			
		
		
		<!-- 검색 영역 -->
		<tr>
			<td height="21" align="left" valign="top">
				<%@ include file="/common/include/searchBox_Header.jsp"%>
								<table border="0" width="100%" height="26" cellpadding="0" cellspacing="0">
									<colgroup> 
										<col style="padding-left: 3px; padding-right: 3px; text-align: right;" width="70px">
										<col style="padding-left: 3px; padding-right: 3px; text-align: left;"  width="85px">
										<col style="padding-left: 3px; padding-right: 3px; text-align: right;" width="70px">
										<col style="padding-left: 3px; padding-right: 3px; text-align: left;"  width="60px">
										<col style="padding-left: 3px; padding-right: 3px; text-align: right;" width="85px"> 
										<col style="padding-left: 3px; padding-right: 3px; text-align: left;"  width="*">
									</colgroup>
									<tr>
										<td class="searchTitle_center">조직단위별</td>
										<td class="searchField_center">
											<org:divList enabled="1" depth="1" divType="A"  all="Y" attribute=" name='dept'  class='selectbox' style='width: 80pt' " isSelectBox="Y" selectedInfo="${dept}" ></org:divList>
										</td>
										<td class="searchTitle_center">초과여부</td>
										<td class="searchField_center">
											<select	name="isCostOver" class="selectbox" style="width: 60pt">
												<option value="">전체</option>
												<option value="Y"
													<c:if test="${isCostOver == 'Y' }">selected</c:if>>초과</option>
												<option value="N"
													<c:if test="${isCostOver == 'N' }">selected</c:if>>미초과</option>
											</select>
										</td>
										<td class="searchTitle_center">프로젝트명</td>
										<td class="searchField_center">
											<input type="text" name="projectName" size="25" class="textInput_left" value="<c:out value="${projectName}"/>">
										</td>
									</tr>									
								</table>
				<%@ include file="/common/include/searchBox_Footer.jsp"%>
			</td>
		</tr>
		
		
		<!-- SPACER -->						
		<tr>
			<td height="14">&nbsp;</td>
		</tr>
		<tr>
			<td align="right" height="30"><!-- <input type="button" value="데이터 가져오기" onclick="refresh()"> -->
			<a href="#" onclick="refresh()"><img alt="데이터 가져오기" src="/images/btn_budget_refresh.jpg" border="0"></a>
			</td>
		</tr>
	<!-- 검색부분 종료-->	

	<!-- 본문시작-->
		<tr>
			<td>
			<table width='765' cellpadding="0" cellspacing="0" class='listTable'>
				<thead>
					<tr height="25px">
						<td width="60px">조직단위</td>
						<td width="*">프로젝트명</td>
						<%-- <td width="45px">구분</td>--%>
						<td width="90px">예산</td>
						<td width="90px">결산</td>
						<%-- <td width="60px">예상실적</td> --%>
						<td width="150px">합계(결산/예산)</td>
					</tr>
				</thead>
				<tbody id="ListData">
					<c:set var="GroupCodeCntChk" value="" />
					<c:set var="projectNameChk" value="" />
					<c:forEach var="rs" items="${list.list}" varStatus="i">
						<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
							<c:if test="${GroupCodeCntChk != rs.runningDivCode}">
								<td style="word-break: break-all; text-align: center;" 
									<c:choose><c:when test="${rs.isOverYn == 'Y'}"> bgcolor="#ffdfff" </c:when><c:otherwise> class="BGL" </c:otherwise></c:choose>
									rowspan="<c:out value="${rs.runningDivCodeCnt}"/>"><c:out
									value="${rs.runningDivName}" /></td>
							</c:if>
							<c:set var="projectNameCnt" value="1" />
							<c:if test="${list.list[i.count].projectName == rs.projectName}">
								<c:set var="projectNameCnt" value="2" />
							</c:if>
							<c:if test="${projectNameChk != rs.projectName}">
								<td rowspan="<c:out value="${projectNameCnt}" />" 
								<c:choose><c:when test="${rs.isOverYn == 'Y'}"> bgcolor="#ffdfff" </c:when><c:otherwise> class="BGL" </c:otherwise></c:choose>
									>[<c:out value="${rs.projectCode}" />]<a href="javascript:goBudgetDetail('<c:out value="${rs.projectCode}" />')"><c:out value="${rs.projectName}" /></td>
							</c:if>
							<%-- <td class="detailTableField_center"><code:code tableName="ACCT_CODE_TBL" code="${rs.acctCode}" /></td> --%>
							<td class="detailTableField_right" align="right"><c:out value="${rs.planAmount}" /></td>
							<td class="detailTableField_right"><c:out value="${rs.exeAmount}" /></td>
							<%-- <td class="detailTableField_right"><c:out value="${rs.realtimeSalary}" /></td> --%>
							<c:if test="${projectNameChk != rs.projectName}">
								<td rowspan="<c:out value="${projectNameCnt}" />"
									class="detailTableField_right"><c:out
									value="${rs.grandTotalAmount}" />/<c:out
									value="${rs.grandPlanAmount}" /></td>
							</c:if>
						</tr>
						<c:set var="GroupCodeCntChk" value="${rs.runningDivCode}" />
						<c:set var="projectNameChk" value="${rs.projectName}" />
					</c:forEach>
					<c:if test="${empty list.list}"><tr><td colspan='12' align='center'><br>검색 결과가 존재하지 않습니다 .<br><br></td></tr></c:if>
				</tbody>
			</table>
			</td>
		</tr>
		<!-- 본문종료-->
	</table>
</form>
</body>
</html>