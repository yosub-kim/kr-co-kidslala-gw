<%@page import="kr.co.kmac.pms.common.util.DateUtil"%>
<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>스케쥴 관리</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<script src="/m/js/moment.js"></script>	
<script type="text/javascript" src="<c:url value="/js/AjaxRequest.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/Common.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/prototype/prototype.js"/>"></script>
<script src="/js/jquery/js/jquery-1.10.2.js"></script>
<script src="/js/jquery/js/jquery-ui-1.10.4.custom.js"></script>
<link rel="stylesheet" href="/js/jquery/css/ui-lightness/jquery-ui-1.10.4.custom.css">
<script>
jQuery.noConflict();
</script>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
//var clientId = '538553006464.apps.googleusercontent.com';
//var scopes =  'https://www.googleapis.com/auth/calendar'; 
//var apiKey = 'AIzaSyD4TUyoZqljXZPBmc-TgScUdcGwJ5wdOxk';
var clientId = '288988552136-6a04df1qemkknlfhn76i7uhs6n9mm43d.apps.googleusercontent.com';
var scopes =  'https://www.googleapis.com/auth/calendar';
var apiKey = 'AIzaSyBbT5USVAQsHtJwvNAoM3b3Zh75KfiH9RQ';
var googleSyncIdArray = new Array();
var googleLoginConfig = {'client_id': clientId, 'scope': scopes};

function handleClientLoad() {
	gapi.client.setApiKey(apiKey);
	window.setTimeout(checkAuth,1);
}

function checkAuth() {
	gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, handleAuthResult);
}


function handleAuthResult(authResult) {
	//var authorizeButton = document.getElementById('googleLinkBtn');
	if (authResult && !authResult.error) {
		//authorizeButton.style.visibility = 'hidden';
		loadGoogleCal();
	} else {
		//alert("구글 로그인 필요");
		//authorizeButton.style.visibility = '';
		//authorizeButton.onclick = handleAuthClick;
	}
}
/* 
function googleAuth() {
	gapi.auth.authorize(googleLoginConfig, function() {
		//console.log('login complete');
		//console.log(gapi.auth.getToken());
		loadGoogleCal();
		alert("입력하신 Google계정과 동기화 되었습니다.");
	});
}
 */
 /* 
function loadGoogleCal() {
	if(gapi.auth.getToken() != null){
		gapi.client.load('calendar', 'v3', function() {
			var request = gapi.client.calendar.events.list({
				"calendarId": "primary",
				"showDeleted": false,
				"timeMin": "<c:out value="${sdate}"/>T00:00:00.000+09:00",
				"timeMax": "<c:out value="${sdate}"/>T23:59:00.000+09:00"
		}); 
			request.execute(function(resp) {
				if(resp.items != undefined){
					for (var i = 0; i < resp.items.length; i++) {
						var startDayStr;if(resp.items[i].start.date==undefined){startDayStr=resp.items[i].start.dateTime;}else{startDayStr=resp.items[i].start.date;}
						var startDayObj;if(startDayStr.length>10){startDayObj=moment(startDayStr,"YYYY-MM-DDTHH:mm:ssZ").toDate();}else{startDayObj=moment(startDayStr,"YYYY-MM-DD").toDate();};
						var summary=resp.items[i].summary==undefined?"":resp.items[i].summary;
						var location=resp.items[i].location==undefined?"":resp.items[i].location;
						var tableObj = $('scheduleDetailTable');
					    var newRow=tableObj.insertRow(-1);
						var newCellArr = new Array();
						for ( var j=0; j<(tableObj.down('tr').down('td').nextSiblings().length + 1); j++ ){
							newCellArr[j] = newRow.insertCell(-1);
						}
						newCellArr[0].innerHTML = moment(startDayObj).format("YYYY-MM-DD"); 
						newCellArr[1].innerHTML = "googleCal";
						newCellArr[2].innerHTML = summary;
						newCellArr[3].innerHTML = location;
						newCellArr[4].innerHTML = "";
						newCellArr[5].innerHTML = moment(resp.items[i].start.dateTime,"YYYY-MM-DDTHH:mm:ssZ").format("HH시 mm분")+"~"+moment(resp.items[i].end.dateTime,"YYYY-MM-DDTHH:mm:ssZ").format("HH시 mm분");
						newCellArr[6].innerHTML = "";
						newCellArr[0].align = "center";
						newCellArr[1].align = "center";
						newCellArr[5].align = "center";
					}
				}
			});
		});
	}
}
 */
function btnClear_onclick(){
	document.frm.reset();
}

function customerInfo(){
	var sFrm = document.frm;
	if(sFrm.customerName.value == ""){
		alert("관련회사를 입력 후 검색하세요.");
		sFrm.customerName.focus();
		return false;
	}
	
	var customerInfo;
	var sURL = "";
	var sFeatures = "width=780, height=450, top=100, left=100,scrollbars=yes";

	customerInfo = window.open(sURL, "customerInfo", sFeatures);
	sFrm.target = "customerInfo";
	sFrm.action = "http://intranet.kmac.co.kr/kmac/comlist/pop_customerListForSchedule.asp";
	document.charset='euc-kr';
	sFrm.submit();  
	customerInfo.focus();
	document.charset='utf-8';
}

function multiYN_onclick(chkObj){
	if(chkObj.checked){
		frm.edate.disabled = false;
		frm.inWeekYN.disabled = false;
		document.getElementById("divMulti").style.display = "";
		document.getElementById("")
		// 종료일에 시작일 다음 날짜를 자동입력(초기값)
		sdate = document.frm.sdate.value;
		document.frm.edate.value = dateAddDel(sdate, 1, 'd');
	} else {
		frm.edate.disabled = true;
		frm.inWeekYN.disabled = true;
		document.getElementById("divMulti").style.display = "none";
		document.frm.edate.value = "";
	}
	
	
}

