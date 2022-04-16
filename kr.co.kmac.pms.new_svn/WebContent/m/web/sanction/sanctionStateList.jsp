<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<%@ include file="/common/include/taglib.jsp"%>
<script type="text/javascript">
function goPage(next_page) {	
	document.location.href = "/action/SanctiontPresentStateAction.do?mode=getAllSanctiontPresentStateForMobile&pageNo="+next_page
	+"&startDate="+document.sanctionPresentStateForm.startDate.value
	+"&endDate="+document.sanctionPresentStateForm.endDate.value
	+"&divCode="+document.sanctionPresentStateForm.divCode.value
	+"&projectName="+encodeURIComponent(document.sanctionPresentStateForm.projectName.value);
}

function doSearch() {
	if ($('startDate').value > $('endDate').value) {
		alert("종료일이 시작일보다 앞설 수 없습니다.");
		return;
	}
	
	document.sanctionPresentStateForm.target = "";		
	document.sanctionPresentStateForm.action = "/action/SanctiontPresentStateAction.do?mode=getAllSanctiontPresentStateForMobile";
	document.sanctionPresentStateForm.submit();
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
function mobileTaskExcetion(){
	alert("선택하신 결재건은 모바일 버전을 지원하지 않으므로 PC버전으로 이동됩니다.");	
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
               <button type="button" class="btn arrowL" title="이전 페이지" onclick="history.back()"><i class="mdi mdi-arrow-left"></i></button>
               <p>결재현황보기</p>
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
                       <input type="text" name="projectName" class="textInput_left"  placeholder="검색어(제목) 입력" value="<c:out value="${projectName}"/>" >					
                       <button type="button" class="btn-close" onclick="javascript:layer_close('pop_search');" title="닫기"><i class="mdi mdi-close"></i></button>
                   </div>
                   <div class="pop_close">
						<fmt:parseDate value="${startDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
						<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
						<fmt:parseDate value="${endDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
						<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
						<script>
							jQuery(function(){jQuery( "#startDate" ).datepicker({});});
							jQuery(function(){jQuery( "#endDate" ).datepicker({});});
						</script>
						<input type="text" name="startDate" id="startDate" readonly="readonly" size="7" style="width: 48%; text-align:center;" value="<c:out value="${start}" />" />~
						<input type="text" name="endDate" id="endDate" readonly="readonly" size="7" style="width: 48%; text-align:center;"  value="<c:out value="${end}" />" />
                   </div>
                   <div class="write_group">
                        <SELECT name='approvalType' id='approvalType' style="width: 50%;">
							<option value="">전체</option>
							<c:forEach var="item" items="${sanctionTemplateList}">
								<option value="<c:out value="${item.approvalType}"/>" <c:if test="${approvalType == item.approvalType}">selected</c:if>><c:out value="${item.approvalName}" /></option>
							</c:forEach>
						</SELECT>
						<div id="currDivList" style='width:50%'>
							<org:divList enabled="1" divType="A" isSelectBox="Y" depth="2" selectedInfo="${divCode}" attribute=" name='divCode'  id='divCode'" all="Y"></org:divList>
						</div>
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
									<ul class="info"></ul><a href="#" onclick="sanctionItemClick('<c:out value="${rs.projectCode}" />', '<c:out value="${rs.approvalType}" />', '<c:out value="${rs.seq}" />', '<c:out value="${rs.workType}" />', '<c:out value="${rs.workTypeUrl}" />','<c:out value="${rs.useMobile }"/>')">
										<div>
											<div>
												<fmt:parseDate value="${rs.registerDate}" type="DATE" dateStyle="SHORT" pattern="yyyy-MM-dd HH:mm:ss.SSS" var="var4" />
												<fmt:formatDate value="${var4}" pattern="yyyy-MM-dd" var="registerDate" />
												<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />
												<div class="subject"><c:choose><c:when test="${rs.useMobile eq '1' }"><i class="mdi mdi-cellphone c-aqua"></i></c:when>
													<c:otherwise><i class="mdi mdi-monitor c-red"></i></c:otherwise></c:choose><p><strong>[<c:out value="${rs.approvalTypeName}" />]</strong>
													<span id="state"></span>
													<c:out value="${rs.projectName}" /></p>
												</div>
												<ul class="info">
													<%-- <c:choose><c:when test="${rs.useMobile eq '1' }"><li><i class="mdi mdi-check-circle-outline"></i><p>확인가능</p></li></c:when>
													<c:otherwise><li><i class="mdi mdi-close-circle-outline"></i><p>확인불가능</p></li></c:otherwise></c:choose> --%>
													<li><i class="mdi mdi-file-tree-outline"></i><p><c:out value="${rs.registerDeptName}" /></p></li>
	                                                <li><i class="mdi mdi-account"></i><p><c:out value="${rs.registerName}" /></p></li>
	                                                <li><i class="mdi mdi-clock-outline"></i><p><c:choose><c:when test="${registerDate eq null}">-</c:when><c:otherwise><c:out value="${registerDate}" /></c:otherwise></c:choose></p></li>
												</ul>
											</div>
										  <c:choose><c:when test="${rs.present == '종료' }"><p class="count c-bk"><c:out value="${rs.present }" /></p></c:when>
										  <c:when test="${rs.present == '반려' }"><p class="count c-red"><c:out value="${rs.present }" /></p></c:when>
										  <c:otherwise><p class="count c-main"><c:out value="${rs.present }" /></p></c:otherwise></c:choose>
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
