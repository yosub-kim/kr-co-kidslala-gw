var clientId = '538553006464.apps.googleusercontent.com';
var scopes =  'https://www.googleapis.com/auth/calendar';
var apiKey = 'AIzaSyD4TUyoZqljXZPBmc-TgScUdcGwJ5wdOxk';
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

  
$(document).delegate("#scheduleMonthListPage", "pageshow", function(){
	//alert('#scheduleMonthPage');
	//$("#googlePopupDialog").popup("open");
	try{
		gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, handleAuthResult);
	}catch(e){}
});
$(document).delegate("#scheduleDateListPage", "pageshow", function(){
	//alert('#scheduleDateListPage');
	showTodayEventList($("#scheduleSelDate").val());
	try{
		gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, handleAuthResult);
	}catch(e){}
});
 
var gblSelDate;
function showEventList(selDate){
	gblSelDate = selDate;
	//alert(selDate);	
	$.mobile.showPageLoadingMsg();
	$.getJSON("/action/ScheduleMobileAction.do?mode=selectedScheduleList&sdate="+selDate, function(data, status) {
		//alert("Data Loaded: " + (data));
		var listMarkup = "";
		if(data.projectList.length > 0){
			$(data.projectList).each(function(index, project){
				listMarkup += "<li style='padding: 0px;'>";
				listMarkup += "	<a href='/action/ScheduleMobileAction.do?mode=selectedProjectSchedule&projectCode="+project.projectCode+"&seq="+project.seq+"&sdate="+project.year+"-"+project.month+"-"+project.day+"' data-transition='slide'>";
				listMarkup += "	<h4 class='ui-li-heading'>[지도일지] "+project.projectName+"</h4>";
				listMarkup += "	<p class='ui-li-desc' style='font-size: 11px;'>관련회사:"+project.customerName+"&nbsp;|&nbsp;관련자: "+project.relationUser+"</p>";
				listMarkup += "</a></li>"; 
			});
		}
		if(data.scheduleList.length > 0){
			googleSyncIdArray = new Array();
			$(data.scheduleList).each(function(index, schedule){
				if(schedule.googleSyncId != ""){
					googleSyncIdArray.push(schedule.googleSyncId);
				}
				listMarkup += "<li style='padding: 0px;'>";
				listMarkup += "	<a href='/action/ScheduleMobileAction.do?mode=selectedDateSchedule&seq="+schedule.seq+"&sdate="+schedule.year+"-"+schedule.month+"-"+schedule.day+"' data-transition='slide'>";
				listMarkup += "	<h4 class='ui-li-heading'>["+schedule.type+"] "+schedule.content+"</h4>";
				listMarkup += "	<p class='ui-li-desc' style='font-size: 11px;'>"+schedule.startHour+"시 "+schedule.startMin+"분 ~ "+schedule.endHour+"시 "+schedule.endMin+"분&nbsp;|&nbsp; 관련회사:"+schedule.customerName+"&nbsp;|&nbsp;관련자: "+schedule.relationUser+"</p>";
				listMarkup += "</a></li>"; 
			});
		}
		if(listMarkup == ""){
			listMarkup += "<li id='calEmpty' style='padding: 0px;'><a href='/m/web/schedule/scheduleWrite.jsp?saveMode=INSERT' data-transition='slide' >";
			listMarkup += "	<h4 class='ui-li-heading' style='text-align: center;'>등록된 일정이 없습니다. </h4>";
			listMarkup += "	";
			listMarkup += "</a></li>"; 			
		}
		//$("#scheduleListView li:last").after(listMarkup);
		$("#scheduleListView").html(listMarkup);
		$("#scheduleListView").listview('refresh');
		
		try{
			gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, loadScheduleWithGoogle);
		}catch(e){}
		
		$.mobile.hidePageLoadingMsg();
	});		
}
function showTodayEventList(selDate){
	gblSelDate = selDate;
	//alert(selDate);	
	$.mobile.showPageLoadingMsg();
	$.getJSON("/action/ScheduleMobileAction.do?mode=selectedScheduleList&sdate="+selDate, function(data, status) {
		//alert("Data Loaded: " + (data));
		var listMarkup = "";
		if(data.projectList.length > 0){
			$(data.projectList).each(function(index, project){
				listMarkup += "<li style='padding: 0px;'>";
				listMarkup += "	<a href='/action/ScheduleMobileAction.do?mode=selectedProjectSchedule&projectCode="+project.projectCode+"&seq="+project.seq+"&sdate="+project.year+"-"+project.month+"-"+project.day+"' data-transition='slide'>";
				listMarkup += "	<h4 class='ui-li-heading'>[지도일지] "+project.projectName+"</h4>";
				listMarkup += "	<p class='ui-li-desc' style='font-size: 11px;'>관련회사:"+project.customerName+"&nbsp;|&nbsp;관련자: "+project.relationUser+"</p>";
				listMarkup += "</a></li>"; 
			});
		}
		if(data.scheduleList.length > 0){
			googleSyncIdArray = new Array();
			$(data.scheduleList).each(function(index, schedule){
				if(schedule.googleSyncId != ""){
					googleSyncIdArray.push(schedule.googleSyncId);
				}			
				listMarkup += "<li style='padding: 0px;'>";
				listMarkup += "	<a href='/action/ScheduleMobileAction.do?mode=selectedDateSchedule&seq="+schedule.seq+"&sdate="+schedule.year+"-"+schedule.month+"-"+schedule.day+"' data-transition='slide'>";
				listMarkup += "	<h4 class='ui-li-heading'>["+schedule.type+"] "+schedule.content+"</h4>";
				listMarkup += "	<p class='ui-li-desc' style='font-size: 11px;'>"+schedule.startHour+"시 "+schedule.startMin+"분 ~ "+schedule.endHour+"시 "+schedule.endMin+"분&nbsp;|&nbsp; 관련회사:"+schedule.customerName+"&nbsp;|&nbsp;관련자: "+schedule.relationUser+"</p>";
				listMarkup += "</a></li>"; 
			});
		}
		if(listMarkup == ""){
			listMarkup += "<li style='padding: 0px;'><a href='/m/web/schedule/scheduleWrite.jsp?saveMode=INSERT' data-transition='slide' >";
			listMarkup += "	<h4 class='ui-li-heading' style='text-align: center;'>등록된 일정이 없습니다. </h4>";
			listMarkup += "	";
			listMarkup += "</a></li>"; 			
		}
		$("#scheduleListView li:last").after(listMarkup);
		$("#scheduleListView").listview('refresh');
		
		try{
			gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, loadScheduleWithGoogle);
		}catch(e){}

		$.mobile.hidePageLoadingMsg();
	});
	
}

