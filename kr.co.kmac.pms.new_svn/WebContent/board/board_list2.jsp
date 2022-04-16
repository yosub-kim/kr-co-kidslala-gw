<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.common.util.DateUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<!-- 서브페이지에서만 사용 -->
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function btnNew_onclick(){
	document.location = "/action/BoardAction.do?mode=inputForm_qm";
}
function doSearch() {
	frm.submit();
}
function goPage(pageNumber){
	frm.pg.value = pageNumber;
	frm.submit();
}
</script>
</head>
<body>
		<div id="sub_location">
			<div class="location">
				<p class="menu_title">
					<td width="100%" align="left">QM 게시판</td>
				</p>
				<ul>
					<li class="home">HOME</li>
					<li>게시판</li>
					<li><c:out value="${projectName }" /></li>
				</ul>
			</div>
		</div>
		
		<form name="frm" method="post">
			<div class="contents sub"><!-- 서브 페이지 .sub -->
			<!-- 검색 창 -->					
				<div class="box">
					<div class="search_box total">
						<div class="select_group">
							<div class="select_box">
								<div class="label_group">
									<p>검색 조건</p>
									<select name="keyfield" class="SELECT">
										<option value="all" <c:if test="${keyfield == '' }">selected</c:if>>전체</option>
										<option value="subject" <c:if test="${keyfield == 'subject' }">selected</c:if>>제목</option>
										<option value="content" <c:if test="${keyfield == 'content' }">selected</c:if>>내용</option>
										<option value="writer"  <c:if test="${keyfield == 'writer' }">selected</c:if>>작성자</option>
									</select>
								</div>
								<div class="label_group">
									<p>유형</p>
									<select name="keyfield" class="select" style="width:100%">
										<option value="all" <c:if test="${email == '' }">selected</c:if>>전체</option>
										<option value="monitor" <c:if test="${email == 'monitor' }">selected</c:if>>모니터링</option>
										<option value="trouble" <c:if test="${email == 'trouble' }">selected</c:if>>트러블 슈팅</option>
										<option value="needs" <c:if test="${email == 'needs' }">selected</c:if>>니즈발굴</option>
										<option value="other"  <c:if test="${email == 'other' }">selected</c:if>>기타</option>
									</select>		
								</div>
								<div class="label_group">
									<p>내용</p>
									<input type="text" name="keyword" value="<c:out value="${keyword }"/>">
									<input type="hidden" name="mode" value="selectList">
									<input type="hidden" name="bbsId" value="<c:out value="${bbsId}"/>">
								</div>
								<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색<i class="mdi mdi-magnify"></i></button></div>
							</div>
					<!-- // search_box -->
					</div>
				<!-- // 검색 창 -->
				</div>
			</div>
					<!-- 본문 리스트 시작 -->
			<div class="box">
				<div class="a-both total">
					<p>
						총<span><c:out value="${result.valueListInfo.totalNumberOfEntries}" /></span>건
					</p>
					<button type="button" class="btn btn_aqua" onclick="location.href='/action/BoardAction.do?mode=inputForm_qm&bbsId=<c:out value="${bbsId}"/>&projectCode=<c:out value="${projectCode}"/>&projectName=<c:out value="${projectName}"/>'"/>등록</button>
				</div>
				<div class="tbl-wrap sc">
					<table class="tbl-list">
						<thead>
							<tr>
								<th>유형</th>
								<th class="subject">제목</th>
								<th>첨부</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>조회</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="result" items="${result.list}">
							<tr onmouseout="row_out(this)" <c:choose><c:when test="${result.topArticle == 'Y'}">onmouseover="topArticleRow_over(this);" class="topArticle"</c:when><c:otherwise> onmouseover="row_over(this)"</c:otherwise></c:choose>>
								<td align="center">
									<c:choose>
										<c:when test="${result.email == 'A' }">모니터링</c:when>
										<c:when test="${result.email == 'B' }">트러블 슈팅</c:when>
										<c:when test="${result.email == 'C' }">니즈발굴</c:when>
										<c:when test="${result.email == 'D' }">기타</c:when>
									</c:choose>
								</td>
								<c:set value="" var="temp" />
								<c:choose>
									<c:when test="${result.lev > 0}">
										<c:forEach var="i" begin="0" end="${result.lev-1}">
											<c:set value="${temp}&nbsp;&nbsp;&nbsp;" var="temp" />	
										</c:forEach>
										<c:set value="${temp}└ " var="temp" />				
									</c:when>
								</c:choose>
								<td class="subject"><p>
									<c:set value="" var="temp" />
										<c:choose>
											<c:when test="${result.lev > 0}">
												&nbsp;&nbsp;&nbsp;<span class="comment">댓글</span>			
											</c:when>
										</c:choose>
										<a href="/action/BoardAction.do?mode=boardView_qm&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.seq}" />&projectCode=<c:out value="${projectCode}" />&projectName=<c:out value="${projectName}" />" >
										<c:out value="${temp}${result.subject}" escapeXml="false"/>&nbsp;
										<c:if test="${!empty result.commentCnt and result.commentCnt ne '0'}">
											<span><span class="reply">[<c:out value="${result.commentCnt}" />]</span></span>
										</c:if>
										<c:if test="${result.recent}">
											<span><span class="new">N</span></span>
										</c:if>
										</a></p>
								</td>
								<td class="file">
									<c:if test="${ result.fileCnt > 0 }"><a></a></c:if>
								</td>
								<td><c:out value="${result.writer}" /></td>
								<c:choose><c:when test="${bbsId eq 'templatebbs' }"></c:when><c:otherwise><td align="center">
									<fmt:parseDate value="${result.wtime}" type="DATE" dateStyle="SHORT"  pattern="yyyy-MM-dd HH:mm:ss.SSS" var="from"/>
									<fmt:formatDate value="${from}" pattern="yyyy-MM-dd" var="formatted"/>
									<c:out value="${formatted}" />
								</td></c:otherwise></c:choose>
								<c:choose><c:when test="${bbsId eq 'templatebbs' }"></c:when><c:otherwise><td align="center"><c:out value="${result.readCnt}" /></td></c:otherwise></c:choose>
								<c:if test="${bbsId eq 'sharebbs'}"><td align="center"><c:out value="${result.likeCnt }" /></td></c:if>
							</tr>
						</c:forEach>
						<c:if test="${empty result.list}"><tr><td colspan='6' align='center'>검색 결과가 존재하지 않습니다.<br></td></tr></c:if>										
					</tbody>
				</table>
			</div>
			<div class="paging_area">
					<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml2( 
									<c:out value="${result.valueListInfo.pagingPage}" default="1"/>, 
									10, 
									<c:out value="${result.valueListInfo.totalNumberOfEntries}" default="0"/> , 
									10)
							) ;
					</SCRIPT>
				</div>
		</div>
		<input type="hidden" name="mode"     	value="selectList">
				<input type="hidden" name="bbsId"    	value="<c:out value="${bbsId}"/>">
				<input type="hidden" name="keyfield" 	value="<c:out value="${keyfield}"/>">
				<input type="hidden" name="keyword"  	value="<c:out value="${keyword}"/>">
				<input type="hidden" name="pg"       	value="<c:out value="${pg}"/>">
				<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>">
				<input type="hidden" name="projectName" value="<c:out value="${projectName}"/>">
</form>
</body>
</html>