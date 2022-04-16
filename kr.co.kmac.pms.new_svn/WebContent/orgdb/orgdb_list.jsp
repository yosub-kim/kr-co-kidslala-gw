<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
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
function btnNew_onclick(){
	document.location = "/action/ExpertPoolManagerAction.do?mode=loadFormExpertPool";
}

function doSearch() {
	var sfrm = document.forms["frm"];
	sfrm.submit();
}
function keyfield_onchange(obj) {
	var sfrm = document.forms["frm"];
	if(obj.selectedIndex != 0) {
		sfrm.keyword.readOnly = false;
	} else {
		sfrm.keyword.value = "";
		sfrm.keyword.readOnly = true;
	}
}
function goPage(pageNumber){
	pagingfrm.pg.value = pageNumber;
	pagingfrm.mode.value = "selectList";
	pagingfrm.submit();
}
function detail(orgCode) {
	document.location.href = "?mode=select&orgCode="+orgCode;
}
</script>
</head>
<body>
	<form name="frm">
	
	<!-- location -->
		<div class="location">
			<p class="menu_title">협력사 등록 관리</p>
			<ul>
				<li class="home">HOME</li>
				<li>네트워크</li>
				<li>Infra 관리</li>
				<li>협력사 등록 관리</li>
			</ul>
		</div>
	<!-- // location -->
	
		<!-- contents sub -->
	<div class="contents sub">
	<div class="box">

				<div class="search_box total">
					<div class="select_group">
						<div class="select_box">
							<div class="label_group">
								<p>전문 분야</p>
								<code:codelist tableName="RELATION_WITH_KMAC_CODE" attribute=" name='relWithKmacCode' class='select' " isSelectBox="Y" all="Y" selectedInfo="${pageData.relWithKmac}" />
							</div>
							<div class="label_group">
								<p>업체명</p>
								<input type="text" name="companyName" class="textInput_left" value="<c:out value="${pageData.companyName}"/>" >
								<input type="hidden" name="mode" value="selectList">
							</div>
						</div>
					</div>
					<div><button type="button" class="btn btn_blue" onclick="doSearch()">검색 <i class="mdi mdi-magnify"></i></button></div>
				</div>
			</div>
		</form>
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">협력사 현황</p>
					</div>
					<div class="select_box">
						<button type="button" class="btn line btn_blue" onclick="location.href='/action/OrgdbAction.do?mode=loadForm'"><i class="mdi mdi-square-edit-outline"></i>등록</button>
					</div>
				</div>
		
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
							</colgroup>
							<tr>
								<th>회사명</th>
								<th>연락처</th>
								<th>전문 분야</th>
								<th>등록자</th>
								<th>승인 여부</th>
							</tr>
							<tbody id="ListData">
							<c:forEach var="obj" items="${result.list}" varStatus="i">
								<tr onclick="detail('<c:out value="${obj.orgCode}" />')" style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)" >
									<td><c:out value="${obj.orgName }" /></td>
									<td><c:out value="${obj.telNo }"/></td>
									<td><c:out value="${obj.relWithkmac}"/><c:if test="${obj.relWithkmac eq '기타' }" >&nbsp(<c:out value="${obj.specialField }" />)&nbsp</c:if></td>
									<td><c:out value="${obj.writer }"/></td>
									<td>
										<c:if test="${obj.checkYN eq 'Y' }">
											<span class="c-ing">승인 완료</span>
										</c:if>
										<c:if test="${obj.checkYN eq 'N' }">
											<span class="c-new">미승인</span>
										</c:if>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty result.list}"><td colspan='5' align='center'><br>검색 결과가 존재하지 않습니다 .</td></c:if>
							</tbody>
					</table>
			<form name="pagingfrm" style="margin: 0px; padding: 0px;">
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
				<div style="display:none">
					<input type="hidden" name="mode"				value="selectList">
					<input type="hidden" name="businessTypeCode"	value="<c:out value="${pageData.businessType}"/>">
					<input type="hidden" name="relWithKmacCode"		value="<c:out value="${pageData.relWithKmac}"/>">							
					<input type="hidden" name="specialFieldCode"	value="<c:out value="${pageData.specialField}"/>">
					<input type="hidden" name="keyfield"			value="<c:out value="${pageData.keyfield}"/>">
					<input type="hidden" name="keyword"				value="<c:out value="${pageData.keyword}"/>">
					<input type="hidden" name="pg"					value="<c:out value="${pageData.pg}"/>">
				</div>
				</form>
		</div>
		</div>
		</div>
</body>
</html>