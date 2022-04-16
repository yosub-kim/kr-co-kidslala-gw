<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="java.util.Calendar"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>

<%@page import="java.util.List"%>
<%@page import="kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
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
<%
	boolean canSanction = true;
	int exceedCnt = 0;
	//리스트 오브젝트 
	List<ExpenseRealTimeResultDetailForSanction> list = (List)request.getAttribute("expenselist");
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" class='listTable' cellpadding='0' cellspacing='0' width='100%' style='table-layout: fixed;' ");
	tableGrid.setHeadAnnex(false); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합

	//헤더 정의 
		Row headerRow = new Row();		
		headerRow.setAttribute(" style='height:38px;' ");
		headerRow.addCell(new Cell("구분").setAttribute(" width='60' "));
		headerRow.addCell(new Cell("이름").setAttribute(" width='70' ")); 
		headerRow.addCell(new Cell("금액").setAttribute(" width='70' "));
		headerRow.addCell(new Cell("금액(70%)").setAttribute(" width='70' "));
		headerRow.addCell(new Cell("금액(30%)").setAttribute(" width='70' "));
		headerRow.addCell(new Cell("프로젝트").setAttribute(" width='180' "));
		headerRow.addCell(new Cell("프로젝트<br>소계").setAttribute(" align='center' width='80' "));
		headerRow.addCell(new Cell("지급 성과급<br>누계").setAttribute(" align='center' width='80' "));
/* 		headerRow.addCell(new Cell("성과급<br>예산").setAttribute(" align='center' width='70' ")); */
		headerRow.addCell(new Cell("유형").setAttribute(" align='center' width='60' "));
		tableGrid.addHeadRow(headerRow);
		
		Row row = null;
		if(list != null && list.size() > 0){
			for(ExpenseRealTimeResultDetailForSanction data : list){
				String isExceedStyle = "";
				String isReportExceedStyle = "";
				String isMMExceedStyle = "";
				if(data.getSalaryIsExceed().equals("Y")){
					isExceedStyle = " background-color: #ffdfff; ";
					canSanction = false;
					exceedCnt = exceedCnt + 1;
				}
				//if(data.getSalaryIsMMExceed().equals("Y")) {
				//	isMMExceedStyle = " background-color: #ffd9b3; ";
				//}
				row = new Row().setAttribute(" row='salary' ");
				row.addCell(new Cell(data.getSalaryJobClassDesc()).setAttribute("  "));
				row.addCell(new Cell(data.getSalaryName()).setAttribute(" style='text-align:center; "+isReportExceedStyle+" ' "));
	 			row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryTotalRealTimeSalary())).setAttribute(" style='padding: 7px 2px 7px 2px; text-align: right' "));
	 			/*상근 (2) 성과급 화면 변화*/
	 			if(data.getSalaryJobClassDesc().equals("상근(2)")){
	 				row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryTotalRealTimeSalary() * 7 / 10)).setAttribute(" style='padding: 7px 2px 7px 2px; text-align: right' "));
	 				row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryTotalRealTimeSalary() * 3 / 10)).setAttribute(" style='padding: 7px 2px 7px 2px; text-align: right' "));
	 			}else{
	 				row.addCell(new Cell("-"));
	 				row.addCell(new Cell("-"));
	 			}
	 			row.addCell(new Cell("[" + data.getSalaryProjectCode() + "] " + data.getSalaryProjectName()).setAttribute(" style='padding: 7px 2px 7px 2px; "+isExceedStyle+" overflow: hidden; text-overflow: ellipsis; white-space: nowrap; text-align: left'"));
	 			row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryRealTimeSalaryEachProject())).setAttribute("  style='padding: 7px 2px 7px 2px; text-align: right'  "));
	 			row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryCumulativeRealTimeSalary())).setAttribute("  style='padding: 7px 2px 7px 2px; text-align: right'  "));
	 			/* row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryBudgetEachProject())).setAttribute("  style='padding: 7px 2px 7px 2px; text-align: right'  ")); */
	 			row.addCell(new Cell(data.getSalaryApprovalType()).setAttribute(" style='text-align:center; ' "));
				tableGrid.addRow(row); 
			}
		}else{
			row = new Row(); 
			row.addCell(new Cell("검색 결과가 존재하지 않습니다. ").setAttribute(" align='center' colspan='12' " ));
			tableGrid.addRow(row); 
		}
		tableGrid.tableCheck();
	
%>

<script type="text/javascript">
	function saveToExcel(){
		document.location = '/action/ProjectARExpenseSanctionAction.do?mode=saveToExcelAll&year='+document.generalSanctionForm.year.value+'&month='+document.generalSanctionForm.month.value;
	}
</script>
</head> 

<body> 

