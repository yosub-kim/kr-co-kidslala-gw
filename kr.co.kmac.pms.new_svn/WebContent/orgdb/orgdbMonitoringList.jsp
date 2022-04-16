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
function doSearch() {
	var sfrm = document.forms["frm"];
	if(sfrm.fromDate.value > sfrm.toDate.value){
		alert("검색 기간 설정 시 시작일이 종료일 이전이어야 합니다.");
		return;
	} else { 
		sfrm.submit();
	}
}
</script>
</head>
<body>
	<%-- 작업영역 --%>
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td height="12">
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
					<jsp:param name="title" value="협력기관 모니터링" />
				</jsp:include>
			</td>
		</tr>
		<tr>
			<td>
				<!-- 검색bar 시작 -->
				<%-- @param :  B 또는 S --%>
				<%-- @description : 안의 로고 이미지의 사이즈 --%>
				<jsp:include page="/common/include/searchBox_Header.jsp" flush="true">
					<jsp:param name="btnSize" value="S" />
				</jsp:include>
					<table border="0" width="100%"  height="26">
					<form name="frm">
						<tr>
							<td class="searchTitle_center" width="70">구분</td>
							<td class="searchField_left" width="100">
								<select name="category" class="selectbox" style="width:100px;">
									<option value="">[::전체::]</option>
									<option value="전문가 소속기관">전문가 소속기관</option>
									<option value="프로젝트 협력사">프로젝트 협력사</option>
									<option value="고객정보">고객정보</option>
								</select>
							</td>
							<td class="searchTitle_center" width="70" >등록일</td>
							<td class="searchField_left" valign="bottom">
								<script>
									jQuery(function(){jQuery( "#fromDate" ).datepicker({});});
									jQuery(function(){jQuery( "#toDate" ).datepicker({});});
								</script>
								<input type="text" name="fromDate" id="fromDate" size="8" value="<c:out value="${pageData.fromDate}"/>">
								~
								<input type="text" name="toDate" id="toDate" size="8" value="<c:out value="${pageData.toDate}"/>">
							</td>
							<td class="searchTitle_center" width="70">등록자</td>
							<td class="searchField_left"  width="100">
								<input type="text" name="writerName" class="textInput_left" value="<c:out value="${pageData.writerName}"/>">
								<input type="hidden" name="mode" value="selectList">
							</td>	
						</tr>
						</form>
					</table>
				<%@ include file="/common/include/searchBox_Footer.jsp"%>
				<!-- 검색bar 종료 -->
			</td>
		</tr>
		<tr>
			<td>
				<div class="btNbox pb5">
						<img src="/images/sub/line3Blit.gif" alt="">
						<span class="bold colore74c3a"><c:out value="${result.valueListInfo.totalNumberOfEntries}"/></span>
						<span>Total - Page(<span><c:out value="${result.valueListInfo.pagingPage}"/></span> of <span><c:out value="${result.valueListInfo.totalNumberOfPages}"/></span>)</span>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<table class="listTable" style="table-layout:fixed;">
					<colgroup>
						<col style="width:250px;"/>
						<col style="width:    *;"/>
						<col style="width:250px;"/>
					</colgroup>
					<thead>
						<tr>
							<td>등록자</td>
							<td>구분</td>
							<td>건수</td>
						</tr>
					</thead>
					<tbody id="ListData">
						<c:forEach var="list" items="${result.list}"> 
							<c:set var="categoryCode" value=""/>
							<c:choose>
								<c:when test="${list.category=='전문가 소속기관'}">
									<c:set var="categoryCode" value="2"/>
								</c:when>
								<c:when test="${list.category=='프로젝트 협력사'}">
									<c:set var="categoryCode" value="3"/>
								</c:when>
								<c:when test="${list.category=='고객정보'}">
									<c:set var="categoryCode" value="1"/>
								</c:when>
							</c:choose>
							<tr>
								<td>
									<a href="?mode=selectMonitoringDetailList&writerSsn=<c:out value="${list.writerSsn}"/>&category=<c:out value="${categoryCode}"/>"
									   title="<c:out value="${obj.orgName}" escapeXml="false"/>"><c:out value="${list.writerName}" escapeXml="false"/>
									</a>
								</td>
								<td><c:out value="${list.category}"/></td>
								<td><c:out value="${list.cnt}"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>				
			</td>
		</tr>
		<tr>
			<td align="center">
				<%-- 페이징 영역 시작--%>
				<script>
				function goPage(pageNumber){
					pagingfrm.pg.value = pageNumber;
					pagingfrm.submit();
				}
				</script>
				<form name="pagingfrm" style="margin: 0px; padding: 0px;">
					<div>
					<%-- 페이징 영역 시작--%>
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<SCRIPT LANGUAGE="JavaScript">
										document.write( makePageHtml( 
												<c:out value="${result.valueListInfo.pagingPage}" default="1"/>, 
												15, 
												<c:out value="${result.valueListInfo.totalNumberOfEntries}" default="0"/> , 
												15)
										) ;
									</SCRIPT>
								</td>
							</tr>
						</table>
					</div>
					<input type="hidden" name="mode"		value="selectList">
					<input type="hidden" name="category"	value="<c:out value="${pageData.category}"/>">
					<input type="hidden" name="writerName"	value="<c:out value="${pageData.writerName}"/>">							
					<input type="hidden" name="fromDate"	value="<c:out value="${pageData.fromDate}"/>">
					<input type="hidden" name="toDate"		value="<c:out value="${pageData.toDate}"/>">
					<input type="hidden" name="pg"			value="<c:out value="${pageData.pg}"/>">
				</form>
				<%-- 페이징 영역 끝--%>
			</td>
		</tr>
	</table>
</body>
</html>