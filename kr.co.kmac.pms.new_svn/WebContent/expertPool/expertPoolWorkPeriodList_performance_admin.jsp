<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
	String companyPosition = request.getParameter("companyPosition");
	String titleMsg = "인사평과 결과";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=titleMsg %></title>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">

<script type="text/javascript">
function performanceDetail(ssn){
	document.location.href="/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_performance&ssn="+ssn;
}
function goPage(next_page) {
	document.frm.pageNo.value = next_page ;
	document.frm.target = "";		
	document.frm.action = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_performance_admin";
	document.frm.submit();
}
function doSearch() {
	document.frm.target = "";		
	document.frm.action = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_performance_admin";
	document.frm.submit();
}

</script>
</head>


<body>
	<form name="frm" method="post">	
		<input type="hidden" name='pageNo' value="<c:out value="${list.valueListInfo.pagingPage}"/>"> 
		<div class="location">
			<p class="menu_title">인사평가 결과</p>
			<ul>
				<li class="home">HOME</li>
				<li>인사평가 결과</li>
			</ul>
		</div>
		<!-- // location -->
		
		<div class="contents sub"><!-- 서브 페이지 .sub -->
			<!-- 검색 창 -->					
			<div class="box">
				<div class="search_box total">
					<div class="select_group">
						<div class="select_box">
							<div class="label_group">
								<p>조직단위</p>
								<org:divList enabled="1" depth="2" attribute=" name='runningDeptCode' class='select' " 
									divType="A" all="Y" isSelectBox="Y" selectedInfo="${runningDeptCode}"></org:divList>
							</div>
							
							<div class="label_group">
								<p>성명</p>
								<input type="text" name="name" value="<c:out value="${name}"/>" class="textInput" style="width: 100%;" onkeypress="if(event.keyCode == 13) doSearch();">
							</div>
						</div>
					</div>
					<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색<i class="mdi mdi-magnify"></i></button></div>
				</div>
			</div>
			
			<div class="box">
				<div class="a-both total">
					<p>
						총 <span><c:out value="${list.valueListInfo.totalNumberOfEntries}" /></span>건
					</p>
				</div>
			
			<div style="margin-top: 10px; margin-left: 20px;margin-right: 20px;">
				<div class="tbl-wrap sc">
					<table class="tbl-edit td-c">
						<colgroup>
							<col style="width: 25%" />
							<col style="width: 25%" />
							<col style="width: 25%" />
							<col style="width: 25%" />
						</colgroup>
						<tr>
							<th>조직</th>
							<th>직위/직책</th>
							<th class="subject">성명</th>
							<th>결과</th>
						</tr>
						<tbody>
						<c:choose>
							<c:when test="${!empty list.list}">
								<c:forEach var="rs" items="${list.list}">
									<tr>
										<td><c:out value="${rs.dept }" /></td>
										<td><c:out value="${rs.companyPosition}" /></td>
										<td><c:out value="${rs.name }" /></td>
										<td style="cursor:pointer;"><a onclick="performanceDetail('<c:out value="${rs.ssn}"/>')" class="ico-file"></a>
										
										<%-- <button type="button" class="btn line btn_blue" onclick="performanceDetail('<c:out value="${rs.ssn}"/>')"><i class="mdi mdi-square-edit-outline"></i>상세</button></td>
									 --%> </tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr><td align="center" colspan="6">검색된 데이터가 없습니다.</td></tr>
							</c:otherwise>
						</c:choose>
						</tbody>
					</table>
				<div class="paging_area">
					<SCRIPT LANGUAGE="JavaScript">
						document.write( makePageHtml2( 
								<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
								10, 
								<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
								10)
						) ;
					</SCRIPT>
				</div>
			</div>
			</div>
		</div>
	</div>
</div>
</form>
</body>
</html>