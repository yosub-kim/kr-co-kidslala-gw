<%@page import="kr.co.kmac.pms.common.menuMy.data.MenuMy"%>
<%@page import="kr.co.kmac.pms.system.authority.data.NodeData"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html; charset=utf-8"%>
<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.expertpool.manager.ExpertPoolManager"%>
<%@page import="kr.co.kmac.pms.expertpool.data.ExpertPool"%>
<%@page import="kr.co.kmac.pms.system.popup.manager.PopUpConfigManager" %>
<%@page import="kr.co.kmac.pms.system.popup.data.PopUpConfig" %>
<%@page import="kr.co.kmac.pms.common.util.SessionUtils"%>
<%@page import="kr.co.kmac.pms.schedule.data.DailyScheduleInfo"%>
<%@page import="kr.co.kmac.pms.companySchedule.data.CompanyScheduleInfoData"%>
<%@page import="kr.co.kmac.pms.schedule.data.DailyProjectInfo"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>

<%@ include file="/common/include/taglib.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<!-- 메일, 햄버거, 고객만족도 자바스크립트 -->
<%
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	ExpertPoolManager expertPoolManager = null;
	if (wac != null) {
		expertPoolManager = (ExpertPoolManager) wac.getBean("expertPoolManager");
	}
	ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));
	
	PopUpConfigManager popUpConfigManager = null;
	if (wac != null) {
		popUpConfigManager = (PopUpConfigManager) wac.getBean("popUpConfigManager");
	}
	PopUpConfig popUpConfig = popUpConfigManager.getPopUpConfigDAO().getPopUp();
	
	String userId = expertPool.getUserId();
%>

<html lang="ko">
<head>
<title>키즈라라 인트라넷</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Generator" content="EditPlus" charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" /> 
<meta name="Author" content="">
<meta name="Keywords" content=""> 
<meta name="Description" content="">
<meta name="viewport" content="width=1600">
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/main.css" type="text/css" />
<!-- <link rel="stylesheet" href="/resources/css/board.css" type="text/css" /> --><!-- 서브페이지에서만 사용 -->	
<!--[if lt IE 9]>
      <script type="text/javascript" src="/resources/js/html5shiv.js"></script>
      <![endif]-->
      
<script type="text/javascript">
//if(isMobile){
//	top.location.href="/m";
//}

var isPopUpEnable = "<%=popUpConfig.getIsEnable()%>";
var wnWidth		= "<%=popUpConfig.getWidth()%>";
var wnHeight	= "<%=popUpConfig.getHeight()%>";
var workDayCnt  = "<%=request.getAttribute("workDayCnt")%>";

function getCookie( name ){
	var nameOfCookie = name + "=";
	var x = 0;
	while ( x <= document.cookie.length ){
		var y = (x+nameOfCookie.length);
		if ( document.cookie.substring( x, y ) == nameOfCookie ) {
			if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
				endOfCookie = document.cookie.length;
			return unescape( document.cookie.substring( y, endOfCookie ) );
		}
		x = document.cookie.indexOf( " ", x ) + 1;
		if ( x == 0 )
			break;
	}
	return "";
}

if((isPopUpEnable == "Y")&&(getCookie("noPopUp") != "Y")) {
	var screenHeight = screen.height;
	var screenWidth = screen.width;
	var leftpos = screenWidth / 2 - wnWidth / 2;
	var toppos = screenHeight / 2 - wnHeight / 2;
	var popupWin = window.open("/PopUp.jsp","popupWin","toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=yes,width="+wnWidth+",height="+wnHeight+",left="+leftpos+",top="+toppos+"");
	popupWin.focus();
}

//재택근무자 안내 팝업 (2022/04/15 적용)
if(workDayCnt == '7' && (getCookie("noHomePop") != "Y")) {
	var popupWin = window.open("/main_home_popup.jsp","homePopup","toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=0,top=50,left=50,width=620,height=600");
	popupWin.focus();
}

function setCookie1( name, value, expiredays ){
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + '=' + escape( value ) + '; path=/; expires=' + todayDate.toGMTString() + ';'
}

