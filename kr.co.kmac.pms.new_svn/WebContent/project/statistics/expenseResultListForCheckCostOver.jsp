<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
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

function goProjectDetail(projectCode) {
	document.expenseResultListForm.target = "new";
	document.expenseResultListForm.action = "/action/ProjectBudjetInfoAction.do?mode=goProjectBudgetInfo&projectCode="+projectCode+"&viewMode=projectSearch";
	document.expenseResultListForm.submit();	
	//alert("준비중입니다.");
}
function goSalaryDetail(searchMonth, projectCode, acctCode){
	var width="800";
	var height="500";
	if(acctCode == '5000402'){
		acctCode = "A";
	}else if(acctCode == '5001202'){
		acctCode = "~A";		
	}else{
		acctCode = "D";
		var width="600";
		var height="300";
	}
	var url = "/action/ExpenseManagerAction.do?mode=getValueProjectReportListDetail"
		+ "&searchMonth=" + searchMonth
		+ "&projectCode=" + projectCode
		+ "&acctCode=" + acctCode; 
	var sFeatures = "top=100,left=100,width="+width+",height="+height+",resizable=yes,scrollbars=yes";
	detailWin1 = window.open(url,"detail", sFeatures);
	detailWin1.focus();
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
	document.expenseResultListForm.action = "/action/ExpenseManagerAction.do?mode=getValueProjectReportList";
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
						document.location.href="/action/ExpenseManagerAction.do?mode=getValueProjectReportList";
					}
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("에러가 발생하였습니다.");
					
				}
			}
	); 
}

</script>
</head>


<body>
<form name="expenseResultListForm" method="post">
	<div style="display:none">
		<input type="hidden" name="expenseMode" value="<c:out value="${expenseMode}"/>">
		<input type="hidden" name="searchOK">
	</div>
	
	<div class="location">
			<p class="menu_title">인건비 원가 현황</p>
			<ul>
				<li class="home">HOME</li>
				<li>PMS</li>
				<li>프로젝트 현황</li>
				<li>인건비 원가 현황</li>
			</ul>
		</div>
		<!-- // location -->
		
			<!-- contents sub -->
	<div class="contents sub">
	
		<!-- 신규 프로젝트 등록 -->
			<div class="box">

				<div class="search_box total">
					<div class="select_group">
						<div class="select_box">
							<div class="label_group">
								<p>조직 단위</p>
								<org:divList enabled="1" depth="2" divType="A"  all="Y" attribute=" name='dept'  id='dept'  class='select' " isSelectBox="Y" selectedInfo="${dept}" ></org:divList>
								</div>
							<div class="label_group">
								<p>초과 여부</p>
							<select	name="isCostOver" id="isCostOver" class="select">
									<option value="">전체</option>
									<option value="Y"
										<c:if test="${isCostOver == 'Y' }">selected</c:if>>초과</option>
									<option value="N"
										<c:if test="${isCostOver == 'N' }">selected</c:if>>미초과</option>
								</select>	
							</div>
							<div class="label_group">
								<p>프로젝트명</p>
								<input type="text" name="projectName" id="projectName" class="textInput_left" value="<c:out value="${projectName}"/>">
							</div>
					</div>
					</div>
					<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색 <i class="mdi mdi-magnify"></i></button></div>
			</div>
			</div>
				
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">프로젝트 인건비 원가 현황</p>
					</div>
					<div class="select_box">
					<button type="button" class="btn line btn_blue" onclick="refresh()"><i class="mdi mdi-clipboard-check-outline"></i>데이터 가져오기</button>
					</div>
				</div>
		
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 12%" />
								<col style="width: *" />
								<col style="width: 8%" />
								<col style="width: 12%" />
								<col style="width: 12%" />
								<col style="width: 12%" />
								<col style="width: 12%" />
								<col style="width: 12%" />
							</colgroup>
							<tr>
								<th>조직단위</th>
								<th>프로젝트명</th>
								<th>구분</th>
								<th>예산</th>
								<th>총 결산</th>
								<th>결산</th>
								<th>예상 결산</th>
								<th>합계(총결산/예산)</th>			
							</tr>
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
							<c:if test="${projectNameChk != rs.projectName}">
								<td rowspan="<c:out value="${rs.projectCodeCnt}" />" 
								<c:choose><c:when test="${rs.isOverYn == 'Y'}"> bgcolor="#FBDDE5" </c:when><c:otherwise> class="BGL" </c:otherwise></c:choose>
									>[<c:out value="${rs.projectCode}" />]<a href="javascript:goBudgetDetail('<c:out value="${rs.projectCode}" />')"><c:out value="${rs.projectName}" /></td>
							</c:if>
							<td class="detailTableField_center"><code:code tableName="ACCT_CODE_TBL" code="${rs.acctCode}" /></td>
							<td class="detailTableField_right" align="right"><c:out value="${rs.planAmount}" /></td>
							<td class="detailTableField_right"><c:out value="${rs.totalAmount}" /></td>
							<td class="detailTableField_right"><c:out value="${rs.exeAmount}" /></td>
							<td class="detailTableField_right"
								><a href="javascript:goSalaryDetail('<c:out value="${searchMonth}" />','<c:out value="${rs.projectCode}" />','<c:out value="${rs.acctCode}" />')"
								><c:out value="${rs.realtimeSalary}" /></a></td>
							<c:if test="${projectNameChk != rs.projectName}">
								<td rowspan="<c:out value="${rs.projectCodeCnt}" />"
									class="detailTableField_right"><c:out value="${rs.grandTotalAmount}" />/</br><c:out value="${rs.grandPlanAmount}" /></td>
							</c:if>
						</tr>
						<c:set var="GroupCodeCntChk" value="${rs.runningDivCode}" />
						<c:set var="projectNameChk" value="${rs.projectName}" />
					</c:forEach>
					<c:if test="${empty list.list}"><tr><td colspan='8' align='center'><br>검색 결과가 존재하지 않습니다 .<br><br></td></tr></c:if>
					</tbody>
				</table>
				</div>
				</div>
			</div>
			</div>
	
	
</form>
</body>
</html>