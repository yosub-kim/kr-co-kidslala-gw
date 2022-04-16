<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="java.util.List"%>
<%@page import="kr.co.kmac.pms.project.statistics.data.ProjectMonthlyReportDataForList"%>
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
function doSearch()
{
	document.frm.submit();
}
function changeDiv(obj) {
	if(obj == 'A'){
		$('div0').innerHTML = $('div1').innerHTML; 
	}else if(obj == 'J'){
		$('div0').innerHTML = $('div2').innerHTML; 
	}else if(obj == 'C'){
		$('div0').innerHTML = $('div3').innerHTML;
	}else{
		$('div0').innerHTML = $('div4').innerHTML; 
	}
}
function goDetail(state, bizTypeCode, writerName, writerSsn) {
	var year = document.frm.year.value;
	var month = document.frm.month.value;
	if (month < 10) month = "0" + month;
	var params = "&yearMonth="+year+month+"&bizTypeCode="+bizTypeCode+"&writerName="+encodeURIComponent(writerName)+"&writerSsn="+writerSsn;
	switch(state) {
		case "0" :	// 미작성
			params = params + "&doneState=writer";
			break;
		case "1" :	// 작성
			params = params + "&writeState=writer";
			break;
		case "2" :	// 검토
			params = params + "&state=reviewer";
			break;
		case "3" :	// 승인
			params = params + "&state=approver";
			break;
		default :	// 기타: 완료
			break;
	}

	var url="/action/ProjectMonthlyReportAction.do?mode=select"+params;
	//alert(url);
	window.open(url, 'preportList', 'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=685,height=350,directories=no,menubar=no');
}

