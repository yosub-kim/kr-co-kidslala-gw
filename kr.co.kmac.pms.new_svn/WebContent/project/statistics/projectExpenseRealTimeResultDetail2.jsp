<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
<%@page import="java.util.List"%>
<%@page import="net.mlw.vlh.ValueList"%>

<%@page import="kr.co.kmac.pms.project.statistics.data.ExpenseRealTimeResultDetail"%>
<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@page import="kr.co.kmac.pms.expertpool.data.ExpertPool"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>개인별 실시간 성과급 예상 내역</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<%
	ExpertPool expertPool = (ExpertPool)request.getAttribute("expertPool");
%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResultDetail2&ssn=<c:out value="${ssn}"/>";
	document.form.submit();
}
 
</script> 
</head>  
<%
	
	
	//리스트 오브젝트 
	List<ExpenseRealTimeResultDetail> list = ((ValueList)request.getAttribute("list")).getList();
	String totalSalary = "";
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" class='listTable' style='table-layout: fixed'  ");
	tableGrid.setHeadAnnex(true); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합

	//헤더 정의 
	Row headerRow = new Row();
	headerRow.addCell(new Cell("프로젝트명").setAttribute(" width='*' "));
	headerRow.addCell(new Cell("프로젝트<br>코드").setAttribute(" width='8%' "));
	headerRow.addCell(new Cell("성과급<br>소계").setAttribute(" width='12%' "));
	headerRow.addCell(new Cell("연월").setAttribute(" width='12%' "));
	headerRow.addCell(new Cell("성과급").setAttribute(" width='10%' "));
	headerRow.addCell(new Cell("품의<br>진행여부").setAttribute(" width='10%' "));
	tableGrid.addHeadRow(headerRow);

	Row row = null;
	if(list != null && list.size() > 0){
		for(ExpenseRealTimeResultDetail realTimeResult : list){
			row = new Row();
			row.setAttribute(" onmouseover='row_over(this)' onmouseout='row_out(this)' ");
			row.addCell(new Cell(realTimeResult.getProjectName()).setAttribute(" align='left'  "));
			row.addCell(new Cell(realTimeResult.getProjectCode()).setAttribute(" align='center'  "));
			//row.addCell(new Cell(realTimeResult.getEachSalary()).setAttribute(" align='center'"));
			row.addCell(new Cell(StringUtil.longt2Money(realTimeResult.getRealTimeSalary())).setAttribute(" align='right'  "));
			row.addCell(new Cell(realTimeResult.getAssignDate()).setAttribute(" align='center'  "));
			row.addCell(new Cell(StringUtil.longt2Money(Long.parseLong(realTimeResult.getEachSalary()))).setAttribute(" align='right'  "));
			row.addCell(new Cell(realTimeResult.getIsSanction()).setAttribute(" align='center'  ")); 
			totalSalary = realTimeResult.getTotalRealTimeSalary();
			tableGrid.addRow(row); 
		}	
		row = new Row();    
		row.addCell(new Cell("현재 성과급 합계").setAttribute(" bgcolor='#D9D5CC' align='center' colspan='5' "));
		//row.addCell(new Cell(totalSalary).setAttribute(" align='center'  "));
		row.addCell(new Cell(StringUtil.double2Money(Double.parseDouble(totalSalary))).setAttribute(" align='center'  "));
		tableGrid.addRow(row); 
	}else{
		row = new Row(); 
		row.addCell(new Cell("성과급 내역이 없습니다.").setAttribute(" align='center' colspan='9' " ));
		tableGrid.addRow(row); 
	}
	tableGrid.tableCheck(); 
	
%>

<body>
	<form name="form" method="post">
		<div style='display: none;'>
			<input type="hidden" name="pageNo"> 
			<input type="hidden" name="year" value="<c:out value="${year}"/>">  
			<input type="hidden" name="month"  value="<c:out value="${month}"/>">
			<input type="hidden" name="role" value="<c:out value="${role}"/>">
			<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>">
			<input type="hidden" name="divCode" value="<c:out value="${divCode}"/>">
		</div>
		<table width="765" cellpadding="0" cellspacing="0">
			<%
				String title = "실시간 성과급 내역 [" + expertPool.getName() + "]";
			%>
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12">
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="<%=title  %>" />
						<jsp:param name="backButtonYN" value="Y" />
					</jsp:include>
				</td>
			</tr>

			<!-- 본문 리스트 시작 -->			
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="30">
								<img src="/images/sub/line3Blit.gif" alt="">
								<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
								<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
							</td>
						</tr>
						<tr>
							<td>
								<% out.println(tableGrid.getTotalHtml());%>
							</td>
						</tr>
						<%-- 페이징 영역 시작--%>
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
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