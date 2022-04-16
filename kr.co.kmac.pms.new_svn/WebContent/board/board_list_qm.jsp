<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.common.util.DateUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

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
<form name="frm" method="post">
		<table>
			<tr>
				<td height="9"></td>
			</tr>
			<tr>
				<td>
					<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
						<tr>
						<c:choose>
							<c:when test="${projectCode != ''}"> 
								<td style="width:   *;  text-align: left;"><span class="mainTitle">QM 게시판(<c:out value="${projectName}"/>)</span></td>
								<td style="width: 100px; text-align: right;"><a class="btNaaa txt2btn fr" href="javascript:history.back()">이전</a></td>
							</c:when>
							<c:otherwise>
								<td width="100%" align="left" valign="bottom"><span class="mainTitle"><code:code tableName="StandardBBS_Master"  code="${bbsId}"/></span></td>
							</c:otherwise>
						</c:choose>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="10"></td>
			</tr>		
			
			<!-- 검색 영역 -->
			<tr>
				<td>
					<%@ include file="/common/include/searchBox_Header.jsp"%>
					<table border="0" width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse; ">
						<colgroup> 
							<col width="85px">
							<col width="100px">
							<col width="85px"> 
							<col width="100px"> 
							<col width="*"> 
						</colgroup>
						<tr> 
							<td class="searchTitle_center">검색 조건</td>
							<td class="searchField_left">
								<select name="keyfield" class="selectbox" style="width:100%">
									<option value="all" <c:if test="${keyfield == '' }">selected</c:if>>전체</option>
									<option value="subject" <c:if test="${keyfield == 'subject' }">selected</c:if>>제목</option>
									<option value="content" <c:if test="${keyfield == 'content' }">selected</c:if>>내용</option>
									<option value="writer"  <c:if test="${keyfield == 'writer' }">selected</c:if>>작성자</option>
								</select>							
							</td>
							<td class="searchTitle_center">유형</td>
							<td class="searchField_left">
								<select name="keyfield" class="selectbox" style="width:100%">
									<option value="all" <c:if test="${email == '' }">selected</c:if>>전체</option>
									<option value="monitor" <c:if test="${email == 'monitor' }">selected</c:if>>모니터링</option>
									<option value="trouble" <c:if test="${email == 'trouble' }">selected</c:if>>트러블 슈팅</option>
									<option value="needs" <c:if test="${email == 'needs' }">selected</c:if>>니즈발굴</option>
									<option value="other"  <c:if test="${email == 'other' }">selected</c:if>>기타</option>
								</select>							
							</td>
							<td class="searchField_center">
								<input type="text" name="keyword" class="textInput_left" value="<c:out value="${keyword }"/>" style="width:100%">
								<input type="hidden" name="mode" value="selectList">
								<input type="hidden" name="bbsId" value="<c:out value="${bbsId}"/>">
							</td>
						</tr>
					</table>
					<jsp:include page="/common/include/searchBox_Footer.jsp" flush="true">
						<jsp:param name="hasbutton" value="Y" />
					</jsp:include>
				</td>
			</tr>		


			<%
				String dept = (String)session.getAttribute("dept");
				pageContext.setAttribute("dept", dept);
			%>
			
			<!-- 본문 리스트 시작 -->			
			<tr>
				<td align="left" valign="top">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35px">
								<!-- 페이지 정보, 버튼 -->			
								<div class="btNbox pb15">
									<div class="btNlefTdiv">				
										<img src="/images/sub/line3Blit.gif" alt="">
										<span class="bold colore74c3a"><c:out value="${result.valueListInfo.totalNumberOfEntries}"/></span>
										<span>Total - Page(<span><c:out value="${result.valueListInfo.pagingPage}"/></span> of <span><c:out value="${result.valueListInfo.totalNumberOfPages}"/></span>)</span>
									</div>
									<div class="btNrighTdiv">
										<c:choose>
											<c:when test="${projectCode != ''}"> 
												<a class="btNe14f42 txt2btn" href="/action/BoardAction.do?mode=inputForm_qm&bbsId=<c:out value="${bbsId}"/>&projectCode=<c:out value="${projectCode}"/>&projectName=<c:out value="${projectName}"/>">등록</a>
											</c:when>
											<c:otherwise> </c:otherwise>
										</c:choose>
										
									</div>
								</div>								
							</td>
						</tr>				
						<tr>
						
							<td align="left">
								<table class="listTable" style="table-layout:fixed;">
									<thead>
										<tr>
											<td width="70px" >유형</td>
											<td width="*" >제목</td>
											<td width="35px" >첨부</td>
											<td width="72px">작성자</td>
											<td width="90px">작성일</td>
											<td width="40px">조회</td>
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
												<td class="ellipsis" >
													<a href="/action/BoardAction.do?mode=boardView_qm&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.seq}" />&projectCode=<c:out value="${projectCode}" />&projectName=<c:out value="${projectName}" />" >
														<c:out value="${temp}${result.subject}" escapeXml="false"/>&nbsp;
														<c:if test="${!empty result.commentCnt and result.commentCnt ne '0'}">
															<span style="color: red;">[<b><c:out value="${result.commentCnt}" /></b>]</span>
														</c:if>
														<c:if test="${result.recent}">
															<img src="/images/new.gif" border="0" align="absmiddle">
														</c:if>
													</a>
												</td>
												<td align="center">
													<c:if test="${ result.fileCnt > 0 }"><img src="/images/btn_disk.jpg"></c:if>
												</td>
												<td align="center"><c:out value="${result.writer}" /></td>
												<td align="center">
													<fmt:parseDate value="${result.wtime}" type="DATE" dateStyle="SHORT"  pattern="yyyy-MM-dd HH:mm:ss.SSS" var="from"/>
													<fmt:formatDate value="${from}" pattern="yyyy-MM-dd" var="formatted"/>
													<c:out value="${formatted}" />
												</td>
												<td align="center"><c:out value="${result.readCnt}" /></td>
											</tr>
										</c:forEach>
										<c:if test="${empty result.list}"><tr><td colspan='6' align='center'>검색 결과가 존재하지 않습니다.<br></td></tr></c:if>										
									</tbody>
								</table>
							</td>
						</tr>				
						<!-- 본문 리스트 종료 -->
															
 


						<!-- 페이징 -->
						<tr>
							<td align="center">
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="100%" align="center">
											<SCRIPT LANGUAGE="JavaScript">
												document.write( makePageHtml( 
														<c:out value="${result.valueListInfo.pagingPage}" default="1"/>, 
														15, 
														<c:out value="${result.valueListInfo.totalNumberOfEntries}" default="0"/> , 
														15)
												) ;
											</SCRIPT>
										</td>
									</tr>
								</table>									
								
								<input type="hidden" name="mode"     	value="selectList">
								<input type="hidden" name="bbsId"    	value="<c:out value="${bbsId}"/>">
								<input type="hidden" name="keyfield" 	value="<c:out value="${keyfield}"/>">
								<input type="hidden" name="keyword"  	value="<c:out value="${keyword}"/>">
								<input type="hidden" name="pg"       	value="<c:out value="${pg}"/>">
								<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>">
								<input type="hidden" name="projectName" value="<c:out value="${projectName}"/>">
							</td>
						</tr>
						
		<%-- 페이징 영역 끝--%>
					</table>
				</td>
			</tr> 
		</table>
</form>
</body>
</html>