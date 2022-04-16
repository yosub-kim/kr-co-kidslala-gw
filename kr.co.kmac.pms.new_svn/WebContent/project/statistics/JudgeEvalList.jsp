<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="java.util.List"%>
<%@page import="kr.co.kmac.pms.project.statistics.data.JudgeEvalList"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function doSearch(){
	document.frm.submit();
}


</script>
</head>
<%
	//리스트 오브젝트 
	List<JudgeEvalList> list = ((ValueList)request.getAttribute("result")).getList();
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" cellpadding='0' cellspacing='0' class='listTable' width='765' ");
	tableGrid.setHeadAnnex(false); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합

	//헤더 정의 
	Row headerRow = new Row();
	headerRow.addCell(new Cell("심사위원").setAttribute(" width='10%' "));
	headerRow.addCell(new Cell("소속").setAttribute(" width='20%' "));
	headerRow.addCell(new Cell("년도").setAttribute(" width='7%' "));
	headerRow.addCell(new Cell("진흥행사명").setAttribute(" width='25%' "));
	headerRow.addCell(new Cell("심사기업").setAttribute(" width='23%' "));
	headerRow.addCell(new Cell("심사위원 평가").setAttribute(" width='15%' "));
	tableGrid.addHeadRow(headerRow);

	Row row = null;
	if(list == null || list.size() > 0){
		for(JudgeEvalList evalList : list){
			row = new Row();
			row.attribute = "onmouseover='row_over(this)' onmouseout='row_out(this)'";
			row.addCell(new Cell(evalList.getJudgeName()).setAttribute(" align='center'  "));
			row.addCell(new Cell(evalList.getJudgeCompany()).setAttribute(" align='center'  "));
			row.addCell(new Cell(evalList.getYear()).setAttribute(" align='center'  "));
			row.addCell(new Cell(evalList.getPromotionName()).setAttribute(" align='center'  "));
			row.addCell(new Cell(evalList.getCompanyName()).setAttribute(" align='center'  "));
			row.addCell(new Cell(evalList.getJudgeRate()).setAttribute(" align='center'  "));
			tableGrid.addRow(row); 
		}	
	}else{
		row = new Row(); 
		row.addCell(new Cell("검색 결과가 존재하지 않습니다 .").setAttribute(" align='center' colspan='6' " ));
		tableGrid.addRow(row); 
	}
	tableGrid.tableCheck(); 
	
%>

