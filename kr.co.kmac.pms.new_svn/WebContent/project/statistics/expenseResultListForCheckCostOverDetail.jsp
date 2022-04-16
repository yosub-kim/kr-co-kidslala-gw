<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.project.preport.data.ProjectReportListEntity"%>
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
	List<ProjectReportListEntity> list = ((ValueList)request.getAttribute("list")).getList(); 
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" class='listTable' cellpadding='0' cellspacing='0' width='751' ");
	tableGrid.setHeadAnnex(false); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합
	
	//헤더 정의 
	Row headerRow = new Row();	
	headerRow.addCell(new Cell("구분").setAttribute(" width= '45px' "));
	headerRow.addCell(new Cell("작성자").setAttribute(" width='45px' ")); 
	headerRow.addCell(new Cell("인력별 예상결산").setAttribute(" width='*' ")); 
	headerRow.addCell(new Cell("지도일자").setAttribute(" width='65px' "));
	headerRow.addCell(new Cell("RATE").setAttribute(" width='30' "));
	headerRow.addCell(new Cell("예상 MD").setAttribute(" width='*' ")); 
	headerRow.addCell(new Cell("작성일").setAttribute(" width='65px' ")); 
	headerRow.addCell(new Cell("검토자").setAttribute(" width='45px' ")); 
	headerRow.addCell(new Cell("검토일").setAttribute(" width='65px' ")); 
	headerRow.addCell(new Cell("승인자").setAttribute(" width='45px' "));  
	headerRow.addCell(new Cell("승인일").setAttribute(" width='65px' "));
	headerRow.addCell(new Cell("상태").setAttribute(" width='40px' "));
	
	tableGrid.addHeadRow(headerRow);
	String grandTotalRealTimeSalary = ""; 
	
	Row row = null; 
	if(list == null || list.size() > 0){
		for(ProjectReportListEntity entity : list){
			grandTotalRealTimeSalary = entity.getGrandTotalRealTimeSalary();
			row = new Row();
			row.attribute = "onmouseover='row_over(this)' onmouseout='row_out(this)'";
			row.addCell(new Cell(entity.getJobClass()).setAttribute(" align='center' "));
			row.addCell(new Cell(entity.getWriterName()).setAttribute(" align='center' "));
			row.addCell(new Cell(entity.getTotalRealTimeSalary()+"&nbsp;").setAttribute(" align='right' "));
			row.addCell(new Cell(!StringUtil.isNotNull(entity.getAssignDate()) ? "" : entity.getAssignDate().substring(0, 4)+"-"+entity.getAssignDate().substring(4, 6)+"-"+entity.getAssignDate().substring(6, 8)).setAttribute(" align='center' "));
			row.addCell(new Cell(entity.getPayAmount()).setAttribute(" align='center' "));
			row.addCell(new Cell(entity.getRealtimeSalary()).setAttribute(" align='center' "));
			row.addCell(new Cell(!StringUtil.isNotNull(entity.getWriteDate()) ? "" : entity.getWriteDate().substring(0, 4)+"-"+entity.getWriteDate().substring(4, 6)+"-"+entity.getWriteDate().substring(6, 8)).setAttribute(" align='center' "));
			row.addCell(new Cell(entity.getReviewerName()).setAttribute(" align='center' "));
			row.addCell(new Cell(!StringUtil.isNotNull(entity.getRevieweDate()) ? "" : entity.getRevieweDate().substring(0, 4)+"-"+entity.getRevieweDate().substring(4, 6)+"-"+entity.getRevieweDate().substring(6, 8)).setAttribute(" align='center' "));
			row.addCell(new Cell(entity.getApproverName()).setAttribute(" align='center' "));
			row.addCell(new Cell(!StringUtil.isNotNull(entity.getApproveDate()) ? "" : entity.getApproveDate().substring(0, 4)+"-"+entity.getApproveDate().substring(4, 6)+"-"+entity.getApproveDate().substring(6, 8)).setAttribute(" align='center' "));
			String state= "";
			if(entity.getState().equals("complete")){
				state = "확정";
				row.addCell(new Cell(state).setAttribute(" align='center' bgcolor='#d6eafd' "));
			} else if (entity.getState().equals("reject")){
				state = "반려";
				row.addCell(new Cell(state).setAttribute(" align='center' bgcolor='#ffcccc' "));
			} else {
				state = "예정";
				row.addCell(new Cell(state).setAttribute(" align='center' "));
			}
			tableGrid.addRow(row); 
		}		
	}else{
		row = new Row(); 
		row.addCell(new Cell("상세 내역이 없습니다.").setAttribute(" align='center' colspan='12' " ));
		tableGrid.addRow(row); 
	}
	tableGrid.tableCheck(); 
%>
<body>
<form name="form" method="post">	
	<input type="hidden" name="pageNo"> 
	<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>" >

	<table width="751" cellpadding="0" cellspacing="5">
		<tr>
			<td width="751" valign="top">
				<table width="751" cellpadding="0" cellspacing="0"> 

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
					
					<tr><td height='18'><strong>프로젝트형 예상결산 총액: <c:out value="${list2.list[0].grandTotalAmount}" /> 원  [<c:out value="${list2Records}"/> 건] </strong></td></tr> 		 			
					<tr>
						<td>
							<table width="551" class="listTable" cellspacing="0" cellpadding="0">
								<thead>
									<tr null="">
										<td width="90">구분</td>
										<td width="80">성명</td>
										<td width="*">인력별 예상결산</td>
										<td width="45">순번</td>
										<td width="90">성과급(입력값)</td>
										<td width="70">상태</td>
									</tr>
								</thead>
								<tbody style="width:551;max-height:100px;overflow:auto;">
						<c:choose>
							<c:when test="${!empty list2.list}">
								<c:forEach var="rs" items="${list2.list}">								
									<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
										<td align="center"><c:out value="${rs.jobClass}" /></td>
										<td align="center"><c:out value="${rs.name}" /></td>
										<td align="right"><c:out value="${rs.totalAmount}" /></td>
										<td align="center"><c:out value="${rs.seq}" /></td>
										<td align="right"><c:out value="${rs.amount}" /></td>
								<c:choose>
									<c:when test="${rs.approvalYN == 'Y' }">
										<td align="center" style="background-color: #d6eafd">품의 완료</td>
									</c:when>
									<c:otherwise>
										<td align="center">품의 전</td>
									</c:otherwise>
								</c:choose>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
									<tr><td align="center" colspan="6">검색된 데이터가 없습니다.</td></tr>
							</c:otherwise>
						</c:choose>
								</tbody>
							</table>						
						</td>
					</tr> 
				 	
					<tr><td height='10'></td></tr>
					
					<tr><td height='18'><strong>지도형, 교육형 예상결산 총액: <%=grandTotalRealTimeSalary %> 원 [<c:out value="${listRecords}"/> 건] </strong></td></tr> 		 			
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