setCookie1("pms_User_Id",'<%=userId%>',3);
function goEmail(){
	var email = "<authz:authentication operation="email" />";
	if(email == ""){
		alert("등록된 메일 주소가 없습니다. 관리자에게 문의하세요.");
	}else{
		var status = AjaxRequest.post (
				{	'url': "/action/ExpertPoolCheckAction.do",
					'parameters' : { "mode": "getEmailPassword", "ssn": "<authz:authentication operation="username" />"},
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						webmailForm.user_account.value = email;
                    	webmailForm.pass.value = res.password;
                    	webmailForm.cmd.value = "mail_link_2";
						webmailForm.action="https://webmail.kmac.co.kr/a_biz/login.nvd";
						webmailForm.target = "webmail";
						popup = window.open('', 'webmail', 'toolbar=yes,location=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes,left=0,top=0,width=1024,height=768');
						popup.focus();
                    	webmailForm.submit();
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		);
	}
}

function admin_menu(){
	changeContentFrame('0308', '/action/OrgMngAction.do?mode=orgMngPage');
}

function openSiteMap(){
	var openSiteWin;
	openSiteWin = window.open("/system/config/Sitemap.jsp","openSiteWin", "top=10,left=10,width=820,height=610,scrollbars=yes");
	openSiteWin.focus();
}

function openConfigWin() {
	/* var configWin;
	configWin = window.open("/system/config/MyConfig.jsp","configWin", "top=100,left=100,width=570,height=635,scrollbars=no");
	configWin.focus(); */
	changeContentFrame('__1', '/system/config/MyConfig.jsp');
}

function logoff() {
	top.location.href = "/j_acegi_logout";
}

function goOnlineBudget(username) {
 	document.getElementById("onlineBudgetForm").action="https://newbudget.kmac.co.kr:8080/com/login_chk.jsp";
	document.getElementById("onlineBudgetForm").empno.value=username;
	document.getElementById("onlineBudgetForm").target="new";
	document.getElementById("onlineBudgetForm").submit();
}

function goEducationManagement() {
	window.open("https://kmacadmin.kmac.co.kr");
}

function goEhrdManagement() {
	window.open("http://newehrd.kmac.co.kr/System_Manage/check_login.sol?user_id=<%=session.getAttribute("userId")%>","","toolbar=no,status=no,width=1024,height=768,directories=no,scrollbars=0,location=no,resizable=no,menubar=no,screenX=0,left=0,screenY=0,top=0,right=0");
}

function goKDB_popup() {
	userwidth = (screen.width - 1); 
	userheight = (screen.height - 1);
	
	window.open('https://intranet.kmac.co.kr/kmac/ks/hub.asp','new_kdb_pop','scrollbars=yes,toolbar=yes,location=yes,status=yes,menubar=yes,resizable=yes,width='+userwidth+',height='+userheight+',left=0,top=0');
}

function Blind_popup(){
	window.open("https://intranet.kmac.co.kr/kmac/task/blind_board/board_list.asp","","toolbar=no,status=no,width=795,height=600,directories=no,scrollbars=1,location=no,resizable=no,menubar=no,screenX=0,left=0,screenY=0,top=0,right=0");
}

function goBizplay_popup() {
	userwidth = (screen.width - 1); 
	userheight = (screen.height - 1);
	
	window.open('https://www.bizplay.co.kr','bizplay','scrollbars=yes,toolbar=yes,location=yes,status=yes,menubar=yes,resizable=yes,width='+userwidth+',height='+userheight+',left=0,top=0');
}
	
function changeContentFrame(id, url){
	
	//프레임 변경 시 인증 쿠키 값 확인 로직 추가 
	
	var status1 = AjaxRequest.post (
		{	'url': "/action/ExpertPoolCheckAction.do?mode=isPasswordValid",
			'onSuccess':function(obj){
				if(obj.responseText.indexOf("<html>")>0){
					top.document.location.href = "/<c:url value='j_acegi_homepage_logout'/>";
				}
				var res = eval('(' + obj.responseText + ')');
				if(res.needToPasswordUpdate == "true"){
					alert("통합 인트라넷 사용자의 정보보호 및 시스템 보안 향상을 위해 비밀번호를 변경하세요 !");
					top.document.location.href = "/pwalert.jsp"
				} else {					
					j$(".leftMenu").css("display", "none");
					j$("#"+id).show();
					document.getElementById("_contentFrame").src = url;
				}
			}, 
			'onLoading':function(obj){},
			'onError':function(obj){
				alert("session 정보를 읽어올 수 없습니다.");
				top.location.href = "/j_acegi_logout";
			}
		}
	);
}

function getQuerystring(paramName){

	var _tempUrl = contentFrame.location.search.substring(1); //url에서 처음부터 '?'까지 삭제
	var _tempArray = _tempUrl.split('&'); // '&'을 기준으로 분리하기
	
	for(var i = 0; _tempArray.length; i++) {
		var _keyValuePair = _tempArray[i].split('='); // '=' 을 기준으로 분리하기
		
		if(_keyValuePair[0] == paramName){ // _keyValuePair[0] : 파라미터 명
			// _keyValuePair[1] : 파라미터 값
			return _keyValuePair[1];
		}
	}
}

function uuidResult(){
	 var result =  'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		    var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
		    return v.toString(16);
		  });
	 
	 document.getElementById("uuidResult").value = result;
}

/*url check*/
var result = "";

function iframeAutoResize(i, j){
	/*url check*/
	result = j;
	
	if(j != "/action/GadgetAction.do?mode=mainPage"){
		document.getElementById("leftMenu").style.display="";
		/* document.getElementById("sub_location").style.display=""; */
	}
	{
		try{
			var iframeHeight=
		        (i).contentWindow.document.body.scrollHeight;
/* 		        (i).height=iframeHeight+100;	 */	
		}catch(e){}
		j$(this).scrollTop(0);
	    countMyPanel();
	}
	
	/* work day hide */
	var workDayChk = 0;

	j$(document).ready(function () {
		j$.ajax({
			url: "/action/AuthorityAction.do?mode=workDayCnt",
	        type: "POST",
	        success: function (count) {
	        	workDayChk = count;
	        	if(workDayChk > 0){
	        		document.getElementById("workDayMenu").style.display = "";
	        	}else{
	        		document.getElementById("workDayMenu").style.display = "none";
	        	}
	        },
		})
	});
	
}
	
