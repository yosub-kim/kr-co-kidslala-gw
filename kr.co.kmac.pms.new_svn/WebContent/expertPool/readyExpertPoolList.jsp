<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>인력 POOL 제외 인력 관리</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
var myDate = new Date();
var nowYear = myDate.getFullYear();
function goPage(next_page) {
	document.readyUserListForm.pageNo.value = next_page ;
	document.readyUserListForm.target = "";		
	document.readyUserListForm.action = "/action/ExpertPoolManagerAction.do?mode=getReadyExpertPoolList";
	document.readyUserListForm.submit();
}
function doSearch() {
	document.readyUserListForm.target = "";		
	document.readyUserListForm.action = "/action/ExpertPoolManagerAction.do?mode=getReadyExpertPoolList";
	document.readyUserListForm.submit();
}
function detail(ssn) {
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}
function getAge(regno){
	var birthYear = regno.substring(0,2);
	var gbn       = regno.substring(6,7);
	var age = 0;
	if ((gbn == "1") || (gbn == "2")) {
		var age = parseInt(nowYear,10) - parseInt("19" + birthYear);
	} else if ((gbn == "3") || (gbn == "4")) {
		var age = parseInt(nowYear,10) - parseInt("20" + birthYear);
	} else {
		var age = "&nbsp;";
	}
	return age;	
}
function restrictClose(){
	$('restirctInfo').hide();
}
function restrictShow(ssn){
	document.readyUserListForm.ssn.value=ssn;
	
	$('restirctInfo').style.top = window.event.clientY;
	$('restirctInfo').style.left = 455;
	$('restirctInfo').show();
}
function restirctRequest() {
	var sFrm = document.forms["readyUserListForm"];
	var status = AjaxRequest.submit(
			sFrm, 
			{	'url': "/action/ExpertPoolManagerAction.do?mode=setReadyUserList",
				'onSuccess':function(obj){
					alert('인력POOL 제외 여부를 설정하였습니다.');
					doSearch();
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("execution Fail");
				}
			}
	); status = null;	
}
</script> 
</head>


