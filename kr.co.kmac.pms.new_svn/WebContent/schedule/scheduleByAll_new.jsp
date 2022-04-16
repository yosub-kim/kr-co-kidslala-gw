<%@ page contentType="text/html; charset=utf-8" import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=1600">
		<title>KMAC 통합 인트라넷</title>

		<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
		<link rel="stylesheet" href="/resources/css/board.css" type="text/css" /><!-- 서브페이지에서만 사용 -->	

		<script type="text/javascript" src="/resources/js/jquery-3.4.1.min.js"></script>
		<script type="text/javascript" src="/resources/js/jquery-ui.js"></script>
		<script type="text/javascript" src="/resources/js/common.js"></script>

		<!--[if lt IE 9]>
		<script type="text/javascript" src="/resources/js/html5shiv.js"></script>
		<![endif]-->
 
	</head>

	<body>	

		<div class="location">
			<p class="menu_title">일정관리</p>
			<ul>
				<li class="home">HOME</li>
				<li>스케쥴관리</li>
				<li>일정관리</li>
			</ul>
		</div>

		<div class="contents sub"><!-- 서브 페이지 .sub -->

			<div class="fixed_box">
				<div class="title a-l">
					<div class="h1_area">
						<p class="h1"><i class="mdi mdi-file-document-outline"></i>일정관리</p>
					</div>								
				</div>
				<%-- 
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
				 --%>
				<div class="fixed_contents schedule_view sc"><!-- 스케줄 관리 컨텐츠 요소 .schedule_view -->

					<!-- 전사 스케줄 관리 -->
					<div class="board_box schedule">
						
						<div class="board_contents">

							<div class="schedule_head">
								<%--
								<ul class="tab_menu_link">
									<li><a href="/schedule/scheduleView.html" class="active" title="현재 페이지">요약</a></li>
									<li><a href="/schedule/myscheduleView.html">월간</a></li>
								</ul>
								 --%>
								<div class="month_select">
									<input type="hidden" name="mode" value="scheduleByAll">
									<button type="button" onclick="location.href='javascript:;'"><i class="mdi mdi-chevron-left"></i></button>
									<div class="select_area">
										<select name="selectedYear" class="select" >
											<c:forEach begin="${intYear - 2 }" end="${intYear + 1 }" var="year">
												<option value="<c:out value="${year}"/>" <c:if test="${year == intYear }">selected</c:if>><c:out value="${year}"/></option>
											</c:forEach>
										</select>
										<select name="selectedMonth" class="select" >
											<option value="1" <c:if test="${'01' == intMonth }">selected</c:if>>1</option>
											<option value="2" <c:if test="${'02' == intMonth }">selected</c:if>>2</option>
											<option value="3" <c:if test="${'03' == intMonth }">selected</c:if>>3</option>
											<option value="4" <c:if test="${'04' == intMonth }">selected</c:if>>4</option>
											<option value="5" <c:if test="${'05' == intMonth }">selected</c:if>>5</option>
											<option value="6" <c:if test="${'06' == intMonth }">selected</c:if>>6</option>
											<option value="7" <c:if test="${'07' == intMonth }">selected</c:if>>7</option>
											<option value="8" <c:if test="${'08' == intMonth }">selected</c:if>>8</option>
											<option value="9" <c:if test="${'09' == intMonth }">selected</c:if>>9</option>
											<option value="10" <c:if test="${'10' == intMonth }">selected</c:if>>10</option>
											<option value="11" <c:if test="${'11' == intMonth }">selected</c:if>>11</option>
											<option value="12" <c:if test="${'12' == intMonth }">selected</c:if>>12</option>
										</select>
										<select name="jobClass" class="select" >
											<option value="A" <c:if test="${jobClass == 'A' }"> selected</c:if>>상근</option>
											<%-- <option value="B" <c:if test="${jobClass == 'B' }"> selected</c:if>>상근(2)</option> --%>
											<option value="J" <c:if test="${jobClass == 'J' }"> selected</c:if>>상임</option>
											<%-- <option value="H" <c:if test="${jobClass == 'H' }"> selected</c:if>>AA(Ⅰ~Ⅲ)</option> --%>
											<option value="H2" <c:if test="${jobClass == 'H2' }"> selected</c:if>>RA</option>
											<%-- <option value="C" <c:if test="${jobClass == 'C' }"> selected</c:if>>엑스퍼트</option> --%>
										</select>															
									</div>
									<button type="button" onclick="location.href='javascript:;'"><i class="mdi mdi-chevron-right"></i></button>
								</div>
								<div class="search_input">
									<label for="search"></label>
									<input type="text" name="search" id="search" placeholder="검색어를 입력하세요." title="검색어를 입력하세요.">
									<button type="button" class="btn btn_blue" onclick="location.href='javascript:;'">검색</button>									
								</div>
							</div>										

							<div class="gantt_box">
								<div class="list">
									<div class="gantt_title">
										<p>소속부서<br />└ 성명(직급/직책)</p>
										<p class="all-open-text">전체오픈</p>
										<!-- <button type="button" class="btn btn_blue" onclick="location.href='javascript:;'">전체오픈</button>	 -->
									</div>
									<div class="dropdown_list_wrap">
										<ul class="dropdown_list all-open">
											<c:forEach var="group" items="${groupList}" varStatus="i">
												<li class="on">
													<div class="drop_title_list"><a href="javascript:;" title="메뉴 보이기/숨기기"><p><c:out value="${group.groupName}"/></p></a></div>
													<div class="drop_data_list sc">
														<ul>
															<c:forEach var="user" items="${group.userList}" varStatus="idx">
																<li><span class="profile"><img src="/resources/img/photo_none.png" alt="프로필 이미지"></span><a href="javascript:;"><c:out value="${user.userName}"/></a></li>
															</c:forEach>
														</ul>
													</div>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div>
								<div class="view">	
									<div class="week">
										<c:forEach var="weekDay" varStatus="i" items="${weekDays}">
											<div class="<c:out value="${weekDay.dayOfTheWeek}"/> <c:if test="${weekDay.today eq true}"> today</c:if>"
												><c:out value="${weekDay.dayOfTheWeekKor}"/></div>
										</c:forEach>
									</div>
									
									<div class="week">
										<c:forEach var="weekDay" varStatus="i" items="${weekDays}">
											<div class="<c:out value="${weekDay.dayOfTheWeek}"/>"><c:out value="${i.index + 1 }"/></div>
										</c:forEach>									
									</div>
									
									<div class="dropdown_list_wrap">
										<ul class="dropdown_list">
											<c:forEach var="group" items="${groupList}" varStatus="i">
												<li class="on">
													<div class="drop_title_list">
														<div class="task">
															<c:forEach var="weekDay" varStatus="i" items="${weekDays}">
																<div class="<c:if test="${weekDay.today eq true}"> today</c:if>"></div>
															</c:forEach>
														</div>
													</div>
													<c:forEach var="user" items="${group.userList}" varStatus="idx">
														<div class="drop_data_list sc">
															<div class="task task_sub">
																<c:forEach var="weekDay" varStatus="i" items="${weekDays}">
																	<div class="<c:if test="${weekDay.today eq true}"> today</c:if>"
																	><c:out value="${user.scheduleDailyMasterInfo.dayArr[i.index] }"/></div>
																</c:forEach>													
																<%-- 
																<div class="ico-1 select"></div><!-- 사업관리 .ico-1 -->
																<div class="ico-2 select"></div><!-- 교육참석 .ico-2 -->
																<div class="ico-3 select"></div><!-- 재택근무 .ico-3 -->
																<div class="ico-4 select"></div><!-- 프로젝트 투입 .ico-4 -->
																<div class="ico-5 select"></div><!-- 휴가 .ico-5 -->
																<div class="group">
																	<ul>
																		<li class="ico-1 select"></li>
																		<li class="ico-2 select"></li>
																		<li class="ico-4 select"></li>
																	</ul>
																</div>
																--%>
															</div>
														</div>
													</c:forEach>
												</c:forEach>
											</li>
										</ul>
									</div>
								</div>
							</div>													
							<!-- // gantt_box -->

						</div>
						<!-- // board_contents-->
					</div>
					<!-- // 전사 스케줄 관리 -->				
					
				</div>	
				<!-- // fixed_contents -->
			</div>
			<!-- // fixed_box -->			

		</div>
		<!-- // contents -->
											
		<div class="footer">
			<p>Copyright  ⓒ 2021 KMAC. All rights reserved</p>
		</div>
		<!-- // footer -->		


	</body>
</html>