function workOn(){
	var sFrm = document.frm;
	
	if(sFrm.workOnCnt.value > 0){
		alert("이미 출근을 완료하였습니다.");
		return false;
	}
	
	if(confirm("출근 하시겠습니까?"))
	{
		var ActionURL = "/action/AuthorityAction.do";	
		ActionURL += "?mode=workOn";
		var status = AjaxRequest.submit(
				sFrm
				,{ 'url':ActionURL
					,'onSuccess':function(obj){
						alert("출근 완료하였습니다.");
						top.document.location.href = "/action/AuthorityAction.do?mode=mainPage"
					}
					,'onLoading':function(obj){}
					,'onError':function(obj){
						alert("[오류] 경영기획3센터 김요섭(644) 문의");
					}
				}
		); status = null;
	}else{
		return;
	}
		
}

function workOff(){
	var sFrm = document.frm;
	
	if(sFrm.workOffCnt.value > 0){
		alert("이미 퇴근을 완료하였습니다.");
		return false;
	}
	
	if(confirm("퇴근 하시겠습니까?"))
	{
	var ActionURL = "/action/AuthorityAction.do";	
	ActionURL += "?mode=workOff";
	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					alert("퇴근 완료하였습니다.");
					top.document.location.href = "/action/AuthorityAction.do?mode=mainPage"
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("[오류] 경영기획3센터 김요섭(644) 문의");
				}
			}
	); status = null;
	}else{
		return;	
	}
}

function toggleMyMenu(menuId, isMenuMy){
	if(isMenuMy) {
		AjaxRequest.post ({
			'url': "/action/MenuMyAction.do?mode=deleteMenuMy",
			'parameters' : { "menuNum": menuId},
			'onSuccess':function(obj){
				//즐겨찾기 리프레시 필요
			},
			'onLoading':function(obj){},
			'onError':function(obj){
				alert("즐겨찾기 오류가 발생하였습니다.1");
			}
		});
	} else {
		var status = AjaxRequest.get (
				{	'url': "/action/MenuMyAction.do?mode=getMenuMyList",
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						if(res.returnValue.length >=5){
							alert("즐겨찾기 등록은 최대 5개 입니다. ");
						} else {
							AjaxRequest.post ({
								'url': "/action/MenuMyAction.do?mode=insertMenuMy",
								'parameters' : { "menuNum": menuId},
								'onSuccess':function(obj){
									//즐겨찾기 리프레시 필요
								},
								'onLoading':function(obj){},
								'onError':function(obj){
									alert("즐겨찾기 오류가 발생하였습니다.2");
								}
							});							
						}
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("즐겨찾기 오류가 발생하였습니다.3");
					}
				}
		);		
	}


	
}

function home_schedule(ssn){
	var url = "/action/BoardAction.do?mode=selectList_home&bbsId=home&writerId="+ssn;
	var sFeatures = "top=50,left=50,width=800,height=800,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_schedule", sFeatures);
	detailWin.focus();
}
 
function openEp(){
	/* document.getElementById('_contentFrame').height=4000; */
	changeContentFrame('__1', '/emergencyConnection/emergencyTab.jsp');
}

function openSchedule(){
	/* document.getElementById('_contentFrame').height=4000; */
	changeContentFrame('__1', '/_new/schedule/personal/list');
}

function goOnlineBudget(username) {
	document.getElementById("onlineBudgetForm").action = "https://newbudget.kmac.co.kr:8080/com/login_chk.jsp";
	document.getElementById("onlineBudgetForm").empno.value = username;
	document.getElementById("onlineBudgetForm").target = "new";
	document.getElementById("onlineBudgetForm").submit();
}

function openCredit(){
	window.open(
			'https://www.bizplay.co.kr',
			'bizplay',
			'scrollbars=yes,toolbar=yes,location=yes,status=yes,menubar=yes,resizable=yes,left=0,top=0');
}

function openProjectPro(){
	/* document.getElementById('_contentFrame').height=4000; */
	changeContentFrame('___1', 'https://intranet.kmac.co.kr/kmac/comlist/suggest_filelist.asp');
}

function openTMCBox(userId){
	userwidth = (screen.width - 1); 
	userheight = (screen.height - 1);
	
	window.open('https://intranet.kmac.co.kr/tmc_box/index.asp?user_id='+userId,'new_kdb_pop','scrollbars=yes,toolbar=yes,location=yes,status=yes,menubar=yes,resizable=yes,width='+userwidth+',height='+userheight+',left=0,top=0');
}

