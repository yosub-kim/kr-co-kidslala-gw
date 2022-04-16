<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script language="JavaScript" type="text/JavaScript">
<%-- 개별 스크립트 영역 --%>
	function logoff() {
		top.location.href = "<c:url value='j_acegi_logout'/>";
	}
	function goHome() {
		//if(isMobile){
		//	top.location.href="/m";
		//}else{
			top.location.href="/main.jsp";
		//}
		//window.parent.leftFrame.menu_Hide();
		//window.parent.rightFrame.document.location = "/action/GadgetAction.do?mode=mainPage";
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
	                    	webmailForm.cmd.value = "main";
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
	
	function openSiteMap(){
		var openSiteWin;
		configWin = window.open("/system/config/Sitemap.jsp","openSiteWin", "top=10,left=10,width=820,height=610,scrollbars=yes");
		configWin.focus();
	}
	
	function openConfigWin() {
		var configWin;
		configWin = window.open("/system/config/MyConfig.jsp","configWin", "top=100,left=100,width=520,height=555,scrollbars=no");
		configWin.focus();
	}
	
	function Active_Menu(menuName) {
		var pTr = document.getElementById("tr_topMenu");
		for(var j = 0; j < pTr.childNodes.length; j++){
			var tdObj = pTr.childNodes[j];
			for(var i = 0; i < tdObj.childNodes.length; i++){
				if(tdObj.childNodes[i].nodeName == "A") {
					if(tdObj.childNodes[i].innerText == menuName)
						tdObj.childNodes[i].className = "activeMenu";
					else						
						tdObj.childNodes[i].className = "";	
				}
			}
		}
	}
	var grobalIdx = "X";
	function topMenu_Over(idx, obj){
		//alert(grobalIdx +":"+ idx);
		if(grobalIdx != idx){
			grobalIdx = idx;
			var shtml = "";
			if(menuList[idx-1].menuList[0].menuList.length > 0){
				//alert("3단 입니다.");
				var items = menuList[idx-1].menuList;
				for(var i = 0; i < items.length; i++) {
					shtml += (i == 0) ? "" : " | ";
					shtml += "&nbsp;&nbsp;<a href=\"javascript:middleMenu_Click(" + idx + "," + i + ");\">" + items[i].text + "</a>&nbsp;&nbsp;";
				}		
			}
			
			var middlemenu = $("middleMenu");
			middlemenu.innerHTML = shtml;
			var layerWin = $("middleMenuWin");
			var sleft = event.x;
			var swidth = middlemenu.offsetWidth;
			sleft = sleft - (swidth/2) + 20;
			if(sleft < 210) sleft = 210;
			if((sleft + swidth) > 980) {
				sleft = 980 - swidth;
			}
			layerWin.style.left = sleft;
		
			layerWin.style.display = '';
			//alert(sleft);
		}
	}
	function topMenu_Click(idx,obj){
		Active_Menu(obj.innerText);
		if(menuList[idx-1].menuList[0].menuList.length > 0){
			//alert("3단 입니다.");
		} else {
			window.top.parent.resizePmsFrame();
			window.parent.leftFrame.menuWrite(
					menuList[idx-1].menuList
					,menuList[idx-1].text
					,menuList[idx-1].menuId
			);
		}
	}
	function admin_menu() {
		window.parent.leftFrame.menuWrite(sysMenuList,'System', null);
	}
	
	function test_menu() {
		window.parent.leftFrame.menuWrite(testMenuList,'테스트', null);
	}
	
	function middleMenu_Click(idx,idy) {
		if(idx == '8' && idy == '0'){
			window.top.parent.resizeIntranetFrame();
		}else{
			window.top.parent.resizePmsFrame();
		}
		Active_Menu(menuList[idx-1].text);
		window.parent.leftFrame.menuWrite(
				menuList[idx-1].menuList[idy].menuList
				,menuList[idx-1].menuList[idy].text
				,menuList[idx-1].menuList[idy].menuId
		);
	}
	
	function Menu(sText,sId,sDepth,sURL,sLeaf,sList, smenuId) {
		this.text = sText;
		this.id   = sId;
		this.depth= sDepth;
		this.url  = sURL;
		this.leaf = sLeaf;
		var menuList = new Array();
		this.menuList = sList;
		this.menuId   = smenuId;
	}
	var menuList = new Array();
	<c:forEach var="menu" items="${menuList}" varStatus="x">
		var menuList<c:out value="${x.count}"/> = new Array();
		
		<c:forEach var="menu1" items="${menu.children}" varStatus="y">
			var menuList<c:out value="${x.count}"/><c:out value="${y.count}"/> = new Array();
			
			<c:forEach var="menu2" items="${menu1.children}" varStatus="z">
				var menuList<c:out value="${x.count}"/><c:out value="${y.count}"/><c:out value="${z.count}"/> = new Array();
				menuList<c:out value="${x.count}"/><c:out value="${y.count}"/>.push(
						new Menu('<c:out value="${menu2.text}"/>','<c:out value="${menu2.id}"/>'
								,'<c:out value="${menu2.depth}"/>','<c:out value="${menu2.url}" escapeXml="false"/>'
								,'<c:out value="${menu2.leaf}"/>', menuList<c:out value="${x.count}"/><c:out value="${y.count}"/><c:out value="${z.count}"/>
								,'<c:out value="${menu2.menuId}"/>'));
			</c:forEach>
			menuList<c:out value="${x.count}"/>.push(
					new Menu('<c:out value="${menu1.text}"/>','<c:out value="${menu1.id}"/>'
							,'<c:out value="${menu1.depth}"/>','<c:out value="${menu1.url}"  escapeXml="false"/>'
							,'<c:out value="${menu1.leaf}"/>', menuList<c:out value="${x.count}"/><c:out value="${y.count}"/>
							,'<c:out value="${menu1.menuId}"/>'));
		</c:forEach>
		menuList.push(new Menu('<c:out value="${menu.text}"/>','<c:out value="${menu.id}"/>'
				,'<c:out value="${menu.depth}"/>','<c:out value="${menu.url}"  escapeXml="false"/>'
				,'<c:out value="${menu.leaf}"/>', menuList<c:out value="${x.count}"/>
				,'<c:out value="${menu.menuId}"/>'));
	</c:forEach>
	
	var sysMenuList = new Array();
	sysMenuList.push(new Menu('조직 및 인력 관리','','3','/action/OrgMngAction.do?mode=orgMngPage','true',null, null));
	sysMenuList.push(new Menu('Role 관리'      ,'','3','/action/AuthorityAction.do?mode=roleRetrieve','true',null, null));
	sysMenuList.push(new Menu('메뉴 관리'       ,'','3','/action/AuthorityAction.do?mode=menuRetrieve','true',null, null));
	sysMenuList.push(new Menu('코드 관리'       ,'','3','/action/CommonCodeAction.do?mode=codeCMTable','true',null, null));
	sysMenuList.push(new Menu('전자결재 관리'    ,'','3','/action/SanctionTemplateAction.do?mode=getSanctionTemplateList','true',null, null));
	sysMenuList.push(new Menu('프로세스 관리'   ,'','3','/system/process/processManagement.jsp?pane=template','true',null, null)); 
	sysMenuList.push(new Menu('PJ 임시오픈 관리'   ,'','3','/action/ProjectSearchAction.do?mode=getProjectAdminOpenList','true',null, null)); 
	sysMenuList.push(new Menu('가젯 관리'       ,'','3','/action/GadgetAction.do?mode=gadgetList','true',null, null));
	sysMenuList.push(new Menu('팝업 관리'      ,'','3','/action/PopupConfigAction.do?mode=getPopUp','true',null, null));
	sysMenuList.push(new Menu('산출물 Upload'	 ,'','3','/action/ProjectSearchAction.do?mode=getProjectOutputUploadList','true',null, null));
	sysMenuList.push(new Menu('로그인 현황'    ,'','3','/action/AccessLogAction.do?mode=getLoginLogReport','true',null, null));
	sysMenuList.push(new Menu('다운로드 현황'   ,'','3','/action/DownloadLogReportAction.do?mode=getDownloadLogReport','true',null, null));
	sysMenuList.push(new Menu('지도일지 할당'   ,'','3','/project/preport/projectReportManualAssign.jsp','true',null, null));
	
	function openpage(url){
		window.parent.rightFrame.location = url; 
	}
	
</script>
<style type="text/css">
.sitemap em ul li {} 
</style>
</head>
<body>
<div id="sitemapDiv" name="sitemapDiv" class="sitemap" style="display: none; font-family: Dotum,DotumChe,Arial; width: 793px;  height: 400px; border-style: solid; border-width: 1px; border-color: black; margin: 3px; overflow: scroll; vertical-align: top; padding: 3px">
</div>
<script type="text/javascript">
	for(a=0; menuList.length > a; a++){
		$('sitemapDiv').insert(new Element('ul', {'id': 'menuA'+a, 'style': 'background-color: #e6edef; width: 230px; font-size: 14px; font-weight: bold; font-style: normal; border-style: solid; border-color: black; border-width: 1px; margin: 3px; padding: 5px; padding-bottom: 5px; vertical-align: top; float: left; list-style-type: none;'}).update("" + menuList[a].text));
	
		for(b=0; menuList[a].menuList.length > b; b++){
			if(menuList[a].menuList[b].leaf != 'false'){
				$('menuA'+a).insert(new Element('li', {'id': 'menuB'+a+':'+b, 'onclick':'openpage("'+menuList[a].menuList[b].url+'")', 'style': 'cursor: pointer; font-size: 12px; font-weight: normal; margin: 3px; vertical-align: top;'}).update("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+menuList[a].menuList[b].text));
			}else{	
				$('menuA'+a).insert(new Element('li', {'id': 'menuB'+a+':'+b, 'style': 'font-size: 12px; font-weight: normal; margin: 3px; vertical-align: top;'}).update("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+menuList[a].menuList[b].text));
			}
			for(c=0; menuList[a].menuList[b].menuList.length > c; c++){
				$('menuB'+a+':'+b).insert(new Element('dl', {'id': 'menuC'+a+':'+b+':'+c, 'onclick':'openpage("'+menuList[a].menuList[b].menuList[c].url+'")', 'style': 'cursor: pointer; margin: 3px; vertical-align: top;'}).update("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+menuList[a].menuList[b].menuList[c].text));
			}
		}
	}

</script>
<form id="sitemap" name="sitemap" style="display: none;">
	<input type="hidden" name="siteMapHtml" id="siteMapHtml">
</form>
<form name="webmailForm" method="post">
	<input name="user_account" value="" type="hidden" >
	<input name="pass" value="" type="hidden" >
	<input name="cmd" value="" type="hidden" >
</form>
<div id="middleMenuWin" style="position: absolute; left: 214px; top: 83px; height: 20px; z-index: 1; display:inline;">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="9" class="topwhite" id="middleMenu" nowrap></td>
		</tr>
	</table>
</div>
<table width="1020" height="112" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td height="112" valign="top" background="/images/frame/top_bg.jpg">
			<table width="1020" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="189" rowspan="2"><img src="/images/frame/top_logo.gif" width="189" height="112" style="cursor: hand;" onclick="goHome()"></td>
					<td width="10" rowspan="2">&nbsp;</td>
					<td width="821" align="right" valign="top">
						<table width="466" border="0" cellspacing="0" cellpadding="0" >
							<tr><td height="6" ></td></tr>
							<tr>
								<td align="right">
									<img src="/images/frame/glo_home.gif" width="16" height="16" align="absmiddle"><a href="#" onclick="goHome();"> HOME</a>&nbsp;&nbsp;
									<img src="/images/frame/glo_email.gif" width="16" height="16" align="absmiddle"><a href="#" onclick="goEmail()"> E-mail</a>&nbsp;&nbsp;
									<img src="/images/frame/glo_sitemap.gif" width="16" height="16" align="absmiddle"><a href="javascript:openSiteMap();"> SiteMap</a>&nbsp;&nbsp;
									<img src="/images/frame/glo_setting.gif" width="16" height="16" align="absmiddle"><a href="javascript:openConfigWin();"> 환경설정</a>&nbsp;&nbsp;
									<org:admin html="<img src='/images/frame/glo_admin.gif' width='16' height='16' align='absmiddle'><a href='#' onclick='admin_menu();'> ADMIN</a>&nbsp;&nbsp;"/>
									<img src="/images/frame/glo_out.gif" width="16" height="16" align="absmiddle"><a href="#" onclick="logoff();"> 로그아웃</a>&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr> 
					<td valign="top" style="padding-top: 25px">
						<table width="795" height="58" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="20" height="58" background="/images/frame/top_bg_l.png"></td>
								<td width="755" height="58" background="/images/frame/top_bg_c.png" align="left" valign="top" style="padding-top: 7px;">
									<table height="20" border="0" cellpadding="0" cellspacing="0" style="width: 100%">
										<tr id="tr_topMenu">
										<c:forEach var="menu" items="${menuList}" varStatus="i">
											<td height="20" class="topwhiteb" style=" text-align: center;"><a href="#" onMouseOver="topMenu_Over(<c:out value="${i.count}"/>, this);" onClick="topMenu_Click(<c:out value="${i.count}"/>,this);"><c:out value="${menu.text }"/></a></td>
										</c:forEach>	
										</tr>
									</table> 
								</td>
								<td width="20" height="58" background="/images/frame/top_bg_r.png"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>
