<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<script type="text/javascript">


function goPage(pageNumber) {
	frm.pg.value = pageNumber;
	frm.submit();
}
function doSearch() {
	frm.submit();
}
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
</script>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head> 
<body> 
<form name="frm" method="post">
<div id="boardListPage"  class="sub_container">

       <!-- header -->
		<jsp:include page="/m/web/common/header.jsp" >
			<jsp:param value="fixed" name="data_position" />
		</jsp:include>
		<!-- // header -->
		
		 <div class="topbar">
                <div>
                    <button type="button" onclick="history.back()" class="btn arrowL" title="이전 페이지"><i class="mdi mdi-arrow-left"></i></button>
                    <p>내역보기</p>
                </div>
         </div>
		 <!-- Contents -->
            <div class="contents">
                <div class="tbl-wrap">
                    <table class="tbl list common"><!-- 공통으로 사용할 리스트 페이지 .common -->						
                        <tbody>
                            <tr>
                                <td>
                                    <a href="action/MobileTaxWorkAction.do?mode=selectIncentiveList">
                                        <div>
                                            <div>
                                                <div class="title"><p><i class="mdi mdi-message-text"></i>입금내역</p></div>
                                            </div>
                                            <button type="button"><i class="mdi mdi-arrow-right-circle"></i></button>
                                        </div>
                                    </a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <a href="/action/MobileTaxWorkAction.do?mode=selectBonusPreList">
                                        <div>
                                            <div>
                                                <div class="title"><p><i class="mdi mdi-message-text"></i>성과급 예상내역</p></div>
                                            </div>
                                            <button type="button"><i class="mdi mdi-arrow-right-circle"></i></button>
                                        </div>
                                    </a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <a href="/action/MobileTaxWorkAction.do?mode=selectBonusList">
                                        <div>
                                            <div>
                                                <div class="title"><p><i class="mdi mdi-message-text"></i>성과급 내역</p></div>
                                            </div>
                                            <button type="button"><i class="mdi mdi-arrow-right-circle"></i></button>
                                        </div>
                                    </a>
                                </td>
                            </tr>
                            <tr>
                        </tr>
                       </tbody>
                    </table>
                  </div>
                </div>
		
			
		<jsp:include page="/m/web/common/footer.jsp" >
			<jsp:param value="data_position" name=""/>
		</jsp:include><!-- /footerx	 -->
</div><!-- /page -->
	</form>
</body>
</html>
