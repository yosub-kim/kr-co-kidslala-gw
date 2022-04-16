<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>성과급 안내메일 관리 화면</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function selectAll() {
	for (var i = 0; i < document.salaryMailingListForm.elements.length; i++) {
		if (document.salaryMailingListForm.elements[i].name == "ssn[]" && document.salaryMailingListForm.elements[i].disabled==false) {				
			document.salaryMailingListForm.elements[i].checked = document.salaryMailingListForm.checkAll.checked;
		}
	}
}
function sendEmail() {
	var cnt = 0;
	var val = '';
	var msg = '';
	
	for (var i = 0; i < document.salaryMailingListForm.elements.length; i++) {
		if (document.salaryMailingListForm.elements[i].name == "ssn[]"){
			val = 'Y';	 			
		}
		if ((document.salaryMailingListForm.elements[i].name == "ssn[]")&&(document.salaryMailingListForm.elements[i].checked==true)){
			cnt++;
		}
	}
	if (cnt == 0) {	
		if (val == 'Y') {
			alert('성과급 안내메일을 발송할 인력을 선택하지 않았습니다.');  
		} else {
			alert('프로젝트가 존재하지 않습니다.');  
		}
		return; 			
	} else {
		// 이메일 발송 Action 실행
		send();
	}
}

function send(){//작업 필요
	var ssnStr="";
	var ssnList = $$('input[name="ssn[]"]');
	var numOfCheckedSsn = 0;
	for (var i = 0; i < ssnList.length; ++i) {
		if(ssnList[i].checked){
			ssnStr = ssnStr + ssnList[i].value+";";
			numOfCheckedSsn += 1;
		}
	}
	//alert(ssnStr);
	AjaxRequest.post (
		{	'url': "/action/MailCommonAction.do?mode=sendSalaryNoticeEmail",
			'parameters' : { "ssnStr": ssnStr, "year": $("year").value, "month": $("month").value}, 
			'onSuccess':function(obj){
				var res = eval('(' + obj.responseText + ')');
				alert(numOfCheckedSsn + "명에게 Email 전송이 완료되었습니다.");
				doSearch();
			}, 
			'onLoading':function(obj){},
			'onError':function(obj){
				alert("error");
			}
		}
	);
}

function deleteEmailHistory(seq) {
	if (confirm("발송이력을 정말 삭제하시겠습니까?")) {
		AjaxRequest.post (
				{	'url': "/action/EmailLogAction.do?mode=deleteSalaryInfoMailLog",
					'parameters' : { "emailSeq": seq}, 
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						alert("발송이력을 삭제하였습니다.");
						doSearch();
					}, 
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("error");
					}
				}
			);
	} else {
		return;
	}
}

function doSearch() {
	document.salaryMailingListForm.target = "";		
	document.salaryMailingListForm.action = "/action/ExpenseManagerAction.do?mode=getSalaryMailingList";
	document.salaryMailingListForm.submit();
}

function openExpertInfo(ssn) {
	var expertInfo;
	var sURL = "/action/ExpertPoolManagerAction.do?mode=infoview";
	sURL += "&ssn=" + ssn;
	var sFeather = "top=100,left=100,width=800,height=600,scrollbars=yes";
	expertInfo = window.open(sURL, "expertInfo", sFeather);
	expertInfo.focus();
}

function saveListToExcel() {
	var surl = '/action/ExpenseManagerAction.do?mode=saveSalaryMailingListToExcel';
	surl += "&name=" + document.salaryMailingListForm.name.value;
	surl += "&mailingYN=" + document.salaryMailingListForm.mailingYN.value;
	surl += "&year=" + document.salaryMailingListForm.year.value;
	surl += "&month=" + document.salaryMailingListForm.month.value;
	document.location = surl;
}

</script> 
</head>

