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

function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/ProjectExpenseAction.do?mode=projectExpenseHistoryManagement2";
	document.form.submit();
}

function moveNext(){
	var year = document.form.year.value;
	var month = document.form.month.value;
	if (month == "12") {
		document.form.year.value = parseInt(year) + 1;
		document.form.month.value = 1;
	}
	else {
		document.form.month.value = parseInt(month) + 1;
	}
	document.form.submit();
}

function movePrevious(){
	var year = document.form.year.value;
	var month = document.form.month.value;
	if (month == "1") {
		document.form.year.value = parseInt(year) - 1;
		document.form.month.value = 12;
	}
	else {
		document.form.month.value = parseInt(month) - 1;
	}
	document.form.submit();
}

</script> 
</head>  
<%
	//리스트 오브젝트 
	List<ExpenseRealTimeResult> list = ((ValueList)request.getAttribute("list")).getList();
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" width='765' class='listTable' cellpadding='0' cellspacing='0'  ");
	tableGrid.setHeadAnnex(true); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합

	//헤더 정의 
	Row headerRow = new Row();
	headerRow.addCell(new Cell("소속").setAttribute(" style='padding: 7px 1px 7px 1px' width='12%' "));
	headerRow.addCell(new Cell("이름").setAttribute(" style='padding: 7px 1px 7px 1px' width='13%' "));
	headerRow.addCell(new Cell("직위").setAttribute(" style='padding: 7px 1px 7px 1px' width='13%' "));
	headerRow.addCell(new Cell("성과급 합계").setAttribute(" style='padding: 7px 1px 7px 1px' width='15%' "));
	/* headerRow.addCell(new Cell("지도율").setAttribute(" style='padding: 7px 1px 7px 1px' width='10%' ")); */
	headerRow.addCell(new Cell("프로젝트명").setAttribute(" style='padding: 7px 1px 7px 1px' width='32%' "));
	headerRow.addCell(new Cell("성과급").setAttribute(" style='padding: 7px 1px 7px 1px' width='15%' "));
	tableGrid.addHeadRow(headerRow);

	Row row = null;
	if(list != null && list.size() > 0){
		for(ExpenseRealTimeResult realTimeResult : list){
			row = new Row();
			//row.attribute = "onmouseover='row_over(this)' onmouseout='row_out(this)'";
			row.setAttribute(" onmouseover='row_over(this)' onmouseout='row_out(this)' onclick='goDetailPage(\""+realTimeResult.getProjectCode()+ "\",\""+realTimeResult.getSsn()+"\")'");
			
			row.addCell(new Cell(realTimeResult.getDeptName()).setAttribute(" align='center' style='cursor:hand;' "));
			row.addCell(new Cell(realTimeResult.getName()).setAttribute(" align='center' style='cursor:hand;'   "));
			row.addCell(new Cell(realTimeResult.getRole()).setAttribute(" align='center' style='cursor:hand;'   "));
			row.addCell(new Cell(StringUtil.longt2Money(realTimeResult.getSalarySum2())).setAttribute(" align='right' style='cursor:hand;' "));
			/* row.addCell(new Cell(realTimeResult.getInvolvedPrjCnt()).setAttribute(" align='center' style='cursor:hand;'   ")); */
			row.addCell(new Cell(realTimeResult.getProjectName()).setAttribute(" align='left' style='cursor:hand; "+(realTimeResult.getIsExceed()!= null && realTimeResult.getIsExceed().equals("Y") ? "background-color: #FFDFFF;" : "")+" ' "));
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

<body>
	<form name="form" method="post">
		<div style='display: none;'>
			<input type="hidden" name="pageNo">
			<input type="hidden" name="role">
		</div>
	
		<table width="765" cellpadding="0" cellspacing="0">
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12">
					<h3 class="mainTitle"><c:out value="${year}" />년  <c:out value="${month}" />월 컨설턴트별 성과급 내역</h3>
				</td>

			<!-- SPACER -->						
			<tr>
				<td height="30">※ 본 내역은 성과급 품의 내역을 기준으로 표시되는 데이터로써 실 지급된 성과급과 차이가 있을 수 있음.</td>
			</tr>
						

			<!-- 본문 리스트 시작 -->			
			<tr>
				<td align="left" valign="top">
					<table width="765" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35">
								<!-- 페이지 정보, 버튼 -->			
								<div class="btNbox pb15">
									<div class="btNlefTdiv">				
										<img src="/images/sub/line3Blit.gif" alt="">
										<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
										<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
									</div>
									<div class="btNrighTdiv">
										<img src="/images/calendar/leftArrow.gif" border="0" title="이전 달 보기" align="absmiddle" style="cursor:hand;" onclick="movePrevious()"/>
										<date:year beforeYears="4" afterYears="0" attribute=" name='year' class='selectboxPopup' style='width:60px' onchange='this.form.submit();'" selectedInfo="${year}" />년&nbsp;
										<select name="month" class="selectboxPopup" style="width:40px" onchange="this.form.submit();">
											<c:forEach var="i" begin="1" end="12">
												<option value="<c:out value="${i}"/>" <c:if test="${month == i}">selected</c:if>><c:out value="${i}"/></option>월
											</c:forEach>
										</select>월
										<img src="/images/calendar/rightArrow.gif" border="0" title="다음 달 보기" align="absmiddle" style="cursor:hand;" onclick="moveNext()"/>
									</div>
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
							<td align="left">
								<table width="765" border="0" cellspacing="0" cellpadding="0">
									<tr height='30'>
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
					</table>
				</td>
			</tr>
		</table>
	
	</form>
</body>
</html>