<form id="generalSanctionForm" name="generalSanctionForm" method="post" enctype="multipart/form-data">
		<div id="hiddneValue" style="display: none;">
			<input type="text" id="year" name="year" value="<c:out value="${year}" />" />
			<input type="text" id="month" name="month" value="<c:out value="${month}" />" />
		</div>
		
		<!-- location -->
		<div class="location">
			<p class="menu_title">성과급 품의 집계함</p>
			<ul>
				<li class="home">HOME</li>
				<li>전자결재</li>
				<li>성과급 품의 집계함</li>
			</ul>
		</div>
		<!-- // location -->
		
		<div class="contents sub"><!-- 서브 페이지 .sub -->
			<div class="fixed_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1"><i class="mdi mdi-file-document-outline"></i>성과급 품의 전체 내역</p>	
					</div>
					<!-- <div class="btn_area">
						<button type="button" class="btn line btn_grey" onclick="location.href='javascript:;'"><i class="mdi mdi-printer"></i>인쇄</button>
						<button type="button" class="btn line btn_grey" onclick="location.href='javascript:;'"><i class="mdi mdi-backburger"></i>목록</button>
					</div> -->
				</div>	
				
				<div class="fixed_contents sc">
					<!-- 기안 내용 -->
					<div class="board_box">
						<div class="title_both">
							<div class="h1_area">
								<p class="h1"><c:out value="${year}"/>년 <c:out value="${month}"/>월&nbsp성과급 전체 내역</p>
							</div>
							<div class="btn_area">
								<button type="button" class="btn line btn_excel" onclick="saveToExcel()"><i class="mdi mdi-microsoft-excel"></i>Excel 다운로드</button>
							</div>
						</div>
						<div class="board_contents">
							<table class="tbl-edit td-c">
								<colgroup>
									<col style="width: 9%"/>
									<col style="width: 9%"/>
									<col style="width: 10%" />
									<col style="width: 9%" />
									<col style="width: 9%" />
									<col style="width: *" />
									<col style="width: 9%" />
									<col style="width: 9%" />
									<col style="width: 9%" />
								</colgroup>
									<tr>
										<th>구분</th>
										<th>이름</th>
										<th>총 금액</th>
										<th>70% 금액</th>
										<th>30% 금액</th>
										<th>투입 프로젝트</th>
										<th>프로젝트 소계</th>
										<th>지급성과급 누계</th>
										<th>유형</th>
									</tr>
								<tbody>
									<c:forEach var="rs" items="${expenselist}">
										<tr>
											<td><c:out value="${rs.salaryJobClassDesc }" /></td>
											<td><c:out value="${rs.salaryName }" /></td>
											<td style="text-align:right;"><fmt:formatNumber value="${rs.salaryTotalRealTimeSalary}" type="number"/></td>
											<c:choose>
												<c:when test="${rs.salaryJobClassDesc == '상근(2)'}">
													<td style="text-align:right;"><fmt:formatNumber value="${rs.salaryTotalRealTimeSalary * (7/10) }" type="number"/></td>
													<td style="text-align:right;"><fmt:formatNumber value="${rs.salaryTotalRealTimeSalary * (3/10) }" type="number"/></td>
												</c:when>
												<c:otherwise>
													<td style="text-align:center;">-</td>
													<td style="text-align:center;">-</td>
												</c:otherwise>
											</c:choose>
											<td style="text-align:left;">[<c:out value="${rs.salaryProjectCode}" />]<c:out value="${rs.salaryProjectName }" /></td>
											<td style="text-align:right;"><fmt:formatNumber value="${rs.salaryRealTimeSalaryEachProject }" type="number"/></td>
											<td style="text-align:right;"><fmt:formatNumber value="${rs.salaryCumulativeRealTimeSalary }" type="number"/></td>
											<td style="text-align:center;"><c:out value="${rs.salaryApprovalType }" /></td>
										</tr>
									</c:forEach>
									<c:if test="${empty expenselist }">
										<td colspan="9">성과급 내역이 없습니다.</td>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>

					<div style="display: none;">
						<table class="listTable" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
							<c:forEach var="rs" items="${expenselist}">
								<tr>
									<td>
										<input type="hidden" name="salaryType"						value="<c:out value="${rs.salaryType}"/>" >
										<input type="hidden" name="salaryDeptType"					value="<c:out value="${rs.salaryDeptType}"/>" >
										<input type="hidden" name="salaryJobClass"					value="<c:out value="${rs.salaryJobClass}"/>" >
										<input type="hidden" name="salaryJobClassDesc"				value="<c:out value="${rs.salaryJobClassDesc}"/>" >
										<input type="hidden" name="salaryJobClassErp"				value="<c:out value="${rs.salaryJobClassErp}"/>" >
										<input type="hidden" name="salaryYear"						value="<c:out value="${rs.salaryYear}"/>" >
										<input type="hidden" name="salaryMonth"						value="<c:out value="${rs.salaryMonth}"/>" >
										<input type="hidden" name="salaryName"						value="<c:out value="${rs.salaryName}"/>" >
										<input type="hidden" name="salarySsn"						value="<c:out value="${rs.salarySsn}"/>" >
										<input type="hidden" name="salaryTotalRealTimeSalary"		value="<c:out value="${rs.salaryTotalRealTimeSalary}"/>" >
										<input type="hidden" name="salaryProjectCode"				value="<c:out value="${rs.salaryProjectCode}"/>" >
										<input type="hidden" name="salaryProjectName"				value="<c:out value="${rs.salaryProjectName}"/>" >
										<input type="hidden" name="salaryPreportCount"				value="<c:out value="${rs.salaryPreportCount}"/>" >
										<input type="hidden" name="salaryRealTimeSalaryEachProject"	value="<c:out value="${rs.salaryRealTimeSalaryEachProject}"/>" >
										<input type="hidden" name="salaryDept"						value="<c:out value="${rs.salaryDept}"/>" >
										<input type="hidden" name="salarySeq"						value="<c:out value="${rs.salarySeq}"/>" >
										<input type="hidden" name="salaryResRate"					value="<c:out value="${rs.salaryResRate}"/>" >
										<input type="hidden" name="salaryCost"						value="<c:out value="${rs.salaryCost}"/>" >
										<input type="hidden" name="salaryMMValueRatio"				value="<c:out value="${rs.salaryMMValueRatio}"/>" >
									</td>
								</tr>										
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>	
	</form>						
</body>
</html>
