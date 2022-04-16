<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.kmac.pms.project.statistics.data.ExpenseRealTimeResult"%>
<%@page import="net.mlw.vlh.ValueList"%>

<%@page import="kr.co.kmac.pms.common.util.StringUtil"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function goDetailPage(projectCode, ssn) {
	var today = new Date();
	
	document.form.target = "";
	document.form.year.value = today.getFullYear();
	document.form.month.value = today.getMonth() + 1;
	document.form.action = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResultDetail&valueYearMonth=Y&projectCode="+projectCode+"&ssn="+ssn+"&role=<c:out value="${role}"/>";
	document.form.submit();
	return;	
}
function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/ProjectExpenseAction.do?mode=realTimeProjectExpenseManagement";
	document.form.submit();
}
function doSearch() {
	document.form.pageNo.value = '1';
	document.form.target = "";		
	document.form.action = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResult";
	document.form.submit();
}
function goHistoryPage() {
	var today = new Date();
	var year = today.getFullYear();
	var month = today.getMonth() + 1;
	
	// 이전 월 값으로 자동 지정
	if (month == 1) {
		year = year - 1;
		month = 12;
	} else {
		month = month;
	}
	
	document.form.target = "";
	document.form.year.value = year;
	document.form.month.value = month;
	document.form.action = "/action/ProjectExpenseAction.do?mode=projectExpenseHistoryManagement";
	document.form.submit();
	return;	
}
function goHistoryPage_20() {
	var today = new Date();
	var year = today.getFullYear();
	var month = today.getMonth() + 1;
	
	// 이전 월 값으로 자동 지정
	if (month == 1) {
		year = year - 1;
		month = 12;
	} else {
		month = month - 1;
	}
	
	document.form.target = "";
	document.form.year.value = year;
	document.form.month.value = month;
	document.form.action = "/action/ProjectExpenseAction.do?mode=projectExpenseHistoryManagement_20";
	document.form.submit();
	return;	
}

</script> 
</head>  
<%
	//리스트 오브젝트 
	List<ExpenseRealTimeResult> list = ((ValueList)request.getAttribute("list")).getList();
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" class='listTable' cellpadding='0' cellspacing='0'  ");
	tableGrid.setHeadAnnex(true); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합

	//헤더 정의 
	Row headerRow = new Row();
	headerRow.addCell(new Cell("프로젝트명").setAttribute(" style='padding: 7px 1px 7px 1px' width='32%' "));
	headerRow.addCell(new Cell("소속").setAttribute(" style='padding: 7px 1px 7px 1px' width='12%' "));
	headerRow.addCell(new Cell("이름").setAttribute(" style='padding: 7px 1px 7px 1px' width='13%' "));
	headerRow.addCell(new Cell("직위").setAttribute(" style='padding: 7px 1px 7px 1px' width='13%' "));
	/* headerRow.addCell(new Cell("지도율").setAttribute(" style='padding: 7px 1px 7px 1px' width='10%' ")); */
	headerRow.addCell(new Cell("예상 성과급 합계(원)").setAttribute(" style='padding: 7px 1px 7px 1px' width='25%' "));
	tableGrid.addHeadRow(headerRow);

	Row row = null;
	if(list != null && list.size() > 0){
		for(ExpenseRealTimeResult realTimeResult : list){
			row = new Row();
			//row.attribute = "onmouseover='row_over(this)' onmouseout='row_out(this)'";
			row.setAttribute(" onmouseover='row_over(this)' onmouseout='row_out(this)' onclick='goDetailPage(\""+realTimeResult.getProjectCode()+ "\",\""+realTimeResult.getSsn()+"\")'");
			
			row.addCell(new Cell(realTimeResult.getProjectName()).setAttribute(" align='left' style='cursor:hand; "+(realTimeResult.getIsExceed()!= null && realTimeResult.getIsExceed().equals("Y") ? "background-color: #FFDFFF;" : "")+" ' "));
			row.addCell(new Cell(realTimeResult.getDeptName()).setAttribute(" align='center' style='cursor:hand;' "));
			row.addCell(new Cell(realTimeResult.getName()).setAttribute(" align='center' style='cursor:hand;'   "));
			row.addCell(new Cell(realTimeResult.getRole()).setAttribute(" align='center' style='cursor:hand;'   "));
			/* row.addCell(new Cell(realTimeResult.getInvolvedPrjCnt()).setAttribute(" align='center' style='cursor:hand;'   ")); */
			row.addCell(new Cell(StringUtil.longt2Money(realTimeResult.getRealTimeSalary())).setAttribute(" align='right' style='cursor:hand;'   "));
			tableGrid.addRow(row); 
		}	
	}else{
		row = new Row(); 
		row.addCell(new Cell(" 검색 결과가 존재하지 않습니다 . ").setAttribute(" align='center'  colspan='12' " ));
		tableGrid.addRow(row); 
	}
	tableGrid.tableCheck(); 
	
%>