function dateAddDel(sDate, nNum, type) {
    var yy = parseInt(sDate.substr(0, 4), 10);
    var mm = parseInt(sDate.substr(5, 2), 10);
    var dd = parseInt(sDate.substr(8), 10);
    
    if (type == "d") {
        d = new Date(yy, mm - 1, dd + nNum);
    }
    else if (type == "m") {
        d = new Date(yy, mm - 1 + nNum, dd);
    }
    else if (type == "y") {
        d = new Date(yy + nNum, mm - 1, dd);
    }
 
    yy = d.getFullYear();
    mm = d.getMonth() + 1; mm = (mm < 10) ? '0' + mm : mm;
    dd = d.getDate(); dd = (dd < 10) ? '0' + dd : dd;
 
    return '' + yy + '-' +  mm  + '-' + dd;
}

function setRelatedCompanyInfo(val) {
	var sFrm = document.frm;
	// 내근 버튼을 최초로 누른 경우 관련사 입력란에 'KMAC'를 자동으로 넣어준다. 
	if (sFrm.workType[1].checked && sFrm.customerName.value == "" ){
		sFrm.customerName.value = "KMAC";
		sFrm.place.value = "KMAC";
		document.getElementById('relationUser_1').checked = false;
		document.getElementById('relationUser_2').checked = false;
		document.getElementById('googleSyncId_1').checked = false;
		document.getElementById('googleSyncId_2').checked = false;
		document.getElementById('relationUser_1').disabled = true;
		document.getElementById('relationUser_2').disabled = true;
		/* document.getElementById('googleSyncId_1').disabled = true;
		document.getElementById('googleSyncId_2').disabled = true; */
	}
	// 외근 버튼을 누를 시 관련사에 KMAC 라는 글씨가 있는 경우 삭제한다.
	if (sFrm.workType[0].checked && sFrm.customerName.value == "KMAC" ){
		sFrm.customerName.value = "";
		sFrm.place.value = "";
		document.getElementById('relationUser_1').disabled = false;
		document.getElementById('relationUser_2').disabled = false;
		document.getElementById('googleSyncId_1').disabled = false;
		document.getElementById('googleSyncId_2').disabled = false;
	}
}

function setWorkType() {
	var scheduleTypeValue = document.all.type.options[document.all.type.selectedIndex].value;
	
	if (scheduleTypeValue == "교육참석") {
		alert("'교육참석'은\n조직주도교육 및 개인주도학습(집합교육) 등에 참여하는 경우\n에만 해당합니다.\n교육과정 오픈 시에는 '사업관리'를 선택하시기 바랍니다.");		
	}
	
	/* <c:if test="${isManager != true }">
	if (scheduleTypeValue == "Up-day") {
		alert("2019년 'Up-day' 신청이 마감되었습니다.\n문의 : 경영기획1센터(634)");
		document.location.reload();		
	}
	</c:if> */
	
	if (scheduleTypeValue == "휴가" || scheduleTypeValue == "개인휴무" || scheduleTypeValue == "교육참석" || scheduleTypeValue == "Up-day") {
		document.all.divMulti2.style.display = "none";
		document.all.divMulti3.style.display = "none";
		document.all.divMulti4.style.display = "none";
		document.all.divMulti5.style.display = "none";
		document.all.divMulti8.style.display = "none";
		document.all.divMulti7.style.display = "none";
		document.all.divMulti9.style.display = "none";
		document.all.divMulti10.style.display = "none";
		
		
		for (var i = 0; i < document.frm.startHour.length; i++) {
			document.frm.startHour.options[i].selected = false;
			document.frm.endHour.options[i].selected = false;
		}
		for (var i = 0; i < document.frm.startMin.length; i++) {
			document.frm.startMin.options[i].selected = false;
			document.frm.endMin.options[i].selected = false;
		}
	} else {
		document.all.divMulti2.style.display = "";		
		document.all.divMulti3.style.display = "";
		document.all.divMulti4.style.display = "";
		document.all.divMulti5.style.display = "";
		document.all.divMulti8.style.display = "";
		document.all.divMulti7.style.display = "";
		document.all.divMulti9.style.display = "";
		document.all.divMulti10.style.display = "";
	}
}

function setWorkType_t() {

		document.all.divMulti8.style.display = "";
		document.all.divMulti7.style.display = "";
}


function setDefaultEndHour() {
	document.forms[0].endHour.options[document.all.startHour.selectedIndex+1].selected = true;
}
 

