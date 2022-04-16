<%@page import="kr.co.kmac.pms.common.util.DateUtil"%>
<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>근무시간 관리</title>
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

function googleAuth() {
	gapi.auth.authorize(googleLoginConfig, function() {
		//console.log('login complete');
		//console.log(gapi.auth.getToken());
		loadGoogleCal();
		alert("입력하신 Google계정과 동기화 되었습니다.");
	});
}

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

/* function setRelatedCompanyInfo(val) {
	var sFrm = document.frm;
	// 내근 버튼을 최초로 누른 경우 관련사 입력란에 'KMAC'를 자동으로 넣어준다. 
	if (sFrm.workType[1].checked && sFrm.customerName.value == "" ){
		sFrm.customerName.value = "KMAC";
		sFrm.place.value = "KMAC";
	}
	// 외근 버튼을 누를 시 관련사에 KMAC 라는 글씨가 있는 경우 삭제한다.
	if (sFrm.workType[0].checked && sFrm.customerName.value == "KMAC" ){
		sFrm.customerName.value = "";
		sFrm.place.value = "";
	}
} */
/* 
function setWorkType() {
	var scheduleTypeValue = document.all.type.options[document.all.type.selectedIndex].value;
	
	if (scheduleTypeValue == "교육참석") {
		alert("'교육참석'은\n조직주도교육 및 개인주도학습(집합교육) 등에 참여하는 경우\n에만 해당합니다.\n교육과정 오픈 시에는 '사업관리'를 선택하시기 바랍니다.");		
	}
	
	<c:if test="${isManager != true }">
	if (scheduleTypeValue == "Up-day") {
		alert("2019년 'Up-day' 신청이 마감되었습니다.\n문의 : 경영기획1센터(634)");
		document.location.reload();		
	}
	</c:if>
	
	if (scheduleTypeValue == "휴가" || scheduleTypeValue == "개인휴무" || scheduleTypeValue == "교육참석" || scheduleTypeValue == "Up-day") {
		document.all.divMulti2.style.display = "none";
		document.all.divMulti3.style.display = "none";
		document.all.divMulti4.style.display = "none";
		document.all.divMulti5.style.display = "none";
		
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
	}
}
 */
function setDefaultEndHour() {
	document.forms[0].endHour.options[document.all.startHour.selectedIndex+9].selected = true;
	document.forms[0].endHour.disabled = true;
}
 
function setDefaultEndMin() {
	document.forms[0].endMin.options[document.all.startMin.selectedIndex].selected = true;
	document.forms[0].endMin.disabled = true;
}

function btnSave_onclick() {
	var sFrm = document.frm;
/* 	var scheduleType = document.all.type.options[document.all.type.selectedIndex].value; */
	var workTypeFlag = false;
	
/* 	for(var i=0; i<sFrm.workType.length; i++){
		if(sFrm.workType[i].checked){
			workTypeFlag = true;
		}
	} */
	
	if(sFrm.sdate.value == "") {
		alert("시작일을 입력하세요.");sFrm.sdate.focus();return false;
	}
	
	var day_result = dateToMM(new Date());

	var result_date = sFrm.sdate.value;
	var mm = parseInt(result_date.substr(5, 2), 10);
	
	var sdate_s = document.frm.sdate.value;
	var edate_s = document.frm.edate.value;
	
	var sdate_mm = parseInt(sdate_s.substr(5,2), 10);
	var edate_mm = parseInt(edate_s.substr(5,2), 10);
    var sdate_yy = parseInt(sdate_s.substr(0, 4), 10);
    var edate_yy = parseInt(edate_s.substr(0, 4), 10);
    var date = new Date(); 
    var result_yy = parseInt(date.getFullYear()); 
    var result_mm = parseInt(new String(date.getMonth()+1));
    
    var one = parseInt(document.frmLst.test.value);
    var two = parseInt(document.frm.seq.value);
    
    var startHour = parseInt(document.all.startHour.selectedIndex);
    var startMin = parseInt(document.all.startMin.selectedIndex);

 	/* // 10시 30분 근무 시간 시작 불가
	alert(startHour);
	alert(startMin);
	
	if (document.all.startHour.selectedIndex == 10 && document.all.startMin.selectedIndex == 30){
		alert("출근 시간은 07:00~10:00 사이만 선택 가능합니다.");
	} */
	
    // 근무시간 중복 입력 불가
    <c:if test="${isManager != true }">
    	if(one == 1 && two == 0){
    		alert("이미 근무시간을 입력하였습니다."); return false;
    	}
    </c:if>

    // 한달 이후의 근무시간 입력이 불가능
 	<c:if test="${isManager != true }">
	    if(day_result == mm){
			 alert("선택하신 월의 근무시간을 변경하실 수 없습니다."); return false; 
		}else if((sdate_yy == result_yy) && (sdate_mm != result_mm+1)){
	    	alert("근무시간 입력이 불가능합니다."); return false;
	    }else if((sdate_yy == result_yy) && ((sdate_mm < edate_mm) || (sdate_mm > edate_mm))){
	    	alert("근무시간 입력이 불가능합니다."); return false;
	    }else if((sdate_yy != result_yy) && (sdate_mm == 12)){
	    	if(edate_mm != 1){
	    		alert("근무시간 입력이 불가능합니다."); return false;
	    	}
	    }else if((sdate_yy != result_yy) && (sdate_mm != 12)){
	    	alert("근무시간 입력이 불가능합니다."); return false;
	    }
    </c:if>
  
    // 이전 근무시간 등록 및 수정 불가능
	<c:if test="${isManager != true }">
		var today = dateToYYYYMMDD(new Date());
		if(today > sFrm.sdate.value) {
			alert("오늘(" + today + ") 이전 일자의 근무시간을 저장할 수 없습니다."); return false;
		}else{
			
		}
	</c:if>
	
	if(sFrm.startHour.value == "" || sFrm.startMin.value == "") {
		alert("시간을 입력하세요."); return false;
	}
	
	var ActionURL = "/action/ScheduleAction.do?mode=saveSchedule_time";
		
	//if($('googleChk').checked){
	//	gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, createScheduleWithGoogle);
	//}else{
		AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					opener.document.location.reload();
					location.href = "/action/ScheduleAction.do?mode=scheduleForm_time&ssn=<c:out value="${ssn}"/>&sdate=<c:out value="${sdate}"/>&seq=";
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){alert("이미 근무시간을 입력하여 저장할 수 없습니다.");}
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

