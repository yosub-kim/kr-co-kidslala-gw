$(document).delegate("#customerListPage", "pageinit", function(){
	//alert('#customerListPage');
	$('#customerListMore').bind('click', function(){
		getCustomerListPage();
	});
	getCustomerListPage();
});

$(document).delegate("#customerViewPage", "pageinit", function(){
	//alert('#customerViewPage');
	getCustomerViewPage();
});

function getCustomerListPage(){
	$.mobile.showPageLoadingMsg();
	var pg = parseInt($('#customerListPg').val()) + 1;
	//alert("getCustomerListPage:" + pg);
	$.getJSON("/action/CustomerInfoMobileAction.do?mode=getCustomerInfoList&pg="+pg,
			{"customerinfo_type":$("#customerinfo_type").val(), "subject": $("#searchKey").val(), "function_type": $("#function_type").val()},
			function(data, status) {
				//alert("Data Loaded: " + (data));
				var listMarkup = ""; 
				if(data.customerInfoList.length > 0){
					$(data.customerInfoList).each(function(index, rs){
						//listMarkup += "<li style='padding: 0px;'><a href='/m/web/customer/customerList.jsp#customerViewPage'  data-transition='slide'>";
						listMarkup += "<li style='padding: 0px;'><a href='/m/web/customer/customerView.jsp?idx="+rs.idx+"' data-transition='slide'  rel='external'>";
						listMarkup += "	<h4 class='ui-li-heading'>["+rs.customerInfoName+"]"+rs.subject+"</h4>";
						listMarkup += "	<p class='ui-li-desc' style='font-size: 11px;'>"
							+rs.customerName+" | 작성자:"+rs.writer+" | 작성일:"+rs.regdates+"</p>";
						listMarkup += "</a></li>"; 
					});
				}else{
					alert("검색결과가 없습니다.");
				}
				$('#customerListPg').val(data.pagingPage);
				$("#customerListPageInfo").text("목록 더보기 ("+data.pagingEntries+" / "+data.totalNumberOfEntries+")");
				$("#customerListView li:last").after(listMarkup);
				$("#customerListView").listview('refresh');
				if(parseInt(data.pagingEntries) >= parseInt(data.totalNumberOfEntries)){
					$("#customerListPageInfo").text("목록 더보기 ("+data.pagingEntries+" / "+data.totalNumberOfEntries+")");
					$('#customerListMore').unbind();
					$('#customerListMore').bind('click', function(){ 
						alert("마지막 페이지 입니다.");
					});			
				}
				$.mobile.hidePageLoadingMsg();
	});	
}

function getCustomerListPageSearch(){
	$.mobile.showPageLoadingMsg();
	$('#customerListPg').val("0");
	var pg = parseInt($('#customerListPg').val()) + 1;
	//alert("getCustomerListPage:" + pg);
	$.getJSON("/action/CustomerInfoMobileAction.do?mode=getCustomerInfoList&pg="+pg, 
			{"customerinfo_type":$("#customerinfo_type").val(), "subject": $("#searchKey").val(), "function_type": $("#function_type").val()}, 
			function(data, status) {
				//alert("Data Loaded: " + (data));
				var listMarkup = "<li data-role='list-divider'>고객 정보</li>";
				if(data.customerInfoList.length > 0){
					$(data.customerInfoList).each(function(index, rs){
						listMarkup += "<li style='padding: 0px;'><a href='/m/web/customer/customerView.jsp?idx="+rs.idx+" ' data-transition='slide'>";
						listMarkup += "	<h4 class='ui-li-heading'>["+rs.customerInfoName+"]"+rs.subject+"</h4>";
						listMarkup += "	<p class='ui-li-desc' style='font-size: 11px;'>"
							+rs.customerName+" | 작성자:"+rs.writer+" | 작성일:"+rs.regdates+"</p>";
						listMarkup += "</a></li>"; 
					});
				}else{
					alert("검색결과가 없습니다."); 
				}
				$('#customerListPg').val(data.pagingPage);
				$("#customerListPageInfo").text("목록 더보기 ("+data.pagingEntries+" / "+data.totalNumberOfEntries+")");
				$("#customerListView").html(listMarkup);
				$("#customerListView").listview('refresh');
				if(parseInt(data.pagingEntries) >= parseInt(data.totalNumberOfEntries)){
					$("#customerListPageInfo").text("목록 더보기 ("+data.pagingEntries+" / "+data.totalNumberOfEntries+")");
					$('#customerListMore').unbind();
					$('#customerListMore').bind('click', function(){
						alert("마지막 페이지 입니다.");
					});			
				}else{
					$('#customerListMore').unbind();
					$('#customerListMore').bind('click', function(){
						getCustomerListPage();
					});				
				}
				$.mobile.hidePageLoadingMsg();
	});	
}

