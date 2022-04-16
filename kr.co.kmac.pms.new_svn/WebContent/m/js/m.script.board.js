//##########################################################################################
//게시판 관련
//##########################################################################################
/*$(document).delegate("#boardListPage", "pageinit", function(){
	//alert('#boardListPage');
	$('#boardListMore').bind('click', function(){
		getBoardListPage();
	});
	getBoardListPage();
});
*/
/*
function getBoardListPage(){
	var pg = parseInt($('#boardListPg').val()) + 1;
	var bbsId = $('#boardListId').val();
	$.getJSON("/action/MobileBoardAction.do?mode=selectBoardList&bbsId="
			+bbsId+"&pg="+pg, {"subject":$("#boardSearchSubject").val()}, function(data, status) {
		var listMarkup = "";
		$("#boardListTitle").text(data.codeEntity.data1);
		if(data.boardList.length > 0){
			$(data.boardList).each(function(index, rs){
				var temp = "";
				if(rs.lev > 0){
					for( var i=0; (rs.lev-1) >= i; i++){
						temp += "&nbsp;&nbsp;&nbsp;";
					}
					temp += "&nbsp;&nbsp;&nbsp;└";
				}
			    listMarkup += "<table class='tbl list'>";							
			    listMarkup += "<tbody>";		
				listMarkup += "<tr>";
				listMarkup += "<td>";
				listMarkup += "<a href='/action/MobileBoardAction.do?mode=boardView&bbsId="+data.codeEntity.key1+"&seq="+rs.seq+"' data-transition='slide' rel='external'>";
				listMarkup += "  <div><div>";
				listMarkup += "		<p class='subject new'>"+temp+" "+rs.subjectNoTag+"</p>";
				listMarkup += "		<ul class='info'>";
				listMarkup += "			<li><i class='mdi mdi-clock-outline'></i><p>"+rs.wtime.substring(0, 10)+"</p></li>";
				listMarkup += "			<li><i class='mdi mdi-account'></i><p>"+rs.writer+"</p></li>";
				listMarkup += "			<li><i class='mdi mdi-eye'></i><p>"+rs.readCnt+"</p></li>";
				listMarkup += "		</ul>";
				listMarkup += "	  </div></div>";
				listMarkup += "</a>";
				listMarkup += "</td>";
				listMarkup += "</tr>";
				listMarkup += "</tbody>";							
			    listMarkup += "</table>";
			    listMarkup += "<li></li>"
			});
		}else{}			
		
		$('#boardListPg').val(data.pagingPage);
		if(data.pagingEntries == 0){
			$("#boardListPageInfo").text(" (10)");
		}else{
			$("#boardListPageInfo").text(" ("+data.pagingEntries+" / "+data.totalNumberOfEntries+")");
			
		}
		$("#boardListView li:last").after(listMarkup);
		if(data.pagingEntries == data.totalNumberOfEntries){
			$('#boardListMore').unbind();
			$('#boardListMore').bind('click', function(){
				$('#boardListMore').unbind();
				getBoardListPage();
			});			
		}				
	});
} 
*/
/*
function getBoardListPage(){
	//$.mobile.showPageLoadingMsg();
	var pg = parseInt($('#boardListPg').val()) + 1;
	var bbsId = $('#boardListId').val();
	//alert("getBoardListPage:" + pg+":"+bbsId);
	$.getJSON("/action/MobileBoardAction.do?mode=selectBoardList&bbsId="
			+bbsId+"&pg="+pg, {"subject":$("#boardSearchSubject").val()}, function(data, status) {
		//alert("Data Loaded: " + (data));
		var listMarkup = "";
		if(bbsId == "thanks"){
			$("#boardListTitle").text("감사합니다.");
			if(data.boardList.length > 0){
				$(data.boardList).each(function(index, rs){
					var temp = "";
					if(rs.refLevel != 0){
						temp += "&nbsp;&nbsp;&nbsp;└";
					}
					listMarkup += "<li style='padding: 0px;'><a href='/action/MobileBoardAction.do?mode=thxBoardView&idx="+rs.idx+"' data-transition='slide' rel='external'>";
					listMarkup += "		<h4 class='ui-li-heading'>"+temp+" "+rs.subject+"</h4>";
					listMarkup += "		<p class='ui-li-desc' style='font-size: 11px;'>";
					listMarkup += "			감사대상: "+rs.thanksName+" | 추천수: "+rs.likeCnt+" | 등록일: "+rs.writeDate.substring(0, 10); 
					listMarkup += "		</p>";
					if(rs.commentCnt != '0' && rs.commentCnt != ''){
					listMarkup += "		<span class='ui-li-count ui-btn-up-c ui-btn-corner-all'>"+rs.commentCount+"</span>";
					}
					listMarkup += "</a></li>";
				});
			}else{
				alert("검색결과가 없습니다.");
			}					
		} else if(bbsId == "businessplan"){
			$("#boardListTitle").text(data.codeEntity.data1);
			if(data.boardList.length > 0){
				$(data.boardList).each(function(index, rs){
					var temp = "";
					if(rs.lev > 0){
						for( var i=0; (rs.lev-1) >= i; i++){
							temp += "&nbsp;&nbsp;&nbsp;";
						}
						temp += "&nbsp;&nbsp;&nbsp;└";
					}
					listMarkup += "<li style='padding: 0px;'><a href='/action/MobileBoardAction.do?mode=boardView&bbsId="+data.codeEntity.key1+"&seq="+rs.seq+"' data-transition='slide' rel='external'>";
					listMarkup += "		<h4 class='ui-li-heading'>"+temp+" "+rs.subjectNoTag+"</h4>";
					listMarkup += "		<p class='ui-li-desc' style='font-size: 11px;'>";
					listMarkup += "		</p>";
					listMarkup += "</a></li>";
				});
			}else{
				alert("검색결과가 없습니다.");
			}			
		}else{
			$("#boardListTitle").text(data.codeEntity.data1);
			if(data.boardList.length > 0){
				$(data.boardList).each(function(index, rs){
					var temp = "";
					if(rs.lev > 0){
						for( var i=0; (rs.lev-1) >= i; i++){
							temp += "&nbsp;&nbsp;&nbsp;";
						}
						temp += "&nbsp;&nbsp;&nbsp;└";
					}
				    listMarkup += "<table class='tbl list'>";							
				    listMarkup += "<tbody>";		
					listMarkup += "<tr>";
					listMarkup += "<td>";
					listMarkup += "<a href='/action/MobileBoardAction.do?mode=boardView&bbsId="+data.codeEntity.key1+"&seq="+rs.seq+"' data-transition='slide' rel='external'>";
					listMarkup += "  <div><div>";
					listMarkup += "		<p class='subject new'>"+temp+" "+rs.subjectNoTag+"</p>";
					listMarkup += "		<ul class='info'>";
					listMarkup += "			<li><i class='mdi mdi-clock-outline'></i><p>"+rs.wtime.substring(0, 10)+"</p></li>";
					listMarkup += "			<li><i class='mdi mdi-account'></i><p>"+rs.writer+"</p></li>";
					listMarkup += "			<li><i class='mdi mdi-eye'></i><p>"+rs.readCnt+"</p></li>";
					listMarkup += "		</ul>";
					listMarkup += "	  </div></div>";
					listMarkup += "</a>";
					listMarkup += "</td>";
					listMarkup += "</tr>";
					listMarkup += "</tbody>";							
				    listMarkup += "</table>";
				    listMarkup += "<li></li>"
				});
			}else{
				alert("검색결과가 없습니다.");
			}			
		}
		$('#boardListPg').val(data.pagingPage);
		$("#boardListPageInfo").text("더 보기 + ("+data.pagingEntries+" / "+data.totalNumberOfEntries+")");
		$("#boardListView li:last").after(listMarkup);
		$("#boardListView").listview('refresh');
		if(data.pagingEntries == data.totalNumberOfEntries){
			$('#boardListMore').unbind();
			$('#boardListMore').bind('click', function(){
				$('#boardListMore').unbind();
				getBoardListPage();
			});			
		}				
		//$.mobile.hidePageLoadingMsg();
	});
}
*/ 

