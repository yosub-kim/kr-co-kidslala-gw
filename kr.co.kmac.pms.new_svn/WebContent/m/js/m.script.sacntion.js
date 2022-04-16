//##########################################################################################
//새 업무 카운트 가져오기
//##########################################################################################
$(document).delegate("#sanctionIndexPage", "pageinit", function(){
	$.mobile.showPageLoadingMsg();
	//alert('#sanctionIndexPage');
	$.getJSON("/action/WorkCabinetAction.do?mode=countMyWorkList", function(data){
		//alert("Data Loaded: " + data);
		$("#sanctionIndexMyWorkCnt").text(data.workCount);
		$.mobile.hidePageLoadingMsg();
	});
});

//##########################################################################################
// 전자결재 현황
//##########################################################################################
$(document).delegate("#sanctionStateListPage", "pageinit", function(){
	//alert('#sanctionStateListPage');
	$('#sanctionStateListMore').bind('click', function(){
		getSanctionStateListPage();
	});
	getSanctionStateListPage();
});


function getSanctionStateListPage(){
	$.mobile.showPageLoadingMsg();
	var pg = parseInt($('#sanctionStateListPg').val()) + 1;
	//alert("getSanctionStateListPage:" + pg);
	$.getJSON("/action/SanctiontPresentStateAction.do?mode=getAllSanctiontPresentStateForMobile&pg="+pg, "", function(data, status) {
		//alert("Data Loaded: " + (data));
		var listMarkup = "";
		if(data.sanctionStateList.length > 0){
			$(data.sanctionStateList).each(function(index, rs){
				/*//alert(rs.useMobile);
				if( rs.workType == "S018809e0a4c4436010a4ead57e4001f"){
					listMarkup += "<li style='padding: 0px;'><a href='"+rs.workTypeUrl+"ForMobile&&readonly=true&projectCode="+rs.projectCode+"&approvalType="+rs.approvalType+"&seq="+rs.seq+"' data-transition='slide'>";
				} else if( rs.workType == "S028809e0a4c4436010a4ead57e4001f"){//실행품의	
					listMarkup += "<li style='padding: 0px;'><a href='"+rs.workTypeUrl+"ForMobile&&readonly=true&projectCode="+rs.projectCode+"&approvalType="+rs.approvalType+"&seq="+rs.seq+"' data-transition='slide'>";
				} else if( rs.workType == "S038809e0a4c4436010a4ead57e4001f"){//운영품의	
					listMarkup += "<li style='padding: 0px;'><a href='"+rs.workTypeUrl+"ForMobile&&readonly=true&projectCode="+rs.projectCode+"&approvalType="+rs.approvalType+"&seq="+rs.seq+"' data-transition='slide'>";
				} else if( rs.workType == "S068809e0a4c4436010a4ead57e4001f"){//취소품의	
					listMarkup += "<li style='padding: 0px;'><a href='"+rs.workTypeUrl+"ForMobile&&readonly=true&projectCode="+rs.projectCode+"&approvalType="+rs.approvalType+"&seq="+rs.seq+"' data-transition='slide'>";
				} else {
					listMarkup += "<li style='padding: 0px;'><a href='javascript:mobileTaskExcetion()' data-transition='slide'>";
				}
				*/
				var cssType = "";
				if (rs.useMobile == "1") {
					listMarkup += "<li style='padding: 0px;'><a href='"+rs.workTypeUrl+"ForMobile&&readonly=true&projectCode="+rs.projectCode+"&approvalType="+rs.approvalType+"&seq="+rs.seq+"' data-transition='slide' rel='external'>";
					cssType = "ui-li-heading-green";
				} else {
					listMarkup += "<li style='padding: 0px;'><a href='javascript:mobileTaskExcetion()' data-transition='slide' rel='external'>";
					cssType = "ui-li-heading-red";
				}
				listMarkup += "		<h4 class='"+cssType+"'>["+rs.approvalTypeName+"]"+rs.projectName+"</h4>";
				var style = "";
				if(rs.present == "반려"){style = "color='red'";}else if(rs.present == "종료"){style = "color='blue'";}
				listMarkup += "		<p class='ui-li-desc' style='font-size: 11px;'>상태:  <font	"+style+">"+rs.present+"</font>&nbsp;|&nbsp;";
				listMarkup += "													기안자: "+rs.registerName+"&nbsp;|&nbsp;";
				listMarkup += "													기안일: "+rs.registerDate.substring(0, 10)+"</p>";
				listMarkup += "</a></li>";
			});
		}else{
			alert("검색결과가 없습니다.");
		}
		$('#sanctionStateListPg').val(data.pagingPage);
		$("#sanctionStateListPageInfo").text("목록 더보기 ("+data.pagingEntries+" / "+data.totalNumberOfEntries+")");
		$("#sanctionStateListView li:last").after(listMarkup);
		$("#sanctionStateListView").listview('refresh');
		if(parseInt(data.pagingEntries) >= parseInt(data.totalNumberOfEntries)){
			$('#sanctionStateListMore').unbind();
			$('#sanctionStateListMore').bind('click', function(){
				alert("마지막 페이지 입니다.");
			});			
		}
		$.mobile.hidePageLoadingMsg();
	});	
}