<body>
	<form name="salaryMailingListForm" method="post">
		<div style='display: none;'>
			<input type="hidden" name="mode" value="getSalaryMailingList">
		</div>
		
		<table width="765" cellpadding="0" cellspacing="0">
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12">
					<% String back = request.getParameter("backButtonYN"); %>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="성과급 안내메일 발송 관리" />
						<jsp:param name="backButtonYN" value="<%=back %>" />
					</jsp:include>
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
							<col width="*"> 
						</colgroup>
						<tr> 
							<td class="searchTitle_center">성명</td>
							<td class="searchField_left">
								<input type="text" class="textInput_left" name="name" id="id" value="<c:out value="${name}"/>">							
							</td>
							<td class="searchTitle_center">수신여부</td>
							<td class="searchField_left">
								<select name="mailingYN" id="mailingYN" class="selectbox" style="width:100%"> 
									<option value=""  <c:if test="${mailingYN == ''  }">selected</c:if>>전체</option>
									<option value="Y" <c:if test="${mailingYN == 'Y' }">selected</c:if>>예</option>
									<option value="N" <c:if test="${mailingYN == 'N' }">selected</c:if>>아니오</option>
								</select>
							</td>
							<td class="searchTitle_center">기간</td> 
							<td class="searchField_left">
							 	<date:year beforeYears="3" afterYears="3" hasAll="Y" attribute=" id='year' name='year' class='selectbox' style='width:60px' " selectedInfo="${year}" />년&nbsp;
							 	<date:month hasAll="Y" attribute=" id='month' name='month' class='selectbox' style='width:60px' " selectedInfo="${month}" />월
							</td>
						</tr>
					</table>
					<jsp:include page="/common/include/searchBox_Footer.jsp" flush="true">
						<jsp:param name="hasbutton" value="Y" />
					</jsp:include>
				</td>
			</tr>			
			<!-- 본문 리스트 시작 -->			
			<tr>
				<td>
					<table width="765" border="0" cellspacing="0" cellpadding="0">
						<!-- 타이틀 영역 -->
						<tr>
							<td height="35px">
							<!-- 페이지 정보, 버튼 -->			
								<div class="btNbox pb15">
									<div class="btNlefTdiv">				
										<img src="/images/sub/line3Blit.gif" alt="">
										<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
										<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
									</div>
									<div class="btNrighTdiv">
										<a class="btN3fac0c txt2btn" href="javascript:saveListToExcel();">Excel 저장</a>
										<a class="btNe14f42 txt2btn" href="javascript:sendEmail();">안내메일발송</a>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<table width="765" class="listTable" id="listTable">
									<thead>
										<tr height="25px">
											<td width="25px"><input type="checkbox" class="checkbox" name="checkAll" onclick="javascript:selectAll();"></td>
											<td width="70px">구분</td>
											<td width="65px">이름</td>
											<td width="70px">소속</td>
											<td width="*">프로젝트</td>
											<td width="75px">PJT<br>성과급</td>
											<td width="65px">총<br>성과급</td>
											<td width="40px">메일<br>발송</td>
										</tr>
									</thead>
									<tbody id="ListData">
										<c:set var="nRowIndex" value="0"/>
										<c:forEach var="rs" items="${list.list}">
											<tr id="<c:out value='${nRowIndex}'/>">
											<c:choose>
												<c:when test="${rs.projectCount > 1 && nRowIndex == 0}">
													<td align="center" rowspan="<c:out value='${rs.projectCount}'/>"><input type="checkbox" class="checkbox" name="ssn[]" <c:if test="${mailingYN == 'N' || rs.emailSendYN == 'Y'}">disabled</c:if> value="<c:out value="${rs.ssn}" />" /></td>
													<td align="center" rowspan="<c:out value='${rs.projectCount}'/>"><c:out value="${rs.jobClassDesc}" /></td>
													<td align="center" onclick="javascript:openExpertInfo('<c:out value='${rs.ssn }' />')" style="cursor:hand;" rowspan="<c:out value='${rs.projectCount}'/>"><c:out value="${rs.name}" /></td>
													<td align="center" rowspan="<c:out value='${rs.projectCount}'/>"><c:out value="${rs.company}" /></td>
													<td class="myoverflow">[<c:out value="${rs.projectCode}" />] <c:out value="${rs.projectName}" /></td>
													<td align="right"><fmt:formatNumber value="${rs.realTimeSalaryEachProject}" pattern="#,###.##"/></td>
													<td align="right" rowspan="<c:out value='${rs.projectCount}'/>"><fmt:formatNumber value="${rs.totalRealTimeSalary}" pattern="#,###.##"/></td>
													<td align="center" rowspan="<c:out value='${rs.projectCount}'/>" title="<fmt:formatDate value="${rs.emailSendDate}" pattern="yyyy-MM-dd HH:mm:ss" />" <c:if test="${rs.emailSendYN == 'Y'}"> onclick="javascript:deleteEmailHistory('<c:out value='${rs.emailSeq }' />')" style="cursor:hand;background-color:#f0f0f0;font-weight:bold;" </c:if>><c:out value="${rs.emailSendYN}" /></td>
													<c:set var="nRowIndex" value="${nRowIndex + 1}"/>
												</c:when>
												<c:when test="${rs.projectCount > 1 && nRowIndex > 0 && nRowIndex < rs.projectCount}">
													<td class="myoverflow" >[<c:out value="${rs.projectCode}" />] <c:out value="${rs.projectName}" /></td>
													<td align="right"><fmt:formatNumber value="${rs.realTimeSalaryEachProject}" pattern="#,###.##"/></td>
													<c:choose>
														<c:when test="${nRowIndex == rs.projectCount - 1}">
															<c:set var="nRowIndex" value="0"/>
														</c:when>
														<c:otherwise>
															<c:set var="nRowIndex" value="${nRowIndex + 1}"/>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<td align="center"><input type="checkbox" class="checkbox" name="ssn[]" <c:if test="${mailingYN == 'N' || rs.emailSendYN == 'Y'}">disabled</c:if> value="<c:out value="${rs.ssn}" />" /></td>
													<td align="center"><c:out value="${rs.jobClassDesc}" /></td>
													<td align="center" onclick="javascript:openExpertInfo('<c:out value='${rs.ssn }' />')" style="cursor:hand;"><c:out value="${rs.name}" /></td>
													<td align="center"><c:out value="${rs.company}" /></td>
													<td class="myoverflow">[<c:out value="${rs.projectCode}" />] <c:out value="${rs.projectName}" /></td>
													<td align="right"><fmt:formatNumber value="${rs.realTimeSalaryEachProject}" pattern="#,###.##"/></td>
													<td align="right"><fmt:formatNumber value="${rs.totalRealTimeSalary}" pattern="#,###.##"/></td>
													<td align="center" title="<fmt:formatDate value="${rs.emailSendDate}" pattern="yyyy-MM-dd HH:mm:ss" />" <c:if test="${rs.emailSendYN == 'Y'}"> onclick="javascript:deleteEmailHistory('<c:out value='${rs.emailSeq }' />')" style="cursor:hand;background-color:#f0f0f0;font-weight:bold;" </c:if>><c:out value="${rs.emailSendYN}" /></td>
													<c:set var="nRowIndex" value="0"/> 
												</c:otherwise>
											</c:choose>
											</tr>
										</c:forEach>
										<c:if test="${empty list.list}">
											<tr><td align="center" colspan="8">검색된 데이터가 없습니다.</td></tr>
										</c:if>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	
	</form>
</body>
</html>