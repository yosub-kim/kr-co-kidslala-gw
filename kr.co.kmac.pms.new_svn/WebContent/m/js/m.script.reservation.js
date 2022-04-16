//페이지 초기화 함수
$(document).delegate("#bizSchoolListPage", "pageinit", function(){
	//alert('#bizSchoolListPage');
	getBizSchoolListPage();
});
$(document).delegate("#equipmentListPage", "pageinit", function(){
	//alert('#equipmentListPage');
	getEquipmentListPage();
});
$(document).delegate("#meetingRoomListPage", "pageinit", function(){
	//alert('#meetingRoomListPage');
	getMeetingRoomListPage();
});


//비즈니스 스쿨 시작
function getBizSchoolListPage(){
	if($("#bizSchoolListView li").length){
		$.mobile.showPageLoadingMsg();
		$.getJSON("/action/BizSchoolReservationAction.do?mode=getBizSchoolReservationList", 
				{"yyyy": $("#selYear").val(), "mm": $("#selMonth").val(), "dd": $("#selDay").val()}, 
				function(data, status) {
			var listMarkup = "";
			if(data.bizSchoolReservationList.length > 0){
				$(data.bizSchoolReservationList).each(function(index, rs){
					listMarkup += "<li><a href='javascript:getBizSchoolDetail("+rs.fId+");'>";
					listMarkup += "	 <h4 class='ui-li-heading'>"+rs.fUsername+"</h4>"; 
					listMarkup += "	 <span class='ui-li-count ui-btn-up-c ui-btn-corner-all'>"; 
					if(rs.fontCheck == '1') {listMarkup += "<font color='#000080'>"+rs.fHh+"시</font>";}			// 확정
					else if(rs.fontCheck == '3') {listMarkup += "<font color='#008000'>"+rs.fHh+"시</font>";}	// 외부임대
					else {listMarkup += "<font color='#FF0000'>"+rs.fHh+"시</font>";}	// 미확정
					listMarkup += "</span></a></li>";
				});
			}else{
				alert("검색결과가 없습니다.");
			}
			listMarkup += "<li><span style='font-size:8pt; color:#008000;'>녹색</span><span style='font-size:8pt;'>: 외부임대 / </span><span style='font-size:8pt; color:#FF0000;'>빨강</span><span style='font-size:9pt;'>: 미확정 /</span><span style='font-size:8pt; color:#000080;'> 파랑</span><span style='font-size:8pt;'>: 확정 / 표시되는 시간은 교육시작 시간</span></li>";
			$("#bizSchoolListView li:last").after(listMarkup);
			$("#bizSchoolListView").listview('refresh');
	
			$.mobile.hidePageLoadingMsg();
		});	
	}
}
function getBizSchoolListPageSearch(){
	$.mobile.showPageLoadingMsg();
	//var listMarkup = "<li data-role='list-divider'>강의실 선택</li>";
	$.getJSON("/action/BizSchoolReservationAction.do?mode=getBizSchoolReservationList", 
			{"yyyy": $("#selYear").val(), "mm": $("#selMonth").val(), "dd": $("#selDay").val()}, 
			function(data, status) {
				var listMarkup = "<li data-role='list-divider'>강의실 선택</li>";
				if(data.bizSchoolReservationList.length > 0){
					$(data.bizSchoolReservationList).each(function(index, rs){
						listMarkup += "<li><a href='javascript:getBizSchoolDetail("+rs.fId+");'>";
						listMarkup += "	 <h4 class='ui-li-heading'>"+rs.fUsername+"</h4>";
						listMarkup += "	 <span class='ui-li-count ui-btn-up-c ui-btn-corner-all'>"; 
						if(rs.fontCheck == '1') {listMarkup += "<font color='#000080'>"+rs.fHh+"시</font>";}			// 확정
						else if(rs.fontCheck == '3') {listMarkup += "<font color='#008000'>"+rs.fHh+"시</font>";}	// 외부임대
						else {listMarkup += "<font color='#FF0000'>"+rs.fHh+"시</font>";}	// 미확정
						listMarkup += "</span></a></li>";
					});
				}else{
					listMarkup += "<li><a href='javascript:void(0);'>";
					listMarkup += "	 <h4 class='ui-li-heading'>검색결과가 없습니다</h4>";
					listMarkup += "</a></li>";
				}
				listMarkup += "<li><span style='font-size:8pt; color:#008000;'>녹색</span><span style='font-size:8pt;'>: 외부임대 / </span><span style='font-size:8pt; color:#FF0000;'>빨강</span><span style='font-size:8pt;'>: 미확정 /</span><span style='font-size:8pt; color:#000080;'> 파랑</span><span style='font-size:8pt;'>: 확정 / 표시되는 시간은 교육시작 시간</span></li>";
				$("#bizSchoolListView").html(listMarkup);
				$("#bizSchoolListView").listview('refresh');
				
				$.mobile.hidePageLoadingMsg();
			});	
}
function getBizSchoolDetail(fId){
	$.mobile.changePage( "/action/BizSchoolReservationAction.do?mode=getBizSchoolReservationDetail&fId="+fId
			, { transition: "slide"} );	
}
function modifyBizSchoolDetail(fId){
	$.mobile.changePage( "/action/BizSchoolReservationAction.do?mode=updateForm&fId="+fId
			, { transition: "slide"} );		
}
function confirmBizSchoolDetail(fId){
	$.mobile.showPageLoadingMsg();
	$.post('/action/BizSchoolReservationAction.do?mode=confirmBizSchoolReservation', $('#bizSchoolViewForm').serialize(), function(data) {
		if($.parseJSON(data).result == "SUCCESS"){
			alert("확정되었습니다."); 
			$.mobile.changePage("/m/web/reservation/bizSchoolList.jsp");
		} else{
			alert($.parseJSON(data).resMsg);
			$.mobile.hidePageLoadingMsg();	
		}
	});	
}
function cancleBizSchoolDetail(fId){
	$.mobile.showPageLoadingMsg();
	$.post('/action/BizSchoolReservationAction.do?mode=cancleBizSchoolReservation', $('#bizSchoolViewForm').serialize(), function(data) {
		if($.parseJSON(data).result == "SUCCESS"){
			alert("취소되었습니다."); 
			$.mobile.changePage("/m/web/reservation/bizSchoolList.jsp");
		} else{
			alert($.parseJSON(data).resMsg);
			$.mobile.hidePageLoadingMsg();	
		}
	});		
}

