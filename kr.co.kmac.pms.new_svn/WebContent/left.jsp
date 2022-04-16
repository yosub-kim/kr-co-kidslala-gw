<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<link href='<c:url value="/css/kmac.css"/>' 	rel="stylesheet" type="text/css"></link>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script language="JavaScript" type="text/JavaScript">
<%-- 개별 스크립트 영역 --%> 

function kdb_popup() {
	userwidth = (screen.width - 1);
	userheight = (screen.height - 1);
	
	window.open('http://intranet.kmac.co.kr/kmac/ks/hub.asp','new_kdb_pop','scrollbars=yes,toolbar=yes,location=yes,status=yes,menubar=yes,resizable=yes,width='+userwidth+',height='+userheight+',left=0,top=0');
}

function leftMenuMouseOver(obj) {
	//new Effect.Highlight(obj, { startcolor: '#6ABDE5', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	return false;
}
function leftMenuMouseOut() {
	
}
function menu_Hide() {
	document.getElementById("firstOnly").style.display = '';
	document.getElementById("groupBox").style.display = 'none';
}
function open_nameCard() {
	window.top.parent.resizeIntranetFrame();
}
function menuWrite(menuList,groupName, menuId) {
	quickLinkToggleState = "opened";
	<%if(session.getAttribute("jobClass").equals("A") || session.getAttribute("jobClass").equals("J")
			|| session.getAttribute("userId").equals("jyglha") || session.getAttribute("userId").equals("user12")){%>
		quickLiskToggle();
	<%}%>
		
	var tableId = document.getElementById("menuListTable");

	for(var i = tableId.childNodes.length -1; i >= 0; i--) {
		tableId.removeChild(tableId.childNodes[i]);
	}
	for(var i = 0; i < menuList.length; i++){
		var tr = document.createElement("tr");
		var sUrl = menuList[i].url;
		var sMenuId = menuList[i].menuId;
		if(sUrl.substring(0,5).toUpperCase()  == "HTTP:") sUrl += "?_Id=<c:out value="${userId}"/>"; 
		
		var sHTML = "";
		sHTML += "<td onmouseover=\"leftMenuMouseOver(this);\" onclick=\"menuClick('" + sUrl + "'," + i + ", '"+ sMenuId +"');\" height=\"28\" align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;";
		sHTML += "<img src=\"/images/frame/left_dot02.gif\" width=\"6\" height=\"7\" align=\"absmiddle\">&nbsp; <span class=\"leftsubtw\" style=\"cursor: hand;\">" + menuList[i].text + "</span>\n";
		sHTML += "</td>\n";

		//내명함 관리일 경우
		if(menuId == 'MENU12303265620'){
			var status0 = AjaxRequest.post (
					{	'url': "/action/ScheduleAction.do?mode=countMySchedule",
						'onSuccess':function(obj){
							var res = eval('(' + obj.responseText + ')');
							$('scheduleCount').innerHTML = res.scheduleCount;
						}, 
						'onLoading':function(obj){},
						'onError':function(obj){ 
							alert("하위 메뉴  정보를 읽어올 수 없습니다.");
						}
					}
			); 			
		}
		
		sHTML = HTMLtoDOM(sHTML);
		
		
		tableId.appendChild(sHTML);
	}

	
	<%if(session.getAttribute("jobClass").equals("A") || session.getAttribute("jobClass").equals("J")
			|| session.getAttribute("userId").equals("kos137") || session.getAttribute("userId").equals("user12")){%>
		document.getElementById("firstOnly").style.display = 'none';
	<%}%>
	document.getElementById("groupBox").style.display = '';
	document.getElementById("menuGroupName").innerText = groupName;
	menuClick(menuList[0].url, 0, menuId);

}
function menuClick(sURL,idx, menuId) {
	///* -- 메뉴 클릭 로그 저장 기능 비활성화
	AjaxRequest.get (
			{	'url': "/action/AccessLogAction.do?mode=insertLog&menuId="+menuId,
				'onSuccess':function(obj){}, 
				'onLoading':function(obj){},
				'onError':function(obj){}
			}
	);
	window.parent.rightFrame.location = sURL;
	
	var tbody = document.getElementById("menuListTable");
	for(var i = 0 ; i < tbody.childNodes.length; i++) {
		if(tbody.childNodes[i].childNodes.length > 0){
			var str = tbody.childNodes[i].childNodes[0].innerHTML;
			//alert(str);
			str = str.replaceAll('leftsubtw','leftsubt');
			str = str.replaceAll('left_dot01','left_dot02');
			tbody.childNodes[i].childNodes[0].innerHTML = str;
		}
	}
	
	if(tbody.childNodes.length > 0){
		var str = tbody.childNodes[idx].childNodes[0].innerHTML;
		str = str.replaceAll('leftsubt','leftsubtw');
		str = str.replaceAll('left_dot02','left_dot01');
		tbody.childNodes[idx].childNodes[0].innerHTML = str;	
	}
}
function goOnlineBudget(username) {
 	document.getElementById("onlineBudgetForm").action="https://newbudget.kmac.co.kr:8080/com/login_chk.jsp";
	document.getElementById("onlineBudgetForm").empno.value=username;
	document.getElementById("onlineBudgetForm").target="new";
	document.getElementById("onlineBudgetForm").submit();
}
function goEducationManagement() {
	window.open("http://www.kmac.co.kr/Newadmin/login/login.htm");
}

