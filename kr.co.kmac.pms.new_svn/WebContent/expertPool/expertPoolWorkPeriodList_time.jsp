<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="java.util.List"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.expertpool.data.ExpertPool"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>

<%
	String companyPosition = request.getParameter("companyPosition");
	String titleMsg = "근무시간 현황";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>근무시간 현황</title>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">

<script type="text/javascript">

/*popup layout*/
window.onload=function(){
	layer_open(this, 'pop_register');
}

<%-- 개별 스크립트 영역 --%>
function detail(ssn) {
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}
function doSearch() {
	document.frm.submit();
}
function saveListToExcel() {
	var surl = '/action/ExpertPoolManagerAction.do?mode=saveRestrictExpertPoolListToExcel_time';
 	surl += "&startDate=" + document.frm.startDate.value;
	surl += "&endDate=" + document.frm.endDate.value;
	/* surl += "&name=" + document.frm.name.value; */
	document.location = surl;
}
</script>
</head>
<%
	List<ExpertPool> list = ((ValueList)request.getAttribute("list")).getList();
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" class='listTable' style='table-layout: fixed; width: 100%' ");
	tableGrid.setHeadAnnex(false);
	tableGrid.setAnnex(true); 
/* 	String year = request.getAttribute("year").toString();
	String type = request.getAttribute("type").toString(); */
	
	//헤더 정의 
	Row headerRow = new Row();

	headerRow.addCell(new Cell("소속").setAttribute(" width='100px' "));
	headerRow.addCell(new Cell("성명").setAttribute(" width='80px' "));
	headerRow.addCell(new Cell("1일").setAttribute(" width='40px' "));		
	headerRow.addCell(new Cell("2일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("3일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("4일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("5일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("6일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("7일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("8일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("9일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("10일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("11일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("12일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("13일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("14일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("15일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("16일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("17일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("18일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("19일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("20일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("21일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("22일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("23일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("24일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("25일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("26일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("27일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("28일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("29일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("30일").setAttribute(" width='40px' "));
	headerRow.addCell(new Cell("31일").setAttribute(" width='40px' "));
	tableGrid.addHeadRow(headerRow);
	
	Row row = null;
 	if(list == null || list.size() > 0){
		for(ExpertPool mm : list){
			row = new Row();
			String style = "";
			String style1 = "";
			
			row.addCell(new Cell(mm.getLabelName()).setAttribute(" align='center' "));
			row.addCell(new Cell(mm.getUserName()).setAttribute(" align='center' "));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM01() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM02() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM03() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM04() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM05() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM06() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM07() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM08() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM09() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM10() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM11() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM12() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM13() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM14() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM15() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM16() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM17() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM18() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM19() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM20() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM21() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM22() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM23() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM24() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM25() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM26() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM27() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM28() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM29() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM30() +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail(' ')\">" + mm.getM31() +"</a>").setAttribute(" align='center'  " + style ));

			tableGrid.addRow(row); 
		}	
		
	}else{
		row = new Row(); 
		row.addCell(new Cell("검색 결과가 없습니다 .").setAttribute(" colspan='33' align='center' "));
		tableGrid.addRow(row); 
	}
 	
	tableGrid.tableCheck();
%>

<body>
<form name="frm" method="post">
	<input type="hidden" id="today" name="today" value="<c:out value="${today}" />" />
	
	<div style="padding: 10 10 0 10">
		<!-- <div class="popup_bg"></div> -->
		<div id="pop_register" class="popup_layer pop_register">
		<div class="fixed_contents sc">
			<div class="popup_inner tbl-sc" style="width:1600px; ">
			
				<div class="board_box">
					<div class="title_both">
						<div class="h1_area">
							<p class="term">
								<i class="mdi mdi-file-document-outline"></i>
								근무시간 현황
							</p>
						</div>
						<div class="text-R">
							<button type="button" class="btn line btn_excel" onclick="saveListToExcel()"><i class="mdi mdi-square-edit-outline"></i>EXCEL 다운로드</button>
						</div>
					</div>
					
					<div class="board_contents sc">
						<!-- Table View-->
						<table class="tbl-edit td-c pd-none">
							<colgroup>
								<col style="width: 7%" />
								<col style="width: 7%" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
							</colgroup>
							<tbody>
								<tr>
									<th>소속</th>
									<th>이름</th>
									<th>1일</th>
									<th>2일</th>
									<th>3일</th>
									<th>4일</th>
									<th>5일</th>
									<th>6일</th>
									<th>7일</th>
									<th>8일</th>
									<th>9일</th>
									<th>10일</th>
									<th>11일</th>
									<th>12일</th>
									<th>13일</th>
									<th>14일</th>
									<th>15일</th>
									<th>16일</th>
									<th>17일</th>
									<th>18일</th>
									<th>19일</th>
									<th>20일</th>
									<th>21일</th>
									<th>22일</th>
									<th>23일</th>
									<th>24일</th>
									<th>25일</th>
									<th>26일</th>
									<th>27일</th>
									<th>28일</th>
									<th>29일</th>
									<th>30일</th>
									<th>31일</th>
								</tr>
								<c:choose>
									<c:when test="${!empty list.list}">
										<c:forEach var="rs" items="${list.list}">
											<tr>
												<td><c:out value="${rs.labelName }" /></td>
												<td><c:out value="${rs.userName }" /></td>
												<td><c:out value="${rs.m01 }" /></td>
												<td><c:out value="${rs.m02 }" /></td>
												<td><c:out value="${rs.m03 }" /></td>
												<td><c:out value="${rs.m04 }" /></td>
												<td><c:out value="${rs.m05 }" /></td>
												<td><c:out value="${rs.m06 }" /></td>
												<td><c:out value="${rs.m07 }" /></td>
												<td><c:out value="${rs.m08 }" /></td>
												<td><c:out value="${rs.m09 }" /></td>
												<td><c:out value="${rs.m10 }" /></td>
												<td><c:out value="${rs.m11 }" /></td>
												<td><c:out value="${rs.m12 }" /></td>
												<td><c:out value="${rs.m13 }" /></td>
												<td><c:out value="${rs.m14 }" /></td>
												<td><c:out value="${rs.m15 }" /></td>
												<td><c:out value="${rs.m16 }" /></td>
												<td><c:out value="${rs.m17 }" /></td>
												<td><c:out value="${rs.m18 }" /></td>
												<td><c:out value="${rs.m19 }" /></td>
												<td><c:out value="${rs.m20 }" /></td>
												<td><c:out value="${rs.m21 }" /></td>
												<td><c:out value="${rs.m22 }" /></td>
												<td><c:out value="${rs.m23 }" /></td>
												<td><c:out value="${rs.m24 }" /></td>
												<td><c:out value="${rs.m25 }" /></td>
												<td><c:out value="${rs.m26 }" /></td>
												<td><c:out value="${rs.m27 }" /></td>
												<td><c:out value="${rs.m28 }" /></td>
												<td><c:out value="${rs.m29 }" /></td>
												<td><c:out value="${rs.m30 }" /></td>
												<td><c:out value="${rs.m31 }" /></td>
											</tr>
										</c:forEach>
									</c:when>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
					
<%-- 	<table width="750" cellpadding="0" cellspacing="0">
		<!-- 타이틀 영역 -->
		<tr>
			<td height="12">
				<% String back = request.getParameter("backButtonYN"); %>
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true" >
					<jsp:param name="title" value="<%=titleMsg %>" />
					<jsp:param name="backButtonYN" value="<%=back %>" />
				</jsp:include>
			</td>
		</tr>
		<tr>
			<td height="21" align="left" valign="top">
				<%@ include file="/common/include/searchBox_Header.jsp"%>
				<table border="0" width="100%" style="border-collapse: collapse;">
					<colgroup> 
						<col width="130px">
						<col width="*">
						<col width="200px">
						<col width="225px"> 
					</colgroup>
					<tr>
						<td class="searchTitle_center">연도</td> 
						<td class="searchField_center">
							<fmt:parseDate value="${startDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
							<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
							<fmt:parseDate value="${endDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
							<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
						<script>
							jQuery(function(){jQuery( "#startDate" ).datepicker({});});
							jQuery(function(){jQuery( "#endDate" ).datepicker({});});
						</script>
						<input type="text" name="startDate" id="startDate" readonly="readonly" size="13" value="<c:out value="${start}" />" />&nbsp~&nbsp     
						<input type="text" name="endDate" id="endDate" readonly="readonly" size="13" value="<c:out value="${end}" />" />									
						</td>
					</tr>
				</table>
				<jsp:include page="/common/include/searchBox_Footer.jsp" flush="true">
				<jsp:param name="hasButton" value="Y" /></jsp:include>
			</td>
		</tr>
	</table> --%>
</form>
</body>
</html>