function btnSave_onclick() {
	var sFrm = document.frm;
	var scheduleType = document.all.type.options[document.all.type.selectedIndex].value;
	var workTypeFlag = false;
	
	for(var i=0; i<sFrm.workType.length; i++){
		if(sFrm.workType[i].checked){
			workTypeFlag = true;
		}
	}
	
	if(sFrm.sdate.value == "") {
		alert("시작일을 입력하세요.");sFrm.sdate.focus();return false;
	}
	
	var chkbox = document.getElementsByName('relationUser');
	var chk = false;
	var chk_result="0";
	
	for(var i=0; i<chkbox.length; i++){
		if(chkbox[i].checked){
			chk = true;
		}else{
			chk = false;
			break;
		}
	}
	
	if(scheduleType != "휴가" && scheduleType != "개인휴무" && scheduleType != "교육참석" && scheduleType != "Up-day" && scheduleType != "전사행사"){
			if (sFrm.workType[1].checked){
				chk_result="0";
			}else{
				if(chk){
					chk_result="3";
				}else{
					if(chkbox[0].checked){
						chk_result="1";
					}else if(chkbox[1].checked){
						chk_result="2";
					}
					else{
					}
				}
			}
	}
	document.frm.relationUser_val.value = chk_result; 
	
	
	if(scheduleType != "휴가" && scheduleType != "개인휴무" && scheduleType != "교육참석" && scheduleType != "Up-day") {		
		if(sFrm.startHour.value == "" || sFrm.startMin.value == "") {
			alert("시작시간을 입력하세요."); return false;
		}
		if(sFrm.endHour.value == "" || sFrm.endMin.value == "" ) {
			alert("종료시간을 입력하세요."); return false;
		}
		
		if(sFrm.startHour.value + sFrm.startMin.value >=  sFrm.endHour.value +  sFrm.endMin.value) {
			alert("시작시간이 종료시간 이후가 될 수 없습니다.");return false;			
		}
	}
	
	if((scheduleType != "휴가" && scheduleType != "개인휴무" && scheduleType != "교육참석" && scheduleType != "Up-day" && scheduleType != "전사행사" && scheduleType != "기타") && !workTypeFlag){
		alert("외/내근 유형을 선택하세요.");sFrm.workType[0].focus();return false;
	}
	if((sFrm.multiYN.checked)&&(sFrm.sdate.value >= sFrm.edate.value)){
		alert("종료일자는 기준일자 이후이어야 합니다.");return false;
	}
	if(sFrm.content.value == ""){
		alert("업무내용을 입력하세요.");sFrm.content.focus();return false;
	}
	if((scheduleType != "휴가" && scheduleType != "개인휴무" && scheduleType != "교육참석" && scheduleType != "Up-day" && scheduleType != "전사행사" && scheduleType != "기타") && sFrm.customerName.value == ""){
		alert("관련회사를 입력하세요.");sFrm.customerName.focus();return false;
	}
	if((scheduleType != "휴가" && scheduleType != "개인휴무" && scheduleType != "교육참석" && scheduleType != "Up-day" && scheduleType != "전사행사" && scheduleType != "기타") && sFrm.place.value == ""){
		if(sFrm.place.value == ""){
			alert("장소를 입력하세요.");sFrm.place.focus();return false;
		}
	}
	if (sFrm.workType[0].checked){
		if(!sFrm.googleSyncId[0].checked && !sFrm.googleSyncId[1].checked){
			alert("외근 시 고객정보 등록 유/무를 반드시 입력해야 합니다.");
			return false;
		}else{
			
		}
	}else{
		
	}
	
    // 이전 근무시간 등록 및 수정 불가능
	
	var flag=false;

	var ActionURL = "/action/ScheduleAction.do?mode=saveSchedule";
		
	//if($('googleChk').checked){
	//	gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, createScheduleWithGoogle);
	//}else{
		AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					opener.document.location.reload();
					location.href = "/action/ScheduleAction.do?mode=scheduleForm&ssn=<c:out value="${ssn}"/>&sdate=<c:out value="${sdate}"/>&seq=";
					
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){alert("저장할 수 없습니다.");}
		});	
	//}
}

function dateToYYYYMMDD(date){
    function pad(num) {
        num = num + '';
        return num.length < 2 ? '0' + num : num;
    }
    return date.getFullYear() + '-' + pad(date.getMonth()+1) + '-' + pad(date.getDate());
}

function home_schedule(ssn){
	var url = "/action/BoardAction.do?mode=selectList_home&bbsId=home&writerId="+ssn;
	var sFeatures = "top=50,left=50,width=800,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_schedule", sFeatures);
	detailWin.focus();
}

/* 
function createScheduleWithGoogle(authResult) {
	if (authResult && !authResult.error) {
		var sendStr = '{	"start"		: {"dateTime": "'+$('sdate').value +'T'+ $('startHour').value+":"+$('startMin').value+':00.000+09:00'+'"},'
					+ '		"end"		: {"dateTime": "'+$('sdate').value +'T'+ $('endHour').value+":"+$('endMin').value+':00.000+09:00'+'"},'
					+ '		"summary"	: "'+$('content').value+'",'
					+ '		"location"	: "'+$('place').value+'"}';
		gapi.client.load('calendar', 'v3', function() {
			var request = gapi.client.calendar.events.insert({
	    		"calendarId": "primary",
	    		"resource": sendStr.evalJSON()
	    	});
		  	request.execute(function(resp) {
				var sFrm = document.frm;
				//console.log(resp);
				if (resp.id){
					var ActionURL = "/action/ScheduleAction.do?mode=saveSchedule&googleSyncId="+resp.id;
					AjaxRequest.submit(
						sFrm
						,{ 'url':ActionURL
							,'onSuccess':function(obj){
								var res = eval('(' + obj.responseText + ')');
								opener.document.location.reload();
								location.href = "/action/ScheduleAction.do?mode=scheduleForm&ssn=<c:out value="${ssn}"/>&sdate=<c:out value="${sdate}"/>&seq=";
							}
							,'onLoading':function(obj){}
							,'onError':function(obj){alert("저장할 수 없습니다.");}
					});	
				}else{
					alert("An error occurred during google Sync. Please try again later.");
				}
			});
		});
	} else {
		alert("Google 계정에 로그인 중입니다. 로그인 완료 후 등록 됩니다.");
		gapi.auth.authorize(googleLoginConfig, function() {
			//console.log('login complete');
			//console.log(gapi.auth.getToken());
			gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, createScheduleWithGoogle);
		});			
		return false;
	}	
}
 */
function deletProjectSchedule(projectCode,pYear,pMonth,pDay,pSsn) {
	if(confirm("선택한 스케쥴을 삭제 하시겠습니까?")){
		var ActionURL = "/action/ProjectReportInfoAction.do?mode=deleteProjectReportInfo";	
		ActionURL += "&projectCode=" + projectCode;
		ActionURL += "&year=" + pYear;
		ActionURL += "&month=" + pMonth;
		ActionURL += "&day=" + pDay;
		ActionURL += "&ssn=" + pSsn;
			
		AjaxRequest.get({ 
			'url':ActionURL
			,'onSuccess':function(obj){
				var res = eval('(' + obj.responseText + ')');
				//alert(res.resultMsg);
				opener.document.location.reload();
				location.href = "/action/ScheduleAction.do?mode=scheduleForm&ssn=<c:out value="${ssn}"/>&sdate=<c:out value="${sdate}"/>&seq=";
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){alert("저장할 수 없습니다.");}
		});
	}
}