function getCustomerViewPage(){
	$.mobile.showPageLoadingMsg();
	var idx = $('#customerIdx').val();
	var agent = encodeURI(navigator.userAgent);
	//alert(encodeURI(navigator.userAgent));
	$.getJSON("/action/CustomerInfoMobileAction.do?mode=getCustomerInfoDetail&idx="+idx+"&agent="+agent, "", function(data, status) {
		//alert("Data Loaded: " + (data));
		//정보유형
		$("#customerView_customerInfoName").text(data.customerInfoDetail.customerInfoName);
		//제목
		$("#customerView_subject").text(data.customerInfoDetail.subject);
		//입찰금액
		//$("#customerView_").text(data.customerInfoDetail.customerInfoName);
		//고객사
		$("#customerView_customerName").text(data.customerInfoDetail.customerName);
		//산업별구분
		$("#customerView_industryTypeName").text(data.customerInfoDetail.industryTypeName);
		//비즈니스타입
		$("#customerView_businessTypeName").text(data.customerInfoDetail.businessTypeName);
		//주요상담영역
		$("#customerView_refer_dept").text(data.customerInfoDetail.refer_dept);
		//작성자
		$("#customerView_writer").text(data.customerInfoDetail.writer);
		//Function
		$("#customerView_writerDeptName").text(data.customerInfoDetail.writerDeptName);
		//정보수집자
		$("#customerView_embbsGather").text(data.customerInfoDetail.embbsGather);
		//정보수집일
		$("#customerView_info_date").text(data.customerInfoDetail.info_date);
		//정보제공자
		$("#customerView_embbsName").text(data.customerInfoDetail.embbsName 
											+ " " + data.customerInfoDetail.embbsStatus 
											+ "("+data.customerInfoDetail.embbsDept+")");
		//제공자전화번호
		$("#customerView_embbsPhone").text(data.customerInfoDetail.embbsPhone);
		//제공자Email
		$("#customerView_embbsEmail").text(data.customerInfoDetail.embbsEmail);
		//정보수집방법
		$("#customerView_embbsMethod").text(data.customerInfoDetail.embbsMethod);
		//정보내용
		$("#customerView_content").html(data.customerInfoDetail.content);
		//제출자의견
		$("#customerView_opinion").html(data.customerInfoDetail.opinion);
	
		$.mobile.hidePageLoadingMsg();	
	});	
}

function getCustomerCompanySearcgList(){
	$.mobile.showPageLoadingMsg();
	//alert("getCustomerListPage:" + pg);
	$.getJSON("/action/CustomerInfoMobileAction.do?mode=getCustomerCompanySearch", 
			{"search_cpmpanyName": $("#search_cpmpanyName").val()}, 
			function(data, status) {
				//alert("Data Loaded: " + (data));
				var listMarkup = ""; 
				if(data.customerCompanyList.length > 0){
					$(data.customerCompanyList).each(function(index, rs){
						listMarkup += "<li style='padding: 0px;'><a href='javascript:insertCustomerCompany("+index+")' id='insertCustomerCompany"+index+"' " 
										+" com_name='"+rs.com_name+"' com_code='"+rs.comcode+"' > ";
						listMarkup += "	<h4 class='ui-li-heading'>"+rs.com_name+"</h4>";
						listMarkup += "	<p class='ui-li-desc' style='font-size: 11px;'>	사업자번호:" +rs.comcode+" | 분류:"+rs.com_sect+"</p>";
						listMarkup += "</a></li>"; 
					});
				}else{
					alert("검색결과가 없습니다.");
				}
				$("#customerCompanyPopupListView").html(listMarkup);
				$("#customerCompanyPopupListView").listview('refresh');
				$.mobile.hidePageLoadingMsg();
	});	
}