<body>
<%@ include file="/common/include/includeBodyTop.jsp"%>
<%-- 작업영역 --%>
	<table width="765" cellpadding="0" cellspacing="0">
		<!-- SPACER -->
		<tr>
			<td width="765" height="8">&nbsp;</td>
		</tr>
		
		
		<!-- 타이틀 영역 -->
		<tr>
			<td height="12">
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
					<jsp:param name="title" value="심사위원별 평가 현황" />
				</jsp:include>
			</td>
		</tr>
		<tr>
			<td height="10"></td>
		</tr>			
		
		
		<!-- 검색 영역 -->
		<tr>
			<form name="frm" method="post">
			<td height="21" align="left" valign="top">
				<%@ include file="/common/include/searchBox_Header.jsp"%>
								<table border="0" width="100%" height="26" cellpadding="0" cellspacing="0">
									<colgroup> 
										<col  style="padding-left: 3px; padding-right: 3px; text-align: right;"  width="80px">
										<col  style="padding-left: 3px; padding-right: 3px; text-align: left;"    width="100px">
										<col  style="padding-left: 3px; padding-right: 3px; text-align: right;"  width="80px">
										<col  style="padding-left: 3px; padding-right: 3px; text-align: left;"    width="130px">
										<col  style="padding-left: 3px; padding-right: 3px; text-align: right;"  width="80px"> 
										<col  style="padding-left: 3px; padding-right: 3px; text-align: left;"    width="*">
									</colgroup>
									<tr>
										<td class="searchTitle_center">년 도</td>
										<td class="searchField_center">
											<date:year beforeYears="4" afterYears="4" attribute=" name='year' class='selectbox' style='width:80pt' " selectedInfo="${year}" />
										</td>
										<td class="searchTitle_center">진흥행사명</td>
										<td class="searchField_center">
											<select name="promotionType" class='selectbox' style="width:130pt">
												<option value="">[::전체::]</option>
												<option value="BTB2110001" <c:if test="${promotionType == 'BTB2110001' }">selected</c:if>>한국경영대상</option>
												<option value="BTB2120001" <c:if test="${promotionType == 'BTB2120001' }">selected</c:if>>대한민국고객만족경영대상</option>
												<option value="BTB2130001" <c:if test="${promotionType == 'BTB2130001' }">selected</c:if>>대한민국마케팅대상</option>
												<option value="BTB2150001" <c:if test="${promotionType == 'BTB2150001' }">selected</c:if>>대한민국경영품질대상</option>
												<option value="BTB2160001" <c:if test="${promotionType == 'BTB2160001' }">selected</c:if>>대한민국생산성대상</option>
												<option value="PRODUCT2008031416530218615" <c:if test="${promotionType == 'PRODUCT2008031416530218615' }">selected</c:if>>인재경영대상</option>
											</select>
										</td>
										<td class="searchTitle_center">심사위원명</td>
										<td class="searchField_center">
											<input type="text" name="judgeName" size="12" value="<c:out value="${judgeName}"/>" class="textInput_left">
										</td>
									</tr>									
								</table>
				<%@ include file="/common/include/searchBox_Footer.jsp"%>
			</td>
		</tr>
		</form>
		
		<!-- SPACER -->						
		<tr>
			<td height="18">&nbsp;</td>
		</tr>	
<!-- 검색부분 종료-->					

<!-- 본문시작-->
		<tr>
			<td height='7'></td>
		</tr>
		<tr>
			<td>
				<table width='765' cellpadding="0" cellspacing="0">
					<tr>
						<td><b><font class='SF'><c:out value="${result.valueListInfo.totalNumberOfEntries}"/> Total - Page(<c:out value="${result.valueListInfo.pagingPage}"/>/<c:out value="${result.valueListInfo.totalNumberOfPages}"/>)</font></b></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td height='5'></td></tr> 
		<tr>
			<td>
				<% out.println(tableGrid.getTotalHtml());%>
			</td>
		</tr>
		<tr><td height='7'></td></tr>
		<%-- 페이징 영역 시작--%>
		<script>
		function goPage(pageNumber){
			pagingfrm.pg.value = pageNumber;
			pagingfrm.submit();
		}
		</script>
		<form name="pagingfrm">
		<tr>
			<td>
				<div align='center'l>
				<%-- 페이징 영역 시작--%>
					<table cellpadding="0" cellspacing="0">
						<tr height='30'>
							<td width="100%" align="center">
								<SCRIPT LANGUAGE="JavaScript">
									document.write( makePageHtml( 
											<c:out value="${result.valueListInfo.pagingPage}" default="1"/>, 
											15, 
											<c:out value="${result.valueListInfo.totalNumberOfEntries}" default="0"/> , 
											<c:out value="${result.valueListInfo.pagingNumberPer}"/>)
									) ;
								</SCRIPT>
							</td>
						</tr>
					</table>									
				
				</div>
				<input type="hidden" name="mode"     value="getValueJudgeEvalList">
				<input type="hidden" name="pg"       value="<c:out value="${pg}"/>">
				<input type="hidden" name="year"       value="<c:out value="${year}"/>">
				<input type="hidden" name="promotionType"       value="<c:out value="${promotionType}"/>">
				<input type="hidden" name="judgeName"       value="<c:out value="${judgeName}"/>">
			</td>
		</tr>
		</form>
		<%-- 페이징 영역 끝--%>
<!-- 본문종료-->
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>
</html>