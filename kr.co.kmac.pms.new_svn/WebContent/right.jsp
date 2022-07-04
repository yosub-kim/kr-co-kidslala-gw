<%@page import="kr.co.kmac.pms.schedule.data.DailyScheduleInfo"%>
<%@page import="kr.co.kmac.pms.companySchedule.data.CompanyScheduleInfoData"%>
<%@page import="kr.co.kmac.pms.schedule.data.DailyProjectInfo"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="kr.co.kmac.pms.expertpool.manager.ExpertPoolManager"%>
<%@page import="kr.co.kmac.pms.expertpool.data.ExpertPool"%>
<%@page import="kr.co.kmac.pms.common.util.SessionUtils"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	ExpertPoolManager expertPoolManager = null;
	if (wac != null) {
		expertPoolManager = (ExpertPoolManager) wac.getBean("expertPoolManager");
	}
	ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));

	
	String userId = expertPool.getUserId();
%>
<html lang="ko">
<head>;
<title>KMAC</title>
<!-- <meta name="Generator" content="EditPlus" charset="UTF-8"> -->
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<meta name="viewport" content="width=1600">
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/main.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" /><!-- 서브페이지에서만 사용 -->	
<!--[if lt IE 9]>
      <script type="text/javascript" src="../resources/js/html5shiv.js"></script>
      <![endif]-->