<!-- 20% 실시간 성과급 확인 -->
<%
	//리스트 오브젝트 
	List<ExpenseRealTimeResult> list2 = ((ValueList)request.getAttribute("list2")).getList();
	//테이블 그리드 생성
	TableGrid tableGrid2 = new TableGrid();
	tableGrid2.setAttribute(" class='listTable' cellpadding='0' cellspacing='0'  ");
	tableGrid2.setHeadAnnex(true); // true 이면 Header Cell 병합
	tableGrid2.setAnnex(true); // true 이면 Cell 병합

	//헤더 정의 
	Row headerRow2 = new Row();
	headerRow2.addCell(new Cell("프로젝트명").setAttribute(" style='padding: 7px 1px 7px 1px' width='32%' "));
	headerRow2.addCell(new Cell("소속").setAttribute(" style='padding: 7px 1px 7px 1px' width='12%' "));
	headerRow2.addCell(new Cell("이름").setAttribute(" style='padding: 7px 1px 7px 1px' width='13%' "));
	headerRow2.addCell(new Cell("직위").setAttribute(" style='padding: 7px 1px 7px 1px' width='13%' "));
	/* headerRow.addCell(new Cell("지도율").setAttribute(" style='padding: 7px 1px 7px 1px' width='10%' ")); */
	headerRow2.addCell(new Cell("예상 성과급 합계(원)").setAttribute(" style='padding: 7px 1px 7px 1px' width='25%' "));
	tableGrid2.addHeadRow(headerRow2);

	Row row2 = null;
	if(list2 != null && list2.size() > 0){
		for(ExpenseRealTimeResult realTimeResult : list2){
			row2 = new Row();
			//row.attribute = "onmouseover='row_over(this)' onmouseout='row_out(this)'";
			row2.setAttribute(" onmouseover='row_over(this)' onmouseout='row_out(this)' onclick='goDetailPage(\""+realTimeResult.getProjectCode()+ "\",\""+realTimeResult.getSsn()+"\")'");
			
			row2.addCell(new Cell(realTimeResult.getProjectName()).setAttribute(" align='left' style='cursor:hand; "+(realTimeResult.getIsExceed()!= null && realTimeResult.getIsExceed().equals("Y") ? "background-color: #FFDFFF;" : "")+" ' "));
			row2.addCell(new Cell(realTimeResult.getDeptName()).setAttribute(" align='center' style='cursor:hand;' "));
			row2.addCell(new Cell(realTimeResult.getName()).setAttribute(" align='center' style='cursor:hand;'   "));
			row2.addCell(new Cell(realTimeResult.getRole()).setAttribute(" align='center' style='cursor:hand;'   "));
			/* row.addCell(new Cell(realTimeResult.getInvolvedPrjCnt()).setAttribute(" align='center' style='cursor:hand;'   ")); */
			row2.addCell(new Cell(StringUtil.longt2Money(realTimeResult.getRealTimeSalary())).setAttribute(" align='right' style='cursor:hand;'   "));
			tableGrid2.addRow(row2); 
		}	
	}else{
		row2 = new Row(); 
		row2.addCell(new Cell(" 검색 결과가 존재하지 않습니다 . ").setAttribute(" align='center'  colspan='12' " ));
		tableGrid2.addRow(row2); 
	}
	tableGrid2.tableCheck(); 
%>

<body>
	<form name="form" method="post">
		<div style='display: none;'>
			<input type="hidden" name="pageNo">
			<input type="hidden" name="role">
			<input type="hidden" name="year">
			<input type="hidden" name="month">
		</div>
	
		<table width="100%" cellpadding="0" cellspacing="0">
			<!-- 타이틀 영역 -->
			<tr>
				<td>
					<h3 class="mainTitle"><c:out value="${year}" />년  <c:out value="${month}" />월 프로젝트별 실시간 성과급</h3>
				</td>
			</tr>

			<!-- 본문 리스트 시작 -->			
			<tr>
				<td>
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35">
								<!-- 페이지 정보, 버튼 -->			
								<div class="btNbox pb15">
								<h4 class="subTitle mt15 mb5">1) 실시간 80%  성과급 </h4>
									<div class="btNlefTdiv">				
										<img src="/images/sub/line3Blit.gif" alt="">
										<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
										<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
									</div>
									<div class="btNrighTdiv">
										<a class="btNa0a0a0 txt4btn" href="javascript:goHistoryPage()">이전 성과급 내역 보기</a>
									</div>
									</br>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<% out.println(tableGrid.getTotalHtml());%>
							</td>
						</tr>
						<%-- 페이징 영역 시작--%>
						<tr>
							<td align="center">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr height="30">
										<td width="100%" align="center">
											<SCRIPT LANGUAGE="JavaScript"> 
												document.write( makePageHtml( 
														<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
														15, 
														<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
														15)
												) ;
											</SCRIPT>
										</td>
									</tr>
								</table>									
							</td>
						</tr>
						<%-- 페이징 영역 끝--%>
						
						<!-- 20% 실시간 성과급 확인 -->
						<tr>
							<td height="35">
								<!-- 페이지 정보, 버튼 -->			
								<div class="btNbox pb15">
								<h4 class="subTitle mt15 mb5">2) 실시간 20% 성과급 </h4>
									<div class="btNlefTdiv">				
										<img src="/images/sub/line3Blit.gif" alt="">
										<span class="bold colore74c3a"><c:out value="${list2.valueListInfo.totalNumberOfEntries}"/></span>
										<span>Total - Page(<span><c:out value="${list2.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list2.valueListInfo.totalNumberOfPages}"/></span>)</span>
									</div>
									<div class="btNrighTdiv">
										<a class="btNa0a0a0 txt4btn" href="javascript:goHistoryPage_20()">이전 성과급 내역 보기</a>
									</div>
									</br>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<% out.println(tableGrid2.getTotalHtml());%>
							</td>
						</tr>
						<%-- 페이징 영역 시작--%>
						<tr>
							<td align="center">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr height="30">
										<td width="100%" align="center">
											<SCRIPT LANGUAGE="JavaScript"> 
												document.write( makePageHtml( 
														<c:out value="${list2.valueListInfo.pagingPage}" default="1"/>, 
														15, 
														<c:out value="${list2.valueListInfo.totalNumberOfEntries}" default="0"/> , 
														15)
												) ;
											</SCRIPT>
										</td>
									</tr>
								</table>									
							</td>
						</tr>
						<%-- 페이징 영역 끝--%>
					</table>
				</td>
			</tr> 
		</table>
	
	</form>
</body>
</html>