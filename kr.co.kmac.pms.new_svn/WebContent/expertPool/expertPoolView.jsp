<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="kr.co.kmac.pms.expertpool.data.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function btnModify_click(){
	document.location = "/action/ExpertPoolManagerAction.do?mode=loadFormExpertPool&ssn=<c:out value="${expertPool.ssn}"/>";
}
function fileDownload(uuid){
	if(uuid != ""){
		if($$("#_targetDownload").length == 0)
			document.body.insertAdjacentHTML('beforeEnd', "<iframe id=\"_targetDownload\" src=\"\" style=\"display:none\"></iframe>");
    	$("_targetDownload").src = "/servlet/PhotoDownLoadServlet?fileId=" + uuid;
	}
}
function goProjectDetail(projectCode, ssn) {
	var rateWin;
	var sfeature = "top=0,left=50,width=800,height=690,scrollbars=yes";
	/* 프로젝트 참여 이력 추가(상세)*/
	//var url = "https://newpms.kmac.co.kr/project/endProcess/projectEndInfoTab.jsp?viewMode=projectSearch&projectCode="+projectCode+"&businessTypeCode=BTD&processTypeCode=D1&evalRole=Y";
	var url = "/action/ProjectViewAction.do?mode=summaryForExpertPool&projectCode="+projectCode+"&ssn="+ssn; 
	rateWin = window.open(url,"rateWin",sfeature);
	rateWin.focus();
}
function goPrint() {
	open('','expertpoolPrintView','scrollbars=yes,width=740,height=800');			
	    document.pagingfrm.action="http://intranet.kmac.co.kr/kmac/expertpool/expert_print.asp";
		document.pagingfrm.target="expertpoolPrintView";
		document.pagingfrm.method="post";						
		document.pagingfrm.submit();			 
		document.pagingfrm.action="expertPoolview.jsp";
		document.pagingfrm.target="";
}
function saveToExcel(){
	var surl = '/action/ExpertPoolManagerAction.do?mode=saveToExcel&ssn=<c:out value="${expertPool.ssn}"/>';
	document.location = surl;
}

var myDate = new Date();
var nowYear = myDate.getFullYear();

function getAge(regno){
	var birthYear = regno.substring(0,2);
	var gbn       = regno.substring(6,7);
	var age = 0;
	if ((gbn == "1") || (gbn == "2")) {
		var age = parseInt(nowYear,10) - parseInt("19" + birthYear)+1;
	} else if ((gbn == "3") || (gbn == "4")) {
		var age = parseInt(nowYear,10) - parseInt("20" + birthYear)+1;
	} else {
		var age = "&nbsp;";
	}
	return age;	
}
function goPage(pageNumber){
	pagingfrm.pg.value = pageNumber;
	pagingfrm.submit();
}
</script>
</head>
<body>
<%-- 작업영역 --%>
<div class="location">
	<p class="menu_title">인력 POOL</p>
	<ul>
		<li class="home">HOME</li>
		<li>경영관리</li>
		<li>인력관리</li>
		<li>인력 POOL</li>
	</ul>
</div>