function loadScheduleWithGoogle(authResult) {
	if (authResult && !authResult.error) {
		$.mobile.showPageLoadingMsg();
		gapi.client.load('calendar', 'v3', function() {
			var request = gapi.client.calendar.events.list({
				"calendarId": "primary"
					//,"orderBy": "startTime",
					//,"singleEvents": "true"
					,"timeMin": gblSelDate + "T00:00:00.000+09:00"
					,"timeMax": gblSelDate + "T23:59:00.000+09:00"
			});
			request.execute(function(resp) {
				var listMarkup = "";
				if(resp.items != undefined){
					for (var i = 0; i < resp.items.length; i++) {
						if(!arrayInclude(googleSyncIdArray, resp.items[i].id)) {
							var startDayStr;if(resp.items[i].start.date==undefined){startDayStr=resp.items[i].start.dateTime;}else{startDayStr=resp.items[i].start.date;}
							var startDayObj;if(startDayStr.length>10){startDayObj=moment(startDayStr,"YYYY-MM-DDTHH:mm:ssZ").toDate();}else{startDayObj=moment(startDayStr,"YYYY-MM-DD").toDate();};
							var endDDayStr;if(resp.items[i].end.date==undefined){endDDayStr=resp.items[i].end.dateTime;}else{endDDayStr=resp.items[i].end.date;}
							var endDDayObj;if(endDDayStr.length>10){endDDayObj=moment(endDDayStr,"YYYY-MM-DDTHH:mm:ssZ").toDate();}else{endDDayObj=moment(endDDayStr,"YYYY-MM-DD").toDate();};
							var location=resp.items[i].location==undefined?"":resp.items[i].location;
							var summary=resp.items[i].summary==undefined?"":resp.items[i].summary;
							
							listMarkup += "<li style='padding: 0px;'>";
							listMarkup += "	<a href='' data-transition='slide'>";
							listMarkup += "	<h4 class='ui-li-heading'>[Google] "+summary+"</h4>";
							listMarkup += "	<p class='ui-li-desc' style='font-size: 11px;'>"+moment(startDayObj).format("YYYY-M-D h:mm")+" ~ "+moment(endDDayObj).format("YYYY-M-D h:mm")+"</p>";
							listMarkup += "</a></li>"; 
						}
					}
					if(listMarkup != ""){
						if($("#calEmpty").length > 0 ){
							$("#scheduleListView").html(listMarkup);
						}else{
							$("#scheduleListView li:last").after(listMarkup);
						}
						$("#scheduleListView").listview('refresh');
					}
				}
				$.mobile.hidePageLoadingMsg();
			});
		});
	} else {
		//gapi.auth.authorize(googleLoginConfig, function() {
		//	console.log('login complete');
		//	console.log(gapi.auth.getToken());
		//	gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, loadScheduleWithGoogle);
		//});			
		//$.mobile.hidePageLoadingMsg();	
		//return false;
	}	
}

