<%@page import="kr.co.kmac.pms.common.util.SessionUtils"%>
<%@page import="kr.co.kmac.pms.expertpool.data.ExpertPool"%>
<%@page import="kr.co.kmac.pms.expertpool.manager.ExpertPoolManager"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="kr.co.kmac.pms.schedule.data.DailyScheduleInfo"%>
<%@page import="kr.co.kmac.pms.companySchedule.data.CompanyScheduleInfoData"%>
<%@page import="kr.co.kmac.pms.schedule.data.DailyProjectInfo"%>
<%@ taglib prefix="c"			uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="authz"		uri="http://acegisecurity.org/authz" %>
<%@ taglib prefix="code"		uri="/WEB-INF/commonCode.tld" %>
<%@page import="java.util.List"%>
<%
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	ExpertPoolManager expertPoolManager = null;
	if (wac != null) {
		expertPoolManager = (ExpertPoolManager) wac.getBean("expertPoolManager");
	}
	ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));
		
	String userId = expertPool.getUserId();
%>
<script type="text/javascript" src="<c:url value="/js/AjaxRequest.js"/>"></script>
<script type="text/javascript">

function goEmail3(){
	var email = "<authz:authentication operation="email" />";
	var status = AjaxRequest.post (
			{	'url': "/action/ExpertPoolCheckAction.do",
				'parameters' : { "mode": "getEmailPassword", "ssn": "<authz:authentication operation="username" />"},
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					webmailForm.user_account.value = email;
                   	webmailForm.pass.value = res.password;
                   	webmailForm.cmd.value = "mail_link_2";
					webmailForm.action="https://webmail.kmac.co.kr/a_biz/m_login.nvd";
					webmailForm.target = "webmail";
					var win = window.open('', 'webmail', 'toolbar=yes,location=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes,left=0,top=0,width=1024,height=768');
					if(win == null || typeof(win) == "undefined" || (win == null && win.outerWidth == 0) || (win != null && win.outerHeight == 0) || win.test == "undefined"){
						alert("브라우저의 팝업 차단을 허용해주시기 바랍니다.");
					}else{
						
					}
					//popup.focus();
                   	webmailForm.submit();
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	);
} 
</script>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>KMAC 통합 인트라넷</title>

    <script src="/m/resources/js/jquery-3.4.1.min.js"></script>
    <script src="/m/resources/js/jquery-ui-1.12.1.min.js"></script>
    <script src="/m/resources/js/common.js"></script>

    <link href="/m/resources/css/jquery-ui.css" rel="stylesheet" />
    <link href="/m/resources/css/kmac.css" rel="stylesheet" />
    

</head>
<body>

	<input type="hidden" value="<authz:authentication operation="email" />" id="useremail">
	<input type="hidden" value="<authz:authentication operation="username" />" id="username"> 
	<form name="webmailForm" method="post">
		<input name="user_account" value="" type="hidden" />
		<input name="pass" value="" type="hidden" />
		<input name="cmd" value="" type="hidden" />
	</form>
	
    <div id="wrap" class="main"><!-- 메인 패이지에서만 .main-->

        <!-- header -->
		<jsp:include page="/m/web/common/header.jsp" >
			<jsp:param value="fixed" name="data_position" />
		</jsp:include>
		<!-- // header -->

        <!-- Container -->
        <div id="container">

            <div class="logo" onclick="location.href='/m/'" style="cursor: pointer;"></div>

            <div class="profile">
         			<%if(expertPool.getPhoto() != null) {%>
						<div class="img"><img id="MyPic" style="cursor:hand;" src="/servlet/PhotoDownLoadServlet?fileId=<%=expertPool.getPhoto() %>" ></div>		
					<%}else{ %>
						<div class="img"><i class="mdi mdi-account"></i></div>	
					<%} %>
                <p><%=expertPool.getName() %> <%=expertPool.getCompanyPositionName() %><span><%=expertPool.getDeptName() %></span></p>
            </div>
            <!-- <p class="subtit">주요 메뉴</p> -->

            <div class="link_area">
                <ul>
                    <li>
                        <button type="button" onclick="goEmail3()"><i class="mdi mdi-email"></i><span id="mailCount"></span></button>
                        <p>메일</p>
                    </li>
                    <li>
                        <button type="button" onclick="location.href='/action/WorkCabinetAction.do?mode=getMyWorkListForMobile'"><i class="mdi mdi-playlist-edit"><span id="workCount"></span></i></button>
                        <p>업무</p>
                    </li>
                    <li>
                        <button type="button" onclick="location.href='/_new/schedule/m/personal/list'"><i class="mdi mdi-calendar-month"><span id="scheduleCount"></span></i></button>
                        <p>일정</p>
                    </li>
                    <li>
                        <button type="button" onclick="location.href='/m/web/board/bbsidList.jsp'"><i class="mdi mdi-bulletin-board copy"></i></button>
                        <p>게시판</p>
                    </li>
                </ul>
                <ul>
                	<%if(session.getAttribute("jobClass").equals("A")|| session.getAttribute("jobClass").equals("B") || session.getAttribute("jobClass").equals("N")){ %>
	                    <li>
	                        <button type="button" onclick="location.href='/action/SanctiontPresentStateAction.do?mode=getAllSanctiontPresentStateForMobile'"><i class="mdi mdi-draw-pen"></i></button>
	                        <p>결재현황</p>
	                    </li>
                    <%} else {%>
	                    <li>
	                        <button type="button" onclick="location.href='/m/web/support/supportList.jsp'"><i class="mdi mdi-file-document-multiple-outline"></i></button>
	                        <p>내역보기</p>
	                    </li>
                    <%} %>
                    <li>
                        <button type="button" onclick="location.href='/_m/customer/customer_list.asp?_id=<%=expertPool.getUserId() %>'"><i class="mdi mdi-account-details"></i></button>
                        <p>고객정보</p>
                    </li>
                    <li>
                        <button type="button" onclick="location.href='/_m/room_rsrvt/index.asp?_id=<%=expertPool.getUserId() %>'"><i class="mdi mdi-google-classroom"></i></button>
                        <p>회의실 예약</p>
                    </li>
                    <li>
                        <button type="button" onclick="location.href='/action/EmergencyConnectionAction.do?mode=retrieveListForMobile'"><i class="mdi mdi-format-list-numbered"></i></button>
                        <p>비상연락망</p>
                    </li>
                </ul>
            </div>
            
            <%
				List todayScheduleList = (List)request.getAttribute("todayScheduleList");
				List tomorrowScheduleList = (List)request.getAttribute("tomorrowScheduleList");
				Object todayScheduleListCount = request.getAttribute("todayScheduleListSize");
				Object tomorrowScheduleListCount = request.getAttribute("tomorrowScheduleListSize");
			%>
            
            <!-- index schedule -->
             <div class="schedule_area">
                <ul class="dropdown">
                    <li>
                        <p class="drop_title"><i class="mdi mdi-format-list-checks"></i><b>오늘의 일정<span>(<%=todayScheduleListCount %>)</span></b></p>
                        <div class="drop_data sc">
                            <ul>
                            	<%
									if(todayScheduleList.size() > 0) {
										for(int i =0; todayScheduleList.size() > i; i++){
											if(todayScheduleList.get(i) instanceof DailyProjectInfo){
												DailyProjectInfo d = (DailyProjectInfo)todayScheduleList.get(i);
								%>
                                <li>
                                    <a href="javascript:;">
                                        <p class="time">프로젝트 일정</p>
										<p><%=d.getProjectName() %></p>
                                    </a>                                    
                                </li>
                                <%
									}else if(todayScheduleList.get(i) instanceof DailyScheduleInfo){
												DailyScheduleInfo d = (DailyScheduleInfo)todayScheduleList.get(i);
								%>
                                <li>
                                    <a href="/_new/schedule/m/personal/list">
                                        <p class="time"><%=d.getStartHour() %>:<%=d.getStartMin() %><%-- ~<%=d.getEndHour() %>:<%=d.getEndMin() %> --%></p>
										<p><%=d.getContent() %></p>
                                    </a>                                    
                                </li>
                               	<% 		
	                               	}
									}
								} else {
								%>
                                <li>
                                   <p>일정이 없습니다.</p>                              
                                </li>
                                <%
									}
                                %>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <p class="drop_title"><i class="mdi mdi-format-list-checks"></i><b>내일의 일정<span>(<%=tomorrowScheduleListCount %>)</span></b></p>
                        <div class="drop_data sc">
                            <ul>
                                <%
                                if(tomorrowScheduleList.size() > 0) {
                    				for(int i =0; tomorrowScheduleList.size() > i; i++){
                    					if(tomorrowScheduleList.get(i) instanceof DailyProjectInfo){
                    						DailyProjectInfo d = (DailyProjectInfo)tomorrowScheduleList.get(i);
								%>
                                <li>
                                    <a href="/_new/schedule/m/personal/list">
                                        <p class="time">프로젝트 일정</p>
										<p><%=d.getProjectName() %></p>
                                    </a>                                    
                                </li>
                                <%
                    					}else if(tomorrowScheduleList.get(i) instanceof DailyScheduleInfo){
                    						DailyScheduleInfo d = (DailyScheduleInfo)tomorrowScheduleList.get(i);
								%>
                                <li>
                                    <a href="/_new/schedule/m/personal/list">
                                       <p class="time"><%=d.getStartHour() %>:<%=d.getStartMin() %><%-- ~<%=d.getEndHour() %>:<%=d.getEndMin() %> --%></p>
									   <p><%=d.getContent() %></p>
                                    </a>                                    
                                </li>
                               	<% 		
	                               	}
									}
								} else {
								%>
                                <li>
                                   <p>일정이 없습니다.</p>                              
                                </li>
                                <%
									}
                                %>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>
  			<!-- // index schedule -->
  
            <c:forEach var="item" items="${myNormalGadgetList }" varStatus="status">
	            <ul class="dropdown">
            		<%-- <c:if test="${status.index == '0' }"> --%>
	                <li class="on">
	                    <p class="drop_title"><c:out value="${item.gadgetName }" /></p>
	                    <div class="drop_data sc">
	                        <ul>
	                        <c:forEach var="t" items="${item.contentList }" varStatus="counts" >
								<li <c:if test="${t.info0Style eq 'new' }">class="new"</c:if>>
	                                <p><a href="<c:out value="${t.linkUrl }"/>">
	                                	<span><c:out value="${t.info0 }" /></span>
	                                	<span><c:out value="${t.info2 }" /></span>
	                                </a></p>
	                            </li>
							</c:forEach>
	                        </ul>
	                    </div>
	                </li>
	                <%-- </c:if> --%>
	            </ul>
            </c:forEach>

        </div>
        <!-- // Container -->

        <!-- Footer -->
        <jsp:include page="/m/web/common/footer.jsp" >
			<jsp:param value="fixed" name="data_position" />
		</jsp:include>
        <!-- // Footer -->
    </div>
   
</body>
</html>
</body>
</html>