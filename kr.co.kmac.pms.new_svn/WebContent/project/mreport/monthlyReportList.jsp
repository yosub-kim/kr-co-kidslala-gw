<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
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
function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/MonthlyReportAction.do?mode=getMonthlyReportList";
	document.form.submit();
}
function getMonthlyReportDetail(projectCode, assignYear, assignMonth, assignWeekOfMonth, writerSsn) {

	var url = "/action/MonthlyReportAction.do?mode=getMonthlyReport"
			+"&projectCode="+projectCode
			+"&assignYear="+assignYear
			+"&assignMonth="+assignMonth
			+"&assignWeekOfMonth="+assignWeekOfMonth
			+"&writerSsn="+writerSsn
			+"&readonly=true";
	 window.open(url,'projectReport','top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=784,height=510,directories=no,menubar=no');
	 
	//document.form.target = "";		
	//document.form.submit();
}
function doSearch() {
	document.form.target = "";		 
	document.form.action = "/action/MonthlyReportAction.do?mode=getMonthlyReportList";
	document.form.submit();
}

</script>
</head>

<body>
	<form name="form" method="post">	
		<div style="display: none;">
			<input type="hidden" name="pageNo" id="pageNo"> 
			<input type="hidden" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" >
			<input type="hidden" name="viewMode" id="viewMode" value="<c:out value="${viewMode}"/>" >
		</div>
		
		<div id="tab_menu_content">
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">프로젝트 레포트</p>
						<!-- <p class="term">[프로젝트 기간: <span>2019.09.01 ~ 2030.12.31</span>]</p> -->
						<!-- <button type="button" class="btn line btn_blue" onclick="location.href='javascript:;'">
							<i class="mdi mdi-square-edit-outline"></i>생성
						</button> -->
					</div>
				</div>
				<div class="board_contents">
					<div class="weekly_wrap">
						<div class="list">
							<%-- <div class="month_check">
								<div class="input_date">
									<input type="text" name="fromDate" id="fromDate" value="<c:out value="${fromDate}"/>" > 
								</div>
								<span>~</span>
								<div class="input_date">
									<input type="text" name="toDate" id="toDate" value="<c:out value="${toDate}"/>" >
								</div>
								<input type="text" name="writerName" placeholder="작성자 검색" value="<c:out value="${writerName}"/>" onkeypress="if(event.keyCode == 13) doSearch();">
								<button type="button" class="btn btn_blue" onclick="doSearch_weeklyReport();"><i class="mdi mdi-magnify"></i></button>
								<script>
									jQuery(function(){jQuery( "#fromDate" ).datepicker({});});
									jQuery(function(){jQuery( "#toDate" ).datepicker({});});
								</script>
							</div> --%>
							<div class="weekly_list sc">
								<ul>
									<c:if test="${!empty monthlyReportList.list}">
										<c:forEach var="rs" items="${monthlyReportList.list}" varStatus="status">
											<li>
												<p class="weekly"><c:out value="${rs.assignYear }"/>년 <c:out value="${rs.assignMonth}"/>월 </p>
													<div style="display: none;">
														<fmt:parseDate value="${rs.writeDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="v1"/>
														<fmt:formatDate value="${v1}" pattern="yyyy-MM-dd" var="writeDate"/>
														<fmt:parseDate value="${rs.revieweDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="v3"/>
														<fmt:formatDate value="${v3}" pattern="yyyy-MM-dd" var="revieweDate"/>
														<fmt:parseDate value="${rs.approveDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="v4"/>
														<fmt:formatDate value="${v4}" pattern="yyyy-MM-dd" var="approveDate"/>
													</div>										
													<ul class="weekly_detail">
														<li>작성자: <c:out value="${rs.writerName}"/>/ <span><c:out value="${writeDate}"/></span></li>
														<li>검토자: <c:out value="${rs.reviewerName}"/>/ <span><c:out value="${revieweDate}"/></span></li>
														<li>승인자: <c:out value="${rs.approverName}"/>/ <span><c:out value="${approveDate}"/></span></li>
													</ul>
													<ul>
													<c:choose>
														<c:when test="${status.index eq '0'}">
															<li><a href="#"  class="active btn-R"	id='<c:out value="${rs.projectCode}"/><c:out value="${rs.assignYear}"/><c:out value="${rs.assignMonth}"/><c:out value="${rs.assignWeekOfMonth}"/>' class="current"></a></li>
														</c:when>
														<c:otherwise>
															<li><a href="#" class="btn-R" id='<c:out value="${rs.projectCode}"/><c:out value="${rs.assignYear}"/><c:out value="${rs.assignMonth}"/><c:out value="${rs.assignWeekOfMonth}"/>'></a></li>
														</c:otherwise>
													</c:choose>
													</ul>
											 </li>
										</c:forEach>
									</c:if>
									<c:if test="${empty monthlyReportList.list}">
										<li>
											<ul class="weekly_detail">
												<li>프로젝트 레포트가 없습니다.</li>
											</ul>
										</li>
									</c:if>
								</ul>
							</div>
							<!-- // weekly_list -->
						</div>
						<!-- // list -->	
						
						<div class="view">
							<div id="Process_overlay" class="overlay" style="display: none">
								<img alt="" src="/images/loading.gif" align="middle">
							</div>
							<div id="Process2" class="pane"></div>
						</div>
						<!-- // view -->
						<!-- 실행품의 Tab구조 확인할 것 -->
						<c:if test="${!empty monthlyReportList.list}">
							<script type="text/javascript">
									new TabbedPane('Process2', 
									{
										<c:forEach var="rs" items="${monthlyReportList.list}">
											'<c:out value="${rs.projectCode}"/><c:out value="${rs.assignYear}"/><c:out value="${rs.assignMonth}"/><c:out value="${rs.assignWeekOfMonth}"/>' : '/action/MonthlyReportAction.do?mode=getMonthlyReport&projectCode=<c:out value="${rs.projectCode}"/>&assignYear=<c:out value="${rs.assignYear}"/>&assignMonth=<c:out value="${rs.assignMonth}"/>&assignWeekOfMonth=<c:out value="${rs.assignWeekOfMonth}"/>&writerSsn=<c:out value="${rs.writerSsn }"/>&readonly=true',	
										</c:forEach>
									},
										{
											onClick: function(e) {
												$('Process_overlay').show();
										},
											onSuccess: function(e) {
												$('Process_overlay').hide();
										}
									});	
							</script>
					   </c:if>
					</div>												
				</div>
			</div>
		</div>
</form>
</body>

</html>					