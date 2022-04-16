<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
	location.href="/action/BoardAction.do?mode=inputForm_qm&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.boardData.seq}" />&saveMode=UPDATE&projectCode=<c:out value="${projectCode}"/>&projectName=<c:out value="${projectName}"/>";
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
</head>
<body>
	<%-- location --%>
	<div class="location">
		<p class="menu_title">QM 게시판</p>
		<ul>
			<li class="home">HOME</li>
			<li>게시판</li>
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
						<c:out value="${result.boardData.subjectNoTag}" escapeXml="false" />
					</p>
					<div class="btn_area">
						<button type="button" class="btn line btn_aqua"
							onclick="location.href='/action/BoardAction.do?mode=inputForm_qm&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.boardData.seq}" />&saveMode=REPLY'">
							<i class="mdi mdi-message-reply-text-outline copy 2"></i>답글
						</button>
					</div>
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
						<c:when test="${ssn eq 'A000128' || ssn eq 'A000132' || ssn eq 'A001561' || ssn eq 'H000087' || ssn eq 'A000031'}">
							<button type="button" class="btn line btn_blue"
								onclick="location.href='/action/BoardAction.do?mode=inputForm&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.boardData.seq}" />&saveMode=UPDATE&projectCode=<c:out value="${projectCode}"/>&projectName=<c:out value="${projectName}"/>'">
								<i class="mdi mdi-square-edit-outline"></i>수정
							</button>
							<button type="button" class="btn line btn_pink"
								onclick="location.href='javascript:deleteBoard();'">
								<i class="mdi mdi-trash-can-outline"></i>삭제
							</button>
						</c:when>
						<c:when test="${ssn eq 'H003949' || ssn eq 'A000106' || ssn eq 'A000024'}">
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
								onclick="location.href='/action/BoardAction.do?mode=inputForm_qm&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.boardData.seq}" />&saveMode=UPDATE&projectCode=<c:out value="${projectCode}"/>&projectName=<c:out value="${projectName}"/>'">
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
							<td><c:out value="${result.boardData.writer}"/></td>
							<th>작성일</th>
							<td><fmt:parseDate value="${result.boardData.wtime}" type="DATE" dateStyle="SHORT" pattern="yyyy-MM-dd HH:mm:ss.SSS" var="from" /> 
								<fmt:formatDate value="${from}" pattern="yyyy-MM-dd" var="formatted" /> 
								<c:out value="${formatted}" />
							</td>
						<tr>
						<tr>
							<th>유형</th>
							<td colspan="3">
								<c:choose>
									<c:when test="${result.boardData.email == 'A' }">모니터링</c:when>
									<c:when test="${result.boardData.email == 'B' }">트러블 슈팅</c:when>
									<c:when test="${result.boardData.email == 'C' }">니즈발굴</c:when>
									<c:when test="${result.boardData.email == 'D' }">기타</c:when>
								</c:choose>
							</td>
						</tr>
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
			</div>
		</div>
	</div>
</body>
</html>