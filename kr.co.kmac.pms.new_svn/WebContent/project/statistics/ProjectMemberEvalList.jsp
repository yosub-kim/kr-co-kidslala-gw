<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="java.util.List"%>
<%@page import="kr.co.kmac.pms.project.statistics.data.ProjectMemberEvalList"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
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
function doSearch(){
	document.frm.submit();
}
function goPage(pageNumber){
	document.frm.pg.value = pageNumber;
	document.frm.submit();
}
function changeDiv(obj) {
	if(obj == 'A'){
		$('div0').innerHTML = $('div1').innerHTML; 
	}else if(obj == 'J'){
		$('div0').innerHTML = $('div2').innerHTML; 
	}else if(obj == 'C'){
		$('div0').innerHTML = $('div3').innerHTML;
	}else{
		$('div0').innerHTML = $('div4').innerHTML; 
	}
}

function goEvalDetail(item,ssn) {
	var year = document.frm.searchYear.value;
	/* var url = "/action/ProjectMemberEvalAction.do?mode=getConsultantEvalListDetail&year="+year+"&item=" + encodeURIComponent(item)+"&ssn="+ssn;
	window.open(url,'eval','top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=730,height=410,directories=no,menubar=no'); */
	
	location.href="/action/ProjectMemberEvalAction.do?mode=getConsultantEvalListDetail&year="+year+"&item=" + encodeURIComponent(item)+"&ssn="+ssn;
}

</script>
</head>

<body>
<form name="frm" method="post">
<%-- 작업영역 --%>