<script>
	function changeContentFrame(id, url) {
		var status1 = AjaxRequest
				.post({
					'url' : "/action/ExpertPoolCheckAction.do?mode=isPasswordValid",
					'onSuccess' : function(obj) {
						if (obj.responseText.indexOf("<html>") > 0) {
							top.document.location.href = "/<c:url value='j_acegi_homepage_logout'/>";
						}
						var res = eval('(' + obj.responseText + ')');
						if (res.needToPasswordUpdate == "true") {
							alert("통합 인트라넷 사용자의 정보보호 및 시스템 보안 향상을 위해 비밀번호를 변경하세요 !");
							top.document.location.href = "/pwalert.jsp"
						} else {
							j$(".leftMenu").css("display", "none");
							j$("#" + id).show();
							document.getElementById("_contentFrame").src = url;
						}
					},
					'onLoading' : function(obj) {
					},
					'onError' : function(obj) {
						alert("session 정보를 읽어올 수 없습니다.");
						top.location.href = "/j_acegi_logout";
					}
				});

	}

	function selectWork(workId, type) {
		var ActionURL = "/action/WorkCabinetAction.do?mode=getWorkType";
		var status = AjaxRequest.post({
			'url' : ActionURL,
			'parameters' : {
				"workTypeId" : type
			},
			'onSuccess' : function(obj) {
				var res = eval('(' + obj.responseText + ')');
				var rsObj = res.workType.formUrl;
				document.location.href = rsObj + "&workId=" + workId;
			},
			'onLoading' : function(obj) {
			},
			'onError' : function(obj) {
				alert("저장할 수 없습니다.");
			}
		});
		status = null;
	}

	function sanctionItemClick(projectCode, approvalType, seq, workType,
			woriTypeUrl) {
		location.href = woriTypeUrl + "&readonly=true&projectCode="
				+ projectCode + "&approvalType=" + approvalType + "&seq=" + seq;
	}

	function goOnlineBudget(username) {
		document.getElementById("onlineBudgetForm").action = "https://newbudget.kmac.co.kr:8080/com/login_chk.jsp";
		document.getElementById("onlineBudgetForm").empno.value = username;
		document.getElementById("onlineBudgetForm").target = "new";
		document.getElementById("onlineBudgetForm").submit();
	}

	function goBizplay_popup() {
		userwidth = (screen.width - 1);
		userheight = (screen.height - 1);

		window
				.open(
						'https://www.bizplay.co.kr',
						'bizplay',
						'scrollbars=yes,toolbar=yes,location=yes,status=yes,menubar=yes,resizable=yes,width='
								+ userwidth
								+ ',height='
								+ userheight
								+ ',left=0,top=0');
	}

	function goKDB_popup() {
		userwidth = (screen.width - 1);
		userheight = (screen.height - 1);

		window
				.open(
						'https://intranet.kmac.co.kr/kmac/ks/hub.asp',
						'new_kdb_pop',
						'scrollbars=yes,toolbar=yes,location=yes,status=yes,menubar=yes,resizable=yes,width='
								+ userwidth
								+ ',height='
								+ userheight
								+ ',left=0,top=0');
	}

	function goTMC_popup(userId) {
		userwidth = (screen.width - 1);
		userheight = (screen.height - 1);

		window
				.open(
						'https://intranet.kmac.co.kr/tmc_box/index.asp?user_id='
								+ userId,
						'new_kdb_pop',
						'scrollbars=yes,toolbar=yes,location=yes,status=yes,menubar=yes,resizable=yes,width='
								+ userwidth
								+ ',height='
								+ userheight
								+ ',left=0,top=0');
	}

	function goBlind_popup() {
		window
				.open(
						"https://intranet.kmac.co.kr/kmac/task/blind_board/board_list.asp",
						"",
						"toolbar=no,status=no,width=795,height=600,directories=no,scrollbars=1,location=no,resizable=no,menubar=no,screenX=0,left=0,screenY=0,top=0,right=0");
	}

	function goEducationManagement_popup() {
		window.open("https://kmacadmin.kmac.co.kr");
	}

	function goSchedule_popup() {
		changeContentFrame('__1',
				'https://intranet.kmac.co.kr/kmac/task/projectshare_board/Board_list.asp');
	}

	function openProject() {
		document.getElementById('_contentFrame').height = 4000;
		changeContentFrame('___1',
				'https://intranet.kmac.co.kr/kmac/comlist/suggest_filelist.asp');
	}

	function goEmail() {
		var email = "<authz:authentication operation="email" />";
		if (email == "") {
			alert("등록된 메일 주소가 없습니다. 관리자에게 문의하세요.");
		} else {
			var status = AjaxRequest
					.post({
						'url' : "/action/ExpertPoolCheckAction.do",
						'parameters' : {
							"mode" : "getEmailPassword",
							"ssn" : "<authz:authentication operation="username" />"
						},
						'onSuccess' : function(obj) {
							var res = eval('(' + obj.responseText + ')');
							webmailForm.user_account.value = email;
							webmailForm.pass.value = res.password;
							webmailForm.cmd.value = "mail_link_2";
							webmailForm.action = "https://webmail.kmac.co.kr/a_biz/login.nvd";
							webmailForm.target = "webmail";
							popup = window
									.open(
											'',
											'webmail',
											'toolbar=yes,location=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes,left=0,top=0,width=1024,height=768');
							popup.focus();
							webmailForm.submit();
						},
						'onLoading' : function(obj) {
						},
						'onError' : function(obj) {
							alert("저장할 수 없습니다.");
						}
					});
		}
	}
	function goProjectSearchPage(runningDivCode, projectState, delayState, costOver, endProcess, hasDate)  {
		var params="";
		if(runningDivCode != '' && runningDivCode != undefined){
			params = params + "&runningDeptCode="+runningDivCode;
		}
		if(projectState != '' && projectState != undefined){
			params = params + "&projectState="+projectState;
		}
		if(delayState != '' && delayState != undefined){
			params = params + "&delayState="+delayState;
		}
		if(costOver != '' && costOver != undefined){	
			params = params + "&costOver="+costOver;
		}
		if(endProcess != '' && endProcess != undefined){
			params = params + "&endProcess="+endProcess;
		}
		if(hasDate == 'yes'){
			params = params + "&realStartDate="+$('startYear').value+$('startMonth').value;
			params = params + "&realEndDate="+$('endYear').value+$('endMonth').value;
		}
		// 비즈니스 유형 변수에 값이 없으면 BTA로 처리되므로 빈값을 넘긴다
		var businessTypeCode="";
		params = params + "&businessTypeCode="+businessTypeCode;
		
		//parent.document.location.href = "/action/ProjectSearchAction.do?mode=getProjectSearchList&backButtonYN=Y"+params;
		var url="/action/ProjectSearchAction.do?mode=getProjectSearchList"+params;
		window.open(url, 'pjList', 'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=800,height=580,directories=no,menubar=no');
	}
</script>
</head>

<!-- form tag -->
<form id="sitemap" name="sitemap" style="display: none;">
	<input type="hidden" name="siteMapHtml" id="siteMapHtml">
