<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.project.statistics.data.ExpenseRealTimeResultDetailEtc"%>
<%@page import="java.util.List"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>예상결산 상세 내역</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<% float totalSalary = 0; %>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

</script>
</head>
<%
	//리스트 오브젝트 
	List<ExpenseRealTimeResultDetailEtc> list = ((ValueList)request.getAttribute("list")).getList();
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" class='listTable' cellpadding='0' cellspacing='0' width='551' ");
	tableGrid.setHeadAnnex(false); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합
	
	//헤더 정의 
	Row headerRow = new Row();
	headerRow.addCell(new Cell("구분").setAttribute(" width='90px' "));
	headerRow.addCell(new Cell("성명").setAttribute(" width='80px' "));
	headerRow.addCell(new Cell("인력별 예상결산").setAttribute(" width='*' "));
	headerRow.addCell(new Cell("순번").setAttribute(" width='45px' "));
	headerRow.addCell(new Cell("성과급(입력값)").setAttribute(" width='90' "));
	headerRow.addCell(new Cell("상태").setAttribute(" width='70' "));
	
	tableGrid.addHeadRow(headerRow);
	String grandTotalRealTimeSalary = ""; 
	
	Row row = null; 
	if(list == null || list.size() > 0){
		for(ExpenseRealTimeResultDetailEtc entity : list){
			grandTotalRealTimeSalary = entity.getGrandTotalAmount();
			row = new Row();
			row.attribute = "onmouseover='row_over(this)' onmouseout='row_out(this)'";
			row.addCell(new Cell(entity.getJobClass()).setAttribute(" align='center' "));
			row.addCell(new Cell(entity.getName()).setAttribute(" align='center' "));
			row.addCell(new Cell(entity.getTotalAmount()+"&nbsp;").setAttribute(" align='right' "));
			row.addCell(new Cell(entity.getSeq()).setAttribute(" align='center' "));
			row.addCell(new Cell(entity.getAmount()).setAttribute(" align='right' "));
			String state= "";
			if(entity.getApprovalYN().equals("Y")){
				state = "품의 완료";
				row.addCell(new Cell(state).setAttribute(" align='center' bgcolor='#d6eafd' "));
			} else {
				state = "품의 전";
				row.addCell(new Cell(state).setAttribute(" align='center' "));
			}
			tableGrid.addRow(row); 
		}		
	}else{
		row = new Row(); 
		row.addCell(new Cell("상세 내역이 없습니다.").setAttribute(" align='center' colspan='6' " ));
		tableGrid.addRow(row); 
	}
	tableGrid.tableCheck(); 

%>
<body>
<form name="form" method="post">	
	<input type="hidden" name="pageNo"> 
	<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>" >

	<table width="551" cellpadding="0" cellspacing="5">
		<tr>
			<td width="551" valign="top">
				<table width="551" cellpadding="0" cellspacing="0"> 

					<!-- sub 타이틀 영역 시작-->  
					<tr>
						<td>
							<table width="551" height="24" border="0" cellpadding="0" cellspacing="0">
								<tr>
									
									<td width="100%" align="left" valign="middle"><span class="subTitle">&nbsp;예상결산 상세 내역</span></td>
								</tr>
							</table>
						</td>
					</tr>
					<!-- sub 타이틀 영역 종료--> 
				 	
					<tr><td height='10'></td></tr>
					
					<tr><td height='18'><strong>예상결산 총액: <%=grandTotalRealTimeSalary %> 원 [<c:out value="${list.valueListInfo.totalNumberOfEntries}"/> 건] </strong></td></tr> 		 			
					<tr>
						<td>
							<% out.println(tableGrid.getTotalHtml());%>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>						
</form>
</body>

</html>					