function createSchedule(){
	var regExpDate = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/;
	var regExpTime = /^[0-9]{2}:[0-9]{2}$/;
	var today = new Date();
	
	if(!regExpDate.test($('#sDate').val())){alert("시작일 날짜 형식을 YYYY-MM-DD(ex.2013-05-08)로 입력하세요.");return false;}
	if(!regExpDate.test($('#eDate').val())){alert("종료일 날짜 형식을 YYYY-MM-DD(ex.2013-05-08)로 입력하세요.");return false;}
	if ($('#type').val() != "휴가" && $('#type').val() != "교육참석") {
		if(!regExpTime.test($('#sTime').val())){alert("시작시간 형식을 HH:MM(ex.13:30)로 입력하세요.");return false;} 
		if(!regExpTime.test($('#eTime').val())){alert("종료시간 형식을 HH:MM(ex.13:30)로 입력하세요.");return false;}
		if(!(moment($('#sTime').val(), "HH:mm").isValid())){alert("시작시간 형식을 HH:MM(ex.13:30)로 입력하세요.");return false;} 
		if(!(moment($('#eTime').val(), "HH:mm").isValid())){alert("종료시간 형식을 HH:MM(ex.13:30)로 입력하세요.");return false;} 
	} else {
		$('#sTime').val("00:00");
		$('#eTime').val("00:00");
	}
	if(!(moment($('#sDate').val(), "YYYY-MM-DD").isValid())){alert("입력한 시작일이 유효하지 않습니다.");return false;} 
	if(!(moment($('#eDate').val(), "YYYY-MM-DD").isValid())){alert("입력한 종료일이 유효하지 않습니다.");return false;}
	if($('#sDate').val() == ""){alert("시작일을 입력하세요.");return false;}
	if($('#eDate').val() == ""){alert("종료일을 입력하세요.");return false;}
	if($('#type').val() != "휴가" && $('#type').val() != "교육참석" && $('#sTime').val() == ""){alert("시작시간을 입력하세요.");return false;}
	if($('#type').val() != "휴가" && $('#type').val() != "교육참석" && $('#eTime').val() == ""){alert("종료시간을 입력하세요.");return false;}
	if($('#type').val() != "휴가" && $('#type').val() != "교육참석" && $('#content').val() == ""){alert("업무내용을 입력하세요.");return false;}
	if($('#sDate').val() > $('#eDate').val()){alert("시작일이 종료일 이전 이어야 합니다.");return false;}
	if(($('#sDate').val() == $('#eDate').val()) && ($('#sTime').val() > $('#eTime').val())){alert("시작시간이 종료시간 이전 이어야 합니다.");return false;}
	if($('#type').val() != "휴가" && $('#type').val() != "교육참석")	{
		if(!$('input[name=workType]').is(':checked')){
			alert('외/내근 유형을 선택하세요');return;
		}
	}
	
	
	if (dateToYYYYMMDD(today) > $('#sDate').val() || dateToYYYYMMDD(today) > $('#eDate').val()) {
		alert("오늘 이전 날짜를 시작일과 종료일로 지정할 수 없습니다.");
		return;
	}
	
	$.mobile.showPageLoadingMsg();
	var ActionURL = "/action/ScheduleMobileAction.do?mode=saveSchedule";
	var formObj = $('#scheduleForm');

	if( $('#saveGoogle').attr('checked')){
		gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, createScheduleWithGoogle);
	}else{
		$.post(ActionURL, formObj.serialize(), function(data) {
			alert("등록하였습니다.");
			$.mobile.hidePageLoadingMsg();	
			location.href = "/action/ScheduleMobileAction.do?mode=scheduleOfMonth";
		});	
	}
	
}
function dateToYYYYMMDD(date){
    function pad(num) {
        num = num + '';
        return num.length < 2 ? '0' + num : num;
    }
    return date.getFullYear() + '-' + pad(date.getMonth()+1) + '-' + pad(date.getDate());
}
function createScheduleWithGoogle(authResult) {
	//var authorizeButton = document.getElementById('googleLinkBtn');
	if (authResult && !authResult.error) {
		var sendStr = '{	"start"		: {"dateTime": "'+$('#sDate').val() +'T'+ $('#sTime').val()+':00.000+09:00'+'"},'
					+ '		"end"		: {"dateTime": "'+$('#eDate').val() +'T'+ $('#eTime').val()+':00.000+09:00'+'"},'
					+ '		"summary"	: "'+$('#content').val()+'",'
					+ '		"location"	: "'+$('#place').val()+'"}';
		gapi.client.load('calendar', 'v3', function() {
			var request = gapi.client.calendar.events.insert({
	    		"calendarId": "primary",
	    		"resource": jQuery.parseJSON(sendStr)
	    	});
		  	request.execute(function(resp) {
				//console.log(resp);
				if (resp.id){
					var ActionURL = "/action/ScheduleMobileAction.do?mode=saveSchedule";
					var formObj = $('#scheduleForm');					
					$.post(ActionURL + "&googleSyncId="+resp.id, formObj.serialize(), function(data) {
						alert("Google 캘린더와 동기화하여 등록하였습니다.");
						$.mobile.hidePageLoadingMsg();	
						location.href = "/action/ScheduleMobileAction.do?mode=scheduleOfMonth";
					});	
				}else{
					alert("An error occurred. Please try again later.");
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
		$.mobile.hidePageLoadingMsg();	
		return false;
	}	
}

function deleteSchedule(type){
	$.mobile.showPageLoadingMsg();	
	if(type == "project"){
		deletProjectSchedule();
	}else{
		deleteDateSchedule();
	}
}
function deletProjectSchedule(projectCode,pYear,pMonth,pDay,pSsn) {
	if(confirm("선택한 스케쥴을 삭제 하시겠습니까?")){
		var ActionURL = "/action/ProjectReportInfoAction.do?mode=deleteProjectReportInfo";	
		ActionURL += "&projectCode=" + $('#scheduleDateView_projectCode').val();
		ActionURL += "&year=" + $('#scheduleDateView_year').val();
		ActionURL += "&month=" + $('#scheduleDateView_month').val();
		ActionURL += "&day=" + $('#scheduleDateView_day').val();
		ActionURL += "&ssn=" + $('#scheduleDateView_ssn').val();
		
		$.post(ActionURL, "", function(data) {
			alert("삭제하였습니다.");
			$.mobile.hidePageLoadingMsg();	
			location.href = "/action/ScheduleMobileAction.do?mode=scheduleOfMonth";
		});	
	}
}
function deleteDateSchedule(sdate,seq,ssn) {
	var ActionURL = "/action/ScheduleAction.do?mode=removeSchedule";
		ActionURL += "&sdate=" + $('#scheduleDateView_date').val();
		ActionURL += "&seq=" + $('#scheduleDateView_seq').val();
		ActionURL += "&ssn=" + $('#scheduleDateView_ssn').val();
	if(confirm("선택한 일정을 삭제 하시겠습니까?")){
		if($('#scheduleDateView_googleSyncId').val() == ""){
			$.post(ActionURL, "", function(data) {
				alert("삭제하였습니다.");
				$.mobile.hidePageLoadingMsg();	
				location.href = "/action/ScheduleMobileAction.do?mode=scheduleOfMonth";
			});	
		}else{
			if(confirm("Google 계정에 연결된 일정도 같이 삭제 하시겠습니까?")){
				gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, deleteScheduleWithGoogle);
			}else{
				$.post(ActionURL, "", function(data) {
					alert("삭제하였습니다.");
					$.mobile.hidePageLoadingMsg();	
					location.href = "/action/ScheduleMobileAction.do?mode=scheduleOfMonth";
				});	
			}
		}
	}else{
		$.mobile.hidePageLoadingMsg();
	}
}

function deleteScheduleWithGoogle(authResult) {
	if (authResult && !authResult.error) {
		if( $('#scheduleDateView_googleSyncId').val() == ""){alert("not found google sync id;");return false;}
		gapi.client.load('calendar', 'v3', function() {
			var request = gapi.client.calendar.events.delete({
				"calendarId": "primary",
				"eventId": $('#scheduleDateView_googleSyncId').val()
			});
			request.execute(function(resp) {
				var ActionURL = "/action/ScheduleAction.do?mode=removeSchedule";
					ActionURL += "&sdate=" + $('#scheduleDateView_date').val();
					ActionURL += "&seq=" + $('#scheduleDateView_seq').val();
					ActionURL += "&ssn=" + $('#scheduleDateView_ssn').val();
				$.post(ActionURL, "", function(data) {
					alert("삭제하였습니다.");
					$.mobile.hidePageLoadingMsg();	
					location.href = "/action/ScheduleMobileAction.do?mode=scheduleOfMonth";
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
		$.mobile.hidePageLoadingMsg();	
		return false;
	}	
}

function scheduleChageColor(type, obj){
	if(type == "over"){
		$(obj).css('background-color','#abd0bc');
	}else{
		if($(obj).attr("isToday") == 'true'){
			$(obj).css('background-color','#c6f4fd');
		}else{
			$(obj).css('background-color','#f1f1f1');
		}
	}
}

function googleAuth() {
    gapi.auth.authorize(googleLoginConfig, function() {
      //console.log('login complete');
      //console.log(gapi.auth.getToken());
      $("#googlePopupDialog").popup("close");
      loadGoogleCal(getYear()+"-"+getMonth()+"-01", getYear()+"-"+getMonth()+"-"+getMaxDay(getMonth()));
      alert("입력하신 Google계정과 동기화 되었습니다.");
      $.mobile.loadPage( "/action/ScheduleMobileAction.do?mode=scheduleOfMonth" );
    });
}


function loadGoogleCal() {
	if(gapi.auth.getToken() != null){
		gapi.client.load('calendar', 'v3', function() {
			var request = gapi.client.calendar.events.list({
				"calendarId": "primary",
				"showDeleted": false,
				"timeMin": $("#intYear").val()+"-"+$("#intMonth").val()+"-01T00:00:00.000+09:00",
				"timeMax": $("#intYear").val()+"-"+$("#intMonth").val()+"-"+getMaxDay($("#intMonth").val())+"T23:59:00.000+09:00"
		}); 
			request.execute(function(resp) {
				for (var i = 0; i < resp.items.length; i++) {
					var startDayStr;if(resp.items[i].start.date==undefined){startDayStr=resp.items[i].start.dateTime;}else{startDayStr=resp.items[i].start.date;}
					var startDayObj;if(startDayStr.length>10){startDayObj=moment(startDayStr,"YYYY-MM-DDTHH:mm:ssZ").toDate();}else{startDayObj=moment(startDayStr,"YYYY-MM-DD").toDate();};
					$("#cal"+moment(startDayObj).format("D")).html(".");
				}
			});
		});
	}
}


function getDay(){ 
    var day = new Date().getDate(); 
     if(day < 10) day = "0"+day; 
    return day; 
} 
function getMonth(){ 
    var r = new Array( '01','02','03','04','05','06','07','08','09','10','11','12'); 
    return r[new Date().getMonth()]; 
} 
function getYear(){ 
    return new Date().getFullYear(); 
} 
function getMaxDay(month){
	if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){return 31;
	}else if(month == 2){return 28;
	}else{return 30;}
}