var googleSyncId;
var delActionUrl;
function deleteSchedule(sdate,seq,ssn, _googleSyncId) {
	if(confirm("선택한 스케쥴을 삭제 하시겠습니까?")){
		delActionUrl = "/action/ScheduleAction.do?mode=removeSchedule&sdate=" + sdate + "&seq=" + seq + "&ssn=" + ssn;
		
		
			AjaxRequest.get({
				'url':delActionUrl
				,'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					//alert(res.resultMsg);
					opener.document.location.reload();
					location.href = "/action/ScheduleAction.do?mode=scheduleForm&ssn=<c:out value="${ssn}"/>&sdate=<c:out value="${sdate}"/>&seq=";
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){alert("저장할 수 없습니다.");}
			});	

	}
}
/* 
function deleteScheduleWithGoogle(authResult) {
	if (authResult && !authResult.error) {
		if( googleSyncId == ""){alert("not found google sync id;");return false;}
		gapi.client.load('calendar', 'v3', function() {
			var r = gapi.client.calendar.events.delete({"calendarId": "primary", "eventId": googleSyncId});
			r.execute(function(resp) {
				AjaxRequest.get({
					'url':delActionUrl
					,'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						//alert(res.resultMsg);
						opener.document.location.reload();
						location.href = "/action/ScheduleAction.do?mode=scheduleForm&ssn=<c:out value="${ssn}"/>&sdate=<c:out value="${sdate}"/>&seq=";
					}
					,'onLoading':function(obj){}
					,'onError':function(obj){alert("저장할 수 없습니다.");}
				}); 
			});
		});			
	} else {
		alert("Google계정에 로그인 중입니다. 로그인 완료 후 등록 됩니다.");
		gapi.auth.authorize(googleLoginConfig, function() {
			//console.log('login complete');
			//console.log(gapi.auth.getToken());
			gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, deleteScheduleWithGoogle);
		});			
		return false;
	}	
} */

function openCustomerInfo(customerInfoIdx){
	var url = "http://intranet.kmac.co.kr/kmac/comlist/customer_detail.asp?idx="+customerInfoIdx;
	window.open(url, 'CustomerInfo',
			'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=795,height=560,directories=no,menubar=no');
}
function openProjectBoardInfo(bbsId, seq, projectName){
	//var url = "http://intranet.kmac.co.kr/kmac/comlist/customer_detail.asp?idx="+customerInfoIdx;
	var url="/action/BoardAction.do?mode=boardView&bbsId=" + bbsId + "&seq=" + seq + "&projectCode=" + bbsId + "&projectName=" + projectName;
	window.open(url, 'ProjectBoardInfo',
			'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=795,height=560,directories=no,menubar=no');
	
}
function check_box(){
	var flag = false;
	var values = document.getElementsByName("relationUser");
	
	for(var i=0; i<values.length; i++){
		if(values[i].checked){
			alert(values[i].value);
			count++;
		}
	}
	if(count = 2){
		alert("Gg");
	}
}
/* RA 활동보고서  */
/* function rareportDetail(ssn){
	var url = "/action/BoardAction.do?mode=selectList_RAReport&bbsId=RAReport&writerId="+ssn;
	var sFeatures = "top=50,left=50,width=800,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_schedule", sFeatures);
	window.close();
	detailWin.focus();
} */
</script>
<script src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>
<script type="text/javascript">

