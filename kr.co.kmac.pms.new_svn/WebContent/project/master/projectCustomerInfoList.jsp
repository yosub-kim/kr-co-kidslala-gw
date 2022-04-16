<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>니즈발굴 고객정보 연계</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function insertNewCI(){
	var val = document.projectCustomerInfoForm.isAdmin.value;
	var url = "http://intranet.kmac.co.kr/kmac/comlist/customer_list_p.asp?projectCode="+<c:out value="${project.projectCode}"/>;
	if (val.length>0) {
		url = url + "&isAdmin=" + val;
	}
	document.location.href = url;
	//document.location.href = "http://intranet.kmac.co.kr/kmac/comlist/customer_list_p.asp?projectCode="+<c:out value="${project.projectCode}"/>;
}
function deleteSelectedCI(){
	if(confirm("선택하신 고객정보의 연계를 삭제하시겠습니까?")){
		document.projectCustomerInfoForm.target = "";
		document.projectCustomerInfoForm.action = "?mode=deleteProjectCustomerInfo";
		document.projectCustomerInfoForm.submit();
	}
}
function openCustomerInfo(customerInfoIdx){
	var url = "http://intranet.kmac.co.kr/kmac/comlist/customer_detail.asp?idx="+customerInfoIdx;
	window.open(url, 'CustomerInfo',
			'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=795,height=560,directories=no,menubar=no');
}
</script>
</head>

<body>
	<form name="projectCustomerInfoForm" method="post">
	<div style="display: none;">
		<input type="text" name="projectCode" id="projectCode" value="<c:out value="${project.projectCode}"/>" >
		<input type="text" name="isAdmin" id="isAdmin" value="<c:out value="${isAdmin}"/>" >
	</div>
	<table width="751" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0">

					<!-- sub 타이틀 영역 시작--> 
					<tr>
						<td><h4 class="subTitle mt15 mb5">프로젝트 연계 고객 정보</h4></td>
					</tr>
					<!-- sub 타이틀 영역 종료--> 

					<tr>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<table id="projectCustomerInfoTable" width="100%" class="listTable">
											<thead id="projectCustomerInfoTableHeader"> 
												<tr> 
													<td style="width: 30px;" >선택</td>
													<td style="width: 60px;" >유형</td>
													<td style="width: *;" >제목</td>
													<td style="width: 60px; text-align: center;">작성자</td>
													<td style="width: 80px; text-align: center;">작성일</td>
													<td style="width: 60px; text-align: center;">연계자</td>
													<td style="width: 80px; text-align: center;">연계일</td>
												</tr>
											</thead>
											<tbody id="projectCustomerInfoTableBody">
												<c:if test="${!empty projectCustomerInfoList}">
													<c:forEach var="rs" items="${projectCustomerInfoList}">
														<tr>
															<td style="text-align: center;"><input type="checkbox" name="seq" value="<c:out value="${rs.seq}"/>"></td>
															<td style="text-align: center;">
																<c:choose>
																	<c:when test="${rs.customerInfoType eq 'CTM'}">잠재니즈</c:when>
																	<c:otherwise>표출니즈</c:otherwise>
																</c:choose>
															</td>
															<td style="text-align: left; cursor: hand;" onclick="openCustomerInfo(<c:out value="${rs.customerInfoIdx}"/>)">
																<c:out value="${rs.subject}"/>
															</td>
															<td style="text-align: center;"><c:out value="${rs.writer}"/> </td>
															<td style="text-align: center;"><c:out value="${rs.regDate}"/> </td>
															<td style="text-align: center;"><c:out value="${rs.createUserName}"/> </td>
															<td style="text-align: center;"><c:out value="${rs.createDate}"/> </td>
														</tr>
													</c:forEach>
												</c:if>
												<c:if test="${empty projectCustomerInfoList}">
													<tr>
														<td class="detailTableField_center" colspan="8">검색 결과가 없습니다. </td>
													</tr>
												</c:if>
											</tbody>
										</table>
	 								</td> 
							 	</tr>
								<tr> 
									<td height="36">
									<div class="btNbox txtR">
										<c:if test="${project.projectState == null || project.projectState < '3' || isAdmin == 'Y'}">
											<a class="btN006bc6 txt2btn" href="#" onclick="insertNewCI();">추가</a>
											<a class="btNe14f42 txt2btn" href="#" onclick="deleteSelectedCI();">삭제</a>
										</c:if>
											<a class="btNa0a0a0 txt2btn" href="#" onclick="window.close()">닫기</a>
									</div>
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
</body>

</html>					