function mobileTaskExcetion(){
	alert("선택하신 결재건은 모바일 버전을 지원하지 않으므로 PC버전으로 이동됩니다.");	
}


//##########################################################################################
//새 업무함
//##########################################################################################
$(document).delegate("#sanctionCabinetListPage", "pageinit", function(){
	//alert('#sanctionCabinetListPage');
	$('#sanctionCabinetListMore').bind('click', function(){
		getSanctionCabinetListPage();
	});
	getSanctionCabinetListPage();
});


function getSanctionCabinetListPage(){
	$.mobile.showPageLoadingMsg();
	var pg = parseInt($('#sanctionCabinetListPg').val()) + 1;
	//alert("getSanctionCabinetListPage:" + pg);
	$.getJSON("/action/WorkCabinetAction.do?mode=getMyWorkListForMobile&pg="+pg, "", function(data, status) {
		//alert("Data Loaded: " + (data));
		var listMarkup = "";
		var cssType = "";
		if(data.sanctionCabinetList.length > 0){
			$(data.sanctionCabinetList).each(function(index, rs){
				listMarkup += "<li style='padding: 0px;'><a href='javascript:selectWork(\""+rs.id+"\", \""+rs.type+"\")' data-transition='slide' rel='external'>";
				if(rs.useMobile == "1") {cssType = "ui-li-heading-green";} else {cssType = "ui-li-heading-red";} // 모바일 결재 가능 여부 이미지 표시
				listMarkup += "		<h4 class='"+cssType+"'>["+rs.approvalName+"]"+rs.title+"</h4>";
				var sctnState = "";
				if(rs.level == "SANCTION_STATE_DRAFT"){sctnState = "작성";
				}else if(rs.level == "SANCTION_STATE_REJECT_DRAFT"){sctnState = "<font color='red'>반려</font>";
				}else if(rs.level == "SANCTION_STATE_REVIEW"){sctnState = "검토";
				}else if(rs.level == "SANCTION_STATE_APPROVE"){sctnState = "승인";
				}else if(rs.level == "SANCTION_STATE_CHECK"){sctnState = "확인";
				}else if(rs.level == "SANCTION_STATE_ASSIST1"){sctnState = "협의1";
				}else if(rs.level == "SANCTION_STATE_ASSIST2"){sctnState = "협의2";
				}else if(rs.level == "SANCTION_STATE_ASSIST3"){sctnState = "협의3";
				}else if(rs.level == "SANCTION_STATE_SUPPORT_DRAFT"){sctnState = "지원실 기안";
				}else if(rs.level == "SANCTION_STATE_SUPPORT_REVIEW"){sctnState = "지원실 검토";
				}else if(rs.level == "SANCTION_STATE_SUPPORT_APPROVE"){sctnState = "지원실 승인";
				}else if(rs.level == "SANCTION_STATE_CEO"){sctnState = "대표이사";
				}else if(rs.level == "SANCTION_STATE_COMPLETE"){sctnState = "완료";
				}else if(rs.level == "SANCTION_STATE_RATING"){sctnState = "작성";
				}else if(rs.level == "SANCTION_STATE_ENDRIVIEW_DRAFT"){sctnState = "작성";
				}else if(rs.level == "SANCTION_STATE_ENDRIVIEW_RIVIEW"){sctnState = "검토";
				}else if(rs.level == "SANCTION_STATE_ENDRIVIEW_APPROVE"){sctnState = "승인";
				}else if(rs.level == "SANCTION_STATE_ENDRIVIEW_VERIFICATE"){sctnState = "검증";
				}
				listMarkup += "		<p class='ui-li-desc' style='font-size: 11px;'>상태: "+sctnState+"&nbsp;|&nbsp;";
				listMarkup += "														기안자: "+rs.draftUserName+"&nbsp;|&nbsp;";
				listMarkup += "														기안일: "+rs.draftDate.substring(0, 10)+"</p>";
				listMarkup += "</a></li>";
			});
		}else{
			alert("검색결과가 없습니다.");
		}
		$('#sanctionCabinetListPg').val(data.pagingPage);
		$("#sanctionCabinetListPageInfo").text("목록 더보기 ("+data.pagingEntries+" / "+data.totalNumberOfEntries+")");
		$("#sanctionCabinetListView li:last").after(listMarkup);
		$("#sanctionCabinetListView").listview('refresh');
		if(parseInt(data.pagingEntries) >= parseInt(data.totalNumberOfEntries)){
			$('#sanctionCabinetListMore').unbind();
			$('#sanctionCabinetListMore').bind('click', function(){
				alert("마지막 페이지 입니다.");
			});			
		}		
		$.mobile.hidePageLoadingMsg();
	});	
}