function countMyPanel(){
	
	/* left menu hide */
	if(result == "https://newpms.kmac.co.kr/action/GadgetAction.do?mode=mainPage"){
		$('leftMenuInfo').hide();
	}else{
		$('leftMenuInfo').show();
	}
	
	/*var status1 = AjaxRequest.post (
			{	'url': "/action/ProjectReportCabinetAction.do?mode=countMyProjectReport",
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					document.getElementById('preportCount').innerHTML = (res.preportCount == "" ? "-" : res.preportCount);
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("지도일지 정보를 읽어올 수 없습니다.");
				}
			}
	);*/
	var status2 = AjaxRequest.post (
			{	'url': "/action/WorkCabinetAction.do?mode=countMyWorkList",
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					document.getElementById('workCount').innerHTML = (res.workCount == "" ? "-" : res.workCount);
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("업무함 정보를 읽어올 수 없습니다.");
				}
			}
	);
	var status3 = AjaxRequest.post (
			{	'url': "/action/MailCommonAction.do?mode=countMyEmail",
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					document.getElementById('mailCount').innerHTML = (res.mailCount == "" ? "-" : res.mailCount);
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("이메일 정보를 읽어올 수 없습니다.");
				}
			}
	)
	/* var status4 = AjaxRequest.post (
			{	'url': "/action/WeeklyReportCabinetAction.do?mode=countMyWeeklyReport",
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					document.getElementById('wreportCount').innerHTML = (res.wreportCount == "" ? "-" : res.wreportCount);
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("주간보고 정보를 읽어올 수 없습니다.");
				}
			}
	); 	
	var status5 = AjaxRequest.post (
			{	'url': "/action/MonthlyReportCabinetAction.do?mode=countMyMonthlyReport",
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					document.getElementById('mreportCount').innerHTML = (res.mreportCount == "" ? "-" : res.mreportCount);
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("새 레포트  정보를 읽어올 수 없습니다.");
				}
			}
	);	 */
}

j$(document).ready(function() {
	AjaxRequest.post (
		{	'url': "/action/ExpertPoolCheckAction.do?mode=isPasswordValid",
			'onSuccess':function(obj){
				if(obj.responseText.indexOf("<html>")>0){
					top.document.location.href = "/<c:url value='j_acegi_homepage_logout'/>";
				}
				var res = eval('(' + obj.responseText + ')');
				if(res.needToPasswordUpdate == "true"){
					alert("통합 인트라넷 사용자의 정보보호 및 시스템 보안 향상을 위해 비밀번호를 변경하세요 !!");
					top.document.location.href = "/pwalert.jsp"
				} else {
					countMyPanel();
				}	
			}, 
			'onLoading':function(obj){},
			'onError':function(obj){
				alert("session 정보를 읽어올 수 없습니다.");
				top.location.href = "/j_acegi_logout";
			}
		}
	);
	
//var screenHeight = screen.height-166;
//j$('#_contentFrame').prop('height', screenHeight);
});

</script>
</head>

<form id="sitemap" name="sitemap" style="display: none;">
	<input type="hidden" name="siteMapHtml" id="siteMapHtml">	
</form>
<form name="webmailForm" method="post" style="display: none;">
	<input name="user_account" value="" type="hidden" >
	<input name="pass" value="" type="hidden" >
	<input name="cmd" value="" type="hidden" >
</form>
<form name="onlineBudgetForm" method="post" id="onlineBudgetForm">
	<input name="empno" value="" type="hidden" >
</form>

<body onload="countMyPanel()">