<div class="contents sub"><!-- 서브 페이지 .sub -->
		<div class="fixed_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1"><i class="mdi mdi-file-document-outline"></i>인력정보</p>
				</div>
				<div class="btn_area">
					<%if(session.getAttribute("jobClass").equals("A")){ %>
					<button type="button" class="btn line btn_blue" onclick="btnModify_click();"><i class="mdi mdi-square-edit-outline"></i>수정</button>
					<%} %>
					<!-- <button type="button" class="btn line btn_pink" onclick="location.href='javascript:;'"><i class="mdi mdi-trash-can-outline"></i>삭제</button> -->
					<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back();'"><i class="mdi mdi-backburger"></i>목록</button>
				</div>
			</div>	
			<div class="fixed_contents sc">
				
				<!-- 인력 정보 -->
				<div class="board_box">
					<div class="title">
						<div class="h1_area">
							<p class="h1">기본정보</p>
						</div>
					</div>
					<div class="board_contents">
						<div class="info_area">
							<div class="profile_box">
								<c:choose>
								<c:when test="${!empty expertPool.photo}">
									<div class="profile"><img id="MyPic" style="cursor:hand;" src="/servlet/PhotoDownLoadServlet?fileId=<c:out value="${expertPool.photo }"/>" ></div>		
								</c:when>
								<c:otherwise>
									<div class="profile"><img src="../resources/img/photo_none.png" alt="프로필 이미지"></div>			
								</c:otherwise>
							</c:choose>
								<p class="name"><c:out value="${expertPool.name}"/></p>
								
								<%ExpertPool ex = (ExpertPool)request.getAttribute("expertPool");%>	
								<%if(session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381") || session.getAttribute("dept").equals("9384")){ %>
									<p class="resident_num"><%=ex.getUid().substring(0,6) + "-" + ex.getUid().substring(6,13) %></p>
								<%} else {%>
									<p class="resident_num"><script>document.write(getAge('<%=ex.getUid().substring(0,7) %>'));</script>세 </p>
								<%}%>
								<ul>
									<li><i class="mdi mdi-gender-male mdi-gender-female"></i><i class="mdi mdi-gender-female"></i><p>
									<c:choose><c:when test="${expertPool.gender == '0'}">Female</c:when><c:otherwise>Male</c:otherwise></c:choose>
									</p></li>
									<li><i class="mdi mdi-map-marker-circle"></i><p><code:code tableName="NATIONALITY" code="${expertPool.nationality}"/></p></li>												
								</ul>
							</div>
							<div class="profile_detail_box">
								<div>
									<p class="profile_suject">개인정보</p>
									<ul>
										<li><i class="mdi mdi-cellphone"></i><p><c:out value="${expertPool.mobileNo}"/></p></li>
										<%if(session.getAttribute("dept").equals("2000") || session.getAttribute("dept").equals("2040") || session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381")){ %>
											<c:choose><c:when test="${expertPool.address1 eq '' || expertPool.address1 eq null}"> </c:when><c:otherwise>
												<li><i class="mdi mdi-email-newsletter"></i><p><c:out value="${expertPool.zipCode}"/>&nbsp;&nbsp;&nbsp;<c:out value="${expertPool.address1}"/> <c:out value="${expertPool.address2}"/></p></li>		
											</c:otherwise></c:choose>
										<%} %>
										<%if(session.getAttribute("userId").equals("eun") || session.getAttribute("userId").equals("chang") || session.getAttribute("userId").equals("yskim") || session.getAttribute("userId").equals("hindongs")){ %>	
											<li><i class="mdi mdi-bank"></i><p><c:out value="${bankAccount.bankName}"/> <c:out value="${bankAccount.bankAccount}"/></p></li>
										<%} %>									
									</ul>													
								</div>
								<div>
									<p class="profile_suject">회사정보</p>
									<ul>
										<li><i class="mdi mdi-domain"></i><p><c:out value="${expertPool.company }" /> <%if(session.getAttribute("dept").equals("2000") || session.getAttribute("dept").equals("2040") || session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381")){ %>(입사일: <c:out value="${expertPool.acctBeginDate}"/>)</p></li><%} %>
										<li><i class="mdi mdi-sitemap"></i><p><c:out value="${expertPool.deptName}"/>/<c:choose><c:when test="${(expertPool.jobClass == 'A')||(expertPool.jobClass == 'J') }"><org:role id="${expertPool.companyPosition}"/></c:when><c:otherwise><c:out value="${expertPool.companyPositionName}"/></c:otherwise></c:choose></p></li>
										<li><i class="mdi mdi-phone"></i><p><c:out value="${expertPool.companyTelNo}"/></p></li>											
										<li><i class="mdi mdi-fax"></i><p><c:out value="${expertPool.companyFaxNo}"/></p></li>											
										<li><i class="mdi mdi-email-outline"></i><p><a href="mailto:<c:out value="${expertPool.email}"/>"><c:out value="${expertPool.email}"/></a></p></li>		
									</ul>													
								</div>
								<div>
									<p class="profile_suject">프로필</p>
									<ul>
										<li><i class="mdi mdi-file-account"></i><p>
										<c:choose><c:when test="${expertPool.resume eq '' || expertPool.resume eq null }">
										</c:when><c:otherwise><a href="#" onclick="fileDownload('<c:out value="${expertPool.resume}" />');"><span id="resumeS"><c:out value="${expertPool.resumeName}" /></span></a><i class="mdi mdi-link-variant"></i>
										</c:otherwise></c:choose>
										</p></li>
									</ul>
								</div>
								<c:if test="${!empty hashTagList }">
								<div>
									<p class="profile_suject">전문분야·스킬</p>
									<ul class="hashtag_box">
										<c:forEach var="rs" items="${hashTagList}">	
										 	<li>
												#<c:out value="${rs.hashTag}"/>													
						        			</li>
						        		</c:forEach>
									</ul>												
								</div>
								</c:if>
							</div>											
						</div>
					</div>
				</div>
				<!-- // 인력 정보 -->
	
			<!-- KMAC 프로젝트 참여 이력 -->
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">KMAC 프로젝트 참여이력</p>
					</div>
					<button type="button" class="btn line btn_excel" onclick="saveToExcel()"><i class="mdi mdi-microsoft-excel"></i>엑셀저장</button>
				</div>
				<div class="board_contents">
					<table class="tbl-edit td-c"><!-- 요소들 100% 할 경우 -->
						<colgroup>
							<col style="width: 28%">
							<col style="width: 18%">
							<col style="width: 18%">
							<col style="width: 18%">
							<col style="width: 18%">
						</colgroup>
						<tr>
							<th>프로젝트</th>
							<th>고객사</th>
							<th>조직단위</th>
							<th>시작일</th>
							<th>종료일</th>
						</tr>				
						<tbody>
							<c:forEach var="projectInfoList" items="${projectInfoList.list}">
							<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
								<td style="cursor: hand;" title="프로젝트명을 클릭하면 상세정보를 볼 수 있습니다." onclick="goProjectDetail('<c:out value="${projectInfoList.projectCode}" />','<c:out value="${expertPool.ssn}"/>')"> <c:out value="${projectInfoList.projectName}"/></td>
								<td><c:out value="${projectInfoList.customerName}"/></td>
								<%-- <td><c:out value="${projectInfoList.bussinessTypeName}"/></td> --%>
								<td><c:out value="${projectInfoList.runningDivName}"/></td>
								<td>
									<fmt:parseDate value="${projectInfoList.realStartDate}" pattern="yyyyMMdd" var="realStartDate" />
									<fmt:formatDate value="${realStartDate}" pattern="yyyy-MM-dd" dateStyle="short" var="startDate" />
									<c:out value="${startDate}"/>
								</td>
								<td>
									<fmt:parseDate value="${projectInfoList.realEndDate}" pattern="yyyyMMdd" var="realEndDate" />
									<fmt:formatDate value="${realEndDate}" pattern="yyyy-MM-dd" dateStyle="short" var="endDate" />
									<c:out value="${endDate}"/>
								</td>						
							</tr>
							</c:forEach>
							<c:if test="${empty projectInfoList.list}"><tr><td colspan="5" align="center">프로젝트 참여 이력이 없습니다.</td></tr></c:if>
						</tbody>
					</table>
				</div>
				<div class="paging_area">
					<div class="paging">
						<form name="pagingfrm">
							<SCRIPT LANGUAGE="JavaScript">
								document.write( makePageHtml2( 
										<c:out value="${projectInfoList.valueListInfo.pagingPage}" default="1"/>, 
										10, 
										<c:out value="${projectInfoList.valueListInfo.totalNumberOfEntries}" default="0"/> , 
										10)
								) ;
							</SCRIPT>
							<div style="display:none;">								
								<input type="hidden" name="mode"     value="infoview">
								<input type="hidden" name="bbsId"    value="<c:out value="${bbsId}"/>">
								<input type="hidden" name="keyfield" value="<c:out value="${keyfield}"/>">
								<input type="hidden" name="keyword"  value="<c:out value="${keyword}"/>">
								<input type="hidden" name="ssn"  value="<c:out value="${expertPool.ssn}"/>">
								<input type="hidden" name="pg"   value="<c:out value="${pg}"/>">
								<input type="hidden" name="_id"   value="<%=session.getAttribute("userId")%>">
							</div>
						</form>	
					</div>	
				</div>						
			</div>
			<!-- // KMAC 프로젝트 참여 이력 -->	
			
			<%if(session.getAttribute("dept").equals("2000") || session.getAttribute("dept").equals("2040") ||
					session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381")){ %>
			<!-- 학력 사항 -->
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">학력 사항</p>
					</div>
				</div>
				<div class="board_contents">
					<table class="tbl-edit td-c"><!-- 요소들 100% 할 경우 -->
						<colgroup>
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: *">
						</colgroup>
						<tr>
							<th>기간</th>
							<th>학교명</th>
							<th>전공</th>
							<th>졸업구분</th>
							<th>위치</th>
							<th>비고</th>
						</tr>				
						<tbody id="schoolingList">	
						<c:forEach var="rs" items="${expertPool.expertPoolSchoolHst}">
							<tr>
								<td><c:out value="${rs.term}"/></td>
								<td><c:out value="${rs.schoolName}"/></td>
								<td><c:out value="${rs.major}"/></td>
								<td><c:out value="${rs.graduationState}"/></td>
								<td><c:out value="${rs.location}"/></td>
								<td><c:out value="${rs.remark}"/></td>
							</tr>
						</c:forEach>
						<c:if test="${empty expertPool.expertPoolSchoolHst}"><tr><td colspan="6">학력 사항이 없습니다.</td></tr></c:if>
						</tbody>
					</table>
				</div>
			</div>
			<!-- // 학력 사항 -->
			
			<!-- 경력 사항 -->
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">경력 사항</p>
					</div>
				</div>
				<div class="board_contents">
					<table class="tbl-edit td-c"><!-- 요소들 100% 할 경우 -->
						<colgroup>
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: *">
						</colgroup>
						<tr>
							<th>기간</th>
							<th>회사명</th>
							<th>부서</th>
							<th>직위</th>
							<th>담당업무</th>
							<th>비고</th>
						</tr>				
						<tbody id="careerList">
							<c:forEach var="rs" items="${expertPool.expertPoolCareerHst}">
								<tr>
									<td><c:out value="${rs.term}"/></td>
									<td><c:out value="${rs.companyName}"/></td>
									<td><c:out value="${rs.workingDept}"/></td>
									<td><c:out value="${rs.companyPosition}"/></td>
									<td><c:out value="${rs.workingDetail}"/></td>
									<td><c:out value="${rs.remark}"/></td>
								</tr>
							</c:forEach>
							<c:if test="${empty expertPool.expertPoolCareerHst}"><tr><td colspan="6">경력 정보가 없습니다.</td></tr></c:if>
						</tbody>
					</table>
				</div>
			</div>
			<!-- // 경력 사항 -->
			<%} %>
		</div>	
	</div>
</div>
</body>
</html>