function dateToMM(date){
    function pad(num) {
        num = num + '';
        return num.length < 2 ? '0' + num : num;
    }
    return pad(date.getMonth()+1);
}

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
					var ActionURL = "/action/ScheduleAction.do?mode=saveSchedule_time&googleSyncId="+resp.id;
					AjaxRequest.submit(
						sFrm
						,{ 'url':ActionURL
							,'onSuccess':function(obj){
								var res = eval('(' + obj.responseText + ')');
								opener.document.location.reload();
								location.href = "/action/ScheduleAction.do?mode=scheduleForm_time&ssn=<c:out value="${ssn}"/>&sdate=<c:out value="${sdate}"/>&seq=";
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

function deletProjectSchedule(projectCode,pYear,pMonth,pDay,pSsn) {
	if(confirm("선택한 근무시간을 삭제 하시겠습니까?")){
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
				location.href = "/action/ScheduleAction.do?mode=scheduleForm_time&ssn=<c:out value="${ssn}"/>&sdate=<c:out value="${sdate}"/>&seq=";
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){alert("저장할 수 없습니다.");}
		});
	}
}

var googleSyncId;
var delActionUrl;
function deleteSchedule(sdate,seq,ssn, _googleSyncId) {
	if(confirm("선택한 근무시간을 삭제 하시겠습니까?")){
		delActionUrl = "/action/ScheduleAction.do?mode=removeSchedule_time&sdate=" + sdate + "&seq=" + seq + "&ssn=" + ssn;
		
		if(_googleSyncId == ""){
			AjaxRequest.get({
				'url':delActionUrl
				,'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					//alert(res.resultMsg);
					opener.document.location.reload();
					location.href = "/action/ScheduleAction.do?mode=scheduleForm_time&ssn=<c:out value="${ssn}"/>&sdate=<c:out value="${sdate}"/>&seq=";
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){alert("저장할 수 없습니다.");}
			});	
		}else{
			if(confirm("Google 계정에 연결된 일정도 같이 삭제 하시겠습니까?")){
				googleSyncId = _googleSyncId;
				gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, deleteScheduleWithGoogle);
			}else{
				AjaxRequest.get({
					'url':delActionUrl
					,'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						//alert(res.resultMsg);
						opener.document.location.reload();
						location.href = "/action/ScheduleAction.do?mode=scheduleForm_time&ssn=<c:out value="${ssn}"/>&sdate=<c:out value="${sdate}"/>&seq=";
					}
					,'onLoading':function(obj){}
					,'onError':function(obj){alert("저장할 수 없습니다.");}
				});
			}
		}		
			

	}
}


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
</script>
<script src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>
<script type="text/javascript">

</script>
</head>