<form id="frm" name="frm" method="post">
		<input type="hidden" id="workDayCnt" name="workDayCnt" value="<c:out value="${workDayCnt }" />" />
		<div class="wrap">
			<div class="container">
			
				<!-- link_area -->
				<div class="link_area">
					<div>
						<ul>
							<li><a href="/" title="HOME"><i class="mdi mdi-home"></i><p>HOME</p></a></li>
							<li><a href="javascript:openConfigWin();" title="마이페이지"><i class="mdi mdi-card-account-details"></i><p>마이페이지</p></a></li>
							<li><a href="javascript:logoff();" title="로그아웃"><i class="mdi mdi-lock-open"></i><p>로그아웃</p></a></li>
						</ul>
					</div>
					<div>
						<ul>
							<%
							    List<MenuMy> menuMyList = (List<MenuMy>)request.getAttribute("menuMyList");
							    int menuMyListSize = menuMyList.size();
							    for(int a=0; menuMyList.size() > a; a++){
							        MenuMy menuMy = menuMyList.get(a);
						    %>
						        <li>
						        	<a href="javascript:changeContentFrame('___1', '<%=menuMy.getMenuPath() %>');"><i class="mdi mdi-star"></i><a>
						        	<ul>
						        		<li><%=menuMy.getMenuName()%></li>
						        	</ul>
						        </li>	
						    <%
						    }
						    for(int b=menuMyList.size(); 1 > b; b++){
						    %>
						        <li>
						        	<a href="javascript:;" title="즐겨찾기"><i class="mdi mdi-star"></i></a>
							        <ul>
							        	<li>즐겨찾기를 추가하세요</li>
							        </ul>
							   </li>
						    <%
						    }
						    %>
						</ul>
					</div>
					<div>
						<ul>
							<%-- <% if(session.getAttribute("jobClass").equals("A") ||session.getAttribute("jobClass").equals("B")){ %>
							<li><a href="javascript:goOnlineBudget('<%=session.getAttribute("ssn") %>');" title="예산 관리"><i class="mdi mdi-shape-plus"></i><p>예산 관리</p></a></li>
							<li><a href="javascript:openCredit();" title="법인 카드"><i class="mdi mdi-credit-card-outline"></i><p>법인 카드</p></a></li>
							<%} %> --%>
							<li><a href="javascript:openSchedule();" title="개인 일정"><i class="mdi mdi-calendar-month"></i><p>개인 일정</p></a></li>
							<li><a href="javascript:openEp();" title="비상연락망"><i class="mdi mdi-contacts"></i><p>비상연락망</p></a></li>
						</ul>
					</div>
				</div>
				<!-- // link_area -->
				<div class="status_area">
					<div class="logo"><a href="/"><img style="width:180px; height:43px;" src="/resources/kidzlala/kidzlala_logo.jpg" alt="키즈라라" /></a></div>
					<div class="profile"><img src="/servlet/PhotoDownLoadServlet?fileId=<%=expertPool.getPhoto() %>" alt="" /></div>
						<p class="name"><%=expertPool.getName() %> <%=expertPool.getCompanyPositionName() %></p>
						<p class="position"><%=expertPool.getDeptName() %></p>
					<div class="today_date"><p><%=DateTime.getDateString(". ") %> (<%=DateTime.getDayofWeek() %>)</p></div>

					<!-- workDay -->
					<div id="workDayMenu" style="display: none;">
						<div class="commute">
							<input type="hidden" id="workOn" name="workOn" value="on" />
							<input type="hidden" id="workOff" name="workOff" value="off" />
							<input type="hidden" id="workOnCnt" name="workOnCnt" value='<c:out value="${workDayOnCnt }" />' />
							<input type="hidden" id="workOffCnt" name="workOffCnt" value='<c:out value="${workDayOffCnt }" />' />
							<ul>
								<li class="on">
									<a href="javascript:workOn();" id="workOn" name="workOn" value="on">
										<p>출근</p>
										<span><c:out value="${workDayOn}"/></span>
										<span><%-- <c:out value="${workDayOnChk }"/> --%></span>
									</a>
								</li>
								<li class="off">
									<a href="javascript:workOff();" id="workOff" name="workOff" value="off">
										<p>퇴근</p>
										<span><c:out value="${workDayOff}" /></span>
										<span><%-- <c:out value="${workDayOffChk }"/> --%></span>
									</a>
								</li>
							</ul>
						</div>
						<div class="data_box" style="background-color: #F4F6F9;">
							<div>
								<a href="javascript:changeContentFrame('___1', '/action/BoardAction.do?mode=selectList_home&bbsId=home&writerId=<%=session.getAttribute("ssn")%>');"><i class="mdi mdi-notebook-edit"></i><p>재택근무 업무보고서 등록</p></a>
							</div>
						</div>
					</div>
					<!-- // workDay -->
					<!-- <div class="data_box">
						<div>
							<a href="javascript:changeContentFrame('___1', 'https://intranet.kmac.co.kr/kmac/task/reform_board/board_list.asp');"><i class="mdi mdi-clipboard-check"></i><p>개선의견 등록 게시판</p></a>
						</div>
					</div> -->
				
				<!-- 메일/업무 -->
				<%
					Object mailCount = request.getAttribute("mailCount");
					Object workCount = request.getAttribute("workCount");
				%>		
				<div id="leftMenuInfo" style="display: none;">	
					<div id="leftMenu" style="display: none;">
						<div class="data_box_both">
							<div>
								<a href="javascript:goEmail();">							
								<p><i class="mdi mdi-email"></i>메일</p>
								<p class="total"><span id="mailCount"></span><span>건</span></p>
								</a>
							</div>
							<div>
								<a href="javascript:changeContentFrame('___1', '/action/WorkCabinetAction.do?mode=getMyWorkList');">							
								<p><i class="mdi mdi-playlist-edit"></i>업무</p>
								<p class="total"><span id="workCount"></span><span>건</span></p>
								</a>
							</div>
						</div>
	
						<%-- <% if (expertPool.getRole().equals("ROLE2006080116041070759") || expertPool.getRole().equals("ROLE2006080116061636366") || expertPool.getRole().equals("ROLE17404E902CA")
							 || expertPool.getRole().equals("ROLE125B1491C42") || expertPool.getRole().equals("ROLE2006080118352520784") || expertPool.getRole().equals("ROLE2006050120451853989")
							 || expertPool.getRole().equals("ROLE2006080116033211358") || expertPool.getRole().equals("ROLE17A73D54752") || expertPool.getRole().equals("ROLE179AB746279")) {%>
						<div class="data_box_both">
							<div>
								<a href="javascript:changeContentFrame('___1', '/action/WeeklyReportCabinetAction.do?mode=getMyWeeklyReportList');">							
								<p><i class="mdi mdi-calendar-weekend"></i>주간진척관리</p>
								<p class="total"><span id="wreportCount"></span><span>건</span></p>
								</a>
							</div>
							<div>		
								<a href="javascript:changeContentFrame('___1', '/action/MonthlyReportCabinetAction.do?mode=getMyMonthlyReportList');">					
								<p><i class="mdi mdi-file-chart-outline"></i>프로젝트레포트</p>
								<p class="total"><span id="mreportCount"></span><span>건</span></p>
								</a>
							</div>
						</div>
						<%} %> --%>
					</div>
				</div>
				<!-- // 메일/업무 -->				
	
