<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript" src="/js/contextMenu/contextMenu.js"></script> 
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function goProjectDetail(projectCode, projectRole) {
	document.location.href = "/action/ProjectSearchAction.do"
		+"?mode=getProjectInfoTab&viewMode=myProject&projectCode="+projectCode+"&projectRole="+projectRole;
}
function goPage(next_page) {
	document.searchResultForm.pageNo.value = next_page ;
	document.searchResultForm.target = "";		
	document.searchResultForm.action = "/action/SearchAction.do?mode=serchRequest";
	document.searchResultForm.submit();
}

function doSearch() {
	document.searchResultForm.target = "";		
	document.searchResultForm.action = "/action/SearchAction.do?mode=serchRequest";
	document.searchResultForm.submit();
}
function fileDownload(uuid){
	if(uuid != ""){
		if($$("#_targetDownload").length == 0)
			document.body.insertAdjacentHTML('beforeEnd', "<iframe id=\"_targetDownload\" src=\"\" style=\"display:none\"></iframe>");
    	$("_targetDownload").src = "/servlet/RepositoryDownLoadServlet?fileId=" + uuid;
	}
}
</script> 
</head>


<body>
	<form name="searchResultForm" method="post">
		<div style='display: none;'>
			<input type="hidden" name='pageNo' value="<c:out value="${list.valueListInfo.pagingPage}"/>">		
		</div>
		 
		<table width="100%" cellpadding="0" cellspacing="0">
			
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12">
					<% String back = request.getParameter("backButtonYN"); %>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="파일 검색" />
						<jsp:param name="backButtonYN" value="Y" />
					</jsp:include>
				</td>
			</tr>
			
			<!-- 검색 영역 -->
			<tr>
				<td height="21" align="left" valign="top">
					<%@ include file="/common/include/searchBox_Header.jsp"%>
					<table width="100%" height="52" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td class="searchField_center">
								<input type="text" name="keyword" value="<c:out value="${keyword}"/>" class="textInput_left" style="width: 100%;" onkeypress="if(event.keyCode == 13) doSearch();">
							</td>
						</tr>
						<tr>
							<td class="searchField_center">
								<input type="checkbox" value="1" name="projectNameChk" id="projectNameChk" <c:if test="${projectNameChk eq '1' }">checked</c:if>>프로젝트명&nbsp;&nbsp;
								<input type="checkbox" value="1" name="projectCodeChk" id="projectCodeChk" <c:if test="${projectCodeChk eq '1' }">checked</c:if>>프로젝트코드&nbsp;&nbsp;
								<input type="checkbox" value="1" name="customerNameChk" id="customerNameChk" <c:if test="${customerNameChk eq '1' }">checked</c:if>>고객사명&nbsp;&nbsp;
								<input type="checkbox" value="1" name="attachFileNameChk" id="attachFileNameChk" <c:if test="${attachFileNameChk eq '1' }">checked</c:if>>파일명&nbsp;&nbsp;
								<input type="checkbox" value="1" name="attachOutputNameChk" id="attachOutputNameChk" <c:if test="${attachOutputNameChk eq '1' }">checked</c:if>>첨부문서명&nbsp;&nbsp;
								<input type="checkbox" value="1" name="hashTagChk" id="hashTagChk" <c:if test="${hashTagChk eq '1' }">checked</c:if>>해시태그&nbsp;&nbsp;
								<input type="checkbox" value="1" name="attachCreatorNameChk" id="attachCreatorNameChk" <c:if test="${attachCreatorNameChk eq '1' }">checked</c:if>>등록자
								
							</td>
						</tr>
					</table>
					<%@ include file="/common/include/searchBox_Footer.jsp"%>
				</td>
			</tr>
			
						
			<!-- 본문 리스트 시작 -->			
			<tr>
				<td align="left" valign="top">
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35px">
								<!-- 페이지 정보, 버튼 -->			
								<div class="btNbox pb15">
									<div class="btNlefTdiv">
										<c:if test="${!empty list.list}">
											<img src="/images/sub/line3Blit.gif" alt="">
											<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
											<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
										</c:if>				
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td align="left">
								<c:if test="${!empty list.list}">
									<c:forEach var="rs" items="${list.list}">
										<div class="outLine1" style="margin:0px 0px 8px 0px; padding: 5px; line-height: 1.5 ">
											<span style="color: blue; cursor: pointer; margin: 0px 5px 5px 0px; font-weight: bold;" 
												onclick="javascript: fileDownload('<c:out value="${rs.attachFileId}" />')"><c:out value="${rs.attachFileName}" /></span></br>
												
											<c:if test="${not empty rs.projectName}"><span style="font-weight: bold;">프로젝트:</span> [<c:out value="${rs.projectCode}" />] <c:out value="${rs.projectName}" /></br>    </c:if>         
											<c:if test="${not empty rs.customerName}"><span style="font-weight: bold;">고객사: </span><c:out value="${rs.customerName}" />            </br></c:if>
											        
											<c:if test="${not empty rs.attachOUtputTypeName}not empty "><span style="font-weight: bold;">분류:</span>[<c:out value="${rs.attachOUtputTypeName}" />]</c:if>
											<c:if test="${not empty rs.attachOutputBusTypeName}">[<c:out value="${rs.attachOutputBusTypeName}" />]</c:if>
											<c:if test="${not empty rs.attachOutputName}"><c:out value="${rs.attachOutputName}" />   </br></c:if>
											<c:if test="${not empty rs.attachCreateDate}"><span style="font-weight: bold;">등록일:</span> <c:out value="${rs.attachCreateDate}" />        </br></c:if>
											<c:if test="${not empty rs.attachCreatorName}"><span style="font-weight: bold;">등록자:</span> <c:out value="${rs.attachCreatorName}" />       </br></c:if>
											<c:if test="${not empty rs.hashTag}">HashTag: <c:out value="${rs.hashTag}" />       </br></c:if>
											
											<span style="display: none;"><c:if test="${not empty rs.taskFormTypeName}"><c:out value="${rs.taskFormTypeName}" />    </br></c:if></span>
											<span style="display: none;"><c:out value="${rs.taskId}" />                  </br></span>
											<span style="display: none;"><c:out value="${rs.taskFormTypeId}" />          </br>`</span>
											<span style="display: none;"><c:out value="${rs.attachOutputBusType}" />     </br></span>
										</div>
										
										
										
									</c:forEach>
								</c:if>
								<c:if test="${empty list.list}">
									<td colspan="10" style="text-align: center;">검색된 데이터가 없습니다. </td>
								</c:if>
							</td>
						</tr>				
						<!-- 본문 리스트 종료 -->
						
			
						<!-- 페이징 -->
						<tr>
							<td align="center">
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="100%" align="center">
										<SCRIPT LANGUAGE="JavaScript">
											document.write( makePageHtml( 
													<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
													15, 
													<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
													15)
											) ;
										</SCRIPT>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</td>
			</tr> 
		</table>
	</form> 
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>
</html>