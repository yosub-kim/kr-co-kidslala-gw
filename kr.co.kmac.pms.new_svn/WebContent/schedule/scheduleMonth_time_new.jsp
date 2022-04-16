<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<title>개인근무시간</title>

	<!--[if lt IE 9]>
	<script type="text/javascript" src="/resources/js/html5shiv.js"></script>
	<![endif]-->
	<script type="text/javascript">
	</script> 
</head>
<body>
	
	<div class="location">
		<p class="menu_title">개인일정</p>
		<ul>
			<li class="home">HOME</li>
			<li>스케줄관리</li>
			<li>개인일정</li>
		</ul>
	</div>
		
		<div class="contents sub">
		
			<div class="fixed_box">
				<div class="title a-l">
					<div class="h1_area">
						<p class="h1"><i class="mdi mdi-file-document-outline"></i>일정관리</p>
					</div>
				</div>
				<div class="schedule_status">
					<ul>
						<li class="total"><a href="javascript:;" class="current">전체(30)</a></li><!-- 선택 값 .current -->
						<li class="ico ico-1"><a href="javascript:;">사업관리(3)</a></li>
						<li class="ico ico-2"><a href="javascript:;">교육참석(2)</a></li>
						<li class="ico ico-3"><a href="javascript:;">재택근무(4)</a></li>
						<li class="ico ico-4"><a href="javascript:;">프로젝트 투입(5)</a></li>
						<li class="ico ico-5"><a href="javascript:;">휴가(5)</a></li>
					</ul>
					<ul>
						<li><a href="javascript:;">현장출근(30)</a></li>
						<li><a href="javascript:;">현장퇴근(5)</a></li>
						<li><a href="javascript:;">현장출/퇴근(6)</a></li>
					</ul>
					<ul>
						<li><a href="javascript:;">외근(30)</a></li>
						<li><a href="javascript:;">고객정보 등록(5)</a></li>
					</ul>
				</div>
				
				<div class="fixed_contents schedule_view sc">
					<div class="board_box schedule">
						<div class="board_contents">
							<div class="schedule_head">
								<div class="month_select">
									<button type="button" onclick="location.href='javascript:movePrevious();'"><i class="mdi mdi-chevron-left"></i></button>
									<div class="select_area">
										<select name="selectedYear" onchange="this.form.submit();">
											<c:forEach begin="${intYear - 2}" end="${intYear + 1}" var="year">
												<option value="<c:out value="${year}"/>" <c:if test="${year == intYear }">selected</c:if>><c:out value="${year}"/></option>
											</c:forEach>
										</select>										
										<select name="selectedMonth" onchange="this.form.submit();">
											<c:forEach begin="1" end="12" var="month">
												<option value="<c:out value="${month}"/>" <c:if test="${month == intMonth }">selected</c:if>><c:out value="${month}"/></option>
											</c:forEach>
										</select>   
										<button type="button" onclick="location.href='javascript:moveNext();'"><i class="mdi mdi-chevron-right"></i></button>
									</div>
								</div>
								<div class="search_input">
									<label for="search"></label>
									<input type="text" name="search" id="search" placeholder="검색어를 입력하세요." title="검색어를 입력하세요." />
									<button type="button" class="btn btn_blue" onclick="location.href='javascript:;'">검색</button>									
								</div>
							</div>	
							<div class="gantt_box">
								<div class="view month_box">
									<div class="week">
										<div class="sun">일요일</div>
										<div class="mon">월요일</div>
										<div class="tue">화요일</div>
										<div class="wed">수요일</div>
										<div class="thu">목요일</div>
										<div class="fri">금요일</div>
										<div class="sat">토요일</div>
									</div>
									<c:forEach var="week" items="${calendar}">
										<div class="month">
											<c:forEach var="nDay" items="${week}" varStatus="i">
												<div class="<c:out value="${nDay.dayOfWeekStr}"/>">
													<c:choose>
														<c:when test="${!empty nDay.holidayLst}">
															<p class="day_off">
																<c:forEach var="holidayInfo" items="${nDay.holidayLst}">
																	<span class="day"><c:out value="${holidayInfo.holidayName}" /></span><span><c:out value="${nDay.day}"/></span>
																</c:forEach>
															</p>
														</c:when>
														<c:when test="${intMonth eq '12' && nDay.day eq '21' }">
															<span class="day">창립기념일</span><span><c:out value="${nDay.day}"/></span>
														</c:when>
														<c:otherwise>
															<p><span class="day"></span><span><c:out value="${nDay.day}"/></span></p>
														</c:otherwise>
													</c:choose>
													<ul>
													
														<c:if  test="${!empty nDay.dailyScheduleLst_time}"> 
															<c:forEach var="dailyScheduleInfo" items="${nDay.dailyScheduleLst_time}">
																<li class="ico-1"><a href="javascript:;" 
																	onclick="javascript:layer_open(this,'pop_register');"
																	><c:out value="${dailyScheduleInfo.startHour}"/>:<c:out value="${dailyScheduleInfo.startMin}"/> ~ 
																			<c:out value="${dailyScheduleInfo.endHour}"/>:<c:out value="${dailyScheduleInfo.endMin}"/></a></li>
															</c:forEach>
														</c:if>
														
													</ul>
												</div>
											</c:forEach>
										</div>							
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
				
	</body>
</html>	