function insertBizSchool(){
	// 입력값 체크
	var customer =$('#fCustomer').val();
	if ( customer == "") {
		alert("담당자를 입력하여 주십시오.");
		$('#fCustomer').focus();
		return;
	}
	var stime = $('#fHh').val();
	var etime = $('#fEhh').val();
	if (stime >= etime) {
		alert("시작시간이 종료시간 이전이어야 합니다.");
		return;
	}
	$.mobile.showPageLoadingMsg();
	$.post('/action/BizSchoolReservationAction.do?mode=insertBizSchoolReservation', $('#bizSchoolInsertForm').serialize(), function(data) {
		if($.parseJSON(data).result == "SUCCESS"){
			alert("등록되었습니다."); 
			$.mobile.changePage("/m/web/reservation/bizSchoolList.jsp");
		} else{
			alert($.parseJSON(data).resMsg);
			$.mobile.hidePageLoadingMsg();	
		}
	});
} 
function updateBizSchool(){
	$.mobile.showPageLoadingMsg();
	$.post('/action/BizSchoolReservationAction.do?mode=updateBizSchoolReservation', $('#bizSchoolInsertForm').serialize(), function(data) {
		if($.parseJSON(data).result == "SUCCESS"){
			alert("수정되었습니다."); 
			$.mobile.changePage("/m/web/reservation/bizSchoolList.jsp");
		} else{
			alert($.parseJSON(data).resMsg);
			$.mobile.hidePageLoadingMsg();	
		} 
	});
}
function goInsertBizSchoolReservation() {
	var month = $("#selMonth").val();
	var day =  $("#selDay").val();
	//alert(month1+"/"+day1);
	$.mobile.changePage( "/action/BizSchoolReservationAction.do?mode=inputForm&month="+month+"&day="+day, { transition: "slide"} );
}

//비즈니스 스쿨 끝


