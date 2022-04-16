<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.kmac.pms.project.statistics.data.CustomerSatisfactionReportSummary"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>프로젝트 고객만족도 월별 현황</title>
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
function doSearch()
{
	document.frm.target="";
	document.frm.action="/action/CustomerSatisfactionReportAction.do?mode=getValueCustomerSatisfactionReportSummary";
	document.frm.submit();
}

function goProjectCSIDetail(businessTypeCode, runningDivCode, grade) {
	var year = document.frm.year.value;
	var month = document.frm.month.value;
	var url = "/action/CustomerSatisfactionReportAction.do?"
		+ "mode=getValueCustomerSatisfactionReportSummaryDetail&"
		+ "businessTypeCode="+businessTypeCode+"&runningDeptCode="+runningDivCode+"&grade="+grade+"&year="+year+"&month="+month;
	window.open(url, 'csResult', 'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=730,height=540,directories=no,menubar=no');
}

function goDelayProjectDetail(businessTypeCode, runningDivCode, delayState) {
	var url = "/action/ProjectSearchAction.do?"
		+ "mode=getProjectSearchList&"
		+ "businessTypeCode="+businessTypeCode+"&runningDeptCode="+runningDivCode+"&delayState="+delayState+"&projectState=4";
	window.open(url, 'delayResult', 'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=800,height=570,directories=no,menubar=no');
}

