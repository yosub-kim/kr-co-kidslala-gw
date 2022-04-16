<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ include file="/common/include/taglib.jsp"%>
<%
	//리스트 오브젝트 
	List<ExpenseRealTimeResultDetailForSanction> list = (List)request.getAttribute("result");
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" class='listTable' cellpadding='0' cellspacing='0' width='765' border='1' style='table-layout: fixed;' ");
	tableGrid.setHeadAnnex(false); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합

	//헤더 정의 
	Row headerRow = new Row();
	headerRow.setAttribute(" style='background:silver; text-align:center; font-weight:bold;' ");
	headerRow.setAttribute(" style='height:38px;' ");
	headerRow.addCell(new Cell("구분").setAttribute(" width='60' "));
	headerRow.addCell(new Cell("이름").setAttribute(" width='70' ")); 
	headerRow.addCell(new Cell("주민번호").setAttribute(" width='60' "));
	headerRow.addCell(new Cell("금액").setAttribute(" width='70' "));
	headerRow.addCell(new Cell("금액(70%)").setAttribute(" width='70' "));
	headerRow.addCell(new Cell("금액(30%)").setAttribute(" width='70' "));
	headerRow.addCell(new Cell("프로젝트").setAttribute(" width='210' "));
	headerRow.addCell(new Cell("PJT소계<br>(성과급)").setAttribute(" align='center' width='80' "));
	headerRow.addCell(new Cell("지급 성과급<br>누계(예상)").setAttribute(" align='center' width='80' "));
	/* headerRow.addCell(new Cell("성과급<br>예산").setAttribute(" align='center' width='70' ")); */
	headerRow.addCell(new Cell("유형").setAttribute(" align='center' width='50' "));
	tableGrid.addHeadRow(headerRow);
	
	Row row = null;
	if(list != null && list.size() > 0){
		for(ExpenseRealTimeResultDetailForSanction data : list){
			row = new Row().setAttribute(" row='salary' ");
			row.addCell(new Cell(data.getSalaryJobClassDesc()).setAttribute("  "));
			row.addCell(new Cell(data.getSalaryName()).setAttribute(" style='text-align:center;' "));
			row.addCell(new Cell(data.getSalaryUid()).setAttribute(" align='center' "));
 			row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryTotalRealTimeSalary())).setAttribute(" style='padding: 7px 2px 7px 2px; text-align: right' "));
 			/*상근 (2) 성과급 화면 변화*/
 			if(data.getSalaryJobClassDesc().equals("상근(2)") || (data.getSalaryJobClassDesc().equals("상근") && (data.getSalaryName().equals("오진영") || data.getSalaryName().equals("고두균") 
 					|| data.getSalaryName().equals("이호성") || data.getSalaryName().equals("권해상") || data.getSalaryName().equals("김희철") || data.getSalaryName().equals("이유택")))){
 				row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryTotalRealTimeSalary() * 7 / 10)).setAttribute(" style='padding: 7px 2px 7px 2px; text-align: right' "));
 				row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryTotalRealTimeSalary() * 3 / 10)).setAttribute(" style='padding: 7px 2px 7px 2px; text-align: right' "));
 			}else{
 				row.addCell(new Cell("-"));
 				row.addCell(new Cell("-"));
 			}
 			row.addCell(new Cell("[" + data.getSalaryProjectCode() + "] " + data.getSalaryProjectName()).setAttribute(" style='padding: 7px 2px 7px 2px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; text-align: left'"));
 			row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryRealTimeSalaryEachProject())).setAttribute("  style='padding: 7px 2px 7px 2px; text-align: right'  "));
 			row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryCumulativeRealTimeSalary())).setAttribute("  style='padding: 7px 2px 7px 2px; text-align: right'  "));
 			/* row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryBudgetEachProject())).setAttribute("  style='padding: 7px 2px 7px 2px; text-align: right'  ")); */
 			row.addCell(new Cell(data.getSalaryApprovalType()).setAttribute("  style='padding: 7px 2px 7px 2px; text-align: center'  "));
			tableGrid.addRow(row); 
		}
	}else{
		row = new Row(); 
		row.addCell(new Cell("검색 결과가 존재하지 않습니다. ").setAttribute(" align='center' colspan='10' " ));
		tableGrid.addRow(row); 
	}
	tableGrid.tableCheck(); 
	
%>

<table width="765" border="1">
	<tr>
		<td><% out.println(tableGrid.getTotalHtml());%></td>
	</tr>
</table>