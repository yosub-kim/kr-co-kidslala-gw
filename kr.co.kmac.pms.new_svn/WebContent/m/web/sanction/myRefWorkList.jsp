<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<script type="text/javascript">
function goPage(next_page) {	
	document.location.href = "/action/WorkCabinetAction.do?mode=getMyRefWorkListForMobile&pageNo="+next_page;
}
function sanctionItemClick(projectCode, approvalType, seq, workType, workTypeUrl, useMobile) {
	
	document.sanctionPresentStateForm.target = "";	
	if (useMobile == "1") {
		document.sanctionPresentStateForm.action = workTypeUrl+"ForMobile&readonly=true&projectCode="+projectCode+"&approvalType="+approvalType+"&seq="+seq;
	} else {
		mobileTaskExcetion();
		document.sanctionPresentStateForm.action = workTypeUrl+"&readonly=true&projectCode="+projectCode+"&approvalType="+approvalType+"&seq="+seq;
	}	
	document.sanctionPresentStateForm.submit();
}
function selectWork(workId, type){
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
function mobileTaskExcetion(){
	alert("선택하신 결재건은 모바일 버전을 지원하지 않으므로 PC버전으로 이동됩니다.");	
}

function doSearch() {
	document.sanctionPresentStateForm.target = "";		
	document.sanctionPresentStateForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkListForMobile";
	document.sanctionPresentStateForm.submit();
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
<form name="sanctionPresentStateForm" method="post"> 
<div>
	<input type="hidden" id="sanctionStateListPg" value="0"/>
	
	<!-- header -->
	<jsp:include page="/m/web/common/header.jsp" >
		<jsp:param value="fixed" name="data_position" />
	</jsp:include>
	<!-- // header -->
	
	 <!-- sub_container -->
     <div class="sub_container">
     	<div class="topbar">
           <div>
               <button type="button" class="btn arrowL" onclick="history.back()" title="이전 페이지"><i class="mdi mdi-arrow-left"></i></button>
               <p>내 참조함</p>
           </div>                
           
           <ul>
               <li><button type="button" class="btn c-sub" title="검색 창 열기" onclick="javascript:layer_open(this,'pop_search');"><i class="mdi mdi-magnify"></i></button></li>
               <!-- <li><button type="button" class="btn c-main" title="게시글 작성" onclick="location.href='boardWrite.html'"><i class="mdi mdi-pencil-plus-outline"></i></button></li> -->
           </ul>
           
           <!-- 검색 창 팝업 -->
           <div id="pop_search" class="popup_layer pop_search">
               <div class="popup_bg"></div>
               <div class="popup_inner">
                   <div class="pop_close">
                       <input type="text" name="keyword" class="textInput_left"  placeholder="검색어(제목) 입력" value="<c:out value="${keyword}"/>" >					
                       <button type="button" class="btn-close" onclick="javascript:layer_close('pop_search');" title="닫기"><i class="mdi mdi-close"></i></button>
                   </div>
                   <button type="button" class="btn c-wt" title="검색" onclick="location.href='javascript:doSearch();'">검색</button>
               </div>
           </div>
           <!-- // 검색 창 팝업 -->
    	</div>
      </div>
      
      <!-- contents -->
      <div class="contents">
          <div class="tbl-wrap">
               <table class="tbl list">							
                   <tbody>
                       <c:if test="${!empty list.list}">
							<c:forEach var="rs" items="${list}">
								<tr>
									<td>
										<ul class="info"></ul>
										<a href="#" onclick="javascript:selectWork('<c:out value="${rs.id}" />', '<c:out value="${rs.type}" />')">
											<div>
												<div>
												<div class="subject"><c:choose><c:when test="${rs.useMobile eq '1' }"><i class="mdi mdi-cellphone c-aqua"></i></c:when>
													<c:otherwise><i class="mdi mdi-monitor c-red"></i></c:otherwise></c:choose><p><strong>[<c:out value="${rs.approvalName}" />]</strong>
													<span id="state"></span>
													<c:out value="${rs.title}" /></p>
												</div>
													<ul class="info">
														<li><i class="mdi mdi-file-tree-outline"></i><p><c:out value="${rs.draftUserDeptName}" /></p></li>
														<li><i class="mdi mdi-account"></i><p><c:out value="${rs.draftUserName}" /></p></li>
														<li><i class="mdi mdi-clock-outline"></i><p><c:choose><c:when test="${registerDate eq null}">-</c:when><c:otherwise><fmt:formatDate value="${rs.draftDate}" pattern="yyyy-MM-dd" /></c:otherwise></c:choose></p></li>
													</ul>
												</div>
											</div>
										</a>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty list.list}"><tr><td align='center'>검색 결과가 존재하지 않습니다.<br></td></tr></c:if>
					</tbody>
				</table>
				<div class="paging_area">
					<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml( 
								<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
								5, 
								<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
								10)
							) ;
					</SCRIPT>
				</div>
			</div>
		</div>
		<!-- // contents -->

	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include><!-- /footerx	 -->
</div><!-- /page -->
</form>
</body>
</html>
