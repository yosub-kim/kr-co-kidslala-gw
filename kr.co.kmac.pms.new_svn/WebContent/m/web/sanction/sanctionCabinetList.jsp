<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<script type="text/javascript">
function doSearch() {
	document.myWorkListForm.target = "";		
	document.myWorkListForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkListForMobile";
	document.myWorkListForm.submit();
}

function goPage(next_page) {
	document.myWorkListForm.pageNo.value = next_page ;
	document.myWorkListForm.target = "";		
	document.myWorkListForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkListForMobile";
	document.myWorkListForm.submit();
}
</script>
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head> 
<body> 
<form name="myWorkListForm" method="post">
	<!-- pageNo -->
	<div style='display: none;'>
		<input type="hidden" name='pageNo'> 
	</div>
	<!-- // pageNo -->

	<!-- header -->
	<jsp:include page="/m/web/common/header.jsp" >
		<jsp:param value="fixed" name="data_position" />
	</jsp:include>
	<!-- // header -->
	
	 <!-- sub_container -->
     <div class="sub_container">
     	<div class="topbar">
           <div>
               <button type="button" class="btn arrowL" title="이전 페이지" onclick="history.back();"><i class="mdi mdi-arrow-left"></i></button>
               <p>내 결재함</p>
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
									<a href="javascript:selectWork('<c:out value="${rs.id}" />', '<c:out value="${rs.type}" />')">
										<div>
											<div>
												<div class="subject"><c:choose><c:when test="${rs.useMobile eq '1' }"><i class="mdi mdi-cellphone c-aqua"></i></c:when>
													<c:otherwise><i class="mdi mdi-monitor c-red"></i></c:otherwise></c:choose><p><strong>[<c:out value="${rs.approvalName}" />]</strong>
														<span id="state"></span>
													<c:choose>
														<c:when test="${rs.refWorkId2 == 'A' || rs.refWorkId2 == 'PA' }"><c:out value="${rs.projectName}" /></c:when>
														<c:otherwise><c:out value="${rs.title}" /></c:otherwise>
													</c:choose>
												</p></div>
												<ul class="info">
													<li><i class="mdi mdi-file-tree-outline"></i><p><c:out value="${rs.draftUserDeptName}" /></p></li>
	                                                <li><i class="mdi mdi-account"></i><p><c:out value="${rs.draftUserName}" /></p></li>
	                                                <li><i class="mdi mdi-clock-outline"></i><p><fmt:formatDate value="${rs.draftDate}" pattern="yyyy-MM-dd" /></span></p></li>
												</ul>
											</div>
											<c:choose>
												<c:when test="${rs.level == 'SANCTION_STATE_DRAFT'}"><p class="count c-main">작성</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_REJECT_DRAFT'}"><p class="count c-red">반려</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_REVIEW'}"><p class="count c-main">검토</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_APPROVE'}"><p class="count c-main">승인</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_CHECK'}"><p class="count c-main">확인</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ASSIST1'}"><p class="count c-main">협의1</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ASSIST2'}"><p class="count c-main">협의2</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ASSIST3'}"><p class="count c-main">협의3</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_SUPPORT_DRAFT'}"><p class="count c-main">지원실 기안</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_SUPPORT_REVIEW'}"><p class="count c-main">지원실 검토</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_SUPPORT_APPROVE'}"><p class="count c-main">지원실 승인</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_CEO'}"><p class="count c-main">대표이사</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_COMPLETE'}"><p class="count c-main">완료</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_RATING'}"><p class="count c-main">작성</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ENDRIVIEW_DRAFT'}"><p class="count c-main">작성</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ENDRIVIEW_RIVIEW'}"><p class="count c-main">검토</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ENDRIVIEW_APPROVE'}"><p class="count c-main">승인</p></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ENDRIVIEW_VERIFICATE'}"><p class="count c-main">검증</p></c:when>
											</c:choose>
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
	
	
		<!-- footer -->
		<jsp:include page="/m/web/common/footer.jsp" >
			<jsp:param value="data_position" name=""/>
		</jsp:include>
		<!-- // footer -->
</form>
</body>
</html>