</script>
</head>
<%
	//리스트 오브젝트 
	List<CustomerSatisfactionReportSummary> list = ((ValueList)request.getAttribute("result")).getList();
	//테이블 그리드 생성 
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" class='listTable' width='765' cellpadding='0' cellspacing='0'  ");
	tableGrid.setHeadAnnex(false); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합

	//헤더 정의 
	Row headerRow = new Row();
	headerRow.addCell(new Cell("비즈니스유형").setAttribute(" width='19%' "));
	headerRow.addCell(new Cell("조직단위").setAttribute(" width='21%' "));
	headerRow.addCell(new Cell("75점 이상<br>(100~75)").setAttribute(" width='15%' "));
	headerRow.addCell(new Cell("75점 미만<br>(75~50)").setAttribute(" width='15%' "));
	headerRow.addCell(new Cell("50점 미만<br>(50~0)").setAttribute(" width='15%' "));
	headerRow.addCell(new Cell("평가지연<br>(30일 초과)").setAttribute(" width='15%' "));
	tableGrid.addHeadRow(headerRow);
	
	int high = 0;
	int medium = 0;
	int low = 0; 
	int delay = 0;

	Row row = null;
	if(list == null || list.size() > 0){
		for(CustomerSatisfactionReportSummary custSatisList : list){
			row = new Row();
			row.attribute = "onmouseover='row_over(this)' onmouseout='row_out(this)'";
			row.addCell(new Cell(custSatisList.getBusinessTypeName()).setAttribute("align='center' "));
			row.addCell(new Cell(custSatisList.getRunningDivName()).setAttribute("align='center' "));
			row.addCell(new Cell(custSatisList.getHigh()).setAttribute("align='center' style='cursor:hand;' onclick='goProjectCSIDetail(\""
					+custSatisList.getBusinessTypeCode()+ "\",\""+custSatisList.getRunningDivCode()+"\",\"high\")'  "));
			row.addCell(new Cell(custSatisList.getMedium()).setAttribute("align='center' style='cursor:hand;' onclick='goProjectCSIDetail(\""
					+custSatisList.getBusinessTypeCode()+ "\",\""+custSatisList.getRunningDivCode()+"\",\"medium\")'  "));
			row.addCell(new Cell(custSatisList.getLow()).setAttribute("align='center' style='cursor:hand;' onclick='goProjectCSIDetail(\""
					+custSatisList.getBusinessTypeCode()+ "\",\""+custSatisList.getRunningDivCode()+"\",\"low\")'  "));
			row.addCell(new Cell(custSatisList.getDelayCnt()).setAttribute("align='center' style='cursor:hand;' onclick='goDelayProjectDetail(\""
					+custSatisList.getBusinessTypeCode()+ "\",\""+custSatisList.getRunningDivCode()+"\",\"ER\")'  "));
			
			high += Integer.parseInt(custSatisList.getHigh());
			medium += Integer.parseInt(custSatisList.getMedium());
			low += Integer.parseInt(custSatisList.getLow());
			delay += Integer.parseInt(custSatisList.getDelayCnt());
			
			tableGrid.addRow(row);
		}	
		
		if (high != 0 || high != 0){
			row = new Row();		
			row.addCell(new Cell("합계").setAttribute(" align='center' style='background-color:#F2F1EE' "));
			row.addCell(new Cell("").setAttribute(" align='center' style='background-color:#F2F1EE' "));
			row.addCell(new Cell(Integer.toString(high)).setAttribute(" align='center' style='background-color:#F2F1EE' "));
			row.addCell(new Cell(Integer.toString(medium)).setAttribute(" align='center' style='background-color:#F2F1EE' "));
			row.addCell(new Cell(Integer.toString(low)).setAttribute(" align='center' style='background-color:#F2F1EE' "));
			row.addCell(new Cell(Integer.toString(delay)).setAttribute(" align='center' style='background-color:#F2F1EE' "));
			
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
<%-- 작업영역 --%>

<!-- location -->
		<div class="location">
			<p class="menu_title">고객만족도 현황</p>
			<ul>
				<li class="home">HOME</li>
				<li>PMS</li>
				<li>프로젝트 현황</li>
				<li>고객만족도 현황</li>
			</ul>
		</div>
<!-- // location -->
	<div class="contents sub">
		<form name="frm" method="post">
		<div class="box">
				<div class="search_box total">
					<div class="select_group">
						<div class="select_box">
							<div class="label_group">
								<p>비즈니스 타입</p>
								<select name="businessTypeCode" class='select'>
									<option value="">전체</option>
									<option value="BTA" <c:if test="${businessTypeCode == 'BTA'}">selected</c:if>>컨설팅</option>
									<option value="BTJ" <c:if test="${businessTypeCode == 'BTJ'}">selected</c:if>>진단</option>
									<option value="BTD" <c:if test="${businessTypeCode == 'BTD'}">selected</c:if>>리서치</option>
									<option value="N4" <c:if test="${businessTypeCode == 'N4'}">selected</c:if>>사내교육</option>
									<option value="N1" <c:if test="${businessTypeCode == 'N1'}">selected</c:if>>공개교육(장기)</option>
									<option value="N2" <c:if test="${businessTypeCode == 'N2'}">selected</c:if>>공개교육(단기)</option>
									<option value="SS" <c:if test="${businessTypeCode == 'SS'}">selected</c:if>>특강</option>
									<option value="DE" <c:if test="${businessTypeCode == 'DE'}">selected</c:if>>수주연수</option>
								</select>
							</div>
							<div class="label_group">
								<p>조직단위</p>
								<org:divList enabled="1" depth="2" divType="A" all="Y" attribute=" name='runningDeptCode' class='select' " isSelectBox="Y" selectedInfo="${runningDeptCode}" />	
							</div>
							<div class="label_group">
								<p>기간</p>
								<date:year beforeYears="2" afterYears="2" attribute=" name='year' class='select' style='width:48%;' " selectedInfo="${year}" />
								<date:month hasAll="Y" attribute=" name='month' class='select' style='width:48%;' " selectedInfo="${month}" />
							</div>
						</div>
						</div>
						<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색 <i class="mdi mdi-magnify"></i></button></div>
					</div>
				</div>
				</form>
			
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">고객만족도 현황</p>
					</div>
					<div class="select_box">
					<button type="button" class="btn line btn_blue" onclick="location.href='/action/CustomerSatisfactionReportAction.do?mode=getValueCustomerSatisfactionReportList&year=<c:out value="${year}" />'"><i class="mdi mdi-clipboard-check-outline"></i><c:out value="${year}" />년 프로젝트별 현황</button>
					</div>
				</div>
		
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 15%" />
								<col style="width: 25%" />
								<col style="width: 15%" />
								<col style="width: 15%" />
								<col style="width: 15%" />
								<col style="width: 15%" />
							</colgroup>
							<tr>
								<th>비즈니스유형</th>
								<th>조직단위</th>
								<th>75점 이상</br>(100~75)</th>
								<th>75점 미만</br>(75~50)</th>
								<th>50점 미만</br>(50~0)</th>
								<th>평가지연</br>(30일 초과)</th>	
							</tr>
							<tbody id="ListData">
							<c:forEach var="rs" items="${result.list}">
								<tr>
									<td><c:out value="${rs.businessTypeName }" /></td>
									<td><c:out value="${rs.runningDivName }" /></td>
									<td style="cursor:hand;" onclick="goProjectCSIDetail('<c:out value="${rs.businessTypeCode}"/>', '<c:out value="${rs.runningDivCode}"/>', 'high')"><c:out value="${rs.high }" /></td>
									<td style="cursor:hand;" onclick="goProjectCSIDetail('<c:out value="${rs.businessTypeCode}"/>', '<c:out value="${rs.runningDivCode}"/>', 'medium')"><c:out value="${rs.medium }" /></td>
									<td style="cursor:hand;" onclick="goProjectCSIDetail('<c:out value="${rs.businessTypeCode}"/>', '<c:out value="${rs.runningDivCode}"/>', 'low')"><c:out value="${rs.low }" /></td>
									<td style="cursor:hand;" onclick="goDelayProjectDetail'<c:out value="${rs.businessTypeCode}"/>', '<c:out value="${rs.runningDivCode}"/>', 'ER')"><c:out value="${rs.delayCnt }" /></td>
								</tr>
							</c:forEach>
					<c:if test="${empty result.list}"><td colspan='6' align='center'>검색 결과가 존재하지 않습니다 .</td></c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>