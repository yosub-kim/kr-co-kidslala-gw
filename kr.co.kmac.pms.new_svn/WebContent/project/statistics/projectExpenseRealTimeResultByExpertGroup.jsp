<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.kmac.pms.project.statistics.data.ExpenseRealTimeResult"%>
<%@page import="net.mlw.vlh.ValueList"%>

<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>COO 소속 상임 컨설턴트의 월 전체 성과급</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function goDetailPage(ssn) {
	document.form.target = "";
	document.form.action = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResultDetail&ssn="+ssn;
	document.form.submit();	
}
function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResultByExpertGroup";
	document.form.submit();
}
function doSearch() {
	document.form.pageNo.value = '1';
	document.form.target = "";		
	document.form.action = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResultByExpertGroup";
	document.form.submit();
}
</script> 
</head>  
<%
	//리스트 오브젝트 
	List<ExpenseRealTimeResult> list = ((ValueList)request.getAttribute("list")).getList();
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" width='100%' class='listTable' cellpadding='0' cellspacing='0'  ");
	tableGrid.setHeadAnnex(true); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합

	//헤더 정의 
	Row headerRow = new Row();
	headerRow.addCell(new Cell("소속").setAttribute(" align='center' width='20%' "));
	headerRow.addCell(new Cell("이름").setAttribute(" align='center' width='20%' "));
	headerRow.addCell(new Cell("직위/직책").setAttribute(" align='center' width='20%' "));
	headerRow.addCell(new Cell("참여 프로젝트 수").setAttribute(" align='center' width='20%' "));
	headerRow.addCell(new Cell("현재 성과급 합계(원)").setAttribute(" align='center' width='20%' "));
	tableGrid.addHeadRow(headerRow);

	Row row = null;
	if(list != null && list.size() > 0){
		for(ExpenseRealTimeResult realTimeResult : list){
			row = new Row();
			row.attribute = "onmouseover='row_over(this)' onmouseout='row_out(this)' style='cursor: hand;'";
			row.addCell(new Cell(realTimeResult.getDeptName()).setAttribute(" align='center'  onclick='goDetailPage(\""+realTimeResult.getSsn()+"\")'  "));
			row.addCell(new Cell(realTimeResult.getName()).setAttribute(" align='center'  onclick='goDetailPage(\""+realTimeResult.getSsn()+"\")'  "));
			row.addCell(new Cell(realTimeResult.getRole()).setAttribute(" align='center'  onclick='goDetailPage(\""+realTimeResult.getSsn()+"\")'  "));
			row.addCell(new Cell(realTimeResult.getInvolvedPrjCnt()).setAttribute(" align='center'  onclick='goDetailPage(\""+realTimeResult.getSsn()+"\")'  "));
			row.addCell(new Cell(StringUtil.longt2Money(realTimeResult.getRealTimeSalary())).setAttribute(" align='right'   onclick='goDetailPage(\""+realTimeResult.getSsn()+"\")' "));
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
		</div>
	
		<table width="100%" cellpadding="0" cellspacing="0">
			<!-- 타이틀 영역 -->
			<tr>
				<td>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="COO 소속 상임 컨설턴트의 월 전체 성과급" />
					</jsp:include>
				</td>
			</tr>
			<tr>
				<td height="30">※ 본 내역은 해당 COO 소속 상임 컨설턴트의 해당 월 예상 성과급 전체를 실시간으로 계산한 것이므로 실 지급된 성과급과 차이가 있을 수 있음.</td>
			</tr>

			<!-- 검색 영역 -->
			<tr>
				<td>
					<%@ include file="/common/include/searchBox_Header.jsp"%>
						<table border="0" width="100%"  cellpadding="0" cellspacing="0" height="26">
							<tr>
								<td class="searchTitle_center" width="80">기간</td>
								<td class="searchField_left">
									<date:year beforeYears="4" afterYears="4" attribute=" name=year class=selectbox style='width: 70px' " selectedInfo="${year}" />년&nbsp;
									<date:month attribute=" name=month class=selectbox style='width: 60px' " selectedInfo="${month}" />월&nbsp;
								</td>
							</tr>
						</table>
					<c:if test="${readOnly!='ON'}">
					<%@ include file="/common/include/searchBox_Footer.jsp"%>
					</c:if>
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
							<td align="left">
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