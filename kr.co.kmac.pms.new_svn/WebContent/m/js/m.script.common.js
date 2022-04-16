//##########################################################################################
// Index Page 초기화
//##########################################################################################
/*$(document).delegate("#indexPage", "pageinit", function(){
	//alert('#indexPage');
	$('#goEmail').bind('click', function(){
		goEmail();
	});
	getIndexPageInfo();
});*/
getIndexPageInfo();
//##########################################################################################
// 새메일 관련
//##########################################################################################
function goEmail(){
	$.getJSON("/action/ExpertPoolCheckAction.do?mode=getEmailPassword&ssn="+$("#username").val(), function(data){
		//alert("Data Loaded: " + data);
		webmailForm.user_account.value = $("#useremail").val();
    	webmailForm.pass.value = data.password;
    	webmailForm.cmd.value = "mail_link_2";
		webmailForm.action="https://webmail.kmac.co.kr/a_biz/m_login.nvd";
		webmailForm.target = "_blank";
		webmailForm.submit();
	});
}
function goEmail2(email, ssn){
	var email = email;
	var status = AjaxRequest.post (
			{	'url': "/action/ExpertPoolCheckAction.do",
				'parameters' : { "mode": "getEmailPassword", "ssn": ssn},
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
					}else{}
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
//##########################################################################################
// 새업무 관련
//##########################################################################################
function getIndexPageInfo(){
	$.getJSON("/action/ScheduleAction.do?mode=countMySchedule", function(data){
		//alert("내 스케줄 개수: " + data.scheduleCount);
		if(data.scheduleCount != "0"){
			$('#scheduleCount').text(data.scheduleCount);
		}else{
			$('#scheduleCount').hide();
		}
	});
	$.getJSON("/action/ProjectReportCabinetAction.do?mode=countMyProjectReport", function(data){
		//alert("내 지도일지 개수: " + data.preportCount);
		if(data.preportCount != "0"){
			$('#preportCount').text(data.preportCount);
		}else{
			$('#preportCount').hide();
		}
	});
	$.getJSON("/action/WorkCabinetAction.do?mode=countMyWorkList", function(data){
		//alert("새 업무 개수: " + data.workCount);
		if(data.workCount != "0"){
			$('#workCount').text(data.workCount);
		}else{
			$('#workCount').hide();
		}
	});
	$.getJSON("/action/MailCommonAction.do?mode=countMyEmail", function(data){
		//alert("새 이메일 개수: " + data.mailCount);
		if(data.mailCount != "0"){
			$('#mailCount').text(data.mailCount);
		}else{
			$('#mailCount').hide();
		}			
	});
}


//##########################################################################################
// 기타 공용 함수
//##########################################################################################\
//홈으로
function goHOME(){
	$.mobile.changePage( "/m/index.jsp", { transition: "pop"} );	
}


//로그인
function login(){
	if($("#j_username").val() == ''){alert('아이디를 입력하세요.');return(false);}
	if($("#j_password").val() == ''){alert('비밀번호를 입력하세요.');return(false);}
	
	document.loginform.submit();
	return(false); 
}
//파일 다운로드
function fileDownload(uuid, log) {
	if (uuid != "") {
		if ($("#_targetDownload").length == 0) {
			document.body.insertAdjacentHTML(
							'beforeEnd',
							"<form id='_targetDownload' name='_targetDownload' style='display:none'>"
									+ "	<input name='fileId' id='_targetDownloadId' value='"
									+ uuid + "'/>"
									+ "	<input name='isMobile' value='Y'/>"
									+ "</form>");
		} else {
			$('_targetDownloadId').value = uuid;
		}
		if (log == 'Y') {
			document._targetDownload.action = "/servlet/RepositoryDownLoadServlet";
		} else {
			document._targetDownload.action = "/servlet/PhotoDownLoadServlet";
		}
		document._targetDownload.target = "_blank";
		document._targetDownload.submit();
	}
}

//아이디 찾기
function search_id(){
	var url = "http://intranet.kmac.co.kr/kmac/pwmanage/idsearch.asp";
	window.open(url,'idsearch','fullscreen,scrollbars'); 
}

//비밀번호 찾기
function search_pass(){
	alert("비밀번호 찾기는 PC를 통해서만 가능합니다.");
	//var url = "http://intranet.kmac.co.kr/kmac/pwmanage/pwsearch.asp";
	//window.open(url,'pwsearch','fullscreen,scrollbars'); 
}

	

//##########################################################################################
//인증 관련 함수
//##########################################################################################\
//쿠키 생성 - name, value, expireDate
function setCookie( name, value, expiredays )
{
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";" ;
}


// 쿠키 가져오기
function getCookie(cName) {
     cName = cName + '=';
     var cookieData = document.cookie;
     var start = cookieData.indexOf(cName);
     var cValue = '';
     if(start != -1){
          start += cName.length;
          var end = cookieData.indexOf(';', start);
          if(end == -1)end = cookieData.length;
          cValue = cookieData.substring(start, end);
     }
     return unescape(cValue);
}

function getAuthToken(device){
	$.mobile.showPageLoadingMsg();
	$.getJSON(
		"/action/UserAuthenticationAction.do?mode=getAuthenticationToken", 
		{"phone1":$("#phone1").val(), "phone2":$("#phone2").val(), "phone3":$("#phone3").val(), "device": device}, 
		function(data, status) {
			if(data.resultMsg == "1"){
				$("#authTokenInput").show();
				$("#authToken").focus();
			} else if(data.resultMsg == "2"){
				alert("인증불가!\n등록된 휴대전화 번호가 아닙니다. ");
			} else if(data.resultMsg == "3"){
				alert("시스템 오류\n관리자에게 연락하기 바랍니다.");
			}
			$.mobile.hidePageLoadingMsg();
		}
	);	
}

function authRequest(device){ 
	$.mobile.showPageLoadingMsg();
	$.getJSON(
		"/action/UserAuthenticationAction.do?mode=authRequest", 
		{"authToken":$("#authToken").val(), "device": device}, 
		function(data, status) {
			$.mobile.hidePageLoadingMsg();
			if(data.resultMsg == "1"){
				alert("인증되었습니다.");
				
				if(device =="mobile"){
					setCookie("m_kmacPms_auth", data.authKey, 90);
					$.mobile.changePage( "/m/index.jsp", { transition: "pop"} );					
				} else {
					setCookie("d_kmacPms_auth", data.authKey, 90);
					location.href = "/forward.jsp";
				}
				//location.href = "/m/auth_c.jsp";
			} else if(data.resultMsg == "2"){
				alert("올바른 인증 번호가 아닙니다.");
				if(device =="mobile"){
					location.href = "/j_acegi_mobile_logout";
				} else {
					location.href = "/j_acegi_logout";
				}
			} else if(data.resultMsg == "3"){
				alert("시스템 오류\n관리자에게 연락하기 바랍니다.");
				if(device =="mobile"){
					location.href = "/j_acegi_mobile_logout";
				} else {
					location.href = "/j_acegi_logout";
				}
			}
	});		
}


function arrayInclude(arr, obj) {
    for(var i=0; i<arr.length; i++) {
        if (arr[i] == obj) 
        	return true;
    }
    return false;
}

// ##########################################################################################
// 페이징 관련
// ##########################################################################################\
function makePageHtml(nPageNo, nPageSize, nRecordCount, nListSize) {

	var nTotalEnd = Math.floor(nRecordCount / nListSize); // 맨 끝 페이지를 구한다.(총
	// 글수 / 화면에 보여질 페이지의
	// 수)

	// 마지막 페이지의 글 수가 초과하면 페이지수 1 추가
	if ((nRecordCount % nListSize) != 0) {
		++nTotalEnd;
	}

	//현재 페이지가 마지막 페이지보다 클때 전 페이지를 보여준다.
	if (nPageNo > nTotalEnd) {
		nPageNo -= 1;
	}

	var nStartPage = Math.floor(((nPageNo - 1) / nPageSize)) * nPageSize + 1;
	var nEndPageTmp = nStartPage + nPageSize - 1;
	var nEndPage = (nTotalEnd > nEndPageTmp) ? nEndPageTmp : nTotalEnd;

	var nPrevious = (nStartPage == 1) ? 0 : (nStartPage - 1);
	var nNext = (nTotalEnd > nEndPage) ? (nEndPage + 1) : 0;

	pageHtml = "";

	pageHtml += "	<div class='paging'>";
	pageHtml += "<ul>";
	/*pageHtml += "	<div class='pagination'> ";*/

	if (nRecordCount > 0) {

		if (nRecordCount != 0) {
			if (nPrevious != 0) {

				pageHtml += "<li><a href=\"javascript:goPage('"
						+ nPrevious
						+ "')\" class='icon'><i class='mdi mdi-page-first'></i></a></li>";

			} else {
				pageHtml += "<li><i class='mdi mdi-page-first'></i><li>";
			}
		}

		for ( var i = nStartPage; i <= nEndPage; i++) {
			if (i == nPageNo) {
				pageHtml += "<li><a href='#' class='current'>" + i + "</a></li>";
			} else {
				pageHtml += "<li><a href=\"javascript:goPage('" + i + "')\" >" + i	+ "</a></li>";
			}
		}

		if (nRecordCount != 0) {
			if (nNext != 0) {
				pageHtml += "<li><a href=\"javascript:goPage('"
						+ nNext
						+ "')\"  class='icon'><i class='mdi mdi-page-last'></i></a></li>";
			} else {
				pageHtml += "<li><i class='mdi mdi-page-last'></i></li>";
			}
		}
	}

	pageHtml += "</ul>";
	pageHtml += "</div>";
	/*pageHtml += "</div>";*/

	return pageHtml;

}


