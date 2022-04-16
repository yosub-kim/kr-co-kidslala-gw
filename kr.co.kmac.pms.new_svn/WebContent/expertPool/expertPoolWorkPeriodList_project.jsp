<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
	String companyPosition = request.getParameter("companyPosition");
	String titleMsg = "디렉터 투입현황";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=titleMsg %></title>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function detail(ssn) {
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}
function doSearch() {
	document.frm.submit();
}
function home_schedule(ssn){
	var url = "/action/BoardAction.do?mode=selectList_home&bbsId=home&writerId="+ssn;
	var sFeatures = "top=50,left=50,width=800,height=600,resizable=no,scrollbars=yes";
 	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_schedule", sFeatures);
/* 	detailWin.focus(); */
}

/* function goErpDetail(entno, ssn) {
	var url = "https://budgettest.kmac.co.kr:8080/DWPM3/DWPM30180_L.jsp?entno="+entno+"&empno="+ssn;
	var sFeatures = "top=50,left=50,width=600,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_schedule", sFeatures);
	detailWin.focus();
} */

</script>
</head>


<body>
<form name="frm" method="post">
	<input type="hidden" id="today" name="today" value="<c:out value="${today}" />" />
	<!-- location -->
	<div class="location">
		<p class="menu_title">디렉터 투입현황</p>
		<ul>
			<li class="home">HOME</li>
			<li>디렉터 투입현황</li>
		</ul>
	</div>
	<!-- // location -->
	
	<div class="contents sub">
		<div class="box">
			<div class="search_box">
				<div class="select_group">
					<div class="search_input_box">
						<select  name="keyfield" class="select">
							<option value="userName" <c:if test="${keywordType=='userName'}">selected</c:if>>성명</option>
							<option value="projectName" <c:if test="${keywordType=='projectName'}">selected</c:if>>프로젝트명</option>
						</select>
						<div class="search_input">
							<label for="search"></label> 
							<input type="text" name="keyword" value="<c:out value="${keyword}"/>" class="textInput_left" style="width: 100%;" onkeypress="if(event.keyCode == 13) doSearch();">
						</div>
					</div>
				</div>
				<div><button type="button" class="btn btn_blue" onclick="location.href='javascript:doSearch();'">검색</button></div>
			</div>
		</div>
<%-- 		<!-- 검색 영역 -->
		<%if(!session.getAttribute("companyPosition").equals("16DJR")){ %>
		<tr>
			<td height="5px">
				<div class="btNbox txtR">
					<font size="2px">(금액단위 :만원)</font>&nbsp
				</div>
			</td>
		</tr>
		<%} %> --%>
		
		<div class="box">
			<div class="board_contents">
				<div class="tbl-wrap sc" style="height: 800px;">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<thead>
						<colgroup>
							<col style="width: 10%" />
							<col style="width: 10%" />
							<col style="width: *" />
							<col style="width: 10%" />
							<col style="width: 15%" />
							<col style="width: 15%" />
							<col style="width: 15%" />
						</colgroup>
						<tr style="position:sticky; top: 0px;">
							<th>성명</th>
							<th>구분</th>
							<th>프로젝트</th>
							<th>역할</th>
							<th>기간</th>	
							<th>담당본부</th>	
							<th>총성과급 (지급 성과급)</th>		
						</tr>
					</thead>
					<tbody id="ListData">
						<c:choose><c:when test="${!empty list.list}">
							<c:set var="nameChk" value="" />
							<c:set var="gubunChk" value="" />
							<c:set var="chk" value="1" />
							<c:forEach var="rs" items="${list.list}">
								<c:choose>
									<c:when test="${chk eq '1' }">
										<tr style="text-align: center; border-top: 2px solid #9D9D9D;">
									</c:when>
									<c:otherwise>
										<tr>
									</c:otherwise>
								</c:choose>
									<c:if test="${rs.name != nameChk }">										
										<td rowspan="<c:out value="${rs.ssnCnt}"/>" style="text-align: center" ><c:out value="${rs.name }" /></td>
									</c:if>
									<c:choose>
									<c:when test="${rs.stateChk == 'Y' }">
									<c:if test="${rs.gubunChk != gubunChk }">
										<td rowspan="<c:out value="${rs.progressCnt}"/>" style="text-align: center" >현재</br>투입 중</br>(PMS)</td>
									</c:if>
									</c:when>
									<c:when test="${rs.stateChk == 'N' }">
									<c:if test="${rs.gubunChk != gubunChk }">
										<td rowspan="<c:out value="${rs.progressCnt}"/>" style="text-align: center; background: #DBF0FE;" >투입예정</br></td>
									</c:if>
									</c:when>
									</c:choose>
									<c:choose>
									<c:when test="${rs.stateChk == 'Y' }">
										<td><c:out value="${rs.projectName }" /></td>
										<td><c:out value="${rs.role }" /></td>
										<td><c:out value="${rs.resultDate }" /></td>
										<td><c:out value="${rs.dept }" /></td>
										<%if(!session.getAttribute("companyPosition").equals("16DJR")){ %>
										<td><c:out value="${rs.monthlyAmount }"/></td>
										<%} %>
										<%-- <td><img alt="예산서" src="/images/btn_glass.jpg"  style="cursor: hand;" onclick="goErpDetail('<c:out value="${rs.entno}"/>','<c:out value="${rs.ssn}"/>')" /></td> --%>
									</c:when>
									<c:when test="${rs.stateChk == 'N' }">
										<td style="text-align: center; background: #DBF0FE;"><c:out value="${rs.projectName }" /></td>
										<c:if test="${rs.gubunChk != gubunChk }">
											<td rowspan="<c:out value="${rs.progressCnt}"/>" style="text-align: center; background: #DBF0FE;" >예상착수시기</td>
										</c:if>
										<td style="text-align: center; background: #DBF0FE;"><c:out value="${rs.resultDate }" />
										<td style="text-align: center; background: #DBF0FE;"><c:out value="${rs.dept }" /></td>
										<%if(!session.getAttribute("companyPosition").equals("16DJR")){ %>
										<td style="text-align: center; background: #DBF0FE;"><c:out value="${rs.prj_Amount }" /></td>
										<%} %>
									</c:when>
									</c:choose>
								</tr>
								<c:if test="${chk == rs.ssnCnt}">
									<c:set var="chk" value="0" />
								</c:if>
								<c:set var="nameChk" value="${rs.name}"/>
								<c:set var="gubunChk" value="${rs.gubunChk }"/>
								<c:set var="chk" value="${chk + 1 }"/>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr><td align="center" colspan="7">검색된 데이터가 없습니다.</td></tr>
						</c:otherwise></c:choose>
					</tbody>
				</table>
				</div>
			</div>
	</div>
</div>
</form>
</body>
</html>