
<script type="text/javascript">

</script>
<script src="/m/js/m.script.common.js"></script>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=440, user-scalable=yes">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@page import="kr.co.kmac.pms.common.util.SessionUtils"%>
<%@page import="kr.co.kmac.pms.expertpool.data.ExpertPool"%>
<%@page import="kr.co.kmac.pms.expertpool.manager.ExpertPoolManager"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	ExpertPoolManager expertPoolManager = null;
	if (wac != null) {
		expertPoolManager = (ExpertPoolManager) wac.getBean("expertPoolManager");
	}
	ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));
		
	String userId = expertPool.getUserId();
%>
        <!-- Header -->
        <header id="header">
            <div class="gnb">
                <div class="navbar">
                    <div class="overlay"></div>
                    <div class="conts_box">
                        <div class="in_box">
                            <section>
                                <div class="scroll_box sc" >
                                    <div class="navbar_menu">
                                        <p class="home"><a href="/forwardM.jsp" title="HOME"><i class="mdi mdi-home"></i>HOME</a></p>										
                                        <ul>
                                            <li>
                                                <a href="javascript:;"><i class="mdi mdi-bulletin-board"></i>게시판</a>	
                                                <ul class="noDepth">														
                                                    <li>
                                                        <p>
                                                            <a href="/action/MobileBoardAction.do?mode=selectList&bbsId=publicNotice">공지사항</a>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <a href="/action/MobileBoardAction.do?mode=selectList&bbsId=sharebbs">열린세상</a>
                                                        </p>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li>
                                                <a href="javascript:;"><i class="mdi mdi-account-group-outline"></i>고객정보</a>	
                                                <ul class="noDepth">														
                                                    <li>
                                                        <p>
                                                            <a href="/_m/customer/customer_list.asp?_id=<%=expertPool.getUserId() %>">고객정보등록</a>
                                                        </p>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li>
                                                <a href="javascript:;"><i class="mdi mdi-calendar-month"></i>스케줄관리</a>	
                                                <ul class="noDepth">													
                                                    <li>
                                                        <p>	
                                                            <a href="/_new/schedule/m/personal/list">개인일정</a>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <a href="/_new/schedule/m/summary/list">일정관리</a>
                                                        </p>
                                                    </li>
                                                </ul>
                                            </li>
                                            <%if(!session.getAttribute("jobClass").equals("J")){ %>
                                            <li>
                                                <a href="javascript:;"><i class="mdi mdi-stamper"></i>전자결재</a>	
                                                <ul class="noDepth">															
                                                    <li>
                                                        <p>
                                                            <a href="/action/WorkCabinetAction.do?mode=getMyWorkListForMobile">내 결재함</a>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>	
                                                        	<a href="/action/GeneralSanctionAction.do?mode=loadFormGeneralSanctionForMobile">기안하기</a>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <a href="/action/SanctiontPresentStateAction.do?mode=getAllSanctiontPresentStateForMobile">결재현황보기</a>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <a href="/action/WorkCabinetAction.do?mode=getMyRefWorkListForMobile">내 참조함</a>
                                                        </p>
                                                    </li>
                                                    <%if (session.getAttribute("companyPosition").equals("06CB") || session.getAttribute("companyPosition").equals("08CF") 
                                                    		|| session.getAttribute("companyPosition").equals("09CJ") || session.getAttribute("companyPosition").equals("10TM")){%>
                                                    <li>
                                                        <p>	
                                                            <a href="/action/ProjectARExpenseSanctionAction.do?mode=loadARExpenseSanctionFormForMobile&approvalType=expenseB;">성과급 품의하기</a>
                                                        </p>
                                                    </li>
                                                    <%} %>
                                                </ul>
                                            </li>
                                            <%} %>
                                            <li>
                                                <a href="javascript:;"><i class="mdi mdi-projector-screen-outline"></i>업무지원</a>
                                                <ul>
                                                    <li>
                                                        <p>
                                                        	<a href="javascript:;">예약하기</a>
                                                        </p>
                                                        <ul>
                                                            <li>
                                                                <p>															
                                                                    <a href="/action/MobileCertificateAction.do?mode=list">증명서 신청</a>
                                                                </p>
                                                            </li>
                                                             <li>
                                                                <p>															
                                                                    <a href="/_m/expense/expense_reg.asp?_id=<%=expertPool.getUserId() %>">출장비 신청</a>
                                                                </p>
                                                            </li>
                                                             <li>
                                                                <p>															
                                                                    <a href="/_m/room_rsrvt/index.asp?_id=<%=expertPool.getUserId() %>">회의실 예약</a>
                                                                </p>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <a href="javascript:;">내역보기</a>
                                                        </p>
                                                        <ul>
                                                            <li>
                                                                <p>															
                                                                   <a href="/action/MobileTaxWorkAction.do?mode=selectIncentiveList">입금내역</a>
                                                                </p>
                                                            </li>
                                                            <%if(session.getAttribute("jobClass").equals("J")){ %>
	                                                            <li>
	                                                                <p>															
	                                                                    <a href="/action/MobileTaxWorkAction.do?mode=selectBonusPreList">성과급예상내역</a>
	                                                                </p>
	                                                            </li>
	                                                            <li>
	                                                                <p>															
	                                                                    <a href="/action/MobileTaxWorkAction.do?mode=selectBonusList">성과급내역</a>
	                                                                </p>
	                                                            </li>
                                                            <%}else{ %>
	                                                            <li>
	                                                                <p>															
	                                                                    <a href="/action/MobileTaxWorkAction.do?mode=selectSalaryList">급여내역</a>
	                                                                </p>
	                                                            </li>
                                                            <%} %>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li>
                                                <a href="javascript:;"><i class="mdi mdi-bulletin-board"></i>인력관리</a>	
                                                <ul class="noDepth">														
                                                    <li>
                                                        <p>															
                                                           <a href="/action/MobileExpertPoolManagerAction.do?mode=getExpertPoolExtListForMobile">인력POOL</a>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>															
                                                           <a href="/m/web/expertpool/chargerInfo.jsp">담당자 안내</a>
                                                        </p>
                                                    </li>
                                                    <li>
                                                    	<p>
                                                    		<a href="/action/EmergencyConnectionAction.do?mode=retrieveListForMobile">비상연락망</a>
                                                    	</p>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li>
                                                <a href="#" onclick="goEmail2('<%=expertPool.getEmail() %>','<%=expertPool.getSsn() %>')"><i class="mdi mdi-email"></i>메일</a>	
                                            </li>														
                                        </ul>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>
                    <a class="btn_close" href="javascript:;"><span class="blind"></span></a>
                </div>						
                <!-- // navbar -->         
            </div>
            <%-- <% if(request.getServletPath().equals("/m/index.jsp")) { %>	  
            <%}else{ %><div class="logo" onclick="location.href='/m/'"  style="cursor: pointer;"></div><%} %> --%>
            <div class="util">
                <ul>
                	<li><button type="button" title="home" onclick="location.href='/forwardM.jsp'"><i class="mdi mdi-home-outline"></i></button></li>
                 	<li><button type="button" title="PC버전" onclick="location.href='/action/AuthorityAction.do?mode=mainPage'"><i class="mdi mdi-monitor"></i></button></li> 
                    <!-- <li><button type="button" title="새로고침" onclick="window.location.reload()"><i class="mdi mdi-refresh"></i></button></li> -->
                    <li><button type="button" title="로그아웃" onclick="location.href='/j_acegi_mobile_logout'"><i class="mdi mdi-logout"></i></button></li>
                </ul>            
            </div>
        </header>
        <!-- // Header -->
		 
	