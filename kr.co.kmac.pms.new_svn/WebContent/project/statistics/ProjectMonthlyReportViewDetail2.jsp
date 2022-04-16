<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.common.util.DateUtil"%><html>
<head>
<title>프로젝트별 프로젝트 레포트 관리 상세</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

</head>
<body style="width: 650px; background-color: #FFFFFF;">
	<table width="100%">
		<tr>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
								<jsp:param name="title" value="프로젝트별 프로젝트 레포트 관리 상세" />
								<jsp:param name="backButtonYN" value="N" />
							</jsp:include>
						</td>
					</tr>
			
					<!-- 본문 리스트 시작 -->			
					<tr>
						<td align="left" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="35">
										<!-- 페이지 정보, 버튼 -->			
										<div class="btNbox pb15">
											<div class="btNlefTdiv">				
												<img src="/images/sub/line3Blit.gif" alt="">
												<span class="bold colore74c3a"><c:out value="${result.valueListInfo.totalNumberOfEntries}"/></span>
												<span>Total - Page(<span><c:out value="${result.valueListInfo.pagingPage}"/></span> of <span><c:out value="${result.valueListInfo.totalNumberOfPages}"/></span>)</span>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<table class="listTable">
											<thead>
												<tr>
													<td width="60px" >유형</td>
													<td width="*" >프로젝트명</td>
													<td width="65px" >작성자</td>
													<td width="80px" >할당일자</td>
													<td width="40px" >상태</td>
													<td width="65px">
														<c:choose>
															<c:when test="${state=='reviewer'}">검토자</c:when>
															<c:when test="${state=='approver'}">승인자</c:when>
															<c:when test="${doneState!=''}">담당자</c:when>
															<c:otherwise>-</c:otherwise>
														</c:choose>											
													</td>
												</tr>
											</thead>			
											
														
											<tbody>
												<c:forEach var="result" items="${result.list}">
													<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
														<td><c:out value="${result.businessTypeName}" /></td>
														<td class="myoverflow"><c:out value="${result.projectName}" /></td>
														<td><c:out value="${result.writerName}" /></td>
														<td>
															<fmt:parseDate value="${result.assignDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="date"/>
															<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="formattedFrom"/>
															<c:out value="${formattedFrom}" />
														</td>
														<td>
															<c:choose>
																<c:when test="${result.state=='writer'}">미작성</c:when>
																<c:when test="${result.state=='reviewer'}">검토중</c:when>
																<c:when test="${result.state=='approver'}">승인중</c:when>
																<c:when test="${result.state=='complete'}"><font color="#17375E" style="font-weight: bold;">완료</font></c:when>
																<c:when test="${result.state=='reject'}"><font color="red">반려</font></c:when>
															</c:choose>
														</td>
														<td><!--<c:out value="${result.assignee}" />-->
															<c:choose>
																<c:when test="${state=='reviewer'}"><c:out value="${result.assignee}" /></c:when>
																<c:when test="${state=='approver'}"><c:out value="${result.assignee}" /></c:when>
																<c:when test="${doneState!=''}"><c:out value="${result.assignee}" /></c:when>
																<c:otherwise>-</c:otherwise>
															</c:choose>	
														</td>
													</tr>
												</c:forEach>
												<c:if test="${empty result.list}"><tr><td colspan='6' align='center'><br>검색 결과가 존재하지 않습니다 .<br><br></td></tr></c:if>										
											</tbody>
										</table>
									</td>
								</tr>				
								<!-- 본문 리스트 종료 --> 
								
							</table>
						</td>
					</tr> 
				</table>
			</td>
		</tr>
		<tr>
			<td height="20"></td>
		</tr>
	</table>		
</body>
</html>