<!-- location -->
		<div class="location">
			<p class="menu_title">컨설턴트 평가 현황</p>
			<ul>
				<li class="home">HOME</li>
				<li>PMS</li>
				<li>프로젝트 현황</li>
				<li>컨설턴트 평가 현황</li>
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
						<p>평가 유형</p>
						<select name="item" class='select'>
							<option value="">전체</option>
							<option value="컨설턴트평가" <c:if test="${item == '컨설턴트평가'  }">selected</c:if>>컨설턴트평가</option>
							<option value="PL평가"  <c:if test="${item == 'PL평가'  }">selected</c:if>>PL평가</option>
							<option value="컨설팅고객평가"  <c:if test="${item == '컨설팅고객평가'  }">selected</c:if>>고객만족도(컨설팅)</option>
							<option value="리서치고객평가"  <c:if test="${item == '리서치고객평가'  }">selected</c:if>>고객만족도(리서치)</option>
							<option value="공개교육고객평가" <c:if test="${item == '공개교육고객평가'  }">selected</c:if>>고객만족도(공개교육)</option>
							<option value="사내교육고객평가"  <c:if test="${item == '사내교육고객평가'  }">selected</c:if>>고객만족도(사내교육)</option>
						</select>
					</div>
					<div class="label_group">
						<p>구분</p>
						<select name="jobClass" onchange="changeDiv(this.value);" class='select'>
						<option value="">전체</option>
						<option value="A" <c:if test="${jobClass == 'A' }">selected</c:if>>상근(1)</option>
						<option value="B" <c:if test="${jobClass == 'B' }">selected</c:if>>상근(2)</option>
						<option value="J" <c:if test="${jobClass == 'J' }">selected</c:if>>상임</option>
						<option value="C" <c:if test="${jobClass == 'C' }">selected</c:if>>엑스퍼트</option>
						<option value="H" <c:if test="${jobClass == 'H' }">selected</c:if>>AA</option>
						</select>
					</div>
					<div class="label_group">
						<p>소속</p>
						<div id="div0">
									<c:choose>
									<c:when test="${jobClass=='A'}">
										<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='select' " all="Y"></org:divList>
									</c:when>
									<c:when test="${jobClass=='J'}">
										<org:divList enabled="1" divType="J" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='select' " all="Y"></org:divList>
									</c:when>
									<c:when test="${jobClass=='C'}">
										<org:divList enabled="1" divType="C" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='select' " all="Y"></org:divList>
									</c:when>
									<c:when test="${jobClass=='H'}">
										<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='select' " all="Y"></org:divList>
									</c:when>
									<c:otherwise>
										<org:divList enabled="1" divType="" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='select' " all="Y"></org:divList>
									</c:otherwise>
									</c:choose>
								</div>
						</select>
					</div>
				</div>
				<div class="select_box">
					<div class="label_group">
						<p>연도</p>
						<date:year beforeYears="2" afterYears="2" attribute=" name='year' id='year' class='select'" selectedInfo="${year}" />				
					</div>
					<div class="label_group">
					<p>컨설턴트명</p>
					<input type="text" name="projectName" class="textInput_left" value="<c:out value="${projectName}"/>">
					</div>
				</div>
			</div>
			<div><button type="button" class="btn btn_blue" onclick="doSearch()">검색 <i class="mdi mdi-magnify"></i></button></div>
		</div>
		</div>

			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">컨설턴트 평가 현황</p>
					</div>
				</div>
		
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 20%" />
								<col style="width: 20%" />
								<col style="width: 20%" />
								<col style="width: 20%" />
								<col style="width: 20%" />
								<!-- <col style="width: 15%" /> -->
							</colgroup>
							<tr>
								<th>컨설턴트명</th>
								<th>직급</th>
								<th>비즈니스 타입</th>
								<th>평가 유형</th>
								<!-- <th>평가 점수</th> -->
								<th>평가 보기</th>	
							</tr>
							<tbody id="ListData">
								<c:choose>
								<c:when test="${!empty result.list }">
									<c:set var="ssn" value="" />
									<c:forEach var="rs" items="${result.list}" varStatus="i">
										<tr>
											<td><c:out value="${rs.name } " /></td>
											<td><c:out value="${rs.companyPosition } " /></td>
											<td><c:out value="${rs.businessType } " /></td>
											<td><c:out value="${rs.item } " /></td>
											<%-- <td><c:out value="${rs.total } " /></td> --%>
											<td>
												<button type="button" class="btn line btn_blue" onclick="goEvalDetail('<c:out value="${rs.item } " />','<c:out value="${rs.ssn}" />')"><i class="mdi mdi-square-edit-outline"></i>상세</button>
											</td>
										</tr>
										<c:set var="chk1" value="${rs.name}"/>
										<c:set var="chk2" value="${rs.companyPosition}"/>
										<c:set var="chk3" value="${rs.businessType}"/>
									</c:forEach>
								</c:when>
								</c:choose>
								<c:if test="${empty result.list}"><td colspan='5' align='center'>검색 결과가 존재하지 않습니다 .</td></c:if>
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
		</div>
		</div>
		<div style="display:none">
				<input type="hidden" name="mode"			id="mode"			value="getValueProjectMemberEvalList">
				<input type="hidden" name="pg"				id="pg"				value="<c:out value="${pg}"/>">
				<input type="hidden" name="item"			id="item"			value="<c:out value="${item}"/>">
				<input type="hidden" name="projectName"		id="projectName"    value="<c:out value="${projectName}"/>">
				<input type="hidden" name="consultantName"	id="consultantName" value="<c:out value="${consultantName}"/>">
				<input type="hidden" name="deptCode"		id="deptCode"		value="<c:out value="${deptCode}"/>">
				<input type="hidden" name="jobClass"		id="jobClass"		value="<c:out value="${jobClass}"/>">
				<input type="hidden" name="searchYear"		id="searchYear"		value="<c:out value="${year}"/>">
		
</div>
</form>
<div style="display: none;">
	<div id="div1">
		<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100%;' " all="Y"></org:divList>
	</div>
	<div id="div2">
		<org:divList enabled="1" divType="J" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100%;' " all="Y"></org:divList>
	</div>
	<div id="div3">	
		<org:divList enabled="1" divType="C" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100%;' " all="Y"></org:divList>
	</div>
	<div id="div4">
		<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100%;' " all="Y"></org:divList>
	</div>
</div>
</body>
</html>