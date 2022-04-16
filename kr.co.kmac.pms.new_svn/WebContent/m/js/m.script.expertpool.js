$(document).delegate("#expertpoolListPage", "pageinit", function(){
	//alert('#expertpoolListPage');
	$('#expertpoolListMore').bind('click', function(){
		getExpertpoolListPage();
	});
	getExpertpoolListPage();
});


function getExpertpoolListPage(){
	$.mobile.showPageLoadingMsg();
	var pg = parseInt($('#expertpoolListPg').val()) + 1;
	//alert("getExpertpoolListPage:" + pg);
	$.getJSON("/action/ExpertPoolManagerAction.do?mode=getExpertPoolExtListForMobile&pg="+pg, {"keyword":$("#expertpoolSearchSubject").val(), "jobClass":$("#expertpoolSearchJobClass").val()}, function(data, status) {
		//alert("Data Loaded: " + (data));
		var listMarkup = "";
		if(data.expertpoolList.length > 0){
			$(data.expertpoolList).each(function(index, rs){
				listMarkup += "<li><a href='/action/ExpertPoolManagerAction.do?mode=infoviewForMobile&ssn="+rs.ssn+"' data-transition='slide' rel='external'>";
				if(rs.photo == ""){
					listMarkup += "<img width=67 height=77 src='/images/im_noimage.jpg' 'class='ui-li-thumb'>";					
				}else{
					listMarkup += "<img width=67 height=77 src='http://newpms.kmac.co.kr/servlet/PhotoDownLoadServlet?fileId=" + rs.photo +"' class='ui-li-thumb'>";
				}
				listMarkup += "		<h3 class='ui-li-heading'>["+rs.company+"] "+rs.name+"</h3>";
				listMarkup += "		<p class='ui-li-desc'>"+rs.mobileNo+" &nbsp;&nbsp;|&nbsp;&nbsp; "+rs.email+"</p>";
				listMarkup += "</a></li>";
			});
		}else{
			alert("검색결과가 없습니다.");
		}
		$('#expertpoolListPg').val(data.pagingPage);
		$("#expertpoolListPageInfo").text("목록 더보기 ("+data.pagingEntries+" / "+data.totalNumberOfEntries+")");
		$("#expertpoolListView li:last").after(listMarkup);
		$("#expertpoolListView").listview('refresh');
		if(parseInt(data.pagingEntries) >= parseInt(data.totalNumberOfEntries)){
			$('#expertpoolListMore').unbind();
			$('#expertpoolListMore').bind('click', function(){
				alert("마지막 페이지 입니다.");
			});			
		}
		$.mobile.hidePageLoadingMsg();
	});	
}


function getExpertpoolListPageSearch(){
	$.mobile.showPageLoadingMsg();
	$('#expertpoolListPg').val("0");
	var pg = parseInt($('#expertpoolListPg').val()) + 1;
	//alert("getExpertpoolListPage:" + pg);
	$.getJSON("/action/ExpertPoolManagerAction.do?mode=getExpertPoolExtListForMobile&pg="+pg, {"keyword":$("#expertpoolSearchSubject").val(), "jobClass":$("#expertpoolSearchJobClass").val() }, function(data, status) {
		//alert("Data Loaded: " + (data));
		var listMarkup = "<li data-role='list-divider'>전문가 정보</li>";
		if(data.expertpoolList.length > 0){
			$(data.expertpoolList).each(function(index, rs){
				listMarkup += "<li><a href='/action/ExpertPoolManagerAction.do?mode=infoviewForMobile&ssn="+rs.ssn+"' data-transition='slide' rel='external'>";
				if(rs.photo == ""){
					listMarkup += "<img width=67 height=77 src='/images/im_noimage.jpg' 'class='ui-li-thumb'>";					
				}else{
					listMarkup += "<img width=67 height=77 src='http://newpms.kmac.co.kr/servlet/PhotoDownLoadServlet?fileId=" + rs.photo +"' class='ui-li-thumb'>";
				}
				listMarkup += "		<h3 class='ui-li-heading'>["+rs.company+"] "+rs.name+"</h3>";
				listMarkup += "		<p class='ui-li-desc'>"+rs.mobileNo+" &nbsp;&nbsp;|&nbsp;&nbsp; "+rs.email+"</p>";
				listMarkup += "</a></li>";
			});
		}else{
			alert("검색결과가 없습니다.");
		}
		$('#expertpoolListPg').val(data.pagingPage);
		$("#expertpoolListPageInfo").text("목록 더보기 ("+data.pagingEntries+" / "+data.totalNumberOfEntries+")");
		$("#expertpoolListView").html(listMarkup);
		$("#expertpoolListView").listview('refresh');
		if(parseInt(data.pagingEntries) >= parseInt(data.totalNumberOfEntries)){
			$('#expertpoolListMore').unbind();
			$('#expertpoolListMore').bind('click', function(){
				alert("마지막 페이지 입니다.");
			});			
		}else{
			$('#expertpoolListMore').unbind();
			$('#expertpoolListMore').bind('click', function(){
				getExpertpoolListPage();
			});			
		}
		$.mobile.hidePageLoadingMsg();
	});	
}



$(document).delegate("#expertpoolViewPage", "pageinit", function(){
	//alert("expertpoolViewPage");		
});