<%
	List menuList = (List)request.getAttribute("menuList");
	for(int a=0; menuList.size() > a; a++){
		NodeData data = (NodeData)menuList.get(a);
		if(data.getChildren().size()>0){
			if(((NodeData)(data.getChildren().get(0))).getMenuType() == 3){//last node
%>
				<div class="leftMenu" style="display: none;" id="<%=data.getId() %>" >
					<ul class="dropdown depth_menu"><!-- 2뎁스 메뉴에 필요 .depth_menu -->
					<li class="on"><!-- 2뎁스 메뉴에 필요 .on -->
						<div class="drop_title"><i class="mdi mdi-bulletin-board"></i><a title="메뉴 보이기/숨기기"><%=data.getText() %></a></div>
						<div class="drop_data sc">
	                    <ul>
<%
				for(int b=0; data.getChildren().size() > b; b++){
					NodeData data_sub = (NodeData)data.getChildren().get(b);
%>
						<li><a href="javascript:changeContentFrame('<%=data.getId() %>', '<%=data_sub.getUrl() %>')"><%=data_sub.getText()%></a></li>
<%
				}
%>
						</ul>
						</div></li></ul>
				</div>
<%
			}else if(((NodeData)(data.getChildren().get(0))).getMenuType() == 2){//middle node
				for(int b=0; data.getChildren().size() > b; b++){
					NodeData data_sub = (NodeData)data.getChildren().get(b);
%>
				<div class="leftMenu" style="display: none;" id="<%=data_sub.getId() %>" >
					<ul class="dropdown depth_menu"><!-- 2뎁스 메뉴에 필요 .depth_menu -->
					<li class="on"><!-- 2뎁스 메뉴에 필요 .on -->
						<div class="drop_title"><i class="mdi mdi-bulletin-board"></i><a title="메뉴 보이기/숨기기"><%=data_sub.getText() %></a></div>
						<div class="drop_data sc">
                   	 <ul>
<%					
					for(int c=0; data_sub.getChildren().size() > c; c++){
						NodeData data_sub_sub = (NodeData)data_sub.getChildren().get(c);
%>
						<li><a href="javascript:changeContentFrame('<%=data_sub.getId() %>', '<%=data_sub_sub.getUrl() %>')"><%=data_sub_sub.getText()%></a></li>
<%
					}
%>
					</ul>
				</div></li></ul>
				</div>
<%
				}
			}
		}
	}
%>	
	</li>
</ul>
					
<!-- 일정 -->
<%
	List todayScheduleList = (List)request.getAttribute("todayScheduleList");
	List tomorrowScheduleList = (List)request.getAttribute("tomorrowScheduleList");
	Object todayScheduleListCount = request.getAttribute("todayScheduleListSize");
	Object tomorrowScheduleListCount = request.getAttribute("tomorrowScheduleListSize");
%>
<ul class="dropdown">
	<li>
		<div class="drop_title"><i class="mdi mdi-format-list-checks"></i><a href="javascript:;" title="메뉴 목록 보이기/숨기기">오늘 일정<span class="rec_count"></span><span>(<%=todayScheduleListCount %>)</span></a></div>
		<div class="drop_data sc">
			<ul>
			<%
				if(todayScheduleList.size() > 0) {
						for(int i =0; todayScheduleList.size() > i; i++){
							if(todayScheduleList.get(i) instanceof DailyProjectInfo){
								DailyProjectInfo d = (DailyProjectInfo)todayScheduleList.get(i);
				%>
					<li>
						<p class="time">프로젝트 일정</p>
						<p class="schedule"><%=d.getProjectName() %></p>
					</li>
				<%
					}else if(todayScheduleList.get(i) instanceof DailyScheduleInfo){
								DailyScheduleInfo d = (DailyScheduleInfo)todayScheduleList.get(i);
				%>
					<li><a href="javascript:changeContentFrame('___1', '/_new/schedule/personal/list');">
						<p class="time"><%=d.getStartHour() %>:<%=d.getStartMin() %>~<%=d.getEndHour() %>:<%=d.getEndMin() %></p>
						<p class="schedule"><%=d.getContent() %></p>
					</a></li>
				<%					
							}
						}
					} else {
				%>
					<li>
						<p class="schedule">일정이 없습니다.</p>
					</li>
				<%
					}
				%>
			</ul>
		</div>
	</li>
