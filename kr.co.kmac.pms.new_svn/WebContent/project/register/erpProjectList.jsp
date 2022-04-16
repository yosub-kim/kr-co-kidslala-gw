<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function selectErpProject(projectCode) {
	var ActionURL = "/action/ErpProjectAction.do?mode=erpProjectCheck";
	var status = AjaxRequest.post (
			{	'url':ActionURL,
				'parameters' : { "projectCode": projectCode },
				'onSuccess':function(obj){
		           	var res = eval('(' + obj.responseText + ')');
		           	var rsObj = res.isRegistered;
		           	if(rsObj == true){
			           	alert("이미 등록된 프로젝트 입니다. ");
		           	}else{
						$('projectCode').value = projectCode;
						document.erpProjectForm.target = "contentFrame";
						//document.erpProjectForm.action = "/project/projectInfoTab.jsp?viewMode=register&projectCode="+projectCode;
						document.erpProjectForm.action = "/action/ProjectSearchAction.do"
							+"?mode=getProjectInfoTab&viewMode=register&projectCode="+projectCode
						document.erpProjectForm.submit();	
		           	}
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;	
	
}
function goPage(next_page) {
	document.erpProjectForm.pageNo.value = next_page ;
	document.erpProjectForm.target = "";		
	document.erpProjectForm.action = "/action/ErpProjectAction.do?mode=getErpProjectList";
	document.erpProjectForm.submit();
}
function closePopup() {
	window.parent.Windows.close("modal_window");
}
</script> 
</head>

<body style="margin-left: 5px; width: 650px; background-color: #FFFFFF;">
		<div style="display: none;">
			<form name="erpProjectForm" method="post">
				<input type="text" name="projectCode" id="projectCode" />
				<input type="text" name="pageNo" />
			</form>
		</div>

	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td valign="top">
				<table width="100%" cellpadding="0" cellspacing="0"> 

					<!-- sub 타이틀 영역 시작-->  
					<tr>
						<td>
							<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
								<jsp:param name="title" value="프로젝트 예산 목록" />
							</jsp:include>
						</td>
					</tr>
					<!-- sub 타이틀 영역 종료--> 
					
				 	
					<tr>
						<td>
							<div class="btNbox pb15">
								<div class="btNlefTdiv">				
									<img src="/images/sub/line3Blit.gif" alt="">
									<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
									<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
								</div>
							</div>
						</td>
					</tr>
					<tr><td height='5'></td></tr>
					
					
					
						<tr>
							<td>
								<table class="listTable" width="100%" cellpadding="0" cellspacing="0">
									<thead>
										<tr>
											<td width="80px">프로젝트 코드</td>
											<td width="80px">산업 구분</td>
											<td width="80px">Biz 구분</td>
											<td width="300px">프로젝트 명</td>
											<td width="110px">등록일</td>
										</tr>
									</thead>			
									<tbody id="ListData">
										<c:forEach var="rs" items="${list.list}">
											<tr onmouseover="row_over(this)" onmouseout="row_out(this)" onclick="selectErpProject('<c:out value="${rs.projectCode}" />')" style="cursor: hand;">
												<td><c:out value="${rs.projectCode}" /></td>
												<td><c:out value="${rs.indsTypeName}" /></td>
												<td><c:out value="${rs.projectTypeName}" /></td>
												<td class="myoverflow"><c:out value="${rs.projectName}" /></a></td>
												<td><c:out value="${rs.inputDate}" /></td>
											</tr>
										</c:forEach>
										<c:if test="${empty list.list}">
											<tr><td colspan="5"><br>등록한 프로젝트 예산 목록이 없습니다.<br><br></td></tr>
										</c:if>
									</tbody>
								</table>
							</td>
						</tr>
						<%-- 페이징 영역 시작--%>
						<tr>
							<td>
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr height='30'>
										<td width="100%" align="center">
											<SCRIPT LANGUAGE="JavaScript">
												document.write( makePageHtml( 
														<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
														10, 
														<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
														10)
												) ;
											</SCRIPT>
										</td>
									</tr>
								</table>									
							</td>
						</tr>
						<%-- 페이징 영역 끝--%>						
		<!-- 본문종료-->
		<!-- 버튼부분-->
						<tr>
							<td height="7"></td>
						</tr>
						<tr>
							<td>
								<table width="100%" height="40" bgcolor='#F3F3F3' cellpadding="0" cellspacing="0">
									<tr>
										<td style="padding-right:5">
											<div class="btNbox txtR">
												<a class="btNa0a0a0 txt2btn" href="javascript:closePopup()">닫기</a>
											</div>
										</td>
									</tr>
								</table>
								
							</td>
						</tr>
		<!-- 버튼종료-->

					</table>
				</td>
			</tr>
		</table>						
	
</body>

</html>					