function getBoardListPageSearch(){
	$('#boardListPg').val("0");
	var pg = parseInt($('#boardListPg').val()) + 1; 
	var bbsId = $('#boardListId').val();
	//alert("getBoardListPage:" + pg+":"+bbsId); 
	$.getJSON("/action/MobileBoardAction.do?mode=selectBoardList&bbsId="+bbsId+"&pg="+pg, {"keyfield":$("#keyfield").val(), "subject":$("#boardSearchSubject").val()}, function(data, status) {
		//alert("Data Loaded: " + (data));
		var listMarkup = "<li data-role='list-divider' id='boardListTitle' style=''></li>";
		
		if(bbsId == "thanks"){
			if(data.boardList.length > 0){
				$(data.boardList).each(function(index, rs){
					var temp = "";
					if(rs.refLevel != 0){
						temp += "&nbsp;&nbsp;&nbsp;└";
					}
					listMarkup += "<li style='padding: 0px;'><a href='/action/MobileBoardAction.do?mode=thxBoardView&idx="+rs.idx+"' data-transition='slide' rel='external'>";
					listMarkup += "		<h4 class='ui-li-heading'>"+temp+" "+rs.subject+"</h4>";
					listMarkup += "		<p class='ui-li-desc' style='font-size: 11px;'>";
					listMarkup += "			감사대상: "+rs.thanksName+" | 추천수: "+rs.likeCnt+" | 등록일: "+rs.writeDate.substring(0, 10); 
					listMarkup += "		</p>";
					if(rs.commentCnt != '0' && rs.commentCnt != ''){
					listMarkup += "		<span class='ui-li-count ui-btn-up-c ui-btn-corner-all'>"+rs.commentCount+"</span>";
					}
					listMarkup += "</a></li>";
				});
			}else{
				alert("검색결과가 없습니다.");
			}					
		}else{
			if(data.boardList.length > 0){
				$(data.boardList).each(function(index, rs){
					var temp = "";
					if(rs.lev > 0){
						for( var i=0; (rs.lev-1) >= i; i++){
							temp += "&nbsp;&nbsp;&nbsp;";
						}
						temp += "&nbsp;&nbsp;&nbsp;└";
					}
					listMarkup += "<table class='tbl list'>";
				    listMarkup += "<tbody>";		
					listMarkup += "<tr>";
					listMarkup += "<td>";
					listMarkup += "<a href='/action/MobileBoardAction.do?mode=boardView&bbsId="+data.codeEntity.key1+"&seq="+rs.seq+"' data-transition='slide' rel='external'>";
					listMarkup += "  <div><div>";
					listMarkup += "		<p class='subject new'>"+temp+" "+rs.subjectNoTag+"</p>";
					listMarkup += "		<ul class='info'>";
					listMarkup += "			<li><i class='mdi mdi-clock-outline'></i><p>"+rs.wtime.substring(0, 10)+"</p></li>";
					listMarkup += "			<li><i class='mdi mdi-account'></i><p>"+rs.writer+"</p></li>";
					listMarkup += "			<li><i class='mdi mdi-eye'></i><p>"+rs.readCnt+"</p></li>";
					listMarkup += "		</ul>";
					listMarkup += "	  </div></div>";
					listMarkup += "</a>";
					listMarkup += "</td>";
					listMarkup += "</tr>";
					listMarkup += "</tbody>";							
				    listMarkup += "</table>";
				    listMarkup += "<li></li>"
				});
			}else{
				alert("검색결과가 없습니다.");
			}			
		}
		
		$('#boardListPg').val(data.pagingPage);
		if(data.pagingEntries == 0){
			$("#boardListPageInfo").text(" (10)");
		}else{
			$("#boardListPageInfo").text(" ("+data.pagingEntries+" / "+data.totalNumberOfEntries+")");
			
		}
		$("#boardListView li:last").after(listMarkup);
		if(data.pagingEntries == data.totalNumberOfEntries){
			$('#boardListMore').unbind();
			$('#boardListMore').bind('click', function(){
				$('#boardListMore').unbind();
				getBoardListPage();
			});			
		}		
	});
}