</ul>

	<ul class="dropdown">
		<li>
			<div class="drop_title"><i class="mdi mdi-format-list-checks"></i><a href="javascript:;" title="메뉴 목록 보이기/숨기기">내일 일정<span>(<%=tomorrowScheduleListCount %>)</span></a></div>
			<div class="drop_data sc">
				<ul>
					<%
			if(tomorrowScheduleList.size() > 0) {
				for(int i =0; tomorrowScheduleList.size() > i; i++){
					if(tomorrowScheduleList.get(i) instanceof DailyProjectInfo){
						DailyProjectInfo d = (DailyProjectInfo)tomorrowScheduleList.get(i);
		%>
			<li>
					<p class="time">프로젝트 일정</p>
					<p class="schedule"><%=d.getProjectName() %></p>
			</li>
		<%
					}else if(tomorrowScheduleList.get(i) instanceof DailyScheduleInfo){
						DailyScheduleInfo d = (DailyScheduleInfo)tomorrowScheduleList.get(i);
		%>
			<li><a href="javascript:changeContentFrame('___1', '/_new/schedule/personal/list');">
							<p class="time"><%=d.getStartHour() %>:<%=d.getStartMin() %>~<%=d.getEndHour() %>:<%=d.getEndMin() %></p>
							<p class="schedule"><%=d.getContent() %></p>
			</a></li>
		<%		
					/* }else if(tomorrowScheduleList.get(i) instanceof CompanyScheduleInfoData){
						CompanyScheduleInfoData d = (CompanyScheduleInfoData)tomorrowScheduleList.get(i); */
		%>
			<%-- <li><img src="/images/main/01_06.gif" alt="회사일정" /><a href="/action/CompanyScheduleAction.do?mode=companyScheduleOfMonth"><span class="bold"><%=d.getStartHour() %>:<%=d.getStartMin() %>~<%=d.getEndHour() %>:<%=d.getEndMin() %></span> <%=d.getContent() %></a></li> --%>
		<%			
					}
				}
			} else {
		%>
			<li>
				<p class="schedule">일정이 없습니다.</p>
			</li>
		<%
			}
		%>
				</ul>
			</div>
		</li>
	</ul>				
</div>
				<!-- // status_area -->
				<div class="contents_area">

					<div class="header">

						<div class="gnb">
							<ul>
<%
	for(int a=0; menuList.size() > a; a++)
    {
		NodeData data = (NodeData)menuList.get(a);
		if (!data.getText().equals("ADMIN")) 
        {
 %>
			<li>
                <a href="javascript:changeContentFrame('<%=data.getId() %> ', ' <%=data.getUrl() %>  ')"><%=data.getText() %></a>
				<div>
<%
                            if(data.getChildren().size()>0)
                            {
                                if(((NodeData)(data.getChildren().get(0))).getMenuType() == 3)
                                {//last node
%>
                                    <ul>
<%
                                    for(int b=0; data.getChildren().size() > b; b++){
                                        NodeData data_sub = (NodeData)data.getChildren().get(b);
%>
                                         <li><a href="javascript:changeContentFrame('<%=data.getId() %>  ', ' <%=data_sub.getUrl() %>  ')"> <%=data_sub.getText() %>  </a></li>
<%                
                                    }
%>                                    
                                    </ul>
<%
                                }
                        
                                else if(((NodeData)(data.getChildren().get(0))).getMenuType() == 2)
                                {//middle node
                                    for(int b=0; data.getChildren().size() > b; b++)
                                    {
                                        NodeData data_sub = (NodeData)data.getChildren().get(b);
%>
                                        <ul>
                                           <li>
                                                <a href="javascript:changeContentFrame('<%=data_sub.getId() %>  ', ' <%=data_sub.getUrl() %>  ')"> <%=data_sub.getText() %>  </a>
                                                 <ul>
<%
                                                    for(int c=0; data_sub.getChildren().size() > c; c++)
                                                    {
                                                        NodeData data_sub_sub = (NodeData)data_sub.getChildren().get(c);
%>
                                                            <li><a href="javascript:changeContentFrame('<%=data_sub.getId() %>  ', '<%=data_sub_sub.getUrl() %>  ')"> <%=data_sub_sub.getText()  %> </a></li>
<%
                                                    }
 %>                                                   
                                                </ul>
                                           </li>
                                        </ul>
<%                                        
                                    }
                                }
                            }
%>
	        	</div>
            </li>
<%
        }
	}
%>
</ul>
						</div>
						<!-- // gnb -->
						
						<div class="navbar">
							<div class="overlay"></div>
							<div class="conts_box">
								<div class="in_box">
									<section>
										<div class="scroll_box sc">
											<div class="navbar_menu">
												<p class="home"><a href="/"><i class="mdi mdi-home"></i>HOME</a></p>										
	<ul>