</script>
</head>
<%
	//리스트 오브젝트 
	List<ProjectMonthlyReportDataForList> list = ((ValueList)request.getAttribute("result")).getList();
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" class='listTable' style='table-layout: fixed; width: 100%' ");
	tableGrid.setHeadAnnex(false);	// true 이면 Header Cell 병합
	tableGrid.setAnnex(true); 		// true 이면 Cell 병합

	//헤더 정의 
	Row headerRow = new Row();

	headerRow.addCell(new Cell("소속").setAttribute(" width='*' "));
	headerRow.addCell(new Cell("컨설턴트명").setAttribute(" width='12%' "));
	headerRow.addCell(new Cell("구분").setAttribute(" width='12%' "));
	headerRow.addCell(new Cell("작성").setAttribute(" width='12%' "));		
	headerRow.addCell(new Cell("미작성").setAttribute(" width='12%' "));
	headerRow.addCell(new Cell("검토중").setAttribute(" width='12%' "));
	headerRow.addCell(new Cell("승인중").setAttribute(" width='12%' "));
	headerRow.addCell(new Cell("확정").setAttribute(" width='12%' "));
	tableGrid.addHeadRow(headerRow);
	
	int isWriteTotal= 0;
	int isNotWriteTotal= 0;
	int isNotReviewTotal= 0; 
	int isNotApproveTotal= 0;
	int confirmTotal= 0;

	Row row = null;
	if(list == null || list.size() > 0){
		for(ProjectMonthlyReportDataForList monthlyReport : list){
			row = new Row();
			row.addCell(new Cell(monthlyReport.getDept()).setAttribute(" align='center' "));
			row.addCell(new Cell(monthlyReport.getName()).setAttribute(" align='center' "));
			row.addCell(new Cell(monthlyReport.getBizType()).setAttribute(" align='center' "));
			/*
			row.addCell(new Cell("<a href='#' onclick=\"goDetail('0', '" + monthlyReport.getWriter() + "')\">" + monthlyReport.getIsWrite()+"</a>").setAttribute(" align='center' "));
			row.addCell(new Cell("<a href='#' onclick=\"goDetail('1', '" + monthlyReport.getWriter() + "')\">" + monthlyReport.getIsNotWrite()+"</a>").setAttribute(" align='center' "));
			row.addCell(new Cell("<a href='#' onclick=\"goDetail('2', '" + monthlyReport.getWriter() + "')\">" + monthlyReport.getIsNotReview()+"</a>").setAttribute(" align='center' "));
			row.addCell(new Cell("<a href='#' onclick=\"goDetail('3', '" + monthlyReport.getWriter() + "')\">" + monthlyReport.getIsNotApprove()+"</a>").setAttribute(" align='center' "));
			*/
			row.addCell(new Cell("<a href=\"javascript:goDetail('0', '" + monthlyReport.getBizTypeCode() + "\',\'" + monthlyReport.getName() + "\',\'" + monthlyReport.getWriter() + "')\">" + monthlyReport.getIsWrite()+"</a>").setAttribute(" align='center' "));
			row.addCell(new Cell("<a href=\"javascript:goDetail('1', '" + monthlyReport.getBizTypeCode() + "\',\'" + monthlyReport.getName() + "\',\'" + monthlyReport.getWriter() + "')\">" + monthlyReport.getIsNotWrite()+"</a>").setAttribute(" align='center' "));
			row.addCell(new Cell("<a href=\"javascript:goDetail('2', '" + monthlyReport.getBizTypeCode() + "\',\'" + monthlyReport.getName() + "\',\'" + monthlyReport.getWriter() + "')\">" + monthlyReport.getIsNotReview()+"</a>").setAttribute(" align='center' "));
			row.addCell(new Cell("<a href=\"javascript:goDetail('3', '" + monthlyReport.getBizTypeCode() + "\',\'" + monthlyReport.getName() + "\',\'" + monthlyReport.getWriter() + "')\">" + monthlyReport.getIsNotApprove()+"</a>").setAttribute(" align='center' "));
			
			row.addCell(new Cell(monthlyReport.getConfirm()).setAttribute(" align='center' "));
			
			isWriteTotal = isWriteTotal+Integer.parseInt(monthlyReport.getIsWrite());
			isNotWriteTotal = isNotWriteTotal+Integer.parseInt(monthlyReport.getIsNotWrite());
			isNotReviewTotal = isNotReviewTotal+Integer.parseInt(monthlyReport.getIsNotReview());
			isNotApproveTotal = isNotApproveTotal+Integer.parseInt(monthlyReport.getIsNotApprove());
			confirmTotal = confirmTotal+Integer.parseInt(monthlyReport.getConfirm());
			
			tableGrid.addRow(row); 
		}	
		
		if (isWriteTotal != 0 || isNotWriteTotal != 0){
			row = new Row();		
			row.addCell(new Cell("합계").setAttribute(" align='center' style='background-color:#F2F1EE' "));
			row.addCell(new Cell("").setAttribute(" align='center' style='background-color:#F2F1EE' "));
			row.addCell(new Cell("").setAttribute(" align='center' style='background-color:#F2F1EE' "));
			row.addCell(new Cell(Integer.toString(isWriteTotal)).setAttribute(" align='center' style='background-color:#F2F1EE' "));
			row.addCell(new Cell(Integer.toString(isNotWriteTotal)).setAttribute(" align='center' style='background-color:#F2F1EE' "));
			row.addCell(new Cell(Integer.toString(isNotReviewTotal)).setAttribute(" align='center' style='background-color:#F2F1EE' "));
			row.addCell(new Cell(Integer.toString(isNotApproveTotal)).setAttribute(" align='center' style='background-color:#F2F1EE' "));
			row.addCell(new Cell(Integer.toString(confirmTotal)).setAttribute(" align='center' style='background-color:#F2F1EE' "));
			
			tableGrid.addRow(row); 
		}		
		
	}else{
		row = new Row(); 
		row.addCell(new Cell("검색 결과가 없습니다 .").setAttribute(" colspan='8' align='center' "));
		tableGrid.addRow(row); 
	}
	tableGrid.tableCheck();
	
%>

