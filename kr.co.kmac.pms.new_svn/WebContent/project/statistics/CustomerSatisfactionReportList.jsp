<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.kmac.pms.project.statistics.data.CustomerSatisfactionReportList"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>

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
function doSearch()
{
	document.frm.target="";
	document.frm.action="/action/CustomerSatisfactionReportAction.do?mode=getValueCustomerSatisfactionReportList";
	document.frm.submit();
}

function goProjectDetail(projectCode, businessTypeCode, seq) {
	//document.frm.target = "_blank";
	//document.frm.action = "/action/CustomerSatisfactionReportAction.do?mode=getValueCustomerSatisfactionReportDetail&projectcode="+projectCode;
	//document.frm.submit();	
	//alert(projectCode);
	var url = "/action/CustomerSatisfactionReportAction.do?"
		+ "mode=getValueCustomerSatisfactionReportDetail&"
		+ "businessTypeCode="+businessTypeCode+"&projectcode="+projectCode+"&seq="+seq;
	window.open(url, 'csResult', 'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=730,height=640,directories=no,menubar=no');
}

function saveListToExcel() {
	var keyword = document.frm.keyword.value;
	var startDate = document.frm.startDate.value;
	var endDate = document.frm.endDate.value;
	var bizType = document.frm.bizType.value;
	var year = document.frm.year.value;
	var groupCode = document.frm.groupCode.value;
	
	var surl = "/action/CustomerSatisfactionReportAction.do?"
		+ "mode=getValueCustomerSatisfactionReportListToExcel&"
		+ "keyword="+keyword+"&startDate="+startDate+"&endDate="+endDate+"&bizType="+bizType+"&year="+year+"&groupCode="+groupCode;
	//alert(surl);
	document.location = surl;
}
function goPage(pageNumber){
	pagingfrm.pg.value = pageNumber;
	pagingfrm.submit();
}

</script>
</head>
<%
	//리스트 오브젝트 
	List<CustomerSatisfactionReportList> list = ((ValueList)request.getAttribute("result")).getList();
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" class='listTable' width='765' cellpadding='0' cellspacing='0'  ");
	tableGrid.setHeadAnnex(false); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합

	//헤더 정의 
	Row headerRow = new Row();
	headerRow.addCell(new Cell("비즈니스유형").setAttribute(" width='10%' "));
	headerRow.addCell(new Cell("조직단위").setAttribute(" width='10%' "));
	headerRow.addCell(new Cell("프로젝트명").setAttribute(" width='*' "));
	headerRow.addCell(new Cell("고객사명").setAttribute(" width='27%' "));
	headerRow.addCell(new Cell("만족도").setAttribute(" width='10%' "));
	headerRow.addCell(new Cell("평가일").setAttribute(" width='10%' "));
	//headerRow.addCell(new Cell("만족도<br>보기").setAttribute(" width='8%' "));
	tableGrid.addHeadRow(headerRow);

	Row row = null;
	if(list == null || list.size() > 0){
		for(CustomerSatisfactionReportList custSatisList : list){
			row = new Row();
			row.attribute = "onmouseover='row_over(this)' onmouseout='row_out(this)'";
			row.addCell(new Cell(custSatisList.getBusinessTypeName()).setAttribute("align='center' "));
			row.addCell(new Cell(custSatisList.getRunningDivName()).setAttribute("align='center' "));
			row.addCell(new Cell(custSatisList.getProjectName()).setAttribute("align='left' "));
			row.addCell(new Cell(custSatisList.getCustomerName()).setAttribute("align='center' "));
			row.addCell(new Cell(custSatisList.getRatio()).setAttribute("align='center' style='cursor:hand;' onclick='goProjectDetail(\""
					+custSatisList.getProjectCode()+ "\",\""+custSatisList.getBusinessTypeCode()+"\",\""+custSatisList.getSeq()+"\")'  "));
			//row.addCell(new Cell("<img src='/images/btn_glass.jpg'>").setAttribute(" align='center' style='cursor:hand;' onclick='goProjectDetail(\""
			//		+custSatisList.getProjectCode()+ "\",\""+custSatisList.getBusinessTypeCode()+"\",\""+custSatisList.getProcessTypeCode()+"\")'  "));
			row.addCell(new Cell(custSatisList.getWriteDate()).setAttribute("align='center' "));
			tableGrid.addRow(row); 
		}	
	}else{
		row = new Row(); 
		row.addCell(new Cell("검색 결과가 존재하지 않습니다 .").setAttribute(" align='center' colspan='5' " ));
		tableGrid.addRow(row); 
	}
	tableGrid.tableCheck(); 
	
%>

<body>
<!-- location -->
		<div class="location">
			<p class="menu_title">프로젝트별 고객만족도 현황</p>
			<ul>
				<li class="home">HOME</li>
				<li>PMS</li>
				<li>프로젝트 현황</li>
				<li>프로젝트별 고객만족도 현황</li>
			</ul>
		</div>
<!-- // location -->