function checkApprovalType() {
	if($("#approvalTypeSelect").val() == ""){
		alert('결재 유형을 선택하고 진행하세요');
	}
}

function selectWork(workId, type) {
	var ActionURL = "/action/WorkCabinetAction.do?mode=getWorkType";
	$.getJSON(ActionURL, { "workTypeId": type }, function(data, status) {
		if (data.workType.useMobile == "1") {
			document.location.href = data.workType.formUrl + "ForMobile&workId="+workId;
		} else {
			mobileTaskExcetion();
			document.location.href = data.workType.formUrl + "&workId="+workId;
		}
	});
}

function executeSanction(){
	var ActionURL = "";
	if($("#subject").val() == ""){alert('제목을 입력하세요');return;}
	if($("#content").val() == ""){alert('내용을 입력하세요');return;}
	if($('#state').val() == "SANCTION_STATE_DRAFT"){
		if($('registerSsn') == null || $('registerSsn').value == ''){alert("기안자 정보가 없습니다.");	return;}
		if($('cioSsn') != null && $('cioSsn').value == ''){
			if(!confirm("승인자 정보가 없습니다.\n계속하시겠습니까?")){return;}
		}
		$('#isApproved').val('Y');
		ActionURL = "/action/GeneralSanctionAction.do?mode=createGeneralSanction";
	}else{
		ActionURL = "/action/GeneralSanctionAction.do?mode=executeGeneralSanction";
	}	
	var isApprovedStr="";
	if(document.sanctionDraftForm.isApproved.value == 'Y'){
		if($('#state').val() == "SANCTION_STATE_DRAFT" || $('#state').val() == "SANCTION_STATE_REJECT_DRAFT"){
			isApprovedStr = "기안";
		} else {
			isApprovedStr = "승인";
		}
	}else if($("#isApproved_2").val() == 'N'){
		isApprovedStr = "반려";
	}
	if(!confirm(isApprovedStr + "하시겠습니까?")){return;}
	$.post(ActionURL, $("#sanctionDraftForm").serialize(), function(data) {
		alert('등록하였습니다.');
		location.href = "/action/WorkCabinetAction.do?mode=getMyWorkListForMobile";
	});	
}