<%
	List menuList2 = (List)request.getAttribute("menuList");
	for(int a=0; menuList2.size() > a; a++)
    {
		NodeData data = (NodeData)menuList2.get(a);
		if (!data.getText().equals("ADMIN")) 
        {
 %>
			<li>
                <a href="javascript:;">
	                <%if(data.getText().equals("게시판")){%><i class="mdi mdi-bulletin-board"></i><%}else if(data.getText().equals("고객정보")){%><i class="mdi mdi-account-group-outline"></i><%}else if(data.getText().equals("스케줄관리")){%><i class="mdi mdi-calendar-month"></i>
				    <%}else if(data.getText().equals("전자결재")){%><i class="mdi mdi-stamper"></i><%}else if(data.getText().equals("PMS")){%><i class="mdi mdi-projector-screen-outline"></i><%}else if(data.getText().equals("업무지원")){%><i class="mdi mdi-handshake"></i>
				    <%}else if(data.getText().equals("KDB")){%><i class="mdi mdi-server-network"></i><%}else if(data.getText().equals("네트워크")){%><i class="mdi mdi-access-point-network"></i>
				    <%}else if(data.getText().equals("경영관리")){%><i class="mdi mdi-folder-open-outline"></i><%}else if(data.getText().equals("관리자")){%><i class="mdi mdi-badge-account"></i><%}%><%=data.getText() %>
			   	</a>
<%
                            if(data.getChildren().size()>0)
                            {
                                if(((NodeData)(data.getChildren().get(0))).getMenuType() == 3)
                                {//last node
%>
                                    <ul class="noDepth">
<%
                                    for(int b=0; data.getChildren().size() > b; b++){
                                        NodeData data_sub = (NodeData)data.getChildren().get(b);
%>
                                         <li>
	                                         <p>															
												<input type="checkbox" id="<%=data_sub.getId() %> " class="bookmark" onclick="toggleMyMenu('<%=data_sub.getMenuId() %>', <%=data_sub.isMenuMy()?"true":"false" %>)"  <%=data_sub.isMenuMy()?"checked":"" %>   ><label for="<%=data_sub.getId() %> "></label>
                                         		<a href="javascript:changeContentFrame('<%=data.getId() %>  ', ' <%=data_sub.getUrl() %>  ')"> <%=data_sub.getText() %></a>
                                         	</p>
                                         </li>
<%                
                                    }
%>                                    
                                    </ul>
<%
                                }
                        
                                else if(((NodeData)(data.getChildren().get(0))).getMenuType() == 2)
                                {//middle node
%>
								<ul>
<%
                                    for(int b=0; data.getChildren().size() > b; b++)
                                    {
                                        NodeData data_sub = (NodeData)data.getChildren().get(b);
%>
                                      
                                           <li>
                                           	 	<p>															
													<input type="checkbox" id="<%=data_sub.getId() %>" class="bookmark"><label for="<%=data_sub.getId() %>"></label>
                                                	<a href="javascript:changeContentFrame('<%=data_sub.getId() %>  ', ' <%=data_sub.getUrl() %>  ')"> <%=data_sub.getText() %>  </a>
                                                 </p>
                                                 <ul>
<%
                                                    for(int c=0; data_sub.getChildren().size() > c; c++)
                                                    {
                                                        NodeData data_sub_sub = (NodeData)data_sub.getChildren().get(c);
%>
                                                            <li>
                                                            	 <p>															
																	<input type="checkbox" id="<%=data_sub_sub.getUrl() %>" class="bookmark"  onclick="toggleMyMenu('<%=data_sub_sub.getMenuId() %>', <%=data_sub_sub.isMenuMy()?"true":"false" %>)"  <%=data_sub_sub.isMenuMy()?"checked":"" %>  ><label for="<%=data_sub_sub.getUrl() %>"></label>
                                                            		<a href="javascript:changeContentFrame('<%=data_sub.getId() %>  ', '<%=data_sub_sub.getUrl() %>  ')"> <%=data_sub_sub.getText()  %> </a>
                                                            	</p>		
                                                           	</li>
<%
                                                    }
 %>                                                   
                                                </ul>
                                           </li>
                                        
<%                                        
                                    }
%>
								</ul>
<%
                                }
                            }
%>
            </li>
<%
        }
	}
%>													
												</ul>
											</div>
										</div>
									</section>
								</div>
							</div>
							<a class="btn_close" href="javascript:;"><span class="blind">창닫기</span></a>
						</div>						
						<!-- // navbar -->

					</div>
					<!-- // header -->

					<iframe name="contentFrame" id="_contentFrame" scrolling="yes" width="100%" height="100%" frameborder="0" 
						 src="/action/GadgetAction.do?mode=mainPage" onload="iframeAutoResize(this, src)" 
						 style="" >
					</iframe>
					<!-- // contentFrame -->
					
					<div class="footer">
						<p>Copyright  ⓒ 2022 KIDZLALA. All rights reserved</p>
					</div>
					<!-- // footer -->		

				</div>
				<!-- // contents_area -->

			</div>
			<!-- // container -->				

		</div>
		<!-- // wrap -->

	</body>
</form>
</html>

<%
	String value = request.getParameter("alert");
%>