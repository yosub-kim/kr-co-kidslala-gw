<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.common.util.DateUtil"%>
<html>
<head>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Generator" content="EditPlus" charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=1600">

<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<!-- 서브페이지에서만 사용 -->
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<script type="text/javascript">
	
<%-- 개별 스크립트 영역 --%>
function btnNew_onclick() {
	document.location = "/action/BoardAction.do?mode=inputForm";
}
function doSearch() {
	frm.submit();
}
function goPage(pageNumber) {
	frm.pg.value = pageNumber;
	frm.submit();
}
function viewNewbasisbbs(seq) {
	var url = "/action/BoardAction.do?mode=boardView_popup&bbsId=newbasisbbs&seq="
			+ seq + "&projectCode=&projectName=";
	var sFeatures = "top=140,left=140,width=980,height=495,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "viewNewbasisbbs", sFeatures);
	detailWin.focus();
}
</script>
</head>

<body>
	<div id="sub_location">
		<div class="location">
			<p class="menu_title">
				<c:choose>
					<c:when test="${projectCode != ''}">
						<td width="100%" align="left">프로젝트 게시판</td>
					</c:when>
					<c:otherwise>
						<td width="100%" align="left"><span class="mainTitle"><code:code tableName="StandardBBS_Master"  code="${bbsId}"/></span></td>
					</c:otherwise>
				</c:choose>	
			</p>
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
	</div>
	<!-- // location -->
	
	<form name="frm" method="post">
		<!-- 서브 페이지 .sub -->
		<div class="contents sub">
			
			<c:if test="${bbsId eq 'qna'}">
				<!-- 안내 문구 박스 -->
				<div class="box">
					<div class="info_box">
						<p class="info_title">회사 업무 시스템 IT 서비스,인프라 관련 문의 시 신속히 답변
							드리겠습니다.</p>
						<p class="info_text">
							· 회사 업무 시스템 활용 방법과 관련된 궁금증 및 활용시 어려운 점이 있을 경우 편하게 문의 주시기 바랍니다.<br />
							· 문의 사항과 관련된 화면을 캡쳐하여 첨부해 주시면 좀 더 정확한 답변이 가능합니다.<br /> · 문의 주신
							사항에 대해서는 신속히 확인하여, 성심성의껏 답변 드리겠습니다.
						</p>
					</div>
					<!-- // info_box -->
				</div>
				<!-- // 안내 문구 박스 -->
			</c:if>

			<c:if test="${bbsId eq 'newbasisbbs'}">
				<!-- 주요 기준지침 바로가기 -->
				<div class="box">
					<div class="link_box">
						<div class="link_ui">
							<p>주요 기준지침 바로보기</p>
							<ul>
								<li><a href="javascript:viewNewbasisbbs('46771');"> <span><i
											class="mdi mdi-office-building-outline"></i></span>
										<p>사내규정 및 운영기준</p>
								</a></li>
								<li><a href="javascript:viewNewbasisbbs('51009');"> <span><i
											class="mdi mdi-fountain-pen-tip"></i></span>
										<p>전결 규정</p>
								</a></li>
								<li><a href="javascript:viewNewbasisbbs('46772');"> <span><i
											class="mdi mdi-account-star-outline"></i></span>
										<p>상임 및 엑스퍼트 운영기준</p>
								</a></li>
								<li><a href="javascript:viewNewbasisbbs('51874');"> <span><i
											class="mdi mdi-sitemap"></i></span>
										<p>조직 및 프로젝트 운영지침</p>
								</a></li>
								<li><a href="javascript:viewNewbasisbbs('50970');"> <span><i
											class="mdi mdi-domain"></i></span>
										<p>협력사 운영지침</p>
								</a></li>
								<!-- <li><a href="javascript:viewNewbasisbbs('51990');"> <span><i
											class="mdi mdi-security"></i></span>
										<p>보안관리 규정</p>
								</a></li> -->
							</ul>
						</div>
					</div>
					<!-- // link_box -->
				</div>
			</c:if>
			<!-- 검색 영역 -->
			<!-- 검색 창 -->
			<div class="box">
				<div class="search_box">
					<div class="select_group">
					<div class="search_input_box">
						<select  name="keyfield" class="select">
							<option value=subject
								<c:if test="${keyfield == 'subject' }">selected</c:if>>제목</option>
							<option value="content"
								<c:if test="${keyfield == 'content' }">selected</c:if>>내용</option>
							<option value="writer"
								<c:if test="${keyfield == 'writer' }">selected</c:if>>작성자</option>
						</select>
					<div class="search_input">
						<label for="search"></label> 
							<input type="text" name="keyword" id="keyword" placeholder="검색어를 입력하세요." title="검색어를 입력하세요." value="<c:out value="${keyword }"/>" />
							<input type="hidden" name="mode" value="selectList"> 
							<input type="hidden" name="bbsId" value="<c:out value="${bbsId}"/>">
					</div>
					</div>
				</div>
				<div><button type="button" class="btn btn_blue" onclick="location.href='javascript:doSearch();'">검색</button></div>
				</div>
				<!-- // search_box -->
			</div>
			<!-- // 검색 창 -->

			<%
				String dept = (String) session.getAttribute("dept");
				pageContext.setAttribute("dept", dept);
			%>
			<!-- 본문 리스트 시작 -->
			<div class="box">
				<div class="a-both total">
					<p>
						총<span><c:out value="${result.valueListInfo.totalNumberOfEntries}" /></span>건
					</p>
					<c:choose>
						<c:when test="${projectCode != '' and bbsId ne 'faq'}">
							<button type="button" class="btn btn_aqua"
								onclick="location.href='/action/BoardAction.do?mode=inputForm&bbsId=<c:out value="${bbsId}"/>&projectCode=<c:out value="${projectCode}"/>&projectName=<c:out value="${projectName}"/>'">등록</button>
						</c:when>
						<c:when test="${bbsId eq 'publicNotice' }">
							<c:choose>
								<c:when test="${jobClass eq 'A' }">
									<button type="button" class="btn btn_aqua"
										onclick="location.href='/action/BoardAction.do?mode=inputForm&bbsId=<c:out value="${bbsId}"/>'">등록</button>
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<button type="button" class="btn btn_aqua" onclick="location.href='/action/BoardAction.do?mode=inputForm&bbsId=<c:out value="${bbsId}"/>'">등록</button>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="tbl-wrap sc">
					<table class="tbl-list all">
						<thead>
							<tr>
								<th>번호</th>
								<th class="subject">제목</th>
								<th>첨부</th>
								<th>작성자</th>
								<c:choose><c:when test="${bbsId eq 'templatebbs' }"></c:when><c:otherwise><th>작성일</th></c:otherwise></c:choose>
								<c:choose><c:when test="${bbsId eq 'templatebbs' }"></c:when><c:otherwise><th>조회</th></c:otherwise></c:choose>
								<c:if test="${bbsId eq 'sharebbs'}"><th>좋아요</th></c:if>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="result" items="${result.list}">
							<tr onmouseout="row_out(this)" <c:choose><c:when test="${result.topArticle == 'Y'}">onmouseover="topArticleRow_over(this);" class="topArticle"</c:when><c:otherwise> onmouseover="row_over(this)"</c:otherwise></c:choose>>
								<td><c:out value="${result.ref}" /></td>
								<td style="cursor:pointer" class="subject" onclick= "location.href='/action/BoardAction.do?mode=boardView&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.seq}" />&projectCode=<c:out value="${projectCode}" />&projectName=<c:out value="${projectName}" />'"><p>
										<c:set value="" var="temp" />
										<c:choose>
											<c:when test="${result.lev > 0}">
												&nbsp;&nbsp;&nbsp;<span class="comment">댓글</span>			
											</c:when>
										</c:choose>
										<c:if test="${result.prjType == '2' }"><b>《상근》</b></c:if>
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
									<c:if test="${ result.fileCnt > 0 }"><a href="javascript">첨부</a></c:if>
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
				<!-- // tbl-wrap -->
				
				<!-- // paging -->
				<input type="hidden" name="mode"     	value="selectList">
				<input type="hidden" name="bbsId"    	value="<c:out value="${bbsId}"/>">
				<input type="hidden" name="keyfield" 	value="<c:out value="${keyfield}"/>">
				<input type="hidden" name="keyword"  	value="<c:out value="${keyword}"/>">
				<input type="hidden" name="pg"       	value="<c:out value="${pg}"/>">
				<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>">
				<input type="hidden" name="projectName" value="<c:out value="${projectName}"/>">
			</div>
			<!-- // box -->

			<!-- // contents_are -->
		</div>
	</form>
</body>
</html>