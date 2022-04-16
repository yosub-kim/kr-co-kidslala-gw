<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
<%@page import="java.util.List"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="kr.co.kmac.pms.project.statistics.data.ExpenseContributionCostRealTimeResult"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function doSearch() {
	document.form.pageNo.value = '1';
	document.form.target = "";		
	document.form.action = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseContributionCostRealTimeResult";
	document.form.submit();
}
function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseContributionCostRealTimeResult";
	document.form.submit();
}
</script> 
</head>  
<%
	//리스트 오브젝트 
	List<ExpenseContributionCostRealTimeResult> list = ((ValueList)request.getAttribute("list")).getList();
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" width='765' class='listTable' cellpadding='0' cellspacing='0'  ");
	tableGrid.setHeadAnnex(true); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합

	//헤더 정의 
	Row headerRow = new Row();
	headerRow.addCell(new Cell("소속").setAttribute(" align='center'  width='17%' "));
	headerRow.addCell(new Cell("이름").setAttribute(" align='center'  width='10%' "));
	headerRow.addCell(new Cell("프로젝트명").setAttribute(" align='center'  width='33%' "));
	headerRow.addCell(new Cell("투입일").setAttribute(" align='center'  width='10%' "));
	headerRow.addCell(new Cell("현재 성과급 합계(원)").setAttribute(" align='center'  width='15%' "));
	headerRow.addCell(new Cell("총 기여금액(원)").setAttribute(" align='center'  width='15%' "));
	tableGrid.addHeadRow(headerRow);

	Row row = null;
	if(list != null && list.size() > 0){
		for(ExpenseContributionCostRealTimeResult  realTimeResult : list){
			row = new Row();  
			row.addCell(new Cell(realTimeResult.getDept()).setAttribute(" align='center'   "));
			row.addCell(new Cell(realTimeResult.getName()).setAttribute(" align='center'   "));
			row.addCell(new Cell(realTimeResult.getProjectName()).setAttribute(" align='center'   "));
			row.addCell(new Cell(realTimeResult.getRealWorkCount()).setAttribute(" align='center'   "));
			row.addCell(new Cell(realTimeResult.getRealTimeSalary()).setAttribute(" align='right'  "));
			row.addCell(new Cell(realTimeResult.getTotalContributionCost()).setAttribute(" align='right'  "));
			tableGrid.addRow(row); 
		}	
	}else{
		row = new Row(); 
		row.addCell(new Cell("검색 결과가 존재하지 않습니다 .").setAttribute(" align='center'  colspan='6' " ));
		tableGrid.addRow(row); 
	}
	tableGrid.tableCheck(); 
	
%>

<body leftmargin="15" topmargin="0" style="overflow-x:hidden;overflow-y:hidden">
<%@ include file="/common/include/includeBodyTop.jsp"%>
	<form name="form" method="post">
		<div style='display: none;'>
			<input type="hidden" name='pageNo'> 
		</div>

		<table width="765" cellpadding="0" cellspacing="0">
			<!-- SPACER -->
			<tr>
				<td width="765" height="8">&nbsp;</td>
			</tr>
			
			
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12">
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="실시간 성과급 현황" />
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
									<table border="0" width="100%" cellpadding="0" cellspacing="0">
										<colgroup> 
											<col  style="padding-left: 3px; padding-right: 3px; text-align: right;"  width="85px">
											<col style="padding-left: 3px; padding-right: 3px; text-align: left;"   width="140px">
											<col  style="padding-left: 3px; padding-right: 3px; text-align: right;"  width="70px">
											<col style="padding-left: 3px; padding-right: 3px; text-align: left;"   width="100px">
											<col  style="padding-left: 3px; padding-right: 3px; text-align: right;"  width="70px"> 
											<col style="padding-left: 3px; padding-right: 3px; text-align: left;"   width="*">
										</colgroup>
										<tr>
											<td>PU : </td>
											<td>
												<org:divList enabled="1" depth="1" divType="J" isSelectBox="Y" all="Y" attribute=" name='deptCode' class='selectbox' style='width:99%;' " selectedInfo="${deptCode}"></org:divList>
											</td>
											<td>소속 구분 : </td>
											<td>
												<select name='jobClass' class='selectbox' style="width: 100*">
													<option value="">-- 선택 --</option>
													<option value="1" <c:if test="${jobClass=='B'}">selected</c:if>>상근</option>
													<option value="2" <c:if test="${jobClass=='J'}">selected</c:if>>상임</option>
													<option value="3" <c:if test="${jobClass=='C'}">selected</c:if>>엑스퍼트</option>
												</select>
											</td>
											<td>기간 : </td>
											<td>
												<date:year beforeYears="4" afterYears="4" attribute=" name=year class=selectbox style='width: 70px' " selectedInfo="${year}" />년&nbsp;
												<date:month attribute=" name=month class=selectbox style='width: 60px' " selectedInfo="${month}" />월&nbsp;
											</td>
										</tr>
									</table>
					<%@ include file="/common/include/searchBox_Footer.jsp"%>
				</td>
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
							<td height="2" align="left">
								<!-- 페이지 정보, 버튼 -->			
								<table width="765" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td height="18" width="*"><div><img src="/images/sub/line3Blit.gif" alt=""> <span style="font-weight: bold; color: #e74c3a;"> <c:out value="${list.valueListInfo.totalNumberOfEntries}"/> </span><span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/> </span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span></div></td>
									</tr>
								</table>
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
														10, 
														<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
														10)
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
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>
</html>