//주차 예약 시작
function getParkingListPageSearch() {
	var month = $("#selYear").val() + $("#selMonth").val();
	$.mobile.changePage( "/action/ParkingReservationAction.do?mode=getParkingReservationMonthlyList&month="+month, { transition: "slide"} );
}
function goInsertParkingReservation(pdate){
	//alert($("li[id='parkingReservationItem']").length);
	if($("li[id='parkingReservationItem']").length >= 10){
		alert("하루 최대 주차 대수는 10대 입니다.");
	}else {
		$.mobile.changePage( "/action/ParkingReservationAction.do?mode=inputForm&date="+pdate, { transition: "slide"} );		
	}
}
function insertParkingReservation(){
	// 입력값 체크
	var customer =$('#pname').val();
	if ( customer == "") {
		alert("성명을 입력하여 주십시오.");
		$('#pname').focus();
		return;
	}
	var tel = $('#ptel').val();
	if (tel == "") {
		alert("연락처를 입력하여 주십시오.");
		$('#ptel').focus();
		return;
	}	
	var carNum = $('#pcarNum').val();
	if (carNum == "") {
		alert("차량번호를 입력하여 주십시오.");
		$('#pcarNum').focus();
		return;
	}	
	$.mobile.showPageLoadingMsg();
	$.post('/action/ParkingReservationAction.do?mode=insertParkingReservation', $('#parkingInsertForm').serialize(), function(data) {
		if($.parseJSON(data).result == "SUCCESS"){
			alert("등록되었습니다."); 
			$.mobile.changePage("/action/ParkingReservationAction.do?mode=getParkingReservationMonthlyList&month="+$("#pdate").val().replace("-", "").substring(0, 6));
		} else{
			alert($.parseJSON(data).msg);
			$.mobile.hidePageLoadingMsg();	
		}
	});
}
function updateParkingReservation(){
	$.mobile.showPageLoadingMsg();
	$.post('/action/ParkingReservationAction.do?mode=updateParkingReservation', $('#parkingInsertForm').serialize(), function(data) {
		if($.parseJSON(data).result == "SUCCESS"){
			alert("수정되었습니다."); 
			$.mobile.changePage("/action/ParkingReservationAction.do?mode=getParkingReservationMonthlyList&month="+$("#pdate").val().replace("-", "").substring(0, 6));
		} else{
			alert($.parseJSON(data).resMsg);
			$.mobile.hidePageLoadingMsg();	
		}
	});
}
function modifyParkingDetail(idx){
	$.mobile.changePage( "/action/ParkingReservationAction.do?mode=updateForm&idx="+idx
			, { transition: "slide"} );		
}
function confirmParkingDetail(idx){
	$.mobile.showPageLoadingMsg();
	$.post('/action/ParkingReservationAction.do?mode=confirmParkingReservation', $('#parkingViewForm').serialize(), function(data) {
		if($.parseJSON(data).result == "SUCCESS"){
			alert("확정되었습니다."); 
			$.mobile.changePage("/action/ParkingReservationAction.do?mode=getParkingReservationMonthlyList&month="+$("#pdate").val().replace("-", "").substring(0, 6));
		} else{
			alert($.parseJSON(data).resMsg);
			$.mobile.hidePageLoadingMsg();	
		}
	});	
}
function cancleParkingDetail(idx){
	$.mobile.showPageLoadingMsg();
	$.post('/action/ParkingReservationAction.do?mode=deleteParkingReservation', $('#parkingViewForm').serialize(), function(data) {
		if($.parseJSON(data).result == "SUCCESS"){
			alert("삭제되었습니다."); 
			$.mobile.changePage("/action/ParkingReservationAction.do?mode=getParkingReservationMonthlyList&month="+$("#pdate").val().replace("-", "").substring(0, 6));
		} else{
			alert($.parseJSON(data).resMsg);
			$.mobile.hidePageLoadingMsg();	
		}
	});	
}
//주차 예약 끝



//회의실, 장비 예약 시작
function getEquipmentListPage(){
	//alert("getEquipmentListPage:E");
	if($("#equipmentListView li").length){
		$.mobile.showPageLoadingMsg();
		$.getJSON("/action/ReservationAction.do?mode=getReservationItemList", 
				{"gubun":"E"}, function(data, status) {
					//alert("Data Loaded: " + (data));
					var listMarkup = "";
					if(data.reservationItemList.length > 0){
						$(data.reservationItemList).each(function(index, rs){
							listMarkup += "<li><a href='javascript:goReservation("+rs.resvCode+");'>";
							listMarkup += "		<h3 class='ui-li-heading'>"+rs.codeName+"</h3>";
							listMarkup += "</a></li>";
						});
					}else{
						alert("검색결과가 없습니다.");
					}
					
					$("#equipmentListView li:last").after(listMarkup);
					$("#equipmentListView").listview('refresh');
					
					$.mobile.hidePageLoadingMsg();
				});	
	}
}