function getCustomerPersonSearcgList(){
	$.mobile.showPageLoadingMsg();
	//alert("getCustomerListPage:" + pg);
	$.getJSON("/action/CustomerInfoMobileAction.do?mode=getCustomerPersonSearch", 
			{"search_customerName":$("#search_customerName").val()}, 
			function(data, status) {
				//alert("Data Loaded: " + (data));
				var listMarkup = ""; 
				if(data.customerPersonList.length > 0){
					$(data.customerPersonList).each(function(index, rs){
						listMarkup += "<li style='padding: 0px;'><a href='javascript:insertCustomerPerson("+index+")' id='insertCustomerPerson"+index+"'  "
										+"	aidx='"+rs.aidx+"' p_name='"+rs.p_name+"' p_part='"+rs.p_part+"' p_status='"+rs.p_status+"' p_tel='"+rs.p_tel1+"' p_email='"+rs.p_email+"' >";
						listMarkup += "	<h4 class='ui-li-heading'>"+ rs.p_name +" ["+rs.p_company+"]</h4>";
						listMarkup += "	<p class='ui-li-desc' style='font-size: 11px;'>	부서: "+rs.p_part+" | 등록자:"+rs.writer+" | 작성일:"+rs.updatedate+"</p>";
						listMarkup += "</a></li>"; 
					});
				}else{
					alert("검색결과가 없습니다.");
				}
				$("#customerPersonPopupListView").html(listMarkup);
				$("#customerPersonPopupListView").listview('refresh');
				$.mobile.hidePageLoadingMsg();
	});	
}

function getCustomerCollectorSearchList(){
	$.mobile.showPageLoadingMsg();
	//alert("getCustomerListPage:" + pg);
	$.getJSON("/action/CustomerInfoMobileAction.do?mode=getCustomerCollectorSearch", 
			{"search_collectorName":$("#search_collectorName").val()}, 
			function(data, status) {
				//alert("Data Loaded: " + (data));
				var listMarkup = ""; 
				if(data.customerPersonList.length > 0){
					$(data.customerPersonList).each(function(index, rs){
						listMarkup += "<li style='padding: 0px;'><a href='javascript:insertCustomerCollector("+index+")' id='customerCollectorList"+index+"'  "
										+"	p_ssn='"+rs.ssn+"' p_name='"+rs.name+"' p_dept='"+rs.dept+"' p_email='"+rs.email+"' >";
						listMarkup += "	<h4 class='ui-li-heading'>"+ rs.name + " | " + rs.dept + " | " + rs.email + "</h4>";
						listMarkup += "</a></li>"; 
					});
				}else{
					alert("검색결과가 없습니다.");
				}
				$("#customerCollectorPopupListView").html(listMarkup);
				$("#customerCollectorPopupListView").listview('refresh');
				$.mobile.hidePageLoadingMsg();
	});	
}

function insertCustomerCollector(obj){	
	var str  = '<span data-role="button" data-icon="delete" style="width: 130px;" data-inline="true" onclick="$(this).remove()">'+$("#customerCollectorList"+obj).attr("p_name");
		str += '<input type="hidden" name="picker_ssn" value="'+$("#customerCollectorList"+obj).attr("p_ssn")+'"/>'+'</span>';
	$("#customerWrite_embbsGather_dev").html($("#customerWrite_embbsGather_dev").html()+str)
	$("#customerWrite_embbsGather_dev").trigger('create');
	history.go(-1);
}