<body style="width: 700px">
<%-- 작업영역 --%>
<table width="700px" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="70%"><h4 class="mainTitle">근무시간<c:if test="${scheduleUserName != '' }">(<c:out value="${scheduleUserName}" />)</c:if></h4></td>
					<td width="30%">
						<div class="btNbox txtR">
							<!-- <a class="btNaaa txt4btn" href="javascript:googleAuth();">Google 동기화</a>  -->
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
			 
	<c:if test="${cSsn }" >
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
						<td class="detailTableTitle_center" width="15%">근무 일자</td>
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
						<td class="detailTableTitle_center">근무 시간<span id="divMulti5">(*)</span></td>
						<td class="detailTableField_left" colspan="3">
							<select name="startHour" id="startHour" value="5" class="selectboxPopup" style="width: 63px;" onchange="setDefaultEndHour();">
								<option value=""></option>
								<c:forEach var="hour" items="${hourList}">
									<option value="<c:out value="${hour}"/>"<c:if test="${hour==scheduleInfo.startHour}"> selected</c:if>><c:out value="${hour}"/></option>
								</c:forEach>
							</select>&nbsp시&nbsp
							<select name="startMin" id="startMin" class="selectboxPopup" style="width: 63px;" onchange="setDefaultEndMin();">
								<option value=""></option>
								<c:forEach var="minute" items="${minList}">
								<option value="<c:out value="${minute}"/>"<c:if test="${minute==scheduleInfo.startMin}"> selected</c:if>><c:out value="${minute}"/></option>
								</c:forEach>
							</select>&nbsp분&nbsp~&nbsp
							<select name="endHour" id="endHour" class="selectboxPopup" style="width: 63px;" disabled>
								<option value=""></option>
								<c:forEach var="hour" items="${hourList_end}">
								<option value="<c:out value="${hour}"/>"<c:if test="${hour==scheduleInfo.endHour}"> selected</c:if>><c:out value="${hour}"/></option>
								</c:forEach>
							</select>&nbsp시&nbsp
							<select name="endMin" id="endMin" class="selectboxPopup" style="width: 63px;" disabled>
								<option value=""></option>
								<c:forEach var="minute" items="${minList}">
								<option value="<c:out value="${minute}"/>"<c:if test="${minute==scheduleInfo.endMin}"> selected</c:if>><c:out value="${minute}"/></option>
								</c:forEach>
							</select>&nbsp분&nbsp
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
					<td style="text-align:left;padding-left: 5px; padding-top: 5px;"><b>* 출근시간은 07:00~10:00 사이만 선택 가능</b><br/>
					 근무시간을 저장하지 않은 채 익월로 넘어갈 경우 08:30~17:30 값으로 자동 입력 처리
					</td>
					<td>
						<div class="btNbox txtR">
							<a class="btN006bc6 txt2btn" href="?mode=scheduleForm_time&ssn=<c:out value="${ssn}"/>&sdate=<c:out value="${sdate}"/>">새일정</a>
							<a class="btNe006bc6 txt2btn" href="#" onclick="btnSave_onclick();">저장</a>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</c:if>
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
							<td style="width:20%">근무 일자</td>
							<td style="width:*">근무 시간</td>
							<td style="width:10%">삭제</td>
						</tr>				
					</thead> 
					<form name="frmLst" method="post">
						<input type="hidden" name="ssn" value="<c:out value="${schedule.ssn}"/>">
						<input type="hidden" name="sdate" value="<c:out value="${sdate}"/>">
						<input type="hidden" name="test" value="<c:out value="${schedule.seq}" /> ">
					<tbody>
					<c:forEach var="schedule" items="${scheduleList}">
						<tr>
							<td><c:out value="${schedule.year}"/>-<c:out value="${schedule.month}"/>-<c:out value="${schedule.day}"/></td>
					<%-- 		<td><c:out value="${schedule.startHour}"/>시<c:out value="${schedule.startMin}"/>분 ~ <c:out value="${schedule.endHour}"/>시<c:out value="${schedule.endMin}"/>분</td> --%>
							<td >
								<c:if test="${cSsn }" ><a href="?mode=scheduleForm_time&ssn=<c:out value="${schedule.ssn}"/>&sdate=<c:out value="${sdate}"/>&seq=<c:out value="${schedule.seq}"/>"></c:if>
									<c:out value="${schedule.startHour}"/>시<c:out value="${schedule.startMin}"/>분 ~ <c:out value="${schedule.endHour}"/>시<c:out value="${schedule.endMin}"/>분
									<%-- <c:out value="${schedule.seq}" /> --%>
								<c:if test="${cSsn }" ></a></c:if>
							</td>
							<td><c:if test="${cSsn }" ><a href="#" onclick="deleteSchedule('<c:out value="${sdate}"/>','<c:out value="${schedule.seq}"/>','<c:out value="${ssn}"/>', '<c:out value="${schedule.googleSyncId}"/>');"><img alt="삭제" border="0" src="/images/delete.jpg"></a></c:if></td>
							<script>
									document.frmLst.test.value = '<c:out value="${schedule.seq }" />';
							</script>
						</tr>
					</c:forEach>
					</tbody> 
					</form>
				</table>
			</div>
		</td>
	</tr>
</table>
</body>

</html>