</form>
<form name="webmailForm" method="post" style="display: none;">
	<input name="user_account" value="" type="hidden"> <input
		name="pass" value="" type="hidden"> <input name="cmd" value=""
		type="hidden">
</form>
<form name="onlineBudgetForm" method="post" id="onlineBudgetForm">
	<input name="empno" value="" type="hidden">
</form>
<%
	Object mailCount = request.getAttribute("mailCount");
	Object workCount = request.getAttribute("workCount");
	Object mreportCount = request.getAttribute("mreportCount");
	Object wreportCount = request.getAttribute("wreportCount");
%>	

<body>
<div class="contents">
			<div class="section_both">
				<div class="section_L">
					<c:forEach var="item" items="${myNormalGadgetList }"
						varStatus="status">
						<c:choose>
							<c:when test="${status.index eq '0'}">
								<div class="section_box project_status">
									<div class="box">
										<div class="title">
											<p class="h1">
												<i class="mdi mdi-projector-screen-outline"></i><c:out value="${item.gadgetName }" />
											</p>
											<p class="more">
												<a href="<c:out value="${item.linkUrl }"/>" title="더 보기"><i class="mdi mdi-dots-horizontal"></i></a>
											</p>
										</div>
										<div class="status_ui">

											<div class="sc">
												<!-- 스크롤바 -->
												<ul>
													<!-- 부문 -->
													<li class="status_title">
														<c:out value="${item.tableHeader}" escapeXml="false" />
													</li>
													<!-- 부문 -->

													<!-- contents -->
													<c:forEach var="t" items="${item.contentList }">
														<li>
															<div class="status">
																<div class="subject">
																	<span class='<c:out value="${t.info0Style}" />'><c:out value="${t.info0Style2 }" /></span>
																	<!-- 가치혁신부문 .value  -->
																	<c:choose>
																		<c:when test="${t.specialStyle eq 'N' }">
																			<p><a href="#" onclick="location.href='<c:out value="${t.info3Style2 }" />'"><c:out value="${t.info0 }" /></a></p>
																		</c:when>
																		<c:otherwise>
																			<p><a href="#" onclick="location.href='<c:out value="${t.info1Style2 }" />'"><c:out value="${t.info0 }" /></a></p>
																		</c:otherwise>
																	</c:choose>
																</div>
																<div class="detail">
																	<ul>
																		<li>
																			<c:choose>
																				<c:when test="${t.specialStyle eq 'N' }">
																					<p class="<c:out value="${t.info1Style }" />"><c:out value="${t.info1 }" /></p> <!-- 진행 .ing -->
																				</c:when>
																				<c:otherwise>
																					<p class="<c:out value="${t.info1Style }" />">
																						<a href="#" onclick="location.href='<c:out value="${t.info1Style2 }" />'"><c:out value="${t.info1 }" /></a>
																					</p>
																				</c:otherwise>
																			</c:choose>
																		</li>
																		<li>
																			<c:choose>
																				<c:when test="${t.specialStyle eq 'N' }">
																					<%-- <p class="<c:out value="${t.info2Style }" />"><c:out value="${t.info2 }" /></span></p> --%>
																					<p class="<c:out value="${t.info2Style }" />"><c:out value="${t.info2Style2 }" /><span class="day">(<c:out value="${t.info2 }" />)</span></p> <!-- 진행 .ing -->
																				</c:when>
																				<c:otherwise>
																					<p class="<c:out value="${t.info2Style }" />">
																						<a href="#" onclick="location.href='<c:out value="${t.info2Style2 }" />'"><c:out value="${t.info2 }" /></a>
																					</p>
																				</c:otherwise>
																			</c:choose>
																		</li>
																		<li>
																			<c:choose>
																				<c:when test="${t.specialStyle eq 'N' }">
																					<p><a href="#" onclick="location.href='<c:out value="${t.info3Style2 }" />'"><i class="<c:out value="${t.info3 }"/>"></i></a><p>
																				</c:when>
																				<c:otherwise>
																					<p class="<c:out value="${t.info3Style }" />">
																						<a href="#" onclick="location.href='<c:out value="${t.info3Style2 }" />'"><c:out value="${t.info3 }" /></a>
																					</p> <!-- 변동사항 .change  -->
																				</c:otherwise>
																			</c:choose>
																		</li>
																	</ul>
																</div>
															</div> <!-- status -->
														</li>
													</c:forEach>
												</ul>
											</div>
											<!-- sc -->
										</div>
										<!-- status_ui -->
									</div>
								</div>
								<div class="section_box board">
							</c:when>

							<c:otherwise>
								<div class="box">
									<div class="title">
										<p class="h1">
											<c:choose>
												<c:when test="${status.index eq '1' }">
													<i class="mdi mdi-bell-outline"></i>
												</c:when>
												<c:otherwise>
													<i class="mdi mdi-account-group-outline"></i>
												</c:otherwise>
											</c:choose>
											<c:out value="${item.gadgetName }" />
										</p>
										<p class="more">
											<a href="<c:out value="${item.linkUrl }"/>" title="더 보기"><i
												class="mdi mdi-dots-horizontal"></i></a>
										</p>
									</div>
									<c:choose>
										<c:when test="${item.contentListSize > 0}">
											<div class="status_ui">
												<ul>
													<c:forEach var="t" items="${item.contentList }">
														<li><a href="<c:out value="${t.linkUrl }"/>"><span></span>
														<c:if test="${t.info0Style eq 'new' }"><span class="new"><font color="white">N</font></span> </c:if><c:out value="${t.info0 }" /></a>
														<span><c:out value="${t.info2}" /></span></li>
													</c:forEach>
												</ul>
											</div>
										</c:when>
										<c:otherwise>
											<div class="status_ui">
												<ul>
													<li><span>결과가 존재하지 않습니다.</span></li>
												</ul>
											</div>
										</c:otherwise>
									</c:choose>
								</div>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</div>

			<div class="section_R">
				<div class="my_status">
					
					<!-- 메일 / 새 업무 고정 -->
					<div>
						<%-- <% if (expertPool.getRole().equals("ROLE2006080116041070759") || expertPool.getRole().equals("ROLE2006080116061636366") || expertPool.getRole().equals("ROLE17404E902CA")
						 || expertPool.getRole().equals("ROLE125B1491C42") || expertPool.getRole().equals("ROLE2006080118352520784") || expertPool.getRole().equals("ROLE2006050120451853989") || expertPool.getRole().equals("ROLE2006080116033211358")
						 || expertPool.getRole().equals("ROLE17A73D54752") || expertPool.getRole().equals("ROLE179AB746279")) {%>
						<ul class="line">
							<a href="javascript:goEmail();">
							<li>	
								<div class="ico">										
									<i class="mdi mdi-email"></i>
									<div>
										<p class="subject_kr">메일</p>
									</div>
								</div>
								<p class="subject_count"><span><c:out value="${mailCount }" /></span>건</p>											
							</li>
							</a>
							<a href="/action/WorkCabinetAction.do?mode=getMyWorkList">
							<li>	
								<div class="ico">										
									<i class="mdi mdi-playlist-edit"></i>
									<div>
										<p class="subject_kr">업무</p>
									</div>
								</div>
								<p class="subject_count"><span><c:out value="${workCount }" /></span>건</p>											
							</li>
							</a>
						</ul>
						<ul class="line">
							<a href="/action/WeeklyReportCabinetAction.do?mode=getMyWeeklyReportList">
							<li>	
								<div class="ico">										
									<i class="mdi mdi-calendar-weekend"></i>
									<div>
										<p class="subject_kr">주간진척관리</p>
									</div>
								</div>
								<p class="subject_count"><span><c:out value="${wreportCount }" /></span>건</p>											
							</li>
							</a>
							<a href="/action/MonthlyReportCabinetAction.do?mode=getMyMonthlyReportList">
							<li>	
								<div class="ico">										
									<i class="mdi mdi-file-chart-outline"></i>
									<div>
										<p class="subject_kr">프로젝트레포트</p>
									</div>
								</div>
								<p class="subject_count"><span><c:out value="${mreportCount }" /></span>건</p>											
							</li>
							</a>
						</ul>
						<%} else { %> --%>
							<%-- <ul>
								<a href="javascript:goEmail();">
								<li>											
									<i class="mdi mdi-email"></i>
									<p class="subject_kr">메일</p>
									<p class="subject_en">Email</p>
									<p class="subject_count"><span><c:out value="${mailCount }" /></span>건</p>											
								</li>
								</a>
								<a href="/action/WorkCabinetAction.do?mode=getMyWorkList">
								<li>											
									<i class="mdi mdi-playlist-edit"></i>
									<p class="subject_kr">업무</p>
									<p class="subject_en">Task</p>
									<p class="subject_count"><span><c:out value="${workCount }" /></span>건</p>											
								</li>
								</a>
							</ul> --%>
						<%-- <%} %> --%>
					</div>

					<c:forEach var="item" items="${myWideGadgetList }" varStatus="status">
						<c:choose>
							<c:when test="${status.index eq '0'}">
								<c:forEach var="t" items="${item.contentList }" varStatus="counts" >
									<c:choose>
										<c:when test="${t.specialStyle eq 'N' }" >
											<c:if test="${counts.index eq '0' }" >
												<div class="section_box">
													<div class="box confirm emergency"><!-- Emergency 프로젝트 현황 .emergency -->
														<div class="title">
															<p class="h1"><i class="mdi mdi-car-brake-alert"></i>Emergerncy 프로젝트 현황</p>
															<p class="more"><a href="<c:out value="${item.linkUrl }"/>" title="더 보기"><i class="mdi mdi-dots-horizontal"></i></a></p>
														</div>
														<div class="status_ui">
			
															<div class="sc"><!-- 스크롤바 -->
																<div class="confirm_status">
																	<div class="progress">
																	    <div class="progress-bar" data-per=<c:out value="${t.info0 }" /> data-bgcolor="#c870d8"></div><!-- 변경 사항 #c870d8 -->
																	</div>
																	<p>
																		<span class="progress-tit"><a href="#" onclick="location.href='<c:out value="${t.info0Style }" />'">일정 지연</a></span>
																		<span class="progress-label"><span class="c-change"><c:out value="${t.info0 }" /></span><span class="grey">/100</span><!-- 변경 사항 .c-change -->
																	</p>
																</div>
																<div class="confirm_status">
																	<div class="progress">
																	    <div class="progress-bar" data-per=<c:out value="${t.info1 }" /> data-bgcolor="#f089a4"></div><!-- 변경 사항 #c870d8 -->
																	</div>
																	<p>
																		<span class="progress-tit"><a href="#" onclick="location.href='<c:out value="${t.info1Style }" />'">평가 지연</a></span>
																		<span class="progress-label"><span class="c-delay"><c:out value="${t.info1 }" /></span><span class="grey">/100</span><!-- 변경 사항 .c-change -->
																	</p>
																</div>
																<div class="confirm_status">
																	<div class="progress">
																	    <div class="progress-bar" data-per=<c:out value="${t.info2 }" /> data-bgcolor="#ffb731"></div><!-- 예산 초과 #ffb731 -->
																	</div>
																	<p>
																		<%-- <span><c:choose><c:when test="${t.info2 > 0 }"><a href="#" onclick="location.href='<c:out value="${t.info2Style }" />'">예산 초과</a></c:when><c:otherwise>예산 초과</c:otherwise></c:choose></span> --%>
																		<span><a href="#" onclick="location.href='<c:out value="${t.info2Style }" />'">예산 초과</a></span>
																		<span class="progress-label"><span class="c-over"><c:out value="${t.info2 }" /></span><span class="grey">/100</span><!-- 예산 초과 .c-over -->
																	</p>
																</div>
															</div>
															<!-- // sc -->
														</div>
													</div>
												</div>
											</c:if>
										</c:when>
												
										<c:otherwise>
											<c:if test="${counts.index eq '0' }" >
												<div class="section_box">
													<div class="box confirm">
														<div class="title">
															<p class="h1">
																<i class="mdi mdi-emoticon-outline"></i><c:out value="${item.gadgetName }" />
															</p>
															<!-- 프로젝트 고객만족도 .mdi-emoticon-outline -->
															<p class="more">
																<a href="<c:out value="${item.linkUrl }"/>" title="더 보기"><i
																	class="mdi mdi-dots-horizontal"></i></a>
															</p>
														</div>
														<div class="status_ui">
															<div class="sc">
																<!-- 스크롤바 -->
																<c:forEach var="t" items="${item.contentList }" varStatus="status">
																	<div class="confirm_status">
																		<div class="progress">
																			<div class="progress-bar" data-per='<c:out value="${t.info2 }" />'
																				data-bgcolor='<c:out value="${t.info4 }" />'></div>
																		</div>
																		<p>
																			<a href="<c:out value="${t.linkUrl }"/>"><span class="progress-tit"><c:if test="${t.info0Style eq 'new' }"><span class="new"><font color="white">N</font></span> </c:if><c:out value="${t.info1 }" /></span></a>
																			<span class="progress-label">
																				<span class="grey"><c:out value="${t.info2 }" /></span><span class="grey">/100</span>
																			</span>
																			<div style="height: 7px;"></div>
																		</p>
																	</div>
																</c:forEach>
															</div>
														</div>
													</div>
												</div>
											</c:if>
										</c:otherwise>
										
									</c:choose>
								</c:forEach>
								<c:if test="${empty item.contentList }">
									<div class="section_box">
										<div class="box confirm emergency"><!-- Emergency 프로젝트 현황 .emergency -->
											<div class="title">
												<p class="h1"><i class="mdi mdi-car-brake-alert"></i>Emergerncy 프로젝트 현황</p>
												<p class="more"><a href="" title="더 보기"><i class="mdi mdi-dots-horizontal"></i></a></p>
											</div>
										</div>
									</div>
								</c:if>
							</div>
							<!-- // my_status -->
						</c:when>
						
						<c:otherwise>
							<div class="section_box board">
								<div class="box">
									<div class="title">
										<p class="h1"><i class="mdi mdi-party-popper"></i>열린세상</p>
										<p class="more"><a href="<c:out value="${item.linkUrl }"/>" title="더 보기"><i class="mdi mdi-dots-horizontal"></i></a></p>	
									</div>
									<div class="status_ui">
										<ul>
											<c:forEach var="t" items="${item.contentList }">
												<li><a href="<c:out value="${t.linkUrl }"/>"><c:if test="${t.info0Style eq 'new' }"><span class="new"><font color="white">N</font></span> </c:if><c:out value="${t.info0 }" /></a><span><c:out value="${t.info2}" /></span></li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</div>
						</c:otherwise>
								
					</c:choose>
				</c:forEach>
			<!-- section_R -->
			</div>
		</div>
		
		<%-- <% if((!session.getAttribute("jobClass").equals("N")) && (!session.getAttribute("jobClass").equals("C")) && (!session.getAttribute("jobClass").equals("R"))){ %>
		<div class="section_box quick_menu">
			<!-- 바로가기 링크 영역 .quick_menu  -->
			<div class="menu_link" style="background-color:#FFB64B;">
				<ul>
					<li><a
						href="javascript:goOnlineBudget('<authz:authentication operation="username" />');"><i
							class="mdi mdi-shape-plus"></i><span>예산관리</span></a></li>
					<li><a href="javascript:goBizplay_popup();"><i
							class="mdi mdi-credit-card-outline"></i><span>법인카드</span></a></li>
					<li><a href="javascript:goKDB_popup();"><i
							class="mdi mdi-server-network"></i><span>KDB</span></a></li>
					<li><a
						href="javascript:goTMC_popup('<%=session.getAttribute("userId")%>');"><i
							class="mdi mdi-monitor-edit"></i><span>TMC BOX</span></a></li>
				</ul>
			</div>
			<div class="board_link" style="background-color:#FFB64B;">
				<ul>
					<li><a href="javascript:goBlind_popup();">블라인드 소통채널</a></li>
					<%if(session.getAttribute("jobClass").equals("A")) {%>
						<li><a href="https://intranet.kmac.co.kr/kmac/comlist/suggest_filelist.asp">제안서 검색</a></li>
					<%} %>
					<li><a href="javascript:goEducationManagement_popup();">교육 관리</a></li>
					<li><a href="/action/BoardAction.do?mode=selectList&bbsId=newbasisbbs">기준 지침</a></li>
					<%if(session.getAttribute("userId").equals("yskim") || session.getAttribute("userId").equals("harry2080")){ %>
						<li><a href="/action/AuthorityAction.do?mode=userChkPage">인사평가</a></li>
						<li><a href="/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_performance_admin">인사평가(관)</a></li>
					<%} %>
				</ul>
			</div>
		</div>
		<%} %> --%>
	</div>
</div>
</body>
</html>
