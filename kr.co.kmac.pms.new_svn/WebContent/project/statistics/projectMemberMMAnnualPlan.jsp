<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="java.util.List"%>
<%@page import="kr.co.kmac.pms.project.statistics.data.ProjectMemberAnnualMM"%>
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

function goDetail(ssn, year, type, month){
	var width="800";
	var height="500";

	var url = "/project/statistics/projectMemberMMPlanFrame.jsp?mode=getProjectMemberMMPlan"
		+ "&ssn=" + ssn
		+ "&year=" + year
		+ "&type=" + type 
		+ "&month=" + month;
	
	var sFeatures = "top=100,left=100,width="+width+",height="+height+",resizable=yes,scrollbars=yes";
	detailWin1 = window.open(url,"detail", sFeatures);
	detailWin1.focus();
}


function doSearch(){
	var type = document.projectMemberMMPlanForm.type.value;
	document.projectMemberMMPlanForm.target = "";
	document.projectMemberMMPlanForm.action = "/action/ProjectMemberMMPlanAction.do?mode=getProjectMemberMMAnnualPlan&type=" + type;
	document.projectMemberMMPlanForm.submit();
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

</script>
</head>
<%
	//리스트 오브젝트 
	List<ProjectMemberAnnualMM> list = ((ValueList)request.getAttribute("list")).getList();
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" class='listTable' style='table-layout: fixed; width: 100%' ");
	tableGrid.setHeadAnnex(false);	// true 이면 Header Cell 병합
	tableGrid.setAnnex(true); 		// true 이면 Cell 병합
	String year = request.getAttribute("year").toString();
	String type = request.getAttribute("type").toString();

	//헤더 정의 
	Row headerRow = new Row();

	headerRow.addCell(new Cell("소속").setAttribute(" width='*' "));
	headerRow.addCell(new Cell("성명").setAttribute(" width='80px' "));
	headerRow.addCell(new Cell("구분").setAttribute(" width='105px' "));
	headerRow.addCell(new Cell("1월").setAttribute(" width='35px' "));		
	headerRow.addCell(new Cell("2월").setAttribute(" width='35px' "));
	headerRow.addCell(new Cell("3월").setAttribute(" width='35px' "));
	headerRow.addCell(new Cell("4월").setAttribute(" width='35px' "));
	headerRow.addCell(new Cell("5월").setAttribute(" width='35px' "));
	headerRow.addCell(new Cell("6월").setAttribute(" width='35px' "));
	headerRow.addCell(new Cell("7월").setAttribute(" width='35px' "));
	headerRow.addCell(new Cell("8월").setAttribute(" width='35px' "));
	headerRow.addCell(new Cell("9월").setAttribute(" width='35px' "));
	headerRow.addCell(new Cell("10월").setAttribute(" width='35px' "));
	headerRow.addCell(new Cell("11월").setAttribute(" width='35px' "));
	headerRow.addCell(new Cell("12월").setAttribute(" width='35px' "));
	tableGrid.addHeadRow(headerRow);
	
	Row row = null;
	if(list == null || list.size() > 0){
		for(ProjectMemberAnnualMM mm : list){
			row = new Row();
			String style = "";
			String style1 = "";
			if (mm.getProjectTypeName().equals("합계(M/M)")) {
				style = "style='background-color:#F2F1EE' ";
			}
			row.addCell(new Cell(mm.getDeptName()).setAttribute(" align='center' "));
			row.addCell(new Cell(mm.getName()).setAttribute(" align='center' "));
			row.addCell(new Cell(mm.getProjectTypeName()).setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail('" + mm.getSsn() + "\',\'" + year + "\',\'" + type + "\','01')\">" + (Math.round(Float.valueOf(mm.getM01()) * 100) / 100.0) +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail('" + mm.getSsn() + "\',\'" + year + "\',\'" + type + "\','02')\">" + (Math.round(Float.valueOf(mm.getM02()) * 100) / 100.0) +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail('" + mm.getSsn() + "\',\'" + year + "\',\'" + type + "\','03')\">" + (Math.round(Float.valueOf(mm.getM03()) * 100) / 100.0) +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail('" + mm.getSsn() + "\',\'" + year + "\',\'" + type + "\','04')\">" + (Math.round(Float.valueOf(mm.getM04()) * 100) / 100.0) +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail('" + mm.getSsn() + "\',\'" + year + "\',\'" + type + "\','05')\">" + (Math.round(Float.valueOf(mm.getM05()) * 100) / 100.0) +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail('" + mm.getSsn() + "\',\'" + year + "\',\'" + type + "\','06')\">" + (Math.round(Float.valueOf(mm.getM06()) * 100) / 100.0) +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail('" + mm.getSsn() + "\',\'" + year + "\',\'" + type + "\','07')\">" + (Math.round(Float.valueOf(mm.getM07()) * 100) / 100.0) +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail('" + mm.getSsn() + "\',\'" + year + "\',\'" + type + "\','08')\">" + (Math.round(Float.valueOf(mm.getM08()) * 100) / 100.0) +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail('" + mm.getSsn() + "\',\'" + year + "\',\'" + type + "\','09')\">" + (Math.round(Float.valueOf(mm.getM09()) * 100) / 100.0) +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail('" + mm.getSsn() + "\',\'" + year + "\',\'" + type + "\','10')\">" + (Math.round(Float.valueOf(mm.getM10()) * 100) / 100.0) +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail('" + mm.getSsn() + "\',\'" + year + "\',\'" + type + "\','11')\">" + (Math.round(Float.valueOf(mm.getM11()) * 100) / 100.0) +"</a>").setAttribute(" align='center'  " + style ));
			row.addCell(new Cell("<a href=\"javascript:goDetail('" + mm.getSsn() + "\',\'" + year + "\',\'" + type + "\','12')\">" + (Math.round(Float.valueOf(mm.getM12()) * 100) / 100.0) +"</a>").setAttribute(" align='center'  " + style ));

			tableGrid.addRow(row); 
		}	
		
	}else{
		row = new Row(); 
		row.addCell(new Cell("검색 결과가 없습니다 .").setAttribute(" colspan='15' align='center' "));
		tableGrid.addRow(row); 
	}
	tableGrid.tableCheck();
	
%>
<script type="text/javascript">
function openTip() {
	var configWin;
	configWin = window.open("/project/statistics/OpenTip.jsp","configWin", "top=100,left=100,width=675,height=150,scrollbars=no");
	configWin.focus();
}
</script>

<body style="overflow-x: hidden; overflow-y: auto">
<form name="projectMemberMMPlanForm" method="post">
<input type="hidden" name="expenseMode" value="<c:out value="${expenseMode}"/>">
<input type="hidden" name="searchOK">
<input type="hidden" name="type" id="type" value="<c:out value="${type}"/>">

	<table width="100%" cellpadding="0" cellspacing="0">
		
		<!-- 타이틀 영역 -->
		
		<tr>
			<td height="12">
			<table cellSpacing="0" width="100%" border="0" style="table-layout:fixed">
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
					<jsp:param name="title" value="상임 컨설턴트 월별 투입현황" />
				</jsp:include>
				<td align="right" width="200" height="10">
					<a class="btN3fac0c txt2btn" href="javascript:openTip();">투입률 표시기준 및 수정방법</a>
				</td>
			</table>
			</td>
		</tr>
		
		
		<!-- 검색 영역 -->
		<tr>
			<td height="21" align="left" valign="top">
				<%@ include file="/common/include/searchBox_Header.jsp"%>
					<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<colgroup>
							<col width="85" />
							<col width="250" />
							<col width="85" />
							<col width="*" />
						</colgroup>
						<tr>
							<td class="searchTitle_center">구분</td>
							<td class="searchField_left">
								<select	name="jobClass" id="jobClass" class="selectbox" style="width: 100%" onchange="changeDiv(this.value);">
									<option value="A"
										<c:if test="${jobClass == 'A' }">selected</c:if>>상근</option>
									<option value="J"
										<c:if test="${jobClass == 'J' }">selected</c:if>>상임</option>
									<option value="C"
										<c:if test="${jobClass == 'C' }">selected</c:if>>엑스퍼트</option>
								</select>
								<td class="searchTitle_center">연도</td>
								<td class="searchField_left">
									<date:year beforeYears="2" afterYears="2" attribute=" name='year' class='selectbox' style='width:100%' " selectedInfo="${year}" />
								</td>
							</td>
						</tr>
						<tr>
							<td class="searchTitle_center">소속</td>
							<td class="searchField_left">
								<div id="div0">
									<c:choose>
									<c:when test="${jobClass=='A'}">
										<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' style='width:100%' class='selectbox'" all="Y"></org:divList>
									</c:when>
									<c:when test="${jobClass=='J'}">
										<org:divList enabled="1" divType="J" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' style='width:100%' class='selectbox'" all="Y"></org:divList>
									</c:when>
									<c:when test="${jobClass=='C'}">
										<org:divList enabled="1" divType="C" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' style='width:100%' class='selectbox'" all="Y"></org:divList>
									</c:when>
									<c:otherwise>
										<org:divList enabled="1" divType="" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' style='width:100%' class='selectbox'" all="Y"></org:divList>
									</c:otherwise>
									</c:choose>
								</div>								
							</td>
							<td class="searchTitle_center">성명</td>
							<td class="searchField_left">
								<input type="text" name="name" id="name" size="10" class="textInput_left" value="<c:out value="${name}"/>">
								
							</td>
						</tr>
					</table>
				<%@ include file="/common/include/searchBox_Footer.jsp"%>
			</td>
		</tr>
	<!-- 검색부분 종료-->	
	<!-- 본문시작-->
		<tr>
			<td>
				<% out.println(tableGrid.getTotalHtml());%>
			</td>
		</tr>
		<tr><td height='7'></td></tr>
	</table>
</form>
<div style="display: none;">
	<div id="div1">
		<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' style='width:100%' class='selectbox'" all="Y"></org:divList>
	</div>
	<div id="div2">
		<org:divList enabled="1" divType="J" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' style='width:100%' class='selectbox'" all="Y"></org:divList>
	</div>
	<div id="div3">	
		<org:divList enabled="1" divType="C" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' style='width:100%' class='selectbox'" all="Y"></org:divList>
	</div>
	<div id="div4">
		<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' style='width:100%' class='selectbox'" all="Y"></org:divList>
	</div>
</div>
</body>
</html>