function goEhrdManagement() {
	window.open("http://ehrd.kmac.co.kr/System_Manage/check_login.sol?user_id=<%=session.getAttribute("userId")%>","","toolbar=no,status=no,width=1024,height=768,directories=no,scrollbars=0,location=no,resizable=no,menubar=no,screenX=0,left=0,screenY=0,top=0,right=0");
}

function goTaskManagement() {
	window.open("http://intranet.kmac.co.kr/kmac/task/task/taskAllview.asp", "TaskManagement", "top=10,left=10,width=630,height=930,scrollbars=yes");
}
function goSuggestion() {
	window.open("http://intranet.kmac.co.kr/kmac/task/suggest_board/Board_list.asp", "Suggestion","top=10,left=10,width=800,height=550,scrollbars=no");
}
function goHiraCallCenter(userId, password) {
	window.open("http://hira.kmac.co.kr/main/login_ok.asp?c_txt_userid="+userId+"&c_txt_password="+password, "NHIC", "top=0,left=0,width=1024,height=768,scrollbars=no");
}
var quickLinkToggleState = "opened";
function quickLiskToggle() {
	/* -- quick link 메뉴 숨기기 펼기기 기능 비활성화 
	if(quickLinkToggleState == "opened"){
		quickLinkToggleState = "closed";
		$('quickLinkImage').src = "/images/frame/tit_quicklink_w.jpg";
		$('quickLinkTable').hide();
	}else if(quickLinkToggleState == "closed"){
		quickLinkToggleState = "opened";
		$('quickLinkImage').src = "/images/frame/tit_quicklink.jpg";
		$('quickLinkTable').show();
	}
	*/
}
function countMyPanel(){
	var status0 = AjaxRequest.post (
			{	'url': "/action/ScheduleAction.do?mode=countMySchedule",
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					$('scheduleCount').innerHTML = res.scheduleCount;
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){ 
					alert("일정 정보를 읽어올 수 없습니다.");
				}
			}
	); 	
	var status1 = AjaxRequest.post (
			{	'url': "/action/ProjectReportCabinetAction.do?mode=countMyProjectReport",
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					$('preportCount').innerHTML = res.preportCount;
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("지도일지 정보를 읽어올 수 없습니다.");
				}
			}
	); 	
	var status2 = AjaxRequest.post (
			{	'url': "/action/WorkCabinetAction.do?mode=countMyWorkList",
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					$('workCount').innerHTML = res.workCount;
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
					$('mailCount').innerHTML = res.mailCount;
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("이메일 정보를 읽어올 수 없습니다.");
				}
			}
	); 	
	var status4 =  AjaxRequest.post (
				{	'url': "/action/ProjectARExpenseSanctionAction.do?mode=checkARExpenseSanction",
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						//alert(res.hasExSanction);
						if(res.hasExSanction == "true" || res.hasExRestSanction == "true"){
							window.open("/system/popup/expPopUp.jsp?cnt="+res.cntExSanction+"&restCnt="+res.cntExRestSanction, "expPopup","top=10,left=10,width=280,height=270,scrollbars=no,toolbar=no,location=no,status=no");
							//if(confirm("품의 해야 할 강사료 내역 n건이 있습니다.\n해당 화면으로 바로가시겠습니까 ?")){
							//	window.parent.rightFrame.location = "/action/ProjectARExpenseSanctionAction.do?mode=loadARExpenseSanctionForm&approvalType=expenseB"; 
							//}
						}
					}, 
					'onLoading':function(obj){},
					'onError':function(obj){ 
						alert("강사료 결재 정보를 읽어올 수 없습니다. #1");
					}
				}
		); 	
}
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

