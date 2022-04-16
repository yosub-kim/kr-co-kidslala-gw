<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Generator" content="EditPlus" charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=1600">
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
		
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" /><!-- 서브페이지에서만 사용 -->		
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" /><!-- 셀렉트 박스 UI -->
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function deleteBoard() {
	if(confirm("삭제 하시겠습니까?")) {
		var sFrm = document.frm;
		var ActionURL = "/action/BoardAction.do";	
		ActionURL += "?mode=saveBoard";

		var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					location.href = "/action/BoardAction.do?mode=selectList&bbsId=<c:out value="${bbsId}"/>&seq=<c:out value="${result.boardData.seq}" />";
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){				
					alert("저장할 수 없습니다.");
				}
			}
		); status = null;	
	}	
}
function editBoard() {
	location.href="/action/BoardAction.do?mode=inputForm&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.boardData.seq}" />&saveMode=UPDATE&projectCode=<c:out value="${projectCode}"/>&projectName=<c:out value="${projectName}"/>";
}
function saveBoardComment() {
		var sFrm = document.frmCmmt;
		var ActionURL = "/action/BoardAction.do";	
		ActionURL += "?mode=saveBoardComment";

		var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					location.href = "/action/BoardAction.do?mode=boardView&bbsId=<c:out value="${bbsId}" />&projectCode=<c:out value="${projectCode}" />&projectName=<c:out value="${projectName}" />&seq=<c:out value="${result.boardData.seq}" />";					
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){				
					alert("저장할 수 없습니다.");
				}
			} 
		); status = null;	
}
function deleteBoardComment(objTR, bbsId, seq, commentSeq) {
	if(confirm("삭제 하시겠습니까?")) {
		var ActionURL = "/action/BoardAction.do";	
		ActionURL += "?mode=deleteBoardComment";
		ActionURL += "&bbsId="+bbsId;
		ActionURL += "&seq="+seq;
		ActionURL += "&commentSeq="+commentSeq;

		var status = AjaxRequest.get(
				{	'url': ActionURL,
					'async' : false,
					'anotherParameter':'false',
					'onSuccess':function(obj){  // 
			           	var res = eval('(' + obj.responseText + ')');
				           	$(objTR).up().up().remove();
					}, 
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("데이터를 가져오지 못했습니다.");
					}
				});
	}	
}
</script>
<script type="text/javascript">
j$(document).ready(function () {
	
	// 추천버튼 클릭시(추천 추가 또는 추천 제거)
	j$("#rec_update").on("click", function(){
		j$.ajax({
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
		j$.ajax({
			url: "/action/BoardAction.do?mode=boardViewRecCnt",
            type: "POST",
            data: {
                bbsId: '<c:out value="${bbsId}" />',
                seq: '<c:out value="${result.boardData.seq}" />'
            },
            success: function (count) {
            	j$(".rec_count").html(count);
            	recResult();
            },
		})
    };
    
    // 게시글 이미지 경로
    function recResult() {
		j$.ajax({
			url: "/action/BoardAction.do?mode=boardViewRecResult",
            type: "POST",
            data: {
                bbsId: '<c:out value="${bbsId}" />',
                seq: '<c:out value="${result.boardData.seq}" />'
            },
            success: function (result) {
            	j$(".rec_result").html(result);
            	recContent();
            },
		})
    };
    
    // 게시글 인원
    function recContent() {
		j$.ajax({
			url: "/action/BoardAction.do?mode=boardViewContentResult",
            type: "POST",
            data: {
                bbsId: '<c:out value="${bbsId}" />',
                seq: '<c:out value="${result.boardData.seq}" />'
            },
            success: function (content) {
            	j$(".rec_content").html(content);
            },
		})
    };
    
    recCount(); // 처음 시작했을 때 실행되도록 해당 함수 호출 
});
</script>

<style>

	.button1 { 
	    height: 30px;
	    background-color: #ffffff;
	    font-size: 15px;
 		border-radius: 10px 10px 10px 10px;
 		border-style: none;
	}
</style>
</head>
<body>
	<%-- location --%>
	<div class="location">
		<p class="menu_title">
			<c:choose><c:when test="${projectCode != '' }">프로젝트 게시판 </c:when><c:otherwise><code:code tableName="StandardBBS_Master"  code="${bbsId}"/></c:otherwise></c:choose></p>
		<ul>
			<li class="home">HOME</li>
			<li><c:choose><c:when test="${bbsId eq 'templatebbs' }">업무지원</c:when><c:otherwise>게시판</c:otherwise></c:choose></li>
			<li>
			<c:choose>
				<c:when test="${projectCode != ''}">
					<td width="100%" align="left"><span class="mainTitle"><c:out value="${projectName}"/></span></td>
				</c:when>
				<c:otherwise>
					<td width="100%" align="left"><span class="mainTitle"><code:code tableName="StandardBBS_Master"  code="${bbsId}"/></span></td>
				</c:otherwise>
			</c:choose>		
			</li>
		</ul>
	</div>
	<!-- // location -->

	<div class="contents sub">
		<!-- 서브 페이지 .sub -->

		<div class="fixed_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1">
						<i class="mdi mdi-file-document-outline"></i>
						<c:if test="${result.boardData.prjType == '2' }">
							<b>《상근》</b>
						</c:if>
						<c:out value="${result.boardData.subjectNoTag}" escapeXml="false" />
					</p>
					
					<!-- 답글 권한 -->
					<c:choose>
						<c:when test="${bbsId eq 'publicNotice' }">
							<c:if test="${jobClass eq 'A' }">
							<div class="btn_area">
								<button type="button" class="btn line btn_aqua"
									onclick="location.href='/action/BoardAction.do?mode=inputForm&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.boardData.seq}" />&saveMode=REPLY'">
									<i class="mdi mdi-message-reply-text-outline copy 2"></i>답글
								</button>
							</div>
							</c:if>
						</c:when>
						<c:otherwise>
							<div class="btn_area">
									<button type="button" class="btn line btn_aqua"
										onclick="location.href='/action/BoardAction.do?mode=inputForm&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.boardData.seq}" />&saveMode=REPLY'">
										<i class="mdi mdi-message-reply-text-outline copy 2"></i>답글
									</button>
								</div>
						</c:otherwise>
					</c:choose>
				</div>
				
				<div class="btn_area">
					<%
						String id = (String)session.getAttribute("name");
						pageContext.setAttribute("id", id);
						
						String dept = (String)session.getAttribute("dept");
						pageContext.setAttribute("dept", dept);
						
						String ssn = (String)session.getAttribute("ssn");
						pageContext.setAttribute("ssn", ssn);
					%>
					<c:choose>
							<c:when test="${ssn eq 'A000128' || ssn eq 'A000132' || ssn eq 'A001561' || ssn eq 'H000087' || ssn eq 'A000031' || ssn eq 'A004298' || ssn eq 'G000840'}">
								<button type="button" class="btn line btn_blue"
									onclick="location.href='/action/BoardAction.do?mode=inputForm&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.boardData.seq}" />&saveMode=UPDATE&projectCode=<c:out value="${projectCode}"/>&projectName=<c:out value="${projectName}"/>'">
									<i class="mdi mdi-square-edit-outline"></i>수정
								</button>
								<button type="button" class="btn line btn_pink"
									onclick="location.href='javascript:deleteBoard();'">
									<i class="mdi mdi-trash-can-outline"></i>삭제
								</button>
							</c:when>
							<c:when test="${ssn eq 'H003949' || ssn eq 'A000106' || ssn eq 'A000024' || ssn eq 'A004297'}">
								<button type="button" class="btn line btn_blue"
									onclick="location.href='javascript:editBoard();'">
									<i class="mdi mdi-square-edit-outline"></i>수정
								</button>
								<button type="button" class="btn line btn_pink"
									onclick="location.href='javascript:deleteBoard();'">
									<i class="mdi mdi-trash-can-outline"></i>삭제
								</button>
							</c:when>
							<c:otherwise>
								<c:if test="${myUserId == result.boardData.writerId}">
									<button type="button" class="btn line btn_blue"
									onclick="location.href='/action/BoardAction.do?mode=inputForm&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.boardData.seq}" />&saveMode=UPDATE&projectCode=<c:out value="${projectCode}"/>&projectName=<c:out value="${projectName}"/>'">
									<i class="mdi mdi-square-edit-outline"></i>수정
								</button>
								<button type="button" class="btn line btn_pink"
									onclick="location.href='javascript:deleteBoard();'">
									<i class="mdi mdi-trash-can-outline"></i>삭제
								</button>
								</c:if>
							</c:otherwise>
						</c:choose>
					<button type="button" class="btn line btn_grey"
						onclick="location.href='javascript:history.back();'">
						<i class="mdi mdi-backburger"></i>목록
					</button>
				</div>
			</div>

			<div class="fixed_contents sc">

				<!-- Table View-->
				<table class="tbl-view">
					<thead>
						<tr>
							<th>작성자</th>
							<td><c:out value="${result.boardData.writer}" /></th>
							<th>작성일</th>
							<td><fmt:parseDate value="${result.boardData.wtime}" type="DATE" dateStyle="SHORT" pattern="yyyy-MM-dd HH:mm:ss.SSS" var="from" /> 
								<fmt:formatDate value="${from}" pattern="yyyy-MM-dd" var="formatted" /> 
								<c:out value="${formatted}" />
							</td>
						</tr>
						<c:if test="${bbsId eq 'sharebbs'  || bbsId eq 'doobitCorps'}">
							<tr>
								<th>좋아요</th>
								<td colspan="3"><div class="btn_like"><p><span class="rec_content"></span>님 등 <span class="rec_count"></span>명이 좋아합니다.</p><button id="rec_update" type="button" class="like" ><span class="rec_result"></span></button></div></td>
							</tr>
						</c:if>
						<c:if test="${result.boardData.refSchedule == 'Y'}">
						<tr>
							<th>외근일</th>
							<td colspan="3"><c:out value="${result.boardData.workDate }" />
							</td>
						</tr>
						</c:if>
						<tr class="file">
							<th>첨부파일</th>
							<td colspan="3">
									<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log"  /></jsp:include>
							</td>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td colspan="4" class="text_view_box">
								<p class="text_view">
									<c:out value="${result.boardData.content}" escapeXml="false" />
								</p>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- // Table View-->
				
			<form name="frm" method="post">
				<input type="hidden" name="saveMode"	value="DELETE">
				<input type="hidden" name="bbsId"		value="<c:out value="${bbsId}" />">
				<input type="hidden" name="seq"			value="<c:out value="${result.boardData.seq}" />">
				<input type="hidden" name="jobClass"	value='<%=session.getAttribute("jobClass") %>' />
			</form>
			<c:if test="${codeEntity.data2 != 'N'}">
				<table class="tbl-view-reply">
				<form name="frmCmmt" method="post" >
							<input type="hidden" name="bbsId"		value="<c:out value="${bbsId}" />">
							<input type="hidden" name="seq"			value="<c:out value="${result.boardData.seq}" />">
							<thead>
								<tr>
									<td><i class="mdi mdi-reply"></i>댓글 <span><c:out value="${result.boardCommentDataListSize}" /></span></td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="reply_write_box">
										<div class="reply_write">
											<textarea class="sc" name="commentContents" style="width:100%; height:100%;" maxlength="1000" ></textarea>
											<button type="button" class="btn btn_blue" onclick="location.href='javascript:saveBoardComment();'">등록</button>
										</div>
									</td>
								</tr>
								<c:if test="${!empty result.boardCommentDataList }">
									<c:forEach items="${result.boardCommentDataList }" var="item">
										<tr>
											<td>
												<div class="reply_view">
													<div class="a-both">
														<div class="wirter">
															<p class="name">
																<c:out value="${item.writer }" />
															</p>
															<p class="date">
																<c:out value="${item.writeTime }" />
															</p>
														</div>
													<div class="btn_area">
														<!-- <button type="button" class="btn line btn_blue" onclick="location.href='javascript:;'"><i class="mdi mdi-square-edit-outline"></i>수정</button> -->
														<c:if test="${myUserId == item.writerId}">
															<button type="button" class="btn line btn_pink"
																onclick="deleteBoardComment(this, '<c:out value="${item.bbsId}"/>','<c:out value="${item.seq}"/>','<c:out value="${item.commentSeq}"/>');">
																<i class="mdi mdi-trash-can-outline"></i>삭제
															</button>
														</c:if>
													</div>
													</div>
													<!-- <div class="file_link">
																<p><a href="javascript:;">메일 캡쳐.JPG</a><i class="mdi mdi-link-variant"></i></p>
															</div>-->
													<p class="reply_text">
														<c:out value="${item.content }" escapeXml="false" />
													</p>
												</div>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</form>
					</table>
				<!-- // Table View Reply -->
			</c:if>

			<!-- Table List Link -->
			<c:if test="${bbsId ne 'home'}">
				<table class="tbl-list-link">
					<tbody>
						<tr>
							<c:choose><c:when test="${result.prev.seq > 0}">
								<td>
									<button type="button" class="btn btn_grey prev"
										onclick="location.href='/action/BoardAction.do?mode=boardView&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.prev.seq}" />&projectCode=<c:out value="${projectCode}" />&projectName=<c:out value="${projectName}" />'">이전글</button>
									<p>
										<a href="/action/BoardAction.do?mode=boardView&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.prev.seq}" />&projectCode=<c:out value="${projectCode}" />&projectName=<c:out value="${projectName}" />"><c:out value="${result.prev.subject}" escapeXml="false" /></a>
									</p>
								</td>
							</c:when>
							<c:otherwise>
								<td>
									<button type="button" class="btn btn_grey prev">이전글</button>
									<p>
										이전글이 없습니다.
									</p>
								</td>
							</c:otherwise>
							</c:choose>
							
							<c:choose><c:when test="${result.next.seq > 0}">
							<td>
								<p>
									<a href="/action/BoardAction.do?mode=boardView&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.next.seq}" />&projectCode=<c:out value="${projectCode}" />&projectName=<c:out value="${projectName}" />"><c:out value="${result.next.subject}" escapeXml="false" /></a>
								</p>
								<button type="button" class="btn btn_grey next"
									onclick="location.href='/action/BoardAction.do?mode=boardView&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.next.seq}" />&projectCode=<c:out value="${projectCode}" />&projectName=<c:out value="${projectName}" />'">다음글</button>
							</td>
							</c:when>
							<c:otherwise>
								<td>
									<p>
										다음글이 없습니다.
									</p>
									<button type="button" class="btn btn_grey next">다음글</button>
								</td>
							</c:otherwise>
							</c:choose>
						</tr>
					</tbody>
				</table>
			</c:if>
			<!-- // Table List Link -->
			</div>
			</div>
</body>
</html>