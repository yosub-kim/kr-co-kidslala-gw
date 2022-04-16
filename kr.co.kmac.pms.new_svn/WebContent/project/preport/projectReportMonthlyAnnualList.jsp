<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function doSearch() {
	document.form.target = "";		 
	document.form.action = "/action/ProjectReportSearchAction.do?mode=getProjectReportMonthlyAnnualList&year="+$('year').value;
	document.form.submit();
}
</script>
</head>

<body>
<form name="form" method="post">	

	<table width="751" cellpadding="0" cellspacing="0">
		<tr>
			<td width="751" valign="top">
				<table width="751" cellpadding="0" cellspacing="0"> 

					<!-- sub 타이틀 영역 시작-->  
					<tr>
						<td>
							<table width="751" height="24" border="0" cellpadding="0" cellspacing="0">
								<tr><td height='10' colspan="3"></td></tr>
								<tr>
									
									<td width="100%" align="left" valign="middle"><span class="subTitle">&nbsp;본부별 상임 컨설턴트 &amp; 엑스퍼트 월별 작성건 수</span></td>
								</tr>
							</table>
						</td>
					</tr>
					<!-- sub 타이틀 영역 종료--> 
					<!-- 검색 영역 -->
					<tr>
						<td height="21" align="left" valign="top">
							<table border="0" cellpadding="0" cellspacing="0" width="751" > 
								<tr>
									<td width="681">						
										<table border="0" width="681" style="border-collapse: collapse; ">
											<colgroup> 
												<col width="100px">
												<col width="*">
											</colgroup>
											<tr>
												<td class="searchTitle_center">기간</td> 
												<td class="searchField_left">
													<date:year beforeYears="2" afterYears="2" attribute=" name='year' id='year' style='width: 80px;' " hasAll="N" selectedInfo="${year }"/>
												</td>
											</tr>
										</table>
									</td>
									<td align="center" width="70" class="searchField_center" style="border-left-width: 0px;"
										><a href="#" onclick="doSearch()"><img src="/images/btn_search.jpg"></a></td>
								</tr>
							</table>
						</td>
					
					</tr>					
				 	
				 	
					<tr><td height='10'></td></tr>
					<%--
					<tr>
						<td width="751">
							<table class='751' cellpadding="0" cellspacing="0">
							<tr>
								<td><b><font class='SF'><c:out value="${projectReportList.valueListInfo.totalNumberOfEntries}"/> Total- Page(<c:out value="${projectReportList.valueListInfo.pagingPage}"/> of <c:out value="${projectReportList.valueListInfo.totalNumberOfPages}"/>)</font></b></td>
							</tr>
							</table>
						</td>
					</tr>
					<tr><td height='5'></td></tr> 					
					--%>
					
					
					
					<tr>
						<td>
							<table id="projectReportTable" class='listTable' width="751" cellpadding="0" cellspacing="0">
								<thead id="projectReportTableHeader">
									<tr> 
										<td width="*" rowspan="2">소속</td>
										<td width="90px" rowspan="2">구분</td>
										<td width="50px" colspan="2">1월</td> 
										<td width="50px" colspan="2">2월</td> 
										<td width="50px" colspan="2">3월</td> 
										<td width="50px" colspan="2">4월</td> 
										<td width="50px" colspan="2">5월</td> 
										<td width="50px" colspan="2">6월</td> 
										<td width="50px" colspan="2">7월</td> 
										<td width="50px" colspan="2">8월</td> 
										<td width="50px" colspan="2">9월</td> 
										<td width="50px" colspan="2">10월</td> 
										<td width="50px" colspan="2">11월</td> 
										<td width="50px" colspan="2">12월</td> 
									</tr>  
									<tr> 
										<td width="25px">지도일지</td> 
										<td width="25px">투입인원</td> 
										<td width="25px">지도일지</td> 
										<td width="25px">투입인원</td> 
										<td width="25px">지도일지</td> 
										<td width="25px">투입인원</td> 
										<td width="25px">지도일지</td> 
										<td width="25px">투입인원</td> 
										<td width="25px">지도일지</td> 
										<td width="25px">투입인원</td> 
										<td width="25px">지도일지</td> 
										<td width="25px">투입인원</td> 
										<td width="25px">지도일지</td> 
										<td width="25px">투입인원</td> 
										<td width="25px">지도일지</td> 
										<td width="25px">투입인원</td> 
										<td width="25px">지도일지</td> 
										<td width="25px">투입인원</td> 
										<td width="25px">지도일지</td> 
										<td width="25px">투입인원</td> 
										<td width="25px">지도일지</td> 
										<td width="25px">투입인원</td> 
										<td width="25px">지도일지</td> 
										<td width="25px">투입인원</td> 
									</tr>  
								</thead>									
								<tbody id="projectReportTableBody"> 
									<c:if test="${!empty projectReportMonthlyAnnualList}">
										<c:forEach var="rs" items="${projectReportMonthlyAnnualList}" varStatus="status">
											<tr onmouseover="row_over(this)" onmouseout="row_out(this)" onclick="">
												<c:if test="${status.count % 2 == 1 }">
													<td align="center" rowspan="2"><c:out value="${rs.deptName}"/></td> 
												</c:if>
												<td align="center"><span style="font-size: 11px;"><c:out value="${rs.jobClassName}"/></span></td> 
												<td align="center"><c:out value="${rs.preportCnt01}"/></td> 
												<td align="center"><c:out value="${rs.memberCnt01}"/></td> 
												<td align="center"><c:out value="${rs.preportCnt02}"/></td> 
												<td align="center"><c:out value="${rs.memberCnt02}"/></td> 
												<td align="center"><c:out value="${rs.preportCnt03}"/></td> 
												<td align="center"><c:out value="${rs.memberCnt03}"/></td> 
												<td align="center"><c:out value="${rs.preportCnt04}"/></td> 
												<td align="center"><c:out value="${rs.memberCnt04}"/></td> 
												<td align="center"><c:out value="${rs.preportCnt05}"/></td> 
												<td align="center"><c:out value="${rs.memberCnt05}"/></td> 
												<td align="center"><c:out value="${rs.preportCnt06}"/></td> 
												<td align="center"><c:out value="${rs.memberCnt06}"/></td> 
												<td align="center"><c:out value="${rs.preportCnt07}"/></td> 
												<td align="center"><c:out value="${rs.memberCnt07}"/></td> 
												<td align="center"><c:out value="${rs.preportCnt08}"/></td> 
												<td align="center"><c:out value="${rs.memberCnt08}"/></td> 
												<td align="center"><c:out value="${rs.preportCnt09}"/></td> 
												<td align="center"><c:out value="${rs.memberCnt09}"/></td> 
												<td align="center"><c:out value="${rs.preportCnt10}"/></td> 
												<td align="center"><c:out value="${rs.memberCnt10}"/></td> 
												<td align="center"><c:out value="${rs.preportCnt11}"/></td> 
												<td align="center"><c:out value="${rs.memberCnt11}"/></td> 
												<td align="center"><c:out value="${rs.preportCnt12}"/></td> 
												<td align="center"><c:out value="${rs.memberCnt12}"/></td> 
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${empty projectReportMonthlyAnnualList}">
										<tr>
											<td align="center" colspan="26">지도일지가 없습니다.</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>						
</form>
</body>

</html>					