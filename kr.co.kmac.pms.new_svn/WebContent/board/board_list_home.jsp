<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.common.util.DateUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<!-- 서브페이지에서만 사용 -->
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function btnNew_onclick(){
	document.location = "/action/BoardAction.do?mode=inputForm";
}
function doSearch() {
	frm.submit();
}
function goPage(pageNumber){
	frm.pg.value = pageNumber;
	frm.submit();
}
function sanctionItemClick(projectCode, approvalType, seq, workType, woriTypeUrl) {
	document.frm.target = "";		
	document.frm.action = woriTypeUrl+"&readonly=true&projectCode="+projectCode+"&approvalType="+approvalType+"&seq="+seq;
	document.frm.submit();
}
</script>
</head>
<body>
	<div id="sub_location">
		<div class="location">
			<p class="menu_title">
				<td width="100%" align="left">근무현황</td>
			</p>
			<ul>
				<li class="home">HOME</li>
				<!-- <li>스케줄 관리</li> -->
				<li>근무현황</li>
			</ul>
		</div>
	</div>
	
<form name="frm" method="post">
	<div class="contents sub">
		<div class="box">
			<div class="search_box">
				<div class="select_group">
				<div class="search_input_box">
					<select  name="keyfield" class="select">
						<option value=subject
							<c:if test="${keyfield == 'subject' }">selected</c:if>>제목</option>
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
		
		<!-- 본문 리스트 시작 -->
		<div class="box">
			<div class="a-both total">
				<p>
					총<span><c:out value="${result.valueListInfo.totalNumberOfEntries}" /></span>건
				</p>
				<div class="btn_area">
				<button type="button" class="btn btn_pink" onclick="location.href='/servlet/PhotoDownLoadServlet?fileId=b22aa1fa-9475-4763-bd9c-af6cfa1fff3c'">업무보고서 양식</button>
				<button type="button" class="btn btn_aqua" onclick="location.href='/action/GeneralSanctionAction.do?mode=loadFormGeneralSanction&approvalType=draft26'">등록</button>
				</div>
			</div>
			
			<div class="tbl-wrap sc">
					<table class="tbl-list all">
						<colgroup> 
							<col width="15%">
							<col width="25%">
							<col width="15%">
							<col width="15%">
							<col width="15%">
						</colgroup>	
						<thead>
							<tr>
								<th>결재유형</th>
								<th>제목</th>
								<th>소속</th>
								<th>기안자</th>
								<th>기안일</th>
								<th colspan="2">진행상태</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${!empty result.list}">
								<c:forEach var="rs" items="${result.list}">
									<tr style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)" onclick="sanctionItemClick('<c:out value="${rs.projectCode}" />', '<c:out value="${rs.approvalType}" />', '<c:out value="${rs.seq}" />', '<c:out value="${rs.workType}" />', '<c:out value="${rs.workTypeUrl}" />')">
										<fmt:parseDate value="${rs.registerDate}" type="DATE" dateStyle="SHORT" pattern="yyyy-MM-dd HH:mm:ss.SSS" var="var4" />
										<fmt:formatDate value="${var4}" pattern="yyyy-MM-dd" var="registerDate" />
										<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />
		
										<td><c:out value="${rs.approvalTypeName}" /></td>
										<td class="subject"><p><c:out value="${rs.projectName}" /></p></td>
										<td><c:out value="${rs.registerDeptName}" /></td>
										<td><c:out value="${rs.registerName}" /></td>
										<td><c:choose><c:when test="${registerDate eq null}">-</c:when><c:otherwise><c:out value="${registerDate}" /></c:otherwise></c:choose></td>
										<c:choose><c:when test="${rs.present == '반려'}"><td><font color="#F290A5">반려&nbsp<span class="ico-progress"></span></td></c:when>
												  <c:when test="${rs.present == '종료' }"><td colspan="2">종료</td></c:when>
												  <c:otherwise><td><c:out value="${rs.present}" /></td></c:otherwise></c:choose>
										<c:choose><c:when test="${rs.present == '종료' }"></c:when><c:otherwise><td><c:out value="${rs.presentName}" /></td></c:otherwise></c:choose>
									</tr>
								</c:forEach>
							</c:if>
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
				<input type="hidden" name="mode"     	value="selectList">
				<input type="hidden" name="bbsId"    	value="<c:out value="${bbsId}"/>">
				<input type="hidden" name="keyfield" 	value="<c:out value="${keyfield}"/>">
				<input type="hidden" name="keyword"  	value="<c:out value="${keyword}"/>">
				<input type="hidden" name="pg"       	value="<c:out value="${pg}"/>">
				<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>">
				<input type="hidden" name="projectName" value="<c:out value="${projectName}"/>">
			</div>
		</div>
	</form>
</body>
</html>