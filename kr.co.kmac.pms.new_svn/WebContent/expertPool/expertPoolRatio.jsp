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
function openExpertDetail(ssn){
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}
function doSearch() {
	if (document.frm.dateFrom.value > document.frm.dateTo.value) {
		alert("종료일이 시작일을 이전 일 수 없습니다.");
		return;
	}
	frm.submit();
}
function goView(mm,ssn,gubun,kname,jobclass){
	var url="http://intranet.kmac.co.kr/kmac/projectexpert/IndividualProjectSchedule.asp?mm="+mm+"&gubun="+gubun+"&kname="+kname+"&jobClass="+jobclass+"&ssn="+ssn;
	window.open(url, 'expertRatioDetailView', 'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=820,height=450,directories=no,menubar=no');
}
</script>
</head>

<body>
<form name="frm" method="post">
	<input type="hidden" name="ssn">
	<input type="hidden" name="gubun">
	<input type="hidden" name="kname">
	<input type="hidden" name="mm">
	<input type="hidden" name="jobclass">
		<table width="100%" cellpadding="0" cellspacing="0">
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12">
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="전문가 그룹 실 가동률 구분 현황" />
					</jsp:include>
				</td>
			</tr>

			<!-- 검색 영역 -->
			<tr>
				<td>
					<%@ include file="/common/include/searchBox_Header.jsp"%>
						<table border="0" width="100%">
							<colgroup> 
								<col width="100px">
								<col width="200px"> 
								<col width="100px">
								<col width="*"> 
							</colgroup>
							<tr>
								<td class="searchTitle_center">실가동률구분</td>
								<td class="searchField_left">
									<select name="ratioType" class="selectbox" style="width:100%">
										<option value="">전체</option>
										<option value="ratioType3" <c:if test="${ratioType == 'ratioType3' }">selected</c:if>>50% 이하</option>
										<option value="ratioType2" <c:if test="${ratioType == 'ratioType2' }">selected</c:if>>51% 이상 80%이하</option>
										<option value="ratioType1" <c:if test="${ratioType == 'ratioType1' }">selected</c:if>>81% 이상</option>
									</select>
								</td>
								<td class="searchTitle_center">기간</td>
								<td class="searchField_left">
									<script>
										jQuery(function(){jQuery( "#dateFrom" ).datepicker({});});
										jQuery(function(){jQuery( "#dateTo" ).datepicker({});});
									</script>
									<fmt:parseDate value="${dateFrom}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
									<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
									<fmt:parseDate value="${dateTo}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
									<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
									<input type="text" name="dateFrom" id="dateFrom" size="8" value="<c:out value="${start}" />" /> 
									~ <input type="text" name="dateTo" id="dateTo" size="8" value="<c:out value="${end}" />" />
								</td>
							</tr>									
						</table>
				<%@ include file="/common/include/searchBox_Footer.jsp"%>
				</td>
			</tr>			
						
			<!-- 본문 리스트 시작 -->			
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table class="listTable" style="table-layout: fixed">
									<colgroup>
										<col width="120px">
										<col width="70px">
										<col width="130px" align="left">
										<col width="*" align="left">
										<col width="60px">
										<col width="45px">
									</colgroup>
									<thead>
										<tr> 
											<td>소속</td>
											<td>성명</td>
											<td>전문분야</td>
											<td>솔루션</td>
											<td>실가동률</td>
											<td>투입<br/>현황</td>
										</tr>
									</thead>									
									<tbody id="ListData">
										<c:forEach var="rs" items="${result.list}">
											<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
												<td class="myoverflowC"><c:out value="${rs.deptName}" /></td>
												<td><a href="javascript:openExpertDetail('<c:out value="${rs.ssn }"/>')"><c:out value="${rs.name}" /></a></td>
												<td><c:out value="${rs.consultingFunction}" escapeXml="false"/></td>
												<td><c:out value="${rs.consultingMajor}" escapeXml="false"/></td>
												<td><c:out value="${rs.runningRatio}" /></td>
												<td><img style="cursor: pointer;" onclick="goView('','<c:out value="${rs.ssn}"/>','M','<c:out value="${rs.name}"/>','J')" alt="투입현황" src="/images/btn_glass_y.jpg"/></td>
											</tr>
										</c:forEach>
										<c:if test="${empty result.list}"><tr><td colspan="6" align="center">검색 결과가 존재하지 않습니다.</td></tr></c:if>
									</tbody>
								</table>
							</td>
						</tr>

<!-- 본문종료-->
					</table>
				</td>
			</tr>
		</table>
</form>
</body>

</html>					