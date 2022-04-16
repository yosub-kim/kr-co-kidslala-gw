<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head>

<script type="text/javascript">
jQuery(document).ready(function () {
	
	// 추천버튼 클릭시(추천 추가 또는 추천 제거)
	jQuery("#rec_update").on("click", function(){
		jQuery.ajax({
			url: "/action/HashTagAction.do?mode=updateRecommend",
            type: "POST",
            data: {
                bbsId: '<c:out value="${bbsId}" />',
                seq: '<c:out value="${result.boardData.seq}" />'
            },
            success: function () {
		        recCount();
            },
		})
	})
	
 	// 게시글 추천수
    function recCount() {
		jQuery.ajax({
			url: "/action/BoardAction.do?mode=boardViewRecCnt",
            type: "POST",
            data: {
                bbsId: '<c:out value="${bbsId}" />',
                seq: '<c:out value="${result.boardData.seq}" />'
            },
            success: function (count) {
            	jQuery(".rec_count").html(count);
            	recResult();
            },
		})
    };
    
    // 게시글 이미지 경로
    function recResult() {
		jQuery.ajax({
			url: "/action/BoardAction.do?mode=boardViewRecResultForMobile",
            type: "POST",
            data: {
                bbsId: '<c:out value="${bbsId}" />',
                seq: '<c:out value="${result.boardData.seq}" />'
            },
            success: function (result) {
            	jQuery(".rec_result").html(result);
            	recContent();
            },
		})
    };
    
    // 게시글 인원
    function recContent() {
		jQuery.ajax({
			url: "/action/BoardAction.do?mode=boardViewContentResult",
            type: "POST",
            data: {
                bbsId: '<c:out value="${bbsId}" />',
                seq: '<c:out value="${result.boardData.seq}" />'
            },
            success: function (content) {
            	jQuery(".rec_content").html(content);
            },
		})
    };
    
    recCount(); // 처음 시작했을 때 실행되도록 해당 함수 호출 
});
</script>

<body>
	<input id="bbsId" type="hidden" value="<c:out value="${codeEntity.key1 }"/>"/>
	<input id="seq" type="hidden" value="<c:out value="${result.boardData.seq}" />"/>
	<input id="chk_dept" type="hidden" value="<c:out value="${result.boardData.email}" />"/>

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
                    <p><c:out value="${codeEntity.data1 }"/></p>
                </div>
                <!-- <ul>
                    <li><button type="button" class="btn c-red" title="삭제"><i class="mdi mdi-trash-can-outline"></i></button></li>
                </ul> -->
            </div>
            
            <!-- Contents -->
            <div class="contents">

                <div class="tbl-wrap">
                    <table class="tbl view">							
                        <tbody>
                        	<!-- title -->
                            <tr>
                                <td>
                                    <div>
                                        <div>
                                            <p class="subject"><c:out value="${result.boardData.subjectNoTag}" escapeXml="false"/> </p>
                                            <ul class="info">
                                                <li><i class="mdi mdi-clock-outline"></i><p>
                                                	<fmt:parseDate value="${result.boardData.wtime}" type="DATE" dateStyle="SHORT"  pattern="yyyy-MM-dd HH:mm:ss.SSS" var="from"/>
                                                	<fmt:formatDate value="${from}" pattern="yyyy-MM-dd" var="formatted"/><c:out value="${formatted}" />
                                                </p></li>
                                                <li><i class="mdi mdi-account"></i><p><c:out value="${result.boardData.writer}"/></p></li>
                                                <li><i class="mdi mdi-eye"></i><p><c:out value="${result.boardData.readCnt}" /></p></li>
                                                <c:if test="${bbsId eq 'sharebbs' || bbsId eq 'doobitCorps'}">
                                                	<!-- <li><i class="mdi mdi-cards-heart"></i><p><span class="rec_count"></span></p></li> --> 
                                                	<li><div class="btn_like">			
                                                        <button id="rec_update" type="button" class="like"><span class="rec_result"></span><p><span class="rec_count"></span></p></button>
                                                  	</div></li>
                                                </c:if>
                                               
                                                <!-- <li><i class="mdi mdi-cards-heart"></i><p>3333</p></li> -->
                                            </ul>
                                        </div>
                                    </div>
                                </td>
                            </tr>
							<!-- // title -->
							
							<!-- file list -->
                            <c:if test="${!empty fileList}">
                            <tr>
                                <td class="pd-10">
                                    <ul class="file">
	                                    <c:forEach var="file" items="${fileList}">
	                                        <li><a href="javascript:fileDownload('<c:out value="${file.attachFileId}"/>', 'N')"><i class="mdi mdi-paperclip"></i><p><c:out value="${file.attachFileName}" escapeXml="false"/></p></a></li>
	                                    </c:forEach>
                                    </ul>
                                </td>
                            </tr>
                            </c:if>
                            <!-- // file list -->
                            
                            <!-- contents -->
                             <tr>
                                <td>                                    
                                    <p class="text_view">
                                    	<c:out value="${result.boardData.content}"  escapeXml="false"/>
                                    </p>
                                 </td>
                             </tr>
                             <!-- // contents -->
                           </tbody>
                        </table>
                        
                        <form name="frm" method="post">
							<input type="hidden" name="saveMode"	value="DELETE">
							<input type="hidden" name="bbsId"		value="<c:out value="${bbsId}" />">
							<input type="hidden" name="seq"			value="<c:out value="${result.boardData.seq}" />">
							<input type="hidden" name="jobClass"	value='<%=session.getAttribute("jobClass") %>' />
						</form>
						
						<c:if test="${codeEntity.data2 != 'N'}">
							<form name="frmCmmt" method="post" >
								<input type="hidden" name="bbsId"		value="<c:out value="${bbsId}" />">
								<input type="hidden" name="seq"			value="<c:out value="${result.boardData.seq}" />">
							
								<div class="inner">
			                        <div class="title_area">
			                            <p class="title">댓글<span>(<c:out value="${result.boardCommentDataListSize}" />)</span></p>
			                            <ul>
			                                <li><button type="button" class="btn c-main" title="작성" onclick="saveBoardComment();"><i class="mdi mdi-pencil-plus-outline"></i></button></li>
			                            </ul>
			                        </div>
			                        <div class="write_box">
			                            <textarea name="textarea" id="commentContents" placeholder="댓글작성" cols="30" rows="10"></textarea>
			                        </div>
			                        
			                         <c:if test="${!empty result.boardCommentDataList }">
			                        	<div class="write_list">
										<c:forEach items="${result.boardCommentDataList }" var="item">
											<div class="list">
												<div class="writer">
													<p><c:out value="${item.writer }" /><span class="date"><c:out value="${item.writeTime }" /></span></p>
													<c:if test="${myUserId == item.writerId}">
														<ul class="btn_edit">
				                                       		<li><button type="button" class="btn line c-red" title="삭제" onclick="deleteBoardComment('<c:out value="${item.bbsId}"/>','<c:out value="${item.seq}"/>','<c:out value="${item.commentSeq}"/>')"><i class="mdi mdi-trash-can-outline"></i></button></li>
				                                        </ul>
				                                    </c:if>
			                                    </div>			
												<p><c:out value="${item.content }" escapeXml="false" /></p>
											</div>
										</c:forEach>
										</div>
									</c:if>
								</div>  
							</form>
						</c:if>
	</div>
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include><!-- /footerx	 -->
	
</div><!-- /page -->

</body>
</html>