</script>
<style>
input:-ms-input-placeholder{
	color: gray;
}
input::-webkit-input-placeholder{
	color: gray;
}
input::placeholder{
	color: gray;
}
</style>
</head>
<body style="width: 1000px">
<%-- 작업영역 --%>
<table width="1000px" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="70%"><h4 class="mainTitle">스케줄 관리<c:if test="${scheduleUserName != '' }">(<c:out value="${scheduleUserName}" />)</c:if></h4></td>
					<td width="30%">
						<div class="btNbox txtR">
							<!-- <a class="btNaaa txt4btn" href="javascript:googleAuth();">Google 동기화</a>  -->
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
			 
	<tr>
		<td align="center">
			<table cellpadding="0" cellspacing="0" width="100%" border="0">
				<form name="frm" method="post">
				<div style="display: none;">
					<input type="hidden" name="ssn"   value="<c:out value="${ssn}"/>">
					<input type="hidden" name="isManager"   value="<c:out value="${isManager}"/>">
					<input type="hidden" name="odate" value="<c:out value="${sdate}"/>">
					<input type="hidden" name="saveMode" value="<c:out value="${saveMode}"/>">
					<input type="hidden" name="seq"   value="<c:out value="${scheduleInfo.seq}"/>">
				</div>
				<tbody>
					<tr>
						<td class="detailTableTitle_center" width="15%">일자</td>
						<td class="detailTableField_left" width="35%">
							<script>
								jQuery(function(){jQuery( "#sdate" ).datepicker({});});
							</script>
							<input type="text" name="sdate" id="sdate" size="14" value="<c:out value="${sdate}"/>">
						</td>
						<td class="detailTableTitle_center" width="15%">
							<input type="checkbox" name="multiYN" value="Y" <c:if test="${saveMode=='UPDATE'}">disabled</c:if> onclick="multiYN_onclick(this);">
							연속입력
						</td>
						<td class="detailTableField_left" width="35%">
							<script>
								jQuery(function(){jQuery( "#edate" ).datepicker({});});
							</script>
						<div id="divMulti" style="display:none;">
							<input type="text" name="edate" id="edate" size="12" value="" disabled>
							<input type="checkbox" name="inWeekYN" value="Y" checked disabled>주말,공휴일 제외 
						</div>
						</td>
					</tr>
					<tr>
						<td class="detailTableTitle_center" class="textInput_noLine_center">업무유형</td>
						<td class="detailTableField_left">
						<%if(session.getAttribute("dept").equals("9260") || session.getAttribute("dept").equals("9261")){%>
							<select name="type" style="width: 100%;" class="selectboxPopup" onchange="setWorkType();">
								<option value="사업관리" <c:if test="${'사업관리'==scheduleInfo.type}">selected</c:if>>사업관리</option>
								<option value="전사행사" <c:if test="${'전사행사'==scheduleInfo.type}">selected</c:if>>전사행사</option>
								<option value="교육참석" <c:if test="${'교육참석'==scheduleInfo.type}">selected</c:if>>교육참석</option>
								<option value="회의일정" <c:if test="${'회의일정'==scheduleInfo.type}">selected</c:if>>회의일정</option>
								<option value="Up-day" <c:if test="${'Up-day'==scheduleInfo.type}">selected</c:if>>재택근무</option>
								<option value="개인휴무" <c:if test="${'개인휴무'==scheduleInfo.type}">selected</c:if>>개인휴무</option>
								<option value="휴가" <c:if test="${'휴가'==scheduleInfo.type}">selected</c:if>>휴가</option>
								<option value="기타" <c:if test="${'기타'==scheduleInfo.type}">selected</c:if>>기타</option>
							</select>	
						<%}else if( session.getAttribute("jobClass").equals("A") || session.getAttribute("jobClass").equals("B")) {%>
							<select name="type" style="width: 100%;" class="selectboxPopup" onchange="setWorkType();">
								<option value="사업관리" <c:if test="${'사업관리'==scheduleInfo.type}">selected</c:if>>사업관리</option>
								<option value="교육참석" <c:if test="${'교육참석'==scheduleInfo.type}">selected</c:if>>교육참석</option>
								<option value="회의일정" <c:if test="${'회의일정'==scheduleInfo.type}">selected</c:if>>회의일정</option>
								<option value="Up-day" <c:if test="${'Up-day'==scheduleInfo.type}">selected</c:if>>재택근무</option>
								<option value="휴가" <c:if test="${'휴가'==scheduleInfo.type}">selected</c:if>>휴가</option>
								<option value="기타" <c:if test="${'기타'==scheduleInfo.type}">selected</c:if>>기타</option>
							</select>	
						<%}else if(session.getAttribute("jobClass").equals("J")){%>
							<select name="type" style="width: 100%;" class="selectboxPopup" onchange="setWorkType();">
								<option value="교육참석" <c:if test="${'교육참석'==scheduleInfo.type}">selected</c:if>>교육참석</option>
								<option value="회의일정" <c:if test="${'회의일정'==scheduleInfo.type}">selected</c:if>>회의일정</option>
								<option value="개인휴무" <c:if test="${'개인휴무'==scheduleInfo.type}">selected</c:if>>개인휴무</option>
								<option value="기타" <c:if test="${'기타'==scheduleInfo.type}">selected</c:if>>기타</option>
							</select>
						<%}else if(session.getAttribute("jobClass").equals("H") || session.getAttribute("jobClass").equals("N")){%>
							<select name="type" style="width: 100%;" class="selectboxPopup" onchange="setWorkType();">
								<option value="사업관리" <c:if test="${'사업관리'==scheduleInfo.type}">selected</c:if>>사업관리</option>
								<option value="교육참석" <c:if test="${'교육참석'==scheduleInfo.type}">selected</c:if>>교육참석</option>
								<option value="회의일정" <c:if test="${'회의일정'==scheduleInfo.type}">selected</c:if>>회의일정</option>
								<option value="Up-day" <c:if test="${'Up-day'==scheduleInfo.type}">selected</c:if>>재택근무</option>
								<option value="휴가" <c:if test="${'휴가'==scheduleInfo.type}">selected</c:if>>휴가</option>
								<option value="기타" <c:if test="${'기타'==scheduleInfo.type}">selected</c:if>>기타</option>
							</select>	
						<%}else if(session.getAttribute("jobClass").equals("C")){%>
							<select name="type" style="width: 100%;" class="selectboxPopup" onchange="setWorkType();">
								<option value="회의일정" <c:if test="${'회의일정'==scheduleInfo.type}">selected</c:if>>회의일정</option>
								<option value="기타" <c:if test="${'기타'==scheduleInfo.type}">selected</c:if>>기타</option>
							</select>	
						<%}%>
						</td>
						<td class="detailTableTitle_center">비공개 여부</td>
						<td class="detailTableField_left">
							<input type="checkbox" name="secretYN" value="Y" <c:if test="${scheduleInfo.secretYN=='Y'}">checked</c:if>>
							비공개로 설정시 체크해 주세요.
						</td>
					</tr>
					<tr>
						<td class="detailTableTitle_center"><span id="divMulti4">외/내근 유형(*)</span></td>
						<td class="detailTableField_left">
							<div id="divMulti2">
							<input type="radio" name="workType" value="E" <c:if test="${scheduleInfo.workType=='E'}">checked</c:if> onclick="setRelatedCompanyInfo(this)">외근
		                	<input type="radio" name="workType" value="I" <c:if test="${scheduleInfo.workType=='I'}">checked</c:if> onclick="setRelatedCompanyInfo(this)">내근
							</div>
						</td>
						<td class="detailTableTitle_center"><span id="divMulti7">현장 출/퇴근</span></td>
						<td class="detailTableField_left">
						<div id="divMulti8">
							<input type="checkbox" name="relationUser" id="relationUser_1" value="" <c:if test="${scheduleInfo.relationUser == '1' || scheduleInfo.relationUser == '3'}">checked</c:if>>현장 "출근" &nbsp
		                	<input type="checkbox" name="relationUser" id="relationUser_2" value="" <c:if test="${scheduleInfo.relationUser == '2' || scheduleInfo.relationUser == '3'}">checked</c:if>>현장 "퇴근"
		                	<input type="hidden" name="relationUser_val"> 
		                </div>
						</td>
					</tr>
					<tr>
						<td class="detailTableTitle_center"><span id="divMulti9">고객정보 등록 대상</span></td>
							<td class="detailTableField_left">
							<div id="divMulti10">
							<input type="radio" name="googleSyncId" id="googleSyncId_1" value="Y" <c:if test="${scheduleInfo.googleSyncId=='Y'}">checked</c:if> onclick="setRelatedCompanyInfo(this)">YES
		                	<input type="radio" name="googleSyncId" id="googleSyncId_2" value="N" <c:if test="${scheduleInfo.googleSyncId=='N'}">checked</c:if> onclick="setRelatedCompanyInfo(this)">NO
							</div>
						</td>
						<td class="detailTableTitle_center">시간<span id="divMulti5">(*)</span></td>
						<td class="detailTableField_left">
							<select name="startHour" id="startHour" class="selectboxPopup" style="width: 50px;" onchange="setDefaultEndHour();">
								<option value=""></option>
								<c:forEach var="hour" items="${hourList}">
								<option value="<c:out value="${hour}"/>"<c:if test="${hour==scheduleInfo.startHour}"> selected</c:if>><c:out value="${hour}"/></option>
								</c:forEach>
							</select>시
							<select name="startMin" id="startMin" class="selectboxPopup" style="width: 50px;">
								<option value=""></option>
								<c:forEach var="minute" items="${minList}">
								<option value="<c:out value="${minute}"/>"<c:if test="${minute==scheduleInfo.startMin}"> selected</c:if>><c:out value="${minute}"/></option>
								</c:forEach>
							</select>분~
							<select name="endHour" id="endHour" class="selectboxPopup" style="width: 50px;">
								<option value=""></option>
								<c:forEach var="hour" items="${hourList}">
								<option value="<c:out value="${hour}"/>"<c:if test="${hour==scheduleInfo.endHour}"> selected</c:if>><c:out value="${hour}"/></option>
								</c:forEach>
							</select>시
							<select name="endMin" id="endMin" class="selectboxPopup" style="width: 50px;">
								<option value=""></option>
								<c:forEach var="minute" items="${minList}">
								<option value="<c:out value="${minute}"/>"<c:if test="${minute==scheduleInfo.endMin}"> selected</c:if>><c:out value="${minute}"/></option>
								</c:forEach>
							</select>분
						</td>
					</tr>
					<tr>
					<td class="detailTableTitle_center">고객사<span id="divMulti3">(*)</span></td>
						<td class="detailTableField_left">
						<%if(session.getAttribute("jobClass").equals("A") || session.getAttribute("jobClass").equals("J") || session.getAttribute("jobClass").equals("B")) {%>
							<input class="contentInput_left" type="text" name="customerName" value="<c:out value="${scheduleInfo.customerName}"/>" class="textInput_left"  style="width: 100%;">
							<!-- <a title="고객이력검색" class="btNa0a0a0" style="padding: 4px 5px 4px 5px" href="#" onclick="customerInfo();">고객이력검색</a> -->
						<%}else{%>
							<input class="contentInput_left" type="text" name="customerName" value="<c:out value="${scheduleInfo.customerName}"/>" style="width: 100%;">
						<%}%>
						</td>
						<td class="detailTableTitle_center">장소<span id="divMulti6">(*)</td>
						<td class="detailTableField_left">
							<input class="contentInput_left" placeholder="직접입력(예시: 서울 종로, 전남 광주)" type="text" name="place" id="place" value="<c:out value="${scheduleInfo.place}"/>" class="textInput_left"  style="width: 100%;">
						</td> 
					</tr>
					<tr>
						<td class="detailTableTitle_center">업무내용(*)</td>
						<td class="detailTableField_left" colspan="3">
							<input type="text" class="contentInput_left" name="content" id="content" value="<c:out value="${scheduleInfo.content}"/>" class="textInput_left"  style="width: 100%;" >
						</td>
					</tr>
				</tbody>
				</form>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1"></td>
	</tr>		
	<tr>
		<td>
			<table cellSpacing="0" width="100%" border="0" class='listTable' >
				<tr>
					<td style="text-align:left;padding-left: 5px; padding-top: 5px;">(*)는 필수입력, 고객사는 방문회사 입력, 회사 내부 관련 스케쥴은 'KMAC'로 입력,
					 <br/><!-- <input type="checkbox" name="googleChk" id="googleChk"/> <span style="font-weight: bold;">Google 캘린더에도 동일하게 저장하기</span>  -->
					 '장소/현장출근/퇴근/고객정보등록 여부'는 외근시만 필수. <b>오늘 이전 일자의 스케줄을 입력할 수 없습니다.</b>
					</td>
					<td>
						<div class="btNbox txtR">
							<a class="btN006bc6 txt2btn" href="?mode=scheduleForm&ssn=<c:out value="${ssn}"/>&sdate=<c:out value="${sdate}"/>">새일정</a>
							<a class="btNe006bc6 txt2btn" href="#" onclick="btnSave_onclick();">저장</a>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>

	<tr>
		<td>
		
		</td>
	</tr>

	<tr>
		<td height="6"></td>
	</tr>				
	<tr>
		<td align="center">
			<div style="overflow: auto;">
				<table class="tableStyle05" id="scheduleDetailTable">
					<thead>
						<tr>
							<td style="width:10%">일자</td>
							<td style="width:10%">업무유형</td>
							<td style="width:18%">업무내용</td>
							<td style="width:8%">고객사</td>
							<td style="width:8%">장소</td>
							<td style="width:8%">내/외근</td>
							<td style="width:10%">현장 <br>출/퇴근</td>
							<td style="width:8%">고객정보</br> 등록계획</td>
							<td style="width:15%">시간</td>
							<td style="width:5%">삭제</td>
							<c:forEach var="schedule" items="${scheduleList}">
							<c:choose><c:when test="${schedule.type ==  'Up-day'}">								
								<%if(session.getAttribute("companyPosition").equals("01HC") || session.getAttribute("companyPosition").equals("05CC") || session.getAttribute("companyPosition").equals("06CB") ||
										session.getAttribute("companyPosition").equals("07CC") || session.getAttribute("companyPosition").equals("08CF") || session.getAttribute("companyPosition").equals("09CJ") || 
										session.getAttribute("userId").equals("5815choi") || session.getAttribute("dept").equals("9261")){%>
									<td style="width:7%">상세확인</td>
								<%} else {%>
									<c:if test="${cSsn }" >
										<td style="width:7%">상세확인</td>
									</c:if>								
								 <%} %>
							</c:when><c:otherwise></c:otherwise></c:choose>
							</c:forEach>
						</tr>				
					</thead> 
					<form name="frmLst" method="post">
						<input type="hidden" name="ssn" value="<c:out value="${schedule.ssn}"/>">
						<input type="hidden" name="sdate" value="<c:out value="${sdate}"/>">
					<tbody>
						<c:forEach var="project" items="${projectList}">
						<tr>
							<td><c:out value="${project.year}"/>-<c:out value="${project.month}"/>-<c:out value="${project.day}"/></td>
							<td style="font-weight: bold !Important; color: #48BBD6">프로젝트</td>
							<td title="<c:out value="${project.projectName}"/>"><c:out value="${project.projectName}"/></td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td><c:if test="${cSsn }" ><a href="#" onclick="deletProjectSchedule('<c:out value="${project.projectCode}"/>','<c:out value="${project.year}"/>','<c:out value="${project.month}"/>','<c:out value="${project.day}"/>','<c:out value="${ssn}"/>');"><img alt="삭제" border="0" src="/images/delete.jpg"></a></c:if></td>
						</tr>
						</c:forEach>
						<c:forEach var="projectManpower" items="${projectManpowerList}">
						<tr>
							<td><c:out value="${projectManpower.year}"/>-<c:out value="${projectManpower.month}"/>-<c:out value="${projectManpower.day}"/></td>
							<td style="font-weight: bold !Important; color: #48BBD6">프로젝트</td>
							<td title="<c:out value="${projectManpower.projectName}"/>"><c:out value="${projectManpower.projectName}"/></td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
						</tr>
						</c:forEach>
						<c:forEach var="projectBoard" items="${projectBoardList}">
						<tr>
							<td><c:out value="${projectBoard.year}"/>-<c:out value="${projectBoard.month}"/>-<c:out value="${projectBoard.day}"/></td>
							<td style="font-weight: bold !Important; color: #48BBD6">PJT게시판</td>
							<td title="<c:out value="${projectBoard.projectName}"/>"><a href="javascript: openProjectBoardInfo(<c:out value="${projectBoard.projectCode}"/>, <c:out value="${projectBoard.time}"/>, '<c:out value="${projectBoard.relationUser}"/>')"><c:out value="${projectBoard.projectName}"/></a></td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
						</tr>
						</c:forEach>
						<c:forEach var="schedule" items="${scheduleList}">
						<tr>
							<td><c:out value="${schedule.year}"/>-<c:out value="${schedule.month}"/>-<c:out value="${schedule.day}"/></td>
							<td <c:choose>
									<c:when test="${schedule.workType == 'E'}">style="font-weight: bold !Important; color: #FCDA54"</c:when>
									<c:when test="${schedule.workType == 'I'}">style="font-weight: bold !Important; color: #ADD14B"</c:when>
									<c:when test="${schedule.type == '휴가'}" >style="font-weight: bold !Important; color: #EC3027"</c:when>
									<c:when test="${schedule.type == '개인휴무'}" >style="font-weight: bold !Important; color: #2cb400"</c:when>
									<c:when test="${schedule.type == '교육참석'}" >style="font-weight: bold !Important; color: #888888"</c:when>
									<c:when test="${schedule.type == 'Up-day'}" >style="font-weight: bold !Important; color: #2cb400"</c:when>
								</c:choose>
							>
							<c:choose><c:when test="${schedule.type ==  'Up-day'}"> 재택근무</c:when><c:otherwise><c:out value="${schedule.type}"/></c:otherwise></c:choose>
							</td>
							<td title="<c:out value="${schedule.content}"/>">
								<a href="?mode=scheduleForm&ssn=<c:out value="${schedule.ssn}"/>&sdate=<c:out value="${sdate}"/>&seq=<c:out value="${schedule.seq}"/>">
									<c:out value="${schedule.content}"/>
								</a>
							</td>
							<td>
								<c:out value="${schedule.customerName}"/>
							</td>
							<td>
								<c:out value="${schedule.place}" />
							</td>
							<td>
								<c:choose>
									<c:when test="${schedule.workType == 'I'}"><c:out value="내근" /></c:when>
									<c:when test="${schedule.workType == 'E'}"><c:out value="외근" /></c:when>
									<c:otherwise><c:out value=" " /></c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${schedule.relationUser == '1'}"><c:out value="현장 출근" /></c:when>
									<c:when test="${schedule.relationUser == '2'}"><c:out value="현장 퇴근" /></c:when>
									<c:when test="${schedule.relationUser == '3'}"><c:out value="현장 출/퇴근" /></c:when>
									<c:otherwise><c:out value=" " /></c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${schedule.googleSyncId == 'Y'}"><c:out value="있음" /></c:when>
									<c:when test="${schedule.googleSyncId == 'N'}"><c:out value="없음" /></c:when>
									<c:otherwise><c:out value=" " /></c:otherwise>
								</c:choose>
							</td>
							<td><c:out value="${schedule.startHour}"/>시<c:out value="${schedule.startMin}"/>분 ~ <c:out value="${schedule.endHour}"/>시<c:out value="${schedule.endMin}"/>분</td>
							<td><a href="#" onclick="deleteSchedule('<c:out value="${sdate}"/>','<c:out value="${schedule.seq}"/>','<c:out value="${ssn}"/>', '<c:out value="${schedule.googleSyncId}"/>');"><img alt="삭제" border="0" src="/images/delete.jpg"></a></td>
							<c:choose><c:when test="${schedule.type ==  'Up-day'}">
								<%															
								if(session.getAttribute("dept").equals("9260") || session.getAttribute("dept").equals("9261") || session.getAttribute("dept").equals("9263") || session.getAttribute("dept").equals("2000")) {%>
									<td><img alt="상세정보" src="/images/btn_glass_y.jpg"  style="cursor: hand;" onclick="home_schedule('<c:out value="${ssn}"/>')"></td>
								<%} else if (session.getAttribute("companyPosition").equals("05CC") || session.getAttribute("companyPosition").equals("06CB") || session.getAttribute("companyPosition").equals("08CF") || 
									session.getAttribute("companyPosition").equals("09CJ")) { %>
										<c:set var="result2" value='${schedule.dept}' /> 
										<% String result2 = (String)pageContext.getAttribute("result2");%>
										<!-- COO -->
										<% if(session.getAttribute("dept").equals(result2)) { %>
											<td><img alt="상세정보" src="/images/btn_glass_y.jpg"  style="cursor: hand;" onclick="home_schedule('<c:out value="${ssn}"/>')"></td>
										<!-- CBO -->
										<% } else if(session.getAttribute("dept").equals("9200")) {%>
											<c:choose>
												<c:when test="${schedule.dept == '9201' || schedule.dept == '9202' || schedule.dept == '9203' || schedule.dept == '9204' || schedule.dept == '9205' }" >
													<td><img alt="상세정보" src="/images/btn_glass_y.jpg"  style="cursor: hand;" onclick="home_schedule('<c:out value="${ssn}"/>')"></td>
												</c:when>
												<c:otherwise>
													<td> </td>
												</c:otherwise>
											</c:choose>
										<% } else if(session.getAttribute("dept").equals("9210")) { %>
											<c:choose>
												<c:when test="${schedule.dept == '9211' || schedule.dept == '9212' || schedule.dept == '9213'  || schedule.dept == '9214' 
												 || schedule.dept == '9215' || schedule.dept == '9216' || schedule.dept == '9217' || schedule.dept == '9218'}" >
													<td><img alt="상세정보" src="/images/btn_glass_y.jpg"  style="cursor: hand;" onclick="home_schedule('<c:out value="${ssn}"/>')"></td>
												</c:when>
												<c:otherwise>
													<td> </td>
												</c:otherwise>
											</c:choose>
										<%} else if(session.getAttribute("dept").equals("9230")) { %>
											<c:choose>
												<c:when test="${schedule.dept == '9231' || schedule.dept == '9232' || schedule.dept == '9233' }" >
													<td><img alt="상세정보" src="/images/btn_glass_y.jpg"  style="cursor: hand;" onclick="home_schedule('<c:out value="${ssn}"/>')"></td>
												</c:when>
												<c:otherwise>
													<td> </td>
												</c:otherwise>
											</c:choose>
										<%} else if(session.getAttribute("dept").equals("9240")) { %>
											<c:choose>
												<c:when test="${schedule.dept == '9241' || schedule.dept == '9242' || schedule.dept == '9243' }" >
													<td><img alt="상세정보" src="/images/btn_glass_y.jpg"  style="cursor: hand;" onclick="home_schedule('<c:out value="${ssn}"/>')"></td>
												</c:when>
												<c:otherwise>
													<td> </td>
												</c:otherwise>
											</c:choose>									
										<%} else { %>
											<td> </td>
										<% } %>
								<% } else { %>
									<c:if test="${cSsn }" >
										<td><img alt="상세정보" src="/images/btn_glass_y.jpg"  style="cursor: hand;" onclick="home_schedule('<c:out value="${ssn}"/>')"></td>
									</c:if>								
								 <% } %>
							</c:when>
							<c:otherwise>
							</c:otherwise>
							</c:choose>
													
						</tr>
						</c:forEach>
						<c:forEach var="customer" items="${customerPickerList}">
						<tr>
							<td><c:out value="${customer.year}"/>-<c:out value="${customer.month}"/>-<c:out value="${customer.day}"/></td>
							<td style="font-weight: bold !Important; color: #723D95">고객정보</td>
							<td title="<c:out value="${customer.subject}"/>"><a href="javascript: openCustomerInfo(<c:out value="${customer.idx}"/>)"><c:out value="${customer.subject}"/></a></td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
						</tr>
						</c:forEach>
						<!-- RA 활동보고서 -->
						<%-- <c:forEach var="raschedule" items="${raScheduleList}">
						<tr>
							<td><c:out value="${raschedule.year}"/>-<c:out value="${raschedule.month}"/>-<c:out value="${raschedule.day}"/></td>
							<td style="font-weight: bold !Important; color: #f69159">활동보고서</td>
							<% if(session.getAttribute("userId").equals("5815choi") || session.getAttribute("dept").equals("9071") || session.getAttribute("dept").equals("9073")) {%>
									<td class="myoverflow" title="<c:out value="${raschedule.content}"/>"><a href="javascript: rareportDetail('<c:out value="${ssn }"/>')"><c:out value="${raschedule.content}" /></td>
							<% } else { %>
								<c:choose>
									<c:when test="${cSsn }" >
										<td class="myoverflow" title="<c:out value="${raschedule.content}"/>"><a href="javascript: rareportDetail('<c:out value="${ssn }"/>')"><c:out value="${raschedule.content}" /></td>
									</c:when>
									<c:otherwise>
										<td> </td>
									</c:otherwise>
								</c:choose>
							<% } %>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
							<td> </td>
						</tr>
						</c:forEach> --%>
					</tbody> 
					</form>
				</table>
			</div>
		</td>
	</tr>
</table>

</body>
</html>