//게시글 작성, 수정
function saveBoard(bbsId){
/*	$.mobile.showPageLoadingMsg();
	alert("g");*/
	$.post('/action/MobileBoardAction.do?mode=saveBoard', $('#boardListForm').serialize(), function(data) {
		alert("등록되었습니다.");
		//*$.mobile.hidePageLoadingMsg();	*/
		//alert(bbsId);
		location.href = "/action/MobileBoardAction.do?mode=selectList&bbsId="+bbsId;
	});	
	/*alert($('#boardListForm').serialize());	
	alert($("#bbsId").val());
	alert($("#seq").val());
	alert($("#saveMode").val());
	alert($("#subject").val());
	alert($("#content").val());*/
}

//게시글 작성, 수정
function saveThxBoard(){
	$.mobile.showPageLoadingMsg();
	$.post('/action/MobileBoardAction.do?mode=saveThxBoard', $('#boardListForm').serialize(), function(data) {
		alert("등록되었습니다.");
		$.mobile.hidePageLoadingMsg();	
		//alert(bbsId);
		location.href = "/m/web/board/boardList.jsp?bbsId=thanks";
	});	
	/*alert($('#boardListForm').serialize());	
	alert($("#bbsId").val());
	alert($("#seq").val());
	alert($("#saveMode").val());
	alert($("#subject").val());
	alert($("#content").val());*/
}