function goReservation(resvCode){
	// 1회의실, 7회의실 모바일 예약 불가
	if(resvCode == 1300)
		alert("1회의실 예약불가");
	else if(resvCode == 3000)
		alert("7회의실 예약불가");
	else{
	$.mobile.changePage( "/action/ReservationAction.do?mode=getReservation&gubun="+$("#reservationGubun").val()+"&resvCode="
			+resvCode+"&sdate="+$("#selYear").val()+$("#selMonth").val()+$("#selDay").val()
			, { transition: "slide"} );}
}
function getMeetingRoomListPage(){
	//alert("getMeetingRoomListPage:E");
	if($("#meetingRoomListView li").length){
		$.mobile.showPageLoadingMsg();
		$.getJSON("/action/ReservationAction.do?mode=getReservationItemList", 
				{"gubun":"C"}, function(data, status) {
					//alert("Data Loaded: " + (data));
					var listMarkup = "";
					if(data.reservationItemList.length > 0){
						$(data.reservationItemList).each(function(index, rs){
							listMarkup += "<li><a href='javascript:goReservation("+rs.resvCode+");'>";
							listMarkup += "		<h3 class='ui-li-heading'>"+rs.codeName+"</h3>";
							listMarkup += "</a></li>";
						});
					}else{
						alert("검색결과가 없습니다.");
					}
					
					$("#meetingRoomListView li:last").after(listMarkup);
					$("#meetingRoomListView").listview('refresh');
					
					$.mobile.hidePageLoadingMsg();
				});	
	}
}

// 회의실, 장비 예약 처리
function insertReservation(){
	var stime = $('#startYear').val() + $('#startMonth').val() + $('#startDay').val() + $('#startTime').val();
	var etime = $('#endYear').val() + $('#endMonth').val() + $('#endDay').val() + $('#endTime').val();
	
	if (stime >= etime) {
		alert("시작일자가 종료일자 이전이어야 합니다.");
	} else {
		var fromDate = new Date(parseInt($('#startYear').val()),parseInt($('#startMonth').val())-1,parseInt($('#startDay').val()));
		var toDate = new Date(parseInt($('#endYear').val()),parseInt($('#endMonth').val())-1,parseInt($('#endDay').val()));
		var dateDiff = toDate.getTime() - fromDate.getTime();
		//alert(Math.floor(dateDiff / (1000 * 60 * 60 * 24)));
		if (Math.floor(dateDiff / (1000 * 60 * 60 * 24)) > 5) {
			alert("5일 이상 연속 예약은 불가합니다");
		} else {
			$.mobile.showPageLoadingMsg();
			$.post('/action/ReservationAction.do?mode=insertReservation', $('#reservationDetailForm').serialize(), function(data) {
				if($.parseJSON(data).result == "SUCCESS"){
					alert("등록되었습니다."); 
				} else{
					alert($.parseJSON(data).resMsg);
				}
				if($("#resvGubun").val() == 'E'){
					$.mobile.changePage("/m/web/reservation/equipmentList.jsp");
				}else{
					$.mobile.changePage("/m/web/reservation/meetingRoomList.jsp");
				}
				$.mobile.hidePageLoadingMsg();	
			});
		}
	}
}

//회의실, 장비 예약 취소
function removeReservation(){
	$.mobile.showPageLoadingMsg();
	$.post('/action/ReservationAction.do?mode=deleteReservation', $('#reservationViewForm').serialize(), function(data) {
		alert("삭제되었습니다.");
		if($("#resvGubun").val() == 'E'){
			$.mobile.changePage("/m/web/reservation/equipmentList.jsp");
		}else{
			$.mobile.changePage("/m/web/reservation/meetingRoomList.jsp");
		}
		$.mobile.hidePageLoadingMsg();	
	});	
}

function companySelect(){
	if($('#companyGubun').val() == "kmac"){
		$('#dept_txt').hide();
		$('#dept_sel').show();
	}else{
		$('#dept_txt').show();
		$('#dept_sel').hide();
	}
}




//기타

function parkCompanySelect(){
	if($("#park_companyGubun").val() == "KMAC"){
		$("#park_dept_sel").show();
		$("#park_dept_txt").hide();
	} else {
		$("#park_dept_txt").show();
		$("#park_dept_sel").hide();		
	}
}
function bizCompanySelect(){
	if($("#biz_companyGubun").val() == "KMAC"){
		$("#biz_dept_sel").show();
		$("#biz_dept_txt").hide();
	} else {
		$("#biz_dept_txt").show();
		$("#biz_dept_sel").hide();		
	}
}