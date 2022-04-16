<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<%-- 공통js,css include --%>
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
	document.form.action = "/action/WeeklyReportAction.do?mode=getWeeklyReportList";
	document.form.submit();
}
function getWeeklyReportDetail(projectCode, assignYear, assignMonth, assignWeekOfMonth) {

	var url = "/action/WeeklyReportAction.do?mode=getWeeklyReport"
			+"&projectCode="+projectCode
			+"&assignYear="+assignYear
			+"&assignMonth="+assignMonth
			+"&assignWeekOfMonth="+assignWeekOfMonth
			+"&readonly=true";
	 window.open(url,'projectReport','top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=784,height=510,directories=no,menubar=no');
	 
	//document.form.target = "";		
	//document.form.submit();
}

function doSearch() {
	document.form.target = "";		 
	document.form.action = "/action/WeeklyReportAction.do?mode=getWeeklyReportList";
	document.form.submit();
}
function createWreport(){
	var ActionURL = "/action/WeeklyReportAction.do?mode=assignWeeklyReportByProjectCode&projectCode=<c:out value="${projectCode}"/>";
	var sFrm = document.form;
	var status = AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				var res = eval('(' + obj.responseText + ')').res;
				if(res == '1'){
					alert("주간진척관리 한 건을 생성했습니다.");					
				} else {
					alert("금주 차 주간진척관리가 존재합니다.");					
				}
				location.reload();
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){				
				alert("등록할 수 없습니다.");
			}
		}
	); status = null;	
}
</script>
</head>

<body>
			<% 
				String projectRole = (String)request.getAttribute("projectRole");
				String position = (String)request.getAttribute("position");
				String jobclass = (String)request.getAttribute("jobclass");
				String isRefresh = (String)request.getAttribute("isRefresh");
				String divRole = (String)request.getAttribute("divRole");
				String reqStr = "&projectRole="+projectRole+"&position="+position+"&jobclass="+jobclass;
			%>
	<form name="form" method="post">	
		<div style="display: none;">
			<input type="hidden" name="pageNo" id="pageNo"> 
			<input type="hidden" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" >
			<input type="hidden" name="viewMode" id="viewMode" value="<c:out value="${viewMode}"/>" >
		</div>

		<div id="tab_menu_content">
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">프로젝트 주간진척정보</p>
						<!-- <p class="term">[프로젝트 기간: <span>2019.09.01 ~ 2030.12.31</span>]</p> -->
						<!-- <button type="button" class="btn line btn_blue" onclick="location.href='javascript:;'">
							<i class="mdi mdi-square-edit-outline"></i>생성
						</button> -->
					</div>
				</div>
				<div class="board_contents">
					<div class="weekly_wrap">
						<div class="list">
							<!-- 날짜 조회 기능 임시 저장 -->
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
									<c:if test="${!empty weeklyReportList.list}">
										<c:forEach var="rs" items="${weeklyReportList.list}" varStatus="status">
											<li>
												<p class="weekly"><c:out value="${rs.assignMonth}"/>월 <c:out value="${rs.assignWeekOfMonth}"/>주차</p>
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
															<a href="#"  class="active btn-R"	id='<c:out value="${rs.projectCode}"/><c:out value="${rs.assignYear}"/><c:out value="${rs.assignMonth}"/><c:out value="${rs.assignWeekOfMonth}"/>' class="current"></a>
															<%-- <li><a href="#" class="current"	id='<c:out value="${rs.projectCode}"/>_<c:out value="${rs.assignYear}"/>_<c:out value="${rs.assignMonth}"/>_<c:out value="${rs.assignWeekOfMonth}"/>'></a></li> --%>
														</c:when>
														<c:otherwise>
															<a href="#" class="btn-R" id='<c:out value="${rs.projectCode}"/><c:out value="${rs.assignYear}"/><c:out value="${rs.assignMonth}"/><c:out value="${rs.assignWeekOfMonth}"/>'></a>
														</c:otherwise>
													</c:choose>
													</ul>
											 </li>
										</c:forEach>
									</c:if>
									<c:if test="${empty weeklyReportList.list}">
										<li>
											<ul class="weekly_detail">
												<li>주간진척관리가 없습니다.</li>
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
						<c:if test="${!empty weeklyReportList.list}">
							<script type="text/javascript">
									new TabbedPane('Process2', 
									{
										<c:forEach var="rs" items="${weeklyReportList.list}">
											'<c:out value="${rs.projectCode}"/><c:out value="${rs.assignYear}"/><c:out value="${rs.assignMonth}"/><c:out value="${rs.assignWeekOfMonth}"/>' : '/action/WeeklyReportAction.do?mode=getWeeklyReport&projectCode=<c:out value="${rs.projectCode}"/>&assignYear=<c:out value="${rs.assignYear}"/>&assignMonth=<c:out value="${rs.assignMonth}"/>&assignWeekOfMonth=<c:out value="${rs.assignWeekOfMonth}"/>&readonly=true',	
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