<div class="contents sub">
			<div class="board_box">
			<form name="frm" method="post">
				<div class="search_box total">
					<div class="select_group">
						<div class="select_box">
							<div class="label_group">
								<p>비즈니스 타입</p>
								<select name="bizType" class='select'>
									<option value="">전체</option>
									<option value="BTA" <c:if test="${bizType == 'BTA'}">selected</c:if>>컨설팅</option>
									<option value="BTJ" <c:if test="${bizType == 'BTJ'}">selected</c:if>>진단</option>
									<option value="BTD" <c:if test="${bizType == 'BTD'}">selected</c:if>>리서치</option>
									<option value="N4" <c:if test="${bizType == 'N4'}">selected</c:if>>사내교육</option>
									<option value="N1" <c:if test="${bizType == 'N1'}">selected</c:if>>공개교육(장기)</option>
									<option value="N2" <c:if test="${bizType == 'N2'}">selected</c:if>>공개교육(단기)</option>
									<option value="SS" <c:if test="${bizType == 'SS'}">selected</c:if>>특강</option>
									<option value="DE" <c:if test="${bizType == 'DE'}">selected</c:if>>수주연수</option>
								</select>
							</div>
							<div class="label_group">
								<p>조직 단위</p>
								<org:divList enabled="1" depth="2" divType="A" all="Y" attribute=" name='groupCode' class='select' " isSelectBox="Y" selectedInfo="${groupCode}" />
							</div>
							<div class="label_group">	
								<p>년도</p>					
								<date:year beforeYears="2" afterYears="2" attribute=" name='year' class='select' " selectedInfo="${year}" />	
							</div>
						</div>
						<div class="select_box">
							<div class="label_group">	
								<p>프로젝트 기간</p>
								<script>
									jQuery(function(){jQuery( "#startDate" ).datepicker({});});
									jQuery(function(){jQuery( "#endDate" ).datepicker({});});
								</script>
								<input type="text" name="startDate" id="startDate" style="width:44%;" value="<c:out value="${startDate}"/>"> ~ <input type="text" name="endDate" id="endDate" style="width:44%;" value="<c:out value="${endDate}"/>">					
							</div>
							<div class="label_group">
							<p>프로젝트 명</p>
							<input type="text" name="keyword" value="<c:out value="${keyword}"/>" >
							</div>
						</div>
						</div>
						<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색 <i class="mdi mdi-magnify"></i></button></div>
					</form>
				</div>
			</div>
			
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">프로젝트별 고객만족도 현황</p>
					</div>
					<div class="select_box">
					<button type="button" class="btn line btn_blue" onclick="location.href='/action/CustomerSatisfactionReportAction.do?mode=getValueCustomerSatisfactionReportSummary&year=<c:out value="${year}" />'"><i class="mdi mdi-clipboard-check-outline"></i><c:out value="${year}" />년 전체 현황</button>
					</div>
				</div>
		
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 15%" />
								<col style="width: 15%" />
								<col style="width: *" />
								<col style="width: 15%" />
								<col style="width: 10%" />
								<col style="width: 15%" />
							</colgroup>
							<tr>
								<th>비즈니스유형</th>
								<th>조직단위</th>
								<th>프로젝트명</th>
								<th>고객사명</th>
								<th>만족도</th>
								<th>평가일</th>	
							</tr>
							<tbody id="ListData">
							<c:forEach var="rs" items="${result.list}">
								<tr>
									<td><c:out value="${rs.businessTypeName }" /></td>
									<td><c:out value="${rs.runningDivName }" /></td>
									<td><c:out value="${rs.projectName }" /></td>
									<td><c:out value="${rs.customerName }" /></td>
									<td style="cursor:hand;" onclick="goProjectDetail('<c:out value="${rs.projectCode}"/>', '<c:out value="${rs.businessTypeCode}"/>', '<c:out value="${rs.seq }"/>')"><c:out value="${rs.ratio }" /></td>
									<td><c:out value="${rs.writeDate }" /></td>
								</tr>
							</c:forEach>
					<c:if test="${empty result.list}"><td colspan='6' align='center'>검색 결과가 존재하지 않습니다 .</td></c:if>
					</tbody>
				</table>
				<form name="pagingfrm">
				<div class="paging_area">
						<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml2( 
									<c:out value="${result.valueListInfo.pagingPage}" default="1"/>, 
									10, 
									<c:out value="${result.valueListInfo.totalNumberOfEntries}" default="0"/> , 
									10)
							) ;
					</SCRIPT>
				</div>
				<div style="display:none">
				<input type="hidden" name="mode"     value="getValueCustomerSatisfactionReportList">
				<input type="hidden" name="pg"       value="<c:out value="${pg}"/>">
				<input type="hidden" name="startDate"  value="<c:out value="${startDate}"/>">
				<input type="hidden" name="endDate"    value="<c:out value="${endDate}"/>">
				<input type="hidden" name="keyword"    value="<c:out value="${keyword}"/>">
				<input type="hidden" name="bizType"    value="<c:out value="${bizType}"/>">
				<input type="hidden" name="groupCode"  value="<c:out value="${groupCode}"/>">
				<input type="hidden" name="year"       value="<c:out value="${year}"/>">
				<input type="hidden" name="searchValue"       value="<c:out value="${searchValue}"/>">
				</div>
			</div>
		</div>
		</form>
		<%-- 페이징 영역 끝--%>
</div>
</body>
</html>