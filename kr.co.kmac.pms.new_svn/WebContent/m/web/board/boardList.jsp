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
		
		<!-- sub_container -->
		<input type="hidden" id="boardListPg" value="0"/>
		<input type="hidden" id="boardListId" value="<c:out value="${param.bbsId}"/>"/>
		
		<div>
            <div class="topbar">
                <div>
                    <button type="button" class="btn arrowL" title="이전 페이지" onclick="history.back()"><i class="mdi mdi-arrow-left"></i></button>
                    <ul><p><c:out value="${codeEntity.data1 }"/></p></ul>
                </div>
                <ul>
                    <li><button type="button" class="btn c-sub" title="검색 창 열기" onclick="javascript:layer_open(this,'pop_search');"><i class="mdi mdi-magnify"></i></button></li>
                    <li><button type="button" class="btn c-main" title="게시글 작성" onclick="location.href='/action/MobileBoardAction.do?mode=inputForm&bbsId=<c:out value="${param.bbsId }"/>&saveMode=INSERT'"><i class="mdi mdi-pencil-plus-outline"></i></button></li>
                </ul>
                <!-- 검색 창 팝업 -->
                <div id="pop_search" class="popup_layer pop_search">
                    <div class="popup_bg"></div>
                    <div class="popup_inner">
                        <div class="pop_close">
                            <input type="text" name=keyword id="boardSearchSubject" value="" placeholder="검색어 입력" />					
                            <button type="button" class="btn-close" onclick="javascript:layer_close('pop_search');" title="닫기"><i class="mdi mdi-close"></i></button>
                        </div>								
                        <div class="write_group">
                            <select name="keyfield" id="keyfield">
							<option value=subject
								<c:if test="${keyfield == 'subject' }">selected</c:if>>제목</option>
							<option value="content"
								<c:if test="${keyfield == 'content' }">selected</c:if>>내용</option>
							<option value="writer"
								<c:if test="${keyfield == 'writer' }">selected</c:if>>작성자</option>
						</select>
                        </div>                     
                        <button type="button" class="btn c-wt" onclick="doSearch();" title="검색">검색</button>
                    </div>
                </div>
                <!-- // 검색 창 팝업 -->
           </div>
            
            <!-- contents -->
           <div class="contents">
                <div class="tbl-wrap">
                  <table class="tbl list">							
                       <tbody>
						<c:forEach var="result" items="${result.list}">
							<tr onmouseout="row_out(this)" <c:choose><c:when test="${result.topArticle == 'Y'}">onmouseover="topArticleRow_over(this);" class="topArticle"</c:when><c:otherwise> onmouseover="row_over(this)"</c:otherwise></c:choose>>
								<td>
									<a href="/action/MobileBoardAction.do?mode=boardView&bbsId=<c:out value="${result.bbsId}" />&seq=<c:out value="${result.seq}" />&projectCode=<c:out value="${projectCode}" />&projectName=<c:out value="${projectName}" />'">
                                        <div>
                                            <div>
                                                <p class="subject <c:if test="${result.recent}">new</c:if>">
                                                <c:set value="" var="temp" />
													<c:choose>
														<c:when test="${result.lev > 0}">
															&nbsp;&nbsp;<i class="mdi mdi-subdirectory-arrow-right"></i>		
														</c:when>
													</c:choose>
													<c:if test="${result.prjType == '2' }">《상근》</c:if>
													<c:out value="${temp}${result.subject}" escapeXml="false"/>&nbsp
                                                </p><!-- 새글 .new -->
                                                <ul class="info">
                                                    <li><i class="mdi mdi-clock-outline"></i><p>
	                                                    <fmt:parseDate value="${result.wtime}" type="DATE" dateStyle="SHORT"  pattern="yyyy-MM-dd HH:mm:ss.SSS" var="from"/>
														<fmt:formatDate value="${from}" pattern="yyyy-MM-dd" var="formatted"/>
														<c:out value="${formatted}" />
                                                    </p></li>
                                                    <li><i class="mdi mdi-account"></i><p><c:out value="${result.writer}" /></p></li>
                                                    <li><i class="mdi mdi-eye"></i><p><c:out value="${result.readCnt}" /></p></li>
                                                    <c:if test="${bbsId eq 'sharebbs'}"><li><i class="mdi mdi-cards-heart"></i><p><c:out value="${result.likeCnt }" /></p></li></c:if>
                                                    <c:if test="${ result.fileCnt > 0 }"><li><i class="mdi mdi-link-variant"></i></li></c:if>
                                                </ul>
                                            </div>
                                            <c:if test="${!empty result.commentCnt and result.commentCnt ne '0'}">
												<p class="count"><c:out value="${result.commentCnt}" /></p>
											</c:if>
                                        </div>
                                    </a>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty result.list}"><tr><td align='center'>검색 결과가 존재하지 않습니다.<br></td></tr></c:if>
					</tbody>
				</table>
				
				<div class="paging_area">
					<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml( 
									<c:out value="${result.valueListInfo.pagingPage}" default="1"/>, 
									5, 
									<c:out value="${result.valueListInfo.totalNumberOfEntries}" default="0"/> , 
									10)
							) ;
					</SCRIPT>
				</div>
			</div>
		</div>
			 <!-- // contents -->
                                    <!-- // paging -->
				<input type="hidden" name="mode"     	value="selectList">
				<input type="hidden" name="bbsId"    	value="<c:out value="${bbsId}"/>">
				<input type="hidden" name="keyfield" 	value="<c:out value="${keyfield}"/>">
				<input type="hidden" name="keyword"  	value="<c:out value="${keyword}"/>">
				<input type="hidden" name="pg"       	value="<c:out value="${pg}"/>">
				<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>">
				<input type="hidden" name="projectName" value="<c:out value="${projectName}"/>">
			
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include><!-- /footerx	 -->
</div><!-- /page -->
	</form>
</body>
</html>
