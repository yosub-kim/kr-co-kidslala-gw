$(document).delegate("#preportListPage", "pageinit", function(){
	//alert('#preportListPage');
	$('#preportListMore').bind('click', function(){
		getPreportListPage();
	});
	getPreportListPage();
});


function getPreportListPage(){
	$.mobile.showPageLoadingMsg();
	var pg = parseInt($('#preportListPg').val()) + 1;
	//alert("getPreportListPage:" + pg);
	$.getJSON("/action/ProjectReportCabinetAction.do?mode=getMyProjectReportListForMobile&pg="+pg, "", function(data, status) {
		//alert("Data Loaded: " + (data));
		var listMarkup = "";
		if(data.preportList.length > 0){ 
			$(data.preportList).each(function(index, rs){
				listMarkup += "<li style='padding: 0px;'><a href='/action/ProjectReportContentAction.do?mode=getProjectReportForMobile&workId="+rs.id+"' data-transition='slide' rel='external'>";
				listMarkup += "	<h4 class='ui-li-heading'>";
				if(rs.level == "SANCTION_STATE_DRAFT"){listMarkup += "[작성] ";}			
				else if(rs.level == "SANCTION_STATE_REJECT_DRAFT"){listMarkup += "<font color='red'>[반려]</font> ";}
				else if(rs.level == "SANCTION_STATE_REVIEW"){listMarkup += "[검토] ";}
				else if(rs.level == "SANCTION_STATE_APPROVE"){listMarkup += "[승인] ";}
				else if(rs.level == "SANCTION_STATE_COMPLETE"){listMarkup += "[완료] ";}
				listMarkup += rs.projectName+"</h4>";
				listMarkup += "	<p class='ui-li-desc' style='font-size: 11px;'>";
				listMarkup += "		지도일:"+rs.assignDate.substring(0, 4)+"-"+rs.assignDate.substring(4, 6)+"-"+rs.assignDate.substring(6, 8)+" |";
				listMarkup += "		작성자: "+rs.draftUserName+" |";
				if(rs.level != "SANCTION_STATE_DRAFT" && rs.level != "SANCTION_STATE_REJECT_DRAFT"){					
					listMarkup += "		작성일: "+rs.draftDate.substring(0, 10)+"</p>";
				}else{
					listMarkup += "		작성일: </p>";
				}
				listMarkup += "</a></li>"; 
			});
		}else{
			alert("검색결과가 없습니다.");
		}
		$('#preportListPg').val(data.pagingPage);
		$("#preportListPageInfo").text("목록 더보기 ("+data.pagingEntries+" / "+data.totalNumberOfEntries+")");
		$("#preportListView li:last").after(listMarkup);
		$("#preportListView").listview('refresh');
		if(parseInt(data.pagingEntries) >= parseInt(data.totalNumberOfEntries)){
			$('#preportListMore').unbind();
			$('#preportListMore').bind('click', function(){
				alert("마지막 페이지 입니다.");
			});			
		}		
		$.mobile.hidePageLoadingMsg();
	});	
}
function executeProjectReport(){
	if($("#workContent").val() == ""){alert('금번진행사항을 입력하세요');return;}
	if($("#title").val() == ""){alert('제목을 입력하세요');return;}
	
	var isApprovedStr="";
	if($('#state').val() != 'writer' && $('#state').val() != 'reject'){
		if($("#appYN").val() == ""){
			alert('승인여부를 선택하세요');return;
		}
		if(!$('input[name=payAmount]').is(':checked')){
			alert('Rate를 선택하세요');return;
		}
		if($("#appYN").val() == 'Y'){
			isApprovedStr = "승인";
		}else{
			isApprovedStr = "반려";
		}	
	}else{
		isApprovedStr = "등록";		
	}
	if(!confirm(isApprovedStr + "하시겠습니까?")){return;}
	var ActionURL = "/action/ProjectReportContentAction.do?mode=executeProjectReportTask";
	$.mobile.showPageLoadingMsg();
	$.post(ActionURL, $('#preportWriteForm').serialize(), function(data) {
		alert("등록되었습니다.");
		$.mobile.hidePageLoadingMsg();	
		location.href = "/m/web/preport/preportList.jsp";
	});	
}