</script>
</head>
<body style="margin-left: 7px" onload="countMyPanel()">
<form name="webmailForm" method="post">
	<input name="user_account" value="" type="hidden" />
	<input name="pass" value="" type="hidden" />
	<input name="cmd" value="" type="hidden" />
</form>
<table width="189" height="500" border="0" cellpadding="0" cellspacing="0">
	<tr valign="top">
		<td height="117" align="center">
			<table width="183" height="122" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="top">
						<table width="183" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="30" align="center" valign="bottom" background="/images/frame/left_minfo_01.jpg">
									<table width="183" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="47" height="26">&nbsp;</td>
											<td width="113" valign="middle"" class="leftNoClickInfoTableTd"><strong><%=DateTime.getDateString(".")+"."+DateTime.getDayofWeekFullName() %></strong></td>
											<td width="23">&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td height="27" align="center" valign="bottom" background="/images/frame/left_minfo_03.jpg">
									<table width="80%" border="0" cellspacing="0" cellpadding="0">
										<tr><td height="3"></td></tr>
										<tr><td height="1" bgcolor="#F7DDBE" ></td></tr>
										<tr>  
											<td height="26" align="center"" bgcolor="#F7DDBE" valign="middle" class="leftNoClickInfoTableTd">
												<table>
													<!-- <tr><td class="leftLoginInfoTableTd"><org:user-org id="loginedUser" group="div"/></td></tr>  -->
													<tr><td class="leftLoginInfoTableTd"><%=session.getAttribute("deptName") %></td></tr>
													<tr><td class="leftLoginInfoTableTd"><strong><authz:authentication operation="name" /></strong>님 안녕하세요</td></tr>
												</table>
											</td>
										</tr>
									</table>
								</td> 
							</tr>
							<tr>
								<td align="center" background="/images/frame/left_minfo_03.jpg"> 
									<table>
										<tr><td height="4"></td></tr>
									</table>
									<table width="90%" border="0" cellspacing="0" cellpadding="0">
										<tr align="left">
											<td width="47%" height="18" align="left" class="leftClickInfoTableTd" onclick="menuClick('/action/WorkCabinetAction.do?mode=getMyWorkList',0, 'MENU12302FF0656');"
												><img src="/images/frame/left_dot.jpg" width="3" height="3" align="absmiddle"
												> 새 업무: <strong><span id="workCount"></span></strong></td>
											<td width="3%"></td>
											<td width="50%" class="leftClickInfoTableTd" onclick="goEmail()"
												><img src="/images/frame/left_dot.jpg" width="3" height="3" align="absmiddle" onclick="goEmail();"
												> 새 메일: <strong><span id="mailCount"></span></strong></td>
											
										</tr>
									</table>
									<table width="90%" border="0" cellspacing="0" cellpadding="0">
										<tr align="left">
											<td width="47%" class="leftClickInfoTableTd" onclick="menuClick('/action/ScheduleAction.do?mode=scheduleOfMonth',0, 'MENU2006072101554591910');"
												><img src="/images/frame/left_dot.jpg" width="3" height="3" align="absmiddle" 
												> 오늘일정: <strong><span id="scheduleCount"></span></strong></td>
											<td width="3%"></td>
											<td width="50%" height="18" align="left" class="leftClickInfoTableTd"  onclick="menuClick('/action/ProjectReportCabinetAction.do?mode=getMyProjectReportList',5, 'MENU1234BCC734E');"
												><img src="/images/frame/left_dot.jpg" width="3" height="3" align="absmiddle" 
												> 지도일지: <strong><span id="preportCount"></span></strong></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td background="/images/frame/left_minfo_03.jpg"><img src="/images/frame/left_minfo_04.jpg" width="183" height="15"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td width="189" height="8" align="center" valign="top"></td>
	</tr>
	
	
	
	
	
	
	
	
	<tr id="groupBox" style="display:none;">
		<td align="center" valign="top">
		<table width="174" border="0" cellspacing="0" cellpadding="0">
		
		
			<tr align="left" valign="bottom">
				<td width="183" height="47" align="center" valign="top" background="/images/frame//left_title01.jpg">
					<table width="118" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="118" height="10"></td>
						</tr>
						<tr>
							<td height="15"><img src="/images/frame/left_titic.jpg" width="23" height="19" align="absmiddle"> <span id="menuGroupName"  class="leftitleb"></span></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr background="/images/frame/left_titlebg03.jpg"> 
				<td align="center" background="/images/frame/left_titlebg03.jpg"> 
					<table width="170" border="0" cellspacing="0" cellpadding="0">
						<tbody id="menuListTable">

						</tbody>
					</table>
				</td>
			</tr> 
			<tr>
				<td><img src="/images/frame/left_titlebg04.jpg" width="183" height="15"></td>
			</tr>
		</table>
		</td>
	</tr>
	
	
	
	
	
	
	
	
	<%if(session.getAttribute("jobClass").equals("A") || session.getAttribute("jobClass").equals("J") 
			|| session.getAttribute("jobClass").equals("H") || session.getAttribute("userId").equals("kos137")
			|| session.getAttribute("userId").equals("user12") || session.getAttribute("userId").equals("user13")){%>
	<%if(!session.getAttribute("jobClass").equals("J") && !session.getAttribute("jobClass").equals("H")){%>					
	<tr id="firstOnly">
		<td align="center">
			
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="center">
						<table>
							<tr>
								<td>
									<a href="#" onClick="goOnlineBudget('<authz:authentication operation="username" />');"><IMG src="/images/frame/left_banner_budget.gif" border="0" alt="온라인 예산 시스템"></a>
								</td>
								<td><table height="30" width="1"><tr><td style="border-right:solid 1; border-right-color: #DCDCDC"></td></tr></table></td>
								<td>
									<a href="#" onClick="goEducationManagement();"><IMG src="/images/frame/left_banner_edu.gif" border="0" alt="교육관리 사이트"></a>
								</td>
								<td><table height="30" width="1"><tr><td style="border-right:solid 1; border-right-color: #DCDCDC"></td></tr></table></td>
								<td>
									<a href="#" onClick="goEhrdManagement();"><IMG src="/images/frame/left_banner_ehrd.gif" border="0" alt="eHRD"></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<%} else { %>
	<tr id="firstOnly">
		<td align="center"></td>
	</tr>
	<%} %>
	<tr>
		<td height="10" align="center" valign="top">&nbsp;</td>
	</tr>
	<tr> 
		<td height="100%" align="center" valign="top">
			<table width="170" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="20" align="center" valign="top"> 
					<!--폴딩하기전 타이틀이미지:tit_quicklink_w.jpg 폴딩 후 반전이미지:images/tit_quicklink.jpg-->
						<img id="quickLinkImage" src="/images/frame/tit_quicklink_w.jpg" style="cursor: hand;" onclick="quickLiskToggle();"></td>
				</tr> 
				<tr>
					<td>
						<table id="quickLinkTable" width="100%" border="0">
							<!--
							<tr>
								<td height="9" align="center" valign="top"><img src="/images/frame/left_banner_top.jpg" alt="" width="183" height="9"></td>
							</tr>				
							<tr>
								<td align="center" valign="top"><a href="http://intranet.kmac.co.kr/kmac/kdb/vod/2009plan.htm" target="rightFrame"><img src="/images/frame/left_banner_n01.jpg" width="137" height="28" border="0" alt="2009년 사업계획"></a></td>
							</tr>
							-->
	 						<tr>
								<td align="center" valign="middle"><img src="/images/frame/left_banner_space.jpg" width="142" height="7"></td>
							</tr>
							<tr>
								<td align="center" valign="top"><a href="http://intranet.kmac.co.kr/kmac/network/shared_value.asp" target="rightFrame"><img src="/images/frame/left_banner_n04.jpg" width="137" height="28" border="0" alt="KMAC 공유가치"></a></td>
							</tr>
	 						<tr>
								<td align="center" valign="middle"><img src="/images/frame/left_banner_space.jpg" width="142" height="7"></td>
							</tr>
							<tr>
								<td align="center" valign="top" ><a href="http://intranet.kmac.co.kr/kmac/task/propose_board/Board_list.asp?" target="rightFrame"><img src="/images/frame/left_banner_n14.jpg" width="137" height="28" alt="제안하기"></td>   <!--  style="cursor: hand;" onclick="quickLiskToggle();" -->
							</tr>
	 						<tr>
								<td align="center" valign="middle"><img src="/images/frame/left_banner_space.jpg" width="142" height="7">
							</tr>
							<tr>
								<td align="center" valign="top"><a href="#" onclick="javascript:kdb_popup();"><img src="/images/frame/left_banner_n11.jpg" width="137" height="28" border="0" alt="KDB"></a></td>
							</tr>
	 						<tr>
								<td align="center" valign="middle"><img src="/images/frame/left_banner_space.jpg" width="142" height="7">
							</tr>
						<%if(!session.getAttribute("dept").equals("2000")){%>
							<tr>
								<td align="center" valign="top" ><img src="/images/frame/left_banner_n12.jpg" width="137" height="28" style="cursor: hand;" onclick="open_nameCard();" alt="내명함 관리"></td>   <!--  style="cursor: hand;" onclick="quickLiskToggle();" -->
							</tr>
	 						<tr>
								<td align="center" valign="middle"><img src="/images/frame/left_banner_space.jpg" width="142" height="7">
							</tr>
						<%}%>
						<%if(!session.getAttribute("userId").equals("user13")){ %>
							<tr>
								<td align="center" valign="top" ><a href="/emergencyConnection/emergencyTab.jsp" target="rightFrame"><img src="/images/frame/left_banner_n03.jpg" width="137" height="28"></a></td>   <!--  style="cursor: hand;" onclick="quickLiskToggle();" -->
							</tr>
						<%}%>
							
							<tr>
								<td align="center" valign="middle"><img src="/images/frame/left_banner_space.jpg" width="142" height="7">
							</tr>
							<tr>
								<td align="center" valign="top" ><a href="http://intranet.kmac.co.kr/kmac/education/cs_confer_2018_plan_state.asp" target="rightFrame"><img src="/images/frame/left_banner_n13.jpg" width="137" height="28" alt="컨퍼런스 참가현황"></td>
							</tr>
							
							<tr>
						    	<td align="center" valign="bottom"><img src="/images/frame/left_banner_bottom.jpg" width="183" height="9"></td>
							</tr>
						</table>
					</td>
				</tr>				
			</table>
		</td>
	</tr>
	<%} %>
</table>
<form name="onlineBudgetForm" method="post" id="onlineBudgetForm">
	<input name="empno" value="" type="hidden" >
</form>
<div id="xxxTempDivForTemplete" style="display:none;"></div>
</body>
</html>