<body>
	<form name="readyUserListForm" method="post">
		<div style='display: none;'>
			<input type="hidden" name="pageNo" value="<c:out value="${list.valueListInfo.pagingPage}"/>"> 
			<input type="hidden" name="ssn" value=""/>"> 
		</div>
		
		<table width="100%" cellpadding="0" cellspacing="0">
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12">
					<% String back = request.getParameter("backButtonYN"); %>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="인력 POOL 제외 관리" />
						<jsp:param name="backButtonYN" value="<%=back %>" />
					</jsp:include>
				</td>
			</tr>
			
			<!-- 검색 영역 -->
			<tr>
				<td height="21" align="left" valign="top">
					<%@ include file="/common/include/searchBox_Header.jsp"%>
					<table border="0" width="100%" style="border-collapse: collapse; ">
						<colgroup> 
							<col width="80px">
							<col width="100px">
							<col width="80px">
							<col width="100px">
							<col width="80px">
							<col width="*">
						</colgroup>
						<tr>
							<td class="searchTitle_center">제외여부</td>
							<td class="searchField_center">
								<select name='restrictUser' class='selectbox' style="width: 100%;">
									<option value="Y" <c:if test="${restrictUser=='Y'}">selected</c:if>>예</option>
									<option value="N" <c:if test="${restrictUser=='N'}">selected</c:if>>아니오</option>
								</select>
							</td>
							<td class="searchTitle_center">구분</td>
							<td class="searchField_center">
								<SELECT  name='jobClass' class='selectbox' style='width:105px;'>`
									<option value=''>전체</option>
									<option value="A" <c:if test="${jobClass=='A'}">selected</c:if>>상근</option>
									<option value="J" <c:if test="${jobClass=='J'}">selected</c:if>>상임</option>
									<option value="H" <c:if test="${jobClass=='H'}">selected</c:if>>AA</option>
									<option value="N" <c:if test="${jobClass=='N'}">selected</c:if>>RA</option>
									<option value="C" <c:if test="${jobClass=='C'}">selected</c:if>>엑스퍼트</option>
									<option value="D" <c:if test="${jobClass=='D'}">selected</c:if>>산업계강사</option>
									<option value="E" <c:if test="${jobClass=='E'}">selected</c:if>>대학교수</option>								
									
									<!-- 2019.09.06 (대기)산업계강사, (대기)대학교수 수정 코드 -->									
									<%-- <%if(session.getAttribute("dept").equals("9071") || session.getAttribute("dept").equals("9073") || session.getAttribute("userId").equals("5815choi")){ %>
									<option value="G" <c:if test="${jobClass=='G'}">selected</c:if>>기타</option>
									<option value="F" <c:if test="${jobClass=='F'}">selected</c:if>>(대기)엑스퍼트</option>
									<option value="L" <c:if test="${jobClass=='L'}">selected</c:if>>(대기)산업계강사</option>
									<option value="M" <c:if test="${jobClass=='M'}">selected</c:if>>(대기)대학교수</option>
									<%}%> --%>
								</SELECT>
							</td>
							<td class="searchTitle_center">이름</td>
							<td class="searchField_center">
								<input type="text" name="name" value="<c:out value="${name}"/>" class="textInput_left" style="width:100%;" onkeypress="if(event.keyCode == 13) doSearch();">
							</td>
						</tr>									
					</table>
					<%@ include file="/common/include/searchBox_Footer.jsp" %>
				</td>
			</tr>
			
			<!-- 본문 리스트 시작 -->			
			<tr>
				<td align="left" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35px">
								<!-- 페이지 정보, 버튼 -->			
								<div class="btNbox pb15">
									<div class="btNlefTdiv">				
										<img src="/images/sub/line3Blit.gif" alt="">
										<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
										<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<table class="listTable">
									<thead>
										<tr height="25px">
											<td width="120px">이름</td>
											<td width="40px">연령</td>
											<td width="90px">직업구분</td>
											<td width="135">소속기관</td>
											<td width="135px">소속부서</td>
											<td width="*">직위</td>
											<td width="50px">제외설정</td>
										</tr>
									</thead>			
									<tbody id="ListData">
										<c:choose>
											<c:when test="${!empty list.list}">
												<c:forEach var="rs" items="${list.list}">
													<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
														<td align="center" onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;"><c:out value="${rs.name}" /></td>
														<td align="center"><script>document.write(getAge('<c:out value="${rs.userId}" />'));</script></td>
														<td align="center"><c:out value="${rs.jobClass}" /></td>
														<td align="center"><c:out value="${rs.company}" /></td>
														<td align="center"><c:if test="${jobClass != 'C'}"><c:out value="${rs.deptName}" /></c:if></td>
														<td align="center"><c:out value="${rs.companyPositionName}" /></td>
														<td align="center">
															<img src="/images/main_ti_icon<c:if test="${restrictUser =='N'}">02</c:if>.jpg" alt="제외설정" style="cursor: hand;" onclick="restrictShow('<c:out value="${rs.ssn}" />');" width="18" height="18" border="0">
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr><td align="center" colspan="7">검색된 데이터가 없습니다.</td></tr>
											</c:otherwise>
										</c:choose>
										
									</tbody>
								</table>
							</td>
						</tr>
						<%-- 페이징 영역 시작--%>
						<tr>
							<td align="left">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr height='30'>
										<td width="100%" align="center">
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
						<%-- 페이징 영역 끝--%>
					</table>
				</td>			
			</tr> 
		</table>
		<div id="restirctInfo" 
			style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 2px;">
			<table style="width: 100%" cellpadding="3" cellspacing="3">
				<tr>
					<td><h4 class="subTitle">인력POOL 제외 설정</h4></td>
				</tr>
				<tr>
					<td><table id="restrictInfoTable" width="290px">
						<tbody id="restrictInfoTableHeader">
							<tr>
								<td class="detailTableTitle_center" width="30%">제외하기</td>
								<td class="detailTableField_left" width="*">
									<input type="radio" name="restrictYn" value="Y" <c:if test="${restrictUser=='N'}">checked</c:if>>예&nbsp;&nbsp;&nbsp;
									<input type="radio" name="restrictYn" value="N" <c:if test="${restrictUser=='Y'}">checked</c:if>>아니오
								</td>
							</tr>
						</tbody>
					</table></td>
				</tr>
				<tr>
					<td height="36">
						<div class="btNbox txtR">
						<a class="btNe006bc6 txt2btn" href="#" onclick="restirctRequest()">등록</a>
						<a class="btNa0a0a0 txt2btn" href="#" onclick="restrictClose()">닫기</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>