//게시글 코멘트 작성
function saveBoardComment(){
	$.post('/action/MobileBoardAction.do?mode=saveBoardComment',
		{"bbsId": $("#bbsId").val(), "seq": $("#seq").val(), "commentContents": $("#commentContents").val()}
		, function(data) {
			alert("등록되었습니다.");	
			location.reload();
	});	
}
//게시글 코멘트 작성
function saveThxBoardComment(){
	if($("#commentContents").val() == ''){
		alert("내용을 입력하세요");
	}
	$.mobile.showPageLoadingMsg();
	$.post('/action/MobileBoardAction.do?mode=saveThxBoardComment',
		{"idx": $("#idx").val(), "commentContents": $("#commentContents").val()}
		, function(data) {
			alert("등록되었습니다.");
			$.mobile.hidePageLoadingMsg();	
			location.reload();
	});	
	/*alert($("#bbsId").val());
	alert($("#seq").val());
	alert($("#commentContents").val());*/
}

function deleteThxBoardComment(parentIdx, idx){
	if(confirm("삭제 하시겠습니까?")) {		
		$.mobile.showPageLoadingMsg();
		$.post('/action/MobileBoardAction.do?mode=deleteThxBoardComment',
				{"parentIdx": parentIdx, "idx": idx}
		, function(data) {
			alert("삭제되었습니다.");
			$.mobile.hidePageLoadingMsg();	
			location.reload();
		});		
	}
}
		
function deleteBoardComment(bbsId, seq, commentSeq){
	if(confirm("삭제 하시겠습니까?")) {		
		$.post('/action/MobileBoardAction.do?mode=deleteBoardComment',
				{"bbsId": bbsId, "seq": seq, "commentSeq": commentSeq}
		, function(data) {
			alert("삭제되었습니다.");
			location.reload();
		});		
	}
}


function getExpertPoolSearcgList(){
	if($("#search_expertPoolName").val() == ''){
		alert("검색어를 입력하세요");return;
	}	
	$.mobile.showPageLoadingMsg();
	//alert("getCustomerListPage:" + pg);
	$.getJSON("/action/MobileBoardAction.do?mode=getThanksExpertPoolList", 
			{"name":$("#search_expertPoolName").val()}, 
			function(data, status) {
				//alert("Data Loaded: " + (data));
				var listMarkup = ""; 
				if(data.expertPoolList.length > 0){
					$(data.expertPoolList).each(function(index, rs){
						listMarkup += "<li style='padding: 0px;'><a href='javascript:insertThxExpertPool("+index+")' id='insertThxExpertPool"+index+"'  "
										+"	thanksEmail='"+rs.email+"' thanksCompanypositionname='"+rs.companyPositionName+"' thanksUserId='"+rs.userId+"' thanksName='"+rs.name+"' >";
						listMarkup += "	<h4 class='ui-li-heading'>["+rs.companyId+"]"+ rs.name +"</h4>";
						listMarkup += "	<p class='ui-li-desc' style='font-size: 11px;'>	부서: "+rs.deptName+" | email:"+rs.email+"</p>";
						listMarkup += "</a></li>"; 
					});
				}else{
					alert("검색결과가 없습니다.");
				}
				$("#expertPoolPopupListView").html(listMarkup);
				$("#expertPoolPopupListView").listview('refresh');
				$.mobile.hidePageLoadingMsg();
	});	
}
function insertThxExpertPool(obj){
	$("#thanksEmail").val($("#insertThxExpertPool"+obj).attr("thanksEmail"));
	$("#thanksCompanypositionname").val($("#insertThxExpertPool"+obj).attr("thanksCompanypositionname"));
	$("#thanksUserId").val($("#insertThxExpertPool"+obj).attr("thanksUserId"));
	$("#thanksName").val($("#insertThxExpertPool"+obj).attr("thanksName"));
	history.go(-1);
}
function changeInputTypeChk(){
	if($("#inputTypeChk").is(":checked") == true ){
		$("#thanksName").attr("readonly",false);
		$("#thanksName").attr("placeholder", "");
	}else{
		$("#thanksName").val("");
		$("#thanksName").attr("readonly",true);
		$("#thanksName").attr("placeholder", "입력시 검색 버튼을 누르세요");
	}
}
function likeThxBoard(idx){
		$.mobile.showPageLoadingMsg();
		$.post('/action/MobileBoardAction.do?mode=insertThxBoardLike',
				{"idx": idx}
		, function(data) {
			alert("추천하였습니다..");
			$.mobile.hidePageLoadingMsg();	
			location.reload();
		});		
}