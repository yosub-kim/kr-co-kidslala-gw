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
function btnNew_onclick(){
	document.location = "/action/SanctionTemplateAction.do?mode=loadFormSanctionTemplate";
}
function doSearch() {
	// do nothing
}
</script>
</head>

<body>
<div style="margin: 70 0 0 20 ">
		<table width="751" cellpadding="0" cellspacing="0">
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12">
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="전자결재 템플릿 관리" />
					</jsp:include>
				</td>
			</tr>

			<!-- 검색 영역 -->
			<tr>
				<td height="21" align="left" valign="top">
					<%@ include file="/common/include/searchBox_Header.jsp"%>
					<table border="0" width="100%" style="padding-right: 3px;">
						<colgroup> 
							<col width="100px">
							<col width="*"> 
						</colgroup>
						<tr>
							<td class="searchTitle_center">결재유형</td>
							<td class="searchField_left">
								<input type="text" name="projectName" value="<c:out value="${projectName}"/>" class="textInput_left" style="width: 100%;">
							</td>
						</tr>									
					</table>
					<%@ include file="/common/include/searchBox_Footer.jsp"%>
				</td>
			</tr>			

			<!-- 본문 리스트 시작 -->			
			<tr>
				<td align="left" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="34px">
								<div class="btNbox pb15">
									<div class="btNlefTdiv">				
										<img src="/images/sub/line3Blit.gif" alt="">
										<span class="bold colore74c3a"><c:out value="${listSize}"/></span>
										<span>Total</span>
									</div>
								</div>	
							</td>
						</tr>
						<tr>
							<td>
								<table class="listTable">
									<colgroup>
										<col width="100px" />
										<col width="*" />
										<col width="100px" />
										<col width="100px" />
										<col width="100px" />
										<col width="100px" />
									</colgroup>
									<thead>
										<tr> 
											<td>결재유형코드</td>
											<td>결재유형</td>
											<td>등록일</td>
											<td>등록자</td>
											<td>수정일</td>
											<td>수정자</td>
										</tr>
									</thead>									
									<tbody id="ListData">
										<c:forEach var="rs" items="${list}">
											<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
												<td style="text-align: center"><c:out value="${rs.approvalType}" /></td>
												<td style="text-align: left"><a href="/action/SanctionTemplateAction.do?mode=loadFormSanctionTemplate&approvalType=<c:out value="${rs.approvalType}" />" ><c:out value="${rs.approvalName}" /></a></td>
												<td style="text-align: center"><c:out value="${rs.createdDate}" /></td>
												<td style="text-align: center"><expertPool:exportname ssn="${rs.createUser}" /></td>
												<td style="text-align: center"><c:out value="${rs.updatedDate}" /></td>
												<td style="text-align: center"><expertPool:exportname ssn="${rs.updateUser}" /></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						
						<tr>
							<td height='7'></td>
						</tr>
						<tr>
							<td>
								<table width="751" height="36" bgcolor='#F3F3F3' cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<div class="btNbox txtR">
												<a title="신규" class="btNe14f42 txt2btn" href="#" onclick="javascirpt:btnNew_onclick()">추가</a>
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
</div>
</body>

</html>					