function insertCustomerCompany(obj){
	$("#customerWrite_customerName").val($("#insertCustomerCompany"+obj).attr("com_name"));
	$("#customerWrite_customerCode").val($("#insertCustomerCompany"+obj).attr("com_code"));
	history.go(-1);
}

function insertCustomerPerson(obj){
	$("#customerWrite_embbs_idx").val($("#insertCustomerPerson"+obj).attr("aidx"));
	$("#customerWrite_embbsName").val($("#insertCustomerPerson"+obj).attr("p_name"));
	$("#customerWrite_embbsDept").val($("#insertCustomerPerson"+obj).attr("p_part"));
	$("#customerWrite_embbsStatus").val($("#insertCustomerPerson"+obj).attr("p_status"));
	$("#customerWrite_embbsPhone").val($("#insertCustomerPerson"+obj).attr("p_tel"));	
	$("#customerWrite_embbsEmail").val($("#insertCustomerPerson"+obj).attr("p_email"));
	//alert($("insertCustomerPerson"+obj).aidx	+":"+$("insertCustomerPerson"+obj).p_name+":"
	//			+$("insertCustomerPerson"+obj).p_part+":"+$("insertCustomerPerson"+obj).p_status+":"
	//			+$("insertCustomerPerson"+obj).p_tel+":"+$("insertCustomerPerson"+obj).p_email+":");
	history.go(-1);
}

function saveCustomerInfo(){
	var today = dateToYYYYMMDD(new Date());
	var infoDate = $("#info_dateYyyy").val() + "-" + $("#info_dateMm").val() + "-" + $("#info_dateDd").val(); 
	if($("#customerWrite_subject").val() == ''){alert("제목을 입력하세요");return false;	}
	if($('input[name=businessTypeCodes]:checked').length <= 0){alert("비즈니스타입을 선택하세요");return false;	}
	if (getDateDiff(today, infoDate) < -3) {
		alert("오늘로부터 3일 이전의 정보수집일을 선택할 수 없습니다.");
		return;
	}

	if($("#customerWrite_embbsGather_dev").length < 1){alert("정보수집자를 입력하세요");return false;	}
	if($("#customerWrite_customerName").val() == ''){alert("고객사를 입력하세요");return false;	}
	if($("#customerWrite_embbsName").val() == ''){alert("정보제공자를 입력하세요");return false;	}
	if($("#customerWrite_embbsMethod").val() == ''){alert("정보수집방법을 입력하세요");return false;	}
	if($("#customerWrite_content").val() == ''){alert("정보내용을 입력하세요");return false;	}
	
	$.mobile.showPageLoadingMsg();
	$.post('/action/CustomerInfoMobileAction.do?mode=saveCustomerInfo', $('#customerInfoForm').serialize(), function(data) {
		alert("등록되었습니다.");
		$.mobile.hidePageLoadingMsg();	
		//alert(bbsId);
		location.href = "/m/web/customer/customerList.jsp";
	});	
}
function dateToYYYYMMDD(date){
    function pad(num) {
        num = num + '';
        return num.length < 2 ? '0' + num : num;
    }
    return date.getFullYear() + '-' + pad(date.getMonth()+1) + '-' + pad(date.getDate());
}
function getDateDiff(sDate, eDate) {
	var arr1 = sDate.split('-');
	var arr2 = eDate.split('-');
	var dat1 = new Date(arr1[0], arr1[1], arr1[2]);
	var dat2 = new Date(arr2[0], arr2[1], arr2[2]);

	var diff = dat2 - dat1;
	var currDay = 24 * 60 * 60 * 1000;// 시 * 분 * 초 * 밀리세컨
	var currMonth = currDay * 30;// 월 만듬
	var currYear = currMonth * 12; // 년 만듬

	return parseInt(diff/currDay);	
}