<body>
<%-- 작업영역 --%>
<div id="PageFull">
	<table width="100%" cellpadding="0" cellspacing="0">
		<!-- 타이틀 영역 -->
		<tr>
			<td height="12">
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
					<jsp:param name="title" value="지도일지 월별현황" />
				</jsp:include>
			</td>
		</tr>
		
		<!-- 검색 영역 -->
		<tr>
			<td height="21" align="left" valign="top">
				<form name="frm" method="post">
				<input type="hidden" name="mode" value="selectList">
				<input type="hidden" name="searchOK" value="1">
				<%@ include file="/common/include/searchBox_Header.jsp"%>
					<table height="52" border="0" width="100%" cellpadding="0" cellspacing="0">
						<colgroup> 
							<col width="85px">
							<col width="100px">
							<col width="85px">
							<col width="100px">
							<col width="85px"> 
							<col width="*"> 
						</colgroup>
						<tr>
							<td class="searchTitle_center">구분</td>
							<td class="searchField_center">
								<select name="jobClass"  class="selectbox" onchange="changeDiv(this.value);">
									<option value="A" <c:if test="${jobClass == 'A' }">selected</c:if>>상근</option>
									<option value="J" <c:if test="${jobClass == 'J' }">selected</c:if>>상임</option>
									<option value="C" <c:if test="${jobClass == 'C' }">selected</c:if>>엑스퍼트</option>
									<option value="H" <c:if test="${jobClass == 'H' }">selected</c:if>>AA</option>
									<option value="N" <c:if test="${jobClass == 'H' }">selected</c:if>>RA</option>
								</select>
							</td>
							<td class="searchTitle_center">연도</td>
							<td class="searchField_center">
								<date:year beforeYears="4" afterYears="4" attribute=" name='year' class='selectbox' style='width:100pt' " selectedInfo="${year}" />
							</td>
							<td class="searchTitle_center">월</td>
							<td class="searchField_center">
								<select name="month" class="selectbox" style="width:100pt">
									<c:forEach var="i" begin="1" end="12">
										<option value="<c:out value="${i}"/>" <c:if test="${month == i}">selected</c:if>><c:out value="${i}"/>월</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="searchTitle_center">소속</td>
							<td class="searchField_center">
								<div id="div0">
									<c:choose>
									<c:when test="${jobClass=='A'}">
										<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100pt;' " all="Y"></org:divList>
									</c:when>
									<c:when test="${jobClass=='J'}">
										<org:divList enabled="1" divType="J" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100pt;' " all="Y"></org:divList>
									</c:when>
									<c:when test="${jobClass=='C'}">
										<org:divList enabled="1" divType="C" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100pt;' " all="Y"></org:divList>
									</c:when>
									<c:when test="${jobClass=='H'}">
										<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100pt;' " all="Y"></org:divList>
									</c:when>
									<c:otherwise>
										<org:divList enabled="1" divType="" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100pt;' " all="Y"></org:divList>
									</c:otherwise>
									</c:choose>
								</div>											
							</td>
							<td class="searchTitle_center">컨설턴트명</td>
							<td class="searchField_center" colspan="3">
								<input type="text" name="writer" size="30" class="textInput_left" value="<c:out value="${writer }"/>">
							</td>
						</tr>									
					</table>
				<%@ include file="/common/include/searchBox_Footer.jsp"%>
				</form>
			</td>
		</tr>

		<tr>
			<td>
				<% out.println(tableGrid.getTotalHtml());%>
			</td>
		</tr>
		<tr><td height='7'></td></tr>
	</table>
</div>
<div style="display: none;">
	<div id="div1">
		<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100pt;' " all="Y"></org:divList>
	</div>
	<div id="div2">
		<org:divList enabled="1" divType="J" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100pt;' " all="Y"></org:divList>
	</div>
	<div id="div3">	
		<org:divList enabled="1" divType="C" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100pt;' " all="Y"></org:divList>
	</div>
	<div id="div4">
		<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100pt;' " all="Y"></org:divList>
	</div>
</div>
</body>
</html>